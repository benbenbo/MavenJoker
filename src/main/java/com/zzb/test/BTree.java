package com.zzb.test;





import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BTree {
    class Node{
        int data;
        Node left;
        Node right;
        Node parent; //方便做删除操作

        public Node(int data, Node left, Node right,Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent=parent;
        }
    }
    private Node root;
    private int size;
    public BTree(){
    }

    // 要向已有的二叉树插入新节点关键在于怎么找到要插入的位置，
    // 假如还需要保证插入后的二叉树保持某种顺序，那么还要有额外的处理。
    // 在这里我们考虑一种简单的情况：给定数据，按层顺序找到一个合适的位置并插入新节点。
    public void insertNode(int data){
        //查找有没有相同的data
        Node node=bfsfind(data);
        if(node!=null){
            System.out.println("结点已插入");
            return;
        }
        if(root==null){
            root=new Node(data,null,null,null);
            size++;
            return;
        }
        //插入
        Queue<Node> queue=new LinkedList<>();
        Node newNode=new Node(data,null,null,null);
        queue.add(root);
        while(!queue.isEmpty()){
            Node poll = queue.poll();
            if(poll.left!=null){
                queue.add(poll.left);
            }else{
                poll.left=newNode;
                newNode.parent=poll;
                size++;
                break;
            }
            if(poll.right!=null){
                queue.add(poll.right);
            }else{
                poll.right=newNode;
                newNode.parent=poll;
                size++;
                break;
            }
        }
    }

    //广度优先遍历,查找结点
    public Node bfsfind(int data){
        if(root!=null){
            Queue<Node> queue=new LinkedList<>();
            queue.add(root);
            while(!queue.isEmpty()){
                Node poll = queue.poll();
                if(poll.data==data){
                    return poll;
                }
                if(poll.left!=null){
                    queue.add(poll.left);
                }
                if(poll.right!=null){
                    queue.add(poll.right);
                }
            }
        }
        return null;
    }

    //思路：这个二叉树没有要求保证顺序，因此可以把要删除的data放到一个叶子结点处
    //然后删除
    public void removeNode(int data){
        //查找有没有域为data的结点
        Node node=bfsfind(data);
        if(node==null){
            System.out.println("结点不存在");
            return;
        }
        //移除
        Queue<Node> queue=new LinkedList<>();
        Node leafNode=null;
        queue.add(root);
        while(!queue.isEmpty()){
            Node poll = queue.poll();
            if(poll.left!=null){
                queue.add(poll.left);
            }
            if(poll.right!=null){
                queue.add(poll.right);
            }
            if(poll.left==null&&poll.right==null){
                leafNode=poll;
                break;
            }
        }
        node.data=leafNode.data;
        Node parent=leafNode.parent;
        if(parent==null){
            root=null;
            size--;
            return;
        }
        if(parent.left==leafNode){
            parent.left=null;
            size--;
            return;
        }
        if(parent.right==leafNode){
            parent.right=null;
            size--;
            return;
        }

    }

    public void inOrder(){
        if(root==null){
            System.out.println("树为空");
        }
        inOrderByStack();
//        inOrder(root);
    }

    private void inOrder(Node node){
        if(node.left!=null){
            inOrder(node.left);
        }
        System.out.println(node.data+"----");
        if(node.right!=null){
            inOrder(node.right);
        }
    }

    //非递归的中序遍历
    public void inOrderByStack(){
        Stack<Node> stack=new Stack<>();
        Node p=root;
        String s="";
        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p=p.left;
            }
            p=stack.pop();
            s+=p.data+",";
            if(p.right!=null){
                p=p.right;
            }else{
                p=null;
            }
        }
        System.out.println(s);
    }

    public void afterOrderByStack(){
        Stack<Node> stack=new Stack<>();
        Node p=root;
        String s="";
        Node pre=null;

        while(p!=null||!stack.isEmpty()){
            while(p!=null){
                stack.push(p);
                p=p.left;
            }
            p=stack.peek();
            if(p.right==null||pre!=null&&pre==p.right){
                p=stack.pop();
                s+=p.data+",";
                pre=p;
                p=null;
            }else{
                p=p.right;
            }
        }
        System.out.println(s);
    }

}
