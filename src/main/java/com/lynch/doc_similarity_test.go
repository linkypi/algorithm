/*
 * @Author: LinkyPi trouble.linky@gmail.com
 * @Date: 2024-01-27 10:55:54
 * @LastEditors: LinkyPi trouble.linky@gmail.com
 * @LastEditTime: 2024-01-27 17:08:01
 * @FilePath: /algorithm/src/main/java/com/lynch/doc_similarity.go
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
package main

import (
	"fmt"
	"testing"
)

func TestSimilarity(t *testing.T) {
	docs := [][]int{
		{14, 15, 100, 9, 3},
		{32, 1, 9, 3, 5},
		{15, 29, 2, 6, 8, 7},
		{7, 10},
	}
	result := computeSimilarities(docs)
	fmt.Println(result)
}

func computeSimilarities(docs [][]int) []string {
	docMap := make(map[int]map[int]struct{})
	samemMap := make(map[int]map[int]int)
	for did, doc := range docs {
		for _, w := range doc {
			cmap, ok := docMap[w]
			if !ok {
				docMap[w] = make(map[int]struct{})
			} else {
				if samemMap[did] == nil {
					samemMap[did] = make(map[int]int, 0)
				}
				for k, _ := range cmap {
					samemMap[did][k]++
				}
			}
			docMap[w][did] = struct{}{}
		}
	}

	result := make([]string, 0)
	// 开始比较两个文档相似性
	for id1, doc1 := range docs {
		for id2 := id1 + 1; id2 < len(docs); id2++ {
			same := samemMap[id2][id1]
			doc2 := docs[id2]
			if same == 0 {
				continue
			}
			sml := float64(same) / float64(len(doc1)+len(doc2)-same)
			// 通过float 结果加上 1e-9，我们可以弥补浮点数运算中可能出现的精度误差
			// 例如 0.1 + 0.2 的输出结果可能不是 0.3 , 为了使得结果更加精确就有必要加上 1e-9
			// 否则 leedcode 上提交不会通过
			resultStr := fmt.Sprintf("%d,%d: %.4f", id1, id2, sml+1e-9)
			result = append(result, resultStr)
		}
	}
	return result
}
