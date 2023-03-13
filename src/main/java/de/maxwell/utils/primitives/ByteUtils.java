package de.maxwell.utils.primitives;

public final class ByteUtils {

    private static final int MAX = Byte.MAX_VALUE;
    private static final int MIN = Byte.MIN_VALUE;

    private ByteUtils() {
        // Do not use.
    }

    public static Byte safeCast(short value) {
        if(value > MAX) {
            throw new NumberFormatException("Short can not be casted to Byte because the value is bigger than a Byte.");
        }

        if(value < MIN) {
            throw new NumberFormatException("Short can not be casted to Byte because the value is smaller than a Byte.");
        }

        return (byte) value;
    }

    public static boolean isCastable(short value) {
        return value <= MAX && value >= MIN;
    }

    public static ByteResult castShort(short value) {
        ByteResult result = null;

        if(ByteUtils.isCastable(value)) {
            result = new ByteResult((byte) value);

        } else {

        }

        return result;
    }
}
