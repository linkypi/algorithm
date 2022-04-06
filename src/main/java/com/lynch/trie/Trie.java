package com.lynch.trie;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2022/4/6 17:04
 */
public class Trie {
    public static void main(String[] args) {
        Trie trie = new Trie('a');
        trie.insert("a");
        boolean result = trie.startsWith("a");
        boolean success = trie.search("app");// 返回 False
        System.out.println("=====");
    }

    private char value;
    private boolean isEnd = false;
    private Trie[] children = null;

    public Trie(char val){
        this.value = val;
        this.children = new Trie[26];
    }

    public void insert(String word){
        Trie ptr = this;
        for(char item: word.toCharArray()){
            int index = item - 'a';
            if(ptr.children[index]==null){
                ptr.children[index] = new Trie(item);
            }
            ptr = ptr.children[index];
        }
        ptr.isEnd = true;
    }

    public boolean search(String word){
        Trie ptr = this;
        for(char item: word.toCharArray()){
            int index = item - 'a';
            if(ptr.children[index]==null){
                return false;
            }
            ptr = ptr.children[index];
        }
        return ptr != null && ptr.isEnd;
    }

    public boolean startsWith(String prefix){
        Trie ptr = this;
        for(char item: prefix.toCharArray()){
            int index = item - 'a';
            if(ptr.children[index]==null){
                return false;
            }
            ptr = ptr.children[index];
        }
        return ptr != null;
    }
}
