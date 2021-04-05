package com.zzb;

public class StringExer {
    String str=new String("good");
    char[] ts={'t','e','s','t'};
    public void change(String str,char[] ch){
        str="test ok";
        ch[0]='b';
    }

    public static void main(String[] args) {
        StringExer stringExer = new StringExer();
        stringExer.change(stringExer.str,stringExer.ts);
        System.out.println(stringExer.str);//good
        System.out.println(stringExer.ts);//best
    }

}
