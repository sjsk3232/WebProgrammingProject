package com.example.webprogrammingproject.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class CloudStorageConfig {

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private Resource gcpStorageCredentialsLocation;

    @Value("${spring.cloud.gcp.storage.project-id}")
    private String gcpStorageProjectId;

    @Bean
    public Storage storage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(gcpStorageCredentialsLocation.getInputStream());
        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(gcpStorageProjectId)
                .build()
                .getService();
    }
}