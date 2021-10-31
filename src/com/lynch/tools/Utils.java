package com.lynch.tools;

public class Utils {
    public static void printArr(int[] arr) {
        printArr("", arr);
    }
    public static void printArr(String msg, int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println(msg + " arr: null");
            return;
        }

        if (msg != null && msg != "") {
            System.out.print(msg + " arr: ");
        } else {
            System.out.print("arr: ");
        }

        for (int item : arr) {
            System.out.print(item + "  ");
        }
        System.out.println();
    }

    public static int[] copyArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }
        return ret;
    }

    /**
     * 对比两个数组中的元素是否一致
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) {
            System.out.println("arr1 is null or it's length is 0.");
            return false;
        }
        if (arr2 == null || arr2.length == 0) {
            System.out.println("arr2 is null or it's length is 0.");
            return false;
        }
        if (arr1.length != arr2.length) {
            System.out.println("arr1 length is not equal arr2 length.");
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] generateRandomArr(int maxSize, int maxvalue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxvalue + 1) * Math.random() - (int) (maxvalue * Math.random()));
        }
        return arr;
    }

    /**
     * 生成正数随机数组
     * @param maxSize
     * @param maxvalue
     * @return
     */
    public static int[] generatePositiveRandomArrNoZero(int maxSize, int maxvalue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
            while (arr[i] == 0) {
                arr[i] = (int) ((maxvalue + 1) * Math.random() - (int) (maxvalue * Math.random()));
            }
            arr[i] = arr[i] < 0 ? -arr[i] : arr[i];
        }
        return arr;
    }

    public static int getRandom(int maxvalue) {
        return (int) (maxvalue * Math.random());
    }

    public static int getRandom(int minValue, int maxvalue) {
        int ret = 0;
        while (ret <= minValue) {
            ret = (int) (maxvalue * Math.random());
        }
        return ret;
    }

    /**
     * 获取正数随机数, 不包含 0
     * @param minValue
     * @param maxvalue
     * @return
     */
    public static int getPositiveRandomNoZero(int minValue, int maxvalue) {
        int ret = 0;
        while (ret <= minValue || ret == 0) {
            ret = (int) (maxvalue * Math.random());
        }
        return ret;
    }
}