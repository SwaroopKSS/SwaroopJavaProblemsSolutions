package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * BACKTRACKING - EXHAUSTIVE SEARCH TECHNIQUE
 * 
 * 💡 PATTERN: "Build Path → Explore → Undo"
 * 
 * USE FOR:
 * - Permutations & Combinations
 * - Subsets & Power Sets
 * - Sudoku, N-Queens
 * - Word Search, Letter Combinations
 * - Partition problems
 */

public class BacktrackingProblems {
    
    // ========================================
    // 1. PERMUTATIONS
    // ========================================
    
    /**
     * Generate all permutations of array
     * Example: [1,2,3] → [[1,2,3], [1,3,2], [2,1,3], ...]
     * 
     * Time: O(n! * n), Space: O(n)
     */
    public static List<List<Integer>> permutations(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackPermute(nums, 0, result);
        return result;
    }
    
    private static void backtrackPermute(int[] nums, int start, List<List<Integer>> result) {
        if (start == nums.length - 1) {
            List<Integer> perm = new ArrayList<>();
            for (int num : nums) {
                perm.add(num);
            }
            result.add(perm);
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            // Swap
            int temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
            
            // Explore
            backtrackPermute(nums, start + 1, result);
            
            // Undo (backtrack)
            temp = nums[start];
            nums[start] = nums[i];
            nums[i] = temp;
        }
    }
    
    // ========================================
    // 2. COMBINATIONS
    // ========================================
    
    /**
     * Generate all combinations of k elements from n
     * Example: n=4, k=2 → [[1,2], [1,3], [1,4], [2,3], [2,4], [3,4]]
     * 
     * Time: O(C(n,k) * k), Space: O(k)
     */
    public static List<List<Integer>> combinations(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombine(1, n, k, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackCombine(int start, int n, int k, 
                                        List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i <= n; i++) {
            // Choose
            current.add(i);
            
            // Explore
            backtrackCombine(i + 1, n, k, current, result);
            
            // Undo
            current.remove(current.size() - 1);
        }
    }
    
    // ========================================
    // 3. SUBSETS (POWER SET)
    // ========================================
    
    /**
     * Generate all subsets
     * Example: [1,2,3] → [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
     * 
     * Time: O(n * 2^n), Space: O(n)
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private static void backtrackSubsets(int[] nums, int start, 
                                        List<Integer> current, List<List<Integer>> result) {
        result.add(new ArrayList<>(current));
        
        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrackSubsets(nums, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
    
    // ========================================
    // 4. N-QUEENS PROBLEM
    // ========================================
    
    /**
     * Solve N-Queens problem
     * Place n queens on n×n chessboard
     * No two queens can attack each other
     * 
     * Time: O(N!), Space: O(N)
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        
        // Initialize board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        backtrackQueens(board, 0, result);
        return result;
    }
    
    private static void backtrackQueens(char[][] board, int row, List<List<String>> result) {
        if (row == board.length) {
            List<String> solution = new ArrayList<>();
            for (char[] r : board) {
                solution.add(new String(r));
            }
            result.add(solution);
            return;
        }
        
        for (int col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = 'Q';
                backtrackQueens(board, row + 1, result);
                board[row][col] = '.';
            }
        }
    }
    
    private static boolean isSafe(char[][] board, int row, int col) {
        // Check column
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }
        
        // Check diagonal up-left
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }
        
        // Check diagonal up-right
        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }
        
        return true;
    }
    
    // ========================================
    // 5. LETTER COMBINATIONS
    // ========================================
    
    /**
     * Letter Combinations of Phone Number
     * Example: "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
     */
    public static List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) return result;
        
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrackLetters(digits, 0, "", mapping, result);
        return result;
    }
    
    private static void backtrackLetters(String digits, int idx, String current, 
                                        String[] mapping, List<String> result) {
        if (idx == digits.length()) {
            result.add(current);
            return;
        }
        
        String letters = mapping[digits.charAt(idx) - '0'];
        for (char c : letters.toCharArray()) {
            backtrackLetters(digits, idx + 1, current + c, mapping, result);
        }
    }
    
    // ========================================
    // 6. WORD SEARCH
    // ========================================
    
    /**
     * Word Search in 2D Grid
     * Find if word exists in grid
     * Can move up, down, left, right
     */
    public static boolean wordSearch(char[][] board, String word) {
        if (board == null || board.length == 0) return false;
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (backtrackSearch(board, word, 0, i, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean backtrackSearch(char[][] board, String word, int idx, int i, int j) {
        if (idx == word.length()) {
            return true;
        }
        
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return false;
        }
        
        if (board[i][j] != word.charAt(idx)) {
            return false;
        }
        
        char temp = board[i][j];
        board[i][j] = '*';  // Mark visited
        
        boolean found = backtrackSearch(board, word, idx + 1, i + 1, j) ||
                       backtrackSearch(board, word, idx + 1, i - 1, j) ||
                       backtrackSearch(board, word, idx + 1, i, j + 1) ||
                       backtrackSearch(board, word, idx + 1, i, j - 1);
        
        board[i][j] = temp;  // Restore
        return found;
    }
    
    // ========================================
    // MAIN - Test Backtracking
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== BACKTRACKING ===\n");
        
        // 1. Permutations
        System.out.println("1. PERMUTATIONS");
        List<List<Integer>> perms = permutations(new int[]{1, 2, 3});
        System.out.println("Count: " + perms.size() + " (should be 6)\n");
        
        // 2. Combinations
        System.out.println("2. COMBINATIONS (4 choose 2)");
        List<List<Integer>> combs = combinations(4, 2);
        System.out.println("Count: " + combs.size() + " (should be 6)");
        System.out.println("First: " + combs.get(0) + "\n");
        
        // 3. Subsets
        System.out.println("3. SUBSETS");
        List<List<Integer>> subs = subsets(new int[]{1, 2, 3});
        System.out.println("Count: " + subs.size() + " (should be 8)\n");
        
        // 4. N-Queens
        System.out.println("4. N-QUEENS (n=4)");
        List<List<String>> queens = solveNQueens(4);
        System.out.println("Solutions: " + queens.size() + "\n");
        
        // 5. Letter Combinations
        System.out.println("5. LETTER COMBINATIONS (23)");
        List<String> letters = letterCombinations("23");
        System.out.println("Result: " + letters + "\n");
        
        // 6. Word Search
        System.out.println("6. WORD SEARCH");
        char[][] board = {
            {'A', 'B', 'C'},
            {'S', 'F', 'C'},
            {'A', 'D', 'E'}
        };
        System.out.println("'ASFD' found: " + wordSearch(board, "ASFD"));
        System.out.println("'ABCB' found: " + wordSearch(board, "ABCB"));
    }
}
