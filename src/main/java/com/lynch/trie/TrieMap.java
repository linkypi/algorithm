package com.lynch.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/11 15:57
 */
public class TrieMap<V> {

    public static void main(String[] args) {
        TrieMap<Integer> trieMap = new TrieMap();
        trieMap.put("abc", 12);
        trieMap.put("abht", 54);
        trieMap.put("bcd", 87);
        trieMap.remove("abc");
        boolean abc = trieMap.hasKeyWithPattern("abc");
        System.out.println("");
        abc= trieMap.hasKeyWithPrefix("abc");
        System.out.println("");

        trieMap.put("abk", 54);
        trieMap.put("abm", 24);
        List<String> list = trieMap.keysWithPrefix("ab");
        boolean has = trieMap.hasKeyWithPattern("abm");
        List<String> list2 = trieMap.keysWithPattern("abm");

        String shortestPrefix = trieMap.shortestPrefixOf("abk");

        System.out.println("");
    }

    /**
     * ascii 个数
     */
    public static int COUNT = 128;
    // 键值对数量
    private int size = 0;

    private static class TrieNode<V> {
        V val = null;
        TrieNode[] children = new TrieNode[COUNT];
    }

    private TrieNode<V> root = null;

    /***** 增/改 *****/

    // 在 Map 中添加 key
    public void put(String key, V val) {
        if (!containsKey(key)) {
            size++;
        }

        if(root == null) {
            root = new TrieNode<>();
            root.children = new TrieNode[COUNT];
        }
        TrieNode<V> node = getNodeWithPrefix(root, key);
        if (node == null) {
            node = new TrieNode<>();
            node.val = val;
        }

        TrieNode pre = root;
        for (int i = 0; i < key.length(); i++) {
            TrieNode child = pre.children[key.charAt(i)];
            if (child != null) {
                pre = child;
            } else {
                TrieNode nn = new TrieNode();
                pre.children[key.charAt(i)] = nn;
                pre = nn;
                if(i == key.length()-1){
                    nn.val = val;
                }
            }
        }

    }

    /***** 删 *****/
    // 删除键 key 以及对应的值
    public void remove(String key) {
        if (!containsKey(key)) {
            return;
        }

        remove(root, key, 0);
    }

    /**
     * 递归删除
     * @param node
     * @param key
     * @param i
     */
    private boolean remove(TrieNode node, String key, int i) {
        if (node == null) {
            return true;
        }

        if (i == key.length()) {
            node.val = null;
            return true;
        }
        //查看其子后续节点是否存在, 若存在则删除
        char charAt = key.charAt(i);
        TrieNode child = node.children[charAt];
        if (child == null) {
            return true;
        }

        // char 子节点不为空，递归删除
        boolean canRemove = remove(child, key, i + 1);
        if (canRemove) {
            child.children[charAt] = null;
            boolean rv = true;
            // 查看 child 所有子节点是否已移除，若已移除则删除当前 child 节点
            for (char j = 0; j < COUNT; j++) {
                if (child.children[j] != null) {
                    rv = false;
                }
            }
            if (rv) {
                node.children[charAt] = null;
            }
        }

        // 查看 node 所有子节点是否已移除，若已移除则删除当前 node 节点
        for (char j = 0; j < COUNT; j++) {
            if (node.children[j] != null) {
                return false;
            }
        }
        child.children = null;
        return true;
    }

    /***** 查 *****/
    // 搜索 key 对应的值，不存在则返回 null
    // get("the") -> 4
    // get("tha") -> null
    public V get(String key) {
        TrieNode<V> node = getNodeWithPrefix(root, key);
        if (node != null && node.val != null) {
            return node.val;
        }
        return null;
    }

    private TrieNode<V> getNodeWithPrefix(TrieNode<V> root, String key) {
        if (root == null) {
            return null;
        }
        TrieNode p = root;
        for (int i = 0; i < key.length(); i++) {

            if (p == null) {
                return null;
            }
            char c = key.charAt(i);
            p = p.children[c];
        }
        return p;
    }

    // 判断 key 是否存在在 Map 中
    // containsKey("tea") -> false
    // containsKey("team") -> true
    public boolean containsKey(String key) {
        return get(key) != null;
    }

    // 在 Map 的所有键中搜索 query 的最短前缀
    // shortestPrefixOf("themxyz") -> "the"
    public String shortestPrefixOf(String query) {
        TrieNode<V> p = root;

        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                return "";
            }
            if (p.val != null) {
                return query.substring(0, i);
            }
            char ch = query.charAt(i);
            p = p.children[ch];
        }
        if (p != null && p.val != null) {
            return query;
        }
        return "";
    }

    // 在 Map 的所有键中搜索 query 的最长前缀
    // longestPrefixOf("themxyz") -> "them"
    public String longestPrefixOf(String query) {
        TrieNode<V> p = root;

        int max = 0;
        for (int i = 0; i < query.length(); i++) {
            if (p == null) {
                break;
            }
            if (p.val != null) {
                max = i;
            }
            char ch = query.charAt(i);
            p = p.children[ch];
        }
        if (p != null && p.val != null) {
            return query;
        }
        return query.substring(0, max);
    }

    // 搜索所有前缀为 prefix 的键
    // keysWithPrefix("th") -> ["that", "the", "them"]
    public List<String> keysWithPrefix(String prefix) {
        TrieNode<V> node = getNodeWithPrefix(root, prefix);
        if (node == null) {
            return new ArrayList<>();
        }

        List<String> res = new ArrayList<>();
        traverse(node, res, new StringBuilder(prefix));

        return res;
    }

    private void traverse(TrieNode<V> root, List<String> res, StringBuilder path) {
        if (root == null) {
            return;
        }
        if (root.val != null) {
            res.add(path.toString());
        }
        for (char c = 0; c < COUNT; c++) {
            path.append(c);
            traverse(root.children[c], res, path);
            path.deleteCharAt(path.length() - 1);
        }
    }

    // 判断是和否存在前缀为 prefix 的键
    // hasKeyWithPrefix("tha") -> true
    // hasKeyWithPrefix("apple") -> false
    public boolean hasKeyWithPrefix(String prefix) {
        return getNodeWithPrefix(root, prefix) != null;
    }

    // 通配符 . 匹配任意字符，搜索所有匹配的键
    // keysWithPattern("t.a.") -> ["team", "that"]
    public List<String> keysWithPattern(String pattern) {
        List<String> res = new ArrayList<>();
        traverse(root, pattern, new StringBuilder(), 0, res);
        return res;
    }

    private void traverse(TrieNode<V> node, String pattern, StringBuilder path, int i, List<String> res) {
        if (node == null) {
            return;
        }

        // 已遍历结束
        if (i == pattern.length()) {
            if (node.val != null) {
                res.add(path.toString());
            }
            return;
        }

        char charAt = pattern.charAt(i);
        if (charAt == '.') { // t.e.x
            for (char j = 0; j < COUNT; j++) {
                path.append(j);
                traverse(node.children[charAt], pattern, path, i + 1, res);
                path.deleteCharAt(path.length() - 1);
            }
        } else {
            path.append(charAt);
            traverse(node.children[charAt], pattern, path, i + 1, res);
            path.deleteCharAt(path.length() - 1);
        }

    }

    // 通配符 . 匹配任意字符，判断是否存在匹配的键
    // hasKeyWithPattern(".ip") -> true
    // hasKeyWithPattern(".i") -> false
    public boolean hasKeyWithPattern(String pattern) {
        return hasKeyWithPattern(root, pattern, 0);
    }

    private boolean hasKeyWithPattern(TrieNode<V> node, String pattern, int i) {
        if (node == null) {
            return false;
        }
        if (i == pattern.length()) {
            return node.val != null;
        }

        char charAt = pattern.charAt(i);
        if (charAt != '.') {
            return hasKeyWithPattern(node.children[charAt], pattern, i + 1);
        }

        // t.e.x
        for (char j = 0; j < COUNT; j++) {
            if (hasKeyWithPattern(node.children[j], pattern, i + 1)) {
                return true;
            }
        }

        return false;
    }

    // 返回 Map 中键值对的数量
    public int size() {
        return size;
    }
}
