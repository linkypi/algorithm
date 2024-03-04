package dp

import (
	"fmt"
	"testing"
)

/**
 * 求最长回文子序列长度
 * https://leetcode.cn/problems/longest-palindromic-subsequence/description/
 */
func TestLongestPalindromeSubseq(t *testing.T) {
	str := "bbbab"
	result := count(str)
	fmt.Println(result)
}

func count(str string) int {
	if str == "" {
		return 0
	}

	n := len(str)
	// dp[i][j] 表示从i到j之间的子串最长回文子序列长度
	// 1. 若str[i] == str[j] 那么 dp[i][j] = dp[i+1][j-1] + 2
	// 2. 若str[i] != str[j] 那么 dp[i][j] = max(dp[i+1][j], dp[i][j-1])
	dp := make([][]int, n)
	for i := range dp {
		dp[i] = make([]int, n)
	}

	for i := 0; i < n; i++ {
		dp[i][i] = 1
	}

	// 仅需遍历左下三角部分
	for k := 1; k < n; k++ {
		j := k
		for i := 0; i < n-k; i++ {
			if str[i] == str[j] {
				if i+1 == j {
					dp[i][j] = 2
				} else {
					dp[i][j] = dp[i+1][j-1] + 2
				}
			} else {
				if dp[i+1][j] > dp[i][j-1] {
					dp[i][j] = dp[i+1][j]
				} else {
					dp[i][j] = dp[i][j-1]
				}
			}
			j++
		}
	}

	return dp[0][n-1]
}
