package com.yilmaznaslan.tutorial.cryptography;

import java.security.*;

import static com.yilmaznaslan.tutorial.cryptography.KeyUtils.generateKeyPair;

public class DigitalSignatureSimpleDemo {

    void main() throws Exception {
        // Simulating sender (Jack) and receiver (Jill)
        KeyPair senderKeyPair = generateKeyPair();

        // Simulating document content
        String document = "This is the document content.";

        // Simulating hashing using SHA-256
        byte[] documentHash = calculateHash(document);

        // Sender signs the hash using their private key
        byte[] digitalSignature = signDocument(documentHash, senderKeyPair.getPrivate());

        // Receiver verifies the signature using sender's public key
        boolean isValid = verifySignature(documentHash, digitalSignature, senderKeyPair.getPublic());

        System.out.println("Digital Signature Verification: " + (isValid ? "Valid" : "Invalid"));
    }



    private static byte[] calculateHash(String data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes());
    }

    private static byte[] signDocument(byte[] documentHash, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(documentHash);
        return signature.sign();
    }

    private static boolean verifySignature(byte[] documentHash, byte[] digitalSignature, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(documentHash);
        return signature.verify(digitalSignature);
    }
}
