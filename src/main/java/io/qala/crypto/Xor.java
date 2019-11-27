package io.qala.crypto;

public class Xor {
    public static void main(String[] args) {
        byte[] cypher = new byte[] {28, -1};//some random bytes
        byte[] toEncrypt = "Hi".getBytes();
        System.out.println(toBinaryString(toEncrypt) + " (original data)");
        System.out.println(toBinaryString(cypher) + " (cypher)");

        byte[] encrypted = xor(toEncrypt, cypher);
        System.out.println(toBinaryString(encrypted) + " (encrypted)");

        byte[] decrypted = xor(encrypted, cypher);
        System.out.println(toBinaryString(decrypted) + " (decrypted)");
        System.out.println(new String(decrypted));
    }

    static byte[] xor(byte[] a, byte[] cypher) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++)
            result[i] = (byte) (a[i] ^ cypher[i%cypher.length]);
        return result;
    }
    static String toBinaryString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte next : bytes)
            result.append(String.format("%8s", Integer.toBinaryString(next & 0xFF)).replace(' ', '0'));
        return result.toString();
    }
}
