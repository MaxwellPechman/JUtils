package de.maxwell.utils.files;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static boolean createDirectoryAndFile(File file) throws IOException {
        if(FileUtils.hasDirectory(file)) {
            return file.createNewFile();

        } else {
            if(FileUtils.createDirectory(file)) {
                return file.createNewFile();
            }
        }

        return false;
    }

    public static boolean hasDirectory(File file) {
        if(!file.exists()) {
            return new File(file.getParent()).exists();
        }

        return true;
    }

    public static boolean createDirectory(File file) {
        return new File(file.getParent()).mkdirs();
    }

    public static String getExtension(File file) {
        String extension = "";
        String filename = file.toString();
        int index = filename.lastIndexOf('.');

        if(index > 0) {
            extension = filename.substring(index + 1);
        }

        return extension;
    }
}