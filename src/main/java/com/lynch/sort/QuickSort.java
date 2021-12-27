package com.lynch.sort;

public class QuickSort extends AbstractSort {
    public QuickSort() {
    }

    public QuickSort(int[] arr) {
        this.array = arr;
    }

    public static void main(String[] args) {
        int[] arr = {6, 2, 9, 4, 7, 1, 8, 5};
        QuickSort quickSort = new QuickSort(arr);
        quickSort.print("排序前: ");
        quickSort.sort();
        quickSort.print("排序后: ");
        System.out.print(quickSort.toString());
//
    }

    @Override
    protected void sortInternal() {
        quickSort(0, array.length);
    }

    private void quickSort(int begin, int end) {
        if (end - begin < 2) {
            return;
        }

        // 确定轴点位置
        int mid = pivotIndex(begin, end);
        // 对子序列进行快速排序
        quickSort(begin, mid);
        quickSort(mid + 1, end);
    }

    /**
     * 确定范围 [begin, end) 的轴点位置
     * @param begin
     * @param end
     * @return  轴点元素的最终位置
     */
    private int pivotIndex(int begin ,int end) {
        // 备份 begin 位置的元素
        int pivot = array[begin];
        end--;

        while (begin < end) {

            while (begin < end) {
                // 从右到左遍历, 若轴点元素小于右边节点则end指针向前移动
                if (compareValue(pivot, array[end]) < 0) {
                    end--;
                } else {
                    // 否则将尾部元素前移到 begin 位置, 同时将 begin 指针后移
                    // 进而退出循环 从开头 begin 位置开始扫描
                    array[begin++] = array[end];
                    break;
                }
            }

            while (begin < end) {
                // 从左往右遍历, 若轴点元素大于左边节点元素则 begin 指针向后移动
                if (compareValue(pivot, array[begin]) > 0) {
                    begin++;
                } else {
                    // 否则将begin 元素移动到 end 位置, 同时将 end 指针前移
                    // 进而退出循环,从结束end 位置开始扫描
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        array[begin] = pivot;
        return begin;
    }
}
