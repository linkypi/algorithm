package string

import (
	"fmt"
	"testing"
)

/*
  - @author: lynch
  - @description: AC自动机，用于实现字符串多模式匹配问题，如在对文章中敏感词过滤。单模式字符串匹配算法都可以处理该问题，如 KMP。
    但是对于访问量巨大的网站来说，比如淘宝，用户每天的评论数有几亿、甚至几十亿。这时候，对敏感词过滤系统的性能要求就要很高。
  - @createTime: 2024/2/28 15:01
*/
func TestACAutomaton(t *testing.T) {
	arr := []string{"hers", "his", "he", "she"}
	content := "ahishers"
	auto := ACAutomaton{root: &Node{children: make([]*Node, N)}}
	auto.build(arr)
	result := auto.query(content)
	fmt.Println(result)
}

type ACAutomaton struct {
	root *Node
}

type Node struct {
	// 以当前字符结尾的字符串长度都有哪些
	exist []int
	// 以当前字符结尾的字符串是否是一个完整单词
	isEnd bool
	// 父节点的 fail 指针
	fail     *Node
	children []*Node
}

const N = 26

func (a *ACAutomaton) build(strs []string) {

	// 构建Trie树
	for _, str := range strs {
		tmp := a.root
		for j := 0; j < len(str); j++ {
			path := str[j] - 'a'
			child := tmp.children[path]
			if child == nil {
				child = &Node{children: make([]*Node, N)}
			}
			tmp.children[path] = child
			tmp = child
			if j == len(str)-1 {
				tmp.isEnd = true
				if tmp.exist == nil {
					tmp.exist = make([]int, 0)
				}
				tmp.exist = append(tmp.exist, j+1)
			}
		}
	}

	queue := make([]*Node, 0, N)
	// 规定首层的 fail 指针一定指向 root
	for i := 0; i < N; i++ {
		child := a.root.children[i]
		if child == nil {
			continue
		}
		child.fail = a.root
		queue = append(queue, child)
	}

	for len(queue) > 0 {
		node := queue[0]
		queue = queue[1:]

		for i := 0; i < N; i++ {
			tmp := node
			child := tmp.children[i]
			if child == nil {
				continue
			}
			// 若child的父节点对应的fail指针存在，并且其子树不存在[i]这个子树则继续往上查找，直到查到根节点为止
			for tmp.fail != nil && tmp.fail.children[i] == nil {
				tmp = tmp.fail
			}

			if tmp.fail == nil {
				// 已经追溯到根节点仍未找到[i]子树
				child.fail = a.root
			} else {
				// 若父节点对应的fail节点存在[i]分支则直接赋值，否则child只能指向根节点
				child.fail = tmp.fail.children[i]
			}
			// 将fail节点中的exist数据复制到当前节点，以实现多模式匹配，减少查询次数
			if len(child.fail.exist) > 0 {
				if child.exist == nil {
					child.exist = make([]int, 0, N)
				}
				child.exist = append(child.exist, child.fail.exist...)
			}
			queue = append(queue, child)
		}
	}
}

func (a *ACAutomaton) query(content string) []string {
	if content == "" || len(content) == 0 {
		return nil
	}

	result := make([]string, 0, N)
	tmp := a.root
	for i := 0; i < len(content); i++ {
		path := content[i] - 'a'
		child := tmp.children[path]
		// 若存在路径则继续往下
		if child != nil {
			tmp = child
			if tmp.isEnd && len(child.exist) > 0 {
				for j := 0; j < len(child.exist); j++ {
					leng := child.exist[j]
					start := i - leng + 1
					result = append(result, content[start:start+leng])
				}
			}
			continue
		}
		// 若不存在路径则通过其fail指针来寻找
		// 若child的父节点对应的fail指针存在，并且其子树不存在[i]这个子树则继续往上查找，直到查到根节点为止
		for tmp.fail != nil && tmp.fail.children[path] == nil {
			tmp = tmp.fail
		}
		if tmp.fail == nil {
			tmp = a.root
			continue
		}
		tmp = tmp.fail.children[path]
		if tmp.isEnd && len(child.exist) > 0 {
			for j := 0; j < len(child.exist); j++ {
				leng := child.exist[j]
				start := i - leng + 1
				result = append(result, content[start:start+leng])
			}
		}

	}
	return result
}
