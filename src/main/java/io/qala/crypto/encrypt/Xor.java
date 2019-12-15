package io.qala.crypto.encrypt;

import io.qala.crypto.Message;

public class Xor {
    private final byte[] cypher;

    public Xor(byte[] cypher) {
        this.cypher = cypher;
    }

    public Message encrypt(Message original) {
        byte[] a = original.toBytes();
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = (byte) (a[i] ^ cypher[i%cypher.length]);
        return new Message(result);
    }
}
