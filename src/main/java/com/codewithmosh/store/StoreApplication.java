package com.codewithmosh.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;

@SpringBootApplication
public class  StoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

//    public int[] twoSum(int[] nums, int target) {
//        HashMap<Integer , Integer> nums1 = new HashMap<>();
//        for(int i = 0 ; i < nums.length ; i++){
//            if(nums1.containsKey(target - nums[i])){
//                return new int[] {nums1.get(target-nums[i]) , i};
//            }
//            nums1.put(nums[i] , i);
//        }
//        throw new IllegalArgumentException("No solution found");
//
//    }
}
