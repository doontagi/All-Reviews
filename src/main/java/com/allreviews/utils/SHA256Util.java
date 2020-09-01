package com.allreviews.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {
    public static String encrypt(String str) {
        String SHA = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            byte byteData[] = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            SHA = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Encryption Error");
        }
        return SHA;
    }
}
