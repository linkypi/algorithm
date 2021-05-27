package com.lynch.binary_tree.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Description algorithm
 * Created by troub on 2021/5/27 14:09
 */
@Getter
@Setter
public class TreeNode <K extends Comparable<?>, V> {
    TreeNode<K,V> left, right;
    K key;
    V value;

    public TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public TreeNode(K key) {
        this.key = key;
        this.value = (V) key;
    }
}