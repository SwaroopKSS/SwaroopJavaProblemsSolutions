package com.swaroop.dsa.harshasir.Sorting1;

public class MergeSort {

    public int[] solve(int[] A) {

        int low = 0;
        int high = A.length-1;

        return mergeSort(A, low, high);

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
