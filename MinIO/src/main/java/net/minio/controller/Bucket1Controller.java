package net.minio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import net.minio.service.MinioFileUploader;

@RestController
@RequestMapping("/api/files/bucket1")
public class Bucket1Controller {

    @Autowired
    private MinioFileUploader fileUploader;

    @Value("${minio.bucket1.name}")
    private String bucket1Name;

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            fileUploader.uploadFile(file, bucket1Name);
            return ResponseEntity.ok("File uploaded successfully to bucket1: " + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload file to bucket1: " + file.getOriginalFilename());
        }
    }
}