package com.zzb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author XBird
 * @date 2021/7/10
 **/
@RunWith(JUnit4.class)
public class ArrayTest {
    @Test
    public void test01(){
        int[] ints = twoSum(new int[]{2, 7, 11, 15}, 9);
        System.out.println(Arrays.toString(ints));
    }


    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> numMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            numMap.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            if(numMap.containsKey(target-nums[i]) && i<numMap.get(target-nums[i])){
                return new int[]{i,numMap.get(target-nums[i])};
            }
        }
        return null;
    }
}
