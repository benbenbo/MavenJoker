package com.zzb.test;

public class DpTest {
    public static void main(String[] args) {
        DpTest dpTest = new DpTest();
        int n=5;
        int dynmian=dpTest.dynmian2(n);
        System.out.println("最终结果为："+dynmian);
    }

    public int dynmian(int[] dp,int n){
        if(n==0||dp[n]!=0){
            return dp[n];
        }
        int x=dynmian(dp,n-1);
        int y=dynmian(dp,n-2);
        if(n-1!=0&&dp[n-1]==0){
            dp[n-1]=x;
        }
        if(n-2!=0&&dp[n-2]==0){
            dp[n-2]=y;
        }
        return x+y;
    }


    public int dynmian2(int n){
        int[] dp=new int[n+1];
        dp[1]=1;
        dp[2]=2;
        for (int i = 3; i < n; i++) {
            dp[i]=dp[i-1]+dp[i-2];
        }
        return dp[n];
    }

    public int dynmianOri(int n){
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        return dynmianOri(n-1)+dynmianOri(n-2);
    }
}
