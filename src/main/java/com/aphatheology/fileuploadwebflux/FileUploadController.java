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


//    @PostMapping("/")
//    Mono<String> upload(@RequestBody MultipartFile file) {
//        return fileUploadService.upload(file);
//    }

    //    @GetMapping("/{fileName}")
//    Mono<ResponseEntity<String>> getFile(@PathVariable int fileId) {
//        return fileUploadService.getFile(fileId)
//                .map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
//    }

//    @GetMapping("/files")
//    Mono<String> upload() {
//        log.info("getting started");
//        return Mono.just("File uploaded successfully!");
//    }

//    @GetMapping(value = "/files/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<InputStream> getFile(@PathVariable String filename) {
//        return ResponseEntity.ok(this.fileUploadService.getFile(filename));
//    }

    @PostMapping
    public Mono<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return fileUploadService.upload(file);
    }

    @GetMapping(value = "/{filename:.+}", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<byte[]> getImage(@PathVariable String filename) throws IOException {
        return this.fileUploadService.getImage(filename);
    }


}
