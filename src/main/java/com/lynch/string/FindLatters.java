package com.lynch.string;

import java.util.*;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/2 17:32
 */
public class FindLatters {

    public static void main(String[] args) {
        String begin = "hit";
        String end = "cog";

        List<String> list = new ArrayList();
        list.add("hot");
        list.add("dot");
        list.add("dog");
        list.add("lot");
        list.add("log");
        list.add("cog");
        List<String> ladders = findLadders(begin, end, list);
        System.out.println("");
    }

    public static List<String> findLadders(String beginWord, String endWord, List<String> wordList) {
        //定义BFS的队列
        Queue<String> queue = new LinkedList<String>();
        //ans存放答案
        List<String> ans = new LinkedList<String>();
        //标记是否被访问过
        boolean[] visited = new boolean[wordList.size()];
        //存放每个单词的前驱，比如hot的前驱可以是hit,lot等；
        HashMap<String, String> map = new HashMap<String, String>();
        //初步判断
        if (!wordList.contains(endWord)) {
            return ans;
        }
        //将第一个单词加入队列
        queue.add(beginWord);
        boolean flag = false;
        //BFS主要操作
        while (queue.size() != 0) {
            //先将头取出
            String queueHead = queue.poll();
            //如果队列头元素等于endword，代表已经找到，break同时设置flag = true;
            if (queueHead.equals(endWord)) {
                flag = true;
                break;
            }
            //寻找可能的元素加入队列，并且设置对应的前驱。
            for (int i = 0; i < wordList.size(); i++) {
                //如果未被访问过并且可以直接转换，则加入队列，compare()函数用来判断是否可以转换。
                if (visited[i] == false && compare(wordList.get(i), queueHead) == true) {
                    queue.add(wordList.get(i));
                    visited[i] = true;
                    //存储前驱
                    map.put(wordList.get(i), queueHead);
                }
            }
        }
        if (flag == false) {
            return ans;
        }

        //遍历答案
        String key = endWord;
        while (map.get(key) != beginWord) {
            ans.add(key);
            key = map.get(key);
        }
        ans.add(key);
        ans.add(map.get(key));
        Collections.reverse(ans);
        return ans;
    }

    public static boolean compare(String word1, String word2) {
        int diff = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diff++;
                if (diff >= 2) {
                    return false;
                }
            }
        }
        return true;
    }

}
