package segment_tree

import "testing"

func TestSegmentTree(t *testing.T) {

}

// 线段数实现
// 1. 在left-right范围上对数组中的每个元素都加上/减去一个数
// 2. 在left-right范围上对数组的元素都改为某个数
type SegmentTree struct {
	arr    []int
	sum    []int
	lazy   []int  // 记录累加
	update []bool // 记录是否存在更新
	change []int  // 记录实际更新的数值
}

func NewSegmentTree(n int) SegmentTree {
	return SegmentTree{
		arr:    make([]int, n+1),
		sum:    make([]int, n<<2),
		lazy:   make([]int, n<<2),
		change: make([]int, n<<2),
		update: make([]bool, n<<2),
	}
}

func (s *SegmentTree) build(arr []int, l, r int) {
	n := len(arr)
	for i := 1; i < n+1; i++ {
		s.arr[i] = arr[i-1]
	}

	s._build(1, n+1, 1)
}

func (s *SegmentTree) _build(l, r, root int) {
	if l == r {
		s.sum[root] = s.arr[l]
		return
	}
	mid := (r + l) >> 1
	s._build(l, mid, root<<1)
	s._build(mid+1, r, root<<1|1)

	s.sum[root] = s.sum[root<<1] + s.sum[root<<1|1]
}

func (s *SegmentTree) add(start, end, num int) {
	s._add(0, len(s.arr)-1, 1, start, end, num)
}
func (s *SegmentTree) _add(l, r, root, start, end, num int) {

	// l-r的范围已经包含在 [start,end] 中，无需再往下层查询，直接更新
	if start <= l && end >= r {
		s.lazy[root] += num
		s.sum[root] += num * (r - l + 1)
		return
	}
	mid := (r + l) >> 1
	s.pushDown(root, mid-l+1, r-mid)

	// 调整左边
	if start <= mid {
		s._add(l, mid, root<<1, start, end, num)
	}
	// 调整右边
	if end > mid {
		s._add(mid+1, r, root<<1|1, start, end, num)
	}
	s.sum[root] = s.sum[root<<1] + s.sum[root<<1|1]
}

func (s *SegmentTree) pushDown(root, ln, rn int) {
	// 必须先检查 update 再检查是否存在累加的 lazy
	if s.update[root] {
		s.update[root] = false
		s.update[root<<1] = true
		s.update[root<<1|1] = true
		s.change[root<<1] = s.change[root]
		s.change[root<<1|1] = s.change[root]
		s.lazy[root<<1] = 0
		s.lazy[root<<1|1] = 0
		s.sum[root<<1] = s.change[root] * ln
		s.sum[root<<1|1] = s.change[root] * rn
	}

	if s.lazy[root] != 0 {
		// 将lazy下推到下一层节点
		s.lazy[root<<1] += s.lazy[root]
		s.lazy[root<<1|1] += s.lazy[root]

		s.sum[root<<1] += s.lazy[root] * ln
		s.sum[root<<1|1] += s.lazy[root] * rn
		s.lazy[root] = 0
	}

}

func (s *SegmentTree) modify(start, end, num int) {
	s._modify(1, len(s.arr)-1, 1, start, end, num)
}
func (s *SegmentTree) _modify(l, r, root, start, end, num int) {
	if start <= l && end >= r {
		s.lazy[root] = 0
		s.update[root] = true
		s.change[root] = num
		s.sum[root] += num * (r - l + 1)
		return
	}

	mid := (l + r) >> 1
	s.pushDown(root, mid-l+1, r-mid)
	if start <= mid {
		s._modify(l, mid, root<<1, start, end, num)
	}

	if mid < end {
		s._modify(mid+1, r, root<<1|1, start, end, num)
	}
	s.sum[root] = s.sum[root<<1] + s.sum[root<<1|1]
}
func (s *SegmentTree) query(start, end int) int {
	return s._query(1, len(s.arr)-1, 1, start, end)
}
func (s *SegmentTree) _query(l, r, root, start, end int) int {
	if start <= l && end >= r {
		return s.sum[root]
	}
	mid := l + (r-l+1)>>1
	s.pushDown(root, mid-l+1, r-mid)
	ans := 0
	if mid >= start {
		ans += s._query(l, mid, root<<1, start, end)
	}
	if mid < end {
		ans += s._query(mid+1, r, root<<1|1, start, end)
	}
	return ans
}
