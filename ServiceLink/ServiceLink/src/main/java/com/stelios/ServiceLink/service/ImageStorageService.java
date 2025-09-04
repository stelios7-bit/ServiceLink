package com.stelios.ServiceLink.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final Path rootLocation = Paths.get("uploads");

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String store(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Failed to store empty file.");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uniqueFilename = UUID.randomUUID().toString() + extension;

        try (InputStream inputStream = file.getInputStream()) {
            Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFilename))
                    .normalize().toAbsolutePath();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}