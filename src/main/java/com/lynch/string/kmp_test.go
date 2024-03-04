package string

import (
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description: https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/
   给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
   如果 needle 不是 haystack 的一部分，则返回  -1 。
   示例 1：

	输入：haystack = "sadbutsad", needle = "sad"
	输出：0
	解释："sad" 在下标 0 和 6 处匹配。
	第一个匹配项的下标是 0 ，所以返回 0 。
 * @createTime: 2024/2/29 14:37
*/
func TestKMP(t *testing.T) {

	//str := "eragababanhmynmbabaqbxuababanmbaba"
	//p := "ababanmbaba"

	//str := "agababhabaxyzaba"
	//p := "abaxyzaba"

	//str := "mississippi"
	//p := "issip"

	str := "aabaaabaaac"
	p := "aabaaac"

	result := kmp(str, p)
	fmt.Println("index:", result)
	if result > -1 {
		res := str[result : result+len(p)]
		fmt.Println("match: ", res == p)
	} else {
		fmt.Println("match: ", false)
	}
}

func kmp(str, pattern string) int {
	if str == "" || pattern == "" {
		return -1
	}
	// next[i] 表示以i-1为止结尾的字符串，其前后缀的最长公共长度
	next := make([]int, len(pattern)+1)

	next[0] = -1
	next[1] = 0
	k := 0
	i := 2
	for i < len(next) {
		// 第 k 个字符与第 i-1 个字符匹配，那么公共前后缀长度+1
		if k == -1 || pattern[k] == pattern[i-1] {
			k++
			next[i] = k
			i++
		} else {
			// 第 k 个字符与第 i-1 个字符匹配 那么进行回退，逐渐缩小比较范围
			k = next[k]
		}
	}

	fmt.Println("next: ", next)

	i, j := 0, 0
	for i < len(str) {
		if str[i] == pattern[j] {
			i++
			j++
			// 模式串已完全匹配，直接返回起始位置下标
			if j >= len(pattern) {
				return i - len(pattern)
			}
			continue
		}

		// 模式串已无法匹配当前以i结尾的字符串，那么只能往下开始匹配
		if next[j] == -1 {
			i++
			continue
		}
		// 字符不匹配则回退到公共前后缀的下一个为止进行匹配
		j = next[j]
	}

	return -1
}
