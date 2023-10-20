package com.lynch.random;

import org.junit.Test;

import java.util.Random;

/**
 * https://leetcode.cn/problems/linked-list-random-node/description/
 *
 * 给你一个单链表，随机选择链表的一个节点，并返回相应的节点值。每个节点 被选中的概率一样 。
 *
 * 实现 Solution 类：
 *
 * Solution(ListNode head) 使用整数数组初始化对象。
 * int getRandom() 从链表中随机选择一个节点并返回该节点的值。链表中所有节点被选中的概率相等。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/10/20 16:14
 */
public class RandomListNode {

    @Test
    public void test(){
        ListNode listNode = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        listNode.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        Solution solution = new Solution(listNode);
        int random = solution.getRandom();
        System.out.println("result: "+ random);
        random = solution.getRandom();
        System.out.println("result: "+ random);
    }

    public class Solution {
        private ListNode head;
        Random random = new Random();

        public Solution(ListNode head) {
            this.head = head;
        }

        /**
         * 数据量不大的情况下可以将链表转为数组后直接使用随机函数求得结果
         *
         * @return
         */
        public int getRandom() {
            int n = 0;
            ListNode temp = head;
            while (head != null) {
                head = head.next;
                n++;
            }
            int[] arr = new int[n];
            int i = 0;
            while (temp != null) {
                arr[i] = temp.val;
                temp = temp.next;
                i++;
            }
            return arr[random.nextInt(n)];
        }

        /**
         * 使用水塘抽样方法获取
         * @return
         */
        public int getRandom2() {
            int i = 1, ans = 0;
            for (ListNode node = head; node != null; node = node.next) {
                if (random.nextInt(i) == 0) { // 1/i 的概率选中（替换为答案）
                    ans = node.val;
                }
                ++i;
            }
            return ans;
        }
    }

    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

}
