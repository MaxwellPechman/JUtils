package de.maxwell.utils;

import com.sun.jdi.InvalidTypeException;

import java.util.Arrays;
import java.util.Iterator;

public class FlexList<V> implements Iterable<V> {

    public static final int DEFAULT_CAPACITY = 4;
    public static final int EXPAND_FACTOR = 2;

    private int size = 0;
    private Object[] values;

    public FlexList() {
        this(DEFAULT_CAPACITY);
    }

    public FlexList(int capacity) {
        this.values = new Object[capacity];
    }

    private Object[] getCopy(int capacity) {
        Object[] valuesCopy = new Object[capacity];

        for(int index = 0; index < this.size; size++) {
            valuesCopy[index] = this.values[index];
        }

        return valuesCopy;
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
        if(capacity < this.getTechnicalSize()) {
           throw new IndexOutOfBoundsException("");
        }

        this.values = this.getCopy(capacity);
    }

    public int getCapacity() {
        return this.values.length;
    }

    public int getAvailableCapacity() {
        return this.getCapacity() - this.size;
    }

    public void addCapacity(int capacity) {
        if(capacity < 0) {
            throw new IndexOutOfBoundsException("");
        }

        int newCap = this.getCapacity() + capacity;
        this.values = this.getCopy(newCap);
    }

    public void removeCapacity(int capacity) {
        if(capacity < 0 || capacity > this.getAvailableCapacity()) {
            throw new IndexOutOfBoundsException("");
        }

        int newCap = this.getCapacity() - capacity;
        this.values = this.getCopy(newCap);
    }

    public boolean hasValue(V value) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        for(int index = 0; index < this.size; size++) {
            if(this.values[index] == value) {
                return true;
            }
        }

        return false;
    }

    public boolean hasValues(V[] values) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        return false;
    }

    public void setValue(int index, V value) {

    }

    public void setValues(int index, V[] values) {

    }

    public V getValue(int index) {
        if(index < 0 || index > this.getTechnicalSize() || this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        return (V) this.values[index];
    }

    public V[] getValues(int fromIndex, int tillIndex) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        if(fromIndex < 0 || fromIndex > tillIndex || fromIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        // Checking "tillIndex < fromIndex" is probably not required.
        if(tillIndex < 0 || tillIndex < fromIndex || tillIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        int capacity = tillIndex - fromIndex;
        Object[] selectedValues = new Object[capacity];

        for(int index = fromIndex; index < tillIndex; index++) {
            int arrayIndex = index - fromIndex;
            selectedValues[arrayIndex] = this.values[index];
        }

        return (V[]) selectedValues;
    }

    public void addValue(V value) {
        if(value == null) {
            throw new IllegalStateException("");
        }

        if(this.getAvailableCapacity() == 0) {
            this.setCapacity(this.getCapacity() * EXPAND_FACTOR);
        }

        this.values[this.size] = value;
        this.size++;
    }

    public void addValues(V[] values) {
        if(values == null) {
            throw new IllegalStateException("");
        }

        while(this.getAvailableCapacity() < values.length) {
            this.setCapacity(this.getCapacity() * EXPAND_FACTOR);
        }

        for(int index = 0; index < values.length; index++) {
            this.values[index + this.size] = values[index];
            this.size++;
        }
    }

    public V removeValue(int index) {
        if(index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        Object[] valuesCopy = new Object[this.getCapacity()];
        Object obj = this.values[index];

        for(int subdex = 0; subdex < this.size; subdex++) {
            if(subdex >= index) {
                valuesCopy[subdex] = this.values[subdex + 1];

            } else {
                valuesCopy[subdex] = this.values[subdex];
            }
        }

        this.values = valuesCopy;
        this.size--;

        return (V) obj;
    }

    public V[] removeValues(int from, int till) {
        return null;
    }

    public void insertValue(int index, V value) {

    }

    public void insertValues(int index, V[] values) {

    }

    public void shiftValues(int fromIndex, int tillIndex, int toIndex) {

    }

    public void reset() {
        this.values = new Object[DEFAULT_CAPACITY];
    }

    public void undo() {

    }

    public void restore(int index) {

    }

    public void haltCollection() {
        this.setCapacity(this.getTechnicalSize());
    }

    public V getFirst() {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        return (V) this.values[0];
    }

    public V getLast() {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        return (V) this.values[this.getTechnicalSize()];
    }

    public V[] getAsArray() {
        return (V[]) this.values;
    }

    public void injectBubbleSort() {

    }

    public void injectQuickSort() {

    }

    public void injectMergeSort() {

    }

    public void injectHeapSort() {

    }

    public void injectRadixSort() {

    }

    public void injectShellSort() {

    }

    @Override
    public Iterator<V> iterator() {
        return (Iterator<V>) Arrays.stream(this.values).iterator();
    }

    @Override
    public int hashCode() {
        return this.values.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[");

        for(int index = 0; index < this.size; index++) {
            string.append(this.values[index].toString());

            if(index < this.getTechnicalSize()) {
                string.append(",");
            }
        }

        return string.toString() + "]";
    }
}