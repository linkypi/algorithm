package double_pointer

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/volume-of-histogram-lcci
// 给定一个直方图 (也称柱状图)，假设有人从上面源源不断地倒水，最后直方图能存多少水量？直方图的宽度为 1。
func TestAmount(t *testing.T) {
	arr := []int{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}
	res := trap(arr)
	fmt.Println(res)
}

func getMin(a, b int) int {
	if a > b {
		return b
	}
	return a
}
func getMax(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func trap(height []int) int {
	if height == nil || len(height) == 0 {
		return 0
	}

	leftMax, rightMax := height[0], height[len(height)-1]
	left, right := 0, len(height)-1
	amount := 0
	for left < right {
		if leftMax < rightMax {
			left++
			if height[left] < leftMax && height[left] < rightMax {
				amount += getMin(leftMax, rightMax) - height[left]
			}
			leftMax = getMax(leftMax, height[left])
		} else {
			right--
			if height[right] < leftMax && height[right] < rightMax {
				amount += getMin(leftMax, rightMax) - height[right]
			}
			rightMax = getMax(rightMax, height[right])
		}
	}
	return amount
}
