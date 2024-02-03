package com.lynch.string;

import org.junit.Test;

import java.util.*;

/**
 * @author: lynch
 * @description:
 * @date: 2024/1/30 23:30
 */
public class MultiSearch {

    @Test
    public void Test(){
        String big =  "mississippi";
        String[] smalls = new String[]{"is","ppi","hi","sis","i","ssippi"};
        int[][] result = multiSearch(big, smalls);
        System.out.print(Arrays.deepToString(result));
    }

   static class Trie{
        TrieNode root;
        public Trie(String[] words){
            root = new TrieNode();
            for(String word : words){
                TrieNode node = root;
                for(char w : word.toCharArray()){
                    int i = w - 'a';
                    if(node.next[i] == null){
                        node.next[i] = new TrieNode();
                    }
                    node = node.next[i];
                }
                node.end = word;
            }
        }

        public List<String> search(String str){
            TrieNode node = root;
            List<String> res = new ArrayList<>();
            for(char c : str.toCharArray()){
                int i = c - 'a';
                if(node.next[i] == null){
                    break;
                }
                node = node.next[i];
                if(node.end != null){
                    res.add(node.end);
                }
            }
            return res;
        }
    }

    static class TrieNode{
        String end;
        TrieNode[] next = new TrieNode[26];
    }


    public static int[][] multiSearch(String big, String[] smalls) {
        Trie trie = new Trie(smalls);
        Map<String, List<Integer>> hit = new HashMap<>();
        for(int i = 0; i < big.length(); i++){
            List<String> matchs = trie.search(big.substring(i));
            for(String word: matchs){
                if(!hit.containsKey(word)){
                    hit.put(word, new ArrayList<>());
                }
                hit.get(word).add(i);
            }
        }

        int[][] res = new int[smalls.length][];
        for(int i = 0; i < smalls.length; i++){
            List<Integer> list = hit.get(smalls[i]);
            if(list == null){
                res[i] = new int[0];
                continue;
            }
            int size = list.size();
            res[i] = new int[size];
            for(int j = 0; j < size; j++){
                res[i][j] = list.get(j);
            }
        }
        return res;
    }
}
