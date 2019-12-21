package io.qala.crypto.key;

import io.qala.crypto.Message;
import io.qala.crypto.DigitalSignature;
import io.qala.crypto.format.Der;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrivKeyTest {
    @Test public void canSignDataWithRsa() {
        Message data = new Message("Hello!");

        PrivKey privKey = new PrivKey(der("/rsa/private_key.pkcs8.der"), KeyAlgorithm.RSA);
        DigitalSignature signature = privKey.sign(data);
        assertEquals(
                "LvqP6jpHQkngRMpDlRva5RpVBp/p0JOridDykyeHFPYHZRXFD8Lp8efFWPlVlPRJJnTXTg3iXngm5Ri+JyCIay17AE104WbJYhIB9s+7zIiYLAWiOh7Ok9B1tNAER1CBzq9GD/4Pwf4Igrn3GQZ2uK0UpdqqNJTZxxEEdTj5OQxOGoh8OxVVjBUR1fyI42tRbpuCeCJNVRJgbkVF/Biz9RkrNbEeEWqufZ30fzcnJJ1nRmC2rw/u/Df/vfKDiWQ5/V/LgtURn53Y3ErKAa9rhfK/1Vheq5303zK4ri8nygUKeAUwA7rEke2PJDL6Fa/RguFMOCFMPG985wxU7j9Jgg==",
                signature.toBase64());

        PubKey pubKey = new PubKey(der("/rsa/public_key.x509.der"), KeyAlgorithm.RSA);
        assertTrue(pubKey.verifySignature(data, signature));
    }
    @Test public void canSignDataWithDsa() {
        Message data = new Message("Hello!");
        PrivKey privKey = new PrivKey(der("/dsa/private_key.pkcs8.der"), KeyAlgorithm.DSA);
        DigitalSignature signature = privKey.sign(data);

        // DSA signing depends on a random number so the signature is different each time and this can't be hardcoded
        PubKey pubKey = new PubKey(der("/dsa/public_key.x509.der"), KeyAlgorithm.DSA);
        assertTrue(pubKey.verifySignature(data, signature));
    }

    private static Der der(String name) {
        return new Der(PrivKeyTest.class.getResourceAsStream(name));
    }
}