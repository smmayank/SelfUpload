package loconsolutions.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

abstract class ImageDetails implements Runnable {
    private static final int INVALID_DIMEN = 0;
    private static final long WAIT_TIME_OUT = 100;
    private static final int MAX_RETRY_COUNT = 20;
    private static int mScreenHeight;
    private static int mScreenWidth;
    private final ImageLoader mLoader;
    private final String mPath;
    private final Object mLock;
    private final ImageView mImageView;
    private final int mScaleType;

    private int mHeight;
    private int mWidth;
    private Thread mThread;

    public ImageDetails(ImageLoader loader, ImageView view, String source, int scaleType) {
        mLoader = loader;
        mPath = source;
        mHeight = view.getMeasuredHeight();
        mWidth = view.getMeasuredWidth();
        mImageView = view;
        mScaleType = scaleType;
        getScreenDimen();
        Logger.logd(this, "ImageDetails details : " + this);
        mLock = new Object();
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void getScreenDimen() {
        if (mScreenHeight > 0 && mScreenWidth > 0) {
            return;
        }
        WindowManager mgr =
                (WindowManager) mImageView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = mgr.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;
        mScreenWidth = displaymetrics.widthPixels;
    }

    private void updateDimen() {
        final int height = mImageView.getMeasuredHeight();
        final int width = mImageView.getMeasuredWidth();
        if (height == mHeight && width == mWidth) {
            return;
        }
        mLoader.setImageView(this, null);
        mHeight = mImageView.getMeasuredHeight();
        mWidth = mImageView.getMeasuredWidth();
        Logger.logd(this, "updateDimen details : " + this);
        mLoader.setImageView(this, mImageView);
    }

    protected final Bitmap getScaledBitmap() {
        return mLoader.getScaledBitmap(this);
    }

    protected final Bitmap createBitmap(File file, int reqWidth, int reqHeight)
            throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }

    public final void run() {
        mThread = Thread.currentThread();
        if (!execution()) {
            synchronized (mLock) {
                mLoader.setImageView(this, null);
            }
        } else {
            Logger.logd(this, "run, request " + this + ", thread interrupted");
        }
        displayImage();
        mThread = null;
    }

    private boolean isValidDimen() {
        return INVALID_DIMEN != mHeight && INVALID_DIMEN != mWidth;
    }

    private boolean execution() {
        int retryCount = 0;
        while (retryCount < MAX_RETRY_COUNT && !isValidDimen()) {
            try {
                retryCount++;
                synchronized (mLock) {
                    if (Thread.interrupted()) {
                        return false;
                    }
                    mLock.wait(WAIT_TIME_OUT);
                    updateDimen();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (!isValidDimen()) {
            Logger.logd(this, "execution, dimen not initialized after wait");
            return false;
        }
        Bitmap scaledBitmap = getScaledBitmap();
        Logger.logd(this, "execution, scaled request " + this + ", scaled image : " + scaledBitmap);
        if (null == scaledBitmap) {
            if (Thread.interrupted()) {
                return false;
            }
            Bitmap originalBitmap = null;
            try {
                originalBitmap =
                        createBitmap(fetchImageFile(mPath), mScreenWidth, mScreenHeight);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Logger.logd(this,
                    "execution, scaled request " + this + ", original image : " + originalBitmap);
            if (null != originalBitmap) {
                if (INVALID_DIMEN == mWidth || INVALID_DIMEN == mHeight) {
                    return false;
                }
                if (Thread.interrupted()) {
                    return false;
                }
                scaledBitmap = createScaledBitmap(originalBitmap);
            } else {
                Logger.logd(this, "Image cannot be downloaded from, " + mPath);
            }
        }
        if (null != scaledBitmap) {
            publishBitmap(scaledBitmap);
        } else {
            Logger.logd(this, "Scaled Image cannot be created, " + this);
        }
        return true;
    }

    String getPath() {
        return mPath;
    }

    protected Context getContext() {
        return mImageView.getContext();
    }

    private Bitmap createScaledBitmap(Bitmap originalBitmap) {
        Bitmap scaledBitmap;
        switch (mScaleType) {
            case ImageLoader.SCALE_TYPE_CROP: {
                scaledBitmap = ThumbnailUtils.extractThumbnail(originalBitmap, mWidth, mHeight);
                break;
            }
            default:
            case ImageLoader.SCALE_TYPE_FIT: {
                int dstWidth = mWidth;
                int dstHeight = mHeight;
                int origHeight = originalBitmap.getHeight();
                int origWidth = originalBitmap.getWidth();
                float srcAspect = (float) origWidth / (float) origHeight;
                float dstAspect = (float) dstWidth / (float) dstHeight;
                if (srcAspect > dstAspect) {
                    dstHeight = (int) (dstWidth / srcAspect);
                } else {
                    dstWidth = (int) (dstHeight * srcAspect);
                }
                scaledBitmap = Bitmap.createBitmap(dstWidth, dstHeight, Bitmap.Config.ARGB_8888);
                Canvas c = new Canvas(scaledBitmap);
                Paint p = new Paint();
                p.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
                Rect srcRect =
                        new Rect(0, 0, originalBitmap.getWidth(), originalBitmap.getHeight());
                Rect dstRect = new Rect(0, 0, dstWidth, dstHeight);
                c.drawBitmap(originalBitmap, srcRect, dstRect, p);
                break;
            }
        }
        originalBitmap.recycle();
        return scaledBitmap;
    }

    private void publishBitmap(Bitmap scaledBitmap) {
        mLoader.setScaledBitmap(this, scaledBitmap);
    }

    private void displayImage() {
        mLoader.displayImage(this);
    }

    protected abstract File fetchImageFile(String mSource) throws IOException;

    @Override
    public final String toString() {
        return "ImageDetails{" +
                "path='" + mPath + '\'' +
                ", height=" + mHeight +
                ", width=" + mWidth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ImageDetails that = (ImageDetails) o;

        if (mScaleType != that.mScaleType)
            return false;
        if (mHeight != that.mHeight)
            return false;
        if (mWidth != that.mWidth)
            return false;
        return mPath.equals(that.mPath);

    }

    @Override
    public int hashCode() {
        int result = mPath.hashCode();
        result = 31 * result + mScaleType;
        result = 31 * result + mHeight;
        result = 31 * result + mWidth;
        return result;
    }

    Thread getThread() {
        return mThread;
    }
}