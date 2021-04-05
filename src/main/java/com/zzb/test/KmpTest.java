package com.zzb.test;

import java.util.Arrays;

public class KmpTest {
    // 获取KMP的next数组
    // 比如说给定字符串ABBDBBA
    //BBABBABB
    //先找出他的最长公共子串
    public static void main(String[] args) {
        String s="GTGTGCF";
        int[] next = getNext(s);
        System.out.println(Arrays.toString(next));

        String txt="GTGTGAGCTGGTGTGTGCFAA";
        char[] chars = txt.toCharArray();
        char[] pattern=s.toCharArray();
        int j=0;
        int start=-1;
        for (int i = 0; i < chars.length; i++) {
            while (j > 0 && chars[i] != pattern[j]) {
                j = next[j];
            }
            if (chars[i] == pattern[j]) {
                j++;
            }
            if (j == pattern.length) {
                start = i - pattern.length + 1;
                break;
            }
        }
        System.out.println("最终结果为起始地址："+start);
    }

    // 比如说给定字符串ABBDBBA
    //BBABBABB
    //先找出他的最长公共子串
    public static int[] getNext(String str){
        int[] next=new int[str.length()];
        int i=2;
        int j=0;
        char[] temp=str.toCharArray();	//对字符串里字符操作时，先转换为字符数组
        next[0]=0;
        next[1]=0;
        for (; i < str.length(); i++) {
            while(j>0&&temp[i-1]!=temp[j]){
                //可以把j指针看作是模式串，当j位置匹配失败，可以借由
                //目前已经计算的next数组，知道下一个要匹配的是哪个位置
                j=next[j];
            }
            //此时j==0或temp[i-1]==temp[j]
            if(temp[i-1]==temp[j]){
                next[i]=next[i-1]+1;
                j++;
            }
            //j==0不可回溯,直接让j与下一个i进行比较
        }
//        while(i<temp.length) {	//不要写temp[i]!='\0'
//            if(temp[i-1]==temp[j]){
//                next[i]=next[i-1]+1;
//                i++;
//                j++;
//            }else{
//                if(j==0){
//                    //已经不可回溯
//                    next[i]=0;
//                    i++;
//                }else{
//                    //可以把j指针看作是模式串，当j位置匹配失败，可以借由
//                    //目前已经计算的next数组，知道下一个要匹配的是哪个位置
//                    j=next[j];
//                }
//            }
//        }
        return next;
    }
}
