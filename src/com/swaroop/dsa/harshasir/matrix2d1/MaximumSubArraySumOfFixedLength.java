package com.swaroop.dsa.harshasir.matrix2d1;


/*

Q2. Maximum subarray sum of fixed length

Problem Description
        Given an array A of length N, Find the maximum subarray sum out of all possible subarray of length B.


        Problem Constraints
        1 <= N <= 105
        1 <= A[i] <= 106


        Input Format
        First argument A is an array of integers.
        Second argument B is length of subarray.


        Output Format
        Return a single integer i.e. max subarray sum


        Example Input
        Input 1:

        A = [6, 7, 8, 2]
        B = 2
        Input 2:

        A = [3, 9, 5, 6, 5, 11]
        B = 3


        Example Output
        Output 1:

        15
        Output 2:

        22


        Example Explanation
        Explanation 1:

        Subarray with maximum sum is [7, 8] with sum 15.
        Explanation 2:

        Subarray with maximum sum is [6, 5, 11] with sum 22.

*/
public class MaximumSubArraySumOfFixedLength {

    public long solve(int[] A, int B) {


        //B is lenth of subarray

        int s = 0;
        int e = B-1;

        long sum = 0;

        long maxSum = Long.MIN_VALUE;

        for(int i=0;i<=e;i++){
            sum = sum + A[i];
        }

        s++; e++;

        maxSum = Math.max(maxSum,sum);


        while(e<A.length){
            sum = sum - A[s-1] + A[e];

            maxSum = Math.max(maxSum,sum);

            s++;e++;

        }
        return maxSum;

    }

}
