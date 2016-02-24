package loconsolutions.imageloader;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Comparator;

class UrlImageDetails extends ImageDetails {

    private static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    private static final int DEFAULT_READ_TIMEOUT = 30000;
    private static final String CACHE_DIR_NAME = "image_loader_cache";
    private static final int FILE_COUNT = 20;
    private static final long CACHE_MAX_SIZE = 5 * 1024 * 1024;

    private static final Comparator<File> FILE_COMPARATOR_DESCENDING = new Comparator<File>() {
        public int compare(File f1, File f2) {
            return compare(f1.lastModified(), f2.lastModified());
        }

        public int compare(long x, long y) {
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }
    };
    private static final int FIRST_INDEX = 0;

    public UrlImageDetails(ImageLoader loader, ImageView view, String source, int scaleType) {
        super(loader, view, source, scaleType);
    }

    private static synchronized File getCacheDir(File cacheDir) {
        File imgCacheDir = new File(cacheDir, CACHE_DIR_NAME);
        if (imgCacheDir.exists()) {
            if (!imgCacheDir.isDirectory()) {
                if (imgCacheDir.delete()) {
                    Logger.logd(UrlImageDetails.class, "Failed to delete cache as not dir");
                    return null;
                }
                if (imgCacheDir.mkdirs()) {
                    Logger.logd(UrlImageDetails.class,
                            "Failed to create cache dir after deleting ");
                    return null;
                }
            }
        } else {
            if (!imgCacheDir.mkdirs()) {
                Logger.logd(UrlImageDetails.class, "Failed to create cache dir");
                return null;
            }
        }
        return imgCacheDir;
    }

    private static synchronized void cleanCacheDir(File imgCacheDir) {
        File[] files = imgCacheDir.listFiles();
        while (null != files && files.length > FILE_COUNT) {
            Arrays.sort(files, FILE_COMPARATOR_DESCENDING);
            if (!files[FIRST_INDEX].delete()) {
                Logger.logd(UrlImageDetails.class, "delete fail while cleaning");
                return;
            }
            files = imgCacheDir.listFiles();
        }
    }

    @Override
    protected File fetchImageFile(String path) throws IOException {
        if (null == path) {
            return null;
        }
        Context context = getContext();
        File imgCacheDir = getCacheDir(context.getCacheDir());
        if (null == imgCacheDir) {
            return null;
        }
        String fileName = Integer.toString(path.hashCode());
        File outFile = new File(imgCacheDir, fileName);
        if (outFile.exists()) {
            return outFile;
        }
        outFile = new File(imgCacheDir, fileName);
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            URL imgUrl = new URL(getPath());
            URLConnection con = imgUrl.openConnection();
            con.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
            con.setReadTimeout(DEFAULT_READ_TIMEOUT);
            inputStream = con.getInputStream();
            outputStream = new FileOutputStream(outFile);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            cleanCacheDir(imgCacheDir);
            return outFile;
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != outputStream) {
                outputStream.close();
            }
        }
    }
}