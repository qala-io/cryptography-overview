package io.qala.crypto.format.asn.string;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERPrintableString;

public class PrintableString implements AsnElement {
    public final String value;
    public PrintableString(String value) {
        this.value = value;
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return new DERPrintableString(value);
    }
}
