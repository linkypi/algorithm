package com.lynch.disjoint_set;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @author: linxueqi
 * @Description:
 * @createTime: 2022/7/28 22:23
 */
public class UnionSet1 {
    public static void main(String[] args) {

    }

    public static class Node<V>{
        V value;
        public Node(V v){
            this.value = v;
        }
    }

    public static class UnionSet<V>{
        public HashMap<V, Node<V>> nodes;
        // 某个节点与其父节点对应关系
        public HashMap<Node<V>,Node<V>> parents;
        // 代表节点所拥有的子节点个数
        public HashMap<Node<V>,Integer> sizeMap;

        public UnionSet(List<V> values){
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for(V item: values){
                Node<V> node = new Node<>(item);
                nodes.put(item, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V a, V b){
            return findParent(nodes.get(a)) == findParent(nodes.get(b));
        }

        /**
         * 合并两个元素所在的集合
         * @param a
         * @param b
         */
        public void union(V a, V b){
            Node<V> ahead = nodes.get(a);
            Node<V> bhead = nodes.get(b);
            // 若两个元素所在的集合为不同集合则合并
            if(ahead != bhead){
                int aSize = sizeMap.get(ahead);
                int bSize = sizeMap.get(bhead);
                // 将小集合挂到大集合下方
                Node<V> bigger = aSize >= bSize ? ahead: bhead;
                Node<V> smaller = bigger == ahead ? bhead: ahead;
                parents.put(smaller, bigger);
                sizeMap.put(bigger, aSize + bSize);
                sizeMap.remove(smaller);
            }
        }

        public Node<V> findParent(Node<V> current){
            Stack<Node<V>> path = new Stack<>();
            // 若 current 节点的父节点不等于其本身 则继续往上一级搜索
            while(current != parents.get(current)){
                // 记录下搜索的路径
                path.push(current);
                current = parents.get(current);
            }
            // 路径优化：将所有经过的路径压缩，避免一条链过长
            while(!path.isEmpty()){
                parents.put(path.pop(), current);
            }
            return current;
        }
    }
}
