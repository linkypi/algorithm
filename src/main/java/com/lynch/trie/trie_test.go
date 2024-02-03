package trie

import "testing"

// 基于静态内存创建字典树
func TestTrie(t *testing.T) {

}

type Trie struct {
	count int
	tree  [MaxN][26]int
	end   [MaxN]int
	pass  [MaxN]int
}

const MaxN = 1000

func (t *Trie) Add(word string) {

	cur := 1 // 表示根节点， 从1出发
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

func (t *Trie) Delete(word string) {
	if t.Search(word) == 0 {
		return
	}
	cur := 1
	for i := 0; i < len(word); i++ {
		t.pass[cur]--
		c := word[i] - 'a'
		if t.pass[cur] == 0 {
			t.tree[cur][c] = 0
			break
		}
		cur = t.tree[cur][c]
	}
	t.end[cur]--
}

func (t *Trie) Search(word string) int {
	if len(word) == 0 {
		return 0
	}
	cur := 1
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			return 0
		}
		cur = t.tree[cur][c]
	}
	return cur
}

func (t *Trie) Count(word string) int {
	cur := 1
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			return 0
		}
		cur = t.tree[cur][c]
	}
	return t.end[cur]
}

func (t *Trie) CountWithPrefix(word string) int {
	cur := 1
	for i := 0; i < len(word); i++ {
		c := word[i] - 'a'
		if t.tree[cur][c] == 0 {
			return 0
		}
		cur = t.tree[cur][c]
	}
	return t.pass[cur]
}
