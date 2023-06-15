package com.swaroop.dsa.harshasir.Sorting3;

/*

Q1. QuickSort

Problem Description

        Given an integer array A, sort the array using QuickSort.



        Problem Constraints

        1 <= |A| <= 105

        1 <= A[i] <= 109



        Input Format

        First argument is an integer array A.



        Output Format

        Return the sorted array.



        Example Input

        Input 1:

        A = [1, 4, 10, 2, 1, 5]
        Input 2:

        A = [3, 7, 1]


        Example Output

        Output 1:

        [1, 1, 2, 4, 5, 10]
        Output 2:

        [1, 3, 7]

*/
public class QuickSort {

    public int[] solve(int[] A) {
        
        quickSort(A,0, A.length-1);

        return A;
    }

    private void quickSort(int[] a, int low, int high) {

        if(low>=high){
            return ;
        }

        int pi=partition(a, low, high);
        quickSort(a,low,pi-1);
        quickSort(a,pi+1,high);

    }


    public int partition(int[] A, int low, int high){

        int pivot = A[high];
        int i=low;
        int j=low;

        while(j<high){

            if(A[j]>=pivot){
                j++;
            }else{

                //swap A[i], A[j}
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;


                i++;
                j++;

            }
        }

        //swap A[i], A[high]

        int temp = A[i];
        A[i]=A[high];
        A[high]=temp;

        return i;

    }


}
