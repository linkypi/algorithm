package tree

import (
	"fmt"
	"testing"
)

/*
*

  - @author: lynch

  - @description: https://leetcode.cn/problems/minimum-edge-reversals-so-every-node-is-reachable/description/
    给你一个 n 个点的 简单有向图 （没有重复边的有向图），节点编号为 0 到 n - 1 。如果这些边是双向边，那么这个图形成一棵树 。
    给你一个整数 n 和一个 二维 整数数组 edges ，其中 edges[i] = [ui, vi] 表示从节点 ui 到节点 vi 有一条有向边 。
    边反转 指的是将一条边的方向反转，也就是说一条从节点 ui 到节点 vi 的边会变为一条从节点 vi 到节点 ui 的边。
    对于范围 [0, n - 1] 中的每一个节点 i ，你的任务是分别 独立 计算 最少 需要多少次 边反转 ，从节点 i 出发经过 一系列有向边 ，可以到达所有的节点。
    请你返回一个长度为 n 的整数数组 answer ，其中 answer[i] 表示从节点 i 出发，可以到达所有节点的 最少边反转 次数。

    示例 1：
    输入：n = 4, edges = [[2,0],[2,1],[1,3]]
    输出：[1,1,0,2]
    解释：上图表示了与输入对应的简单有向图。
    对于节点 0 ：反转 [2,0] ，从节点 0 出发可以到达所有节点。
    所以 answer[0] = 1 。
    对于节点 1 ：反转 [2,1] ，从节点 1 出发可以到达所有节点。
    所以 answer[1] = 1 。
    对于节点 2 ：不需要反转就可以从节点 2 出发到达所有节点。
    所以 answer[2] = 0 。
    对于节点 3 ：反转 [1,3] 和 [2,1] ，从节点 3 出发可以到达所有节点。
    所以 answer[3] = 2 。

  - @createTime: 2024/3/2 21:35
*/
func TestMinEdgeReversals(t *testing.T) {
	//n := 4
	//edges := [][]int{{2, 0}, {2, 1}, {1, 3}}

	n := 3
	edges := [][]int{{1, 2}, {2, 0}}
	result := minEdgeReversals(n, edges)
	fmt.Println(result)
}

type Pair struct {
	to  int
	dir int // 1 表示边存在，-1表示不存该条边
}

// 使用换根dp求解
func minEdgeReversals(n int, edges [][]int) []int {
	adjs := make([][]Pair, n)
	for _, edge := range edges {
		x := edge[0]
		y := edge[1]
		adjs[x] = append(adjs[x], Pair{to: y, dir: 1})
		adjs[y] = append(adjs[y], Pair{to: x, dir: -1})
	}

	// 找到 ans[0]
	ans := make([]int, n)
	var dfs func(start, parent int)
	dfs = func(start, parent int) {
		for _, pair := range adjs[start] {
			if pair.to == parent {
				continue
			}
			if pair.dir < 0 {
				ans[0]++
			}
			dfs(pair.to, start)
		}
	}
	dfs(0, -1)
	// 根据ans[0]退出其他答案，因为树是从上往下遍历，假如x的子节点为y，那么 ans[y] = ans[x] + dir
	// 即我们可以根据父节点x的答案求得子节点y的答案，x与y差的就是一个方向dir
	var reroot func(start, parent int)
	reroot = func(start, parent int) {
		for _, pair := range adjs[start] {
			to := pair.to
			if to == parent {
				continue
			}
			ans[to] = ans[start] + pair.dir
			reroot(to, start)
		}
	}
	reroot(0, -1)
	return ans
}
