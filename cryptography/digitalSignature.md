# Digital Signatures

- 1) Sender creates a key pair (private, public) since digital signatures rely on asymmetric key cryptography

```java
private KeyPair generateKeyPair()throws NoSuchAlgorithmException{
    KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(2048);
    return keyPairGenerator.generateKeyPair();
    }
```

- 2) Document contents are hashed to create a **digest** a.k.a `byte [] documentHash` for example using sha256

```java
    private byte[] calculateHash(String data)throws NoSuchAlgorithmException{
        MessageDigest digest=MessageDigest.getInstance("SHA-256");
        return digest.digest(data.getBytes());
        }
```

- 3) Signature aka((`byte [] signatureByte`)) is created by encrypting the digest(`byte [] documentHash`) with the 
sender's private key.
```java
private byte[] signDocument(byte[] documentHash, PrivateKey privateKey) throws Exception {
    Signature signature = Signature.getInstance("SHA256withRSA");
    signature.initSign(privateKey);
    signature.update(documentHash);
    return signature.sign();
}
```

- 4) Digest is embedded in the document which is then sent







Receiver;

- Recipient decrypts the digest using the sender's public key.
- Recipient calculates the hash from the documents content
- If the recalculated digest matches the decrypted digest, the document has not been tampered with since it was sent

- Digital certificate issued by a certification authority guarantees sender's identity.
- Digital certificate contains a public key along with other information about the sender

- a digest is a fixed-size representation of the original document's content that is generated using a hash function
  like SHA-256.

  
