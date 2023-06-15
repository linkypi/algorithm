package com.lynch.topology;

import java.util.*;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/4/12 10:57
 */
public class DictSort {

    public static void main(String[] args) {
        String[] words = {"wrt","wrf","er","ett","rftt"};
//        String result = alienOrder(words);

        String result2 = new Solution().alienOrder(words);
        System.out.println("result: "+ result2);
    }


    public static class Solution {
        int N = 26, M = N * N, idx = 0, cnt = 0;
        int[] he = new int[N], e = new int[M], ne = new int[M];
        int[] in = new int[N], out = new int[N];
        boolean[] vis = new boolean[N];
        void add(int a, int b) {
            e[idx] = b;
            ne[idx] = he[a];
            he[a] = idx++;
            out[a]++; in[b]++;
        }
        public String alienOrder(String[] ws) {
            int n = ws.length;
            Arrays.fill(he, -1);
            for (int i = 0; i < n; i++) {
                for (char c : ws[i].toCharArray()) {
                    if (!vis[c - 'a'] && ++cnt >= 0) vis[c - 'a'] = true;
                }
                for (int j = 0; j < i; j++) {
                    if (!build(ws[j], ws[i])) return "";
                }
            }
            Deque<Integer> d = new ArrayDeque<>();
            for (int i = 0; i < 26; i++) {
                if (vis[i] && in[i] == 0) d.addLast(i);
            }
            StringBuilder sb = new StringBuilder();
            while (!d.isEmpty()) {
                int u = d.pollFirst();
                sb.append((char)(u + 'a'));
                for (int i = he[u]; i != -1; i = ne[i]) {
                    int j = e[i];
                    if (--in[j] == 0) d.addLast(j);
                }
            }
            return sb.length() == cnt ? sb.toString() : "";
        }
        boolean build(String a, String b) {
            int n = a.length(), m = b.length(), len = Math.min(n, m);
            for (int i = 0; i < len; i++) {
                int c1 = a.charAt(i) - 'a', c2 = b.charAt(i) - 'a';
                if (c1 != c2) {
                    add(c1, c2);
                    return true;
                }
            }
            return n <= m;
        }
    }


    static final int VISITING = 1, VISITED = 2;
    static Map<Character, List<Character>> edges = new HashMap<Character, List<Character>>();
    static Map<Character, Integer> states = new HashMap<Character, Integer>();
    static boolean valid = true;
    static char[] order;
    static int index;

    public static String alienOrder(String[] words) {
        int length = words.length;
        for (String word : words) {
            int wordLength = word.length();
            for (int j = 0; j < wordLength; j++) {
                char c = word.charAt(j);
                edges.putIfAbsent(c, new ArrayList<Character>());
            }
        }
        for (int i = 1; i < length && valid; i++) {
            addEdge(words[i - 1], words[i]);
        }
        order = new char[edges.size()];
        index = edges.size() - 1;
        Set<Character> letterSet = edges.keySet();
        for (char u : letterSet) {
            if (!states.containsKey(u)) {
                dfs(u);
            }
        }
        if (!valid) {
            return "";
        }
        return new String(order);
    }

    public static void addEdge(String before, String after) {
        int length1 = before.length(), length2 = after.length();
        int length = Math.min(length1, length2);
        int index = 0;
        while (index < length) {
            char c1 = before.charAt(index), c2 = after.charAt(index);
            if (c1 != c2) {
                edges.get(c1).add(c2);
                break;
            }
            index++;
        }
        if (index == length && length1 > length2) {
            valid = false;
        }
    }

    public static void dfs(char u) {
        states.put(u, VISITING);
        List<Character> adjacent = edges.get(u);
        for (char v : adjacent) {
            if (!states.containsKey(v)) {
                dfs(v);
                if (!valid) {
                    return;
                }
            } else if (states.get(v) == VISITING) {
                valid = false;
                return;
            }
        }
        states.put(u, VISITED);
        order[index] = u;
        index--;
    }


}
