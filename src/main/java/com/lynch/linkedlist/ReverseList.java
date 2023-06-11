package com.lynch.linkedlist;

/**
 * 反转链表
 */
public class ReverseList {
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
        Node reverse = reverseList(node1);
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

    private static Node reverseList(Node head){
        Node pre = null;

        while (head!=null){
            // 临时保存下一个节点
            Node next = head.next;
            // 当前节点指向新节点
            head.next = pre;
            // 保存反转后的链表，方便反转下个元素时对接
            pre = head;
            // 将头节点改为下一个节点继续遍历
            head = next;
        }
        return pre;
    }

    /**
     * 使用递归的方式反转链表
     * @param head
     * @return
     */
    private static Node reverseListUseRecursive(Node head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node last = reverseListUseRecursive(head.next);
        head.next.next = head;
        head.next = null;
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
