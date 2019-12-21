package io.qala.crypto.format.asn.der;

import io.qala.crypto.format.asn.AsnElement;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERUTCTime;

import java.time.Instant;
import java.util.Date;

public class Time implements AsnElement {
    private final Instant time;

    public Time(Instant time) {
        this.time = time;
    }

    @Override public ASN1Encodable toBouncyCastle() {
        return new DERUTCTime(Date.from(time));
    }
}
