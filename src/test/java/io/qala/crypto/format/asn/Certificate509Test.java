package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.der.OctetString;
import io.qala.crypto.format.asn.der.Sequence;
import io.qala.crypto.format.asn.der.Set;
import io.qala.crypto.format.asn.der.Time;
import io.qala.crypto.format.asn.string.BitString;
import io.qala.crypto.format.asn.string.PrintableString;
import io.qala.crypto.key.KeyAlgorithm;
import io.qala.crypto.key.Keys;
import io.qala.crypto.key.PubKey;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.qala.datagen.RandomShortApi.positiveInteger;

public class Certificate509Test {
    @Test public void x509() throws IOException {
        PubKey pubKey = Keys.generate(KeyAlgorithm.RSA, 2048).getPubKey();
        Sequence subjectPublicKey = new Sequence(pubKey.toX509());
        byte[] pubKeyBits = subjectPublicKey.getBytesAt(1);

        Digest sha1 = new SHA1Digest();
        byte[] pubKeyDigest = new byte[sha1.getDigestSize()];
        sha1.update(pubKeyBits, 0, pubKeyBits.length);
        sha1.doFinal(pubKeyDigest, 0);

        Sequence seq = new Sequence(
                new TaggedObject(true, 0/*version*/, 2/*v3*/),
                new AsnInt(positiveInteger()),//serial number
                Oid.SHA256_WITH_RSA_ENCRYPTION.setNull(),
                new Sequence(// issuer name (RDNSequence)
                        new Set(Oid.COUNTRY.set(new PrintableString("US"))),
                        new Set(Oid.ORGANIZATION.set(new PrintableString("Qala"))),
                        new Set(Oid.ORGANIZATION_UNIT.set(new PrintableString("Security Team"))),
                        new Set(Oid.COMMON_NAME.set(new PrintableString("Qala")))),
                new Sequence(//Validity ::= SEQUENCE { notBefore Time, notAfter Time }
                        new Time(Instant.now().minus(365, ChronoUnit.DAYS)),
                        new Time(Instant.now().plus(365, ChronoUnit.DAYS))),
                new Sequence(// subject name, since it's self-signed - it's the same as issuer name
                        new Set(Oid.COUNTRY.set(new PrintableString("US"))),
                        new Set(Oid.ORGANIZATION.set(new PrintableString("Qala"))),
                        new Set(Oid.ORGANIZATION_UNIT.set(new PrintableString("Security Team"))),
                        new Set(Oid.COMMON_NAME.set(new PrintableString("Qala")))),
                subjectPublicKey,
                Oid.SUBJECT_KEY_IDENTIFIER.set(new OctetString(pubKeyDigest)),
                Oid.AUTHORITY_KEY_IDENTIFIER.set(new OctetString(new Sequence(new TaggedObject(false, 0, pubKeyDigest)))),
                Oid.KEY_USAGE.set(new BitString(new KeyUsage().signature().keyCertSign().cRlSign().getMask())),
                Oid.EXTENDED_KEY_USAGE.set(new OctetString(new Sequence(Oid.SERVER_AUTH_EKU, Oid.EMAIL_PROTECTION_EKU)))
        );
        FileOutputStream out = new FileOutputStream("mycustom.crt");
        out.write(new Sequence(seq).toBouncyCastle().getEncoded());
        out.flush();
        out.close();
    }

}