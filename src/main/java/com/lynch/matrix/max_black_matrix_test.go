package matrix

import (
	"fmt"
	"testing"
)

/**
 * https://leetcode.cn/problems/max-black-square-lcci/
 * 给定一个方阵，其中每个单元 (像素) 非黑即白。设计一个算法，找出 4 条边皆为黑色像素的最大子方阵。
 * 返回一个数组 [r, c, size] ，其中r,c分别代表子方阵左上角的行号和列号，
 * size 是子方阵的边长。若有多个满足条件的子方阵，返回 r 最小的，若 r 相同，
 * 返回 c 最小的子方阵。若无满足条件的子方阵，返回空数组。
 */
func TestMaxBlackMatrix(t *testing.T) {
	//matrix := [][]int{
	//	{1, 0, 1, 0, 1, 0, 1},
	//	{0, 0, 1, 1, 1, 0, 1},
	//	{0, 0, 1, 0, 0, 0, 1},
	//	{1, 1, 1, 0, 1, 0, 1},
	//	{1, 0, 1, 0, 0, 0, 1},
	//}
	matrix := [][]int{
		{0, 1, 1},
		{1, 0, 1},
		{1, 1, 0}}
	res := Find(matrix)
	fmt.Println(res)
}

func Find(matrix [][]int) []int {
	if matrix == nil || len(matrix) == 0 {
		return []int{}
	}
	rows := len(matrix)
	cols := len(matrix[0])

	// 分别记录水平方向与竖直方向上出现连续0的个数
	horient := make([][]int, rows)
	vorient := make([][]int, cols)

	for i := 0; i < rows; i++ {
		horient[i] = make([]int, cols)
	}
	for j := 0; j < cols; j++ {
		vorient[j] = make([]int, rows)
	}

	for r := 0; r < rows; r++ {
		for c := 0; c < cols; c++ {
			if c == 0 && matrix[r][c] == 0 {
				horient[r][c] = 1
			} else if c > 0 && matrix[r][c] == 0 {
				horient[r][c] = horient[r][c-1] + 1
			}
		}
	}

	for c := 0; c < cols; c++ {
		for r := 0; r < rows; r++ {
			if r == 0 && matrix[r][c] == 0 {
				vorient[c][r] = 1
			} else if r > 0 && matrix[r][c] == 0 {
				vorient[c][r] = vorient[c][r-1] + 1
			}
		}
	}

	x, y := 0, 0
	max := -1
	for r := rows - 1; r >= 0; r-- {
		for c := cols - 1; c >= 0; c-- {
			xlen := horient[r][c]

			// 找到最小边长
			if xlen > vorient[c][r] {
				xlen = vorient[c][r]
			}
			if xlen == 0 {
				continue
			}
			// 根据边长确认其他两条对称边是否可以匹配
			preX := c - xlen + 1
			preY := r - xlen + 1
			if preX < cols && horient[preY][c] >= xlen && preY < rows && vorient[preX][r] >= xlen {
				if max < xlen {
					max = xlen
					x = preY
					y = preX
				}
			}
		}
	}
	return []int{x, y, max}
}
