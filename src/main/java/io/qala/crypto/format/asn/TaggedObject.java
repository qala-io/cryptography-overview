package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERTaggedObject;

public class TaggedObject implements AsnElement {
    private final DERTaggedObject bc;

    public TaggedObject(boolean explicit, int tag, int value) {
        bc = new DERTaggedObject(explicit, tag, new ASN1Integer(value));
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return bc;
    }
}
