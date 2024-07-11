package net.minio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.minio.service.MinioFileUploader;

@RestController
@RequestMapping("/api/files/bucket2")
public class Bucket2Controller {

    @Autowired
    private MinioFileUploader fileUploader;

    @Value("${minio.bucket2.name}")
    private String bucket2Name;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            fileUploader.uploadFile(file, bucket2Name);
            return ResponseEntity.ok("File uploaded successfully to bucket2: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file to bucket2: " + file.getOriginalFilename());
        }
    }
}