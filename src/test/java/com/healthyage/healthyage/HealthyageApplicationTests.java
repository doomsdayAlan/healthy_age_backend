package com.healthyage.healthyage;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.healthyage.healthyage.config.FirebaseInitialize;

@SpringBootTest
class HealthyageApplicationTests {
    @MockBean
    private FirebaseInitialize firebaseInitialize;

    @Test
    void contextLoads() {
        // Prueba por defecto de spring
    }
}
