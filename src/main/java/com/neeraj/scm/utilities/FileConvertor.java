package com.neeraj.scm.utilities;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileConvertor {
    public static File convertMutiPartFileToFile(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error converting multipart file to file", e);
        }
        return file;
    }
}
