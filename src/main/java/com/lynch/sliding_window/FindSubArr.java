package com.lynch.sliding_window;

import java.util.HashMap;

/**
 * 假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。
 * 返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/3/31 16:54
 */
public class FindSubArr {
    public static void main(String[] args) {
        int[] big = {7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7};
        int[] small = {1,5,9};
//         int[] big = {842, 336, 777, 112, 789, 801, 922, 874, 634, 121, 390, 614, 179, 565, 740, 672, 624, 130, 555, 714, 9, 950, 887, 375, 312, 862, 304, 121, 360, 579, 937, 148, 614, 885, 836, 842, 505, 187, 210, 536, 763, 880, 652, 64, 272, 675, 33, 341, 101, 673, 995, 485, 16, 434, 540, 284, 567, 821, 994, 174, 634, 597, 919, 547, 340, 2, 512, 433, 323, 895, 965, 225, 702, 387, 632, 898, 297, 351, 936, 431, 468, 694, 287, 671, 190, 496, 80, 110, 491, 365, 504, 681, 672, 825, 277, 138, 778, 851, 732, 176};
//         int[] small ={ 950, 885, 702, 101, 312, 652, 555, 936, 842, 33, 634, 851, 174, 210, 287, 672, 994, 614, 732, 919, 504, 778, 340, 694, 880, 110, 777, 836, 365, 375, 536, 547, 887, 272, 995, 121, 225, 112, 740, 567, 898, 390, 579, 505, 351, 937, 825, 323, 874, 138, 512, 671, 297, 179, 277, 304};
         int[] ints = shortestSeq(big, small);
    }

     public static int[] shortestSeq(int[] big, int[] small) {
          if (big == null || big.length == 0) {
               return new int[]{};
          }
          if (small == null || small.length == 0) {
               return new int[]{};
          }
          HashMap<Integer, Integer> map = new HashMap<>();
          for (int i = 0; i < small.length; i++) {
               map.put(small[i], 0);
          }

          int min = Integer.MAX_VALUE;
          int left = 0;
          int start = 0;

          int windowSize = small.length;
          int index = 0;
          while (index < big.length) {
               int item = big[index];
               // 不是目标元素的直接跳过
               if (!map.containsKey(item)) {
                    index++;
                    continue;
               }

               // 记录窗口起始位置
               if (windowSize == small.length) {
                    left = index;
               }
               // 只有头一次进入窗口的才会减少size
               if(map.get(item) == 0){
                    windowSize--;
               }
               // 加入窗口后标记为 true
               map.put(item, map.get(item) + 1);

               // 已全部包含则首先收缩窗口，去除重复元素后再统计窗口长度
               if (windowSize == 0) {
                    // 若是无关元素 或者 窗口内部存在重复元素() 则继续收缩窗口
                    while (!map.containsKey(big[left]) || map.get(big[left]) > 1) {
                         if(map.containsKey(big[left])){
                              map.put(big[left], map.get(big[left]) - 1);
                         }
                         left++;
                    }

                    int len = index - left + 1;
                    if (len < min) {
                         min = len;
                         start = left;
                    } else if (len == min) {
                         // 长度相同则记录最小索引
                         start = Math.min(start, left);
                    }
                    // 结算完成后从左边收缩窗口
                    map.put(big[left], map.get(big[left]) - 1);
                    left++;

                    windowSize++;
               }

               index++;
          }
          if (min == Integer.MAX_VALUE) {
               return new int[]{};
          }
          return new int[]{ start, start + min - 1 };
     }

     public int[] shortestSeq2(int[] big, int[] small) {
          int[] res = {};//结果数组
          int smallLen = small.length;//用来维护map中当前含有且未出现在大数组里的数字个数
          int bigLen = big.length;
          int left = 0;
          int right = 0;
          int minLen = bigLen;//存放结果子串长度
          HashMap<Integer, Integer> map = new HashMap<>();
          for (int i = 0; i < smallLen; i++) {
               map.putIfAbsent(small[i], 1);//存放小数组所有元素，因为不会重复，直接赋为1
          }
          while (right < bigLen) {//边界条件
               if (map.containsKey(big[right])) {//判断right指针代表的元素是否出现在map
                    //即使含有该元素，只有当map中该元素数量大于0才可以让smallLen--！！在该处浪费了很多时间，比如big：1123，small：123，1在big里出现两次，但只有第一次出现会被统计
                    if (map.get(big[right]) > 0) {
                         smallLen--;
                    }
                    //每次都要将map含有的big数组元素-1
                    map.put(big[right], map.get(big[right]) - 1);

               }
               while (smallLen == 0) {//big里找到了一个子串
                    if (right - left < minLen) {
                         minLen = right - left;
                         res = new int[]{left, right};
                    }
                    if (map.containsKey(big[left])) {//对左指针判断
                         //每次都要将map含有的big数组元素+1
                         map.put(big[left], map.get(big[left]) + 1);
                         //只有当map中该元素数量大于0才可以让smallLen++
                         if (map.get(big[left]) > 0) {
                              smallLen++;
                         }
                    }
                    left++;
               }
               right++;
          }

          return res;
     }
}
