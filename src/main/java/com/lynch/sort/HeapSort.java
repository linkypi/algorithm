package com.lynch.sort;

public class HeapSort extends AbstractSort {
    public HeapSort() {
    }

    public HeapSort(int[] arr) {
        this.array = arr;
    }

    public static void main(String[] args) {
//        int[] arr = {6, 2, 9, 4, 7, 1, 8, 5};
//        int[] arr2 = {6, 2, 9, 4, 7, 1, 8, 5};
//        int[] arr3 = {6, 2, 9, 4, 7, 1, 8, 5};
//        HeapSort heapSort = new HeapSort(arr);
//        heapSort.print("排序前: ");
//        heapSort.sort();
//        heapSort.print("排序后: ");
//        System.out.print(heapSort.toString());
//

    }


    private int heapSize = 0;

    @Override
    protected void sortInternal() {
        heapSize = this.array.length;
        // 建堆
        heapify();
//        System.out.print("建堆完成: ");
//        print();
        // 调整堆
        adjust();
    }

    private void adjust() {
        for (int i = array.length - 1; i >= 0; i--) {
            if (compare(i, 0) < 0) {
                swap(i, 0);
//                System.out.println("交换前后两个元素: " + array[i] + "  " + array[0]);
                heapSize--;
                heapify();
//                System.out.print("建堆完成, heapSize: " + heapSize + "  , arr: ");
//                print();
            }
        }
    }

    /**
     * 从第一个非叶子节点开始, 第一个非叶子节点的下标为 size/2 - 1
     * 对比该节点与其左右两个节点的大小, 存在差异(取决于构建的是大根堆还是小根堆)则交换位置
     * 该位置调整完成后继续往上检查非叶子节点,继续对比交换,直到根节点为止
     */
    private void heapify() {
        int n = heapSize / 2 - 1;
        for (int i = n; i >= 0; i--) {
            int left = (i << 1) + 1;

            if (compare(i, left) < 0) {
                swap(i, left);
            }

            int right = (i << 1) + 2;
            if (right < heapSize && compare(i, right) < 0) {
                swap(i, right);
            }
        }
    }

    private void srt(){
        heapSize = array.length;
        for (int i = (heapSize>>2) - 1; i >=0; i--) {
            shiftDown(i);
        }
        while (heapSize>1){
            swap(0, --heapSize);
            shiftDown(0);
        }
    }

    private void shiftDown(int index) {
        int element = array[index];

        int half = heapSize >> 1;
        while (index < half) {
            // 从子节点中选出最大的一个节点与父节点比较
            int childIndex = (index << 1) + 1;
            int child = array[childIndex];

            int rightIndex = childIndex + 1;
            if (rightIndex < heapSize && array[rightIndex] > child) {
                child = array[childIndex = rightIndex];
            }

            // 若子节点中没有一个节点比父节点大则退出循环
            if (element > child) {
                break;
            }

            array[index] = child;
            index = childIndex;
        }
        array[index] = element;
    }
}
