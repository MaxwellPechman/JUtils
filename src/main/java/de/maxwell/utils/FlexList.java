package de.maxwell.utils;

import java.util.Iterator;

public class FlexList<V> implements Iterable<V> {

    public static final int DEFAULT_CAPACITY = 4;
    public static final int DEFAULT_FACTOR = 2;

    private int size = 0;

    private int expandFactor;
    private Object[] values;
    private ExpandType type;

    enum ExpandType {
        LINEAR,
        FACTORAL;
    }

    public FlexList() {
        this(DEFAULT_CAPACITY, DEFAULT_FACTOR, ExpandType.FACTORAL);
    }

    public FlexList(int capacity) {
        this(capacity, DEFAULT_FACTOR, ExpandType.FACTORAL);
    }

    public FlexList(int capacity, ExpandType type) {
        this(capacity, DEFAULT_CAPACITY, type);
    }

    public FlexList(int capacity, int expandFactor) {
        this(capacity, expandFactor, ExpandType.FACTORAL);
    }

    public FlexList(int capacity, int expandFactor, ExpandType type) {
        this.values = new Object[capacity];
        this.expandFactor = expandFactor;
        this.type = type;
    }

    public int getExpandFactor() {
        return this.expandFactor;
    }

    public void setExpandFactor(int expandFactor) {
        this.expandFactor = expandFactor;
    }

    public ExpandType getExpandType() {
        return this.type;
    }

    public void setExpandType(ExpandType type) {
        this.type = type;
    }

    public int getSize() {
        return this.size;
    }

    public int getTechnicalSize() {
        return this.size - 1;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isEmptyAt(int index) {
        if(index < 0 || index > this.getTechnicalSize() || this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        return this.values[index] == null;
    }

    public void setCapacity(int capacity) {

    }

    public int getCapacity() {
        return this.values.length;
    }

    public int getEmptyCapacity() {
        return this.getCapacity() - this.size;
    }

    public void addCapacity(int capacity) {

    }

    public void removeCapacity(int capacity) {

    }

    @Override
    public Iterator<V> iterator() {
        return null;
    }
}