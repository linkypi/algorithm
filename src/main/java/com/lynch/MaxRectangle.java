package com.lynch;

import org.junit.Test;

import java.util.*;

/**
 * https://leetcode.cn/problems/word-rectangle-lcci
 * 给定一份单词的清单，设计一个算法，创建由字母组成的面积最大的矩形，
 * 其中每一行组成一个单词 (自左向右)，每一列也组成一个单词 (自上而下)。
 * 不要求这些单词在清单里连续出现，但要求所有行等长，所有列等高。
 * 如果有多个面积最大的矩形，输出任意一个均可。一个单词可以重复使用。
 * 示例 1:
 *
 * 输入: ["aa"]
 * 输出:
 * [
 *    "aa",
 *    "aa"
 * ]
 * 输入: ["this", "real", "hard", "trh", "hea", "iar", "sld"]
 * 输出:
 * [
 *    "this",
 *    "real",
 *    "hard"
 * ]
 * @author: lynch
 * @description:
 * @date: 2024/1/28 16:50
 */
public class MaxRectangle {

    @Test
    public void test(){
//        String[] words = new String[]{"this", "real", "hard", "trh", "hea", "iar", "sld"};
        String[] words = new String[]{"aa"};
        String[] result = new MaxRectangle().maxRectangle(words);
        for (String s : result) {
            System.out.println(s);
        }
    }
    class Trie{
        Trie[] childs;
        boolean isLeaf;
        public Trie(){
            childs = new Trie[26];
        }
    }

    Trie root;
    Map<Integer, Set<String>> map;  //把清单根据单词长度集合起来
    int maxArea;
    int maxLength;
    List<String> ans;   //别忘最后转换成数组输出
    public String[] maxRectangle(String[] words) {
        root = new Trie();
        //构造字典树
        for(String str: words){
            Trie node = root;
            for(char c: str.toCharArray()){
                if(node.childs[c-'a'] == null){
                    node.childs[c-'a'] = new Trie();
                }
                node = node.childs[c-'a'];
            }
            node.isLeaf = true;
        }

        map = new HashMap<>();
        ans = new ArrayList<>();
        maxArea = 0;
        maxLength = 0;
        for(String w: words){
            int len = w.length();
            maxLength = Math.max(maxLength, len);
            Set<String> set = map.getOrDefault(len, new HashSet<>());
            set.add(w);
            map.put(len, set);
        }

        List<String> path = new ArrayList<>();
        for(int key: map.keySet()){
            path.clear();
            //回溯需要的参数是：相同长度单词的集合，存放路径的列表，当前单词的长度
            dfs(map.get(key), path, key);
        }

        String[] ultimate = new String[ans.size()];
        return ans.toArray(ultimate);
    }

    //回溯的“套路”
    public void dfs(Set<String> dic, List<String> path, int wordLen){
        //剪枝，dic里的情况不可能得到最优解，提前过滤掉不考虑
        if(wordLen*maxLength <= maxArea)   return;

        //终止条件：如果path矩阵的高度已经超过清单中最长单词长度，结束
        if(path.size() > maxLength) return;

        for(String str: dic){
            //做选择
            path.add(str);

            boolean[] res = isValid(path);
            if(res[0]){ //下面还可以再加
                int area = path.size()*path.get(0).length();
                if(res[1] && (area>maxArea)){   //每列都是单词的矩阵
                    maxArea = area;
                    //ans = path;   一定注意这里不能直接把path引用交给答案
                    ans = new ArrayList<>(path);
                }
                //回溯
                dfs(dic, path, wordLen);
            }
            //撤销选择
            path.remove(path.size()-1);
        }
    }

    /** 判断一个矩阵是否每一列形成的单词都在清单里
     *   存在两种情况：1.有的列中的字母不在字典树中，即这一列不可能构成单词，整个矩阵不合要求
     *   2.每列的所有字母都在字典树中但有的结尾不是leaf，也就是有的列目前还不是个单词
     *   所以需要一个boolean数组res[]来存放结果：
     *   res[0]表示是否有字母不在字典树中，true:都在，false:有不在的
     *   res[1]表示是否所有的列都构成了清单里的单词
     */
    public boolean[] isValid(List<String> input){
        boolean[] res = new boolean[2];
        boolean allLeaf = true;
        int m = input.size();
        int n = input.get(0).length();
        for(int j=0; j<n; j++){
            //按列来看单词是否在字典树
            Trie node = root;
            for(int i=0; i<m; i++){
                int c = input.get(i).charAt(j) - 'a';
                if(node.childs[c] == null)  return new boolean[]{false, false};
                node = node.childs[c];
            }
            if(!node.isLeaf)    allLeaf = false;
        }
        return new boolean[]{true, allLeaf};
    }
}