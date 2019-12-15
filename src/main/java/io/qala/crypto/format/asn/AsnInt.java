package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;

public class AsnInt implements AsnElement{
    private final ASN1Integer bc;

    public AsnInt(int i) {
        this.bc = new ASN1Integer(i);
    }
    @Override public ASN1Encodable toBouncyCastle() {
        return bc;
    }
}
