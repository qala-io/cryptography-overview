package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;

public class AsnEntry implements AsnElement {
    private final Sequence sequence;

    public AsnEntry(String oid) {
        this.sequence = new Sequence(new Oid(oid), AsnElement.NULL);
    }
    public AsnEntry(String oid, int v) {
        this.sequence = new Sequence(new Oid(oid), new AsnInt(v));
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return sequence.toBouncyCastle();
    }
}
