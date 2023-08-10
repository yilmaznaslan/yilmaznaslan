# Digital Signatures

- Digital signatures rely on asymmetric key cryptography
- Document contents are hashed to create a **digest** for example using sha256
- Digest is encrypted by the sender with their private key
- Digest is embedded in the document which is then sent

Receiver;

- Recipient decrypts the digest using the sender's public key.
- Recipient calculates the hash from the documents content
- If the recalculated digest matches the decrypted digest, the document has not been tampered with since it was sent 

- Digital  certificate issued by a certification authority guarantees sender's identity.
- Digital certificate contains a public key along with  other information about the sender

- a digest is a fixed-size representation of the original document's content that is generated using a hash function 
like SHA-256.


