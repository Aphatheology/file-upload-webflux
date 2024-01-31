package com.aphatheology.fileuploadwebflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {
    private final String uploadDirectory = "src/main/resources";
    public InputStream getFile(String filename) {
        try {
            Path file = Paths.get(uploadDirectory).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return new FileInputStream(file.toFile());
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Mono<byte[]> getImage(String imageName) throws IOException {
        Path imagePath = Path.of(uploadDirectory, imageName);

        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            return Mono.just(imageBytes);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    public Mono<String> upload(MultipartFile file) {
        try {
            Path filePath = Paths.get(uploadDirectory, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return Mono.just("File uploaded successfully!");
        } catch (IOException e) {
            return Mono.just("Failed to upload the file.");
        }
    }
}
