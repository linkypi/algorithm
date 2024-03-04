/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-01-26 11:45:13
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-26 18:03:12
 * @FilePath: /algorithm/src/main/java/com/lynch/o1.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main

import (
	"fmt"
	"math/rand"
	"reflect"
	"testing"
)

// https://leetcode.cn/problems/Insert-delete-getrandom-o1-duplicates-allowed/description/
// RandomizedCollection 是一种包含数字集合 (可能是重复的) 的数据结构。它应该支持插入和删除特定元素，以及删除随机元素
// 实现 RandomizedCollection 类:
// RandomizedCollection() 初始化空的 RandomizedCollection 对象。
// bool Insert(int val) 将一个 val 项插入到集合中，即使该项已经存在。如果该项不存在，则返回 true ，否则返回 false 。
// bool Remove(int val) 如果存在，从集合中移除一个 val 项。如果该项存在，则返回 true ，否则返回 false 。
// 注意，如果 val 在集合中出现多次，我们只删除其中一个。
// int GetRandom() 从当前的多个元素集合中返回一个随机元素。每个元素被返回的概率与集合中包含的相同值的数量 线性相关
// 您必须实现类的函数，使每个函数的 平均 时间复杂度为 O(1) 。
// 注意：生成测试用例时，只有在 RandomizedCollection 中 至少有一项 时，才会调用 GetRandom 。

type TestCase struct {
	methods []string
	args    []int
	result  []any
}

func TestO1(t *testing.T) {

	cases := TestCase{}

	// case 1
	// cases.methods = []string{
	// 	"Insert", "Insert", "Insert", "Insert",
	// 	"Insert", "Remove", "Remove", "Remove",
	// 	"Insert", "Remove",
	// 	"GetRandom", "GetRandom", "GetRandom", "GetRandom", "GetRandom",
	// 	"GetRandom", "GetRandom", "GetRandom", "GetRandom", "GetRandom"}
	// cases.args = []int{1, 1, 2, 2, 2, 1, 1, 2, 1, 2}

	// case 2
	// cases.methods = []string{
	// 	"Insert", "Insert", "Insert", "Insert", "Insert", "Insert", "Remove", "Remove", "Remove", "Remove",
	// 	"GetRandom", "GetRandom", "GetRandom", "GetRandom", "GetRandom",
	// 	"GetRandom", "GetRandom", "GetRandom", "GetRandom", "GetRandom",
	// }
	// cases.args = []int{10, 10, 20, 20, 30, 30, 10, 10, 30, 30,
	// 	0, 0, 0, 0, 0, 0, 0, 0, 0, 0}

	// case 3
	// cases.methods = []string{
	// 	"Insert", "Insert", "Insert", "Insert", "Insert",
	// 	"Insert", "Remove", "Remove", "Remove", "Insert", "Remove",
	// }
	// cases.args = []int{9, 9, 1, 1, 2, 1, 2, 1, 1, 9, 1}

	cases.methods = []string{
		"Insert", "Remove", "Insert"}
	cases.args = []int{1, 1, 1}

	cases.result = make([]any, 0, 8)

	collect := Constructor()
	elems := reflect.ValueOf(&collect)
	for i, method := range cases.methods {
		m := elems.MethodByName(method)
		if !m.IsValid() {
			continue
		}
		if method == "Insert" || method == "Remove" {
			params := make([]reflect.Value, 1)
			params[0] = reflect.ValueOf(cases.args[i])
			result := m.Call(params)
			x := result[0].Interface()
			// x2 := result[0].InterfaceData()
			fmt.Println(method, "  ", cases.args[i], "  ", x, "  ")
			// cases.result[i] = x
			continue
		}
		//cases.result[i] =
		x := m.Call(nil)
		fmt.Println(method, "  ", cases.args[i], "  ", x, "  ")
	}
}

type RandomizedCollection struct {
	// 存放一个元素对应的索引列表, 索引列表使用 map 可高效处理列表数据
	indexMap map[int]map[int]struct{}
	list     []int
}

func Constructor() RandomizedCollection {
	return RandomizedCollection{
		indexMap: make(map[int]map[int]struct{}),
		list:     make([]int, 0),
	}
}

// 将一个 val 项插入到集合中，即使该项已经存在。如果该项不存在，则返回 true ，否则返回 false
func (r *RandomizedCollection) Insert(e int) bool {
	r.list = append(r.list, e)

	// 记录元素 e 的索引列表
	indexList, exist := r.indexMap[e]
	if !exist {
		indexList = make(map[int]struct{}, 0)
		r.indexMap[e] = indexList
	}
	indexList[len(r.list)-1] = struct{}{}
	return !exist
}

// 如果存在，从集合中移除一个 val 项。如果该项存在，则返回 true ，否则返回 false
// 注意，如果 val 在集合中出现多次，我们只删除其中一个
func (r *RandomizedCollection) Remove(e int) bool {
	indexList, exist := r.indexMap[e]
	if !exist {
		return false
	}
	index := 0
	for i, _ := range indexList {
		index = i
		break
	}

	// 与列表末尾元素交换后删除该元素, 减少查询复杂度
	n := len(r.list)
	lastIndex := n - 1
	last := r.list[lastIndex]
	r.list[index] = r.list[lastIndex]
	r.list = r.list[0:lastIndex]

	// 移除索引元素
	delete(indexList, index)
	delete(r.indexMap[last], lastIndex)

	// 修改被调整的索引
	if index < n-1 {
		r.indexMap[r.list[index]][index] = struct{}{}
	}
	if len(r.indexMap) == 1 {
		delete(r.indexMap, e)
	}
	return true
}

// 从当前的多个元素集合中返回一个随机元素。每个元素被返回的概率与集合中包含的相同值的数量 线性相关
func (r *RandomizedCollection) GetRandom() int {
	index := rand.Intn(len(r.list))
	return r.list[index]
}
