package com.swaroop.dsa.sorting;

import java.util.*;

/**
 * SORTING ALGORITHMS - ARRANGEMENT
 * 
 * 💡 CHEAT MEMORY:
 * O(n²): Bubble, Selection, Insertion (Simple, slow)
 * O(n log n): Merge, Quick, Heap (Fast, better)
 * O(n+k): Counting, Radix (Non-comparative)
 * 
 * Stable: Merge, Bubble, Insertion (relative order preserved)
 * In-place: Quick, Heap, Selection (O(1) extra space)
 */

public class SortingAlgorithms {
    
    // ========================================
    // O(n²) ALGORITHMS
    // ========================================
    
    /**
     * Bubble Sort - Compare adjacent elements
     * Time: O(n²), Space: O(1), Stable: Yes
     * 
     * LOGIC:
     * - Compare adjacent pairs
     * - Swap if wrong order
     * - Repeat until sorted
     */
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;  // Optimization: already sorted
        }
    }
    
    /**
     * Selection Sort - Find minimum and place at beginning
     * Time: O(n²), Space: O(1), Stable: No
     * 
     * LOGIC:
     * - Find minimum in unsorted portion
     * - Swap with first position of unsorted
     * - Move boundary
     */
    public static void selectionSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            swap(arr, i, minIdx);
        }
    }
    
    /**
     * Insertion Sort - Build sorted array one element at a time
     * Time: O(n²), Space: O(1), Stable: Yes
     * Best for: Small arrays, nearly sorted data
     * 
     * LOGIC:
     * - Take element from unsorted portion
     * - Insert into correct position in sorted portion
     * - Shift elements as needed
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Shift elements greater than key
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
    
    // ========================================
    // O(n log n) ALGORITHMS
    // ========================================
    
    /**
     * Merge Sort - Divide & Conquer
     * Time: O(n log n), Space: O(n), Stable: Yes
     * Best for: Linked lists, guaranteed O(n log n)
     */
    public static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        mergeSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(arr, left, mid);           // Sort left half
            mergeSortHelper(arr, mid + 1, right);      // Sort right half
            merge(arr, left, mid, right);              // Merge
        }
    }
    
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        
        // Merge two sorted halves
        while (i <= mid && j <= right) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }
        
        // Copy remaining elements
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        
        // Copy back to original array
        for (i = 0; i < temp.length; i++) {
            arr[left + i] = temp[i];
        }
    }
    
    /**
     * Quick Sort - Partition & Conquer
     * Time: O(n log n) avg, O(n²) worst, Space: O(log n), Stable: No
     * Best for: General purpose, average case very fast
     */
    public static void quickSort(int[] arr) {
        if (arr.length == 0) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }
    
    private static void quickSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int pi = partition(arr, left, right);
            quickSortHelper(arr, left, pi - 1);    // Sort left of pivot
            quickSortHelper(arr, pi + 1, right);   // Sort right of pivot
        }
    }
    
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        
        for (int j = left; j < right; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }
    
    /**
     * Heap Sort - Build heap and extract elements
     * Time: O(n log n), Space: O(1), Stable: No
     * Best for: Guaranteed O(n log n), in-place
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            swap(arr, 0, i);      // Move root to end
            heapify(arr, i, 0);   // Heapify reduced heap
        }
    }
    
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }
    
    // ========================================
    // NON-COMPARATIVE ALGORITHMS
    // ========================================
    
    /**
     * Counting Sort - Works for integers in known range
     * Time: O(n+k), Space: O(k) where k = max value
     */
    public static void countingSort(int[] arr) {
        if (arr.length == 0) return;
        
        int max = Arrays.stream(arr).max().orElse(0);
        int[] count = new int[max + 1];
        
        // Count occurrences
        for (int num : arr) {
            count[num]++;
        }
        
        // Reconstruct array
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }
    
    // ========================================
    // HELPER FUNCTIONS
    // ========================================
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }
    
    // ========================================
    // MAIN - Test all sorting algorithms
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== SORTING ALGORITHMS ===\n");
        
        int[] data = {64, 34, 25, 12, 22, 11, 90};
        
        // Bubble Sort
        System.out.println("1. BUBBLE SORT - O(n²)");
        int[] arr1 = data.clone();
        System.out.print("Before: ");
        printArray(arr1);
        bubbleSort(arr1);
        System.out.print("After: ");
        printArray(arr1);
        System.out.println();
        
        // Selection Sort
        System.out.println("2. SELECTION SORT - O(n²)");
        int[] arr2 = data.clone();
        selectionSort(arr2);
        System.out.print("Result: ");
        printArray(arr2);
        System.out.println();
        
        // Insertion Sort
        System.out.println("3. INSERTION SORT - O(n²)");
        int[] arr3 = data.clone();
        insertionSort(arr3);
        System.out.print("Result: ");
        printArray(arr3);
        System.out.println();
        
        // Merge Sort
        System.out.println("4. MERGE SORT - O(n log n) - Stable");
        int[] arr4 = data.clone();
        mergeSort(arr4);
        System.out.print("Result: ");
        printArray(arr4);
        System.out.println();
        
        // Quick Sort
        System.out.println("5. QUICK SORT - O(n log n) avg - In-place");
        int[] arr5 = data.clone();
        quickSort(arr5);
        System.out.print("Result: ");
        printArray(arr5);
        System.out.println();
        
        // Heap Sort
        System.out.println("6. HEAP SORT - O(n log n) - In-place");
        int[] arr6 = data.clone();
        heapSort(arr6);
        System.out.print("Result: ");
        printArray(arr6);
        System.out.println();
        
        // Counting Sort
        System.out.println("7. COUNTING SORT - O(n+k) - Non-comparative");
        int[] arr7 = {4, 2, 3, 1, 2, 3, 5, 1};
        System.out.print("Before: ");
        printArray(arr7);
        countingSort(arr7);
        System.out.print("After: ");
        printArray(arr7);
    }
}
