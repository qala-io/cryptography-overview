package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.der.Sequence;
import io.qala.crypto.format.asn.der.SequenceFromBytes;
import io.qala.crypto.format.asn.der.Set;
import io.qala.crypto.format.asn.der.Time;
import io.qala.crypto.format.asn.string.PrintableString;
import io.qala.crypto.key.KeyAlgorithm;
import io.qala.crypto.key.Keys;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static io.qala.datagen.RandomShortApi.positiveInteger;

public class Certificate509Test {
    @Test public void x509() throws IOException {
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
                new SequenceFromBytes(Keys.generate(KeyAlgorithm.RSA, 2048).getPubKey().toX509())

        );
        FileOutputStream out = new FileOutputStream("mycustom.crt");
        out.write(new Sequence(seq).toBouncyCastle().getEncoded());
        out.flush();
        out.close();
    }

}