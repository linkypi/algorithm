package com.lynch.binary_tree;

import com.lynch.binary_tree.common.RBTreeNode;

import java.util.Arrays;
import java.util.List;

import static org.fusesource.jansi.Ansi.Color.BLACK;
import static org.fusesource.jansi.Ansi.Color.RED;
import static org.fusesource.jansi.Ansi.ansi;

public class RedBlackTree<K extends Comparable<K>,V> {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(2,7,5,9,1,12,10,25,19,6,3,5);
        RedBlackTree<Integer,Integer> tree = new RedBlackTree<>();
        for(Integer item : list){
            tree.insert(item);
//            print(tree.root);
        }
        print(tree.root);
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

    public RBTreeNode<K, V> getRoot() {
        return root;
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

    /**
     * 找到指定节点的前驱节点
     * 即找到小于该节点的最大值
     * @param item
     * @return
     */
    private RBTreeNode getPredecessor(RBTreeNode<K,V> item) {
        if (item == null)
            return null;

        // 左子树不为空 则寻找左子树的最大右节点
        if (item.getLeft() != null) {
            RBTreeNode node = item.getLeft();
            while (node.getRight() != null) {
                node = node.getRight();
            }
            return node;
        } else {
            // 删除节点不会进入该部分逻辑
            // 若没有左子树则往父节点的上一级寻找
            RBTreeNode p = item.getParent();
            RBTreeNode temp = item;
            while (p != null && temp == p.getLeft()) {
                temp = p;
                p = p.getParent();
            }
            return p;
        }
    }

    /**
     * 获取后继节点
     * 即获取大于该节点的最小节点
     * @param item
     * @return
     */
    private RBTreeNode getSuccessor(RBTreeNode<K,V> item) {
        if (item == null)
            return null;

        // 右子树不为空 则寻找右子树的最大左节点
        if (item.getRight() != null) {
            RBTreeNode node = item.getRight();
            while (node.getLeft() != null) {
                node = node.getLeft();
            }
            return node;
        } else {
            // 删除节点不会进入该部分逻辑
            // 若没有左子树则往父节点的上一级寻找
            RBTreeNode p = item.getParent();
            RBTreeNode temp = item;
            while (p != null && temp == p.getRight()) {
                temp = p;
                p = p.getParent();
            }
            return p;
        }
    }

    /**
     * 删除节点, 即找到指定节点后做删除该节点, 然后找到其前驱节点或后继节点填补空缺
     * 为了降低删除节点并使用其他节点填补操作的复杂性,实际删除操作也就转变为删除叶子
     * 节点或只有一个子节点的节点, 然后将待删除节点的 key, value 改为删除节点的 key, value
     *
     * @param key
     * @return
     */
    public V remove(K key){
        RBTreeNode node = findByKey(key);
        if(node==null){
            return null;
        }
        V oldValue = (V)node.getValue();

        deleteNode(node);

        return oldValue;
    }

    /**
     * 分三种情形:
     * 1. 若该节点是叶子节点则直接删除
     * 2. 若该节点仅有一个子节点则删除该节点后用其子节点补位
     * 3. 若该节点存在两个子节点,则找到该节点的前驱节点或后继节点进行替换后再删除前驱或后继节点
     *    该情形即转变为将前驱或后继节点覆盖待删除节点, 然后前驱或后继节点:
     *    3.1 被删除节点(即前驱或后继节点)是叶子节点
     *    3.2 被删除节点(即前驱或后继节点)仅有一个子节点
     * @param node
     */
    private void deleteNode(RBTreeNode<K,V> node) {

        // 3. node 节点有两个子节点
        if (node.getLeft() != null && node.getRight() != null) {
            // 后继节点
            RBTreeNode successor = getSuccessor(node);
            node.setValue((V) successor.getValue());
            node.setKey((K) successor.getKey());
            node = successor;
        }

        if (node.getParent() == null) {
            root = null;
        }

        RBTreeNode replacement = node.getLeft() != null ? node.getLeft() : node.getRight();
        // 1. 待替换的节点 node 是叶子节点, 直接删除
        if (replacement == null) {
            if(node.getColor() == RBTreeNode.BLACK){
                // adjustAfterRemove
            }
            if (node.getParent()!=null && node.getParent().getLeft() == node) {
                node.getParent().setLeft(null);
            }
            if (node.getParent()!=null && node.getParent().getRight() == node) {
                node.getParent().setRight(null);
            }
            // 移除 node
            node.setParent(null);
        // 2. node 节点仅有一个子节点
        } else {
            replacement.setParent(node.getParent());
            if (node.getParent() == null) {
                root = replacement;
            }
            // 待替换的节点 node 存在一个子节点, 则将 node 移除后使用其子节点补位
            else if (node.getParent().getLeft() == node) {
                node.getParent().setLeft(replacement);
            } else {
                node.getParent().setRight(replacement);
            }
            // 移除 node
            node.setLeft(null);
            node.setRight(null);
            node.setParent(null);

            // 删除节点后调整树结构, 仅删除黑节点时才需调整, 保持黑色节点平衡
            if(node.getColor() == RBTreeNode.BLACK){
                // adjustAfterRemove
            }
        }
    }

    private void adjustAfterRemove(RBTreeNode<K,V> node){

        // 若删除的节点是黑色节点则需要做调整
        if(node!=root && getColor(node) == RBTreeNode.BLACK){

            if(node.getParent().getLeft() == node){
                RBTreeNode<K, V> brother = node.getParent().getRight();

                // 判断 brother 是否是真正的兄弟节点, 若不是则调整为实际兄弟节点
                if(brother.getColor() == RBTreeNode.RED){
                    // 兄弟节点变为黑色
                    setColor(brother, RBTreeNode.BLACK);
                    // 父节点变为红色
                    setColor(brother.getParent(), RBTreeNode.RED);
                    // 沿着父节点左旋
                    leftRotate(brother.getParent());
                    // brother 调整为实际兄弟节点
                    brother = node.getParent().getRight();
                }

                // 情况 3 , 找兄弟借, 兄弟没的借.
                // 2-3-4 树仅有 3 节点及 4 节点才有的借, 2 节点无法借出. 故此处对应的红黑树即判断其左右子树是否都为空
                if(getColor(brother.getLeft()) == RBTreeNode.BLACK&& getColor(brother.getRight())== RBTreeNode.BLACK){

                }

                // 情况 2 , 找兄弟借, 兄弟有的借
                else{
                    // 分两种情况, 即兄弟节点是 3 节点或 4 节点
                    // 兄弟节点的右孩子为空, 即兄弟节点存在左子节点
                    if(getColor(brother.getRight()) == RBTreeNode.BLACK){
                        brother.setColor(RBTreeNode.RED);
                        brother.getLeft().setColor(RBTreeNode.BLACK);
                        rightRotate(brother);
                        brother = node.getParent().getRight();
                    }
                }

            }else{

            }


        }

       // 替代节点是红色, 直接染黑, 替代删除的黑色节点, 保持黑色节点平衡
       setColor(node, RBTreeNode.BLACK);
    }

    private RBTreeNode findByKey(K key) {
        if (key == null) {
            return null;
        }
        RBTreeNode<K, V> node = root;
        while (node != null) {
            int cmp = key.compareTo((K) node.getKey());
            if (cmp < 0) {
                node = node.getLeft();
            }
            if (cmp > 0) {
                node = node.getRight();
            }
            return node;
        }
        return null;
    }
}
