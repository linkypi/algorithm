package segment_tree

import (
	"fmt"
	"math"
	"testing"
)

// https://leetcode.cn/problems/my-calendar-ii/description/
// 实现一个 MyCalendar 类来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
// MyCalendar 有一个 book(int start, int end) 方法。它意味着在 start 到 end 时间内增加一个日程安排，
// 注意，这里的时间是半开区间，即 [start, end), 实数 x 的范围为，  start <= x < end。
// 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生三重预订。
// 每次调用 MyCalendar.book 方法时，如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。
// 否则，返回 false 并且不要将该日程安排添加到日历中。
// 请按照以下步骤调用 MyCalendar 类: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
//
// 示例：
//
//	MyCalendar();
//	MyCalendar.book(10, 20); // returns true
//	MyCalendar.book(50, 60); // returns true
//	MyCalendar.book(10, 40); // returns true
//	MyCalendar.book(5, 15); // returns false
//	MyCalendar.book(5, 10); // returns true
//	MyCalendar.book(25, 55); // returns true
//
// 解释：
//
//	前两个日程安排可以添加至日历中。 第三个日程安排会导致双重预订，但可以添加至日历中。
//	第四个日程安排活动（5,15）不能添加至日历中，因为它会导致三重预订。
//	第五个日程安排（5,10）可以添加至日历中，因为它未使用已经双重预订的时间10。
//	第六个日程安排（25,55）可以添加至日历中，因为时间 [25,40] 将和第三个日程安排双重预订；
//	时间 [40,50] 将单独预订，时间 [50,55）将和第二个日程安排双重预订。
//
// 提示：
//
//	每个测试用例，调用 MyCalendar.book 函数最多不超过 1000 次。
//	调用函数 MyCalendar.book(start, end) 时， start 和 end 的取值范围为 [0, 10^9]。
func TestMyCalendar2(t *testing.T) {
	myCalendar := Constructor()
	data := [][]int{{10, 20}, {50, 60}, {10, 40}, {5, 15}, {5, 10}, {25, 55}}
	for _, item := range data {
		result := myCalendar.Book(item[0], item[1])
		fmt.Printf("(%d,%d)-> %v\n", item[0], item[1], result)
	}
}

type MyCalendarTwo struct {
	root *Node2
}

type Node2 struct {
	left  *Node2
	right *Node2
	add   int
	val   int
}

const N2 = 1e9

func Constructor() MyCalendarTwo {
	return MyCalendarTwo{root: &Node2{}}
}
func (m *MyCalendarTwo) update(start, end, l, r, val int, root *Node2) {
	if start <= l && end >= r {
		root.val += val
		root.add += val
		return
	}

	mid := (l + r) >> 1
	m.pushDown(root)
	if start <= mid {
		m.update(start, end, l, mid, val, root.left)
	}
	if end > mid {
		m.update(start, end, mid+1, r, val, root.right)
	}
	m.pushUp(root)
}

func (m *MyCalendarTwo) query(start, end, l, r int, root *Node2) int {
	if start <= l && end >= r {
		return root.val
	}

	mid := (l + r) >> 1
	m.pushDown(root)

	left, right := 0, 0
	if start <= mid {
		left = m.query(start, end, l, mid, root.left)
	}
	if end > mid {
		right = m.query(start, end, mid+1, r, root.right)
	}
	max := int(math.Max(float64(left), float64(right)))
	return max
}

func (m *MyCalendarTwo) pushDown(root *Node2) {
	if root.left == nil {
		root.left = &Node2{}
	}
	if root.right == nil {
		root.right = &Node2{}
	}
	if root.add == 0 {
		return
	}
	root.left.val += root.add
	root.right.val += root.add
	root.left.add += root.add
	root.right.add += root.add
	root.add = 0
}

func (m *MyCalendarTwo) pushUp(root *Node2) {
	root.val = int(math.Max(float64(root.left.val), float64(root.right.val)))
}

func (m *MyCalendarTwo) Book(start int, end int) bool {
	val := m.query(start, end-1, 0, N2, m.root)
	if val >= 2 {
		return false
	}
	m.update(start, end-1, 0, N2, 1, m.root)
	return true
}
