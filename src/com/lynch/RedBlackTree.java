package com.lynch;

public class RedBlackTree<K,V> {
    private static final int RED = 1;
    private static final int BLACK = 1;

    private Node<K, V> root = null;

    final static class Node<K, V> implements Comparable<Node<K, V>> {
        K key;
        V value;
        int color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        public Node(K key, V value, int color) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = null;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node<K, V> item) {
            return this.key.toString().compareTo(item.key.toString());
        }
    }

    public void insert(K key, V value) {
        Node<K, V> item = new Node<>(key, value);
        Node<K, V> node = insert0(item);
        check(node);
    }

    public void insert(Node<K, V> item) {
        Node<K, V> node = insert0(item);
        check(node);
    }

    /**
     * 插入节点默认为红色节点，根节点除外
     *
     * @param item
     * @return
     */
    private Node<K, V> insert0(Node<K, V> item) {
        // 若没有根节点则直接新增
        if (root == null) {
            item.color = BLACK;
            root = item;
            return root;
        }

        if (item.key == null) {
            throw new NullPointerException("key must be not null.");
        }
        Node<K, V> parent = root;
        while (parent != null) {
            final int result = parent.compareTo(item);
            if (result > 0) {
                // 右子树为空则插入
                if (parent.right == null) {
                    parent.right = item;
                    item.parent = parent;
                    break;
                }
                parent = parent.right;
            } else if (result < 0) {
                // 左子树为空则插入
                if (parent.left == null) {
                    parent.left = item;
                    item.parent = parent;
                    break;
                }
                parent = parent.left;
            } else {
                parent.value = item.value;
                break;
            }
        }

        // 1. 若当前处理根节点只有新增节点，则上黑下红
        // 2. 若当前处理根节点只有新增节点，则上黑下红

    }



    /**
     * 1. 2-3-4树：新增
     * @param node
     */
    private void adjust(Node<K,V> node){

    }

    private void check(Node node) {
        Node parent = node.parent;
        // 若插入节点的父节点为红色，并且父节点的兄弟节点同为红色 则调整颜色
        // 1. 将父节点及父节点的兄弟节点的颜色改为黑色
        // 2. 将祖父节点的颜色改为红色
        Node brotherOfParent = getOtherBrother(parent);
        if (parent.color == RED && brotherOfParent.color == RED) {
            parent.color = BLACK;
            brotherOfParent.color = BLACK;
            parent.parent.color = RED;
        }

        // 若上述条件满足则继续往上层查找
    }

    private Node getOtherBrother(Node node){
        Node parent = node.parent;
        if(parent.left == node){
            return parent.right;
        }
        return parent.left;
    }

    /**
     * 若当前节点及其父系节点同为红色，父节点的兄弟节点为为黑色，且当前节点为右子树则执行左旋：
     *         pf                       pf                         pf
     *         |                        |                          |
     *         P                      *P*                        *Pr*
     *      /    \         标记      /    \         旋转         /    \
     *     Pl      Pr     >>>     Pl     *Pr*      >>>       *P*      rr
     *           /   \                  /   \               /   \
     *         rl     rr              *rl*   rr           Pl    *rl*
     *  休要调整的节点特点在其前后加上了 *
     *  1. 移动 Pr 的左子树, Pr左子树变为 P 的右子树，同时Pr左子树的父节点为 P
     *  2. P 的父节点为 Pr，Pr的父节点为P的父节点, Pr的左子树为P
     *  3. P 父节点的其中一个孩子节点指向 Pr
     * @param p
     */
    public void leftRotate(Node p) {

        if (p == null) return;

        // 调整 P 节点
        Node pr = p.right;
        Node rl = pr.left;
        p.right = rl;

        // 调整 lr 节点
        if(pr.left!=null){
            rl.parent = p;
        }

        // 调整 Pr 节点
        pr.parent = p.parent;
        p.parent = pr;
        if(p.parent==null){
            root = pr;
            pr.parent = null;
        }
        // 如果P是左子树，则将P父亲节点pf的左子树指向新节点 pr
        else if(p.parent.left == p){
            p.parent.left = pr;
        }
        // 如果P是右子树，则将P父亲节点pf的右子树指向新节点 pr
        else if(p.parent.right == p){
            p.parent.right = pr;
        }
    }

    /**
     * 若当前节点及其父系节点同为红色，父节点的兄弟节点为为黑色，且当前节点为右子树则执行左旋：
     *         pf                       pf                         pf
     *         |                        |                          |
     *         P                      *P*                        *Pl*
     *      /    \         标记      /    \        旋转          /    \
     *     Pl      Pr     >>>     *Pl*    Pr      >>>         rl     *P*
     *   /   \                   /   \                              /   \
     * rl     rr               rl   *rr*                          *rr*   Pr
     *  休要调整的节点特点在其前后加上了 *
     *  1. 移动 Pl 的右子树, Pl 的右子树变为 P 的左子树，同时Pl右子树的父节点为 P
     *  2. P 的父节点为 Pr，Pl的父节点为P的父节点, Pl的左子树为P
     *  3. P 父节点的其中一个孩子节点指向 Pl
     * @param p
     */
    public void rightRotate(Node p) {

        if (p == null) return;

        // 调整 P 节点
        Node pl = p.left;
        Node rr = pl.right;
        p.left = rr;

        // 调整 rr 节点
        if(pl.right!=null){
            rr.parent = p;
        }

        // 调整 Pl 节点
        pl.parent = p.parent;
        p.parent = pl;
        if(p.parent==null){
            root = pl;
            pl.parent = null;
        }
        // 如果P是左子树，则将P父亲节点pf的左子树指向新节点 pl
        else if(p.parent.left == p){
            p.parent.left = pl;
        }
        // 如果P是右子树，则将P父亲节点pf的右子树指向新节点 pl
        else if(p.parent.right == p){
            p.parent.right = pl;
        }
    }
























}
