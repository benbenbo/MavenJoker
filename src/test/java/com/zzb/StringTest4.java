package com.zzb;

import java.util.HashSet;

/**
 * jdk7 -XX:PermSize=6m -XX:MaxPermSize=6m -XX:+PrintGCDetails
 * jdk8 -XX:MetaspaceSize=6m -XX:MaxMetaspaceSize=6m -Xms6m -Xmx6m -XX:+PrintGCDetails
 */
public class StringTest4 {
    public static void main(String[] args) {
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("1");
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
    }
}
