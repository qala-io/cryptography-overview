package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DEROctetString;

import java.io.IOException;

public class OctetString implements AsnElement {
    private final ASN1Encodable asn;

    public OctetString(byte[] octets) {
        this.asn = new DEROctetString(octets);
    }
    public OctetString(AsnElement e) {
        try {
            this.asn = new DEROctetString(e.toBouncyCastle());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return asn;
    }
}
