package com.zzb.test;

import java.util.HashMap;

public class RedBlackTest {
    public static void main(String[] args) {
//        HashMap<String, String> stringStringHashMap = new HashMap<>(30);
//        for (int i = 0; i < 36; i++) {
//            stringStringHashMap.put(i+"",i+"");
//        }
        func();
    }

    public static void func(){
        int i=0;
        for(int j=0;j < 100;j++){
            i = ++i;
        }
        System.out.println(i);//打印什么？？？
    }
}
