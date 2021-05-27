package com.lynch.binary_tree;

import com.lynch.binary_tree.common.RBTreeNode;
import com.lynch.binary_tree.common.RBTreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class RedBlackTree<K extends Comparable<?>,V> {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(2,7,5,9,1,12,10,25,19,6,3,5);
        RedBlackTree<Integer,Integer> tree = new RedBlackTree<>();
        for(Integer item : list){
            tree.insert(item);
            print(tree.root);
        }

    }

    private static void print(RBTreeNode root){
        BTreePrinter.printNode(root, (node)->{
            RBTreeNode item = (RBTreeNode)node;
            if(item.getColor() == RBTreeNode.RED){
                System.out.print(ansi().fg(RED).a(node.getValue()).reset());
            }else{
                System.out.print(node.getValue());
            }
        });
    }
    
    private RBTreeNode<K, V> root = null;

    public void insert(K key) {
        RBTreeNode<K, V> item = new RBTreeNode<>(key, (V)key);
        insert(item);
    }

    public void insert(K key, V value) {
        RBTreeNode<K, V> item = new RBTreeNode<>(key, value);
        insert(item);
    }

    public void insert(RBTreeNode<K, V> item) {
        boolean unExistSameNode = insert0(item);
        if(unExistSameNode) {
            adjust(item);
        }
    }

    /**
     * 插入节点默认为红色节点，根节点除外
     *
     * @param item
     * @return 插入已存在元素时返回 false, 其他返回 true
     */
    private boolean insert0(RBTreeNode<K, V> item) {
        // 若没有根节点则直接新增
        if (root == null) {
            item.setColor(RBTreeNode.BLACK);
            root = item;
            return true;
        }

        if (item.getKey() == null) {
            throw new NullPointerException("key must be not null.");
        }
        RBTreeNode<K, V> parent = root;
        while (parent != null) {
            final int result = item.compareTo(parent);
            if (result > 0) {
                // 右子树为空则插入
                if (parent.getRight() == null) {
                    parent.setRight(item);
                    item.setParent(parent);
                    break;
                }
                parent = parent.getRight();
            } else if (result < 0) {
                // 左子树为空则插入
                if (parent.getLeft() == null) {
                    parent.setLeft(item);
                    item.setParent(parent);
                    break;
                }
                parent = parent.getLeft();
            } else {
                parent.setValue(item.getValue());
                return false;
            }
        }

        return true;
    }

    private RBTreeNode<K,V> getParent(RBTreeNode<K,V> node) {
        return node == null ? null : node.getParent();
    }

    private RBTreeNode<K,V> getLeft(RBTreeNode<K,V> node) {
        return node == null ? null : node.getLeft();
    }

    private RBTreeNode<K,V> getRight(RBTreeNode<K,V> node) {
        return node == null ? null : node.getRight();
    }

    /**
     * 注意此处默认返回黑色是因为红色节点的子节点都为黑色
     * @param node
     * @return
     */
    private int getColor(RBTreeNode<K,V> node) {
        return node == null ? RBTreeNode.BLACK : node.getColor();
    }

    private void setColor(RBTreeNode<K,V> node, int color) {
        if (node == null) return;
        node.setColor(color);
    }

    /**
     * 1. 2-3-4树：新增元素 + 2节点合并（节点只有一个元素），合并后等于三节点
     *      红黑树：新增红节点 + 黑色父节点 = 上黑下红 -- 无需调整
     * 2. 2-3-4树：新增元素 + 3节点合并（节点有两个元素），
     *    此时分四种情况： 左三，右三，及两个左中右（无需调整）
     *    红黑树： 新增红色节点 + 上黑下红 = 排序后中间节点为黑色，两边节点为红色
     * 3. 2-3-4树：新增元素 + 4节点合并（节点有三个元素） = 原来4节点分裂，中间节点升级为父节点，新增元素与剩下的其中一个合并
     *    红黑树： 新增红色节点 + 爷爷节点黑色，父节点及父节点的兄弟节点都是红色 = 爷爷节点变为红色，父节点及其兄弟节点变黑色。若爷爷节点不是根节点则继续遍历
     *
     * @param x
     */
    private void adjust(RBTreeNode<K,V> x) {

        /**
         * 只有父节点为红色才需要调整，为黑色则无需调整, 对应的情况是 2 及 3
         */
        while (x != null && x != root && x.getParent().getColor() == RBTreeNode.RED) {

            RBTreeNode<K,V> parent = x.getParent();
            RBTreeNode<K,V> grandPa = getParent(parent);
            // 1. 左三情况，即x的父节点为爷爷的左子树
            if (parent == getLeft(grandPa)){

                final RBTreeNode<K, V> uncle = getBrother(parent);
                if(getColor(uncle) == RBTreeNode.RED){
                    // 若父节点的兄弟节点也为红色 则调整父节点、兄弟节点颜色，及祖父节点颜色
                    setColor(parent,RBTreeNode.BLACK);
                    setColor(uncle,RBTreeNode.BLACK);
                    setColor(parent.getParent(),RBTreeNode.RED);
                    x = grandPa; // 向上递归
                }else {
                    /* 若父节点的兄弟节点也为黑色，该种情况只有一种，即叔叔节点不存在
                              gp
                             /
                            p
                          /  \
                        x1    x2
                    */
                    if (x == parent.getRight()) {
                        // 一左一右(对应x2) 则先按parent节点左旋，左旋后形成一条都是左边的路径
                        leftRotate(parent);
                        //旋转完成后x变成了p的父节点
                        parent = x;
                    }
                    // 全部是左节点(对应x1) 则调整颜色后右旋
                    setColor(parent, RBTreeNode.BLACK);
                    setColor(parent.getParent(), RBTreeNode.RED);
                    rightRotate(parent.getParent());
                }
            }
            // 2. 右三情况，即x的父节点为爷爷的右子树
            if (parent == getRight(grandPa)) {
                final RBTreeNode<K, V> uncle = getBrother(parent);
                if(getColor(uncle) == RBTreeNode.RED){
                    // 若父节点的兄弟节点也为红色 则调整父节点、兄弟节点颜色，及祖父节点颜色
                    setColor(parent,RBTreeNode.BLACK);
                    setColor(uncle,RBTreeNode.BLACK);
                    setColor(parent.getParent(),RBTreeNode.RED);
                    x = grandPa; // 向上递归
                }else {
                    /* 若父节点的兄弟节点也为黑色，该种情况只有一种，即叔叔节点不存在
                              gp
                               \
                                p
                              /  \
                            x1   x2
                    */
                    if (x == parent.getLeft()) {
                        // 一右一左(对应x1) 则先按parent节点右旋，左旋后形成一条都是右边的路径
                        rightRotate(parent);
                        //旋转完成后x变成了p的父节点
                        parent = x;
                    }
                    // 全部是右节点(对应x2) 则调整颜色后左旋
                    setColor(parent, RBTreeNode.BLACK);
                    setColor(parent.getParent(), RBTreeNode.RED);
                    leftRotate(parent.getParent());
                }
            }
        }
        root.setColor(RBTreeNode.BLACK);
    }

    private void check(RBTreeNode<K,V> node) {
        RBTreeNode<K,V> parent = node.getParent();
        // 若插入节点的父节点为红色，并且父节点的兄弟节点同为红色 则调整颜色
        // 1. 将父节点及父节点的兄弟节点的颜色改为黑色
        // 2. 将祖父节点的颜色改为红色
        RBTreeNode<K,V> brotherOfParent = getBrother(parent);
        if (parent.getColor() == RBTreeNode.RED && brotherOfParent.getColor() == RBTreeNode.RED) {
            parent.setColor(RBTreeNode.BLACK);
            brotherOfParent.setColor(RBTreeNode.BLACK);
            parent.getParent().setColor(RBTreeNode.RED);
        }

        // 若上述条件满足则继续往上层查找
    }

    private RBTreeNode<K,V> getBrother(RBTreeNode<K,V> node) {
        RBTreeNode<K, V> parent = node.getParent();
        if (parent == null) return null;
        if (parent.getLeft() == node) {
            return parent.getRight();
        }
        return parent.getLeft();
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
    public void leftRotate(RBTreeNode<K,V> p) {

        if (p == null) return;

        // 调整 P 节点
        RBTreeNode<K,V> pr = p.getRight();
        RBTreeNode<K,V> rl = pr.getLeft();
        p.setRight(rl);

        // 调整 lr 节点
        if(pr.getLeft()!=null){
            rl.setParent(p);
        }

        // 调整 Pr 节点
        pr.setParent(p.getParent());
        pr.setLeft(p);
        if(p.getParent()==null){
            root = pr;
            pr.setParent(null);
        }
        // 如果P是左子树，则将P父亲节点pf的左子树指向新节点 pr
        else if(p.getParent().getLeft() == p){
            p.getParent().setLeft(pr);
        }
        // 如果P是右子树，则将P父亲节点pf的右子树指向新节点 pr
        else if(p.getParent().getRight() == p){
            p.getParent().setRight(pr);
        }
        p.setParent(pr);
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
    public void rightRotate(RBTreeNode<K,V> p) {

        if (p == null) return;

        // 调整 P 节点
        RBTreeNode<K,V> pl = p.getLeft();
        RBTreeNode<K,V> rr = pl.getRight();
        p.setLeft(rr);

        // 调整 rr 节点
        if(pl.getRight()!=null){
            rr.setParent(p);
        }

        // 调整 Pl 节点
        pl.setParent(p.getParent());
        pl.setRight(p);
        if(p.getParent()==null){
            root = pl;
            pl.setParent(null);
        }
        // 如果P是左子树，则将P父亲节点pf的左子树指向新节点 pl
        else if(p.getParent().getLeft() == p){
            p.getParent().setLeft(pl);
        }
        // 如果P是右子树，则将P父亲节点pf的右子树指向新节点 pl
        else if(p.getParent().getRight() == p){
            p.getParent().setRight(pl);
        }
        p.setParent(pl);
    }
























}
