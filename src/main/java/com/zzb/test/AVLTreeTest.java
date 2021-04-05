package com.zzb.test;

public class AVLTreeTest {
    public static void main(String[] args) throws Throwable {
        AVLTree tree=new AVLTree();
//        tree.put(42);
//        tree.put(22);
//        tree.put(14);
//        tree.put(14);
//        tree.put(5);
//        tree.put(55);
//        tree.put(25);
//        tree.put(15);

        tree.put(10);
        tree.put(7);
        tree.put(15);
        tree.put(3);
        tree.put(8);
        tree.put(9);
        tree.inOrder();
        tree.deleteData(10);
        tree.inOrder();
        tree.sequenceErgodic();

    }
}
