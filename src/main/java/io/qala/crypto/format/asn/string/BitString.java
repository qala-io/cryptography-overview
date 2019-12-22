package io.qala.crypto.format.asn.string;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERBitString;

public class BitString implements AsnElement {
    private final int bits;
    public BitString(int bits) {
        this.bits = bits;
    }
    @Override public ASN1Encodable toBouncyCastle() {
        return new DERBitString(bits);
    }
}
