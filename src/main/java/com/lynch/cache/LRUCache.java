package com.lynch.cache;

import java.util.*;

public class LRUCache {
    public static void main(String[] args) {

        LRUCache cache = new LRUCache(4);
        cache.put("jack", "234");
        cache.put("ert", "56");
        cache.put("leo", "78");
        cache.put("green", "try");

        cache.get("leo");
        cache.get("leo");
        cache.get("jack");

        cache.put("john", "catch");
    }

    static final class LRUCache2 {
        private Map<Integer, LinkNode> map;
        private int size;
        private int capacity;
        private LinkList list;

        public LRUCache2(int capacity) {
            this.capacity = capacity;
            map = new HashMap<>(capacity);
            list = new LinkList(capacity);
        }

        public int get(int key) {
            if(!map.containsKey(key)){
                return -1;
            }
            LinkNode node = map.get(key);
            // 调整到头部
            list.moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            if(!map.containsKey(key)){
                LinkNode node1 = new LinkNode(key, value);
                // 添加到头部
                list.addFirst(node1);
                map.put(key, node1);
                size ++;

                // 检查容量
                if(map.size()>capacity){
                    // 移除尾部节点
                    LinkNode node = list.deleteLast();
                    // 删除map
                    map.remove(node.key);
                    size --;
                }

            }else{
                LinkNode node = map.get(key);
                node.value = value;
                map.put(key, node);
                // 调整到头部
                list.moveToHead(node);
            }
        }

        private static final class LinkList{
            private final int capacity;
            private LinkNode head = new LinkNode(-1,-1);
            private LinkNode tail = new LinkNode(-1,-1);
            public LinkList(int capacity){
                this.capacity = capacity;
                head.next = tail;
                tail.prev = head;
            }

            public void addFirst(LinkNode node) {
                LinkNode next = head.next;
                head.next = node;
                node.prev = head;
                if (next != null) {
                    next.prev = node;
                    node.next = next;
                }
            }

            public void moveToHead(LinkNode node){
                // 先在链表移除再调整到头部
                delete(node);
                addFirst(node);
            }

            public LinkNode deleteLast(){
                LinkNode old = tail.prev;
                LinkNode temp = tail.prev.prev;
                temp.next = tail;
                tail.prev = temp;
                return old;
            }

            public void delete(LinkNode node){
                LinkNode prev = node.prev;
                LinkNode next = node.next;
                if(prev!=null) {
                    prev.next = next;
                }
                if(next!=null) {
                    next.prev = prev;
                }
            }
        }

        private static final class LinkNode{
            private final Integer key;
            private Integer value;
            private LinkNode prev;
            private LinkNode next;
            public LinkNode(int key, int value){
                this.key = key;
                this.value = value;
            }
        }
    }



    // accessOrder = false 默认使用插入顺序，为此可以方便在执行一次put操作就可以将数据标记为最近访问
    private final LinkedHashMap<String, String> cache = new LinkedHashMap<>();
    private int capacity = 32;

    public LRUCache(int cap) {
        this.capacity = cap;
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(String key, String val) {
        if (cache.containsKey(key)) {
            cache.remove(key);
            cache.put(key, val);
            return;
        }

        if (cache.size() == capacity) {
            String oldest = cache.keySet().iterator().next();
            cache.remove(oldest);
        }
        cache.put(key, val);
    }

    private void makeRecently(String key) {
        String value = cache.get(key);
        cache.remove(key);
        cache.put(key, value);
    }
}
