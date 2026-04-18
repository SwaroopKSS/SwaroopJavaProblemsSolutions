package com.swaroop.dsa.arrays;

/**
 * ARRAYS - FOUNDATIONAL DATA STRUCTURE
 * 
 * 💡 CHEAT MEMORY:
 * Arrays = O(1) access, O(n) insertion/deletion at middle
 * Use for: Random access, fixed size collections
 * 
 * KEY TECHNIQUES:
 * 1. Two Pointers - left and right pointers converging
 * 2. Sliding Window - maintain window of fixed/variable size
 * 3. Prefix Sums - store cumulative sums for range queries
 * 4. Kadane's Algorithm - find max subarray sum in O(n)
 */

public class ArraysBasics {
    
    // ========================================
    // 1. TWO POINTERS TECHNIQUE
    // ========================================
    
    /**
     * Two Sum Problem
     * Find two numbers that add up to target
     * 
     * Example: arr = [2,7,11,15], target = 9
     * Output: [0,1] (2+7=9)
     */
    public static int[] twoSum(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;  // Need larger sum, move left pointer right
            } else {
                right--;  // Need smaller sum, move right pointer left
            }
        }
        return new int[]{-1, -1};  // No pair found
    }
    
    /**
     * Reverse Array In-Place
     * Do it using two pointers from both ends
     */
    public static void reverseArray(int[] arr) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            // Swap
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            
            left++;
            right--;
        }
    }
    
    /**
     * Container With Most Water
     * Find two lines that can hold max water
     * 
     * Logic: Start from widest container, move inward
     * Move the smaller height pointer (can't improve by moving other)
     */
    public static int maxArea(int[] heights) {
        int maxWater = 0;
        int left = 0, right = heights.length - 1;
        
        while (left < right) {
            int width = right - left;
            int height = Math.min(heights[left], heights[right]);
            int area = width * height;
            maxWater = Math.max(maxWater, area);
            
            // Move the pointer with smaller height (chance to find taller line)
            if (heights[left] < heights[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxWater;
    }
    
    // ========================================
    // 2. SLIDING WINDOW TECHNIQUE
    // ========================================
    
    /**
     * Maximum Sum of Subarray of Size K
     * Find max sum of any k consecutive elements
     * 
     * Example: arr = [2,1,5,1,3,2], k = 3
     * Subarrays: [2,1,5]=8, [1,5,1]=7, [5,1,3]=9, [1,3,2]=6
     * Output: 9
     */
    public static int maxSumSubarray(int[] arr, int k) {
        if (k > arr.length) return -1;
        
        int windowSum = 0;
        // Calculate sum of first window
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        
        int maxSum = windowSum;
        
        // Slide the window
        for (int i = k; i < arr.length; i++) {
            // Remove leftmost element of previous window, add new right element
            windowSum = windowSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Longest Substring Without Repeating Characters
     * Use sliding window with HashSet
     * 
     * Example: "abcabcbb"
     * Output: 3 ("abc")
     */
    public static int lengthOfLongestSubstring(String s) {
        java.util.Set<Character> charSet = new java.util.HashSet<>();
        int maxLen = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            // If char already in window, remove from left until it's gone
            while (charSet.contains(c)) {
                charSet.remove(s.charAt(left));
                left++;
            }
            
            charSet.add(c);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    // ========================================
    // 3. PREFIX SUM TECHNIQUE
    // ========================================
    
    /**
     * Range Sum Query
     * Answer multiple range sum queries efficiently
     * 
     * Example: arr = [1,2,3,4,5]
     * Query: sum from index 1 to 3 = 2+3+4 = 9
     */
    public static class RangeSumQuery {
        private int[] prefixSum;
        
        public RangeSumQuery(int[] nums) {
            prefixSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }
        
        /**
         * Get sum of range [left, right]
         * Time: O(1)
         */
        public int rangeSum(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }
    
    // ========================================
    // 4. KADANE'S ALGORITHM (Maximum Subarray)
    // ========================================
    
    /**
     * Maximum Subarray Sum (Kadane's Algorithm)
     * Find contiguous subarray with largest sum
     * 
     * Example: [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6 (subarray [4,-1,2,1])
     * 
     * LOGIC:
     * - Keep track of max sum ending at current position
     * - At each position, decide: continue from previous or start new
     * - Track overall max
     */
    public static int maxSubarraySum(int[] arr) {
        int maxCurrent = arr[0];  // Max sum ending at current index
        int maxGlobal = arr[0];   // Overall maximum
        
        for (int i = 1; i < arr.length; i++) {
            // Either continue previous sum or start fresh
            maxCurrent = Math.max(arr[i], maxCurrent + arr[i]);
            // Update global max
            maxGlobal = Math.max(maxGlobal, maxCurrent);
        }
        
        return maxGlobal;
    }
    
    /**
     * Maximum Subarray with Index (returns subarray itself)
     */
    public static int[] maxSubarrayWithIndex(int[] arr) {
        int maxCurrent = arr[0];
        int maxGlobal = arr[0];
        int tempStart = 0;
        int start = 0, end = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxCurrent + arr[i]) {
                maxCurrent = arr[i];
                tempStart = i;
            } else {
                maxCurrent = maxCurrent + arr[i];
            }
            
            if (maxCurrent > maxGlobal) {
                maxGlobal = maxCurrent;
                start = tempStart;
                end = i;
            }
        }
        
        // Extract subarray from start to end
        int[] result = new int[end - start + 1];
        for (int i = 0; i < result.length; i++) {
            result[i] = arr[start + i];
        }
        return result;
    }
    
    // ========================================
    // MAIN - Test all functions
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ARRAYS BASICS ===\n");
        
        // Two Sum
        System.out.println("1. TWO SUM");
        int[] arr1 = {2, 7, 11, 15};
        int[] result = twoSum(arr1, 9);
        System.out.println("Array: " + java.util.Arrays.toString(arr1));
        System.out.println("Target: 9");
        System.out.println("Result indices: [" + result[0] + "," + result[1] + "]\n");
        
        // Reverse Array
        System.out.println("2. REVERSE ARRAY");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Before: " + java.util.Arrays.toString(arr2));
        reverseArray(arr2);
        System.out.println("After: " + java.util.Arrays.toString(arr2) + "\n");
        
        // Max Sum Subarray
        System.out.println("3. MAX SUM SUBARRAY (k=3)");
        int[] arr3 = {2, 1, 5, 1, 3, 2};
        System.out.println("Array: " + java.util.Arrays.toString(arr3));
        System.out.println("Max sum: " + maxSumSubarray(arr3, 3) + "\n");
        
        // Kadane's Algorithm
        System.out.println("4. KADANE'S ALGORITHM (Max Subarray)");
        int[] arr4 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Array: " + java.util.Arrays.toString(arr4));
        System.out.println("Max sum: " + maxSubarraySum(arr4));
        System.out.println("Subarray: " + java.util.Arrays.toString(maxSubarrayWithIndex(arr4)) + "\n");
        
        // Longest Substring Without Repeating
        System.out.println("5. LONGEST SUBSTRING WITHOUT REPEATING");
        String s = "abcabcbb";
        System.out.println("String: " + s);
        System.out.println("Length: " + lengthOfLongestSubstring(s) + "\n");
    }
}
