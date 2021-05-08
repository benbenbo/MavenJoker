package com.zzb.test;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 我创建的RBTree
 */
public class MyRBTree<T extends Comparable<T>> {
    class MyRBTreeNode<T>{
        MyRBTreeNode left;
        MyRBTreeNode right;
        MyRBTreeNode parent; //方便进行左旋右旋
        boolean isRed;//是否是红色节点
        T data;

        public MyRBTreeNode() {
        }

        @Override
        public String toString(){
            return data.toString();
        }

        public MyRBTreeNode(T data) {
            this.data = data;
        }
    }

    private MyRBTreeNode<T> root;
    private AtomicLong size=new AtomicLong();
    private boolean overrideMode=false;//是否支持重复值节点
    public void MyRBTreeNode(){

    }

    //查找操作
    private MyRBTreeNode<T> searchNode(T data){
        MyRBTreeNode<T> node=root;
        while(node!=null){
            if(node.data.compareTo(data)<0){
                node=node.left;
            }else if(node.data.compareTo(data)>0){
                node=node.right;
            }else{
                return node;
            }
        }
        return null;
    }

    public boolean search(T data){
        MyRBTreeNode<T> node=root;
        while(node!=null){
            if(node.data.compareTo(data)<0){
                node=node.left;
            }else if(node.data.compareTo(data)>0){
                node=node.right;
            }else{
                return true;
            }
        }
        return false;
    }


    //左旋操作
    public void rotateLeft(MyRBTreeNode<T> node){
        if(node==null){
            return;
        }
        MyRBTreeNode<T> right = node.right;
        if(right==null){
            return;
        }
        MyRBTreeNode<T> parent = node.parent;

        node.right=right.left;
        if(right.left!=null){
            right.left.parent=node;
        }

        right.left=node;
        node.parent=right;

        if(parent==null){
            root=right;
            right.parent=null;
        }else{
            if(parent.left==node){
                parent.left=right;
            }else{
                parent.right=right;
            }
            right.parent=parent;
        }
    }


    //右旋操作
    public void rotateRight(MyRBTreeNode<T> node){
        if(node==null){
            return;
        }
        MyRBTreeNode<T> left = node.left;
        if(left==null){
            return;
        }

        MyRBTreeNode<T> parent = node.parent;

        node.left=left.right;
        if(left.right!=null){
            left.right.parent=node;
        }

        left.right=node;
        node.parent=left;

        if(parent==null){
            root=left;
            left.parent=null;
        }else{
            if(parent.left==node){
                parent.left=left;
            }else{
                parent.right=left;
            }
            left.parent=parent;
        }
    }

    //打印操作
    public void printTree(){
        java.util.LinkedList<MyRBTreeNode<T>> queue =new java.util.LinkedList<MyRBTreeNode<T>>();
        java.util.LinkedList<MyRBTreeNode<T>> queue2 =new java.util.LinkedList<MyRBTreeNode<T>>();
        if(root==null){
            return ;
        }
        queue.add(root);
        boolean firstQueue = true;

        while(!queue.isEmpty() || !queue2.isEmpty()){
            java.util.LinkedList<MyRBTreeNode<T>> q = firstQueue ? queue : queue2;
            MyRBTreeNode<T> n = q.poll();

            if(n!=null){
                String pos = n.parent==null ? "" : ( n == n.parent.left
                        ? " LE" : " RI");
                String pstr = n.parent==null ? "" : n.parent.toString();
                String cstr = n.isRed?"R":"B";
                cstr = n.parent==null ? cstr : cstr+" ";
                System.out.print(n+"("+(cstr)+pstr+(pos)+")"+"\t");
                if(n.left!=null){
                    (firstQueue ? queue2 : queue).add(n.left);
                }
                if(n.right!=null){
                    (firstQueue ? queue2 : queue).add(n.right);
                }
            }else{
                System.out.println();
                firstQueue = !firstQueue;
            }
        }
    }

    //插入操作
    public boolean add(T value){
        MyRBTreeNode<T> tMyRBTreeNode = searchNode(value);
        if(tMyRBTreeNode!=null){
            return false;
        }
        MyRBTreeNode<T> newNode = new MyRBTreeNode<T>(value);
        addNode(newNode);
        return true;

    }


    private T addNode(MyRBTreeNode<T> node){
        node.left=null;
        node.right=null;
        //总是以红色节点进行插入
        node.isRed=true;
        node.parent=null;
        if(root==null){
            //这里要保证树根节点为黑
            root=node;
            root.isRed=false;
            size.incrementAndGet();
        }else{
            MyRBTreeNode<T> x = findParentNode(node);
            int cmp = x.data.compareTo(node.data);

            if(this.overrideMode && cmp==0){
                T v = x.data;
                x.data=node.data;
                return v;
            }else if(cmp==0){
                //值相等，直接返回，不进行插入
                return x.data;
            }

            node.parent=x;

            if(cmp>0){
                x.left=node;
            }else{
                x.right=node;
            }

            fixInsert(node);
            size.incrementAndGet();
        }
        return null;
    }

    /**
     * 返回与x的值相等的节点或者适合成为x的父节点的节点。返回父节点时一定保证是可放置新的子节点的。
     * @param x
     * @return
     */
    private MyRBTreeNode<T> findParentNode(MyRBTreeNode<T> x){
        MyRBTreeNode<T> dataRoot = root;
        MyRBTreeNode<T> child = dataRoot;

        while(child!=null){
            int cmp = x.data.compareTo(child.data);
            if(cmp==0){
                return child;
            }
            if(cmp>0){
                dataRoot = child;
                child = child.right;
            }else if(cmp<0){
                dataRoot = child;
                child = child.left;
            }
        }
        return dataRoot;
    }

    private void fixInsert(MyRBTreeNode<T> node){
        //主要是解决连续红色节点的情况，所以要从node的parent开始优化
        MyRBTreeNode<T> parent = node.parent;

        while(parent!=null&&parent.isRed){
            MyRBTreeNode<T> uncle = getUncle(node);
            if(uncle!=null&&uncle.isRed){
                MyRBTreeNode<T> ancestor = parent.parent;
                parent.isRed=false;
                uncle.isRed=false;
                ancestor.isRed=true;
                //进行下一轮循环
                node=ancestor;
                parent=ancestor.parent;
                //这里由于我没有使用一个空置节点作为root，所以需要判断root是否被置为了黑色
                if(root==parent){
                    parent.isRed=false;
                }
            }else{
                MyRBTreeNode<T> ancestor = parent.parent;
                //此时ancestor不为空，因为如果ancestor为空，那么parent就是root节点，root节点不能是红色
                if(ancestor.left==parent){
                    boolean isRight=parent.right==node;
                    if(isRight){
                        //是左歪的状态的，先要对parent进行左旋
                        rotateLeft(parent);
                    }
                    rotateRight(ancestor);

                    if(isRight){
                        node.isRed=false;
                        //结束循环
                        parent=null;
                    }else{
                        parent.isRed=false;
                        //这里的源代码并没有显示终结循环，但是其实还是终结了的
                        //因为虽然parent没有更改，但是parent的isRed判断是没办法通过的，因此会终结
                        //上面是因为没有把parent.isRed进行修改，所以需要显示终结
                    }
                    ancestor.isRed=true;
                }else{
                    //上面的镜像操作
                    boolean isLeft=parent.left==node;
                    if(isLeft){
                        rotateRight(parent);
                    }
                    rotateLeft(ancestor);

                    if(isLeft){
                        node.isRed=false;
                        //结束循环
                        parent=null;
                    }else{
                        parent.isRed=false;
                    }
                    ancestor.isRed=true;
                }
            }
        }
        root.isRed=false;
        root.parent=null;
    }

    private MyRBTreeNode<T> getUncle(MyRBTreeNode<T> node){
        if(node==null){
            return null;
        }
        MyRBTreeNode<T> parent = node.parent;
        if(parent==null){
            return null;
        }
        MyRBTreeNode<T> anstor = parent.parent;
        if(anstor==null){
            return null;
        }
        if(anstor.left==parent){
            return anstor.right;
        }else{
            return anstor.left;
        }
    }

    /**
     * 删除方法
     * @param data
     * @return
     */
    public T remove(T data){
        MyRBTreeNode<T> dataRoot = root;
        MyRBTreeNode<T> parent = root;
        while(dataRoot!=null){
            int cmp = data.compareTo(dataRoot.data);
            if(cmp<0){
                parent = dataRoot;
                dataRoot = dataRoot.left;
            }else if(cmp>0){
                parent = dataRoot;
                dataRoot = dataRoot.right;
            }else{
                if(dataRoot.right!=null){
                    //这里只要是存在right就可以找到它的后继节点，代替真实被删除的节点
                    MyRBTreeNode<T> min = removeMin(dataRoot.right);
                    //这里min一定是left==null的，但是right==null不确定，要保证拿到被删除节点的sibling，所以要判断
                    MyRBTreeNode<T> x = min.right==null ? min.parent : min.right;
                    boolean isParent = min.right==null;

                    //这里是将返回的min节点代替dataRoot节点，dataRoot节点就是被删除的节点
                    min.left=dataRoot.left;
                    if(dataRoot.left!=null){
                        min.left.parent=min;
                    }
                    if(parent.left==dataRoot){
                        parent.left=min;
                    }else{
                        parent.right=min;
                    }

                    boolean curMinIsBlack = !min.isRed;
                    //min结点继承待删除结点的颜色
                    min.isRed=dataRoot.isRed;
                    //如果min不是待删除节点的右节点，则把待删除结点的右子树设置到min的右子树当中，避免死循环
                    if(min!=dataRoot.right){
                        min.right=dataRoot.right;
                        if(min.right!=null){
                            min.right.parent=min;
                        }
                    }

                    //移除的是黑色节点，需要修复操作
                    //这里之所以判断min节点原来的颜色是因为min会继承dataRoot的颜色，真正被删掉的时候min节点
                    if(curMinIsBlack){
                        if(min!=dataRoot.right){
                            //是dataRoot的右孩子的孩子，从被删除节点min的右子节点或者父节点开始调整
                            fixRemove(x,isParent);
                        }else if(min.right!=null){
                            //这里可以想成，因为min删除了，原来的min成为dataRoot，min.right成为了min
                            //因此，要拿到被删节点的父节点->可通过min.right去拿
                            fixRemove(min.right,false);
                        }else{
                            //同上
                            //因此，要拿到被删节点的父节点->没有min.right->不能通过right拿到父节点->直接传父节点
                            fixRemove(min,true);
                        }
                    }

                }else{
                    //此时无法找到中序遍历下的后继节点，只能直接删除
                    //若存在dataRoot.left,使用dataRoot.left代替dataRoot
                    if(dataRoot.left!=null){
                        dataRoot.left.parent=parent;
                    }
                    if(parent.left==dataRoot){
                        parent.left=dataRoot.left;
                    }else{
                        parent.right=dataRoot.left;
                    }


                    boolean dataRootIsBlack=!dataRoot.isRed;
                    //此时真正删的是dataRoot，所以判断dataRoot的颜色
                    //如果当前删除的结点是黑色的，而且树是非空的，那么就需要调整
                    if(dataRootIsBlack && root!=null){
                        MyRBTreeNode<T> x = dataRoot.left==null
                                ? parent :dataRoot.left;
                        //isParent是判断x是否是dataRoot的parent节点，为true则是parent节点
                        boolean isParent = dataRoot.left==null;
                        fixRemove(x,isParent);
                    }
                }
                //清除待删除结点的所有指针，方便垃圾回收
                dataRoot.parent=null;
                dataRoot.left=null;
                dataRoot.right=null;
                //将根节点置为黑色结点
                if(root!=null){
                    root.isRed=false;
                    root.parent=null;
                }
                //删除了一个结点之后就进行自减
                size.decrementAndGet();
                //返回已删除结点的值
                return dataRoot.data;
            }
        }
        return null;
    }

    /**
     * 移除中序遍历下node的后继节点，并返回被移除的节点
     * 特殊情况：如果node没有左子树，则返回node
     * @param node
     * @return
     */
    public MyRBTreeNode<T> removeMin(MyRBTreeNode<T> node){
        MyRBTreeNode<T> parent=node;
        MyRBTreeNode<T> x=node;
        while(x!=null&&x.left!=null) {
            parent = x;
            x = parent.left;
        }
        if(parent==node){
            return parent;
        }
        //去掉x节点，将x节点的right赋值给x本来的位置
        parent.left=x.right;
        if(x.right!=null){
            x.right.parent=parent;
        }
        return x;
    }

    /**
     * 获取兄弟结点
     * @param node
     * @param parent
     * @return
     */
    private MyRBTreeNode<T> getSibling(MyRBTreeNode<T> node, MyRBTreeNode<T> parent){
        parent = node==null ? parent : node.parent;
        if(node==null){
            return parent.left==null ? parent.right : parent.left;
        }
        if(node==parent.left){
            return parent.right;
        }else{
            return parent.left;
        }
    }

    /**
     * 移除节点的修复操作
     * @param node 被删除的节点的子节点或父节点
     * @param isParent true-node是被删除节点的父节点，false-node是被删除节点的子节点
     */
    public void fixRemove(MyRBTreeNode<T> node,boolean isParent){
        MyRBTreeNode<T> cur=isParent?null:node;
        MyRBTreeNode<T> parent=isParent?node:node.parent;
        boolean isRed=isParent?false:cur.isRed;
        while(!isRed && cur!=root) {
            //这里兄弟结点是绝对非空的，因为移除之前，颜色是平衡的。
            //被删除节点，没有兄弟的话就违反性质5。因为null节点也被视为黑色节点
            MyRBTreeNode<T> sibling = getSibling(node, parent);
            boolean isLeft = sibling == parent.right;
            if (sibling.isRed && !isLeft) {
                //策略1:目的是可以转化为2，3，4去处理
                parent.isRed = true;
                sibling.isRed = false;
                rotateRight(parent);
                //接下来继续按策略4处理，不进行终止
            } else if (sibling.isRed && isLeft) {
                //策略1镜面操作:目的是可以转化为2，3，4去处理
                parent.isRed = true;
                sibling.isRed = false;
                rotateLeft(parent);
                //接下来继续按策略4处理，不进行终止
            }  else if (isLeft && !(sibling.left == null || sibling.left.isRed == false) && (sibling.right == null || sibling.right.isRed == false)){
                //策略2：近侄子节点为红
                sibling.isRed=true;
                sibling.left.isRed=false;
                rotateRight(sibling);
                //接下来继续按策略3处理，不进行终止
            } else if(!isLeft && (sibling.left == null || sibling.left.isRed == false) && !(sibling.right == null || sibling.right.isRed == false)){
                //策略2镜面：近侄子节点为红
                sibling.isRed=true;
                sibling.right.isRed=false;
                rotateLeft(sibling);
                //接下来继续按策略3镜面处理，不进行终止
            } else if(isLeft && !(sibling.right == null || sibling.right.isRed == false)){
                //策略3：远侄子节点为红
                boolean scolor=sibling.isRed;
                sibling.isRed=parent.isRed;
                parent.isRed=scolor;
                sibling.right.isRed=false;
                rotateLeft(parent);
                //终止循环：因为局部树内黑色节点是相等的，没有变化
                cur=root;
            } else if(!isLeft && !(sibling.left == null || sibling.left.isRed == false)){
                //策略3镜面：远侄子节点为红
                boolean scolor=sibling.isRed;
                sibling.isRed=parent.isRed;
                parent.isRed=scolor;
                sibling.left.isRed=false;
                rotateRight(parent);
                //终止循环：因为局部树内黑色节点是相等的，没有变化
                cur=root;
            } else if ((sibling.left == null || sibling.left.isRed == false) && (sibling.right == null || sibling.right.isRed == false)) {
                if(parent.isRed==true){
                    //策略4
                    parent.isRed=false;
                }
                //策略4和5公共部分，需要一直往上进行调整
                sibling.isRed = true;
                cur=parent;
                parent=cur.parent;
            }
        }
        //对于仅有一个子节点的黑色节点的处理
        if(isRed){
            cur.isRed=false;
        }
        //根节点保证为黑色
        if(root!=null){
            root.isRed=false;
            root.parent=null;
        }

    }

    public static void main(String[] args) {
        MyRBTree<Integer> bst = new MyRBTree<Integer>();
        bst.add(45);
        bst.add(43);
        bst.add(50);
        bst.add(42);
        bst.add(49);
        bst.add(51);
        bst.add(48);
        bst.printTree();
        System.out.println();
        System.out.println("-------------------------");
        bst.remove(48);
        bst.printTree();
        System.out.println();
        System.out.println("-------------------------");
    }


}
