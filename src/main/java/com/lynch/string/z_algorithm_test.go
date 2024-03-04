package string

import (
	"fmt"
	"testing"
)

/**
* @author: lynch
* @description: 给定一个字符串S, 求解数组 Z[i], Z[i]表示从i开始到S末尾组成
                的字符串与原始字符串S所拥有的最长公共前缀的长度
 Z 函数的模拟过程 https://personal.utdallas.edu/~besp/demo/John2010/z-algorithm.htm
* @createTime: 2024/3/1 15:44
*/

func TestZ(t *testing.T) {
	str := "aabcaabcaaaab"
	arr := getZArr(str)
	fmt.Println(arr)
	arr = getZArrOptimize(str)
	fmt.Println(arr)
}

func getZArr(str string) []int {
	if str == "" {
		return []int{}
	}

	z := make([]int, len(str))

	// z[i]表示从i开始到S末尾组成的字符串与原始字符串S所拥有的最长公共前缀的长度
	// 假设 z[i] != 0 那么我们定义区间 [i, i+z[i]-1]  为一个 Z-Box
	// 假设 l 为box的左端点，r 为box的右端点，则有 l <= i <= r
	// 接下来就需要分情况去求解 z[i]
	// 1. 若 i > r, 如在起始位置，此时只能暴力逐个求解
	// 2. 若 l <= i <= r, 因为 S[l...r] 等于 S[0...r-l] 那么 S[i..r] 必然与 S[i-l...r-l]相等
	//    即z-box从i开始到r的后半部分与原始串从x位置到r-l为止部分相等，因为 r-l+1 为z-box长度，换做S起始位置对应的就是[0...r-l]
	//    那么其后半部分即 x = r-l-(r-i) = i-l 也就是r-l位置减去相同距离r-i的后半部分即可得到起始位置为 i-l, 也即  S[i-l...r-l]
	//    得知  S[i..r] 必然与 S[i-l...r-l]相等后，那么实际z[i]就可以通过z[i-l]求解，即 z[i]=z[i-l]，前提是 z[i-l] < r-i+1
	//    即 z[i-l]小于当前z-box的长度：  z[i]=z[i-l] ( z[i-l] < r-i+1 ).
	//    若 z[i-l] >= r-i+1 即公共长度已经超出z-box，那么超出部分只能暴力求解

	l, r := 0, 0
	z[0] = len(str)
	for i := 1; i < len(str); i++ {

		if i > r {
			for i+z[i] < len(str) && str[z[i]] == str[i+z[i]] {
				z[i]++
			}
			l = i
			r = i + z[i] - 1
		} else if z[i-l] < r-i+1 {
			z[i] = z[i-l]
		} else {
			// z[i-l] >= r-i+1 说明z-box 后面部分全部可以匹配，但z-box以外的只能暴力求解
			z[i] = r - i + 1
			for i+z[i] < len(str) && str[z[i]] == str[i+z[i]] {
				z[i]++
			}
			l = i
			r = i + z[i] - 1
		}
	}
	return z
}

func min2(a, b int) int {
	if a >= b {
		return b
	}
	return a
}

// 该方法是对 getZArr 的优化
func getZArrOptimize(str string) []int {
	if str == "" {
		return []int{}
	}

	z := make([]int, len(str))
	z[0] = len(str)
	l, r := 0, 0
	for i := 1; i < len(str); i++ {
		if i <= r { // 在z-box范围内
			// 比较对称点与当前i在box内可以扩到的范围，取两者最小值，超出的部分只能暴力求解
			z[i] = min2(z[i-l], r-i+1)
		}

		for i+z[i] < len(str) && str[i+z[i]] == str[z[i]] {
			z[i]++
		}
		l = i
		r = i + z[i] - 1
	}
	return z
}
