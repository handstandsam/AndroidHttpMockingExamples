package wiremock;

import android.content.res.AssetManager;

import com.github.tomakehurst.wiremock.common.BinaryFile;
import com.github.tomakehurst.wiremock.common.TextFile;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class AndroidAssetsBinaryFile extends TextFile {

    private final AssetManager assetManager;

    private String assetPath;

    public AndroidAssetsBinaryFile(AssetManager assetManager, String assetPath) {
        super(null); // We're completely overriding the super() implementation
        this.assetManager = assetManager;
        this.assetPath = assetPath;
    }

    public byte[] readContents() {
        try {
            InputStream inputStream = assetManager.open(assetPath);
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    private void closeStream(InputStream stream) {
//        if (stream != null) {
//            try {
//                stream.close();
//            } catch (IOException var3) {
//                throw new RuntimeException(var3);
//            }
//        }
//    }

    public String name() {
        return assetPath;
    }

    public String toString() {
        return this.name();
    }

}
