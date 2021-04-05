package com.zzb.test;

import java.util.Arrays;

public class BagTest {
    public static void main(String[] args) {
        BagTest bagTest=new BagTest();
        int value=bagTest.getBiggestValue();
        System.out.println("最大价值为"+value);

    }

    public int getBiggestValue(){
        //候选集合
        int[] times=new int[]{71,69,1};//每一株草药需要的时间
        int[] values=new int[]{100,1,2};//每一株草药需要的价值

        //按单位时间内价值最大的进行排序
        double[] r=new double[times.length];
        int[] indexes=new int[times.length];

        //O(n)遍历
        for(int i=0;i<times.length;i++){
            r[i]=(double)values[i]/(double)times[i];
            indexes[i]=i;//单位价值所对应的候选集的索引
        }
        //对r进行排序
        shellSort(r,indexes);

        int totalValue=0;//解集合

        int totalTime=70;

        //选择函数
        //外循环 O(n)
        for(int i=r.length-1;i>-1;i--){
            int index=indexes[i];
            int restTime = totalTime - times[index];
            if(restTime>=0){
                //判断可行性
                totalTime=restTime;
                totalValue += values[index];//加入解集
                if (totalTime == 0) {
                    //解决函数
                    return totalValue;
                }
            }

        }
        return -1;
    }


    //排序算法是O(logn)
    public void shellSort(double[] r,int[] index){
        int len = r.length;
        int gap = len/2;
        while(gap>0){
            for(int j=gap;j<len;j++){
                double current=r[j];
                int preIndex=j-1;
                while(preIndex>-1&&current<r[preIndex]){
                    r[preIndex+gap]=r[preIndex];
                    preIndex--;
                }
                r[preIndex+gap]=current;
                int x=index[j];
                index[j]=index[preIndex+gap];
                index[preIndex+gap]=x;
            }
            gap/=2;
        }
    }
}
