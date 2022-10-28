package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public class HashListChaining<T extends Comparable<T>> implements HashTable<T> {

    private final Elem<T> nil = null;
    private Elem<T>[] hashElems;
    private int nElem;

    private class Elem<T> {

        private T value;
        private Elem<T> next;

        Elem(T value, Elem<T> nextElem) {
            this.value = value;
            this.next = nextElem;
        }
    }

    public HashListChaining(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("Hash table size cannot be lower than 1!");
        }

        hashElems = new Elem[size];
        initializeHash();
    }

    @Override
    public void add(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot add null value!");
        }

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> oldElem = hashElems[hashId];
        while (oldElem != nil && !oldElem.value.equals(value)) {
            oldElem = oldElem.next;
        }
        if (oldElem != nil) {
            oldElem.value = value;
        } else {
            hashElems[hashId] = new Elem(value, hashElems[hashId]);
            nElem++;
        }
    }

    @Override
    public T get(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot get null value!");
        }

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElems[hashId];

        while (elem != nil && !elem.value.equals(value)) {
            elem = elem.next;
        }

        return elem != nil ? elem.value : null;
    }

    @Override
    public void delete(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot delete null value!");
        }

        int hashCode = value.hashCode();
        int hashId = countHashId(hashCode);

        Elem<T> elem = hashElems[hashId];

        if (elem == nil) {
            return;
        }

        if (elem.value.equals(value)) {
            hashElems[hashId] = elem.next;
            this.nElem--;
        } else {
            Elem<T> previous = elem;
            Elem<T> current = elem.next;

            while (current != nil && !current.value.equals(value)) {
                previous = current;
                current = current.next;
            }

            if (current != nil) {
                previous.next = current.next;
                this.nElem--;
            }
        }
    }

    public double countLoadFactor() {
        double size = hashElems.length;
        return nElem / size;
    }

    private void initializeHash() {
        int n = hashElems.length;

        for (int i = 0; i < n; i++) {
            hashElems[i] = nil;
        }
    }

    private int countHashId(int hashCode) {
        int n = hashElems.length;
        return Math.abs(hashCode & 0x7FFFFFFF) % n;
    }

    public int getNumberOfElements() {
        return this.nElem;
    }

}
