/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-01-27 18:03:24
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-27 21:44:32
 * @FilePath: /algorithm/src/main/java/com/lynch/max_rectangle_test.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/word-rectangle-lcci
// 给定一份单词的清单，设计一个算法，创建由字母组成的面积最大的矩形，
// 其中每一行组成一个单词 (自左向右)，每一列也组成一个单词 (自上而下)。
// 不要求这些单词在清单里连续出现，但要求所有行等长，所有列等高。
// 如果有多个面积最大的矩形，输出任意一个均可。一个单词可以重复使用。
// 示例 1:

// 输入: ["this", "real", "hard", "trh", "hea", "iar", "sld"]
// 输出:
// [
//    "this",
//    "real",
//    "hard"
// ]

func TestMaxRectangle(t *testing.T) {
	//words := []string{"this", "real", "hard", "trh", "hea", "iar", "sld"}
	words := []string{"eat", "tea", "tan", "ate", "nat", "bat"}
	//words := []string{"aa"}
	result := maxRectangle(words)
	fmt.Println("max area: ", result)
}

var maxMatrix = make([]string, 0)
var maxArea = 0

type TrieNode struct {
	IsWord   bool
	Children map[string]*TrieNode
}

func buildTrie(words []string) *TrieNode {
	root := &TrieNode{}
	root.Children = make(map[string]*TrieNode, 26)
	for _, w := range words {
		temp := root
		for _, c := range w {
			child, ok := temp.Children[string(c)]
			if !ok {
				cur := &TrieNode{}
				cur.Children = make(map[string]*TrieNode, 26)
				temp.Children[string(c)] = cur
				temp = cur
			} else {
				temp = child
			}
		}
		temp.IsWord = true
	}
	return root
}

func backTrace(ws []string, matrix []string, preNodes []*TrieNode) {

	// 矩阵行高不能大于每一行单词的列数
	rowCount := len(matrix)
	if rowCount > 0 {
		if rowCount > len(matrix[0]) {
			return
		}
	}
	rowWord := ""
	if rowCount > 0 {
		rowWord = matrix[rowCount-1]
		if len(rowWord)*rowCount <= maxArea {
			return
		}
	}

	// 判断矩阵没一列是否有效, 有效则直接计算结果， preNodes 会记录上次已经匹配到的节点
	temp := make([]*TrieNode, 0)

	for j := 0; j < len(rowWord); j++ {
		c := rowWord[j]
		if preNodes[j] == nil || len(preNodes[j].Children) == 0 {
			return
		}
		r, ok := preNodes[j].Children[string(c)]
		// 若某一列字符不匹配则说明该矩阵无效
		if !ok {
			return
		}
		temp = append(temp, r)
	}
	if len(temp) > 0 {
		preNodes = temp
		// 若都已达到叶子节点，则计算矩阵面积
		wordEnd := true
		for j := 0; j < len(rowWord); j++ {
			if !preNodes[j].IsWord {
				wordEnd = false
				break
			}
		}
		if wordEnd {
			area := len(rowWord) * rowCount
			if area > maxArea {
				maxArea = area
				maxMatrix = append([]string{}, matrix...)
			}
		}
	}

	for x := 0; x < len(ws); x++ {
		w := ws[x]
		matrix = append(matrix, w)
		backTrace(ws, matrix, preNodes)
		matrix = matrix[0 : len(matrix)-1]
	}
}

func maxRectangle(words []string) []string {
	maxMatrix = make([]string, 0)
	maxArea = 0
	root := buildTrie(words)
	//fmt.Println(root)
	// 将长度相同的字符统一为一组, 方便矩阵面积统计
	lenGroup := make(map[int][]string)
	for _, w := range words {
		list, ok := lenGroup[len(w)]
		if !ok {
			list = make([]string, 0)
		}
		list = append(list, w)
		lenGroup[len(w)] = list
	}

	// 对每一组长度相同的字符进行比较, 符合列要求的即可直接计算出面积
	for wlen, ws := range lenGroup {
		preNodes := make([]*TrieNode, 0, wlen)
		for i := 0; i < wlen; i++ {
			preNodes = append(preNodes, root)
		}
		matrix := make([]string, 0)
		backTrace(ws, matrix, preNodes)
	}

	return maxMatrix
}
