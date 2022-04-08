package com.lynch;

import java.util.*;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/8 14:41
 */
public class MaxRectangleFromWordMatrix {
    public static void main(String[] args) {
        String[] words = {
                "this", "real", "hard", "trh", "hea", "iar", "sld"
        };
        List<String> strings = find(words);

        for (String item : strings) {
            System.out.println(item);
        }
//        int[] arr = {1, 2, 3};
//        Deque<Integer> visited = new LinkedList<>();
//        List<Integer> temp = new ArrayList<>();
//        List<List<Integer>> compose2 = getAllCombinations(arr, temp, visited);
//        System.out.println("xxxx");
    }

    /**
     * 获取数组元素所有的组合
     *
     * @param arr
     * @param temp
     * @param visited
     * @return
     */
    private static List<String> getAllStrArranges(char[] arr, List<Character> temp, Deque<Character> visited) {

        List<String> lists = new ArrayList<>();
        // 所有元素都已访问过则结算
        if (visited.size() == arr.length) {
            char[] chars = new char[temp.size()];
            int i = 0;
            for (Character ch : temp) {
                chars[i++] = ch;
            }
            lists.add(new String(chars));
            return lists;
        }
        for (char item : arr) {
            if (visited.contains(item)) {
                continue;
            }
            visited.add(item);
            temp.add(item);

            List<String> combinations = getAllStrArranges(arr, temp, visited);
            lists.addAll(combinations);

            // 都访问过后需要还原
            visited.removeLast();
            temp.remove(temp.size() - 1);
        }
        return lists;
    }

    private static List<List<String>> getAllStrArranges(List<String> list, List<String> temp, Deque<String> visited) {

        List<List<String>> lists = new ArrayList<>();
        // 所有元素都已访问过则结算
        if (visited.size() == list.size()) {
            lists.add(new ArrayList<>(temp));
            return lists;
        }
        // 使用回溯方式获取所有排列
        for (String item : list) {
            if (visited.contains(item)) {
                continue;
            }
            visited.add(item);
            temp.add(item);

            List<List<String>> combinations = getAllStrArranges(list, temp, visited);
            lists.addAll(combinations);

            // 都访问过后需要还原
            visited.removeLast();
            temp.remove(temp.size() - 1);
        }
        return lists;
    }

    private static List<String> find(String[] words) {
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }

        Map<String, Boolean> wordMap = new HashMap<>();
        // 保存指定长度对应的所有字符串
        HashMap<Integer, List<String>> lenMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, true);
            if (!lenMap.containsKey(word.length())) {
                List<String> list = new ArrayList<>();
                list.add(word);
                lenMap.put(word.length(), list);
                continue;
            }
            List<String> list = lenMap.get(word.length());
            list.add(word);
        }

        // 所有矩形可能的边长
        Set<Integer> lens = lenMap.keySet();
        List<String> result = new ArrayList<>();
        int maxArea = 0;

        for (Map.Entry<Integer, List<String>> entry : lenMap.entrySet()) {
            List<String> stringList = entry.getValue();
            // 同等长度的单词所能组成的高度
            int high = stringList.size();
            int width = entry.getKey();

            // 若高度组成的单词长度不存在则跳过
            if (!lens.contains(high)) {
                continue;
            }

            // 若当前面积比已有的最大面积小则跳过
            if(maxArea > high * width){
                continue;
            }
            maxArea = high * width;

            // 获取每行字符的组合方式
            Deque<String> visited = new LinkedList<>();
            List<String> temp = new ArrayList<>();
            List<List<String>> allStrCombinations = getAllStrArranges(stringList, temp, visited);

            for (List<String> list : allStrCombinations) {
                boolean exist = true;
                // 对比每一列组成的字符串是否在清单中存在
                for (int i = 0; i < width; i++) {
                    char[] arr = new char[high];
                    int index = 0;
                    for (String str : list) {
                        arr[index++] = str.charAt(i);
                    }
                    String str = new String(arr);
                    // 判断字符是否存在
                    if (!wordMap.containsKey(str)) {
                        exist = false;
                        break;
                    }
                }
                if (!exist) {
                    continue;
                }
                result = list;
            }
        }
        return result;
    }

}
