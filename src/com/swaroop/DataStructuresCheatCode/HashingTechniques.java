package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * HASHING TECHNIQUES - FAST LOOKUP
 * 
 * 💡 CHEAT MEMORY:
 * HashMap: Average O(1), Uses hash function
 * HashSet: Unique elements only, O(1) operations
 * Collision Handling: Chaining or Open Addressing
 * Load Factor: elements/capacity (rehash when > threshold)
 */

public class HashingTechniques {
    
    // ========================================
    // 1. TWO SUM USING HASH MAP
    // ========================================
    
    /**
     * Two Sum - Find two numbers that add to target
     * Using HashMap for O(n) solution
     * 
     * Example: nums=[2,7,11,15], target=9
     * Output: [0,1]
     */
    public static int[] twoSumHash(int[] nums, int target) {
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
    // 2. CONTAINS DUPLICATE
    // ========================================
    
    /**
     * Contains Duplicate
     * Check if array has any duplicates
     * 
     * Time: O(n), Space: O(n)
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
    // 3. VALID ANAGRAM
    // ========================================
    
    /**
     * Valid Anagram
     * Check if two strings are anagrams
     * 
     * Example: "listen" and "silent" = true
     */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Integer> charCount = new HashMap<>();
        
        // Count characters in s
        for (char c : s.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }
        
        // Verify with t
        for (char c : t.toCharArray()) {
            if (!charCount.containsKey(c)) {
                return false;
            }
            charCount.put(c, charCount.get(c) - 1);
            if (charCount.get(c) < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    // ========================================
    // 4. GROUP ANAGRAMS
    // ========================================
    
    /**
     * Group Anagrams
     * Group words that are anagrams
     * 
     * Example: ["eat","tea","tan","ate","nat","bat"]
     * Output: [["eat","tea","ate"],["tan","nat"],["bat"]]
     */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        
        return new ArrayList<>(map.values());
    }
    
    // ========================================
    // 5. LONGEST SUBSTRING WITHOUT REPEATING
    // ========================================
    
    /**
     * Longest Substring Without Repeating Characters
     * Using HashMap to track character positions
     * 
     * Example: "abcabcbb"
     * Output: 3 ("abc")
     */
    public static int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> charIndex = new HashMap<>();
        int maxLen = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
                left = charIndex.get(c) + 1;
            }
            
            charIndex.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    // ========================================
    // 6. MAJORITY ELEMENT
    // ========================================
    
    /**
     * Majority Element - Element appearing > n/2 times
     * 
     * Example: [3,2,3]
     * Output: 3 (appears 2 times, n/2=1.5)
     */
    public static int majorityElement(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        
        for (int num : nums) {
            count.put(num, count.getOrDefault(num, 0) + 1);
            if (count.get(num) > nums.length / 2) {
                return num;
            }
        }
        
        return -1;
    }
    
    // ========================================
    // 7. LRU CACHE (Advanced)
    // ========================================
    
    /**
     * LRU Cache - Least Recently Used cache with O(1) operations
     */
    public static class LRUCache {
        private int capacity;
        private Map<Integer, Integer> cache;
        private LinkedList<Integer> order;
        
        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();
            this.order = new LinkedList<>();
        }
        
        public int get(int key) {
            if (!cache.containsKey(key)) {
                return -1;
            }
            
            order.remove((Integer) key);
            order.addLast(key);
            
            return cache.get(key);
        }
        
        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                order.remove((Integer) key);
            } else if (cache.size() == capacity) {
                int lru = order.removeFirst();
                cache.remove(lru);
            }
            
            cache.put(key, value);
            order.addLast(key);
        }
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== HASHING TECHNIQUES ===\n");
        
        // 1. Two Sum
        System.out.println("1. TWO SUM");
        int[] nums = {2, 7, 11, 15};
        int[] result = twoSumHash(nums, 9);
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Result: " + Arrays.toString(result) + "\n");
        
        // 2. Contains Duplicate
        System.out.println("2. CONTAINS DUPLICATE");
        int[] arr = {1, 2, 3, 1};
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Has duplicate: " + containsDuplicate(arr) + "\n");
        
        // 3. Valid Anagram
        System.out.println("3. VALID ANAGRAM");
        System.out.println("'listen' and 'silent': " + isAnagram("listen", "silent") + "\n");
        
        // 4. Group Anagrams
        System.out.println("4. GROUP ANAGRAMS");
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Result: " + groupAnagrams(strs) + "\n");
        
        // 5. Longest Substring
        System.out.println("5. LONGEST SUBSTRING WITHOUT REPEATING");
        String s = "abcabcbb";
        System.out.println("String: " + s);
        System.out.println("Length: " + lengthOfLongestSubstring(s) + "\n");
        
        // 6. Majority Element
        System.out.println("6. MAJORITY ELEMENT");
        int[] arr2 = {3, 2, 3};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Majority: " + majorityElement(arr2));
    }
}
