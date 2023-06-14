package com.swaroop.dsa.harshasir.Sorting1;

import java.util.ArrayList;

/*

Bubble Sort


Problem Description
        Implement Bubble Sort in the article below.
        Given an integer array A of size N, sort the array using bubble sort algorithm.

        Return the array after sorting.


        Problem Constraints
        1 <= N <= 103
        1 <= Ai <= 109


        Input Format
        First argument is the integer array A.


        Output Format
        Return the array A after sorting.


        Example Input
        Input 1:
        A = [1, 4, 3, 2]
        Input 2:

        A = [4, 2, 1, 4]


        Example Output
        Output 1:
        [1, 2, 3, 4]
        Output 2:

        [1, 2, 4, 4]

*/
public class BubbleSort {

    public ArrayList<Integer> bubbleSort(ArrayList<Integer> A) {
          int n = A.size();
        for(int i=0;i<n-1;i++){
            for(int j=0;j<n-1-i;j++){
               if(A.get(j)>A.get(j+1)){
                   int temp = A.get(j);
                   A.set(j,A.get(j+1));
                   A.set(j+1,temp);
               }
            }
        }

        return A;

    }

}
