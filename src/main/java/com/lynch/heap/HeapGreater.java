package com.lynch.heap;

import com.lynch.binary_tree.BST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Queue;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/1 23:08
 */
public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> posMap;
    private int heapSize;
    private Comparator<? super T> comparator;

    public HeapGreater(int capacity){
        this.heapSize = capacity;
    }

    public HeapGreater(Comparator<T> comparator){
        this.comparator = comparator;
    }

    public HeapGreater(int capacity, Comparator<T> comparator){
        this.heapSize = capacity;
        this.comparator = comparator;
    }

    public int size(){
        return heapSize;
    }

    public T peek(){
        return heap.get(0);
    }

    public boolean contains(T obj){
        return posMap.containsKey(obj);
    }

    public void push(T obj){
        heap.add(obj);
        posMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop(){
        T obj = heap.get(0);
        swap(0, heapSize-1);
        heap.remove(--heapSize);
        posMap.remove(obj);
        heapify(0);
        return obj;
    }

    public void resign(T obj){
        heapInsert(posMap.get(obj));
        heapify(posMap.get(obj));
    }

    private void heapify(int index) {
        int leftIndex = index * 2 + 1;
        while (leftIndex < heapSize) {
            int best = leftIndex;
            T left = heap.get(leftIndex);
            if (leftIndex + 1 < heapSize && comparator.compare(heap.get(leftIndex + 1), left) < 0) {
                best = leftIndex + 1;
            }
            if (comparator.compare(heap.get(index), heap.get(best)) < 0) {
                best = index;
            }
            if(best == index){
                break;
            }
            swap(index, best);
            index = best;
            leftIndex = index * 2 +1;
        }
    }

    private void swap(int a, int b) {
        T temp1 = heap.get(a);
        T temp2 = heap.get(b);
        heap.set(a, temp2);
        heap.set(b, temp1);
        posMap.put(temp1, b);
        posMap.put(temp2, a);
    }

    private void heapInsert(int index) {
        T current = heap.get(index);
        T parent = heap.get((index - 1) / 2);
        while (comparator.compare(current, parent) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

}
