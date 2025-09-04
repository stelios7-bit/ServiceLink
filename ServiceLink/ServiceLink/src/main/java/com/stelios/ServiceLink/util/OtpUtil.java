package com.stelios.ServiceLink.util;

import java.util.Random;

public class OtpUtil {

    /**
     * Generates a 6-digit random OTP.
     * @return A 6-digit OTP as a String.
     */
    public static String generateOtp() {
        Random random = new Random();
        // Generate a random number between 100000 and 999999
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}