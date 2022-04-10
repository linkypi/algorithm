package com.lynch.string;

/**
 * 给定一个字符串，求最长回文子串长度
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/4/8 21:02
 */
public class Manacher {
    public static void main(String[] args) {
         String str = "rumxabcdcbkfefkbcdcbalqmk";

        int withTwoPoint = findWithTwoPoint(str);
        int max = manacher(str.toCharArray());
        System.out.println("max: " + max);
    }

    private static int findWithTwoPoint(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        int max = 0;
        char[] buf = buildBuf(str.toCharArray());
        for (int i = 0; i < buf.length; i++) {
            try {
                // 奇回文
                String p1 = findPalindrome2(buf, i, i);
                // 偶回文
                String p2 = findPalindrome2(buf, i, i + 1);

                max = Math.max(max, p1.length());
                max = Math.max(max, p2.length());
            }catch (Exception ex){
                System.out.println("xxx");
            }
        }
        return max;
    }

    private static String findPalindrome2(char[] arr, int left, int right){
        while(left >= 0 && right < arr.length && arr[left]== arr[right] && left != right){
            left --;
            right ++;
        }

        return null;
    }

    private static String findPalindrome(String str, int left, int right){
        char[] arr = str.toCharArray();
        while(left >= 0 && right < arr.length && arr[left]== arr[right] && left != right){
            left --;
            right ++;
        }

        return str.substring(left, right-left+1);
    }

    /**
     *  1. i 在 R 外， 直接暴力扩 无忧化
     *  2. i 在 R 内
     *     2.1 i 的对称点 i' 在LR内，那么i的回文半径与i'一致， 如：
     *
     *          [ a b c  d  c b k f e f k b c d c b a ]
     *          L        i'         c         i       R
     *
     *     2.2 i 的对称点 i', 它的回文半径左边界在L外，那么i的回文半径为 R-i
     *
     *          a [ b c d  e  d c b a f s f a b c d e d c b ] k
     *            L        i'           c           i       R
     *
     *     2.3 i 的对称点 i' 在刚好压在 L 上, 那么i的回文半径至少是 R-i，但是仍有可能往外扩充
     *
     *          z [ a b c d c b a f a b c d c b a ] ?
     *            L       i'      c       i       R
     * @param arr
     * @return
     */
    private static int manacher(char[] arr){
        if(arr == null || arr.length ==0){
            return 0;
        }

        char[] buffer = buildBuf(arr);
        int n = buffer.length;
        int[] parr = new int[n];
        int radius = -1;
        int center = -1;
        int max = Integer.MIN_VALUE;

        int index = 0; int x = 0;
        while (index < n){
            if(index > radius){
                parr[x] = 1;
                int i = 1;
                while(index+i<n && index - i >=0 && buffer[index+i] == buffer[index-i]){
                    parr[x]++;
                    i++;
                }

                max = Math.max(max, parr[x] /2 );
                x++;
                radius = index + i - 1;
                index++;
                center ++;
                continue;
            }

            // 左边界位置
            int left = 2*center - radius ;
            // i 对称点 i' 位置
            int sp = 2*center - index;
            if(sp > left){
                parr[x] = parr[sp];
                max = Math.max(max, parr[x] /2 );
                x++;
                index++;
                continue;
            }

            // 对称点左边界位置
            int sp_left = sp-parr[sp];
            if(sp_left < left){
                parr[x] = radius - index;
                max = Math.max(max, parr[x] /2 );
                x++;
                index++;
                continue;
            }

            if(sp_left == left){
                int i =  parr[sp]+ 1;
                while(index+i<n && index - i >=0 && buffer[index+i] == buffer[index-i]){
                    parr[x]++;
                    i++;
                }
                max = Math.max(max, parr[x] /2 );
                x++;
            }

        }

        return max;
    }

    private static char[] buildBuf(char[] arr){
        int len = arr.length * 2 + 1;
        char[] buffer = new char[len];
        buffer[0] = '#';
        int index = 1;
        for (char item: arr){
            buffer[index++] = item;
            buffer[index++] = '#';
        }
        return buffer;
    }
}
