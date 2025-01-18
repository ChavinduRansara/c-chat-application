package com.chavindu.c_chat.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    @Value("${application.file.uploads.media-output-path}")
    private String fileUploadPath;

    public String saveFile(
            @NonNull MultipartFile file,
            @NonNull String senderId
    ) {
        final String fileUploadSubPath = "users" + File.separator + senderId;
        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile file,@NonNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFile = new File(finalUploadPath);
        if (!targetFile.exists()) {
            boolean success = targetFile.mkdirs();
            if (!success) {
                log.warn("Failed to create directory, {}", targetFile.getAbsolutePath());
                return null;
            }
        }
        final String fileExtension = getFileExtention(file.getOriginalFilename());
        String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath, file.getBytes());
            log.info("Successfully uploaded file {}", targetFilePath);
            return targetFilePath;
        }catch (IOException e){
            log.error("File was not saved", e);
        }
        return null;
    }

    private String getFileExtention(String originalFilename) {
        if(originalFilename == null || originalFilename.isEmpty()) {
            return "";
        }
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if(lastDotIndex == -1) {
            return "";
        }
        return originalFilename.substring(lastDotIndex+1).toLowerCase();
    }
}
