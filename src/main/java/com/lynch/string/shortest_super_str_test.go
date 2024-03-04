package string

import (
	"fmt"
	"math"
	"testing"
)

/*
*

  - @author: lynch

  - @description: 943. 最短超级串  https://leetcode.cn/problems/find-the-shortest-superstring/description/
    给定一个字符串数组 words，找到以 words 中每个字符串作为子字符串的最短字符串。
    如果有多个有效最短字符串满足题目条件，返回其中 任意一个 即可。
    我们可以假设 words 中没有字符串是 words 中另一个字符串的子字符串。

    示例 1：
    输入：words = ["alex","loves","leetcode"]
    输出："alexlovesleetcode"
    解释："alex"，"loves"，"leetcode" 的所有排列都会被接受。

    示例 2：
    输入：words = ["catg","ctaagt","gcta","ttca","atgcatc"]
    输出："gctaagttcatgcatc"

    提示：
    1 <= words.length <= 12
    1 <= words[i].length <= 20
    words[i] 由小写英文字母组成
    words 中的所有字符串 互不相同

  - @createTime: 2024/2/29 16:51
*/
func TestShortestSuperStr(t *testing.T) {
	// output : gctaagttcatgcatc
	//words := []string{"catg", "ctaagt", "gcta", "ttca", "atgcatc"}

	// 该用例无法通过，因为其中的 k 被多次访问，导致部分单词背过滤，原因待查
	words := []string{"lugeuklyt", "thwokzob", "rilsthwokz", "onkrilsthw", "kzobvtr", "krilsthwo"}

	//words := []string{"alex", "loves", "leetcode"}
	result := shortestSuperstring(words)
	fmt.Println(result)
}

// 74 / 84 个通过的测试用例， 基于AC自动机实现
func shortestSuperstring(words []string) string {

	superString := newSuperString(words)

	min := math.MaxInt
	// 分别以words[i]各字符串开头求解最大超串

	child := superString.root
	result := ""
	infos := goNext(*child, 0, len(words), "")
	if len(infos) > 0 {
		for _, info := range infos {
			if min > len(info.str) {
				min = len(info.str)
				result = info.str
			}
		}
	}
	return result
}

func goNext(root ACNode2, ws int, n int, str string) []Info {
	result := make([]Info, 0, LetterCount)

	rootTmp := root
	for k := 0; k < LetterCount; k++ {
		tmp := str
		level := ws
		child := rootTmp.children[k]
		if child == nil {
			continue
		}
		if child.count == 0 {
			continue
		}

		child.count--
		tmp += (string)('a' + k)

		if child.isEnd {
			if level == n {
				child.count++
				result = append(result, Info{node: child, ws: level, str: tmp})
				continue
			}

			level++
			res := goNext(*child.fail, level, n, tmp)
			result = append(result, res...)
			child.count++
			level--
			continue
		}

		res := goNext(*child, level, n, tmp)
		result = append(result, res...)
		child.count++
	}

	return result
}

const LetterCount = 26

type ACNode2 struct {
	count    int
	isEnd    bool     // 表示是否已经到达字符串尾部
	fail     *ACNode2 // 失配指针
	children []*ACNode2
	char     string
}

type Info struct {
	node *ACNode2
	ws   int
	str  string
}
type SuperString struct {
	root    *ACNode2
	current *ACNode2
}

func newSuperString(words []string) SuperString {
	streamChecker := SuperString{root: &ACNode2{children: make([]*ACNode2, LetterCount)}}
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
				child = &ACNode2{children: make([]*ACNode2, LetterCount), count: 1, char: string(char)}
			} else {
				child.count++
			}
			tmp.children[p] = child
			tmp = child
		}
		tmp.isEnd = true
	}

	// 指定第一层的fail失配指针指向root
	queue := make([]*ACNode2, 0, LetterCount)
	for i := 0; i < LetterCount; i++ {
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

		for i := 0; i < LetterCount; i++ {
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
				cfail := tmp.fail.children[i]
				if cfail != nil {
					child.fail = cfail
					// 若某个节点已经是结尾则复制，对于多模式匹配的需求同样需要复制exist数组
					if cfail.isEnd {
						child.isEnd = cfail.isEnd
						child.count += cfail.count
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
