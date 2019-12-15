package io.qala.crypto.format;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Asn {
    private final byte[] ber;

    public Asn(Pem pem) {
        this(pem.toBer());
    }
    public Asn(byte[] ber) {
        this.ber = ber;
    }
    public String toString() {
        ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(ber));
        try {
            ASN1Primitive obj = bIn.readObject();
            return ASN1Dump.dumpAsString(obj, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
