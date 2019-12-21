package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.der.Sequence;
import io.qala.crypto.format.asn.der.Set;
import io.qala.crypto.format.asn.der.Time;
import io.qala.crypto.format.asn.string.PrintableString;
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
                /*Name ::= CHOICE { rdnSequence  RDNSequence }
                RDNSequence ::= SEQUENCE OF RelativeDistinguishedName
                RelativeDistinguishedName ::=
                     SET SIZE (1..MAX) OF AttributeTypeAndValue*/

                /*AttributeTypeAndValue ::= SEQUENCE {
                    type     AttributeType,
                    value    AttributeValue }*/
                new Sequence(// issuer name
                        new Set(Oid.COUNTRY.set(new PrintableString("US"))),
                        new Set(Oid.ORGANIZATION.set(new PrintableString("Qala"))),
                        new Set(Oid.ORGANIZATION_UNIT.set(new PrintableString("Security Team"))),
                        new Set(Oid.ISSUER_COMMON_NAME.set(new PrintableString("Qala")))/*self signed*/),
                /*Validity ::= SEQUENCE {
                    notBefore      Time,
                    notAfter       Time } */
                new Sequence(
                        new Time(Instant.now().minus(365, ChronoUnit.DAYS)),
                        new Time(Instant.now().plus(365, ChronoUnit.DAYS)))

        );
        FileOutputStream out = new FileOutputStream("mycustom.crt");
        out.write(new Sequence(seq).toBouncyCastle().getEncoded());
        out.flush();
        out.close();
    }

}