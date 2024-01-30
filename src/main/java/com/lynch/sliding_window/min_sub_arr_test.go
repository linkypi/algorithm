package sliding_window

import (
	"fmt"
	"math"
	"testing"
)

// https://leetcode.cn/problems/shortest-supersequence-lcci
// 假设你有两个数组，一个长一个短，短的元素均不相同。找到长数组中包含短数组所有的元素的最短子数组，其出现顺序无关紧要。
// 返回最短子数组的左端点和右端点，如有多个满足条件的子数组，返回左端点最小的一个。若不存在，返回空数组。
//
// 示例 1:
// 输入:
// big = [7,5,9,0,2,1,3,5,7,9,1,1,5,8,8,9,7]
// small = [1,5,9]
// 输出: [7,10]
func TestMinSubArr(t *testing.T) {
	big := []int{7, 5, 9, 0, 2, 1, 3, 5, 7, 9, 1, 1, 5, 8, 8, 9, 7}
	small := []int{1, 5, 9}
	res := shortestSeq(big, small)
	fmt.Println(res)
}

func shortestSeq(big []int, small []int) []int {
	if big == nil || len(big) == 0 {
		return []int{}
	}
	if small == nil || len(small) == 0 {
		return []int{}
	}

	needed := 0
	smap := make(map[int]int)
	for _, e := range small {
		smap[e] = 0
	}

	start, end := 0, 0
	min := math.MaxInt
	left, right := 0, -1
	for left < len(big) && right < (len(big)-1) {
		//  先扩大窗口 right ++
		right++
		e := big[right]
		if count, ok := smap[e]; ok {
			if count == 0 {
				needed++
			}
			smap[e] = count + 1
		}

		for needed == len(small) {
			if (right-left)+1 < min {
				min = right - left + 1
				start = left
				end = right
			}
			// 从左边收缩窗口
			if count, ok := smap[big[left]]; ok {
				count--
				smap[big[left]] = count
				if count == 0 {
					needed--
				}
			}
			left++
		}
	}

	if end != 0 {
		return []int{start, end}
	}
	return []int{}
}
