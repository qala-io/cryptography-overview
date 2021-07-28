package io.qala.crypto.key;

import io.qala.crypto.DigitalSignature;
import io.qala.crypto.Message;

import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;

public enum SignatureAlgorithm {
    /**
     * PSS (Probabilistic signature scheme) is a padding algorithm. Vanilla SHA256withRSA won't pad which doesn't
     * seem to be secured against chosen text.
     */
    SHA256_WITH_RSA_AND_PSS("SHA256withRSA/PSS", new PSSParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, 32, 1)),
    SHA256_WITH_DSA("SHA256withDSA", null);

    private final String name;
    private final PSSParameterSpec parameterSpec;

    SignatureAlgorithm(String name, PSSParameterSpec parameterSpec) {
        this.name = name;
        this.parameterSpec = parameterSpec;
    }

    public DigitalSignature sign(PrivateKey key, Message data) {
        Signature s = createSignatureAlgorithm();
        try {
            s.initSign(key);
            s.update(data.toBytes());
            return new DigitalSignature(s.sign());
        } catch (SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean verify(PublicKey key, Message data, DigitalSignature signature) {
        Signature s = createSignatureAlgorithm();
        try {
            s.initVerify(key);
            s.update(data.toBytes());
            return s.verify(signature.toBytes());
        } catch (SignatureException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private Signature createSignatureAlgorithm() {
        try {
            Signature s = Signature.getInstance(name);
            s.setParameter(parameterSpec);
            return s;
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
