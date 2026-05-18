package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.BeginnerDS;

import java.util.*;

/**
 * ARRAY & STRING PROBLEMS - BEGINNER LEVEL
 * 
 * Problems covered:
 * 1. LeetCode 1: Two Sum
 * 2. LeetCode 121: Best Time to Buy and Sell Stock
 * 3. LeetCode 217: Contains Duplicate
 * 4. LeetCode 242: Valid Anagram
 * 5. LeetCode 26: Remove Duplicates from Sorted Array
 */

public class ArrayStringProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 1 - Two Sum
    // ========================================
    
    /**
     * LeetCode 1: Two Sum
     * 
     * Given an array of integers nums and an integer target, return the indices
     * of the two numbers that add up to the target.
     * You may assume each input has exactly one solution, and the same element
     * cannot be used twice.
     * 
     * Example:
     * Input: nums = [2,7,11,15], target = 9
     * Output: [0,1]
     * Explanation: nums[0] + nums[1] = 2 + 7 = 9
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            
            map.put(nums[i], i);
        }
        
        return new int[]{};
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 121 - Best Time to Buy and Sell Stock
    // ========================================
    
    /**
     * LeetCode 121: Best Time to Buy and Sell Stock
     * 
     * You are given an array prices where prices[i] is the price on ith day.
     * You want to maximize your profit by choosing a single day to buy and
     * a single day to sell (after buy). Return the maximum profit.
     * 
     * Example:
     * Input: prices = [7,1,5,3,6,4]
     * Output: 5
     * Explanation: Buy on day 2 (price=1), sell on day 5 (price=6)
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int minPrice = prices[0];
        int maxProfit = 0;
        
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - minPrice;
            maxProfit = Math.max(maxProfit, profit);
            minPrice = Math.min(minPrice, prices[i]);
        }
        
        return maxProfit;
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 217 - Contains Duplicate
    // ========================================
    
    /**
     * LeetCode 217: Contains Duplicate
     * 
     * Given an integer array nums, return true if any value appears at least twice,
     * and return false if every element is distinct.
     * 
     * Example:
     * Input: nums = [1,2,3,1]
     * Output: true
     * 
     * Input: nums = [1,2,3,4]
     * Output: false
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            if (seen.contains(num)) {
                return true;
            }
            seen.add(num);
        }
        
        return false;
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 242 - Valid Anagram
    // ========================================
    
    /**
     * LeetCode 242: Valid Anagram
     * 
     * Given two strings s and t, return true if t is an anagram of s, and false otherwise.
     * An anagram is a word formed by rearranging the letters of another word.
     * 
     * Example:
     * Input: s = "anagram", t = "nagaram"
     * Output: true
     * 
     * Input: s = "rat", t = "car"
     * Output: false
     * 
     * Time: O(n)
     * Space: O(1) if assuming lowercase English letters (26 chars)
     */
    public static boolean isAnagram(String s, String t) {
        // Method 1: Using character frequency count
        if (s.length() != t.length()) {
            return false;
        }
        
        int[] count = new int[26];
        
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
        }
        
        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative approach: Sort both strings
     * Time: O(n log n), Space: O(1)
     */
    public static boolean isAnagramSort(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();
        
        Arrays.sort(sArr);
        Arrays.sort(tArr);
        
        return Arrays.equals(sArr, tArr);
    }
    
    // ========================================
    // PROBLEM 5: LeetCode 26 - Remove Duplicates from Sorted Array
    // ========================================
    
    /**
     * LeetCode 26: Remove Duplicates from Sorted Array
     * 
     * Given an integer array nums sorted in non-decreasing order,
     * remove the duplicates in-place such that each unique element appears only once.
     * Return the number of unique elements.
     * 
     * Example:
     * Input: nums = [1,1,2]
     * Output: 1, nums = [1,2,_]
     * 
     * Input: nums = [0,0,1,1,1,2,2,3,3,4]
     * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int k = 1; // k is the position to insert next unique element
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[k] = nums[i];
                k++;
            }
        }
        
        return k;
    }
    
    // ========================================
    // MAIN - Test Array & String Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ARRAY & STRING PROBLEMS - BEGINNER ===\n");
        
        // Problem 1: Two Sum
        System.out.println("1. LeetCode 1: Two Sum");
        int[] nums1 = {2, 7, 11, 15};
        int target = 9;
        System.out.println("Input: nums = " + Arrays.toString(nums1) + ", target = " + target);
        System.out.println("Output: " + Arrays.toString(twoSum(nums1, target)) + "\n");
        
        // Problem 2: Best Time to Buy and Sell Stock
        System.out.println("2. LeetCode 121: Best Time to Buy and Sell Stock");
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("Input: prices = " + Arrays.toString(prices));
        System.out.println("Output: " + maxProfit(prices) + "\n");
        
        // Problem 3: Contains Duplicate
        System.out.println("3. LeetCode 217: Contains Duplicate");
        int[] nums3 = {1, 2, 3, 1};
        System.out.println("Input: nums = " + Arrays.toString(nums3));
        System.out.println("Output: " + containsDuplicate(nums3) + "\n");
        
        // Problem 4: Valid Anagram
        System.out.println("4. LeetCode 242: Valid Anagram");
        String s = "anagram";
        String t = "nagaram";
        System.out.println("Input: s = \"" + s + "\", t = \"" + t + "\"");
        System.out.println("Output: " + isAnagram(s, t) + "\n");
        
        // Problem 5: Remove Duplicates
        System.out.println("5. LeetCode 26: Remove Duplicates from Sorted Array");
        int[] nums5 = {1, 1, 2};
        System.out.println("Input: nums = " + Arrays.toString(nums5));
        int k = removeDuplicates(nums5);
        System.out.println("Output: k = " + k + ", nums = " + Arrays.toString(Arrays.copyOfRange(nums5, 0, k)));
    }
}
