package loconsolutions.imageloader;

import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

class FileImageDetails extends ImageDetails {

    public FileImageDetails(ImageLoader loader, ImageView view, String source, int scaleType) {
        super(loader, view, source, scaleType);
    }

    @Override
    protected File fetchImageFile(String path) throws IOException {
        return new File(path);
    }
}