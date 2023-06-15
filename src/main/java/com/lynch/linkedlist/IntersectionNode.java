package com.lynch.linkedlist;

import java.util.LinkedHashMap;

/**
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/3/16 15:35
 */
public class IntersectionNode {

    public static void main(String[] args) {

        ListNode a = new ListNode(6);
        ListNode a2 = new ListNode(8);
        ListNode c = new ListNode(5);
        ListNode c1 = new ListNode(11);
        ListNode c2 = new ListNode(12);

        a.next = a2;
        a2.next = c;
        c.next =c1;
        c1.next =c2;

        ListNode b = new ListNode(1);
        ListNode b1 = new ListNode(2);
        ListNode b2 = new ListNode(3);

        b.next = b1;
        b1.next = b2;
//        b2.next = c;

        ListNode result = getIntersectionNode(a, b);
        System.out.println("result: "+ result.val);
    }

    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;

        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        return p1;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
