package string

import (
	"fmt"
	"math"
	"testing"
)

/**
 * @author: lynch
 * @description:
 * @createTime: 2024/3/1 17:43
 */
func TestManacher(t *testing.T) {

	str := "aa"
	//str := "abcba"
	result := manacher(str)
	fmt.Println(result)
}

func manacher(str string) string {
	if len(str) == 0 {
		return ""
	}

	// 回文覆盖有边界为r, 回文中心为 c
	// 1. 若 i > r 直接暴力扩
	// 2. 若回文中心对称点 2*c-i 的回文半径在大回文区域内，根据对称关系
	//    那么此时i的回文半径为对称点的回文半径，即 p[i] = p[2*c-i]
	// 3. 若回文中心对称点 2*c-i 的回文半径在大回文区域外，那么此时i的回文半径为 r-i
	// 4. 若回文中心对称点 2*c-i 的回文半径刚好与大回文区域边界重合，此时只能继续往前扩

	r, c := 0, 0

	tmp := "#"
	for i := 0; i < len(str); i++ {
		tmp += string(str[i]) + "#"
	}
	index := -1
	max := math.MinInt
	p := make([]int, len(tmp))
	for i := 0; i < len(tmp); i++ {
		p[i] = 1
	}
	for i := 1; i < len(tmp); i++ {
		if i >= r {
			// i-p[i] 为i位置的最左待匹配位置 i+p[i]为i位置最右待匹配位置
			for i-p[i] > -1 && i+p[i] < len(tmp) && tmp[i+p[i]] == tmp[i-p[i]] {
				p[i]++
			}
			c = i
			r = i + p[i]
		} else {
			if p[2*c-i] <= r-i { // 回文i的对称点2c-i处在大回文区域的内， 此时继续往外扩
				p[i] = p[2*c-i]
				// 继续往外扩
				for i-p[i] > -1 && i+p[i] < len(tmp) && tmp[i+p[i]] == tmp[i-p[i]] {
					p[i]++
				}
				c = i
				r = i + p[i]
			} else { // 回文i的对称点2c-i 在大回文区域外
				p[i] = r - i
			}
		}
		if max < p[i] {
			max = p[i]
			index = i
		}
	}

	max--
	start := (index - max) >> 1
	return str[start : start+max]
}
