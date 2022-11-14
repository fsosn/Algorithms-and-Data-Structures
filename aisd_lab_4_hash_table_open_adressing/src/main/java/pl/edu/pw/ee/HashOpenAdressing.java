package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {

    private final T nil = null;
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;

    private class Deleted implements Comparable<T> {

        @Override
        public int compareTo(T o) {
            return -1;
        }
    }

    HashOpenAdressing() {
        this(2039);
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);
        resizeIfNeeded();

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && !newElem.equals(hashElems[hashId]) && !(hashElems[hashId] instanceof HashOpenAdressing.Deleted)) {
            if (i + 1 == size) {
                doubleResize();
                i = -1;
            }

            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }

        if (!newElem.equals(hashElems[hashId])) {
            nElems++;
        }

        hashElems[hashId] = newElem;
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int numOfTimesInLoop = 0;

        while ((hashElems[hashId] != nil && !elem.equals(hashElems[hashId])) || hashElems[hashId] instanceof HashOpenAdressing.Deleted) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            numOfTimesInLoop++;
            if (numOfTimesInLoop > this.nElems) {
                return null;
            }
        }

        return hashElems[hashId] != nil ? hashElems[hashId] : null;
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);
        int numOfTimesInLoop = 0;

        while ((hashElems[hashId] != nil && !elem.equals(hashElems[hashId])) || hashElems[hashId] instanceof HashOpenAdressing.Deleted) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);

            numOfTimesInLoop++;
            if (numOfTimesInLoop > this.nElems) {
                return;
            }
        }

        if (hashElems[hashId] != nil && elem.equals(hashElems[hashId])) {
            hashElems[hashId] = (T) new Deleted();
            nElems--;
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        }
    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        T[] oldElems = (T[]) new Comparable[this.size];
        System.arraycopy(this.hashElems, 0, oldElems, 0, this.size);

        this.size *= 2;
        this.nElems = 0;
        this.hashElems = (T[]) new Comparable[this.size];

        for (T elem : oldElems) {
            if (elem != nil && !(elem instanceof HashOpenAdressing.Deleted)) {
                put(elem);
            }
        }
    }
}
