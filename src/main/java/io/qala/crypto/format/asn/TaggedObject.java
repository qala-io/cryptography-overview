package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.der.OctetString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERTaggedObject;

public class TaggedObject implements AsnElement {
    private final DERTaggedObject bc;

    public TaggedObject(boolean explicit, int tag, AsnElement e) {
        bc = new DERTaggedObject(explicit, tag, e.toBouncyCastle());
    }
    public TaggedObject(boolean explicit, int tag, byte[] octetString) {
        this(explicit, tag, new OctetString(octetString));
    }
    public TaggedObject(boolean explicit, int tag, int value) {
        this(explicit, tag, new AsnInt(value));
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return bc;
    }
}
