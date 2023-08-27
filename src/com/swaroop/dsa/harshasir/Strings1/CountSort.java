package com.swaroop.dsa.harshasir.Strings1;

/*

Problem Description
        Given an array A. Sort this array using Count Sort Algorithm and return the sorted array.


        Problem Constraints
        1 <= |A| <= 105
        1 <= A[i] <= 105


        Input Format
        The first argument is an integer array A.


        Output Format
        Return an integer array that is the sorted array A.


        Example Input
        Input 1:
        A = [1, 3, 1]
        Input 2:
        A = [4, 2, 1, 3]


        Example Output
        Output 1:
        [1, 1, 3]
        Output 2:
        [1, 2, 3, 4]

*/

import java.util.Arrays;

/*

Hint

We can count the frequency of each of the elements of the array.
        Make sure to make array of proper size, else you'll get either MLE or out of bounds error.

*/
public class CountSort {

    public int[] solve(int[] A) {
        int n = A.length;

        // Find the maximum element in the array
        int max = Arrays.stream(A).max().getAsInt();

        // Create a count array to store the frequency of each element
        int[] count = new int[max + 1];
        for (int num : A) {
            count[num]++;
        }

        // Modify the count array to store cumulative counts
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Create the sorted array using the count array
        int[] sortedArray = new int[n];
        for (int i = 0; i < n; i++) {
            sortedArray[count[A[i]] - 1] = A[i];
            count[A[i]]--;
        }

        return sortedArray;

    }
}
