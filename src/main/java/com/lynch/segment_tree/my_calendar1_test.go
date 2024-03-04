package segment_tree

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/my-calendar-i/description/?show=1
// https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247491187&idx=2&sn=bb2d8b7e89c535914da8107387e951a2
// 实现一个 MyCalendar1 类来存放你的日程安排。如果要添加的日程安排不会造成 重复预订 ，则可以存储这个新的日程安排。
// 当两个日程安排有一些时间上的交叉时（例如两个日程安排都在同一时间内），就会产生 重复预订 。
// 日程可以用一对整数 start 和 end 表示，这里的时间是半开区间，即 [start, end), 实数的范围为: start <= x < end 。
//
// 实现 MyCalendar1 类：
// MyCalendar1() 初始化日历对象。
// boolean book(int start, int end) 如果可以将日程安排成功添加到日历中而不会导致重复预订，
// 返回 true。否则，返回 false 并且不要将该日程安排添加到日历中
//
// 示例：
// 输入：
// ["MyCalendar", "book", "book", "book"]
// [[], [10, 20], [15, 25], [20, 30]]
//
// 输出：
// [null, true, false, true]
//
// 解释：
// MyCalendar1 myCalendar = new MyCalendar1();
// myCalendar.book(10, 20); // return True
// myCalendar.book(15, 25); // return False ，这个日程安排不能添加到日历中，因为时间 15 已经被另一个日程安排预订了。
// myCalendar.book(20, 30); // return True ，这个日程安排可以添加到日历中，因为第一个日程安排预订的每个时间都小于 20 ，且不包含时间 20 。
//
// 提示：
// 1. 0 <= start < end <= 10^9
// 2. 每个测试用例，调用 book 方法的次数最多不超过 1000 次。
func TestMyCalendar1(t *testing.T) {
	myCalendar := newMyCalendar()
	result := myCalendar.book(10, 20)
	fmt.Println("(10,20)->", result)
	result = myCalendar.book(15, 25)
	fmt.Println("(15,25)->", result)
	result = myCalendar.book(20, 30)
	fmt.Println("(20,30)->", result)
}

// 由于start, end 的范围较大 (0 <= start < end <= 10^9) , 所以不能直接基于数组的方式使用线段树进行处理
// 使用线段树时可以按需创建节点, 以免内存超限
type MyCalendar1 struct {
	root *Node1
}

type Node1 struct {
	left   *Node1
	right  *Node1
	booked bool
	update bool
}

// N指定为10的9次方, 0 <= start < end <= 10^9
const N = 1e9

func newMyCalendar() MyCalendar1 {
	return MyCalendar1{
		root: &Node1{},
	}
}
func (m *MyCalendar1) modify(start, end, l, r int, root *Node1) {
	if start <= l && end >= r {
		root.booked = true
		root.update = true
		return
	}

	mid := (l + r) >> 1
	m.pushDown(root)
	if start <= mid {
		m.modify(start, end, l, mid, root.left)
	}
	if end > mid {
		m.modify(start, end, mid+1, r, root.right)
	}
	m.pushUp(root)
}
func (m *MyCalendar1) query(start, end, l, r int, root *Node1) bool {
	if start <= l && end >= r {
		return root.booked
	}

	mid := (l + r) >> 1
	m.pushDown(root)

	left, right := false, false
	if start <= mid {
		left = m.query(start, end, l, mid, root.left)
	}
	if end > mid {
		right = m.query(start, end, mid+1, r, root.right)
	}
	m.pushUp(root)
	return left || right
}
func (m *MyCalendar1) pushUp(root *Node1) {
	root.booked = root.left.booked || root.right.booked
}
func (m *MyCalendar1) pushDown(root *Node1) {
	if root.left == nil {
		root.left = &Node1{}
	}
	if root.right == nil {
		root.right = &Node1{}
	}
	if root.update {
		root.left.update = true
		root.left.booked = true
		root.right.update = true
		root.right.booked = true
		root.update = false
	}
}
func (m *MyCalendar1) book(start, end int) bool {
	booked := m.query(start, end-1, 0, N, m.root)
	if booked {
		return false
	}
	m.modify(start, end-1, 0, N, m.root)
	return true
}
