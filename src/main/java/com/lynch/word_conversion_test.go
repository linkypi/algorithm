package main

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/word-transformer-lcci
// 给定字典中的两个词，长度相等。写一个方法，把一个词转换成另一个词，
// 但是一次只能改变一个字符。每一步得到的新词都必须能在字典中找到。
// 编写一个程序，返回一个可能的转换序列。如有多个可能的转换序列，你可以返回任何一个。
//
// 示例 1:
// 输入:
// beginWord = "hit",  endWord = "cog",
// wordList = ["hot","dot","dog","lot","log","cog"]
// 输出:
// ["hit","hot","dot","lot","log","cog"]
//
// 示例 2:
// 输入:
// beginWord = "hit" endWord = "cog"
// wordList = ["hot","dot","dog","lot","log"]
// 输出: []
// 解释: endWord "cog" 不在字典中，所以不存在符合要求的转换序列。
func TestWordConversion(t *testing.T) {
	//beginWord := "hit"
	//endWord := "cog"
	//wordList := []string{"hot", "dot", "dog", "lot", "log", "cog"}

	//beginWord := "a"
	//endWord := "c"
	//wordList := []string{"a", "b", "c"}

	//beginWord := "hot"
	//endWord := "dog"
	//wordList := []string{"hot", "dog"}

	//beginWord := "hot"
	//endWord := "dog"
	//wordList := []string{"hot", "dog", "dot"}

	beginWord := "hot"
	endWord := "dog"
	wordList := []string{"hot", "cog", "dog", "tot", "hog", "hop", "pot", "dot"}

	//ladders := findLadders2(beginWord, endWord, wordList)
	ladders := findLaddersWithGraph(beginWord, endWord, wordList)
	fmt.Println(">>>>>> result: ", ladders)
}

func findLadders2(beginWord string, endWord string, wordList []string) []string {
	var list []string
	set := make(map[string]bool)
	for _, word := range wordList {
		set[word] = true
	}
	if !set[endWord] {
		return list
	}

	queue := make([][]string, 0)
	list = append(list, beginWord)
	queue = append(queue, list)
	delete(set, beginWord)

	for len(queue) > 0 {
		size := len(queue)
		for size > 0 {
			curPath := queue[0]
			queue = queue[1:]
			curWord := curPath[len(curPath)-1]

			for i := 0; i < len(curWord); i++ {
				ch := []rune(curWord)
				temp := ch[i]

				for j := 'a'; j <= 'z'; j++ {
					if j == temp {
						continue
					}
					ch[i] = j
					nextWord := string(ch)

					if set[nextWord] {
						newPath := append([]string{}, curPath...)
						newPath = append(newPath, nextWord)
						delete(set, nextWord)

						if nextWord == endWord {
							return newPath
						} else {
							queue = append(queue, newPath)
						}
					}
				}
				ch[i] = temp
			}
			size--
		}
	}
	return []string{}
}

type Set struct {
	m map[string]struct{}
}

func (s *Set) add(key string) {
	s.m[key] = struct{}{}
}
func (s *Set) containKey(key string) bool {
	_, ok := s.m[key]
	return ok
}
func (s *Set) delete(key string) {
	delete(s.m, key)
}
func NewSet() Set {
	return Set{
		m: make(map[string]struct{}),
	}
}
func CopySet(set Set) Set {
	ns := Set{
		m: make(map[string]struct{}),
	}
	for k, _ := range set.m {
		ns.add(k)
	}
	return ns
}

type Node struct {
	path []string
	word string
	set  Set
}

func findLadders(beginWord string, endWord string, wordList []string) []string {
	if wordList == nil || len(wordList) == 0 {
		return []string{}
	}
	set := NewSet()
	for _, w := range wordList {
		set.add(w)
	}

	if !set.containKey(endWord) {
		return []string{}
	}

	set.delete(beginWord)
	paths := make([][]string, 0)
	queue := make([]Node, 0)
	n := Node{word: beginWord, path: make([]string, 0), set: set}
	n.path = append(n.path, beginWord)
	queue = append(queue, n)
	match := false
begin:
	for len(queue) > 0 {
		node := queue[len(queue)-1]
		queue = queue[0 : len(queue)-1]
		// 尝试替换每一个位置上的字符，随后查看替换后的字符是否在单词表中有出现
		for x := 0; x < len(node.word); x++ {
			chars := []rune(node.word)
			temp := chars[x]
			for i := 'a'; i <= 'z'; i++ {
				if i == temp {
					continue
				}
				chars[x] = i
				nw := string(chars)
				if nw == endWord {
					match = true
					node.path = append(node.path, nw)
					paths = append(paths, node.path)
					goto begin
				}
				// 不能出现重复
				if node.set.containKey(nw) && nw != node.word {
					// 移除当前字符串，并加入新匹配的字符串
					n := Node{word: nw, path: make([]string, 0), set: CopySet(node.set)}
					n.path = append(n.path, node.path...)
					n.path = append(n.path, nw)
					n.set.delete(nw)
					queue = append(queue, n)
					match = true
				}
				chars[x] = temp
			}
		}
		if !match {
			return []string{}
		}
	}

	//fmt.Println("count: ", len(paths))
	//
	//for _, path := range paths {
	//	fmt.Println(path)
	//}

	if len(paths) > 0 {
		return paths[0]
	}
	return []string{}
}

type GNode struct {
	word string
	// 记录各个端点的边
	edges map[int]struct{}
}

var nodeId = 0
var wordMaps = make(map[string]int)
var idToWord = make(map[int]string)
var nodes = make(map[int]GNode)
var edges = make(map[int]map[int]struct{})

func findLaddersWithGraph(beginWord string, endWord string, wordList []string) []string {
	// 每个单词仅可对其中一个字符做替换，故每个单词都可以拆解为多个通配符模式，如 hot 可以拆分为 "*ot", "h*t", "ho*"
	// 而 dot 同样也可以拆分为多个通配符模式，为此从中就可以发现 hot 与 dot 可以通过 "*ot" 相连，这就形成了联通图
	// 其中 hot及dot就是图中的端点，而他们对应的通配符也就形成了边
	if wordList == nil || len(wordList) == 0 {
		return []string{}
	}
	for _, w := range wordList {
		nodeId++
		wordMaps[w] = nodeId
		n := GNode{edges: make(map[int]struct{}), word: w}
		nodes[nodeId] = n
		idToWord[nodeId] = w
	}

	nodeId++
	wordMaps[beginWord] = nodeId
	startId := nodeId
	idToWord[startId] = beginWord
	n := GNode{edges: make(map[int]struct{}), word: beginWord}
	nodes[startId] = n

	for _, w := range wordList {
		for j := 0; j < len(w); j++ {
			chars := []rune(w)
			temp := chars[j]
			chars[j] = '*'

			nw := string(chars)
			nwId, ok := wordMaps[nw]
			if !ok {
				nodeId++
				nwId = nodeId
				wordMaps[nw] = nwId
			}

			curId := wordMaps[w]
			if edges[nwId] == nil {
				edges[nwId] = make(map[int]struct{})
			}
			if edges[curId] == nil {
				edges[curId] = make(map[int]struct{})
			}
			edges[nwId][curId] = struct{}{}
			edges[curId][nwId] = struct{}{}
			nodes[curId].edges[nwId] = struct{}{}

			chars[j] = temp
		}
	}

	if _, ok := wordMaps[endWord]; !ok {
		return []string{}
	}
	endId := wordMaps[endWord]
	idToWord[endId] = endWord
	n = GNode{edges: make(map[int]struct{}), word: endWord}
	nodes[endId] = n
	edges[endId] = make(map[int]struct{})

	// 图构建完成后， 问题就转换为从 startId 走到 endId 有多少条路可走，输出任意一条即可
	if _, ok := edges[endId]; !ok {
		return []string{}
	}

	paths := make([][]int, 0)
	queue := make([][]int, 0)
	queue = append(queue, []int{startId})
	for len(queue) > 0 {
		path := queue[len(queue)-1]
		start := path[len(path)-1]
		queue = queue[0 : len(queue)-1]
		n, ok := nodes[start]
		if !ok {
			continue
		}

		// 遍历当前节点n的所有边
		for e, _ := range n.edges {
			// 根据边 e 找到其对应的另一端点 nid
			for nid, _ := range edges[e] {
				if nid == start {
					continue
				}
				exist := false
				for _, item := range path {
					if item == nid {
						exist = true
						break
					}
				}
				if exist {
					continue
				}
				cp := append(path, nid)
				if nid == endId {
					paths = append(paths, append([]int{}, cp...))
					continue
				}
				queue = append(queue, cp)
			}
		}
	}

	if len(paths) == 0 {
		return []string{}
	}

	result := make([][]string, len(paths))
	for i, path := range paths {
		result[i] = make([]string, 0, len(path))
		for _, id := range path {
			result[i] = append(result[i], idToWord[id])
		}
	}
	fmt.Println(result)
	return result[0]
}
