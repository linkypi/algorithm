package com.lynch.binary_tree;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import  com.lynch.binary_tree.common.TreeNode;

/**
 * @author: lynch
 * @description: 使用非递归方式对二叉树进行遍历
 * @date: 2024/2/27 11:26
 */
public class PostOrder {

    @Test
    public void test(){
        TreeNode<String, String> root = new TreeNode<>("p");
        TreeNode<String, String> left = new TreeNode<>("a");
        TreeNode<String, String> right = new TreeNode<>("b");
        TreeNode<String, String> a1 = new TreeNode<>("t");
        TreeNode<String, String> a2 = new TreeNode<>("c");
        left.setLeft(a1);
        left.setRight(a2);
        root.setLeft(left);
        root.setRight(right);
        List<String> list = postorderTraversal(root);
        System.out.print(list);
    }
    public List<String> postorderTraversal(TreeNode<String, String> root) {
        LinkedList<String> output = new LinkedList<>();
        if (root == null) {
            return output;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            root = stack.pop();
            output.addFirst(root.getKey());
            if (root.getLeft() != null) {
                stack.push(root.getLeft());
            }
            if (root.getRight() != null) {
                stack.push(root.getRight());
            }
        }
        return output;
    }
}
