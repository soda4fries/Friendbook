package com.wia1002g3.friendbook.services;


import java.util.ArrayList;
import java.util.List;

public class MyHashSet<E> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    private List<List<E>> buckets;
    private int size;
    private double loadFactor;

    public MyHashSet() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashSet(int initialCapacity, double loadFactor) {
        this.buckets = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++) {
            buckets.add(null);
        }
        this.size = 0;
        this.loadFactor = loadFactor;
    }

    public boolean add(E element) {
        int index = getIndex(element);
        List<E> bucket = buckets.get(index);
        if (bucket == null) {
            bucket = new ArrayList<>();
            buckets.set(index, bucket);
        }
        if (bucket.contains(element)) {
            return false;
        }
        bucket.add(element);
        size++;
        if (size > buckets.size() * loadFactor) {
            resize();
        }
        return true;
    }

    public boolean remove(E element) {
        int index = getIndex(element);
        List<E> bucket = buckets.get(index);
        if (bucket != null && bucket.remove(element)) {
            size--;
            return true;
        }
        return false;
    }

    public boolean contains(E element) {
        int index = getIndex(element);
        List<E> bucket = buckets.get(index);
        return bucket != null && bucket.contains(element);
    }

    public int size() {
        return size;
    }

    private int getIndex(E element) {
        return Math.abs(element.hashCode()) % buckets.size();
    }

    private void resize() {
        int newCapacity = buckets.size() * 2;
        List<List<E>> newBuckets = new ArrayList<>(newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            newBuckets.add(null);
        }
        for (List<E> bucket : buckets) {
            if (bucket != null) {
                for (E element : bucket) {
                    int index = Math.abs(element.hashCode()) % newCapacity;
                    List<E> newBucket = newBuckets.get(index);
                    if (newBucket == null) {
                        newBucket = new ArrayList<>();
                        newBuckets.set(index, newBucket);
                    }
                    newBucket.add(element);
                }
            }
        }
        buckets = newBuckets;
    }
}
