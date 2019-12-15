package io.qala.crypto.key;

import io.qala.crypto.Message;
import io.qala.crypto.DigitalSignature;
import io.qala.crypto.IoUtils;
import io.qala.crypto.format.Der;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class PubKey {
    private final PublicKey key;
    private final SignatureAlgorithm algorithm;

    public PubKey(Der der, SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(der.toBytes());
            KeyFactory kf = KeyFactory.getInstance(algorithm.name());
            this.key = kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public PubKey(String privateKeyPath, SignatureAlgorithm algorithm) {
        this(new Der(IoUtils.readFromFile(privateKeyPath)), algorithm);
    }

    public boolean verifySignature(Message data, DigitalSignature signature) {
        try {
            Signature s = Signature.getInstance("SHA256with" + algorithm.name() /*e.g. SHA1withDSA*/);
            s.initVerify(key);
            s.update(data.toBytes());
            return s.verify(signature.toBytes());
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
