/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-01-26 09:50:47
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-26 11:34:23
 * @FilePath: /algorithm/src/main/java/com/lynch/binary_tree/search-in-rotated-sorted-array_test.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package binary_tree

import (
	"fmt"
	"testing"
)

/*
* https://leetcode.cn/problems/search-in-rotated-sorted-array/description/

整数数组 nums 按升序排列，数组中的值 互不相同 。
在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行旋转，
使数组变为 [nums[k], nums[k+1], ...,nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。

给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。
你必须设计一个时间复杂度为 O(log n) 的算法解决此问题
*/
func TestFindTarget(t *testing.T) {
	arr := []int{4, 5, 6, 7, 0, 1, 2, 3}
	fmt.Println(arr)
	index := find(arr, 3)

	// arr := []int{4, 5, 6, 7, 0, 1, 2}
	// fmt.Println(arr)
	// index := find(arr, 5)
	fmt.Println(index)
}

func find(arr []int, target int) int {
	// 由于数组仅仅旋转了一次, 所有整个数组必定存在两端升序的序列
	// 首先找到他们的分界点 x, 可通过二分的方式对比 arr[0] 快速得到 x
	index := 0

	left := 0
	right := len(arr) - 1
	mid := 0
	for left < right {
		mid = (left + right) / 2
		if arr[mid] > arr[0] {
			left = mid + 1
		} else {
			right = mid - 1
		}
	}
	index = left

	res := binSch(arr, target, index, len(arr)-1)
	if res != -1 {
		return res
	}
	return binSch(arr, target, 0, index-1)
}

func binSch(arr []int, target, start, end int) int {
	left := start
	right := end
	for left <= right {
		mid := left + (right-left)>>1
		if arr[mid] == target {
			return mid
		}
		if arr[mid] > target {
			right = mid - 1
		} else {
			left = mid + 1
		}
	}
	return -1
}
