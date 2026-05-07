package com.yilmaznaslan.lab.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class PublicPrivateKeys {

    @Test
    void test_generateKeyPairTutorial() {
        try {
            String algorithm = "RSA";
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            saveKeyToFile("src/test/resources/public_key.pub", publicKey.getEncoded());
            saveKeyToFile("src/test/resources/private_key.key", privateKey.getEncoded());

            String originalData = "Hello, world!";
            byte[] dataBytes = originalData.getBytes();
            byte[] encryptedData = encrypt(dataBytes, publicKey);
            byte[] decryptedData = decrypt(encryptedData, privateKey);

            System.out.println("Original Data: " + originalData);
            System.out.println("Decrypted Data: " + new String(decryptedData));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveKeyToFile(String fileName, byte[] keyBytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(keyBytes);
        }
    }

    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    @Test
    void testGenerateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            saveKeyInPEMFormat("src/test/resources/private_key.pem", privateKey, "PRIVATE KEY");
            saveKeyInPEMFormat("src/test/resources/public_key.pem", publicKey, "PUBLIC KEY");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveKeyInPEMFormat(String fileName, Key key, String header) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] encoded = key.getEncoded();
            String pemHeader = "-----BEGIN " + header + "-----\n";
            String pemFooter = "\n-----END " + header + "-----\n";
            fos.write(pemHeader.getBytes());
            fos.write(Base64.getMimeEncoder().encode(encoded));
            fos.write(pemFooter.getBytes());
        }
    }
}

