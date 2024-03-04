package binary_tree

import (
	"fmt"
	"testing"
)

/**
 * @author: lynch
 * @description: 使用非递归方式对二叉树进行后序遍历
 * @createTime: 2024/2/27 10:40
 */

func TestPostOrderWithoutRecursion(t *testing.T) {

	root := TreeNode{value: "h"}
	left := TreeNode{value: "a"}
	right := TreeNode{value: "b"}

	m := TreeNode{value: "m"}
	x := TreeNode{value: "x"}
	left.left = &m
	left.right = &x
	root.left = &left
	root.right = &right

	result := preOrder(&root)
	fmt.Println("pre order:", result)

	result = inOrder(&root)
	fmt.Println("in order:", result)

	result = postOrder(&root)
	fmt.Println("post order:", result)
}

type TreeNode struct {
	left, right *TreeNode
	value       string
}

func postOrder(root *TreeNode) []string {
	if root == nil {
		return []string{}
	}
	stack := make([]*TreeNode, 0, 8)
	stack = append(stack, root)
	result := make([]string, 0, 8)
	for len(stack) > 0 {
		node := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		// 结果必须放到队列头部，即先放入根，再放入右，最后放入左，最后得到的结果即为左右根
		result = append([]string{node.value}, result...)
		if node.left != nil {
			stack = append(stack, node.left)
		}
		if node.right != nil {
			stack = append(stack, node.right)
		}
	}
	return result
}

// 基于栈实现先序遍历
func preOrder(root *TreeNode) []string {
	if root == nil {
		return []string{}
	}
	stack := make([]*TreeNode, 0, 8)
	stack = append(stack, root)
	result := make([]string, 0, 8)
	for len(stack) > 0 {
		node := stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		result = append(result, node.value)
		if node.right != nil {
			stack = append(stack, node.right)
		}
		if node.left != nil {
			stack = append(stack, node.left)
		}
	}
	return result
}

// 基于栈实现中序遍历
func inOrder(root *TreeNode) []string {
	if root == nil {
		return []string{}
	}
	stack := make([]*TreeNode, 0, 8)
	result := make([]string, 0, 8)
	cur := root
	for len(stack) > 0 || cur != nil {
		for cur != nil {
			stack = append(stack, cur)
			cur = cur.left
		}
		cur = stack[len(stack)-1]
		stack = stack[:len(stack)-1]
		result = append(result, cur.value)
		cur = cur.right
	}
	return result
}
