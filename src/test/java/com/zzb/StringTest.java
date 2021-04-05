package com.zzb;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringTest {
    public static void main(String[] args) {
        String a="abc";//放在字符串池中
        String b="abc";
        a="hello";
        System.out.println(a==b);
    }
}
