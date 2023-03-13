package de.maxwell.utils.primitives;

public class CastedByte {

    private final Object value;

    public CastedByte(Short value) {
        this.value = value;
    }

    public CastedByte(Integer value) {
        this.value = value;
    }

    public CastedByte(Double value) {
        this.value = value;
    }
}
