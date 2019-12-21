package io.qala.crypto.format.asn;

import io.qala.crypto.format.asn.string.PrintableString;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

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
                        new DerSet(Oid.COUNTRY.set(new PrintableString("US"))),
                        new DerSet(Oid.ORGANIZATION.set(new PrintableString("Qala"))),
                        new DerSet(Oid.ORGANIZATION_UNIT.set(new PrintableString("Security Team"))),
                        new DerSet(Oid.ISSUER_COMMON_NAME.set(new PrintableString("Qala")))//self signed
                )

        );
        FileOutputStream out = new FileOutputStream("mycustom.crt");
        out.write(new Sequence(seq).toBouncyCastle().getEncoded());
        out.flush();
        out.close();
    }

}