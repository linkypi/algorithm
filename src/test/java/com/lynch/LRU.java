package com.lynch;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/27 16:26
 */
public class LRU {
    public static class Node{
        private Node pre;
        private Node next;
        private int key;
        private int value;

        public Node(){
        }

        public Node(int key, int val) {
            this.value = val;
            this.key = key;
        }
    }

    private Map<Integer, Node> map = new HashMap<>();
    private Node head = new Node(-1, -1);
    private Node tail = new Node(-10000, -10000);

    private int cap = 16;
    private int size = 0;
    public LRU(int cap){
        this.cap = cap;
        head.next = tail;
        tail.pre  = head;
    }

    public int get(int key) {
        if(!map.containsKey(key)){
           return 0;
        }
        // 包含则将当前节点移动到头部
        Node node = map.get(key);
        moveToHead(node);
        return node.value;
    }

    public void removeLast() {

        Node node = tail.pre;
        map.remove(node.key);

        // 移除最后位置
        node.pre.next = tail;
        tail.pre = node.pre;
        node = null;
        size--;
    }

    public void put(int key, int value) {
        // 若不存在则构建新节点放到链表头部
        if(!map.containsKey(key)){
            if(size == cap){
               removeLast();
            }
            Node node = new Node(key, value);
            // 将新节点插到头部
            Node next = head.next;
            head.next = node;
            node.pre  = head;
            node.next = next;
            next.pre  = node;
            map.put(key, node);
            size++;

            return;
        }

        // 若已存在则直接将该节点放到头部，同时调整map映射关系
        Node node = map.get(key);
        moveToHead(node);
    }

    private void moveToHead(Node node) {
        // 移除节点原来位置
        if(head.next != node) {
            Node pre = node.pre;
            pre.next = node.next;
            node.next.pre = pre;
        }

        // 将新节点插到头部
        Node next = head.next;
        head.next = node;
        node.pre = head;
        node.next = next;
        next.pre  = node;
    }
}
