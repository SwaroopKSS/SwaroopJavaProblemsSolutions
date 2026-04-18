package com.swaroop.DataStructuresCheatCode;

/**
 * INTERMEDIATE DYNAMIC PROGRAMMING - ADVANCED PATTERNS
 * 
 * 💡 ADVANCED DP CONCEPTS:
 * 1. Matrix Chain Multiplication
 * 2. Longest Increasing Subsequence (LIS)
 * 3. Longest Common Subsequence (LCS)
 * 4. Palindrome DP
 * 5. Game Theory DP
 * 6. Tree DP
 * 7. Digit DP
 */

public class IntermediateDynamicProgramming {
    
    // ========================================
    // 1. LONGEST INCREASING SUBSEQUENCE (LIS)
    // ========================================
    
    /**
     * LIS - Find length of longest increasing subsequence
     * 
     * DP Approach: O(n²)
     * dp[i] = LIS length ending at index i
     */
    public static int lisDP(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        java.util.Arrays.fill(dp, 1);
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        
        return java.util.Arrays.stream(dp).max().orElse(1);
    }
    
    /**
     * LIS - Optimal O(n log n) approach with binary search
     * 
     * Trick: Maintain smallest tail of each increasing subsequence length
     * Use binary search to find insertion position
     */
    public static int lisOptimal(int[] arr) {
        java.util.List<Integer> tails = new java.util.ArrayList<>();
        
        for (int num : arr) {
            int pos = java.util.Collections.binarySearch(tails, num);
            
            if (pos < 0) {
                pos = -(pos + 1);  // Insertion position
            }
            
            if (pos == tails.size()) {
                tails.add(num);
            } else {
                tails.set(pos, num);
            }
        }
        
        return tails.size();
    }
    
    /**
     * Find actual LIS (not just length)
     */
    public static java.util.List<Integer> findLIS(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        int[] parent = new int[n];
        java.util.Arrays.fill(dp, 1);
        java.util.Arrays.fill(parent, -1);
        
        int maxLen = 1, maxIdx = 0;
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
            
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                maxIdx = i;
            }
        }
        
        java.util.List<Integer> lis = new java.util.ArrayList<>();
        for (int i = maxIdx; i != -1; i = parent[i]) {
            lis.add(0, arr[i]);
        }
        
        return lis;
    }
    
    // ========================================
    // 2. MATRIX CHAIN MULTIPLICATION
    // ========================================
    
    /**
     * Matrix Chain Multiplication - Min operations to multiply matrices
     * 
     * dp[i][j] = min multiplications to compute matrices[i..j]
     * 
     * Time: O(n³)
     * Space: O(n²)
     */
    public static int matrixChainMultiply(int[] dims) {
        // dims[i-1] x dims[i] is dimension of matrix i
        int n = dims.length - 1;  // Number of matrices
        int[][] dp = new int[n][n];
        
        // len = number of matrices in chain
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                dp[i][j] = Integer.MAX_VALUE;
                
                // Try all split points
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k+1][j] + 
                              dims[i] * dims[k+1] * dims[j+1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }
        
        return dp[0][n-1];
    }
    
    // ========================================
    // 3. PALINDROME PROBLEMS
    // ========================================
    
    /**
     * Minimum cuts to make all palindromes
     * 
     * dp[i] = min cuts needed for s[0..i]
     * Time: O(n²)
     */
    public static int minCutPalindrome(String s) {
        int n = s.length();
        int[] dp = new int[n];
        boolean[][] isPalin = new boolean[n][n];
        
        // Check all palindromes
        for (int i = 0; i < n; i++) {
            isPalin[i][i] = true;
            if (i > 0 && s.charAt(i) == s.charAt(i-1)) {
                isPalin[i-1][i] = true;
            }
        }
        
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                isPalin[i][j] = s.charAt(i) == s.charAt(j) && isPalin[i+1][j-1];
            }
        }
        
        // DP for minimum cuts
        for (int i = 0; i < n; i++) {
            if (isPalin[0][i]) {
                dp[i] = 0;
            } else {
                dp[i] = i;
                for (int j = 1; j <= i; j++) {
                    if (isPalin[j][i]) {
                        dp[i] = Math.min(dp[i], dp[j-1] + 1);
                    }
                }
            }
        }
        
        return dp[n-1];
    }
    
    /**
     * Longest palindromic subsequence
     */
    public static int longestPalindromeSubseq(String s) {
        // LPS = LCS(s, reverse(s))
        String rev = new StringBuilder(s).reverse().toString();
        return lcs(s, rev);
    }
    
    // ========================================
    // 4. LONGEST COMMON SUBSEQUENCE (LCS)
    // ========================================
    
    /**
     * LCS - Length of longest common subsequence
     * 
     * dp[i][j] = LCS length of s1[0..i-1] and s2[0..j-1]
     * Time: O(m * n)
     */
    public static int lcs(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        
        return dp[m][n];
    }
    
    /**
     * Find actual LCS string
     */
    public static String findLCS(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m+1][n+1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        
        // Reconstruct LCS
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                lcs.insert(0, s1.charAt(i-1));
                i--;
                j--;
            } else if (dp[i-1][j] > dp[i][j-1]) {
                i--;
            } else {
                j--;
            }
        }
        
        return lcs.toString();
    }
    
    // ========================================
    // 5. GAME THEORY DP
    // ========================================
    
    /**
     * Predict the Winner - Game where players take turns
     * Optimal play assumption
     * 
     * dp[i][j] = max score current player can get from nums[i..j]
     * Time: O(n²)
     */
    public static boolean predictWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        
        // Base case: single number
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        
        // Fill for increasing lengths
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                
                // Current player picks i or j
                // If picks i: gets nums[i] + (opponent's best from rest)
                // Opponent will try to minimize what we get
                int pickI = nums[i] + Math.min(
                    i+2 <= j ? dp[i+2][j] : 0,
                    i+1 <= j ? dp[i+1][j-1] : 0
                );
                
                int pickJ = nums[j] + Math.min(
                    i <= j-2 ? dp[i][j-2] : 0,
                    i+1 <= j-1 ? dp[i+1][j-1] : 0
                );
                
                dp[i][j] = Math.max(pickI, pickJ);
            }
        }
        
        // Check if player 1 gets more than player 2
        int total = java.util.Arrays.stream(nums).sum();
        return dp[0][n-1] >= total - dp[0][n-1];
    }
    
    // ========================================
    // 6. CLIMBING STAIRS VARIATIONS
    // ========================================
    
    /**
     * Climbing stairs with k steps - vary step size
     * At each step, can climb 1 to k steps
     * 
     * dp[i] = ways to reach step i
     * Time: O(n * k)
     */
    public static int climbStairsK(int n, int k) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                dp[i] += dp[i - j];
            }
        }
        
        return dp[n];
    }
    
    // ========================================
    // MAIN - Test Intermediate DP
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== INTERMEDIATE DYNAMIC PROGRAMMING ===\n");
        
        // 1. LIS
        System.out.println("1. LONGEST INCREASING SUBSEQUENCE");
        int[] arr = {10, 9, 2, 5, 3, 7, 101, 18};
        System.out.println("LIS length (O(n²)): " + lisDP(arr));
        System.out.println("LIS length (O(n log n)): " + lisOptimal(arr));
        System.out.println("Actual LIS: " + findLIS(arr) + "\n");
        
        // 2. Matrix Chain
        System.out.println("2. MATRIX CHAIN MULTIPLICATION");
        int[] dims = {10, 20, 30, 40, 30};
        System.out.println("Min multiplications: " + matrixChainMultiply(dims) + "\n");
        
        // 3. Palindrome Cut
        System.out.println("3. PALINDROME MIN CUT");
        System.out.println("Min cuts for 'nitin': " + minCutPalindrome("nitin") + "\n");
        
        // 4. LCS
        System.out.println("4. LONGEST COMMON SUBSEQUENCE");
        System.out.println("LCS('AGGTAB', 'GXTXAYB'): " + 
                          lcs("AGGTAB", "GXTXAYB"));
        System.out.println("LCS string: " + findLCS("AGGTAB", "GXTXAYB") + "\n");
        
        // 5. Game Theory
        System.out.println("5. PREDICT THE WINNER");
        System.out.println("Winner from [1,5,233,7]: " + 
                          predictWinner(new int[]{1, 5, 233, 7}) + "\n");
        
        // 6. Climbing Stairs
        System.out.println("6. CLIMBING STAIRS (k steps)");
        System.out.println("Ways to climb 5 stairs (max 2 steps): " + 
                          climbStairsK(5, 2));
    }
}
