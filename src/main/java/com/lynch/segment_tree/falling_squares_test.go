package segment_tree

import (
	"fmt"
	"math"
	"sort"
	"testing"
)

// https://leetcode.cn/problems/falling-squares/
// 在二维平面上的 x 轴上，放置着一些方块。
// 给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：
// 第 i 个方块边长为 sideLengthi ，其左侧边与 x 轴上坐标点 lefti 对齐。
// 每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，
// 直到着陆到 另一个正方形的顶边 或者是 x 轴上 。一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。
// 一旦着陆，它就会固定在原地，无法移动。
// 在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
// 返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度。
// 示例1：
// 输入：positions = [[1,2],[2,3],[6,1]]
// 输出：[2,5,5]
// 解释：
// 第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 2 。
// 第 2 个方块掉落后，最高的堆叠由方块 1 和 2 组成，堆叠的最高高度为 5 。
// 第 3 个方块掉落后，最高的堆叠仍然由方块 1 和 2 组成，堆叠的最高高度为 5 。
// 因此，返回 [2, 5, 5] 作为答案。
func TestFallingSquares(t *testing.T) {
	//	positions := [][]int{{1, 2}, {2, 3}, {6, 1}}
	positions := [][]int{{6, 4}, {2, 7}, {6, 9}}
	squares := fallingSquares(positions)
	fmt.Println(squares)
}

type Set map[int]struct{}

func fallingSquares(positions [][]int) []int {

	m := len(positions)
	arr := make([]int, 0, m)
	set := make(Set)

	// 由于x坐标及高度h基数可能会很大，导致内存超限，所以在使用线段树前需做映射处理，将值域映射到较小的空间
	// 1. 去重并按升序排序
	// 2. 将实际坐标映射为实际坐标个数对应的索引

	// 1. 去重并按升序排序
	for i := 0; i < m; i++ {
		item := positions[i]
		set[item[0]] = struct{}{}
		set[item[0]+item[1]-1] = struct{}{}
	}

	for key, _ := range set {
		arr = append(arr, key)
	}
	sort.Ints(arr)
	//  2. 将实际坐标映射为实际坐标个数对应的索引
	count := 1
	mp := make(map[int]int)
	for _, item := range arr {
		mp[item] = count
		count++
	}

	n := len(mp)
	segmentTree := newSegmentTree2(n)
	result := make([]int, 0, n)
	max := 0
	for i := 0; i < len(positions); i++ {
		h := positions[i][1]
		x := mp[positions[i][0]]
		y := mp[positions[i][0]+h-1]

		maxH := segmentTree.query(1, n, 1, x, y) + h
		max = int(math.Max(float64(max), float64(maxH)))
		segmentTree.modify(1, n, 1, x, y, maxH)
		result = append(result, max)
	}
	return result
}

type SegmentTree2 struct {
	max    []int
	update []bool
	change []int
}

func newSegmentTree2(size int) SegmentTree2 {
	n := size + 1
	return SegmentTree2{
		max:    make([]int, n<<2),
		update: make([]bool, n<<2),
		change: make([]int, n<<2),
	}
}

func (s *SegmentTree2) pushDown(root, lc, rc int) {
	if s.update[root] {
		s.update[root<<1] = true
		s.update[root<<1|1] = true

		s.change[root<<1] = s.change[root]
		s.change[root<<1|1] = s.change[root]

		s.max[root<<1] = s.change[root]
		s.max[root<<1|1] = s.change[root]
		s.update[root] = false
	}
}

func (s *SegmentTree2) pushUp(root int) {
	m := int(math.Max(float64(s.max[root<<1]), float64(s.max[root<<1|1])))
	s.max[root] = m
}

func (s *SegmentTree2) modify(l, r, root, start, end, num int) {
	if start <= l && end >= r {
		s.update[root] = true
		s.change[root] = num
		s.max[root] = num
		return
	}

	mid := (l + r) >> 1
	s.pushDown(root, mid-l+1, r-mid)
	if start <= mid {
		s.modify(l, mid, root<<1, start, end, num)
	}
	if end > mid {
		s.modify(mid+1, r, root<<1|1, start, end, num)
	}
	s.pushUp(root)
}
func (s *SegmentTree2) query(l, r, root, start, end int) int {
	if start <= l && end >= r {
		return s.max[root]
	}

	mid := (l + r) >> 1
	s.pushDown(root, mid-l+1, r-mid)

	left, right := 0, 0
	if start <= mid {
		left = s.query(l, mid, root<<1, start, end)
	}

	if end > mid {
		right = s.query(mid+1, r, root<<1|1, start, end)
	}
	return int(math.Max(float64(left), float64(right)))
}
