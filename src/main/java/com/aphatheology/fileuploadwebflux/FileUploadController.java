package com.aphatheology.fileuploadwebflux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;


@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    public Mono<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @GetMapping(value = "/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<byte[]> getImage(@PathVariable String filename) throws IOException {
        return this.fileUploadService.getImage(filename);
    }


}
