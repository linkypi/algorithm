package suffix_array

import (
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description: 倍增法实现 https://oi-wiki.org/string/sa/#sa-is
 * @createTime: 2024/3/3 13:40
 */

func TestDoubling(t *testing.T) {

	str := "abaea" // [5,1,3,2,4]
	//str := "oapomnheygxk" // [2 8 10 7 12 5 6 1 4 3 11 9]
	//str := "amoexlpqixyebksgxq" // [1 13 12 4 16 9 14 6 2 3 7 18 8 15 5 17 10 11]
	n := len(str)
	bytes := make([]byte, n)
	for i, char := range str {
		bytes[i] = byte(char - 'a')
	}
	sa1 := doublingOptimize(bytes)
	sa2 := doubling(bytes)
	sa := myDoubling(bytes)
	sa3 := myDoublingOptimize(bytes)
	fmt.Println("         my:\t", sa)
	fmt.Println("     my opt:\t", sa3)
	fmt.Println(" real unopt:\t", sa2)
	fmt.Println("   real opt:\t", sa1)

}

const N = 1000010

// 基于 https://oi-wiki.org/string/sa/#sa-is 自我实现
// sa[i] 表示排在第i名的子串所在位置
// rk[i] 表示从i位置到字符串末尾所组成的后缀的实际排名
// height[i] 表示以 i 位置开头的后缀以及以 i-1 开头的后缀 的公共前缀长度
func myDoubling(data []byte) []int {
	n := len(data)
	// 首先对每个元素进行排名
	var sa, rk, oldRK, count [N]int
	max := 128 // 注意 max 必须在指定范围内，以便基数排序不会出错
	for i := 1; i <= n; i++ {
		count[data[i-1]]++
		oldRK[i] = int(data[i-1])
	}

	for i := 1; i <= max; i++ {
		count[i] += count[i-1]
	}
	for i := n; i > 0; i-- {
		sa[count[data[i-1]]] = i
		count[data[i-1]]--
	}

	// 判断相邻两个排名的数据是否相同，如果相同那么他们的排名一致
	p := 0
	for i := 1; i <= n; i++ {
		// sa[i] 表示排名所在索引，oldRK[sa[i]] 即为实际数据
		if oldRK[sa[i]] == oldRK[sa[i-1]] {
			rk[sa[i]] = p
		} else {
			p++
			rk[sa[i]] = p
		}
	}

	for k := 1; k < n; k = k << 1 {
		// 清空count
		for i := 1; i <= n; i++ {
			count[i] = 0
		}
		// 对第二个关键字排序
		var oldSA [N]int // 保存原始sa，防止生成新的sa后被覆盖
		for i := 1; i <= n; i++ {
			oldSA[i] = sa[i]
		}
		for i := 1; i <= n; i++ {
			count[rk[oldSA[i]+k]]++
		}
		for i := 1; i <= max; i++ {
			count[i] += count[i-1]
		}
		for i := n; i > 0; i-- {
			sa[count[rk[oldSA[i]+k]]] = oldSA[i]
			count[rk[oldSA[i]+k]]--
		}

		// 清空count
		for i := 1; i <= n; i++ {
			count[i] = 0
		}
		// 对第一个关键字排序
		for i := 1; i <= n; i++ {
			oldSA[i] = sa[i]
		}
		for i := 1; i <= n; i++ {
			count[rk[oldSA[i]]]++
		}
		for i := 1; i <= max; i++ {
			count[i] += count[i-1]
		}
		for i := n; i > 0; i-- {
			sa[count[rk[oldSA[i]]]] = oldSA[i]
			count[rk[oldSA[i]]]--
		}
		// 重新调整排名，调整完成后检查是否存在重复排名，若没有重复排名则直接返回结果
		var oldRK [N]int // 保存原始rk，防止生成新的rk后被覆盖
		for i := 1; i <= n; i++ {
			oldRK[i] = rk[i]
		}
		p := 0
		for i := 1; i <= n; i++ {
			// 倍增后，判断相邻两个排名的区域的数据是否相同，如果相同那么他们排名应该相同
			if oldRK[sa[i]] == oldRK[sa[i-1]] && oldRK[sa[i]+k] == oldRK[sa[i-1]+k] {
				rk[sa[i]] = p
			} else {
				p++
				rk[sa[i]] = p
			}
		}
		if p == n {
			break
		}
	}
	return sa[1 : n+1]
}

// todo 待优化
func myDoublingOptimize(data []byte) []int {
	n := len(data)
	// 首先对每个元素进行排名
	var sa, rk, oldRK, count [N]int
	max := 128 // 注意 max 必须在指定范围内，以便基数排序不会出错
	for i := 1; i <= n; i++ {
		count[data[i-1]]++
		oldRK[i] = int(data[i-1])
	}

	for i := 1; i <= max; i++ {
		count[i] += count[i-1]
	}
	for i := n; i > 0; i-- {
		sa[count[data[i-1]]] = i
		count[data[i-1]]--
	}

	// 判断相邻两个排名的数据是否相同，如果相同那么他们的排名一致
	p := 0
	for i := 1; i <= n; i++ {
		// sa[i] 表示排名所在索引，oldRK[sa[i]] 即为实际数据
		if oldRK[sa[i]] == oldRK[sa[i-1]] {
			rk[sa[i]] = p
		} else {
			p++
			rk[sa[i]] = p
		}
	}

	for k := 1; k < n; k = k << 1 {
		// 对第二个关键字排序
		var oldSA [N]int // 保存原始sa，防止生成新的sa后被覆盖
		for i := 1; i <= n; i++ {
			oldSA[i] = sa[i]
		}

		for i := 1; i <= n; i++ {
			sa[i] = oldSA[i+k]
		}

		// 清空count
		for i := 1; i <= n; i++ {
			count[i] = 0
		}
		// 对第二个关键字排序
		//var oldSA [N]int // 保存原始sa，防止生成新的sa后被覆盖
		//for i := 1; i <= n; i++ {
		//	oldSA[i] = sa[i]
		//}
		//for i := 1; i <= n; i++ {
		//	count[rk[oldSA[i]+k]]++
		//}
		//for i := 1; i <= max; i++ {
		//	count[i] += count[i-1]
		//}
		//for i := n; i > 0; i-- {
		//	sa[count[rk[oldSA[i]+k]]] = oldSA[i]
		//	count[rk[oldSA[i]+k]]--
		//}

		// 清空count
		for i := 1; i <= n; i++ {
			count[i] = 0
		}
		// 对第一个关键字排序
		for i := 1; i <= n; i++ {
			oldSA[i] = sa[i]
		}
		for i := 1; i <= n; i++ {
			count[rk[oldSA[i]]]++
		}
		for i := 1; i <= max; i++ {
			count[i] += count[i-1]
		}
		for i := n; i > 0; i-- {
			sa[count[rk[oldSA[i]]]] = oldSA[i]
			count[rk[oldSA[i]]]--
		}
		// 重新调整排名，调整完成后检查是否存在重复排名，若没有重复排名则直接返回结果
		var oldRK [N]int // 保存原始rk，防止生成新的rk后被覆盖
		for i := 1; i <= n; i++ {
			oldRK[i] = rk[i]
		}
		p := 0
		for i := 1; i <= n; i++ {
			// 倍增后，判断相邻两个排名的区域的数据是否相同，如果相同那么他们排名应该相同
			if oldRK[sa[i]] == oldRK[sa[i-1]] && oldRK[sa[i]+k] == oldRK[sa[i-1]+k] {
				rk[sa[i]] = p
			} else {
				p++
				rk[sa[i]] = p
			}
		}
		if p == n {
			break
		}
	}
	return sa[1 : n+1]
}

// 来自 https://oi-wiki.org/string/sa/#sa-is
func doublingOptimize(bytes []byte) []int {
	var n, p, w int
	var sa, rk, oldrk, id, key1, cnt [N]int
	m := 128
	n = len(bytes)

	for i := 1; i <= n; i++ {
		cnt[int(bytes[i-1])]++
		rk[i] = int(bytes[i-1])
	}
	for i := 1; i < m; i++ {
		cnt[i] += cnt[i-1]
	}
	for i := n; i >= 1; i-- {
		sa[cnt[rk[i]]] = i
		cnt[rk[i]]--
	}

	for w = 1; w < n; w <<= 1 {
		m = p
		p = 0
		for i := n; i > n-w; i-- {
			p++
			id[p] = i
		}
		for i := 1; i <= n; i++ {
			if sa[i] > w {
				p++
				id[p] = sa[i] - w
			}
		}

		for i := 1; i <= n; i++ {
			key1[i] = rk[id[i]]
			cnt[key1[i]]++
		}

		for i := 1; i < m; i++ {
			cnt[i] += cnt[i-1]
		}
		for i := n; i >= 1; i-- {
			sa[cnt[key1[i]]] = id[i]
			cnt[key1[i]]--
		}

		for i := 1; i <= n; i++ {
			oldrk[i] = rk[i]
		}

		p = 0
		for i := 1; i <= n; i++ {
			if oldrk[sa[i]] == oldrk[sa[i-1]] && oldrk[sa[i]+w] == oldrk[sa[i-1]+w] {
				rk[sa[i]] = p
			} else {
				p++
				rk[sa[i]] = p
			}
		}

		if p == n {
			break
		}
	}

	return sa[1 : n+1]
}

// 来自 https://oi-wiki.org/string/sa/#sa-is
func doubling(bytes []byte) []int {

	n := len(bytes)
	s := make([]int, n+1)
	for i, b := range bytes {
		s[i+1] = int(b)
	}

	var sa [N]int
	var rk [N << 1]int
	var oldrk [N << 1]int
	var id [N]int
	var cnt [N]int
	m := 127

	for i := 1; i <= n; i++ {
		cnt[s[i]]++
		rk[i] = s[i]
	}
	for i := 1; i <= m; i++ {
		cnt[i] += cnt[i-1]
	}
	for i := n; i >= 1; i-- {
		sa[cnt[rk[i]]] = i
		cnt[rk[i]]--
	}

	copy(oldrk[1:], rk[1:n+1])
	for p, i := 0, 1; i <= n; i++ {
		if oldrk[sa[i]] == oldrk[sa[i-1]] {
			rk[sa[i]] = p
		} else {
			p++
			rk[sa[i]] = p
		}
	}

	w := 1
	for w < n {
		// 对第二关键字：id[i] + w进行计数排序
		copy(id[1:], sa[1:n+1])
		copy(cnt[:], make([]int, m+1))
		for i := 1; i <= n; i++ {
			cnt[rk[id[i]+w]]++
		}
		for i := 1; i <= m; i++ {
			cnt[i] += cnt[i-1]
		}
		for i := n; i >= 1; i-- {
			sa[cnt[rk[id[i]+w]]] = id[i]
			cnt[rk[id[i]+w]]--
		}

		// 对第一关键字：id[i]进行计数排序
		copy(id[1:], sa[1:n+1])
		copy(cnt[:], make([]int, m+1))
		for i := 1; i <= n; i++ {
			cnt[rk[id[i]]]++
		}
		for i := 1; i <= m; i++ {
			cnt[i] += cnt[i-1]
		}
		for i := n; i >= 1; i-- {
			sa[cnt[rk[id[i]]]] = id[i]
			cnt[rk[id[i]]]--
		}

		copy(oldrk[1:], rk[1:n+1])
		for p, i := 0, 1; i <= n; i++ {
			if oldrk[sa[i]] == oldrk[sa[i-1]] && oldrk[sa[i]+w] == oldrk[sa[i-1]+w] {
				rk[sa[i]] = p
			} else {
				p++
				rk[sa[i]] = p
			}
		}

		w <<= 1
	}

	return sa[1 : n+1]
}
