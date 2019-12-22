package io.qala.crypto.format.asn;

public class KeyUsage {
    private int mask;

    public int getMask() {
        return mask;
    }

    public KeyUsage signature() {
        mask |=  1 << 7;
        return this;
    }
    public KeyUsage nonRepudiation() {
        mask |=  1 << 6;
        return this;
    }
    public KeyUsage keyEncipherment() {
        mask |=  1 << 5;
        return this;
    }
    public KeyUsage dataEncipherment() {
        mask |=  1 << 4;
        return this;
    }
    public KeyUsage keyAgreement() {
        mask |=  1 << 3;
        return this;
    }
    public KeyUsage keyCertSign() {
        mask |=  1 << 2;
        return this;
    }
    public KeyUsage cRlSign() {
        mask |=  1 << 1;
        return this;
    }
    public KeyUsage encipherOnly() {
        mask |=  1;
        return this;
    }
    public KeyUsage decipherOnly() {
        mask |=  1 << 15;
        return this;
    }
}
