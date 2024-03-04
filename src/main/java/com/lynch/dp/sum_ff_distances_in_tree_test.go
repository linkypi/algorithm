package dp

import (
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description: https://leetcode.cn/problems/sum-of-distances-in-tree/description/
	给定一个无向、连通的树。树中有 n 个标记为 0...n-1 的节点以及 n-1 条边 。
	给定整数 n 和数组 edges ， edges[i] = [ai, bi] 表示树中的节点 ai 和 bi 之间有一条边。
	返回长度为 n 的数组 answer ，其中 answer[i] 是树中第 i 个节点与所有其他节点之间的距离之和。

	示例 1:
	输入: n = 6, edges = [[0,1],[0,2],[2,3],[2,4],[2,5]]
	输出: [8,12,6,10,10,10]
	解释: 树如图所示。
	我们可以计算出 dist(0,1) + dist(0,2) + dist(0,3) + dist(0,4) + dist(0,5)
	也就是 1 + 1 + 2 + 2 + 2 = 8。 因此，answer[0] = 8，以此类推。
 * @createTime: 2024/3/2 15:34
*/

func TestSumOfDistancesInTree(t *testing.T) {
	//n := 6
	//edges := [][]int{{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}}

	n := 2
	edges := [][]int{{1, 0}}

	result := sumOfDistancesInTree(n, edges)
	fmt.Println(result)
}

// 换根 DP  https://leetcode.cn/problems/sum-of-distances-in-tree/solutions/2345592/tu-jie-yi-zhang-tu-miao-dong-huan-gen-dp-6bgb/
func sumOfDistancesInTree(n int, edges [][]int) []int {
	// 首先求出邻接表
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

	ans := make([]int, n)
	size := make([]int, n)

	// 使用dfs求得ans[0], 以及每个节点的子节点个数size[i]
	var dfs func(start int, dep, parent int)
	dfs = func(start int, dep, parent int) {
		ans[0] += dep
		size[start] = 1 // start 节点本身算一个子节点
		for _, item := range adj[start] {
			if item != parent {
				dfs(item, dep+1, start)
				size[start] += size[item]
			}
		}
	}
	dfs(0, 0, -1)

	// 假设 x 为 y 的父节点, n为节点个数，那么 size[i]为y的子节点个数，n - size[y] 为y的非子节点个数，
	// ans[y] = ans[x] + n - size[y] - size[i] =  ans[x] + n - 2*size[y]
	// 上面已经求出 ans[0] 那么只要使用dfs 按顺序一层层往下遍历即可得到 ans[i] 的值
	var dfs2 func(start, parent int)
	dfs2 = func(start, parent int) {
		for _, item := range adj[start] {
			if parent == item { // 避免访问父节点
				continue
			}
			ans[item] = ans[start] + n - 2*size[item]
			dfs2(item, start)
		}
	}
	dfs2(0, -1)
	return ans
}
