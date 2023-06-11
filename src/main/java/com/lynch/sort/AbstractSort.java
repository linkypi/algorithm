package com.lynch.sort;

import java.text.DecimalFormat;

public abstract class AbstractSort implements Comparable<AbstractSort> {
    protected int[] array;
    protected long cmpCount = 0;
    protected long swapCount = 0;
    protected long cost;
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.000");

    protected abstract void sortInternal();

    @Override
    public int compareTo(AbstractSort o) {
        int result = (int) (cost - o.cost);
        if(result!=0) return result;

        result = (int) (cmpCount - o.cmpCount);
        if(result!=0) return result;

        return (int) (swapCount - o.swapCount);
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        this.array = arr;
        long startTime = System.nanoTime();
        sortInternal();
        cost = System.nanoTime() - startTime;
    }
    public void sort() {
        if (array == null || array.length < 2) return;
        long startTime = System.nanoTime();
        sortInternal();
        cost = System.nanoTime() - startTime;
    }

    protected int compare(int a, int b) {
        cmpCount++;
        return array[a] - array[b];
    }

    protected int compareValue(int a, int b) {
        cmpCount++;
        return a - b;
    }

    protected void swap(int a, int b) {
        swapCount++;
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    protected void print() {
        print(null);
    }

    protected void print(String msg) {
        if (msg != null && !msg.isEmpty()) {
            System.out.print(msg + "  ");
        }
        for (int value : array) {
            System.out.print(value + "  ");
        }
        System.out.println("");
    }

    @Override
    public String toString() {
        String simpleName = this.getClass().getSimpleName();
        String format = DECIMAL_FORMAT.format(cost / 1000000.0);
        return String.format("%s \t cost %s ms, compare %s times, swap %s times", simpleName, format, numString(cmpCount), numString(swapCount));
    }

    private String numString(long number) {
        if (number < 10000) return number + "";
        if (number < 100000000) return number / 10000 + "万";
        return number / 100000000 + "亿";
    }
}
