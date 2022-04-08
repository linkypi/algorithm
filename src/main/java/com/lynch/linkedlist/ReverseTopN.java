package com.lynch.linkedlist;

/**
 * 反转链表前 N 个节点
 */
public class ReverseTopN {
    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);

        print("before reverse: ", node1);
        Node reverse = reverseTopN(node1, 2);
        print("after reverse: ", reverse);
    }

    private static void print(String msg, Node head){
        StringBuilder builder = new StringBuilder();
        while (head!=null){
            builder.append(head.value).append(" -> ");
            head = head.next;
        }
        builder.append("null");
        System.out.println(msg + builder);
    }

    private static Node successor = null;
    private static Node reverseTopN(Node head, int n){
        if(n == 1 ){
            // 记录下后继节点
            successor = head.next;
            return head;
        }
        Node last = reverseTopN(head.next, n -1);
        head.next.next = head;
        head.next = successor;
        return last;
    }

    private static class Node{
        int value;
        Node next;

        public Node(int v){
            this.value = v;
        }

        public void setNext(Node node){
            this.next = node;
        }
    }
}
