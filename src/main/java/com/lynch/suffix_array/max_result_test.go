package suffix_array

/**
 * @author: lynch
 * @description: https://leetcode.cn/problems/create-maximum-number/description
   给定长度分别为 m 和 n 的两个数组，其元素由 0-9 构成，表示两个自然数各位上的数字。
	现在从这两个数组中选出 k (k <= m + n) 个数字拼接成一个新的数，要求从同一个数组中取出的数字保持其在原数组中的相对顺序。
	求满足该条件的最大数。结果返回一个表示该最大数的长度为 k 的数组。
	说明: 请尽可能地优化你算法的时间和空间复杂度。

	示例 1:
	输入:
	nums1 = [3, 4, 6, 5]
	nums2 = [9, 1, 2, 5, 8, 3]
	k = 5
	输出:
	[9, 8, 6, 5, 3]

	示例 2:
	输入:
	nums1 = [6, 7]
	nums2 = [6, 0, 4]
	k = 5
	输出:
	[6, 7, 6, 0, 4]

	示例 3:
	输入:
	nums1 = [3, 9]
	nums2 = [8, 9]
	k = 3
	输出:
	[9, 8, 9]
	提示：

	m == nums1.length
	n == nums2.length
	1 <= m, n <= 500
	0 <= nums1[i], nums2[i] <= 9
	1 <= k <= m + n
 * @createTime: 2024/3/2 23:24
*/
import (
	"fmt"
	"index/suffixarray"
	"testing"
	"unsafe"
)

func TestMaxNum(t *testing.T) {
	nums1 := []int{3, 4, 6, 5}
	nums2 := []int{9, 1, 2, 5, 8, 3}
	k := 5
	ret := maxNumber(nums1, nums2, k)
	fmt.Println(ret)
}

// 使用后缀数组对不同序列直接计算排名，使用该方式实现过于复杂
// 可以基于单调栈+归并来实现更简单
func maxNumber(nums1 []int, nums2 []int, k int) []int {
	len1 := len(nums1)
	len2 := len(nums2)
	if k < 0 || k > len1+len2 {
		return nil
	}
	res := make([]int, k)
	//两个动态规划
	dp1 := getDp(nums1)
	dp2 := getDp(nums2)
	for x := getMax(0, k-len2); x <= getMin(k, len1); x++ {
		//arr1中挑元素
		pick1 := maxPick(nums1, dp1, x)
		//arr2中挑元素
		pick2 := maxPick(nums2, dp2, k-x)
		//和并挑的元素
		merge := mergeBySuffixArray(pick1, pick2)
		//获取最大值
		if !moreThan(res, merge) {
			res = merge
		}
	}
	return res
}
func moreThan(pre []int, last []int) bool {
	i := 0
	j := 0
	for i < len(pre) && j < len(last) && pre[i] == last[j] {
		i++
		j++
	}
	return j == len(last) || (i < len(pre) && pre[i] > last[j])
}

func mergeBySuffixArray(nums1 []int, nums2 []int) []int {
	size1 := len(nums1)
	size2 := len(nums2)
	nums := make([]int, size1+1+size2)
	for i := 0; i < size1; i++ {
		nums[i] = nums1[i] + 2
	}
	nums[size1] = 1
	for j := 0; j < size2; j++ {
		nums[j+size1+1] = nums2[j] + 2
	}
	all := make([]byte, len(nums))
	for i := 0; i < len(nums); i++ {
		all[i] = byte(nums[i])
	}
	dc3 := NewFddSa(all)

	ans := make([]int, size1+size2)
	i := 0
	j := 0
	r := 0
	for i < size1 && j < size2 {
		if dc3.Rank[i] > dc3.Rank[j+size1+1] {
			ans[r] = nums1[i]
			r++
			i++
		} else {
			ans[r] = nums2[j]
			r++
			j++
		}
	}
	for i < size1 {
		ans[r] = nums1[i]
		r++
		i++
	}
	for j < size2 {
		ans[r] = nums2[j]
		r++
		j++
	}
	return ans
}

func maxPick(arr []int, dp [][]int, pick int) []int {
	res := make([]int, pick)
	for resIndex, dpRow := 0, 0; pick > 0; pick, resIndex = pick-1, resIndex+1 {
		res[resIndex] = arr[dp[dpRow][pick]]
		dpRow = dp[dpRow][pick] + 1
	}
	return res
}

func getDp(arr []int) [][]int {
	size := len(arr)     // 0~N-1
	pick := len(arr) + 1 // 1 ~ N
	dp := make([][]int, size)
	for i := 0; i < size; i++ {
		dp[i] = make([]int, pick)
	}
	// get 不从0开始，因为拿0个无意义
	for get := 1; get < pick; get++ { // 1 ~ N
		maxIndex := size - get
		// i~N-1
		for i := size - get; i >= 0; i-- {
			if arr[i] >= arr[maxIndex] {
				maxIndex = i
			}
			dp[i][get] = maxIndex
		}
	}
	return dp
}

func getMax(a int, b int) int {
	if a > b {
		return a
	} else {
		return b
	}
}
func getMin(a int, b int) int {
	if a < b {
		return a
	} else {
		return b
	}
}

type FddSa struct {
	Sa   []int
	Rank []int
}

func NewFddSa(bytes []byte) *FddSa {
	ret := &FddSa{}
	ret.Rank = make([]int, len(bytes))
	ret.Sa = make([]int, len(bytes))
	index := suffixarray.New(bytes)
	p1 := uintptr(unsafe.Pointer(index)) //获取指针
	p1 += 24
	p2 := *(*[]int32)(unsafe.Pointer(p1)) //将指针转行成切片
	for i := 0; i < len(bytes); i++ {
		ret.Sa[i] = int(p2[i])   //sa数组
		ret.Rank[int(p2[i])] = i //rank数组
	}
	return ret
}
