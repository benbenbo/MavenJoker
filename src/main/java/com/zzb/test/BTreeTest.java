package com.zzb.test;

public class BTreeTest {
    public static void main(String[] args) {
        BTree tree=new BTree();
        tree.insertNode(1);
        tree.insertNode(11);
        tree.insertNode(12);
        tree.insertNode(13);
        tree.insertNode(14);
        tree.insertNode(15);
        tree.insertNode(16);
        tree.insertNode(17);

        tree.inOrder();

        tree.removeNode(1);
        System.out.println("---------");
        tree.inOrder();

    }
}
