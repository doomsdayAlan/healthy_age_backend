package com.healthyage.healthyage.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitialize {
    @Value("${healthyage.service.account.key}")
    private String firebaseFile;

    @PostConstruct
    public void initialize() throws IOException {
        FileInputStream refreshToken = new FileInputStream(firebaseFile);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }
}