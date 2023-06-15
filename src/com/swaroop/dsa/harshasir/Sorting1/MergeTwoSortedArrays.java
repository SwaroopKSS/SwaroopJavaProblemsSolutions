package com.swaroop.dsa.harshasir.Sorting1;

import java.util.ArrayList;
import java.util.List;

/*

Merge Two Sorted Arrays

Problem Description
Given two sorted integer arrays A and B, merge B and A as one sorted array and return it as an output.


Problem Constraints
-1010 <= A[i], B[i] <= 1010


Input Format
First Argument is a 1-D array representing  A.
Second Argument is also a 1-D array representing B.


Output Format
Return a 1-D vector which you got after merging A and B.


Example Input
Input 1:

A = [4, 7, 9]
B = [2, 11, 19]
Input 2:

A = [1]
B = [2]


Example Output
Output 1:

[2, 4, 7, 9, 11, 19]
Output 2:

[1, 2]
*/
public class MergeTwoSortedArrays {

    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public int[] solve(final int[] A, final int[] B) {

        return merge(A,B);
    }

    private int[] merge(int[] a, int[] b) {

        int m = a.length;
        int n = b.length;

        int[] result = new int[m+n];

        int i=0,j=0,k=0;

        while(i<m && j<n){

            if(a[i]<=b[j]){
                result[k]=a[i];
                i++;
            }
            else{
                result[k]=b[j];
                j++;
            }
            k++;

        }

        while(j<n){
            result[k]=b[j];
            j++;k++;
        }

        while(i<m){
            result[k]=a[i];
            i++;k++;
        }

        return result;
    }
}
