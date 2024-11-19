package com.healthyage.healthyage.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.JsonObject;

import jakarta.annotation.PreDestroy;

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

    @Bean
    Firestore firestore() throws IOException {
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
            var credentials = new ByteArrayInputStream(jsonObject.toString().getBytes());

            var firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(credentials))
                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
        }

        return FirestoreClient.getFirestore(); 
    }

    @PreDestroy
    public void cleanUp() {
        if (!FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.getInstance().delete();
        }
    }
}