package io.qala.crypto.key;

import io.qala.crypto.Message;
import io.qala.crypto.IoUtils;
import io.qala.crypto.DigitalSignature;
import io.qala.crypto.format.Der;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class PrivKey {
    private final PrivateKey key;
    private final SignatureAlgorithm algorithm;

    public PrivKey(Der der, SignatureAlgorithm algorithm) {
        this.algorithm = algorithm;
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(der.toBytes());
            KeyFactory kf = KeyFactory.getInstance(algorithm.name());
            this.key = kf.generatePrivate(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public PrivKey(String privateKeyPath, SignatureAlgorithm algorithm) {
        this(new Der(IoUtils.readFromFile(privateKeyPath)), algorithm);
    }

    public DigitalSignature sign(Message data) {
        Signature dsa;
        try {
            dsa = Signature.getInstance("SHA256with" + algorithm.name() /*e.g. SHA1withDSA*/);
            dsa.initSign(key);
            dsa.update(data.toBytes());
            return new DigitalSignature(dsa.sign());
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}