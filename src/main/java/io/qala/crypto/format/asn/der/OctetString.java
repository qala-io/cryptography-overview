package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DEROctetString;

public class OctetString implements AsnElement {
    private final byte[] octets;

    public OctetString(byte[] octets) {
        this.octets = octets;
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return new DEROctetString(octets);
    }
}
