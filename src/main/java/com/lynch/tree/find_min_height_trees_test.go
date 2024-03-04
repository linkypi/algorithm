package tree

import "testing"

/*
*
  - @author: lynch
  - @description: https://leetcode.cn/problems/minimum-height-trees/description/
    树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
    给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），
    其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
    可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。
    在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
    请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
    树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
    示例 1：
    输入：n = 4, edges = [[1,0],[1,2],[1,3]]
    输出：[1]
    解释：如图所示，当根是标签为 1 的节点时，树的高度是 1 ，这是唯一的最小高度树。
  - @createTime: 2024/3/2 18:20
*/
func TestFindMinHeightTrees(t *testing.T) {

}

func findMinHeightTrees(n int, edges [][]int) []int {
	adj := make([][]int, n)
	for i := 0; i < n; i++ {
		adj[i] = make([]int, 0, n)
	}
	for _, edge := range edges {
		x := edge[0]
		y := edge[1]
		adj[x] = append(adj[x], y)
		adj[y] = append(adj[y], x)
	}

	size := make([]int, n) // size[i] 表示以i节点为根时树的高度
	var dfs func(start, dep, parent int)
	dfs = func(start, dep, parent int) {
		if size[0] < dep {
			size[0] = dep
		}
		for _, item := range adj[start] {
			if item != parent {
				dfs(item, dep+1, start)
			}
		}
	}
	dfs(0, 0, -1)

	var reroot func(start, parent int)
	reroot = func(start, parent int) {
		for _, item := range adj[start] {
			size[item] = size[start] - 1
			reroot(item, start)
		}
	}

	reroot(0, -1)
	return nil
}
