package com.swaroop.DataStructuresCheatCode;

/**
 * GREEDY ALGORITHMS - LOCAL OPTIMA APPROACH
 * 
 * 💡 STRATEGY: "Pick best locally, hope it's globally best"
 * 
 * NOT applicable to all problems - only when:
 * 1. Greedy choice property (local choice leads to global optimum)
 * 2. Optimal substructure
 * 
 * USE FOR:
 * - Activity Selection
 * - Huffman Coding
 * - Fractional Knapsack
 * - Jump Game
 * - Interval Scheduling
 */

public class GreedyAlgorithms {
    
    // ========================================
    // 1. ACTIVITY SELECTION
    // ========================================
    
    /**
     * Activity Selection Problem
     * Select max number of non-overlapping activities
     * 
     * Greedy: Always pick activity that ends earliest
     * Time: O(n log n) for sorting, O(n) for selection
     */
    public static int maxActivities(int[] start, int[] end) {
        int n = start.length;
        
        // Create pairs and sort by end time
        Activity[] activities = new Activity[n];
        for (int i = 0; i < n; i++) {
            activities[i] = new Activity(start[i], end[i]);
        }
        
        java.util.Arrays.sort(activities, (a, b) -> a.end - b.end);
        
        int count = 1;  // First activity always selected
        int lastEnd = activities[0].end;
        
        for (int i = 1; i < n; i++) {
            if (activities[i].start >= lastEnd) {
                count++;
                lastEnd = activities[i].end;
            }
        }
        
        return count;
    }
    
    private static class Activity {
        int start, end;
        Activity(int s, int e) {
            start = s;
            end = e;
        }
    }
    
    // ========================================
    // 2. JUMP GAME
    // ========================================
    
    /**
     * Jump Game - Can reach last index?
     * From index i, can jump to any index in [i, i+nums[i]]
     * 
     * Greedy: Track maximum index we can reach
     * Time: O(n), Space: O(1)
     */
    public static boolean canJump(int[] nums) {
        int maxReach = 0;
        
        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false;  // Can't reach this index
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        
        return true;
    }
    
    /**
     * Jump Game II - Minimum jumps to reach end
     */
    public static int jump(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;
        
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }
        
        return jumps;
    }
    
    // ========================================
    // 3. GAS STATION
    // ========================================
    
    /**
     * Gas Station - Can complete circuit?
     * At station i, gain nums[i] gas and need cost[i] to reach i+1
     * 
     * Greedy: Start from index where remaining gas becomes negative
     * Time: O(n), Space: O(1)
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0;
        int currentGas = 0;
        int start = 0;
        
        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i] - cost[i];
            currentGas += gas[i] - cost[i];
            
            if (currentGas < 0) {
                start = i + 1;
                currentGas = 0;
            }
        }
        
        return (totalGas < 0) ? -1 : start;
    }
    
    // ========================================
    // 4. INTERVAL SCHEDULING
    // ========================================
    
    /**
     * Merge Intervals - Merge overlapping intervals
     * 
     * Greedy: Sort by start time, merge overlapping
     * Time: O(n log n)
     */
    public static int[][] mergeIntervals(int[][] intervals) {
        if (intervals.length <= 1) return intervals;
        
        java.util.Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        java.util.List<int[]> merged = new java.util.ArrayList<>();
        int[] current = intervals[0];
        
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= current[1]) {
                // Overlapping - merge
                current[1] = Math.max(current[1], intervals[i][1]);
            } else {
                // Non-overlapping - add and start new
                merged.add(current);
                current = intervals[i];
            }
        }
        merged.add(current);
        
        return merged.toArray(new int[0][]);
    }
    
    // ========================================
    // 5. ASSIGN COOKIES
    // ========================================
    
    /**
     * Assign Cookies - Maximize satisfied children
     * Each child has greed factor, each cookie has size
     * Child satisfied if cookie size >= greed
     * 
     * Greedy: Sort both, match smallest possible
     * Time: O(n log n)
     */
    public static int findContentChildren(int[] greed, int[] cookieSize) {
        java.util.Arrays.sort(greed);
        java.util.Arrays.sort(cookieSize);
        
        int childIdx = 0;
        int cookieIdx = 0;
        
        while (childIdx < greed.length && cookieIdx < cookieSize.length) {
            if (cookieSize[cookieIdx] >= greed[childIdx]) {
                childIdx++;
            }
            cookieIdx++;
        }
        
        return childIdx;
    }
    
    // ========================================
    // 6. FRACTIONAL KNAPSACK
    // ========================================
    
    /**
     * Fractional Knapsack - Maximize value with weight limit
     * Can take fractional items (unlike 0/1 knapsack)
     * 
     * Greedy: Sort by value/weight ratio, take greedily
     * Time: O(n log n)
     */
    public static double fractionalKnapsack(int capacity, int[] values, int[] weights) {
        int n = values.length;
        Item[] items = new Item[n];
        
        for (int i = 0; i < n; i++) {
            items[i] = new Item(values[i], weights[i], (double) values[i] / weights[i]);
        }
        
        // Sort by value/weight ratio
        java.util.Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));
        
        double totalValue = 0;
        int remainingCapacity = capacity;
        
        for (Item item : items) {
            if (remainingCapacity >= item.weight) {
                totalValue += item.value;
                remainingCapacity -= item.weight;
            } else {
                // Take fraction of this item
                totalValue += item.ratio * remainingCapacity;
                break;
            }
        }
        
        return totalValue;
    }
    
    private static class Item {
        int value, weight;
        double ratio;
        Item(int v, int w, double r) {
            value = v;
            weight = w;
            ratio = r;
        }
    }
    
    // ========================================
    // MAIN - Test Greedy
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== GREEDY ALGORITHMS ===\n");
        
        // 1. Activity Selection
        System.out.println("1. ACTIVITY SELECTION");
        int[] start = {1, 3, 0, 5, 8, 5};
        int[] end = {2, 4, 6, 7, 9, 9};
        System.out.println("Max activities: " + maxActivities(start, end) + "\n");
        
        // 2. Jump Game
        System.out.println("2. JUMP GAME");
        int[] nums1 = {2, 3, 1, 1, 4};
        System.out.println("Can reach end: " + canJump(nums1));
        System.out.println("Min jumps: " + jump(nums1) + "\n");
        
        // 3. Gas Station
        System.out.println("3. GAS STATION");
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        System.out.println("Starting station: " + canCompleteCircuit(gas, cost) + "\n");
        
        // 4. Merge Intervals
        System.out.println("4. MERGE INTERVALS");
        int[][] intervals = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] merged = mergeIntervals(intervals);
        System.out.println("Merged count: " + merged.length + "\n");
        
        // 5. Assign Cookies
        System.out.println("5. ASSIGN COOKIES");
        int[] greed = {1, 2, 3};
        int[] cookieSize = {1, 1};
        System.out.println("Content children: " + findContentChildren(greed, cookieSize) + "\n");
        
        // 6. Fractional Knapsack
        System.out.println("6. FRACTIONAL KNAPSACK");
        int[] values = {60, 100, 120};
        int[] itemWeights = {10, 20, 30};
        System.out.println("Max value: " + fractionalKnapsack(50, values, itemWeights));
    }
}
