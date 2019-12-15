package io.qala.crypto.format;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Asn {
    private final Der der;

    public Asn(Der der) {
        this.der = der;
    }
    public String toString() {
        ASN1InputStream bIn = new ASN1InputStream(new ByteArrayInputStream(der.toBytes()));
        try {
            ASN1Primitive obj = bIn.readObject();
            return ASN1Dump.dumpAsString(obj, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
