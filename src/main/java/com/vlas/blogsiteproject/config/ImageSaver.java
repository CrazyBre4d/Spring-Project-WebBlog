package com.vlas.blogsiteproject.config;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class ImageSaver {
    private static final String UPLOAD_DIR = "D:/post_images"; // Директория для загрузки файлов
    public static String save(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        String filePath = Paths.get(UPLOAD_DIR, fileName).toString();
        Files.write(Paths.get(filePath), imageFile.getBytes());
        return filePath;
    }
}
