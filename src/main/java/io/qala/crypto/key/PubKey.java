package io.qala.crypto.key;

import io.qala.crypto.Message;
import io.qala.crypto.DigitalSignature;
import io.qala.crypto.IoUtils;
import io.qala.crypto.format.Der;

import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class PubKey {
    private final PublicKey key;
    private final KeyAlgorithm algorithm;

    public PubKey(PublicKey publicKey, KeyAlgorithm algorithm) {
        this.key = publicKey;
        this.algorithm = algorithm;
    }
    public PubKey(Der der, KeyAlgorithm algorithm) {
        this.algorithm = algorithm;
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(der.toBytes());
            KeyFactory kf = KeyFactory.getInstance(algorithm.name());
            this.key = kf.generatePublic(spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
    public PubKey(Path keyPath, KeyAlgorithm algorithm) {
        this(new Der(IoUtils.readFromFile(keyPath)), algorithm);
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
    public byte[] toX509() {//SubjectPublicKeyInfo
        return key.getEncoded();
    }
}
