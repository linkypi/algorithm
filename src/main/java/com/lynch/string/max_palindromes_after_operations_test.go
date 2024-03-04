package string

import (
	"fmt"
	"sort"
	"testing"
)

/*
*

  - @author: lynch

  - @description: https://leetcode.cn/problems/maximum-palindromes-after-operations/
    给你一个下标从 0 开始的字符串数组 words ，数组的长度为 n ，且包含下标从 0 开始的若干字符串。
    你可以执行以下操作 任意 次数（包括零次）：
    选择整数 i、j、x 和 y，满足 0 <= i, j < n，0 <= x < words[i].length，0 <= y < words[j].length，
    交换 字符 words[i][x] 和 words[j][y] 。返回一个整数，表示在执行一些操作后，words 中可以包含的回文字符串的 最大 数量。
    注意：在操作过程中，i 和 j 可以相等。

    示例 1：

    输入：words = ["abbb","ba","aa"]
    输出：3
    解释：在这个例子中，获得最多回文字符串的一种方式是：
    选择 i = 0, j = 1, x = 0, y = 0，交换 words[0][0] 和 words[1][0] 。words 变成了 ["bbbb","aa","aa"] 。
    words 中的所有字符串都是回文。
    因此，可实现的回文字符串的最大数量是 3 。

    示例 2：

    输入：words = ["abc","ab"]
    输出：2
    解释：在这个例子中，获得最多回文字符串的一种方式是：
    选择 i = 0, j = 1, x = 1, y = 0，交换 words[0][1] 和 words[1][0] 。words 变成了 ["aac","bb"] 。
    选择 i = 0, j = 0, x = 1, y = 2，交换 words[0][1] 和 words[0][2] 。words 变成了 ["aca","bb"] 。
    两个字符串都是回文 。
    因此，可实现的回文字符串的最大数量是 2。

  - @createTime: 2024/3/2 11:13
*/
func TestMaxPalindromesAfterOperations(t *testing.T) {
	words := []string{"abbb", "ba", "aa"}
	count := maxPalindromesAfterOperations(words)
	fmt.Println(count)
}

func maxPalindromesAfterOperations(words []string) int {
	mp := make(map[int32]int)
	for _, word := range words {
		for _, b := range word {
			mp[b]++
		}
	}

	left := 0 // 统计回文串左边字符总数
	for _, v := range mp {
		left += v >> 1
	}

	// 按单词长度由小到大排列
	sort.Slice(words, func(i, j int) bool {
		return len(words[i]) < len(words[j])
	})

	count := 0
	// 按顺序填充回文串左半部分，根据对称关系右半部分自然也就填充完成
	for _, word := range words {
		c := len(word) >> 1
		if left < c {
			break
		}
		left -= c
		count++
	}
	return count
}
