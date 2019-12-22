package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;

public class Sequence implements AsnElement {
    private final ASN1Sequence der;

    public Sequence(AsnElement ... elements) {
        ASN1EncodableVector v = new ASN1EncodableVector();
        for (AsnElement next : elements)
            v.add(next.toBouncyCastle());
        this.der = new DERSequence(v);
    }
    public Sequence(byte[] derBytes) {
        der = ASN1Sequence.getInstance(derBytes);
    }
    public byte[] getBytesAt(int elementIdx) {
        return ((DERBitString)der.getObjectAt(1).toASN1Primitive()).getBytes();
    }

    @Override public ASN1Sequence toBouncyCastle() {
        return der;
    }
}
