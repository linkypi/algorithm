package string

import (
	"fmt"
	"testing"
)

/*
*

  - @author: lynch

  - @description: https://leetcode.cn/problems/minimum-time-to-revert-word-to-initial-state-ii/
    给你一个下标从 0 开始的字符串 word 和一个整数 k 。
    在每一秒，你必须执行以下操作：
    移除 word 的前 k 个字符。
    在 word 的末尾添加 k 个任意字符。
    注意 添加的字符不必和移除的字符相同。但是，必须在每一秒钟都执行 两种 操作。
    返回将 word 恢复到其 初始 状态所需的 最短 时间（该时间必须大于零）。

    示例 1：
    输入：word = "abacaba", k = 3
    输出：2
    解释：
    第 1 秒，移除 word 的前缀 "aba"，并在末尾添加 "bac" 。因此，word 变为 "cababac"。
    第 2 秒，移除 word 的前缀 "cab"，并在末尾添加 "aba" 。因此，word 变为 "abacaba" 并恢复到始状态。
    可以证明，2 秒是 word 恢复到其初始状态所需的最短时间。

    示例 2：
    输入：word = "abacaba", k = 4
    输出：1
    解释：
    第 1 秒，移除 word 的前缀 "abac"，并在末尾添加 "caba" 。因此，word 变为 "abacaba" 并恢复到初始状态。
    可以证明，1 秒是 word 恢复到其初始状态所需的最短时间。

    示例 3：
    输入：word = "abcbabcd", k = 2
    输出：4
    解释：
    每一秒，我们都移除 word 的前 2 个字符，并在 word 末尾添加相同的字符。
    4 秒后，word 变为 "abcbabcd" 并恢复到初始状态。
    可以证明，4 秒是 word 恢复到其初始状态所需的最短时间。

    提示：
    1 <= word.length <= 106
    1 <= k <= word.length
    word 仅由小写英文字母组成。

  - @createTime: 2024/3/2 12:26
*/
func TestMinimumTimeToInitialState(t *testing.T) {
	//str := "abacaba"
	//k := 3

	str := "abcbabcd"
	k := 2
	count := minimumTimeToInitialState(str, k)
	fmt.Println(count)
}

// 基于Z算法实现 https://www.bilibili.com/video/BV1it421W7D8/?p=97&spm_id_from=pageDriver
func minimumTimeToInitialState(str string, k int) int {
	z := make([]int, len(str))
	n := len(str)
	for i, l, r := 1, 0, 0; i < n; i++ {
		if i <= r { // 在z-box范围内
			z[i] = z[i-l]
			if z[i] > r-i+1 {
				z[i] = r - i + 1
			}
		}

		for i+z[i] < n && str[i+z[i]] == str[z[i]] {
			z[i]++
		}
		l = i
		r = i + z[i] - 1
		// 如 word = "abacaba", k = 4，移除前缀 abac 后剩下后缀aba, 而后缀aba刚好就是整个字符的前缀
		// 所以只要在一秒内执行删除前缀abac，然后在追加 aba 即可
		if i%k == 0 && z[i] >= n-i {
			return i / k
		}
	}
	return (n-1)/k + 1
}
