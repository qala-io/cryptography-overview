package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;

import java.util.Arrays;
import java.util.List;

public class Sequence implements AsnElement {
    private final List<AsnElement> elements;

    public Sequence(AsnElement ... elements) {
        this.elements = Arrays.asList(elements);
    }

    @Override public DERSequence toBouncyCastle() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        for (AsnElement next : elements)
            v.add(next.toBouncyCastle());
        return new DERSequence(v);
    }
}
