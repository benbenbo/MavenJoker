package com.zzb.test;

public class BSTreeTest {
    public static void main(String[] args) {
        BSTree<Integer> bsTree=new BSTree<>();
        bsTree.insert(2);
        bsTree.insert(4);
        bsTree.insert(87);
        bsTree.insert(3);
        bsTree.insert(5);
        bsTree.insert(33);

        bsTree.inOrder();

    }
}
