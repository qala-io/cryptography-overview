package io.qala.crypto.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Keys {
    private final PrivKey privKey;
    private final PubKey pubKey;

    public Keys(PrivKey privKey, PubKey pubKey) {
        this.privKey = privKey;
        this.pubKey = pubKey;
    }

    public static Keys generate(KeyAlgorithm algorithm, int keyBitLength) {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            KeyPairGenerator kpGen = KeyPairGenerator.getInstance(algorithm.name());
            kpGen.initialize(keyBitLength, random);
            KeyPair keyPair = kpGen.generateKeyPair();
            return new Keys(
                    new PrivKey(keyPair.getPrivate(), algorithm),
                    new PubKey(keyPair.getPublic(), algorithm));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public PrivKey getPrivKey() {
        return privKey;
    }
    public PubKey getPubKey() {
        return pubKey;
    }
}
