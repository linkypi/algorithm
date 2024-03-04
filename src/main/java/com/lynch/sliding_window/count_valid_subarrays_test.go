package sliding_window

import (
	"fmt"
	"testing"
)

// 给定一个数组及 num，若其子数组最大值 max - 子数组最小值 min <= num 则认为该子数组达标，求达标子数组的个数
func TestCountValidSubArr(t *testing.T) {
	nums := []int{8, 2, 4, 7}
	num := 4
	result := countValidSubArr(nums, num)
	fmt.Println("达标子数组的个数：", result)
}

func countValidSubArr(arr []int, num int) int {
	if arr == nil || len(arr) == 0 {
		return 0
	}

	minQueue := make([]int, 0)
	maxQueue := make([]int, 0)
	left := -1
	result := 0
	for i := 0; i < len(arr); i++ {
		// 维护maxQueue 从大到小的单调递减的双端队列
		for len(maxQueue) > 0 && arr[i] >= arr[maxQueue[len(maxQueue)-1]] {
			maxQueue = maxQueue[:len(maxQueue)-1]
		}
		maxQueue = append(maxQueue, i)

		// 维护minQueue 从小到大的单调递增的双端队列
		for len(minQueue) > 0 && arr[i] < arr[minQueue[len(minQueue)-1]] {
			minQueue = minQueue[:len(minQueue)-1]
		}
		minQueue = append(minQueue, i)

		for arr[maxQueue[0]]-arr[minQueue[0]] > num {
			// 开始收缩窗口
			left++
			for len(maxQueue) > 0 && maxQueue[0] <= left {
				maxQueue = maxQueue[1:]
			}
			for len(minQueue) > 0 && minQueue[0] <= left {
				minQueue = minQueue[1:]
			}
		}
		result += i - left + 1
	}
	return result
}
