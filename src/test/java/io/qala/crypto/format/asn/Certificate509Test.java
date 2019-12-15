package io.qala.crypto.format.asn;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import static io.qala.datagen.RandomShortApi.positiveInteger;

public class Certificate509Test {
    @Test public void x509() throws IOException {
        Sequence seq = new Sequence(
                new TaggedObject(true, 0/*version*/, 2/*v3*/),
                new AsnInt(positiveInteger()),//serial number
                Oid.SHA256_WITH_RSA_ENCRYPTION.setNull()
        );
        FileOutputStream out = new FileOutputStream("mycustom.crt");
        out.write(seq.toBouncyCastle().getEncoded());
        out.flush();
        out.close();
    }

}