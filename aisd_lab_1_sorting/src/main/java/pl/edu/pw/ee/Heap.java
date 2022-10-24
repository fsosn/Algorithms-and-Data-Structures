package pl.edu.pw.ee;

import java.util.List;
import pl.edu.pw.ee.services.HeapExtension;
import pl.edu.pw.ee.services.HeapInterface;

public class Heap<T extends Comparable<T>> implements HeapInterface<T>, HeapExtension {

    private List<T> data;
    private boolean wasBuilt = false;

    private void swap(int firstId, int secondId) {
        T firstVal = data.get(firstId);
        data.set(firstId, data.get(secondId));
        data.set(secondId, firstVal);
    }

    private void heapUp(T item) {
        int currentIndex = data.size() - 1;

        while (currentIndex > 0) {
            int parent = (currentIndex - 1) / 2;

            if (data.get(parent).compareTo(item) > 0) {
                return;
            }

            swap(parent, currentIndex);
            currentIndex = parent;
        }
    }

    public Heap(List<T> data) {
        if (data == null) {
            throw new NullPointerException("Input list cannot be null!");
        }
        if (data.contains(null)) {
            throw new IllegalArgumentException("Elements of input list cannot be null!");
        }

        this.data = data;
    }

    @Override
    public void put(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot put null item on heap!");
        }
        if (!wasBuilt) {
            build();
        }
        this.data.add(item);
        heapUp(item);
    }

    @Override
    public T pop() {
        if (data.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Cannot pop from empty heap!");
        }

        if (!wasBuilt) {
            build();
        }

        int lastIndex = data.size() - 1;
        swap(0, lastIndex);
        T root = data.remove(lastIndex);

        heapify(0, data.size());

        return root;
    }

    @Override
    public void build() {
        int n = data.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i, n);
        }

        this.wasBuilt = true;
    }

    @Override
    public void heapify(int startId, int endId) {
        int largestElement = startId;
        int leftChild = 2 * startId + 1;
        int rightChild = 2 * startId + 2;

        if (leftChild < endId && data.get(leftChild).compareTo(data.get(largestElement)) > 0) {
            largestElement = leftChild;
        }
        if (rightChild < endId && data.get(rightChild).compareTo(data.get(largestElement)) > 0) {
            largestElement = rightChild;
        }
        if (startId != largestElement) {
            swap(startId, largestElement);
            heapify(largestElement, endId);
        }
    }

}
