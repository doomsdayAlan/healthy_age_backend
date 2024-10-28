package com.healthyage.healthyage.config;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;

@Service
public class FirebaseInitialize {
    @Value("${firebase.firestore.type}")
    private String type;
    @Value("${firebase.firestore.project-id}")
    private String projectId;
    @Value("${firebase.firestore.private-key-id}")
    private String privateKeyId;
    @Value("${firebase.firestore.private-key}")
    private String privateKey;
    @Value("${firebase.firestore.client-email}")
    private String clientEmail;
    @Value("${firebase.firestore.client-id}")
    private String clientId;
    @Value("${firebase.firestore.auth-uri}")
    private String authUri;
    @Value("${firebase.firestore.token-uri}")
    private String tokenUri;
    @Value("${firebase.firestore.authprovider-x509-cert-url}")
    private String authProviderX509CertUrl;
    @Value("${firebase.firestore.client-x509-cert-url}")
    private String clientX509CertUrl;
    @Value("${firebase.firestore.universe-domain}")
    private String universeDomain;

    @PostConstruct
    public void initialize() throws IOException {
        var jsonObject = new JsonObject();
        jsonObject.addProperty("type", type);
        jsonObject.addProperty("project_id", projectId);
        jsonObject.addProperty("private_key_id", privateKeyId);
        jsonObject.addProperty("private_key", privateKey.replace("\\n", "\n"));
        jsonObject.addProperty("client_email", clientEmail);
        jsonObject.addProperty("client_id", clientId);
        jsonObject.addProperty("auth_uri", authUri);
        jsonObject.addProperty("token_uri", tokenUri);
        jsonObject.addProperty("auth_provider_x509_cert_url", authProviderX509CertUrl);
        jsonObject.addProperty("client_x509_cert_url", clientX509CertUrl);
        jsonObject.addProperty("universe_domain", universeDomain);

        var refreshToken = new ByteArrayInputStream(jsonObject.toString().getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        FirebaseApp.initializeApp(options);
    }
}