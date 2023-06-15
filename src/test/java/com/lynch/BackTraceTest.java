package com.lynch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个字符数组，求全排列
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/6/8 10:40
 */
public class BackTraceTest {

    public static void main(String[] args) {

        List<String> arr = Arrays.asList("1", "2", "3");
        StringBuilder builder = new StringBuilder();
        List<String> trace = new ArrayList<>();

        Boolean[] visited = new Boolean[arr.size()];
        Arrays.fill(visited, false);

        result = new ArrayList<>(arr.size());
        result2 = new ArrayList<>(arr.size());

        backTrace(arr, builder, visited);
        backTrace2(arr, trace, visited);

        System.out.println("result: " + result.size());
    }

    private static List<List<String>> result2 = null;
    public static void backTrace2(List<String> list, List<String> trace, Boolean[] visited) {

        if (trace.size() == list.size()) {
            result2.add(new ArrayList<>(trace));
            return;
        }

        int count = list.size();
        for (int i = 0; i < count; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            String item = list.get(i);
            trace.add(item);

            // 尝试
            backTrace2(list, trace, visited);

            visited[i] = false;
            trace.remove(trace.size()-1);
        }
    }

    private static List<String> result = null;
    public static void backTrace(List<String> list, StringBuilder builder, Boolean[] visited) {

        // 达标后累计结果
        if (builder.length() == list.size()) {
            result.add(builder.toString());
            return;
        }

        int count = list.size();
        for (int i = 0; i < count; i++) {
            if (visited[i]) {
                continue;
            }
            // 做选择
            visited[i] = true;
            String item = list.get(i);
            builder.append(item);

            // 进入下一层决策
            backTrace(list, builder, visited);

            // 取消选择
            visited[i] = false;
            // 移除最后一个字符
            builder.delete(builder.length() - 1, builder.length());
        }
    }
}
