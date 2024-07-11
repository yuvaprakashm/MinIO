package net.minio.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioFileUploader {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String defaultBucketName;

    public void createBucket(String bucketName) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (MinioException e) {
            System.err.println("MinIO exception occurred: " + e.getMessage());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }

    public void uploadFile(MultipartFile file, String bucketName) {
        try {
            createBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (MinioException e) {
            System.err.println("MinIO exception occurred: " + e.getMessage());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }

    public void uploadFileToDefaultBucket(MultipartFile file) {
        uploadFile(file, defaultBucketName);
    }
}
