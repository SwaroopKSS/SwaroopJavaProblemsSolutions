package com.swaroop.dsa.harshasir.Hashing2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/*

Longest Subarray Zero Sum

Problem Description
        Given an array A of N integers.
        Find the length of the longest subarray in the array which sums to zero.

        Note :
        while we A[i] multiple times, it may cross the range of integer, so wisely select data type for any operations.



        Problem Constraints
        1 <= N <= 105
        -109 <= A[i] <= 109


        Input Format
        Single argument which is an integer array A.


        Output Format
        Return an integer.


        Example Input
        Input 1:

        A = [1, -2, 1, 2]
        Input 2:

        A = [3, 2, -1]


        Example Output
        Output 1:

        3
        Output 2:

        0

*/
public class PairWithGivenDifference {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(10);
        input.add(-20);
        input.add(20);
        //input.add(10);
       /* input.add(1);
        input.add(0);
        input.add(1);
        input.add(1);*/

        System.out.println(solve(input, -10));


    }

    public static int solve(ArrayList<Integer> A, int B) {

        //A = 10, 20

        // b= 10

        // should return 1
        HashSet<Integer> hSet = new HashSet<>();

        for(int i=0;i<A.size();i++){

            int key1 = B+A.get(i);
            int key2 = A.get(i)-B;

            if(hSet.contains(key1)){
                return 1;
            }

            if(hSet.contains(key2)){
                return 1;
            }

            hSet.add(A.get(i));

        }
        return 0;

    }

}
