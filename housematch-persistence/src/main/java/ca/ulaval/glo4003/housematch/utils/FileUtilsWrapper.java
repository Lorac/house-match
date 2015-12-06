package ca.ulaval.glo4003.housematch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileUtilsWrapper {

    public void writeByteArrayToFile(byte[] fileBytes, String fileName) throws IOException {
        FileUtils.writeByteArrayToFile(new File(fileName), fileBytes);
    }

    public byte[] readByteArrayFromFile(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        byte[] data = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return data;
    }

    public void deleteFile(String fileName) throws IOException {
        FileUtils.deleteQuietly(new File(fileName));
    }

}
