package ca.ulaval.glo4003.housematch.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceLoader {

    public InputStream loadResourceAsInputStream(Object classObject, String resourceName) throws FileNotFoundException {
        File file = getResourceAsFile(classObject, resourceName);
        try {
            return FileUtils.openInputStream(file);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an input stream.", file.getPath()), e);
        }
    }

    public OutputStream loadResourceAsOutputStream(Object classObject, String resourceName)
            throws FileNotFoundException {
        File file = getResourceAsFile(classObject, resourceName);
        try {
            return FileUtils.openOutputStream(file);
        } catch (IOException e) {
            throw new UncheckedIOException(
                    String.format("Failed to open file '%s' as an output stream.", file.getPath()), e);
        }
    }

    private File getResourceAsFile(Object classObject, String resourceName) throws FileNotFoundException {
        String resourceFilePath = getResourceFilePathFromClassPath(classObject, resourceName);
        File file = new File(resourceFilePath);
        return file;
    }

    private String getResourceFilePathFromClassPath(Object classObject, String resourceName)
            throws FileNotFoundException {
        String filePath;
        try {
            URL url = getResourceFromClassPath(classObject, resourceName);
            filePath = url.toURI().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to get resource URI of '%s'.", resourceName), e);
        }
        return filePath;
    }

    private URL getResourceFromClassPath(Object classObject, String resourceName) throws FileNotFoundException {
        URL url = classObject.getClass().getResource(resourceName);
        if (url == null) {
            throw new FileNotFoundException(String.format("Resource '%s' does not exist.", resourceName));
        }
        return url;
    }

}
