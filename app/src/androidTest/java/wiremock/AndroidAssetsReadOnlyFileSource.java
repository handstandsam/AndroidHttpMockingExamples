package wiremock;

import android.content.res.AssetManager;

import com.github.tomakehurst.wiremock.common.BinaryFile;
import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.TextFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * WARNING: This is currently be developed and not working yet.
 */
public class AndroidAssetsReadOnlyFileSource implements FileSource {

    Logger logger = LoggerFactory.getLogger(AndroidAssetsReadOnlyFileSource.class);

    private final AssetManager assetManager;

    private String assetPath;

    public AndroidAssetsReadOnlyFileSource(AssetManager assetManager) {
        this(assetManager, "");
    }

    public AndroidAssetsReadOnlyFileSource(AssetManager assetManager, String assetPath) {
        this.assetManager = assetManager;
        this.assetPath = assetPath;
    }

    @Override
    public BinaryFile getBinaryFileNamed(String name) {
        return new AndroidAssetsBinaryFile(assetManager, assetPath + "/" + name);
    }

    @Override
    public TextFile getTextFileNamed(String s) {
        return null;
    }

    @Override
    public void createIfNecessary() {
        return;
    }

    @Override
    public FileSource child(String name) {
        return new AndroidAssetsReadOnlyFileSource(assetManager, name);
    }

    @Override
    public String getPath() {
        return assetPath;
    }

    @Override
    public URI getUri() {
        return null;
    }

    @Override
    public List<TextFile> listFilesRecursively() {
        logger.debug("list files recursively");
        try {
            String[] nameList = assetManager.list(assetPath);
            List<TextFile> textFiles = new ArrayList<>();
            for (String name : nameList) {
                TextFile textFile = new AndroidAssetsBinaryFile(assetManager, assetPath + "/" + name);
                textFiles.add(textFile);
            }
            return textFiles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void writeTextFile(String name, String contents) {
        return;
    }

    @Override
    public void writeBinaryFile(String name, byte[] contents) {
        return;
    }

    @Override
    public boolean exists() {
        boolean exists = false;
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(assetPath);
            exists = true;
        } catch (IOException e) {
            e.printStackTrace();

            try {
                String[] strings = assetManager.list(assetPath);
                logger.debug(strings.toString());
                exists = true;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return exists;
    }

    @Override
    public void deleteFile(String s) {

    }
}
