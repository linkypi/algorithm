package string

import (
	"fmt"
	"testing"
)

/*
*

  - @author: lynch

  - @description: https://leetcode.cn/problems/stream-of-characters/description/
    设计一个算法：接收一个字符流，并检查这些字符的后缀是否是字符串数组 words 中的一个字符串。
    例如，words = ["abc", "xyz"] 且字符流中逐个依次加入 4 个字符 'a'、'x'、'y' 和 'z' ，你所设计的算法应当
    可以检测到 "axyz" 的后缀 "xyz" 与 words 中的字符串 "xyz" 匹配。按下述要求实现 StreamChecker 类：
    StreamChecker(String[] words) ：构造函数，用字符串数组 words 初始化数据结构。
    boolean query(char letter)：从字符流中接收一个新字符，如果字符流中的任一非空后缀能匹配 words 中的某一
    字符串，返回 true ；否则，返回 false。

    提示：
    1 <= words.length <= 2000
    1 <= words[i].length <= 200
    words[i] 由小写英文字母组成
    letter 是一个小写英文字母
    最多调用查询 4 * 104 次

  - @createTime: 2024/2/29 0:08
*/
func TestStreamChecker(t *testing.T) {
	//words := []string{"abc", "xyz"}
	//letters := []byte{'x', 'a', 'b', 'c', 'x', 'y', 'z'}

	//words := []string{"cd", "kl", "f"}
	//letters := []byte{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l'}

	// [false,false,false,false,false,true, true, true, true, true, false,false, true, true,
	//  true, true, false,false,false,true, true, true, true, true, true, false,true, true, true, false]
	//words := []string{"ab", "ba", "aaab", "abab", "baa"}
	//letters := []byte{'a', 'a', 'a', 'a', 'a', 'b', 'a', 'b', 'a', 'b', 'b', 'b', 'a', 'b',
	//	'a', 'b', 'b', 'b', 'b', 'a', 'b', 'a', 'b', 'a', 'a', 'a', 'b', 'a', 'a', 'a'}

	// [false,true,true,false,true,false,true,true,false,true,false,true,false,false,false,true,true,false,
	// false,false,false,true,true,true,true,false,false,false,true,true,true,true,false,false,false,false,
	// true,true,false,false,false,true,false,false,false,false,false,false,false,true]
	words := []string{"baa", "aa", "aaaa", "abbbb", "aba"}
	letters := []byte{'a', 'a', 'a', 'b', 'a', 'b', 'a', 'a', 'b', 'a', 'b', 'a', 'b',
		'b', 'a', 'a', 'a', 'b', 'b', 'a', 'b', 'a', 'a', 'a', 'a', 'b', 'b', 'a', 'a', 'a', 'a', 'a',
		'b', 'b', 'a', 'b', 'a', 'a', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'b', 'a', 'a'}
	checker := Constructor(words)
	result := make([]bool, 0, len(letters))
	for _, letter := range letters {
		res := checker.Query(letter)
		result = append(result, res)
	}
	fmt.Println(result)
}

const Num = 26

type ACNode struct {
	isEnd    bool    // 表示是否已经到达字符串尾部
	fail     *ACNode // 失配指针
	children []*ACNode
}
type StreamChecker struct {
	root    *ACNode
	current *ACNode
}

func Constructor(words []string) StreamChecker {
	streamChecker := StreamChecker{root: &ACNode{children: make([]*ACNode, Num)}}
	if words == nil || len(words) == 0 {
		return streamChecker
	}

	// 构建Trie树
	for _, word := range words {
		tmp := streamChecker.root
		for _, char := range word {
			p := char - 'a'
			child := tmp.children[p]
			if child == nil {
				child = &ACNode{children: make([]*ACNode, Num)}
			}
			tmp.children[p] = child
			tmp = child
		}
		tmp.isEnd = true
	}

	// 指定第一层的fail失配指针指向root
	queue := make([]*ACNode, 0, Num)
	for i := 0; i < Num; i++ {
		child := streamChecker.root.children[i]
		if child != nil {
			child.fail = streamChecker.root
			queue = append(queue, child)
		}
	}

	// 层次遍历
	for len(queue) > 0 {
		node := queue[0]
		queue = queue[1:]

		for i := 0; i < Num; i++ {
			tmp := node
			child := tmp.children[i]
			if child == nil {
				continue
			}

			// 默认失配指针指向root
			child.fail = streamChecker.root
			// 若父节点的失配指针不为空
			for tmp.fail != nil {
				// 若父节点的失配指针存在子树【i】那么当前child的失配指针就指向该子树
				if tmp.fail.children[i] != nil {
					child.fail = tmp.fail.children[i]
					// 若某个节点已经是结尾则复制，对于多模式匹配的需求同样需要复制exist数组
					if tmp.fail.children[i].isEnd {
						child.isEnd = tmp.fail.children[i].isEnd
					}
					break
				}
				// 否则继续往上寻找，直到根节点root
				tmp = tmp.fail
			}
			queue = append(queue, child)
		}
	}
	streamChecker.current = streamChecker.root
	return streamChecker
}

func (s *StreamChecker) Query(letter byte) bool {
	p := letter - 'a'
	// 若经过多次搜索后发现已经没有路径可走，此时只能从失配指针寻找, 一直寻找到根节点为止
	for s.current.children[p] == nil && s.current != s.root {
		s.current = s.current.fail
	}

	// 1. 通过失配指针已经到达根节点，说明查找失败
	// 2. 通过失配指针可以找到下一节点，说明查找成功
	child := s.current.children[p]
	if child == nil {
		child = s.root
	}

	s.current = child
	if child.isEnd {
		return true
	}
	return false
}
