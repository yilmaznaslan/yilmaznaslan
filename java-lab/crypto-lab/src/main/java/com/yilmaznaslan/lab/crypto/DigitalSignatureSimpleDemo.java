package com.yilmaznaslan.lab.crypto;

import java.security.*;

import static com.yilmaznaslan.lab.crypto.KeyUtils.generateKeyPair;

public class DigitalSignatureSimpleDemo {

    public static void main(String[] args) throws Exception {
        KeyPair senderKeyPair = generateKeyPair();
        String document = "This is the document content.";
        byte[] documentHash = calculateHash(document);
        byte[] digitalSignature = signDocument(documentHash, senderKeyPair.getPrivate());
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

