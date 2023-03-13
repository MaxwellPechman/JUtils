package de.maxwell.utils.primitives;

public class CastedNumber {

    private final Object value;

    public CastedNumber(Byte value) {
        this.value = value;
    }

    public CastedNumber(Short value) {
        this.value = value;
    }

    public CastedNumber(Integer value) {
        this.value = value;
    }

    public CastedNumber(Long value) {
        this.value = value;
    }

    public boolean isByte() {
        return this.value instanceof Byte;
    }

    public boolean isShort() {
        return this.value instanceof Short;
    }

    public boolean isInteger() {
        return this.value instanceof Integer;
    }

    public boolean isLong() {
        return this.value instanceof Long;
    }
}