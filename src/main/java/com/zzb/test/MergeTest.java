package com.zzb.test;

public class MergeTest {
    public static void main(String[] args) {
        //以下候选集合都是排好序的
        int[] count={2,2,3,4,2,1,4};
        int[] value={1,2,5,10,20,50,100};


        //问题
        int money=370;

        int num =select(count,value,money);
        System.out.println("结果使用"+num+"张面值");




    }

    //解决函数
    public static boolean isSolve(int money){
        return money==0;
    }

    //因为ath.min(restMoney / value[i], count[i])这一步已经起到了可行函数的作用
    //因此不需要可行函数
    public static boolean isValid(int money){
        return money>=0;
    }

    //选择函数
    public static int select(int[] count,int[] value,int money){
        int restMoney=money;
        //解集合
        int num=0;
        //贪心体现1：总是从面值最大的开始选
        for (int i = value.length-1; i >-1; i--) {
            //直接用有可能会变负数，所以根据面值来选
            int c = Math.min(restMoney/value[i], count[i]);
            restMoney=restMoney-value[i]*c;
            num+=c;
            if(isSolve(restMoney)){
                break;
            }
        }
        return num;
    }
}
