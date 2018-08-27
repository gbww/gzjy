package com.gzjy.common.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUpload {

    public static void upload(InputStream localInputStream, String remoteFile) throws IOException {
        File file = new File(remoteFile);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(localInputStream));
                    localInputStream.close();
                }
            }
        } catch (IOException e) {

        }
    }
}
