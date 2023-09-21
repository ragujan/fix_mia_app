package com.fixmia.rag.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class Encryption {
    public static String encrypt1(String source) {
        String md5 = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(source.getBytes(), 0, source.length());
            BigInteger integer = new BigInteger(1, digest.digest());
            md5 = integer.toString(16);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return md5;

    }

    public static String encrypt(char[] source) throws NoSuchAlgorithmException {
        return DigestUtils.sha512Hex(new String(source));
    }

    public static byte[] encrypt2(char[] passwordByte) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        System.out.println(random.toString());
        KeySpec spec = new PBEKeySpec(new String(passwordByte).toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        System.out.println(hash);
        return hash;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] charPwd1 = new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
        char[] charPwd2 = new char[]{'b', 'a', 'a', '4', 'r', '3', 'e', 'd'};
//        System.out.println(encrypt(charPwd));
        encrypt2(charPwd1);
        byte[] bytes = encrypt2(charPwd2);
        char[] ch = new char[bytes.length];
        for (int i = 0; i <bytes.length ; i++) {
            ch[i] = (char) (bytes[i] & 0xFF);
        }
        System.out.println("byte array is "+bytes);
        System.out.println("char array is "+ch);

    }
}