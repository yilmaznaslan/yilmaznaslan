import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {

    public static  PrivateKey loadPrivateKeyFromPEM(String filePath) throws Exception {
        StringBuilder privateKeyPEM = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("BEGIN PRIVATE KEY") || line.contains("END PRIVATE KEY")) {
                continue;
            }
            privateKeyPEM.append(line);
        }
        br.close();

        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPEM.toString());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKeyFromPEM(String filePath) throws Exception {
        StringBuilder publicKeyPEM = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("BEGIN PUBLIC KEY") || line.contains("END PUBLIC KEY")) {
                continue;
            }
            publicKeyPEM.append(line);
        }
        br.close();

        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPEM.toString());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
