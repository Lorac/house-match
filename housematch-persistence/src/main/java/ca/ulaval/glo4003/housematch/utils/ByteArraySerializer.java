package ca.ulaval.glo4003.housematch.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class ByteArraySerializer {

    public void serializeByteArrayToFile(byte[] fileBytes, String fileName) throws IOException {
        FileUtils.writeByteArrayToFile(new File(fileName), fileBytes);
    }

    public byte[] unserializeFileToByteArray(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        byte[] data = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return data;
    }

}
