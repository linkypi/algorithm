package segment_tree

import (
	"fmt"
	"math"
	"testing"
)

/*
*
  - https://leetcode.cn/problems/longest-increasing-subsequence-ii/description/
    *
  - 给你一个整数数组 nums 和一个整数 k 。
  - 找到 nums 中满足以下要求的最长子序列：
  - 子序列严格递增, 子序列中相邻元素的差值不超过 k 。
  - 请你返回满足上述要求的 最长子序列 的长度。
    *
  - 子序列 是从一个数组中删除部分元素后，剩余元素不改变顺序得到的数组。
  - 示例 1：
  - 输入：nums = [4,2,1,4,3,4,5,8,15], k = 3
  - 输出：5
  - 解释：
  - 满足要求的最长子序列是 [1,3,4,5,8] 。
  - 子序列长度为 5 ，所以我们返回 5 。
  - 注意子序列 [1,3,4,5,8,15] 不满足要求，因为 15 - 8 = 7 大于 3 。
    *
  - 示例 2：
  - 输入：nums = [7,4,5,1,8,12,4,7], k = 5
  - 输出：4
  - 解释：
  - 满足要求的最长子序列是 [4,5,8,12] 。
  - 子序列长度为 4 ，所以我们返回 4 。
  - 提示：
    1 <= nums.length <= 10^5
    1 <= nums[i], k <= 10^5
*/
func TestLongestLengthOfLIS(t *testing.T) {
	//arr := []int{4, 2, 1, 4, 3, 4, 5, 8, 15}
	//lenx := lengthOfLIS(arr, 3)
	arr := []int{7, 4, 5, 1, 8, 12, 4, 7}
	lenx := lengthOfLIS(arr, 5)
	fmt.Println("len:", lenx)
}

func lengthOfLIS(arr []int, k int) int {
	segment := newSegment()
	res := math.MinInt
	for _, item := range arr {
		max := segment.query(item-k, item-1, 0, 1e5, segment.root)
		if res < max+1 {
			res = max + 1
		}
		segment.update(item, item, max+1, 0, 1e5, segment.root)
	}
	return res
}

type Segment struct {
	root *Node
}

type Node struct {
	left  *Node
	right *Node
	max   int
	index int
}

func newSegment() Segment {
	return Segment{root: &Node{index: 1}}
}
func (s *Segment) update(start, end, val, l, r int, root *Node) {
	if start <= l && end >= r {
		root.max = int(math.Max(float64(root.max), float64(val)))
		return
	}

	mid := (l + r) >> 1
	s.pushDown(root)
	if start <= mid {
		s.update(start, end, val, l, mid, root.left)
	}
	if end > mid {
		s.update(start, end, val, mid+1, r, root.right)
	}
	s.pushUp(root)
}
func (s *Segment) query(start, end, l, r int, root *Node) int {
	if start <= l && end >= r {
		return root.max
	}
	mid := (l + r) >> 1
	s.pushDown(root)
	left, right := 0, 0
	if start <= mid {
		left = s.query(start, end, l, mid, root.left)
	}
	if end > mid {
		right = s.query(start, end, mid+1, r, root.right)
	}
	return int(math.Max(float64(left), float64(right)))
}

func (s *Segment) pushDown(root *Node) {
	if root.left == nil {
		root.left = &Node{index: root.index << 1}
	}
	if root.right == nil {
		root.right = &Node{index: root.index<<1 | 1}
	}
}

func (s *Segment) pushUp(root *Node) {
	root.max = int(math.Max(float64(root.left.max), float64(root.right.max)))
}
