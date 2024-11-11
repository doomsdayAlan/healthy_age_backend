package com.healthyage.healthyage.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonObject;

@Configuration
public class FirebaseInitialize {
    @Value("${firebase.type}")
    private String type;
    @Value("${firebase.project-id}")
    private String projectId;
    @Value("${firebase.private-key-id}")
    private String privateKeyId;
    @Value("${firebase.private-key}")
    private String privateKey;
    @Value("${firebase.client-email}")
    private String clientEmail;
    @Value("${firebase.client-id}")
    private String clientId;
    @Value("${firebase.auth-uri}")
    private String authUri;
    @Value("${firebase.token-uri}")
    private String tokenUri;
    @Value("${firebase.authprovider-x509-cert-url}")
    private String authProviderX509CertUrl;
    @Value("${firebase.client-x509-cert-url}")
    private String clientX509CertUrl;
    @Value("${firebase.universe-domain}")
    private String universeDomain;

    @PostConstruct
    public void initialize() throws IOException {
        // Verifica si Firebase ya ha sido inicializado
        if (FirebaseApp.getApps().isEmpty()) {
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

            var options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();

            FirebaseApp.initializeApp(options);
        }
    }
}