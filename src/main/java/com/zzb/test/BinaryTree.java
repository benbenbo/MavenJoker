package com.zzb.test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {
    class BinaryTreeNode {

        public int data; //数据域
        public BinaryTreeNode left, right,parent; //指向左右子节点的指针

        public BinaryTreeNode(int data, BinaryTreeNode left, BinaryTreeNode right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    private BinaryTreeNode root;
    private int size;

    public BinaryTree() {
    }


    //二分插入，已优化
    //思路：二分查找插入数据的父节点
    //三种情况：父节点为空，有可能是根节点，有可能整棵树为空
    //2：父节点不为空，data小于父结点，有可能已存在，有可能需要插入
    //3：父节点不为空，data大于父结点，有可能已存在，有可能需要插入
    public boolean inserBst(int data){
        BinaryTreeNode parent = BSTSearchParentNode(data);
        if(parent==null){
            //父节点为空，有可能是根节点，有可能整棵树为空
            if(root!=null){
                System.out.println("此值已经在树中");
                return false;
            }else{
                this.root=new BinaryTreeNode(data,null,null);
                size++;
                return true;
            }
        }else{
            //父节点不为空，有可能已存在，有可能需要插入
            if(data<parent.data){
                if(parent.left!=null&&parent.left.data==data){
                    System.out.println("此值已经在树中");
                    return false;
                }else{
                    BinaryTreeNode newNode=new BinaryTreeNode(data,null,null);
                    parent.left=newNode;
                    size++;
                    return true;
                }
            }else{
                if(parent.right!=null&&parent.right.data==data){
                    System.out.println("此值已经在树中");
                    return false;
                }else{
                    BinaryTreeNode newNode=new BinaryTreeNode(data,null,null);
                    parent.right=newNode;
                    size++;
                    return true;
                }
            }
        }
    }


    //删除结点，已优化
    public boolean deleteBst(int data){
        if(this.root==null){
            System.out.println("树为空");
            return false;
        }
        BinaryTreeNode pre=null;
        BinaryTreeNode p = root;
        while(p!=null){
            if(p.data==data){
                delete(p,pre);
                return true;
            }
            if(data<=p.data){
                pre=p;
                p=p.left;
            }
            if(data>p.data){
                pre=p;
                p=p.right;
            }
        }
        return false;

    }

    /**
     * 内部删除操作
     * @param node
     * @param parent
     * @return
     */
    private boolean delete(BinaryTreeNode node,BinaryTreeNode parent) {
        BinaryTreeNode q, s;
        if(node.right == null) {
            //情况一：待删除结点只有左子树
            if(parent.left==node){
                parent.left=node.left;
            }else{
                parent.right=node.left;
            }
        }else if(node.right == null) {
            //情况二：待删除结点只有右子树
            if(parent.left==node){
                parent.left=node.right;
            }else{
                parent.right=node.right;
            }
        }else if(node.left==null&&node.right==null){
            //情况三：待删除结点是叶子结点
            if(parent.left==node){
                parent.left=null;
            }else{
                parent.right=null;
            }
        }else {
            //情况四：待删除结点有左右子树
            q = node;//q为待删除结点
            s = node.left;//s为待删除节点的左子树
            while(s.right != null) {
                //找到左子树中最大的结点，赋值到s中，q为s的父节点
                //其实s就是中序遍历下node的前驱结点
                //而且此时保证s是没有右子树
                q = s;
                s = s.right;
            }
            //使用s覆盖node的data
            node.data = s.data;
            //删除s，更改s的父节点的子树指向s的左子树
            if(q != node) {
                q.right = s.left;
            }
            else {
                q.left = s.left;
            }
        }
        return true;
    }

    //二分判断结点是否存在
    public boolean BSTSearch(int data){
        if(this.root==null){
            return false;
        }
        BinaryTreeNode p = root;
        while(p!=null){
            if(p.data==data){
                break;
            }else if(data<=p.data){
                p=p.left;
            }else{
                p=p.right;
            }
        }
        if(p!=null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 二分查找父结点
     * @param data
     * @return
     */
    private BinaryTreeNode BSTSearchParentNode(int data){
        if(this.root==null){
            return null;
        }
        BinaryTreeNode pre=null;
        BinaryTreeNode p = root;
        while(p!=null){
            if(p.data==data){
                break;
            }else if(data<=p.data){
                pre=p;
                p=p.left;
            }else{
                pre=p;
                p=p.right;
            }
        }
        return pre;
    }


    //中序遍历
    public void inOrder(){
        if(this.root==null){
            System.out.println("无输出结点");
        }
        Stack<BinaryTreeNode> stack=new Stack<>();
        BinaryTreeNode p=root;
        StringBuilder stringBuilder=new StringBuilder();
        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p=p.left;
            }
            p=stack.pop();
            stringBuilder.append(p.data).append(",");
            if(p.right!=null){
                p= p.right;
            }else{
                p=null;
            }
        }
        System.out.println("中序遍历："+stringBuilder.toString());
    }
}
