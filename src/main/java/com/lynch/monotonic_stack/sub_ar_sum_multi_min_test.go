package monotonic_stack

import (
	"fmt"
	"math"
	"testing"
)

/**
* @author: linxueqi
* @Description: 给定一个只包含正整数的数组 arr, arr 中任何一个子数组 sub
*              一定都可以算出 (sub累加和) * (sub中的最小值). 那么所有子数组中，这个最大值是多少
* @createTime: 2024/2/25 14:59
 */
func TestSubArrSumMultiMinWithNoDuplicate(t *testing.T) {
	arr := []int{4, 5, 6, 3, 5, 8, 10}
	result := findMaxInUnDuplicateElement(arr)
	fmt.Println(result)
}

func TestSubArrSumMultiMinWithDuplicate(t *testing.T) {
	arr := []int{3, 2, 3, 4, 4, 3, 1}
	result := findMaxInDuplicateElement(arr)
	fmt.Println(result)
}

// 数组中存在重复元素
func findMaxInDuplicateElement(arr []int) int {
	if arr == nil || len(arr) == 0 {
		return 0
	}

	// stack[i][j] 一维用于存放数组下标，二维用于存放重复元素下标的集合
	stack := make([][]int, 0, len(arr))
	result := make([][]int, len(arr))
	for i := 0; i < len(arr); i++ {
		result[i] = make([]int, 2)
	}
	sum := make([]int, len(arr))
	for i := 0; i < len(arr); i++ {
		if i == 0 {
			sum[i] = arr[i]
		} else {
			sum[i] = sum[i-1] + arr[i]
		}
	}

	for i := 0; i < len(arr); i++ {
		for len(stack) > 0 {
			// 查询集合是否存在元素，若存在元素则判断当前元素是否小于尾部元素是否
			// 若小于则说明遇到右边离当前元素
			list := stack[len(stack)-1]
			if len(list) > 0 && arr[i] < arr[list[len(list)-1]] {
				top := list[len(list)-1]
				result[top][1] = i
				// 统计完成后移除元素
				list = list[:len(list)-1]
				stack[len(stack)-1] = list

				top2 := -1
				if len(list) == 0 {
					// 若当前栈中已经没有重复元素，那么可以直接移除栈顶，
					// 移除最新的栈顶元素即是原来栈顶元素top的左边距离最近比top小的数
					stack = stack[:len(stack)-1]
					if len(stack) > 0 {
						ls := stack[len(stack)-1]
						top2 = ls[len(ls)-1]
					}
				} else if len(stack) > 1 {
					// 若当前栈中存在重复元素，那么可以不能直接移除栈顶，
					// 栈顶的下一个元素即是原来栈顶元素top的左边距离最近比top小的数
					ls := stack[len(stack)-2]
					top2 = ls[len(ls)-1]
				}
				result[top][0] = top2
			} else {
				break
			}
		}

		// 栈为空则直接加入
		if len(stack) == 0 {
			stack = append(stack, []int{i})
			continue
		}
		// 栈不为空则判断栈顶是否为空，为空也直接加入
		if len(stack[len(stack)-1]) == 0 {
			stack = append(stack, []int{i})
			continue
		}

		// 最后判断集合是否存在相同元素，存在则加入集合，不存在则直接入栈
		list := stack[len(stack)-1]
		if arr[i] == arr[list[len(list)-1]] {
			list = append(list, i)
			stack[len(stack)-1] = list
			continue
		}
		stack = append(stack, []int{i})
	}

	for len(stack) > 0 {
		list := stack[len(stack)-1]
		if len(list) == 0 {
			continue
		}
		top := list[len(list)-1]
		result[top][1] = -1

		// 统计完成后移除元素
		list = list[:len(list)-1]
		stack[len(stack)-1] = list

		top2 := -1
		if len(list) == 0 {
			stack = stack[:len(stack)-1]
			if len(stack) > 0 {
				ls := stack[len(stack)-1]
				top2 = ls[len(ls)-1]
			}
		} else if len(stack) > 1 {
			ls := stack[len(stack)-2]
			top2 = ls[len(ls)-1]
		}
		result[top][0] = top2
	}

	return calcMax(arr, sum, result)
}

// 数组中不存在重复元素
func findMaxInUnDuplicateElement(arr []int) int {

	if arr == nil || len(arr) == 0 {
		return 0
	}
	sum := make([]int, len(arr))
	for i := 0; i < len(arr); i++ {
		if i == 0 {
			sum[i] = arr[i]
		} else {
			sum[i] = sum[i-1] + arr[i]
		}
	}
	// 维护从小到大递增的单调栈
	stack := make([]int, 0)
	result := make([][]int, len(arr))
	for i := 0; i < len(arr); i++ {
		result[i] = make([]int, 2)
	}
	for right := 0; right < len(arr); right++ {
		// 4 5 6, 栈不断递增，直到右边出现一个比栈底元素都小的数后开始
		// 统计每个元素左边离它最近比它小的数 以及 每个元素右边离它最近比它小的数
		for len(stack) > 0 && arr[right] < arr[stack[0]] {
			top := stack[len(stack)-1]
			// 记录右边离它最近比它小的数
			result[top][1] = right
			stack = stack[:len(stack)-1]
			// 记录左边离它最近比它小的数
			if len(stack) == 0 {
				result[top][0] = -1
			} else {
				result[top][0] = stack[len(stack)-1]
			}
		}
		stack = append(stack, right)
	}

	for len(stack) > 0 {
		top := stack[len(stack)-1]
		// 记录右边离它最近比它小的数， 由于右边已经没有数据，所以是-1
		result[top][1] = -1
		stack = stack[:len(stack)-1]
		if len(stack) == 0 {
			result[top][0] = -1
		} else {
			result[top][0] = stack[len(stack)-1]
		}
	}

	return calcMax(arr, sum, result)
}

func calcMax(arr, sum []int, result [][]int) int {
	max := math.MinInt
	for i, narr := range result {
		// 左闭右闭区间 [left, right]
		// 结果即为： arr[left] * (sum[right]-sum[left-1])
		left := narr[0]
		// 若左边没有小于当前的元素，则说明只能从当前下标开始
		if left == -1 {
			left = i
		} else {
			// 由于是闭区间，所以需要+1
			left = left + 1
		}
		right := narr[1]
		min := arr[left]
		// 右边已经没有比当前值更小的数，说明当前值的右边都是大于当前值的数，并且他们都是连续递增的数
		if right == -1 {
			right = len(result) - 1
		} else {
			// 由于是闭区间，所以需要-1
			right = right - 1
		}
		res := 0
		if left == 0 {
			res = min * sum[right]
		} else {
			res = min * (sum[right] - sum[left-1])
		}
		max = int(math.Max(float64(res), float64(max)))
	}
	return max
}
