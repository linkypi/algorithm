/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-01-27 18:03:24
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-27 21:44:32
 * @FilePath: /algorithm/src/main/java/com/lynch/max_rectangle_test.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main

import "testing"

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

func TestMaxRetangle(t *testing.T) {

}

type TrieNode struct {
	Leaf     bool
	Children map[int]*TrieNode
}

func find(words []string) {
	root := buildeTrie(words)

	// 将长度相同的字符统一为一组, 方便矩阵面积统计
	lenGroup := make(map[int][]string)
	for _, w := range words {
		list, ok := lenGroup[len(w)]
		if !ok {
			list = make([]string, 0)
		}
		list = append(list, w)
	}

	// 对每一组长度相同的字符进行比较, 符合列要求的即可直接计算出面积
	for len, ws := range lenGroup {
		for i, w1 := range ws {
			for j, w2 := range ws {
				if i == j {
					continue
				}

				// 判断列是否有效, 无效的则忽略

			}
		}
	}

}
func backTrace(ws []string,path []string, used map[int]struct,root *TrieNode){

	Begin:
	for x:=0;x<len(ws);x++{
		w := ws[x]
		if _,ok:=used[x]; ok{
			continue
		}

		used[x] = struct{}
		path = append(path, w)

		// 判断 path 组成的矩阵是否有效, 有效则直接计算结果
		row := len(path)
		for i:=0;i<row;i++{
			var r *TrieNode = nil
			var ok = false
			for j:=0;j<len(path[i]);j++{
			   c := path[i][j]
			   r, ok = root.Children[c]
			   if !ok{
				   x++
				  goto Begin
			   }
			}
		}

		//做选择
        backTrace(ws, used)
		//撤销
		path = path[0:len(path)-1]
		used[i] = nil
	}
}

var maxArea = 0

func dfs(idx int, ws []string, used map[int]struct, root *TrieNode)bool{
	w1 := ws[idx]

	row := 2
	for j, w2 := range ws {
		if _,ok:=used[j]; ok {
			continue
		}
		// 对比 w1, w2 每一列是否都能匹配
        for i:=0;i<len(w1);i++ {
			// 判断列是否有效, 无效的则忽略
            r1,ok := root.Children[w1[i]-'a']
			if !ok{
				return false
			}
			r2,ok := r1.Children[w2[i]-'a']
			if !ok{
				return false
			}
			if r1.Leaf && r2.Leaf{
				area := (i+1)*row
			}
		}
        // w1 w2 可以组成有效矩阵, 则继续从 ws 中取出可以字符串进行匹配
		used[idx] = struct{}
		used[j] = struct{}
		w1 = w2
		row++
	}
}

func buildeTrie(words []string) *TrieNode {
	root := &TrieNode{}
	root.Children = make(map[int]*TrieNode, 26)
	for _, w := range words {
		temp := root
		for _, c := range w {
			x := c - 'a'
			cur := &TrieNode{}
			temp.Children[int(x)] = cur
			temp = cur
		}
		temp.Leaf = true
	}
	return root
}
