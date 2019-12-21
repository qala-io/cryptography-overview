package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Sequence;

public class SequenceFromBytes implements AsnElement {
    private final byte[] derBytes;

    public SequenceFromBytes(byte[] derBytes) {
        this.derBytes = derBytes;
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return ASN1Sequence.getInstance(derBytes);
    }
}
