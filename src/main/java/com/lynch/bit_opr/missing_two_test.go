package bit_opr

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/missing-two-lcci
// 给定一个数组，包含从 1 到 N 所有的整数，但其中缺了两个数字。
// 你能在 O (N) 时间内只用 O (1) 的空间找到它们吗？
// 以任意顺序返回这两个数字均可。
//
// 示例 1:
// 输入: [1]
// 输出: [2,3]
// 示例 2:
// 输入: [2,3]
// 输出: [1,4]
// 提示：
// nums.length <= 30000
func TestMissingTwo(t *testing.T) {
	//nums := []int{1, 5, 2}
	nums := []int{1}
	//nums := []int{2}
	res := missingTwo(nums)
	fmt.Println(res)
}

func missingTwo(nums []int) []int {
	if nums == nil || len(nums) == 0 {
		return []int{}
	}

	n := len(nums) + 2
	xor := 0
	for _, e := range nums {
		xor = xor ^ e
	}

	for i := 1; i <= n; i++ {
		xor = xor ^ i
	}

	rightOne := xor & (-xor)
	part1 := 0
	part2 := 0
	for _, e := range nums {
		if rightOne&e == 0 {
			part1 = part1 ^ e
		} else {
			part2 = part2 ^ e
		}
	}

	for i := 1; i <= n; i++ {
		if rightOne&i == 0 {
			part1 = part1 ^ i
		} else {
			part2 = part2 ^ i
		}
	}
	return []int{part1, part2}
}
