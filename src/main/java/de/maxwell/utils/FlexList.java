package de.maxwell.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FlexList<V> implements Iterable<V> {

    public static final int DEFAULT_CAPACITY = 4;
    public static final int EXPAND_FACTOR = 2;

    private int size = 0;
    private Object[] values;
    private ArrayList<ListEvent> events;

    private enum EventType {
        ADD_CAPACITY,
        REMOVE_CAPACITY,
        SET_CAPACITY,
        ADD_VALUE,
        ADD_VALUES,
        REMOVE_VALUE,
        REMOVE_VALUES,
        SET_VALUE,
        SET_VALUES,
        INSERT_VALUE,
        INSERT_VALUES,
        SHIFT_VALUE,
        SHIFT_VALUES,
        UNDO,
        UNDO_ALL,
    }

    private class ListEvent {

        private final EventType type;
        private final int index;
        private final Object[] objects;

        public ListEvent(EventType type, Object[] objects) {
            this(type, 0, objects);
        }

        public ListEvent(EventType type, int index, Object[] objects) {
            this.type = type;
            this.index = index;
            this.objects = objects;
        }

        public EventType getType() {
            return this.type;
        }

        public int getIndex() {
            if(this.index == 0) {
                throw new IllegalStateException("");
            }

            return this.index;
        }

        public Object[] getObjects() {
            return this.objects;
        }
    }

    public FlexList() {
        this(DEFAULT_CAPACITY);
    }

    public FlexList(int capacity) {
        this.values = new Object[capacity];
        this.events = new ArrayList<>();
    }

    private Object[] getCopy(int capacity) {
        Object[] valuesCopy = new Object[capacity];
        for(int index = 0; index < this.size; index++) {
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
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
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
        this.events.add(new ListEvent(EventType.ADD_CAPACITY, this.values));
    }

    public void removeCapacity(int capacity) {
        if(capacity < 0 || capacity > this.getAvailableCapacity()) {
            throw new IndexOutOfBoundsException("");
        }

        int newCap = this.getCapacity() - capacity;
        this.values = this.getCopy(newCap);
        this.events.remove(new ListEvent(EventType.REMOVE_CAPACITY, this.values));
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
        int length = values.length;
        if(this.isEmpty() || length > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        int count = 0;
        for(int index = 0; index < this.size; index++) {
            for(int subdex = 0; subdex < length; subdex++) {
                if(this.values[index] == values[subdex]) {
                    count++;
                }
            }

            if(count == length) {
                return true;
            }
        }

        return false;
    }

    public void setValue(int index, V value) {
        if(value == null) {
            throw new IllegalStateException("");
        }

        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        this.values[index] = value;
        this.events.add(new ListEvent(EventType.SET_VALUE, this.values));
    }

    public void setValues(int index, V[] values) {
        if(values == null) {
            throw new IllegalStateException("");
        }

        int length = values.length;
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize() || length > this.getTechnicalSize() || length + index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        int shiftedIndex = index + length;
        for(int subdex = index; subdex < shiftedIndex; subdex++) {
            this.values[subdex] = values[subdex - index];
        }

        this.events.add(new ListEvent(EventType.SET_VALUES, this.values));
    }

    public V getValue(int index) {
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        return (V) this.values[index];
    }

    public int getIndexOf(V value) {
        if(!this.hasValue(value)) {
            throw new IllegalStateException("");
        }

        for(int index = 0; index < this.size; index++) {
            if(value == this.getValue(index)) {
                return index;
            }
        }

        throw new IllegalStateException("");
    }

    public V[] getValues(int fromIndex, int tillIndex) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        if(fromIndex < 0 || fromIndex > tillIndex || fromIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        if(tillIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        Object[] selectedValues = new Object[tillIndex - fromIndex];
        for(int index = fromIndex; index < tillIndex; index++) {
            selectedValues[index - fromIndex] = this.values[index];
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
        this.events.add(new ListEvent(EventType.ADD_VALUE, this.values));
    }

    public void addValues(V[] values) {
        if(values == null) {
            throw new IllegalStateException("");
        }

        int length = values.length;
        while(this.getAvailableCapacity() < length) {
            this.setCapacity(this.getCapacity() * EXPAND_FACTOR);
        }

        for(int index = 0; index < length; index++) {
            this.values[index + this.size] = values[index];
            this.size++;
        }

        this.events.add(new ListEvent(EventType.ADD_VALUES, this.values));
    }

    public V removeValue(int index) {
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        Object object = this.values[index];
        for(int subdex = index; subdex < this.size + 1; subdex ++) {
            this.values[subdex] = this.values[subdex + 1];
        }

        this.size--;
        this.events.add(new ListEvent(EventType.REMOVE_VALUE, this.values));

        return (V) object;
    }

    public V[] removeValues(int fromIndex, int tillIndex) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        if(fromIndex < 0 || fromIndex >= tillIndex || fromIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        if(tillIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        int indexGap = tillIndex - fromIndex + 1;
        Object[] removedValues = new Object[indexGap];
        for(int index = fromIndex; index < this.size + 1; index++) {
            if(index > tillIndex) {
                this.values[index - indexGap] = this.values[index];

            } else {
                removedValues[index - fromIndex] = this.values[index];
            }
        }

        this.size = this.size - indexGap;
        this.events.add(new ListEvent(EventType.REMOVE_VALUES, this.values));

        return (V[]) removedValues;
    }

    public void insertValue(int index, V value) {
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        if(this.getAvailableCapacity() == 0) {
            this.setCapacity(this.getCapacity() * EXPAND_FACTOR);
        }

        for(int topdex = this.size; topdex >= index; topdex--) {
            if(topdex == index) {
                this.values[topdex] = value;

            } else {
                this.values[topdex] = this.values[topdex - 1];
            }
        }

        this.size++;
        this.events.add(new ListEvent(EventType.INSERT_VALUE, this.values));
    }

    public void insertValues(int index, V[] values) {
        if(this.isEmpty() || index < 0 || index > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        int length = values.length;
        while(this.getAvailableCapacity() < length) {
            this.setCapacity(this.getCapacity() * EXPAND_FACTOR);
        }

        int shiftedIndex = index + length - 1;
        for(int topdex = this.size; topdex >= index; topdex--) {
            if(topdex <= shiftedIndex) {
                this.values[topdex] = values[shiftedIndex - topdex];

            } else {
                this.values[topdex] = this.values[topdex - 1];
            }
        }

        this.events.add(new ListEvent(EventType.INSERT_VALUES, this.values));
    }

    public void shiftValue(int fromIndex, int toIndex) {
        if(this.isEmpty()) {
            throw new IndexOutOfBoundsException("");
        }

        if(fromIndex < 0 || fromIndex > this.getTechnicalSize()) {
            throw  new IndexOutOfBoundsException("");
        }

        if(toIndex < 0 || toIndex > this.getTechnicalSize()) {
            throw new IndexOutOfBoundsException("");
        }

        Object object = this.values[fromIndex];

        if(fromIndex < toIndex) {
            for(int index = fromIndex; index < toIndex; index++) {
                this.values[index] = this.values[index + 1];
            }

        } else {
            for(int index = fromIndex; index > toIndex; index--) {
                this.values[index] = this.values[index - 1];
            }
        }

        this.values[toIndex] = object;
        this.events.add(new ListEvent(EventType.SHIFT_VALUE, this.values));
    }

    public void shiftValues(int fromIndex, int tillIndex, int toIndex) {

    }

    public void resetList() {
        this.values = new Object[DEFAULT_CAPACITY];
        this.events = new ArrayList<>();
    }

    public void undo() {
        int index = this.events.size() - 1;
        ListEvent event = this.events.get(index);

        this.values = event.getObjects();
        this.events.add(new ListEvent(EventType.UNDO, this.values));
    }

    public void undoAll() {

    }

    public void restoreValue(int index) {

    }

    public void restoreValues(int fromIndex, int tillIndex) {

    }

    public V deleteValue(V value) {
        if(!this.hasValue(value)) {
            throw new IllegalStateException("");
        }

        int objectIndex = this.getIndexOf(value);
        Object object = this.removeValue(objectIndex);
        for(int index = 0; index < this.events.size(); index++) {
            Object[] objects = this.events.get(index).getObjects();

            for(int subdex = 0; subdex < objects.length; subdex++) {
                if(objects[subdex].equals(value)) {
                    this.events.remove(index);
                }
            }
        }

        return (V) object;
    }

    public V[] deleteValues(V[] values) {
        if(!this.hasValues(values)) {
            throw new IllegalStateException("");
        }

        int length = values.length;
        Object[] objects = new Object[length];
        for(int index = 0; index < length; index++) {
            int subdex = this.getIndexOf(values[index]);
            objects[index] = this.removeValue(subdex);
        }

        for(int index = 0; index < this.events.size(); index++) {
            Object[] subObjects = this.events.get(index).getObjects();

            for(int subdex = 0; subdex < subObjects.length; subdex++) {
                for(int valuesDex = 0; valuesDex < values.length; valuesDex++) {
                    if(subObjects[valuesDex].equals(values[valuesDex])) {

                    }
                }
            }
        }

        return (V[]) objects;
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
            Object value = this.values[index];
            if(value == null) {
                string.append("null");

            } else {
                string.append(value);
            }

            if(index < this.getTechnicalSize()) {
                string.append(",");
            }
        }

        return string + "]";
    }
}