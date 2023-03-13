package de.maxwell.utils.primitives;

public class ByteResult {

    private final Byte value;
    private final Byte[] values;

    public ByteResult(Byte value) {
        this.value = value;
        this.values = null;
    }

    public ByteResult(Byte[] values) {
        this.value = null;
        this.values = values;
    }

    public Byte getByte() {
        if(this.value == null) {
            throw new NumberFormatException("");
        }

        return this.value;
    }

    public Byte[] getBytes() {
        if(this.values == null) {
            throw new NumberFormatException("");
        }

        return this.values;
    }
}