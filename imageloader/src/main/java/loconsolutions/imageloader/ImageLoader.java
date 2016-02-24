package loconsolutions.imageloader;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import java.util.WeakHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ImageLoader extends Handler {

    /**
     * Source of image is url
     */
    public static final int SOURCE_TYPE_URL = 0;
    /**
     * Source of image is file path
     */
    public static final int SOURCE_TYPE_PATH = 1;

    /**
     * Source of image is url
     */
    public static final int SCALE_TYPE_FIT = 0;
    /**
     * Source of image is file path
     */
    public static final int SCALE_TYPE_CROP = 1;

    private static final int DISPLAY_IMAGE = 1000;
    private static final ThreadPoolExecutor EXECUTOR;
    private static final BlockingQueue<Runnable> mDecodeWorkQueue;
    private static final int DETAILS_TAG = R.id.image_loader_details_tag_key;
    private static final int BYTES_IN_KB = 1024;
    private static final int MEMORY_PART = 8;

    static {
        int NUMBER_OF_CORES =
                Runtime.getRuntime().availableProcessors();
        int KEEP_ALIVE_TIME = 1;
        TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        mDecodeWorkQueue = new LinkedBlockingQueue<>();
        EXECUTOR = new ThreadPoolExecutor(
                NUMBER_OF_CORES,       // Initial pool size
                NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue);
    }

    private final int mSourceType;
    private final WeakHashMap<ImageDetails, ImageView> mViewMap;
    private final WeakHashMap<Integer, ImageDetails> mDetailsMap;
    private final ScaledBitmapCache mScaledCache;

    /**
     * @param sourceType see {@link #SOURCE_TYPE_PATH} and {@link #SOURCE_TYPE_URL}
     */
    public ImageLoader(int sourceType) {
        super();
        mSourceType = sourceType;
        mViewMap = new WeakHashMap<>();
        mScaledCache = new ScaledBitmapCache(getCacheSize());
        mDetailsMap = new WeakHashMap<>();
    }

    public static int getCacheSize() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / BYTES_IN_KB);
        return (maxMemory / MEMORY_PART);
    }

    private static int getBitmapSize(Bitmap bitmap) {
        int byteSize = 0;
        if (null == bitmap) {
            return byteSize;
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            byteSize = bitmap.getByteCount();
        } else {
            byteSize = bitmap.getRowBytes() * bitmap.getHeight();
        }
        return byteSize / BYTES_IN_KB;
    }

    public void loadImage(ImageView view, String source) {
        loadImage(view, source, SCALE_TYPE_FIT);
    }

    public void loadImage(ImageView view, String source, int scaleType) {
        Logger.logd(this, "loadImage called");
        synchronized (mDetailsMap) {
            ImageDetails details = mDetailsMap.get(view.hashCode());
            if (null != details) {
                if (source.equals(details.getPath())) {
                    Logger.logd(this, "loadImage called, same request " + details);
                    return;
                }
                Logger.logd(this, "loadImage called, different request " + details);
                Thread t = details.getThread();
                if (null != t) {
                    Logger.logd(this, "loadImage called, different request interrupted");
                    t.interrupt();
                }
                mDetailsMap.remove(view.hashCode());
            }
        }
        ImageDetails imageDetails;
        switch (mSourceType) {
            case SOURCE_TYPE_PATH: {
                imageDetails = new FileImageDetails(this, view, source, scaleType);
                break;
            }
            default:
            case SOURCE_TYPE_URL: {
                imageDetails = new UrlImageDetails(this, view, source, scaleType);
                break;
            }
        }
        Bitmap b = getScaledBitmap(imageDetails);
        Logger.logd(this, "loadImage called, new request " + imageDetails);
        if (null == b) {
            Logger.logd(this, "loadImage called, new request bitmap not found");
            view.setImageBitmap(null);
            // execute runnable
            setImageView(imageDetails, view);
            EXECUTOR.execute(imageDetails);
            synchronized (mDetailsMap) {
                mDetailsMap.put(view.hashCode(), imageDetails);
            }
        } else {
            Logger.logd(this, "loadImage called, new request bitmap found");
            view.setImageBitmap(b);
        }
    }

    public void resetCache() {
        synchronized (mDetailsMap) {
            for (ImageDetails task : mDetailsMap.values()) {
                if (null != task.getThread()) {
                    task.getThread().interrupt();
                }
            }
        }
        synchronized (mScaledCache) {
            mScaledCache.evictAll();
        }
        synchronized (mViewMap) {
            mViewMap.clear();
        }
    }

    Bitmap getScaledBitmap(ImageDetails imageDetails) {
        synchronized (mScaledCache) {
            return mScaledCache.get(imageDetails);
        }
    }

    void setScaledBitmap(ImageDetails imageDetails, Bitmap scaledBitmap) {
        synchronized (mScaledCache) {
            mScaledCache.put(imageDetails, scaledBitmap);
        }
    }

    void displayImage(ImageDetails imageDetails) {
        obtainMessage(DISPLAY_IMAGE, imageDetails).sendToTarget();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case DISPLAY_IMAGE: {
                if (msg.obj instanceof ImageDetails) {
                    ImageDetails details = (ImageDetails) msg.obj;
                    Bitmap scaledBitmap = getScaledBitmap(details);
                    ImageView imageView = getImageView(details);
                    Logger.logd(this,
                            "display image details : " + details + ", bitmap : " + scaledBitmap +
                                    ", imageView : " + imageView);
                    setImageView(details, null);
                    if (null != imageView) {
                        synchronized (mDetailsMap) {
                            mDetailsMap.remove(imageView.hashCode());
                        }
                    }
                    if (null != imageView && null != scaledBitmap) {
                        imageView.setImageBitmap(scaledBitmap);
                    }
                }
                break;
            }
            default: {
                super.handleMessage(msg);
                break;
            }
        }
    }

    ImageView getImageView(ImageDetails details) {
        synchronized (mViewMap) {
            ImageView imageView = mViewMap.get(details);
            if (null == imageView || details.equals(imageView.getTag(DETAILS_TAG))) {
                Logger.logd(this,
                        "getImageView true details : " + details + ", imageView : " + imageView);
                return imageView;
            }
            Logger.logd(this, "getImageView false details : " + details + ", imageView : " + null);
            return null;
        }
    }

    void setImageView(ImageDetails details, ImageView view) {
        synchronized (mViewMap) {
            Logger.logd(this, "setImageView details : " + details + ", imageView : " + view);
            if (null == view) {
                mViewMap.remove(details);
            } else {
                view.setTag(DETAILS_TAG, details);
                mViewMap.put(details, view);
            }
        }
    }

    private static class ScaledBitmapCache extends LruCache<ImageDetails, Bitmap> {
        public ScaledBitmapCache(int cacheSize) {
            super(cacheSize);
        }

        @Override
        protected int sizeOf(ImageDetails key, Bitmap bitmap) {
            int size = getBitmapSize(bitmap);
            Logger.logd(this, "Size of bitmap " + bitmap + " = " + size);
            return size;
        }
    }
}