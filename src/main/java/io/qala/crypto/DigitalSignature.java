package io.qala.crypto;

import java.util.Base64;

public class DigitalSignature {
    private final byte[] signature;
    public DigitalSignature(byte[] signature) {
        this.signature = signature;
    }

    public String toBase64() {
        return Base64.getEncoder().encodeToString(signature);
    }
    public byte[] toBytes() {
        return signature;
    }
}
