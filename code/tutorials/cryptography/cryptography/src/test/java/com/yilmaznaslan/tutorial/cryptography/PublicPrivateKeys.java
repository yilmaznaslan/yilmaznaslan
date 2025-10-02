package com.yilmaznaslan.tutorial.cryptography;

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
            // Choose the encryption algorithm (e.g., RSA)
            String algorithm = "RSA";

            // Create a KeyPairGenerator instance
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

            // Initialize the KeyPairGenerator with the desired key size
            int keySize = 2048;
            keyPairGenerator.initialize(keySize);

            // Generate the key pair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            System.out.println("Key Pair Generated:");
            System.out.println("Public Key: " + publicKey);
            System.out.println("Private Key: " + privateKey);

            // Save the keys to files
            saveKeyToFile("src/test/resources/public_key.pub", publicKey.getEncoded());
            saveKeyToFile("src/test/resources/private_key.key", privateKey.getEncoded());

            // Data to be encrypted
            String originalData = "Hello, world!";
            byte[] dataBytes = originalData.getBytes();

            // Encrypt the data using the public key
            byte[] encryptedData = encrypt(dataBytes, publicKey);

            // Decrypt the data using the private key
            byte[] decryptedData = decrypt(encryptedData, privateKey);

            // Display results
            System.out.println("Original Data: " + originalData);
            System.out.println("Encrypted Data: " + new String(encryptedData));
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
            // Choose the encryption algorithm (e.g., RSA)
            String algorithm = "RSA";

            // Create a KeyPairGenerator instance
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);

            // Initialize the KeyPairGenerator with the desired key size
            int keySize = 2048;
            keyPairGenerator.initialize(keySize);

            // Generate the key pair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Save the keys in PEM format
            saveKeyInPEMFormat("src/test/resources/private_key.pem", privateKey, "PRIVATE KEY");
            saveKeyInPEMFormat("src/test/resources/public_key.pem", publicKey, "PUBLIC KEY");

            System.out.println("Key Pair Generated and Saved in PEM Format.");
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
