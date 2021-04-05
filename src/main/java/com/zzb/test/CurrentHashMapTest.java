package com.zzb.test;

import net.sf.ehcache.search.aggregator.Count;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class CurrentHashMapTest {
    private static ConcurrentHashMap<String,String> concurrentHashMap=new ConcurrentHashMap<>();
    private static Hashtable<String,String> hashtable=new Hashtable<>();
    private static HashMap<String,String> hashMap=new HashMap<>();

    public static void main(String[] args) {
        testConcurrentHashMapThreadSafe();
        System.out.println(concurrentHashMap.size()+" last:"+concurrentHashMap.get("concurrentHashMap9999"));
        testHashtableThreadSafe();
        System.out.println(hashtable.size()+" last:"+hashtable.get("hashtable9999"));
        testHashMapThreadSafe();
        System.out.println(hashMap.size()+" last:"+hashMap.get("hashmap9999"));
        System.out.println("test end");

    }
    public static void  testConcurrentHashMapThreadSafe(){
        CountDownLatch latch=new CountDownLatch(100000);
        long startTime=System.currentTimeMillis();
        for (int i=0;i<100000;i++){
            new ConcurrentThread(i,"concurrentHashMap",concurrentHashMap,latch).start();

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("ConcurrentHashMap take time:"+(endTime-startTime));

    }
    public static void testHashtableThreadSafe(){
        CountDownLatch latch=new CountDownLatch(100000);
        long startTime=System.currentTimeMillis();
        for (int i=0;i<100000;i++){
            new ConcurrentHashTableThread(i,"hashtable",hashtable,latch).start();

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("Hashtable take time:"+(endTime-startTime));
    }
    public static void testHashMapThreadSafe(){System.out.println("enter test HashMap");
        CountDownLatch latch=new CountDownLatch(100000);

        long startTime=System.currentTimeMillis();
        for (int i=0;i<100000;i++){
            new ConcurrentHashMapThread(i,"hashmap",hashMap,latch).start();

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        System.out.println(" HashMap take time:"+(endTime-startTime));
    }
}
class ConcurrentThread extends Thread{
    public int i;
    public String name;
    private ConcurrentHashMap<String,String> map;
    private CountDownLatch latch;
    public ConcurrentThread(int i,String name,ConcurrentHashMap<String,String> map,CountDownLatch latch){

        this.i=i;
        this.name=name;
        this.map=map;
        this.latch=latch;
    }

    @Override
    public void run() {
        super.run();
        map.put(name+i,i+"");
        latch.countDown();
    }
}
class ConcurrentHashTableThread extends Thread{
    public int i;
    public String name;
    private Hashtable<String,String> map;
    private CountDownLatch latch;
    public ConcurrentHashTableThread(int i,String name,Hashtable<String,String> map,CountDownLatch latch){

        this.i=i;
        this.name=name;
        this.map=map;
        this.latch=latch;
    }

    @Override
    public void run() {
        super.run();
        map.put(name+i,i+"");
        this.latch.countDown();
    }
}
class ConcurrentHashMapThread extends Thread{
    public int i;
    public String name;
    private HashMap<String,String>  map;
    private CountDownLatch latch;
    public ConcurrentHashMapThread(int i,String name,HashMap<String,String> map,CountDownLatch latch){

        this.i=i;
        this.name=name;
        this.map=map;
        this.latch=latch;
    }

    @Override
    public void run() {
        super.run();
        map.put(name+i,i+"");
        latch.countDown();
    }
}
