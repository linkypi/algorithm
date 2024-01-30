package maxsum

import (
	"fmt"
	"testing"
)

// https://leetcode.cn/problems/max-submatrix-lcci
// 给定一个正整数、负整数和 0 组成的 N × M 矩阵，编写代码找出元素总和最大的子矩阵。
// 返回一个数组 [r1, c1, r2, c2]，其中 r1, c1 分别代表子矩阵左上角的行号和列号，
// r2, c2 分别代表右下角的行号和列号。若有多个满足条件的子矩阵，返回任意一个均可。
// 注意：本题相对书上原题稍作改动
func TestMaxSumOfMatrix(t *testing.T) {
	matrix := [][]int{{9, -8, 1, 3, -2}, {-3, 7, 6, -2, 4}, {6, -4, -4, 8, -7}}
	res := getMaxMatrix(matrix)
	fmt.Println(res)
}

func getMaxMatrix(matrix [][]int) [][]int {
	rows := len(matrix)
	cols := len(matrix[0])

	for r := 0; r < rows; r++ {
		sum := make([]int, cols)
		for k := r; k < rows; k++ {
			for c := 0; c < cols; c++ {
				sum[c] = sum[c] + matrix[k][c]
			}
			max, start, end := getMaxSum(sum)
			if maxSum < max {
				maxSum = max
				result = [][]int{{r, start}, {k, end}}
			}
		}
	}
	return result
}

var maxSum int
var result = make([][]int, 0)

func getMaxSum(arr []int) (int, int, int) {
	sum := 0
	start := 0
	max := 0
	res := make([]int, 3)
	for c, v := range arr {
		if sum < 0 {
			sum = v
			start = c
		} else {
			sum += v
		}

		if sum > max {
			max = sum
			res = []int{max, start, c}
		}
	}
	return res[0], res[1], res[2]
}
