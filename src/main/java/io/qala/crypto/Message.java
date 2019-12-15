package io.qala.crypto;

public class Message {
    private final byte[] raw;
    public Message(String raw) {
        this(raw.getBytes());
    }
    public Message(byte[] raw) {
        this.raw = raw;
    }
    public byte[] toBytes() {
        return raw;
    }

    public String toBinaryString() {
        StringBuilder result = new StringBuilder();
        for (byte next : raw)
            result.append(String.format("%8s", Integer.toBinaryString(next & 0xFF)).replace(' ', '0'));
        return result.toString();
    }
    public String toString() {
        return new String(raw);
    }
}
