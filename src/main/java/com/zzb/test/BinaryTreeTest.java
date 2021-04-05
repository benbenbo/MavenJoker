package com.zzb.test;

public class BinaryTreeTest {
    public static void main(String[] args) {
        BinaryTree tree=new BinaryTree();

        //62, 88, 58, 47, 35, 73, 51, 99, 37, 93
        tree.inserBst(62);
        tree.inserBst(88);
        tree.inserBst(58);
        tree.inserBst(47);
        tree.inserBst(35);
        tree.inserBst(73);
        tree.inserBst(51);
        tree.inserBst(99);
        tree.inserBst(37);
        tree.inserBst(93);
        tree.inOrder();
        tree.deleteBst(58);
        tree.inOrder();
    }
}
