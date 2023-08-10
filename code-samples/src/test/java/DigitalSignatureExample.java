import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignatureExample {

    @Test
    void testSigningSignature() throws Exception {
        // Load the private key from the PEM file (replace with your actual file path)
        String privateKeyFilePath = "src/test/resources/private_key.pem";
        PrivateKey privateKey = KeyUtils.loadPrivateKeyFromPEM(privateKeyFilePath);

        // Data to be signed
        String data = "This is the data to be signed.";

        // Create a signature instance and initialize with the private key
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        // Update the signature object with the data
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        signature.update(dataBytes);

        // Generate the digital signature
        byte[] digitalSignature = signature.sign();

        // Print the digital signature as Base64
        System.out.println("Digital Signature: " + Base64.getEncoder().encodeToString(digitalSignature));

    }
}
