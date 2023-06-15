package com.swaroop.dsa.harshasir.Sorting2;

/*

Q1. Inversion count in an array

Problem Description
        Given an array of integers A. If i < j and A[i] > A[j], then the pair (i, j) is called an inversion of A. Find the total number of inversions of A modulo (109 + 7).



        Problem Constraints
        1 <= length of the array <= 105

        1 <= A[i] <= 109



        Input Format
        The only argument given is the integer array A.



        Output Format
        Return the number of inversions of A modulo (109 + 7).



        Example Input
        Input 1:

        A = [1, 3, 2]
        Input 2:

        A = [3, 4, 1, 2]


        Example Output
        Output 1:

        1
        Output 2:

        4

*/
public class InversionCountInArray {

    int count = 0;
    int mod = 1000000007;
    public int solve(int[] A) {

        mergeSort(A, 0, A.length-1);

        return count%mod;

    }

    public int[] mergeSort(int[] A, int low, int high){


        if(low==high){
            int[] ans = new int[1];
            ans[0]=A[low];
            return ans;
        } //base case section

        int mid = (low+high)/2;

        int[] left = mergeSort(A,low, mid);
        int[] right = mergeSort(A,mid+1,high);

        int[] ans = merge(left, right);

        return ans;

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

                count = count + (m-i);
                count=count%mod;

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
