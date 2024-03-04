package string

import (
	"fmt"
	"testing"
)

/*
*

  - @author: lynch

  - @description:  https://leetcode.cn/problems/number-of-subarrays-that-match-a-pattern-ii/description/
    给你一个下标从 0 开始长度为 n 的整数数组 nums ，和一个下标从 0 开始长度为 m 的整数数组 pattern ，
    pattern 数组只包含整数 -1 ，0 和 1 。

    大小为 m + 1 的
    子数组
    nums[i..j] 如果对于每个元素 pattern[k] 都满足以下条件，那么我们说这个子数组匹配模式数组 pattern ：
    如果 pattern[k] == 1 ，那么 nums[i + k + 1] > nums[i + k]
    如果 pattern[k] == 0 ，那么 nums[i + k + 1] == nums[i + k]
    如果 pattern[k] == -1 ，那么 nums[i + k + 1] < nums[i + k]
    请你返回匹配 pattern 的 nums 子数组的 数目 。

    示例 1：
    输入：nums = [1,2,3,4,5,6], pattern = [1,1]
    输出：4
    解释：模式 [1,1] 说明我们要找的子数组是长度为 3 且严格上升的。在数组 nums 中，
    子数组 [1,2,3] ，[2,3,4] ，[3,4,5] 和 [4,5,6] 都匹配这个模式。
    所以 nums 中总共有 4 个子数组匹配这个模式。

    示例 2：
    输入：nums = [1,4,4,1,3,5,5,3], pattern = [1,0,-1]
    输出：2
    解释：这里，模式数组 [1,0,-1] 说明我们需要找的子数组中，第一个元素小于第二个元素，第二个元素等于第三个元素，
    第三个元素大于第四个元素。在 nums 中，子数组 [1,4,4,1] 和 [3,5,5,3] 都匹配这个模式。
    所以 nums 中总共有 2 个子数组匹配这个模式。

  - @createTime: 2024/3/1 22:45
*/
func TestCountMatchingSubArrays(t *testing.T) {
	//nums := []int{1, 4, 4, 1, 3, 5, 5, 3}
	//pattern := []int{1, 0, -1}

	nums := []int{1, 2, 3, 4, 5, 6}
	pattern := []int{1, 1}
	//count := countMatchingSubarrays2(nums, pattern)
	//count := countMatchingSubarrays3(nums, pattern)
	count := countMatchingSubarrays(nums, pattern)
	fmt.Println(count)
}

func compare(a, b int) int {
	if a == b {
		return 0
	}
	if a > b {
		return 1
	}
	return -1
}
func min(a, b int) int {
	if a >= b {
		return b
	}
	return a
}

func countMatchingSubarrays3(nums, pattern []int) (ans int) {
	m := len(pattern)
	pattern = append(pattern, 2)
	for i := 1; i < len(nums); i++ {
		pattern = append(pattern, compare(nums[i], nums[i-1]))
	}

	n := len(pattern)
	z := make([]int, n)
	l, r := 0, 0
	for i := 1; i < n; i++ {
		if i <= r {
			z[i] = min(z[i-l], r-i+1)
		}
		for i+z[i] < n && pattern[z[i]] == pattern[i+z[i]] {
			l, r = i, i+z[i]
			z[i]++
		}
	}

	for _, lcp := range z[m+1:] {
		if lcp == m {
			ans++
		}
	}
	return
}

// 该题实际是求一个升、降、平序列，可以将nums数组中的数组每个元素做对比得到-1,0,1 序列，再将序列转为特定字符组成的字符串
// 最后再与 pattern 序列转为特定字符串匹配，问题即可转为在字符串str中选择模式串p的问题，可以直接使用KMP算法快速求解
// 另外还可以使用Z算法求解，该方法求解的原理是将模式串p插入到字符串str前面作为一整个字符串的前缀，此后只要在str字符串实际开始
// 的位置去匹配相同前缀，若存在相同前缀那么就说明模式串成功匹配，妙！！！
func countMatchingSubarrays(nums, pattern []int) int {

	start := len(pattern)
	for i := 0; i < len(nums); i++ {
		if i == 0 {
			continue
		}
		delta := nums[i] - nums[i-1]
		n := 0
		if delta > 0 {
			n = 1
		} else if delta < 0 {
			n = -1
		}
		pattern = append(pattern, n)
	}

	// 使用Z算法求解公共前缀
	z := make([]int, len(pattern))
	z[0] = len(pattern)

	count := 0
	l, r := 0, 0
	for i := 1; i < len(pattern); i++ {
		if i > r {
			for i+z[i] < len(pattern) && pattern[i+z[i]] == pattern[z[i]] {
				z[i]++
			}
			l, r = i, i+z[i]-1
		} else if z[i-l] < r-i+1 {
			z[i] = z[i-l]
		} else {
			z[i] = r - i + 1
			for i+z[i] < len(pattern) && pattern[i+z[i]] == pattern[z[i]] {
				z[i]++
			}
			l, r = i, i+z[i]-1
		}
		if i >= start && z[i] >= start {
			count++
		}
	}
	return count
}
