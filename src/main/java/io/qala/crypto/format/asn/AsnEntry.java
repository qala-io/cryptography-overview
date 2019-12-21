package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.der.Sequence;
import org.bouncycastle.asn1.ASN1Encodable;

public class AsnEntry implements AsnElement {
    private final Sequence sequence;

    public AsnEntry(String oid) {
        this.sequence = new Sequence(new Oid(oid), AsnElement.NULL);
    }
    public AsnEntry(String oid, int v) {
        this(oid, new AsnInt(v));
    }
    public AsnEntry(String oid, AsnElement e) {
        this.sequence = new Sequence(new Oid(oid), e);
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return sequence.toBouncyCastle();
    }
}
