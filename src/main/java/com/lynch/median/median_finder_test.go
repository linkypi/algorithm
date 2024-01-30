package median

import (
	"fmt"
	"strconv"
	"testing"
)

// https://leetcode.cn/problems/continuous-median-lcci
// 随机产生数字并传递给一个方法。你能否完成这个方法，在每次产生新值时，寻找当前所有值的中间值（中位数）并保存。
// 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
//
// 例如，
// [2,3,4] 的中位数是 3
// [2,3] 的中位数是 (2 + 3) / 2 = 2.5
//
// 设计一个支持以下两种操作的数据结构：
// void addNum (int num) - 从数据流中添加一个整数到数据结构中。
// double findMedian () - 返回目前所有元素的中位数。
// 示例：
// addNum(1)
// addNum(2)
// findMedian() -> 1.5
// addNum(3)
// findMedian() -> 2
func TestMedianFinder(t *testing.T) {
	finder := Constructor()
	finder.AddNum(1)
	finder.AddNum(2)
	median := finder.FindMedian()
	fmt.Println(median)

	for i := 0; i < 800; i++ {
		finder.AddNum(i)
		median = finder.FindMedian()
		fmt.Println(median)
	}

}
func TestMaxHeap(t *testing.T) {
	arr := []int{7, 3, 9, 2, 5, 0, 6}
	maxHeap := newHeap(func(a, b int) int {
		return a - b
	})
	for _, item := range arr {
		maxHeap.add(item)
	}

	for i := 0; i < len(arr); i++ {
		x := maxHeap.pop()
		fmt.Print(strconv.Itoa(x) + "  ")
	}
}

func TestMinHeap(t *testing.T) {
	arr := []int{7, 3, 9, 2, 5, 0, 6}
	minHeap := newHeap(func(a, b int) int {
		return b - a
	})
	for _, item := range arr {
		minHeap.add(item)
	}

	for i := 0; i < len(arr); i++ {
		x := minHeap.pop()
		fmt.Print(strconv.Itoa(x) + "  ")
	}
	fmt.Println()
}

type MedianFinder struct {
	minHeap Heap
	maxHeap Heap
}

func Constructor() MedianFinder {
	max := newHeap(func(a, b int) int {
		return a - b
	})
	min := newHeap(func(a, b int) int {
		return b - a
	})
	return MedianFinder{minHeap: min, maxHeap: max}
}

func (m *MedianFinder) AddNum(num int) {
	minTop := m.minHeap.peek()
	maxTop := m.maxHeap.peek()
	// num 比小根堆的堆顶元素大，说明该元素应该放到小根堆
	if num > minTop {
		m.minHeap.add(num)
		// 小根堆与大根堆之间的元素不能超过两个， 超出则需把多出的元素移到其他一个堆中
		if m.minHeap.size > m.maxHeap.size+1 {
			pop := m.minHeap.pop()
			m.maxHeap.add(pop)
		}
	} else if num < maxTop { // num 比大根堆的堆顶元素小，说明该元素应该放到大根堆
		m.maxHeap.add(num)
		// 小根堆与大根堆之间的元素不能超过两个， 超出则需把多出的元素移到其他一个堆中
		if m.maxHeap.size > m.minHeap.size+1 {
			pop := m.maxHeap.pop()
			m.minHeap.add(pop)
		}
	} else { // maxTop =< num =< minTop
		if m.maxHeap.size > m.minHeap.size {
			m.minHeap.add(num)
		} else {
			m.maxHeap.add(num)
		}
	}
}

func (m *MedianFinder) FindMedian() float64 {
	if m.maxHeap.size == m.minHeap.size {
		top1 := m.minHeap.peek()
		top2 := m.maxHeap.peek()
		return float64(top1+top2) / 2
	}

	if m.maxHeap.size > m.minHeap.size {
		return float64(m.maxHeap.peek())
	}
	return float64(m.minHeap.peek())
}

type Heap struct {
	arr  []int
	size int
	cmp  func(a, b int) int
}

func newHeap(cmp func(a, b int) int) Heap {
	heap := Heap{arr: make([]int, 256), cmp: cmp}
	return heap
}

func (h *Heap) pop() int {
	if h.size == 0 {
		return 0
	}

	result := h.arr[0]
	h.arr[0] = h.arr[h.size-1]
	h.arr[h.size-1] = 0

	h.size--
	n := h.size >> 1
	for i := 0; i < n; i++ {
		left := i<<1 + 1
		right := i<<1 + 2
		target := left
		if right < h.size && h.cmp(h.arr[right], h.arr[target]) >= 0 {
			target = right
		}
		if h.cmp(h.arr[target], h.arr[i]) >= 0 {
			h.arr[i], h.arr[target] = h.arr[target], h.arr[i]
		}
	}

	return result
}

func (h *Heap) peek() int {
	if h.size == 0 {
		return -1
	}
	return h.arr[0]
}

func (h *Heap) add(e int) {

	if h.size == 0 {
		h.arr[0] = e
		h.size++
		return
	}
	curIndex := h.size
	for curIndex > 0 {
		p := (curIndex - 1) >> 1
		parent := h.arr[p]

		// 维护小根堆，若 e 比父节点小 则需与父节点交换
		// e - parent < 0
		//if h.cmp(e, parent) < 0 {
		//	h.arr[p] = e
		//	h.checkCap(curIndex)
		//	h.arr[curIndex] = parent
		//	curIndex = p
		//	break
		//}
		//h.checkCap(curIndex)
		//h.arr[curIndex] = e

		// 维护大根堆，若 e 比父节点大 则需与父节点交换
		// e - parent >= 0
		if h.cmp(e, parent) >= 0 {
			h.arr[p] = e
			h.checkCap(curIndex)
			h.arr[curIndex] = parent
			curIndex = p
			continue
		}
		h.checkCap(curIndex)
		h.arr[curIndex] = e
		break
	}
	h.size++
}

func (h *Heap) checkCap(curIndex int) {
	if cap(h.arr) <= curIndex {
		temp := h.arr
		h.arr = make([]int, curIndex*2, cap(temp)*2)
		for i, x := range temp {
			h.arr[i] = x
		}
	}
}
