package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.AdvancedDS;

import java.util.*;

/**
 * SEGMENT TREE & FENWICK TREE - LEETCODE PROBLEMS
 * 
 * Problems covered:
 * 1. LeetCode 303: Range Sum Query - Immutable
 * 2. LeetCode 307: Range Sum Query - Mutable (Segment Tree)
 * 3. LeetCode 308: Range Sum Query 2D - Mutable
 * 4. LeetCode 493: Reverse Pairs
 */

public class SegmentTreeFenwickProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 303 - Range Sum Query (Immutable)
    // ========================================
    
    /**
     * LeetCode 303: Range Sum Query - Immutable
     * 
     * Given an integer array nums, implement a class NumArray that supports
     * efficient range sum queries.
     * 
     * Example:
     * NumArray numArray = new NumArray([-2, 0, 3, -5, 2, -1]);
     * numArray.sumRange(0, 2);  // return (-2) + 0 + 3 = 1
     * numArray.sumRange(2, 5);  // return 3 + (-5) + 2 + (-1) = -1
     * 
     * Time: Build O(n), Query O(1)
     * Space: O(n)
     */
    public static class NumArray {
        private int[] prefixSum;
        
        public NumArray(int[] nums) {
            prefixSum = new int[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }
        
        public int sumRange(int left, int right) {
            return prefixSum[right + 1] - prefixSum[left];
        }
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 307 - Range Sum Query (Mutable)
    // ========================================
    
    /**
     * LeetCode 307: Range Sum Query - Mutable
     * 
     * Implement a data structure that supports:
     * 1. Update value at index
     * 2. Sum of elements in range [left, right]
     * 
     * Example:
     * NumArrayMutable numArray = new NumArrayMutable([1,3,5]);
     * numArray.sumRange(0, 2); // return 1+3+5 = 9
     * numArray.update(1, 2);
     * numArray.sumRange(0, 2); // return 1+2+5 = 8
     * 
     * Time: Build O(n), Update O(log n), Query O(log n)
     * Space: O(n)
     */
    public static class NumArrayMutable {
        private int[] tree;
        private int[] nums;
        private int n;
        
        public NumArrayMutable(int[] nums) {
            this.nums = nums;
            this.n = nums.length;
            this.tree = new int[4 * n];
            if (n > 0) {
                build(0, 0, n - 1);
            }
        }
        
        private void build(int node, int start, int end) {
            if (start == end) {
                tree[node] = nums[start];
            } else {
                int mid = (start + end) / 2;
                build(2 * node + 1, start, mid);
                build(2 * node + 2, mid + 1, end);
                tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
            }
        }
        
        public void update(int idx, int val) {
            update(0, 0, n - 1, idx, val);
        }
        
        private void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                if (idx <= mid) {
                    update(2 * node + 1, start, mid, idx, val);
                } else {
                    update(2 * node + 2, mid + 1, end, idx, val);
                }
                tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
            }
        }
        
        public int sumRange(int left, int right) {
            return query(0, 0, n - 1, left, right);
        }
        
        private int query(int node, int start, int end, int left, int right) {
            if (right < start || end < left) {
                return 0;
            }
            if (left <= start && end <= right) {
                return tree[node];
            }
            int mid = (start + end) / 2;
            int p1 = query(2 * node + 1, start, mid, left, right);
            int p2 = query(2 * node + 2, mid + 1, end, left, right);
            return p1 + p2;
        }
    }
    
    // ========================================
    // FENWICK TREE (BINARY INDEXED TREE)
    // ========================================
    
    /**
     * Fenwick Tree for efficient prefix sum queries
     */
    public static class FenwickTree {
        private int[] tree;
        private int n;
        
        public FenwickTree(int[] arr) {
            n = arr.length;
            tree = new int[n + 1];
            for (int i = 0; i < n; i++) {
                update(i, arr[i]);
            }
        }
        
        public void update(int idx, int val) {
            idx++;
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & (-idx);
            }
        }
        
        public int prefixSum(int idx) {
            idx++;
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & (-idx);
            }
            return sum;
        }
        
        public int rangeSum(int left, int right) {
            if (left == 0) {
                return prefixSum(right);
            }
            return prefixSum(right) - prefixSum(left - 1);
        }
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 308 - Range Sum Query 2D (Mutable)
    // ========================================
    
    /**
     * LeetCode 308: Range Sum Query 2D - Mutable
     * 
     * Implement a 2D mutable data structure that supports:
     * 1. Update value at (row, col)
     * 2. Sum of elements in rectangle from (row1,col1) to (row2,col2)
     * 
     * Example:
     * matrix = [[3,0,1,4,2],[5,6,3,2,1],[1,2,0,1,5],[4,1,0,1,7],[1,0,3,0,5]]
     * NumMatrix nm = new NumMatrix(matrix);
     * nm.sumRegion(2, 1, 4, 3); // return 8
     * nm.update(3, 2, 2);
     * nm.sumRegion(2, 1, 4, 3); // return 10
     * 
     * Time: Build O(mn log m log n), Update O(log m log n), Query O(log m log n)
     * Space: O(mn)
     */
    public static class NumMatrix {
        private int[][] matrix;
        private int[][] tree;
        private int rows;
        private int cols;
        
        public NumMatrix(int[][] matrix) {
            this.matrix = matrix;
            this.rows = matrix.length;
            this.cols = matrix[0].length;
            this.tree = new int[rows + 1][cols + 1];
            
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    updateTree(i, j, matrix[i][j]);
                }
            }
        }
        
        public void update(int row, int col, int val) {
            int diff = val - matrix[row][col];
            matrix[row][col] = val;
            updateTree(row, col, diff);
        }
        
        private void updateTree(int row, int col, int delta) {
            for (int i = row + 1; i <= rows; i += i & (-i)) {
                for (int j = col + 1; j <= cols; j += j & (-j)) {
                    tree[i][j] += delta;
                }
            }
        }
        
        public int sumRegion(int row1, int col1, int row2, int col2) {
            return getSum(row2, col2) - getSum(row1 - 1, col2) 
                   - getSum(row2, col1 - 1) + getSum(row1 - 1, col1 - 1);
        }
        
        private int getSum(int row, int col) {
            if (row < 0 || col < 0) return 0;
            
            int sum = 0;
            for (int i = row + 1; i > 0; i -= i & (-i)) {
                for (int j = col + 1; j > 0; j -= j & (-j)) {
                    sum += tree[i][j];
                }
            }
            return sum;
        }
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 493 - Reverse Pairs
    // ========================================
    
    /**
     * LeetCode 493: Reverse Pairs
     * 
     * Given an integer array nums, return the number of reverse pairs in the array.
     * A reverse pair is a pair (i, j) where 0 <= i < j < n and nums[i] > 2*nums[j].
     * 
     * Example:
     * Input: nums = [1,3,2,3,1]
     * Output: 2
     * Explanation: Pairs are (1,4) and (2,4) where values are (3,1) and (2,1)
     * 3 > 2*1 and 2 > 2*1
     * 
     * Time: O(n log n) using merge sort
     * Space: O(n)
     */
    public static int reversePairs(int[] nums) {
        return mergeSort(nums, 0, nums.length - 1);
    }
    
    private static int mergeSort(int[] nums, int left, int right) {
        if (left >= right) return 0;
        
        int mid = left + (right - left) / 2;
        int count = mergeSort(nums, left, mid) + mergeSort(nums, mid + 1, right);
        count += merge(nums, left, mid, right);
        
        return count;
    }
    
    private static int merge(int[] nums, int left, int mid, int right) {
        int count = 0;
        int j = mid + 1;
        
        // Count reverse pairs
        for (int i = left; i <= mid; i++) {
            while (j <= right && (long)nums[i] > 2L * nums[j]) {
                j++;
            }
            count += j - (mid + 1);
        }
        
        // Standard merge
        int[] temp = new int[right - left + 1];
        int i = left, k = mid + 1, idx = 0;
        
        while (i <= mid && k <= right) {
            if (nums[i] <= nums[k]) {
                temp[idx++] = nums[i++];
            } else {
                temp[idx++] = nums[k++];
            }
        }
        
        while (i <= mid) temp[idx++] = nums[i++];
        while (k <= right) temp[idx++] = nums[k++];
        
        System.arraycopy(temp, 0, nums, left, temp.length);
        
        return count;
    }
    
    // ========================================
    // MAIN - Test Segment Tree & Fenwick Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== SEGMENT TREE & FENWICK TREE PROBLEMS ===\n");
        
        // Problem 1: Range Sum Query (Immutable)
        System.out.println("1. LeetCode 303: Range Sum Query (Immutable)");
        int[] nums = {-2, 0, 3, -5, 2, -1};
        NumArray numArray = new NumArray(nums);
        System.out.println("sumRange(0, 2): " + numArray.sumRange(0, 2));
        System.out.println("sumRange(2, 5): " + numArray.sumRange(2, 5) + "\n");
        
        // Problem 2: Range Sum Query (Mutable)
        System.out.println("2. LeetCode 307: Range Sum Query (Mutable)");
        int[] nums2 = {1, 3, 5};
        NumArrayMutable numArray2 = new NumArrayMutable(nums2);
        System.out.println("sumRange(0, 2): " + numArray2.sumRange(0, 2));
        numArray2.update(1, 2);
        System.out.println("After update(1, 2), sumRange(0, 2): " + numArray2.sumRange(0, 2) + "\n");
        
        // Problem 4: Reverse Pairs
        System.out.println("4. LeetCode 493: Reverse Pairs");
        int[] nums3 = {1, 3, 2, 3, 1};
        System.out.println("nums: " + Arrays.toString(nums3));
        System.out.println("reversePairs: " + reversePairs(nums3));
    }
}
