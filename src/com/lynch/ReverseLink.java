package com.lynch;

public class ReverseLink {

    public static void main(String[] args) {
        // write your code here
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node1.print(node1);
        node1.print(node1.reverse(node1));
    }

    public static class Node {

        private int value;
        private Node next;

        public Node(int v) {
            this.value = v;
        }

        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * 寻找中间节点，使用快慢指针，快指针每次走两步
         * 慢指针每次走一步，当快指针为null时，慢指针的位置即为中间节点
         * @param head
         * @return
         */
        public Node FindMiddleNode(Node head){
            Node fast = head, slow = head;
            if(null==fast)
                return null;
            while(fast!=null&&fast.next!=null && fast.next.next!=null){
                fast= fast.next.next;
                slow = slow.next;
            }
            return slow;
        }

        public Node reverse(Node head) {
            Node p1 = head;
            Node p2 = head.next;
            p1.next = null;
            while (p2.next != null) {
                Node p3 = p2.next;
                p2.next = p1;
                p1 = p2;
                p2 = p3;
            }
            p2.next = p1;
            return p2;
        }

        public void print(Node head) {
            String str = "";
            Node temp = head;
            while (temp != null) {
                str += temp.value;
                if (temp.next != null) {
                    str += " -> ";
                }
                temp = temp.next;
            }
            System.out.println(str);
        }
    }
}
