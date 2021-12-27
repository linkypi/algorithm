package com.lynch.binary_tree.common;

import com.lynch.binary_tree.RedBlackTree;
import lombok.Getter;
import lombok.Setter;

/**
 * Description algorithm
 * Created by troub on 2021/5/27 14:09
 */
@Getter
@Setter
public class RBTreeNode<K extends Comparable<?>, V> extends TreeNode<K, V> implements Comparable<RBTreeNode<K, V>> {

    RBTreeNode<K,V> left, right, parent;
    K key;
    V value;
    int color;

    public static final int RED = 1;
    public static final int BLACK = 0;

    public RBTreeNode(K key, V value) {
        super(key,value);
        this.key = key;
        this.value = value;
        this.color = RED;
    }

    public RBTreeNode(K key) {
        super(key);
        this.key = key;
        this.value = value;
        this.color = RED;
    }

    public RBTreeNode<K,V> getParent() {
        return this.parent == null ? null : this.parent;
    }

    public RBTreeNode<K,V> getLeft() {
        return left == null ? null : left;
    }

    public RBTreeNode<K,V> getRight() {
        return right == null ? null : right;
    }

    @Override
    public int compareTo(RBTreeNode<K, V> item) {
        if(item.getKey() instanceof Integer){
            return (Integer)this.key - (Integer) item.key;
        }
        return this.key.toString().compareTo(item.key.toString());
    }
}