package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;

import java.util.Arrays;

public class Sequence implements AsnElement {
    private final Iterable<AsnElement> elements;

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
