package io.qala.crypto.encrypt;

import io.qala.crypto.Message;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XorTest {
    @Test public void xor2times_givesBackOriginalData() {
        Message cypher = new Message(new byte[] {28, -1});//some random bytes
        Message toEncrypt = new Message("Hi");
        Xor xor = new Xor(cypher.toBytes());

        assertEquals("0100100001101001", toEncrypt.toBinaryString());
        assertEquals("0001110011111111", cypher.toBinaryString());

        Message encrypted = xor.encrypt(toEncrypt);
        assertEquals("0101010010010110", encrypted.toBinaryString());
        Message decrypted = xor.encrypt(encrypted);
        assertEquals("0100100001101001", decrypted.toBinaryString());
        assertEquals("Hi", decrypted.toString());
    }
}