package pl.edu.pw.ee;

import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T>, HeapExtension {

    private List<T> data;

    public Heap(List<T> data) {
        this.data = data;
    }

    @Override
    public void put(T item) {
        this.data.add(item);
    }

    @Override
    public T pop() {
        T elementToBePopped = null;
        if (!this.data.isEmpty()) {
            elementToBePopped = this.data.remove(0);
        }
        return elementToBePopped;
    }

    @Override
    public void build() {
        int n = data.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i, n);
        }
    }

    @Override
    public void heapify(int startId, int endId) {
        // TODO
    }
}
