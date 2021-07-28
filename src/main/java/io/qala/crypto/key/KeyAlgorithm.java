package io.qala.crypto.key;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

import static io.qala.crypto.key.SignatureAlgorithm.SHA256_WITH_RSA_AND_PSS;

public enum KeyAlgorithm {
    RSA(SHA256_WITH_RSA_AND_PSS), DSA(SignatureAlgorithm.SHA256_WITH_DSA);

    public final SignatureAlgorithm signatureAlgorithm;

    KeyAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

}
