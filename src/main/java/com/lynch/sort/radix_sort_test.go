package sort

import (
	"fmt"
	"math"
	"testing"
)

func TestRadixSort(t *testing.T) {
	//arr := []int{13, 17, 9, 2, 5}
	//result := radixSort(arr)

	arr := []int{65, 17, -99, 76, -50, 123, -38}
	radixSortWithNegative(arr)
	fmt.Println(arr)
}

// 指定多少进制数
const BASE = 10

// radixSortWithNegative 对存在负数的数组进行排序
func radixSortWithNegative(nums []int) {
	min := math.MaxInt
	// 首先找到最小值
	for i := 0; i < len(nums); i++ {
		if nums[i] < min {
			min = nums[i]
		}
	}

	// 将负数转为正数, 最后排序完成后再将实际数据还原即可
	for i := 0; i < len(nums); i++ {
		nums[i] -= min
	}

	radixSort(nums)

	for i := 0; i < len(nums); i++ {
		nums[i] += min
	}
}

func radixSort(arr []int) {
	max := 0
	for i := 0; i < len(arr); i++ {
		if arr[i] > max {
			max = arr[i]
		}
	}
	bits := getBits(max)
	radixSortInternal(arr, bits)
}

// radixSort
// nums 为待排序数组, 不能存在负数
// max 为数组中的最大值
// bits 为最大值有多少位BASE进制数，如十进制的123有3位，16进制的 0x89 有两位
func radixSortInternal(nums []int, bits int) {
	base := 1
	for i := 1; i <= bits; i++ {
		if i > 1 {
			base = BASE * base
		}
		count := make([]int, BASE)
		// 对基数取模后将数字放到相应的桶中进行累加
		for j := 0; j < len(nums); j++ {
			remind := (nums[j] / base) % BASE
			count[remind]++
		}

		// 得到前缀和数组，count[i]可理解为在nums中有count[i]个数小于等于count的桶下标 i
		// 如
		//         i: 0 1 2 3 4 5 6 7 8 9
		//  count[i]: 1 0 3 0 0 0 2 0 3 0
		// count sum: 1 1 4 4 4 4 6 0 9 9
		// 其中count[8]就表示nums数组中共有9个数小于等于8，count[6]就表示有6个数小于等于6
		for j := 1; j < len(count); j++ {
			count[j] = count[j-1] + count[j]
		}
		// 得到上述count前缀和后就可以通过倒序遍历的方式来求出本次排序的结果
		// 同样先求出每个数在桶中的位置，随后根据该位置 index 就可以到 count 得到该数字的 count[index]
		// 即 count[index] 就表示nums数组中共有count[index]个数小于等于index, index也就是映射为当前桶的数
		// 故实际排序结果的位置就可以确定为 (count[index]-1), 处理完当前数字后需要将 count[index] 值减一
		help := make([]int, len(nums))
		for j := len(nums) - 1; j >= 0; j-- {
			index := (nums[j] / base) % BASE
			count[index]--
			help[count[index]] = nums[j]
		}

		for i := 0; i < len(nums); i++ {
			nums[i] = help[i]
		}
	}
}

func getBits(num int) int {
	bits := 0
	bits++
	for num >= BASE {
		bits++
		num = num / BASE
	}
	return bits
}
