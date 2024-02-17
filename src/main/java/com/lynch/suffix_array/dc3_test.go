/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-02-04 16:02:17
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-02-17 11:08:29
 * @FilePath: /algorithm/src/main/java/com/lynch/suffix_array/dc3_test.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
// Package suffix.array
package suffix_array

import (
	"fmt"
	"testing"
)

func TestSuffixArray(t *testing.T) {
	str := "mississippi"
	dc3 := NewDC3(str)
	fmt.Println(dc3.getSA())
}

type DC3 struct {
	sa   []int
	rank []int
}

func NewDC3(str string) DC3 {

	nums := make([]int, len(str)+3)
	max := -1
	for i := 0; i < len(str); i++ {
		value := int(str[i] - 'a')
		nums[i] = value
		if max < value {
			max = value
		}
	}
	dc3 := DC3{}
	dc3.skew(nums, len(str), max)
	return dc3
}

func (d *DC3) getSA() []int {
	return d.sa
}

func (d *DC3) getRank() []int {
	return d.rank
}

func (d *DC3) skew(nums []int, n, max int) []int {
	// 模 3 余 0 的个数  0 1 2 0 1 2 0
	n0 := (n + 2) / 3
	n1 := (n + 1) / 3
	n2 := n / 3

	// 为配合后面 S0 的计算, 使用 n0 长度代替 n1 长度
	n02 := n0 + n2
	// 首先对s12排序
	s12 := make([]int, 0, n02+3)

	for i := 0; i < n+n0-n1; i++ {
		if i%3 != 0 {
			s12 = append(s12, i)
		}
	}
	// 对三维数据进行基数排序
	sa12 := d.radixSort(nums, s12, n02, 2, max)
	s12 = d.radixSort(nums, sa12, n02, 1, max)
	sa12 = d.radixSort(nums, s12, n02, 0, max)

	// 检查结果是否存在相同排名, 如果存在相同排名则使用 S1|S2 的组合方式进行数组拼接
	rank := 0
	c1, c2, c0 := -1, -1, -1
	for _, idx := range sa12 {
		if c0 != nums[idx] || c1 != nums[idx] || c2 != nums[idx+2] {
			rank++
			c0 = nums[idx]
			c1 = nums[idx+1]
			c2 = nums[idx+2]
		}

		//    S1       S2
		// 1 4 7 10 | 2 5 8
		if idx%3 == 1 {
			s12[idx/3] = rank
		} else {
			s12[idx/3+n0] = rank
		}
	}
	if rank < n02 {
		sa12 = d.skew(s12, n02, max)
		// 1 4 7 10 2 5 8 <- index
		// 3 3 2 1  5 5 4 <- check rank
		// 3 2 1 0  6 5 4 <- skew again
		// 将结果转为排名对应的实际索引, 下标为排名
		for idx, item := range sa12 {
			index := 3*idx + 1
			if index > n {
				index = (idx-n0)*3 + 2
			}
			s12[item-1] = index
		}
	} else {
		// 由于没有重复排名,故需将原来 S12 组合拆分为排名与索引下标的方式,即下标 i 对应的是实际排名
		s12 = sa12
	}

	// 求出 S0 排名
	s0 := make([]int, 0, n0)
	// 根据已有的 S12结果排名来求解 S0
	for _, item := range s12 {
		if item%3 == 1 {
			s0 = append(s0, item-1)
		}
	}
	s0 = d.radixSort(nums, s0, n0, 0, max)

	// 合并 S0与S12排名
	sa := make([]int, n)
	i, j := 0, 0
	for i < n0 && j < n02 {
		i0 := s0[i]
		i12 := s12[j]

		if nums[i0] < nums[i12] {
			sa = append(sa, i0)
			i++
			continue
		}
		if nums[i0] > nums[i12] {
			sa = append(sa, i12)
			j++
			continue
		}

		// 若遇到相同字符则可直接利用 S12 的结果来得到最终结果, 每一轮最多比较三次即可得出结果
		idx0, idx12 := i0, i12
		for nums[idx0] == nums[idx12] {
			idx0++
			idx12++

			// 若是遇到 S0 的位置字符则只能进行单个字符对比
			if idx0%3 == 0 || idx12%3 == 0 {
				if nums[idx0] < nums[idx12] {
					sa = append(sa, i0)
					i++
					break
				}
				if nums[idx0] > nums[idx12] {
					sa = append(sa, i12)
					j++
					break
				}
				continue
			}
			// 此后可直接利用 S12 的结果来求解 0 1 2 3 4 5 6 7
			// 首先求出 idx 各自的排名, 然后再比较

			r0, r12 := 0, 0
			for k := 0; k < len(s12); k++ {
				if idx0 == k {
					r0 = k
				}
				if idx12 == k {
					r12 = k
				}
			}

			if r0 < r12 {
				sa = append(sa, i0)
				i++
			} else {
				sa = append(sa, i12)
				j++
			}
			break
		}

		// 移除因为前面为方便求解 S0 时做的补 0 个数, 因为S12 后面补充的都是 0
		// 故他们的最终排名都会排到最前
		return sa[n1-n0:]
	}

	return []int{}
}

func (d *DC3) radixSort(nums []int, ins []int, n int, offset int, max int) []int {
	count := make([]int, max+1)
	for i := 0; i < n; i++ {
		in := ins[i]
		num := nums[in+offset]
		count[num]++
	}

	for i := 1; i < len(count); i++ {
		count[i] += count[i-1]
	}
	// count[i] 表示小于等于 i 的数有 count[i] 个
	result := make([]int, len(ins))

	// 从后往前遍历
	for i := n - 1; i >= 0; i-- {
		num := nums[ins[i]+offset]
		count[num]--
		result[count[num]] = ins[i]
	}

	return result
}
