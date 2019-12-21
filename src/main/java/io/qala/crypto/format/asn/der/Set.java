package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSet;

public class Set implements AsnElement {
    private final DERSet set;

    public Set(AsnElement e) {
        this.set = new DERSet(e.toBouncyCastle());
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return set;
    }
}
