package io.qala.crypto.format;

import java.util.Arrays;

public class Pem {
    private final byte[] content;
    private final int bodyStartIdx, bodyEndIdx;

    public Pem(String content) {
        this(content.getBytes());
    }
    public Pem(byte[] content) {
        this.content = content;
        int bodyStart = -1, bodyEnd = -1;
        for (int i = 0; i < content.length; i++)
            if(content[i] == '\n') {
                bodyStart = i + 1;
                break;
            }
        for (int i = content.length-2; i > 0; i--)
            if(content[i] == '\n') {
                bodyEnd = i-1;
                break;
            }
        if(bodyStart == -1 || bodyEnd == -1)
            throw new RuntimeException("Something's wrong with whitespaces");
        bodyStartIdx = bodyStart;
        bodyEndIdx = bodyEnd;
    }
    public byte[] toBer() {
        return Arrays.copyOfRange(content, bodyStartIdx, bodyEndIdx+1);
    }

    public String getBody() {
        return new String(toBer());
    }
}
