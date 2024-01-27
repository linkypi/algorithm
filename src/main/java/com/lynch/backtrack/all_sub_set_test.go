package backtrack

import (
	"fmt"
	"sort"
	"testing"
)

type Tx struct {
}

func (t *Tx) Get() int {
	return 90
}
func (t Tx) Get2() int {
	return 90
}

func TestAllSubSet(t *testing.T) {

	// 创建一个初始长度和容量为 2 的切片
	s := []int{1, 2}
	fmt.Println("Len:", len(s), "Cap:", cap(s))

	// 追加元素，触发扩容
	s = append(s, 3)
	fmt.Println("Len:", len(s), "Cap:", cap(s))

	// 追加多个元素，可能再次触发扩容
	s = append(s, 4, 5, 6)
	fmt.Println("Len:", len(s), "Cap:", cap(s))

	s = append(s, 4, 5, 6)
	fmt.Println("Len:", len(s), "Cap:", cap(s))

	s = append(s, 4, 5, 6, 4, 5, 6, 4, 5, 6)
	fmt.Println("Len:", len(s), "Cap:", cap(s))

	// 追加多个元素，可能再次触发扩容
	for i := 0; i < 1028; i++ {
		s = append(s, i+10)
	}

	fmt.Println("Len:", len(s), "Cap:", cap(s))

	tx := Tx{}
	tx.Get2()
	tx.Get()
	//arr := []int{1, 2, 3}
	arr := []int{1, 3, 2, 3}

	result = make([][]int, 0)
	path := make([]int, 0)
	//findSubSet(arr, path, 0)
	//res := findSubSetNo(arr)
	findSubSetWithDup(arr, path, 0)

	//var visited = make(map[int]bool)
	//findSubNoDuplicate(arr, path, visited, 0)
	fmt.Println(result)

	t.Log(result)

}

var result = make([][]int, 0)
var set = make([]int, 0)

func swap(arr []int, i, j int) {
	temp := arr[i]
	arr[i] = arr[j]
	arr[j] = temp
}
func getArrange2(arr []int, p, q int) {
	if p == q {
		x := make([]int, p+1)
		copy(x, arr[:p+1])
		result = append(result, x)
		fmt.Println(x)
		return
	}

	for i := p; i <= q; i++ {
		swap(arr, p, i)
		getArrange2(arr, p+1, q)
		swap(arr, p, i)
	}
}

// 获取所有子集合，其中每个子集的元素可重复
func getAllArrange(arr []int) {
	var visited = make(map[int]bool)
	size := len(arr)
	for i := 0; i <= size; i++ {
		getArrange(arr, visited, i)
	}
}

/*
*
找出数组的所有子集, 其中的元素可重复
*/
func findSubSet(arr []int, path []int, start int) {
	result = append(result, append([]int{}, path...))
	for i := start; i < len(arr); i++ {
		path = append(path, arr[i])
		findSubSet(arr, path, i+1)
		path = path[:len(path)-1]
	}
}

func findSubSetNo(arr []int) [][]int {
	path := make([]int, 0)
	sort.Ints(arr)
	used := make(map[int]bool)
	findSubSetNoDup(arr, path, 0, used)
	return result
}

/*
*
从给定数组中找出所有子集, 数组元素可能出现重复数字, 但要求子集合不能有重复元素
*/
func findSubSetNoDup(arr []int, path []int, start int, used map[int]bool) {
	result = append(result, append([]int{}, path...))
	for i := start; i < len(arr); i++ {
		// 对数组重排序后对比相邻两数是否相同,相同则忽略
		if i > 0 && arr[i] == arr[i-1] && !used[i] {
			continue
		}
		path = append(path, arr[i])
		used[i] = true
		findSubSetNoDup(arr, path, i+1, used)
		path = path[:len(path)-1]
		used[i] = false
	}
}

// 给定一个数组 找到其所有子集, 数组元素可能重复, 要求结果可以存在重复元素
func findSubSetWithDup(arr []int, path []int, start int) {
	result = append(result, append([]int{}, path...))

	for i := start; i < len(arr); i++ {
		path = append(path, arr[i])
		findSubSetWithDup(arr, path, i+1)
		path = path[:len(path)-1]
	}
}

func findSubNoDuplicate(arr []int, path []int, visited map[int]bool, start int) {
	if !visited[start] {
		result = append(result, append([]int{}, path...))
	}
	for i := start; i < len(arr); i++ {
		path = append(path, arr[i])
		visited[arr[i]] = true
		findSubSet(arr, path, i+1)
		path = path[:len(path)-1]
		visited[arr[i]] = false
	}
}

func getArrange(arr []int, visited map[int]bool, count int) {
	if count == 0 {
		result = append(result, []int{})
		return
	}

	if count != 0 && len(set) == count {
		target := make([]int, len(set))
		copy(target, set)
		result = append(result, target)
		return
	}

	for _, v := range arr {
		if visited[v] {
			continue
		}
		visited[v] = true
		set = append(set, v)
		getArrange(arr, visited, count)
		visited[v] = false
		set = set[0 : len(set)-1]
	}
}
