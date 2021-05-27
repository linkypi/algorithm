package com.lynch.binary_tree;

import com.lynch.binary_tree.common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

/**
 * Description algorithm
 * Created by troub on 2021/5/27 13:40
 */
class BTreePrinterTest {

    private static TreeNode<Integer,Integer> test1() {
        TreeNode<Integer,Integer> root = new TreeNode<>(2);
        TreeNode<Integer,Integer> n11 = new TreeNode<>(7);
        TreeNode<Integer,Integer> n12 = new TreeNode<>(5);
        TreeNode<Integer,Integer> n21 = new TreeNode<>(2);
        TreeNode<Integer,Integer> n22 = new TreeNode<>(6);
        TreeNode<Integer,Integer> n23 = new TreeNode<>(3);
        TreeNode<Integer,Integer> n24 = new TreeNode<>(6);
        TreeNode<Integer,Integer> n31 = new TreeNode<>(5);
        TreeNode<Integer,Integer> n32 = new TreeNode<>(8);
        TreeNode<Integer,Integer> n33 = new TreeNode<>(4);
        TreeNode<Integer,Integer> n34 = new TreeNode<>(5);
        TreeNode<Integer,Integer> n35 = new TreeNode<>(8);
        TreeNode<Integer,Integer> n36 = new TreeNode<>(4);
        TreeNode<Integer,Integer> n37 = new TreeNode<>(5);
        TreeNode<Integer,Integer> n38 = new TreeNode<>(8);

        root.setLeft(n11);
        root.setRight(n12);

        n11.setLeft(n21);
        n11.setRight(n22);
        n12.setLeft(n23);
        n12.setRight(n24);

        n21.setLeft(n31);
        n21.setRight(n32);
        n22.setLeft(n33);
        n22.setRight(n34);

        n23.setLeft(n35);
        n23.setRight(n36);
        n24.setLeft(n37);
        n24.setRight(n38);

        return root;
    }

    private static TreeNode<Integer,Integer> test2() {
        TreeNode<Integer, Integer> root = new TreeNode<>(2);
        TreeNode<Integer, Integer> n11 = new TreeNode<>(7);
        TreeNode<Integer, Integer> n12 = new TreeNode<>(5);
        TreeNode<Integer, Integer> n21 = new TreeNode<>(2);
        TreeNode<Integer, Integer> n22 = new TreeNode<>(6);
        TreeNode<Integer, Integer> n23 = new TreeNode<>(9);
        TreeNode<Integer, Integer> n31 = new TreeNode<>(5);
        TreeNode<Integer, Integer> n32 = new TreeNode<>(8);
        TreeNode<Integer, Integer> n33 = new TreeNode<>(4);


        root.setLeft(n11);
        root.setRight(n12);

        n11.setLeft(n21);
        n11.setRight(n22);

        n12.setRight(n23);
        n22.setLeft(n31);
        n22.setRight(n32);

        n23.setLeft(n33);

        return root;
    }

    public static void main(String[] args) {

        BTreePrinter.printNode(test1(), (node)->{
            if(node.getValue() == 5){
                System.out.print(ansi().fg(RED).a(node.getValue()).reset());
            }else{
                System.out.print(node.getValue());
            }
        });
        BTreePrinter.printNode(test2());

    }
}

//class Node<T extends Comparable<?>> {
//    Node<T> left, right;
//    T data;
//
//    public Node(T data) {
//        this.data = data;
//    }
//}

public class BTreePrinter {

    public static <K extends Comparable<?>, V> void printNode(TreeNode<K,V> root) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel, null);
    }

    public static <K extends Comparable<?>, V> void printNode(TreeNode<K,V> root, Consumer<TreeNode<K,V>> consumer) {
        int maxLevel = maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel, consumer);
    }

    private static <K extends Comparable<?>, V> void printNodeInternal(List<TreeNode<K,V>> nodes, int level, int maxLevel, Consumer<TreeNode<K,V>> consumer) {
        if (nodes.isEmpty() || isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        printWhitespaces(firstSpaces);

        List<TreeNode<K,V>> newNodes = new ArrayList<>();
        for (TreeNode<K,V> node : nodes) {
            if (node != null) {
                if (consumer != null) {
                    consumer.accept(node);
                } else {
                    System.out.print(node.getValue());
                }

                newNodes.add(node.getLeft());
                newNodes.add(node.getRight());
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).getLeft() != null)
                    System.out.print("/");
                else
                    printWhitespaces(1);

                printWhitespaces(i + i - 1);

                if (nodes.get(j).getRight() != null)
                    System.out.print("\\");
                else
                    printWhitespaces(1);

                printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel, consumer);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <K extends Comparable<?>, V> int maxLevel(TreeNode<K,V> node) {
        if (node == null)
            return 0;

        return Math.max(maxLevel(node.getLeft()), maxLevel(node.getRight())) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}