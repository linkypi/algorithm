package main

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/shu-zu-zhong-de-ni-xu-dui-lcof/
// 在股票交易中，如果前一天的股价高于后一天的股价，则可以认为存在一个「交易逆序对」。
// 请设计一个程序，输入一段时间内的股票交易记录 record，返回其中存在的「交易逆序对」总数。
//
// 示例 1:
// 输入：record = [9, 7, 5, 4, 6]
// 输出：8
// 解释：交易中的逆序对为 (9, 7), (9, 5), (9, 4), (9, 6), (7, 5), (7, 4), (7, 6), (5, 4)。
//
// 限制：0 <= record.length <= 50000
func TestReversePairs(t *testing.T) {
	arr := []int{9, 7, 5, 4, 6}
	count := reversePairs(arr)
	fmt.Println(count)
}

func reversePairs(arr []int) int {
	if arr == nil || len(arr) == 0 {
		return 0
	}
	// 由于 arr 最大长度为50000，为了防止内存溢出，故需定义一个全局临时数组，而非每次使用时都重新创建，以免内存超出限制
	temp := make([]int, len(arr))
	count := 0
	sort(arr, 0, len(arr)-1, &count, temp)
	return count
}

func sort(arr []int, low, high int, count *int, temp []int) {
	if low == high {
		return
	}
	mid := (low + high) >> 1
	sort(arr, low, mid, count, temp)
	sort(arr, mid+1, high, count, temp)
	merge(arr, low, mid, high, count, temp)
}

func merge(arr []int, low, mid, high int, count *int, temp []int) {
	for i := low; i < high+1; i++ {
		temp[i] = arr[i]
	}
	end := mid + 1
	for i := low; i < mid+1; i++ {
		for end <= high && temp[i] > temp[end] {
			end++
		}
		*count += end - (mid + 1)
	}

	i := low
	j := mid + 1
	for p := low; p < high+1; p++ {
		if i == mid+1 { // 左边已合并完成，剩下右边
			arr[p] = temp[j]
			j++
		} else if j == high+1 { // 右边已合并完成，剩下左边
			arr[p] = temp[i]
			i++
		} else if temp[i] < temp[j] {
			arr[p] = temp[i]
			i++
		} else {
			arr[p] = temp[j]
			j++
		}
	}
}
