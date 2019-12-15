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
}
