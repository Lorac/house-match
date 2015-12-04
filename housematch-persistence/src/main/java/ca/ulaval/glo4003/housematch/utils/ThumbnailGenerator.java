package ca.ulaval.glo4003.housematch.utils;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailGenerator {

    public void saveThumbnail(String fileName, String thumbnailFileName, Dimension thumbnailDimensions, String fileFormat)
            throws IOException {
        Thumbnails.of(new File(fileName)).size((int) thumbnailDimensions.getWidth(), (int) thumbnailDimensions.getHeight())
                .outputFormat(fileFormat).toFile(thumbnailFileName);
    }

}
