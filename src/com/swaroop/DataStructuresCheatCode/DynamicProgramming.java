package com.swaroop.DataStructuresCheatCode;

/**
 * DYNAMIC PROGRAMMING - OPTIMIZATION TECHNIQUE
 * 
 * 💡 CHEAT MEMORY:
 * DP = Divide Problem + Store Results
 * Two approaches: Top-down (recursion + memo) or Bottom-up (iteration + table)
 * 
 * KEY PROBLEMS:
 * 1. Fibonacci - Basic DP
 * 2. 0/1 Knapsack - Capacity problem
 * 3. LCS - Longest Common Subsequence
 * 4. Edit Distance - Minimum operations
 */

public class DynamicProgramming {
    
    // ========================================
    // 1. FIBONACCI
    // ========================================
    
    /**
     * Fibonacci (Top-down with Memoization)
     * Time: O(n), Space: O(n)
     * 
     * Example: fib(5) = 5 (0,1,1,2,3,5)
     */
    public static long fibMemoization(int n, long[] memo) {
        if (n <= 1) return n;
        if (memo[n] != -1) return memo[n];
        
        memo[n] = fibMemoization(n - 1, memo) + fibMemoization(n - 2, memo);
        return memo[n];
    }
    
    /**
     * Fibonacci (Bottom-up with Tabulation)
     * Time: O(n), Space: O(n)
     */
    public static long fibTabulation(int n) {
        if (n <= 1) return n;
        
        long[] dp = new long[n + 1];
        dp[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    // ========================================
    // 2. 0/1 KNAPSACK PROBLEM
    // ========================================
    
    /**
     * 0/1 Knapsack - Maximum value with weight constraint
     * 
     * Example: weights=[2,3,4], values=[3,4,5], capacity=5
     * Can take items with weight 2 and 3 = value 7
     */
    public static int knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(
                        values[i - 1] + dp[i - 1][w - weights[i - 1]],
                        dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        
        return dp[n][capacity];
    }
    
    // ========================================
    // 3. LONGEST COMMON SUBSEQUENCE (LCS)
    // ========================================
    
    /**
     * LCS - Longest Common Subsequence
     * 
     * Example: "abc" and "adc"
     * LCS = "ac" (length 2)
     */
    public static int lcs(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    // ========================================
    // 4. EDIT DISTANCE (Levenshtein Distance)
    // ========================================
    
    /**
     * Edit Distance - Minimum operations to convert string
     * Operations: insert, delete, replace
     * 
     * Example: "horse" to "ros"
     * Operations: 3
     */
    public static int editDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        Math.min(dp[i - 1][j], dp[i][j - 1]),
                        dp[i - 1][j - 1]
                    );
                }
            }
        }
        
        return dp[m][n];
    }
    
    // ========================================
    // 5. CLIMBING STAIRS
    // ========================================
    
    /**
     * Climbing Stairs - Ways to climb n stairs
     * Can climb 1 or 2 stairs at a time
     * 
     * Example: n=3
     * Ways: 1+1+1, 1+2, 2+1 = 3 ways
     */
    public static int climbStairs(int n) {
        if (n <= 1) return n;
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
    
    // ========================================
    // 6. COIN CHANGE
    // ========================================
    
    /**
     * Coin Change - Minimum coins to make amount
     * 
     * Example: coins=[1,2,5], amount=5
     * Min coins: 1 (use 5)
     */
    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1;
        }
        
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== DYNAMIC PROGRAMMING ===\n");
        
        // 1. Fibonacci
        System.out.println("1. FIBONACCI");
        long[] memo = new long[50];
        for (int i = 0; i < memo.length; i++) memo[i] = -1;
        System.out.println("fib(10) Memoization: " + fibMemoization(10, memo));
        System.out.println("fib(10) Tabulation: " + fibTabulation(10) + "\n");
        
        // 2. 0/1 Knapsack
        System.out.println("2. 0/1 KNAPSACK");
        int[] weights = {2, 3, 4};
        int[] values = {3, 4, 5};
        System.out.println("Max value with capacity 5: " + knapsack(weights, values, 5) + "\n");
        
        // 3. LCS
        System.out.println("3. LONGEST COMMON SUBSEQUENCE");
        System.out.println("LCS('abc', 'adc'): " + lcs("abc", "adc") + "\n");
        
        // 4. Edit Distance
        System.out.println("4. EDIT DISTANCE");
        System.out.println("Edit distance('horse', 'ros'): " + editDistance("horse", "ros") + "\n");
        
        // 5. Climbing Stairs
        System.out.println("5. CLIMBING STAIRS");
        System.out.println("Ways to climb 4 stairs: " + climbStairs(4) + "\n");
        
        // 6. Coin Change
        System.out.println("6. COIN CHANGE");
        int[] coins = {1, 2, 5};
        System.out.println("Min coins for amount 5: " + coinChange(coins, 5));
    }
}
