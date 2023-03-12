package de.maxwell.utils.lists;

public interface MultiList<V> {

    int getSize();

    int getTechnicalSize();

    boolean isEmpty();

    boolean isEmptyAt(int index);

    void setCapacity(int capacity);

    int getCapacity();

    int getAvailableCapacity();

    void addCapacity(int capacity);

    void removeCapacity(int capacity);

    boolean hasValue();

    boolean hasValues(V[] values);

    void setValue(int index, V value);

    void setValues(int index, V[] values);

    V getValue(int index);

    V[] getValues(int fromIndex, int tillIndex);

    int getIndexOf(V value);

    void addValue(V value);

    void addValues(V[] values);

    V removeValue(int index);

    V[] removeValues(int fromIndex, int tillIndex);

    void insertValue(int index, V value);

    void insertValues(int index, V[] values);

    void shiftValue(int fromIndex, int toIndex);

    void shiftValues(int fromIndex, int tillIndex, int toIndex);

    void resetList();

    void undo();

    void undoAll();

    void restoreValue(int index);

    void restoreValues(int fromIndex, int tillIndex);

    V deleteValue(V value);

    V[] deleteValues(V[] values);

    void haltCollection();

    V getFirst();

    V getLast();

    V[] getAsArray();

    void injectBubbleSort();

    void injectQuickSort();

    void injectMergeSort();

    void injectHeapSort();

    void injectRadixSort();

    void injectShellSort();
}