package io.qala.crypto.format.asn;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

public class Oid implements AsnElement {
    public static final Oid
            SHA256_WITH_RSA_ENCRYPTION = new Oid("1.2.840.113549.1.1.5"),
            COUNTRY = new Oid("2.5.4.6"),
            ORGANIZATION = new Oid("2.5.4.10"),
            ORGANIZATION_UNIT = new Oid("2.5.4.11"),
            ISSUER_COMMON_NAME = new Oid("2.5.4.3")
            ;

    private final String oid;

    public Oid(String oid) {
        this.oid = oid;
    }

    public AsnEntry setNull() {
        return new AsnEntry(oid);
    }
    public AsnEntry set(AsnElement e) {
        return new AsnEntry(oid, e);
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return new ASN1ObjectIdentifier(oid);
    }
}