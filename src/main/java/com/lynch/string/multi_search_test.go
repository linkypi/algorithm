package string

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/multi-search-lcci
// 给定一个较长字符串 big 和一个包含较短字符串的数组 smalls，设计一个方法，根据 smalls 中的每一个较短字符串，
// 对 big 进行搜索。输出 smalls 中的字符串在 big 里出现的所有位置 positions，其中 positions[i] 为 smalls[i] 出现的所有位置。
//
// 示例：
// 输入：
// big = "mississippi"
// smalls = ["is","ppi","hi","sis","i","ssippi"]
// 输出： [[1,4],[8],[],[3],[1,4,7,10],[5]]
func TestMultiSearch(t *testing.T) {
	big := "mississippi"
	smalls := []string{"is", "ppi", "hi", "sis", "i", "ssippi"}
	//result := multiSearchWithDynamicTrie(big, smalls)
	result := multiSearchWithStaticTrie(big, smalls)
	fmt.Println(result)
}

func multiSearchWithStaticTrie(big string, smalls []string) [][]int {
	trie := newTrie()
	for _, small := range smalls {
		trie.add(small)
	}

	chars := []byte(big)
	hit := make(map[string][]int)
	for i, _ := range chars {
		res := trie.search(string(chars[i:]))
		if len(res) == 0 {
			continue
		}

		for _, e := range res {
			list, ok := hit[e]
			if !ok {
				list = make([]int, 0, 8)
			}
			list = append(list, i)
			hit[e] = list
		}
	}

	res := make([][]int, len(smalls))
	for i, small := range smalls {
		res[i] = make([]int, 0, 8)
		list, ok := hit[small]
		if ok {
			res[i] = append(res[i], list...)
		}
	}
	return res
}

const MAX = 100000

type Trie struct {
	// 为树上每个分支进行编号，通常从1开始编号，实际有多少分支可通过count得知
	// 同时亦可通过该参数来统计实际使用内存
	count int
	// 实际存储的字典树，一维表示分支路径，二维表示26个字母组成的子路径
	tree [MAX][26]int
	// 有多少单词经过此路径，用于统计单词前缀出现次数，其下标即为分支编号count
	pass [MAX]int
	// 有多少单词到此结束，用于统计某个单词出现次数，其下标即为分支编号count
	end [MAX]int
}

func newTrie() Trie {
	return Trie{count: 1}
}

func (t *Trie) add(word string) {
	cur := 1
	t.pass[cur]++
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			t.count++
			t.tree[cur][c] = t.count
		}
		cur = t.tree[cur][c]
		t.pass[cur]++
	}
	t.end[cur]++
}

func (t *Trie) delete(word string) {
	if !t.exist(word) {
		return
	}

	cur := 1
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		t.pass[t.tree[cur][c]]--
		if t.pass[t.tree[cur][c]] == 0 {
			t.tree[cur][c] = 0
			break
		}
		cur = t.tree[cur][c]
	}
	t.end[cur]--
}

func (t *Trie) exist(word string) bool {
	if len(word) == 0 {
		return false
	}
	cur := 1
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			return false
		}
		cur = t.tree[cur][c]
	}
	return true
}

func (t *Trie) search(word string) []string {
	cur := 1
	his := make([]string, 0, 8)
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			break
		}
		// 注意 cur = t.tree[cur][c] 必须先于 t.end[cur] != 0 执行
		cur = t.tree[cur][c]
		if t.end[cur] != 0 {
			his = append(his, word[0:i+1])
		}
	}
	return his
}

func multiSearchWithDynamicTrie(big string, smalls []string) [][]int {
	trie := MyTrie{}
	for _, small := range smalls {
		trie.add(small)
	}

	hit := make(map[string][]int)
	for i, _ := range big {
		res := trie.search(big[i:])
		if len(res) == 0 {
			continue
		}

		for _, e := range res {
			list, ok := hit[e]
			if !ok {
				list = make([]int, 0, 8)
			}
			list = append(list, i)
			hit[e] = list
		}
	}

	result := make([][]int, len(smalls))

	for i, w := range smalls {
		list, ok := hit[w]
		if !ok {
			result[i] = make([]int, 0, 0)
			continue
		}
		result[i] = make([]int, 0, len(list))
		result[i] = append(result[i], list...)
	}
	return result
}

type MyTrie struct {
	root *MyTrieNode
}

type MyTrieNode struct {
	isEnd    bool
	children [26]*MyTrieNode
}

func (m *MyTrie) add(word string) {
	if m.root == nil {
		m.root = &MyTrieNode{}
	}
	if len(word) == 0 {
		return
	}
	cur := m.root
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		child := cur.children[c]
		if child == nil {
			child = &MyTrieNode{}
		}
		cur.children[c] = child
		cur = child
	}
	cur.isEnd = true
}

func (m *MyTrie) search(word string) []string {
	if len(word) == 0 {
		return []string{}
	}

	res := make([]string, 0, 8)
	cur := m.root

	for j, e := range word {
		c := e - 'a'
		child := cur.children[c]
		if child == nil {
			break
		}
		if child.isEnd {
			res = append(res, word[:j+1])
		}
		cur = child
	}

	return res
}
