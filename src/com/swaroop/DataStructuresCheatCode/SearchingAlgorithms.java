package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * SEARCHING ALGORITHMS - FINDING ELEMENTS
 * 
 * 💡 CHEAT MEMORY:
 * Linear Search: O(n) - Any array
 * Binary Search: O(log n) - ONLY sorted array
 * Two Pointers: O(n) - Converging pointers
 */

public class SearchingAlgorithms {
    
    // ========================================
    // 1. LINEAR SEARCH
    // ========================================
    
    /**
     * Linear Search - Scan from start to end
     * Time: O(n), Space: O(1)
     * Use when: Array is unsorted
     */
    public static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    // ========================================
    // 2. BINARY SEARCH
    // ========================================
    
    /**
     * Binary Search (Iterative)
     * Time: O(log n), Space: O(1)
     * REQUIREMENT: Array must be SORTED
     * 
     * Example: [1,3,5,7,9], target=5
     * Output: 2
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Binary Search (Recursive)
     */
    public static int binarySearchRecursive(int[] arr, int target, int left, int right) {
        if (left > right) return -1;
        
        int mid = left + (right - left) / 2;
        
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, right);
        } else {
            return binarySearchRecursive(arr, target, left, mid - 1);
        }
    }
    
    // ========================================
    // 3. FIND FIRST & LAST POSITION
    // ========================================
    
    /**
     * Find First and Last Position of Element in Sorted Array
     * 
     * Example: [5,7,7,8,8,10], target=8
     * Output: [3,4]
     */
    public static int[] searchRange(int[] arr, int target) {
        int[] result = {-1, -1};
        
        // Find first position
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result[0] = mid;
                right = mid - 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // Find last position
        left = 0;
        right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                result[1] = mid;
                left = mid + 1;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    // ========================================
    // 4. SEARCH ROTATED SORTED ARRAY
    // ========================================
    
    /**
     * Search in Rotated Sorted Array
     * 
     * Example: [4,5,6,7,0,1,2], target=0
     * Output: 4
     */
    public static int searchRotated(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            // Determine which half is sorted
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    // ========================================
    // 5. FIND PEAK ELEMENT
    // ========================================
    
    /**
     * Find Peak Element (greater than neighbors)
     * 
     * Example: [1,2,1,3,5,6,4]
     * Output: 1 or 5 (both are peaks)
     */
    public static int findPeakElement(int[] arr) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== SEARCHING ALGORITHMS ===\n");
        
        // 1. Linear Search
        System.out.println("1. LINEAR SEARCH");
        int[] arr1 = {4, 2, 7, 1, 9, 3};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Search 7: Index " + linearSearch(arr1, 7) + "\n");
        
        // 2. Binary Search
        System.out.println("2. BINARY SEARCH (SORTED ARRAY REQUIRED)");
        int[] arr2 = {1, 3, 5, 7, 9, 11};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Search 7: Index " + binarySearch(arr2, 7) + "\n");
        
        // 3. Search Range
        System.out.println("3. FIND FIRST & LAST POSITION");
        int[] arr3 = {5, 7, 7, 8, 8, 10};
        int[] range = searchRange(arr3, 8);
        System.out.println("Array: " + Arrays.toString(arr3) + ", target=8");
        System.out.println("Range: " + Arrays.toString(range) + "\n");
        
        // 4. Search Rotated
        System.out.println("4. SEARCH ROTATED SORTED ARRAY");
        int[] arr4 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Search 0: Index " + searchRotated(arr4, 0) + "\n");
        
        // 5. Find Peak
        System.out.println("5. FIND PEAK ELEMENT");
        int[] arr5 = {1, 2, 1, 3, 5, 6, 4};
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("Peak index: " + findPeakElement(arr5));
    }
}
