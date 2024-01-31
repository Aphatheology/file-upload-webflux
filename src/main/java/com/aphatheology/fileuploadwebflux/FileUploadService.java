package com.aphatheology.fileuploadwebflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileUploadService {
    private final String uploadDirectory = "src/main/resources";

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
