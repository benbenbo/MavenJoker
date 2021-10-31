package com.zzb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author XBird
 * @date 2021/10/31
 **/
@RunWith(JUnit4.class)
public class LeetCode05 {
    @Test
    public void testAll(){
        String s = "babad";
        String max = test01(s);
        System.out.println("最大回文子串为："+max);
    }

    public String test01(String s){
        int max=0;
        String maxSub = "";
        //这里具有n平方的时间复杂度，isReverStr又有n的时间复杂度
        //空间复杂度为常数项
        for (int i = 0; i < s.length(); i++) {
            for (int j = i+1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if(isReverStr(sub)){
                    if(sub.length()>max){
                        maxSub = sub;
                        max = sub.length();
                    }
                }
            }
        }
        return maxSub;
    }

    public boolean isReverStr(String s){
        int len = s.length();
        for (int i = 0; i < len/2; i++) {
            if(s.charAt(i)!=s.charAt(len-i-1)){
                return false;
            }
        }
        return true;
    }
}
