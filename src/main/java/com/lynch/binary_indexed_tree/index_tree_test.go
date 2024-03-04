package binary_indexed_tree

import "testing"

/**
 * @author: lynch
 * @description:
 * @createTime: 2024/2/26 22:00
 */
func testIndexTree(t *testing.T) {

}

// 0位置弃而不用！
// 树状数组即索引树非常适合单点更新后获取累积和的情况
type IndexTree struct {
	tree []int
}

func newIndexTree(n int) IndexTree {
	return IndexTree{
		tree: make([]int, n+1),
	}
}

// 更新数组数组某个节点的值，由于节点已更新故其后继节点即父节点同样需要更新
// 以便后续可以通过复杂度O(lgN)来获得其累加和
func (it *IndexTree) update(index, val int) {
	for index <= len(it.tree) {
		it.tree[index] += val
		index += index & (-index)
	}
}

func (it *IndexTree) getSum(index int) int {
	res := 0
	for index > 0 {
		res += it.tree[index]
		index -= index & (-index)
	}
	return res
}
