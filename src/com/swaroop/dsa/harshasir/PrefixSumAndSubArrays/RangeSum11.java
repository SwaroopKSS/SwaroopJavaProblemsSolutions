package com.swaroop.dsa.harshasir.PrefixSumAndSubArrays;

/*

Q1. Range Sum - II
        Unsolved
        character backgroundcharacter
        Stuck somewhere?
        Ask for help from a TA and get it resolved.
        Get help from TA.
        Problem Description
        Given an integer array A of size N. You are asked to perfom Q queries on the given array and return the final array after processing all queries.

        Each query is of type ( l, r, c), for each query add c in index range l to r.

        NOTE: Value of each element in the final array will fit in 32 bit signed integer.



        Problem Constraints
        1 <= N, Q <= 105

        -106 <= A[i], c <= 106

        1 <= l <= r <= N



        Input Format
        First argument is an integer array A of size N.

        Second argument is a 2d array B of size Q x 3, where B[i][0] = l, B[i][1] = r and B[i][2] = c.



        Output Format
        Return the final array after processing all queries.

*/

public class RangeSum11 {

    public static void main(String[] args){

        int[] A = {1, 2, 1, 4};
        int[][] B = {
                {2, 3, 2},
                {1, 4, 5},
                {4, 4, 1}
        };

        System.out.println(solve(A, B));

    }



    public static int[] solve(int[] A, int[][] B) {

       // int[] res = new int[A.length];

        for(int i=0;i<B.length;i++){

            int l = B[i][0];
            int r = B[i][1];
            int c = B[i][2];

            for(int j = (l-1);j<=(r-1);j++){

                A[j] = A[j] + c;

            }

        }

        return A;
    }

}
