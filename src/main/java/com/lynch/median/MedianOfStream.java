package com.lynch.median;

import org.junit.Test;

import java.util.PriorityQueue;

/**
 * https://leetcode.cn/problems/find-median-from-data-stream/
 *
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 *
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 *
 * MedianFinder() 初始化 MedianFinder 对象。
 *
 * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
 *
 * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10-5 以内的答案将被接受。
 *
 * @Author: linxueqi
 * @Description:
 * @Date: create in 2023/8/14 17:29
 */
public class MedianOfStream {

    @Test
    public void test(){
        addNum(1);
        addNum(2);
        addNum(3);
        double median = findMedian();
        System.out.println("median : "+ median);
    }

    /**
     * 为了快速找到数据的中位数： 1,2,3,..,mid,..7,8,9
     * 通常做法是对数据进行排序后再求中间位置的数
     * 但是每次获取中位数时都需要做一次排序，复杂度过高
     * 通过中位数定义可知，实际仅需维护一个 大根堆(对应 1,2,3...,mid)
     * 与 小根堆(mid+1...7,8,9)即可，获取中位数仅需根据奇偶总数从两个堆中取出堆顶元素即可
     * 同时还必须满足条件：
     *  1. 大根堆的堆顶元素必须小于等于小根堆的堆顶元素
     *  2. 大小根堆的长度相差不能大于 1
     */

    private PriorityQueue<Integer> small = new PriorityQueue<>();
    private PriorityQueue<Integer> bigger = new PriorityQueue<>((a,b)->b-a);
    public MedianOfStream() {
    }

    public void addNum(int num) {
        if(bigger.size() >= small.size()){
            bigger.offer(num);
            small.offer(bigger.poll());
        }else{
            // 小于等于
            small.offer(num);
            bigger.offer(small.poll());
        }
    }

    public double findMedian() {
        int left = bigger.size();
        int right = small.size();
        if (left == 0 && right == 0) {
            return 0;
        }
        if ((left + right) % 2 == 0) {
            return (bigger.peek() + small.peek()) / 2.0;
        }
        if (left > right) {
            return bigger.peek();
        }
        return small.peek();
    }

}
