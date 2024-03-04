package heap

import (
	"container/heap"
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description:
 * @createTime: 2024/2/27 12:27
 */
func TestPriorityQueue(t *testing.T) {
	// 创建节点并设计他们的优先级
	items := map[string]int{"二毛": 5, "张三": 3, "狗蛋": 9}
	i := 0
	pq := PriorityQueue{
		arr: make([]*Item, len(items)),
	}
	for k, v := range items { // 将节点放到优先级队列中
		pq.arr[i] = &Item{
			value:    k,
			priority: v,
			index:    i,
		}
		i++
	}
	heap.Init(&pq) // 初始化堆
	item := &Item{ // 创建一个item
		value:    "李四",
		priority: 1,
	}
	heap.Push(&pq, item)           // 入优先级队列
	pq.update(item, 6, item.value) // 更新item的优先级
	for len(pq.arr) > 0 {
		item = heap.Pop(&pq).(*Item)
		fmt.Printf("priority:%d -> %s index:%d\n", item.priority, item.value, item.index)
	}
}

type PriorityQueue struct {
	arr []*Item
}

type Item struct {
	value    string
	priority int
	// 节点在堆中的位置, 当某个元素的优先级需要调整时，需借助于 heap.Fix(pq, index) 来实现优先级调整
	index int
}

func (p *PriorityQueue) Len() int {
	return len(p.arr)
}

func (p *PriorityQueue) Less(i, j int) bool {
	return p.arr[i].priority < p.arr[j].priority
}
func (p *PriorityQueue) Swap(i, j int) {
	p.arr[i], p.arr[j] = p.arr[j], p.arr[i]
	p.arr[i].index = i
	p.arr[j].index = j
}
func (p *PriorityQueue) Pop() interface{} {
	x := p.arr[len(p.arr)-1]
	//x.index = -1
	p.arr = p.arr[:len(p.arr)-1]
	return x
}

func (p *PriorityQueue) Push(ele interface{}) {
	item := ele.(*Item)
	item.index = len(p.arr)
	p.arr = append(p.arr, item)
}

func (p *PriorityQueue) update(item *Item, priority int, value string) {
	item.value = value
	item.priority = priority
	heap.Fix(p, item.index)
}
