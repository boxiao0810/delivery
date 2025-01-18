package com.sky.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Data
@AllArgsConstructor
@Slf4j
public class LocalStorageUtil {
    private String path;

    public String upload(byte[] bytes, String objectName) throws IOException{
        String fullPath = path + File.separator + objectName;
        System.out.println(fullPath);

        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Failed to create directories" + path);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(fullPath)) {
            fos.write(bytes);
        }

        return fullPath;
    }
}
