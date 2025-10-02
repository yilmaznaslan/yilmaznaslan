# Crypto systems

An interceptor (an attacker) is an unauthorized entity who attempts to determine the plaintext. He can see the
**ciphertext** and may know the decryption algorithm. He, however, must never know the **decryption key**.

## Types of Cryptosystems

Fundamentally, there are two types of cryptosystems based on the manner in which **encryption-decryption** is carried out in
the system −

- **Symmetric Key Encryption**(*Symmetric Cryptosystems*)
- **Asymmetric Key Encryption**(*Asymmetric Cryptosystems/Public Key Encryption*)

### Symmetric Key Encryption

The encryption process where **same** keys are used for **encrypting** and **decrypting** the information is known as
Symmetric Key Encryption.

The study of symmetric cryptosystems is referred to as symmetric cryptography. Symmetric cryptosystems are also
sometimes referred to as secret key cryptosystems.

### Asymmetric Key Encryption / Public Key Encryption

The encryption process where **different keys** are used for **encrypting** and **decrypting** the information is known
as
**Asymmetric Key Encryption**. Though the keys are different, they are mathematically related and hence, retrieving the
plaintext by decrypting ciphertext is feasible. The process is depicted in the following illustration

![asymmetric_key_encryption](https://www.tutorialspoint.com/cryptography/images/asymmetric_key_encryption.jpg)

#### Features of the Asymmetric Key Encryption

- Every user in this system needs to have a pair of dissimilar keys, **private key** and **public key.** These keys are
  mathematically related − when **public key is used for encryption**, the **private key** can decrypt the ciphertext
  back to the original plaintext.

- It requires to put the **public key in public repository** and the private key as a well-guarded secret. Hence, this
  scheme of encryption is also called **Public Key Encryption.**

- When **Sender** needs to send data to *Receiver**, **Sender** obtains the public key of **Receiver** from repository, encrypts the data, and
  transmits. **Receiver** uses his private key to extract the plaintext.

- Length of Keys (number of bits) in this encryption is large and hence, the process of encryption-decryption is slower
  than symmetric key encryption.

- Processing power of computer system required to run asymmetric algorithm is higher.

- The concept of public-key cryptography is relatively new. There are fewer public-key algorithms known than symmetric
  algorithms.

#### Challenges of the Asymmetric Key Encryption

Public-key cryptosystems have one significant challenge − the user needs to trust that the public key that he is using
in communications with a person really is the public key of that person and has not been spoofed by a malicious third
party.

This is usually accomplished through a **Public Key Infrastructure (PKI)** consisting a trusted third party. The third
party
securely manages and attests to the authenticity of public keys. When the third party is requested to provide the public
key for any communicating person X, they are trusted to provide the correct public key.

The third party satisfies itself about user identity by the process of attestation, notarization, or some other process
− that X is the one and only, or globally unique, X. **The most common method of making the verified public keys
available
is to embed them in a certificate which is digitally signed by the trusted third party.**

#### Kerckhoff’s Principle for Cryptosystem

In the 19th century, a Dutch cryptographer A. Kerckhoff furnished the requirements of a good cryptosystem. Kerckhoff
stated that a cryptographic system should be secure even if everything about the system, except the key, is public
knowledge. Currently known  **Kerckhoff principle**;

- Falling of the cryptosystem in the hands of an intruder should not lead to any compromise of the system, preventing
  any inconvenience to the user.

**Kerckhoff principle** is applied in virtually all the contemporary encryption algorithms such as **DES, AES**, etc.
These public algorithms are considered to be thoroughly secure. **The
security of the encrypted message depends solely on the security of the secret encryption key.**

In modern era using a secret
algorithm is not feasible, hence Kerckhoff principles became essential guidelines for designing algorithms in modern
cryptography.
