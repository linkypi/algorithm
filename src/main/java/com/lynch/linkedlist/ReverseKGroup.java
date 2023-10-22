package com.lynch.linkedlist;

/**
 * https://leetcode.cn/problems/reverse-nodes-in-k-group/description/
 *
 * 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 * k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 *
 * @author: lynch
 * @description:
 * @date: 2023/8/20 18:17
 */
public class ReverseKGroup {

    public class ListNode{
        int val = 0;
        ListNode next;
        public ListNode(int val){
            this.val = val;
        }
    }

    @org.junit.Test
    public void testKGroup(){
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
//        ListNode listNode = reverseKGroup(node1, 2);
        ListNode listNode = reverseKGroup(node1, 3);
        System.out.print("result");
    }

    /**
     * 可以使用两种方案解题：
     * 1. 每遍历k个元素就对该部分元素进行翻转，然后拼接，注意不足k个时需特殊处理，该方式实现代码考虑细节较多
     * 2. 使用栈的方式，入栈k个元素后出栈即为翻转元素，该方式代码较简洁
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(-1);
        ListNode newHead = dummy;
        ListNode start = head;

        int count = 0;

        while (head != null) {
            head = head.next;
            count++;

            if (count == k) {
                ListNode reverse = reverse(start, k);
                dummy.next = reverse;
                while (dummy != null) {
                    dummy = dummy.next;
                    if (dummy.next == null) {
                        break;
                    }
                }

                count = 0;
                start = head;
            }
        }

        if (count > 0) {
            dummy.next = start;
        }

        return newHead.next;
    }

    private ListNode reverse(ListNode start ,int k) {
        ListNode head = new ListNode(-1);

        int count = 1;
        while (start != null && count <= k) {
            ListNode next = start.next;

            ListNode headNext = head.next;
            head.next = start;
            start.next = headNext;

            start = next;
            count ++;
        }
        return head.next;
    }

}
