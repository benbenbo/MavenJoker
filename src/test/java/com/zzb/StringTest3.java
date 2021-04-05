package com.zzb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StringTest3 {
    public static void main(String[] args){
        BufferedReader read=null;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("words.txt"))){
            long start=System.currentTimeMillis();
            String data;
            while((data=bufferedReader.readLine())!=null){
                data.intern();
            }
            long end=System.currentTimeMillis();
            System.out.println("花费的时间："+(end-start)+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
