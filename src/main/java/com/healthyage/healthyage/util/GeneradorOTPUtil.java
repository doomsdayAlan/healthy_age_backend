package com.healthyage.healthyage.util;

import java.security.SecureRandom;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneradorOTPUtil {
    private static final String CARACTERES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int LONG_OTP = 6;
    private static final SecureRandom ALEATORIO = new SecureRandom();

    public static String generarOTP() {
        var otp = new StringBuilder(LONG_OTP);

        for (var i = 0; i < LONG_OTP; i++) {
            otp.append(CARACTERES.charAt(ALEATORIO.nextInt(CARACTERES.length())));
        }
        
        return otp.toString();
    }
}
