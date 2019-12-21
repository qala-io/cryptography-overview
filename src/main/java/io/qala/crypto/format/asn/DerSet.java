package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERSet;

public class DerSet implements AsnElement {
    private final DERSet set;

    public DerSet(AsnElement e) {
        this.set = new DERSet(e.toBouncyCastle());
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return set;
    }
}
