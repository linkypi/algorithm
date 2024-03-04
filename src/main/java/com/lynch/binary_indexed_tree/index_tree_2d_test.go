package binary_indexed_tree

import (
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description:
 * @createTime: 2024/2/26 22:00
 */
func TestIndexTree2D(t *testing.T) {
	matrix := [][]int{
		{3, 0, 1, 4, 2},
		{5, 6, 3, 2, 1},
		{1, 2, 0, 1, 5},
		{4, 1, 0, 1, 7},
		{1, 0, 3, 0, 5},
	}
	//indexTree2D := NewIndexTree2DInternal(matrix)
	indexTree2D := newIndexTree2D(matrix)
	result := indexTree2D.sumRegion(2, 1, 4, 3)
	fmt.Println(result) // 8

	indexTree2D.update(3, 2, 2)

	result = indexTree2D.sumRegion(2, 1, 4, 3)
	fmt.Println(result) // 10
}

// 树状数组即索引树非常适合单点更新后获取累积和的情况
type IndexTree2D struct {
	rows, cols int
	nums       [][]int
	tree       [][]int
}

func newIndexTree2D(matrix [][]int) IndexTree2D {
	if len(matrix) == 0 || len(matrix[0]) == 0 {
		return IndexTree2D{}
	}
	rows := len(matrix)
	cols := len(matrix[0])
	tree := make([][]int, rows+1)
	for i := 0; i < rows+1; i++ {
		tree[i] = make([]int, cols+1)
	}
	nums := make([][]int, rows)
	for i := 0; i < rows; i++ {
		nums[i] = make([]int, cols)
	}
	indexTree2D := IndexTree2D{
		rows: rows + 1,
		cols: cols + 1,
		nums: nums,
		tree: tree,
	}
	for i := 0; i < rows; i++ {
		for j := 0; j < cols; j++ {
			indexTree2D.update(i, j, matrix[i][j])
		}
	}

	return indexTree2D
}

// 从一维树状数组调整为二维树状数组
// 更新数组数组某个节点的值，由于节点已更新故其后继节点即父节点同样需要更新
// 以便后续可以通过复杂度O(lgN)来获得其累加和
func (it *IndexTree2D) update(row, col, val int) {
	if it.rows == 0 || it.cols == 0 {
		return
	}
	delta := val - it.nums[row][col]
	it.nums[row][col] = val
	for i := row + 1; i < it.rows; i += i & (-i) {
		for j := col + 1; j < it.cols; j += j & (-j) {
			it.tree[i][j] += delta
		}
	}
}

func (it *IndexTree2D) getSum(row, col int) int {
	res := 0
	for i := row + 1; i > 0; i -= i & (-i) {
		for j := col + 1; j > 0; j -= j & (-j) {
			res += it.tree[i][j]
		}
	}
	return res
}

// (r1,c1) 为左下角， (r2,c2)为右上角
func (it *IndexTree2D) sumRegion(r1, c1, r2, c2 int) int {
	if it.rows == 0 || it.cols == 0 {
		return 0
	}
	return it.getSum(r2, c2) - it.getSum(r2, c1-1) - it.getSum(r1-1, c2) + it.getSum(r1-1, c1-1)
}

// https://github.com/algorithmzuo/algorithmbasic2020/blob/master/src/class32/Code02_IndexTree2D.java
type IndexTree2DInternal struct {
	tree [][]int
	nums [][]int
	N    int
	M    int
}

func NewIndexTree2DInternal(matrix [][]int) *IndexTree2DInternal {
	if len(matrix) == 0 || len(matrix[0]) == 0 {
		return nil
	}
	N := len(matrix)
	M := len(matrix[0])
	tree := make([][]int, N+1)
	nums := make([][]int, N)
	for i := range tree {
		tree[i] = make([]int, M+1)
	}
	for i := range nums {
		nums[i] = make([]int, M)
	}
	it := &IndexTree2DInternal{tree: tree, nums: nums, N: N, M: M}
	for i := 0; i < N; i++ {
		for j := 0; j < M; j++ {
			it.update(i, j, matrix[i][j])
		}
	}
	return it
}

func (it *IndexTree2DInternal) sum(row, col int) int {
	sum := 0
	for i := row + 1; i > 0; i -= i & (-i) {
		for j := col + 1; j > 0; j -= j & (-j) {
			sum += it.tree[i][j]
		}
	}
	return sum
}

func (it *IndexTree2DInternal) update(row, col, val int) {
	if it.N == 0 || it.M == 0 {
		return
	}
	add := val - it.nums[row][col]
	it.nums[row][col] = val
	for i := row + 1; i <= it.N; i += i & (-i) {
		for j := col + 1; j <= it.M; j += j & (-j) {
			it.tree[i][j] += add
		}
	}
}

func (it *IndexTree2DInternal) sumRegion(row1, col1, row2, col2 int) int {
	if it.N == 0 || it.M == 0 {
		return 0
	}
	return it.sum(row2, col2) + it.sum(row1-1, col1-1) - it.sum(row1-1, col2) - it.sum(row2, col1-1)
}
