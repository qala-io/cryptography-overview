package io.qala.crypto.key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class PrivKey {
    private final PrivateKey key;

    public PrivKey(String privateKeyPath) {
        try {
            byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            this.key = kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws IOException {
        String filename = "/Users/stas/projects/elsci/peaksel/private_key.pem";
        String s = new String(Files.readAllBytes(Paths.get(filename)));
//        Base64.getDecoder().decode(src)
        new PrivKey("/Users/stas/projects/elsci/peaksel/private_key.pem");
    }
}
