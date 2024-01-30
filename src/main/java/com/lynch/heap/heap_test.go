package heap

import (
	"fmt"
	"testing"
)

func TestHeap(t *testing.T) {

	arr := []int{8, 3, 9, 1, 7, 5}
	buildHeap(arr, len(arr))

	heapSize := 0
	n := len(arr)
	for i := 0; i < len(arr); i++ {
		arr[0], arr[n-1-heapSize] = arr[n-1-heapSize], arr[0]
		heapSize++
		buildHeap(arr, n-heapSize)
	}

	fmt.Println(arr)
}

func buildHeap(arr []int, size int) {
	if arr == nil {
		return
	}

	if size == 0 {
		return
	}
	end := size>>1 - 1
	for i := end; i >= 0; i-- {
		left := 2*i + 1
		right := 2*i + 2
		max := left
		if right < size && arr[left] < arr[right] {
			max = right
		}
		if arr[i] < arr[max] {
			arr[i], arr[max] = arr[max], arr[i]
		}
	}
}
