package com.zzb.test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 平衡二叉树具有二叉查找树的特点
 */
public class AVLTree {
    private Node root;
    private int size;
    private static final int MAX_LEFT=2;
    private static final int MAX_RIGHT=-2;
    private static final int LEFT=1;
    private static final int RIGHT=-1;
    private class Node{
        int data;
        Node left;
        Node right;
        Node parent;

        public Node(int data, Node left, Node right,Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public Node(int data) {
            this.data = data;
        }
    }

    /**
     * 右旋代码保持平衡
     * @param node
     * @return
     */
    public Node rightRotation(Node node) {
        if(node!=null){
            //先单独保存当前结点的左孩子
            Node leftChild=node.left;
            //单独保存当前结点的左孩子的右孩子
            Node rightChildOfLeft=leftChild.right;

            //第一步：将node变为他的左子树的右节点
            leftChild.right=node;
            //第二步：如果原本左孩子就存在右子树，那么将其放到node的左子树,否则清空
            node.left=rightChildOfLeft;

            //第三步：开始修改三个结点各自的parent指针以及它们的parent指向
            //修改左子树的右孩子的parent指向
            if(rightChildOfLeft!=null){
                rightChildOfLeft.parent=node;
            }

            //修改左子树的parent指向
            if(node.parent==null){
                //是头结点
                this.root=leftChild;
                leftChild.parent=null;
            }else if(node.parent!=null&&node.parent.left==node){
                //如果当前结点有父节点,而且是位于左子树处
                leftChild.parent=node.parent;
                leftChild.parent.left=leftChild;
            }else if(node.parent!=null&&node.parent.right==node){
                //如果当前结点有父节点,而且是位于右子树处
                leftChild.parent=node.parent;
                leftChild.parent.right=leftChild;
            }

            //修改当前结点的parent指向，右旋完毕
            node.parent=leftChild;
            return node;
        }
        return null;
    }


    /**
     * 左旋代码保持平衡
     * @param node
     * @return
     */
    public Node leftRotation(Node node) {
        if(node!=null){
            //先单独保存当前结点的左孩子
            Node rightChild=node.right;
            //单独保存当前结点的左孩子的右孩子
            Node leftChildOfRight=rightChild.left;
            //第一步：将node变为他的右子树的左节点
            rightChild.left=node;
            //第二步：如果原本右孩子就存在左子树，那么将其放到node的右子树
            node.right=leftChildOfRight;

            //第三步：开始修改三个结点各自的parent指针以及它们的parent指向
            //修改右子树的左孩子的parent指向
            if(leftChildOfRight!=null){
                leftChildOfRight.parent=node;
            }

            //修改原右子树的parent指向
            if(node.parent==null){
                //是头结点
                this.root=rightChild;
                rightChild.parent=null;
            }else if(node.parent!=null&&node.parent.left==node){
                //如果当前结点有父节点,而且是位于左子树处
                rightChild.parent=node.parent;
                rightChild.parent.left=rightChild;
            }else if(node.parent!=null&&node.parent.left==node){
                //如果当前结点有父节点,而且是位于右子树处
                rightChild.parent=node.parent;
                rightChild.parent.right=rightChild;
            }

            //修改当前结点的parent指向，左旋完毕
            node.parent=rightChild;

            return node;
        }
        return null;
    }



    /**
     * 插入节点
     * @param data
     */
    public void put(int data) throws Throwable {
        if(putData(root, data)){
            size++;
        }
    }

    private boolean putData(Node node, int data) throws Throwable {
        if(node == null) {
            node  = new Node(data);
            root = node;
            return true;
        }
        //就是二叉排序树的插入法
        Node p=node;
        Node pre=null;
        while(p!=null){
            if(p.data==data){
                return false;
            }else if(p.data<data){
                pre=p;
                p=p.right;
            }else if(p.data>data){
                pre=p;
                p=p.left;
            }
        }

        if(pre.data<data){
            pre.right=new Node(data,null,null,pre);
        }else if(pre.data>data){
            pre.left=new Node(data,null,null,pre);
        }
        rebuild(pre); //平衡二叉树的方法
        return true;

    }

    /**
     * 平衡二叉树的方法
     * @param node
     */
    public void rebuild(Node node){
        while(node != null) {
            int height=calcNodeBalanceValue(node);
            if(height== MAX_LEFT) {
                //左子树高
                fixAfterInsertion(node, LEFT);
            } else if(height == MAX_RIGHT) {
                //右子树高
                fixAfterInsertion(node, RIGHT);
            }
            node = node.parent;
        }
    }

    /**
     * 计算node节点的BF值
     * @param node
     * @return
     */
    public int calcNodeBalanceValue(Node node){
        if(node != null) {
            return getHeightByNode(node);
        }
        return 0;
    }

    /**
     * 获取当前结点的平衡因子
     * @param node
     * @return
     */
    private int getHeightByNode(Node node){
        if(node == null) {
            return 0;
        }
        return getChildDepth(node.left) - getChildDepth(node.right);
    }

    /**
     * 递归计算子树高度
     * @param node
     * @return
     */
    private int getChildDepth(Node node){
        if(node == null) {
            return 0;
        }
        int t = 1 + Math.max(getChildDepth(node.left), getChildDepth(node.right));
        return t;
    }

    public void delete(int data){
        if(deleteData(data)){
            size--;
        }
    }

    public boolean deleteData(int data){
        Node node = getNode(data);
        if(node==null){
            System.out.println("无结点可以删除");
            return false;
        }
        boolean flag = false;
        Node p = null;
        Node parent=node.parent;
        Node left = node.left;
        Node right = node.right;
        if(left==null&&right==null){
            //叶子结点可以直接清除
            if(parent.right==node){
                parent.right=null;
            }else{
                parent.left=null;
            }
            p = parent;
            node = null;
            flag = true;
        }else if(left==null&&right!=null){
            //删除结点只有右子树
            if(parent!=null){
                if(parent.right==node){
                    parent.right=right;
                }else{
                    parent.left=right;
                }
                right.parent=parent;
            }else{
                this.root=right;
                right.parent=null;
            }
            p = parent;
            node = null;
            flag = true;
        }else if(left!=null&&right==null){
            //删除结点只有左子树
            if(parent!=null){
                if(parent.right==node){
                    parent.right=left;
                }else{
                    parent.left=left;
                }
                left.parent=parent;
            }else{
                this.root=left;
                left.parent=null;
            }
            p = parent;
            node = null;
            flag = true;
        }else{
            //删除结点具有左子树和右子树
            Node leftChild = node.left;
            Node pre=null;
            while(leftChild!=null){
                pre=leftChild;
                leftChild=leftChild.right;
            }
            int tempData=pre.data;
            //删除pre结点
            //pre一定没有右子树，因为从上面的判断来看的话
            p = pre.parent;
            pre.parent=null;
            if(p.left==pre){
                p.left=pre.left;
            }else if(p.right==pre){
                p.right=pre.left;
            }
            node.data=tempData;
            flag=true;
        }
        if(flag) {
            this.rebuild(p);
        }
        return flag;
    }


    /**
     * 调整树的结构
     * @param node
     * @param type
     */
    public void fixAfterInsertion(Node node, int type) {
        if(type == LEFT) {
            Node leftChild = node.left;
            if(calcNodeBalanceValue(leftChild)==-1) {  //左右旋
                leftRotation(leftChild);
            }
            rightRotation(node);
        } else if(type == RIGHT) {
            Node rightChild = node.right;
            if(calcNodeBalanceValue(rightChild)==1) {   //右左旋
                rightRotation(rightChild);
            }
            leftRotation(node);
        }
    }

    //中序遍历
    public String inOrder(){
        StringBuilder builder=new StringBuilder();
        inOrder(root,builder);
        System.out.println(builder.toString());
        return builder.toString();
    }

    public void inOrder(Node node,StringBuilder builder){
        if(node.left!=null){
            inOrder(node.left,builder);
        }
        builder.append(node.data).append(",");
        if(node.right!=null){
            inOrder(node.right,builder);
        }
    }


    /**
     * 获得指定节点
     * @param key
     * @return
     */
    public Node getNode(int key) {
        Node node = root;
        int t;
        while(node!=null){
            if(key==node.data){
                return node;
            }else if(key>node.data){
                node=node.right;
            }else if(key<node.data){
                node=node.left;
            }
        }
        return null;
    }

    //层序遍历
    public void sequenceErgodic(){
        if(this.root==null){
            System.out.println("树为空");
        }
        StringBuilder builder=new StringBuilder();
        Queue<Node> queue=new LinkedList<>();
        queue.offer(this.root);
        while(!queue.isEmpty()){
            Node poll = queue.poll();
            builder.append(poll.data).append(",");
            if(poll.left!=null){
                queue.offer(poll.left);
            }
            if(poll.right!=null){
                queue.offer(poll.right);
            }
        }
        System.out.println(builder.toString());
    }


    /**
     * 找某个结点的中序遍历下的前继结点,也就是左子树中最大的值
     * @return
     */
    public Node getPreccessor(Node node){
        //情况一：若左子树存在，在找左子树中最大的那个
        Node p = node.left;
        Node pre=null;
        if(p!=null){
            while(p!=null){
                pre=p;
                p=p.right;
            }
            return pre;
        }
        //情况二：如果node的右子树不存在，则从它的父节点找，找到一个父节点比当前结点要小的。
        //如果parent为null或者当前结点是parent的间接右子树的话，就可以返回
        Node parent = node.parent;
        while(parent!=null&&parent.left==node){
            node=parent;
            parent=parent.parent;
        }
        return parent;
    }

    /**
     * 找某个结点的中序遍历下的后继结点，也就是右子树中最大的值
     * 进到这个方法的时候一定有左右子树
     * @return
     */
    public Node getSuccessor(Node node){
        //情况一：若右子树存在，在找右子树中最小的那个
        Node p = node.right;
        Node pre=null;
        while(p!=null){
            pre=p;
            p=p.left;
        }
        return pre;

        //情况二：如果node的右子树不存在，则从它的父节点找，找到一个父节点比当前结点要大的。
        //如果parent为null或者当前结点是parent的间接左子树的话，就可以返回
//        Node parent = node.parent;
//        while(parent!=null&&parent.right==node){
//            node=parent;
//            parent=parent.parent;
//        }
//        return parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
