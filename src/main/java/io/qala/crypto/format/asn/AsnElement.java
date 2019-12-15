package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERNull;

public interface AsnElement {
    ASN1Encodable toBouncyCastle();
    AsnElement NULL = () -> DERNull.INSTANCE;
}
