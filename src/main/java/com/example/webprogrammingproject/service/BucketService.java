package com.example.webprogrammingproject.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BucketService {
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String save(MultipartFile file) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(UUID.randomUUID()).append('_').append(file.getOriginalFilename());
        storage.create(
                BlobInfo.newBuilder(bucketName, sb.toString())
                        .setContentType(file.getContentType())
                        .build(),
                file.getInputStream()
        );

        return sb.toString();
    }

    public void delete(String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        if (blob == null)
            throw new IllegalArgumentException("BucketService delete error: not found file");

        Storage.BlobSourceOption precondition =
                Storage.BlobSourceOption.generationMatch(blob.getGeneration());
        storage.delete(bucketName, fileName, precondition);
    }
}
