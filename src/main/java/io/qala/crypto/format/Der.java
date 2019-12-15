package io.qala.crypto.format;

import io.qala.crypto.IoUtils;

import java.io.InputStream;

public class Der {
    private final byte[] der;

    public Der(byte[] der) {
        this.der = der;
    }
    public Der(InputStream input) {
        this(IoUtils.readFully(input));
    }

    public byte[] toBytes() {
        return der;
    }
}
