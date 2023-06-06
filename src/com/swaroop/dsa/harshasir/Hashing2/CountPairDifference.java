package com.swaroop.dsa.harshasir.Hashing2;


import java.util.ArrayList;
import java.util.HashMap;

/*

Count Pair Difference

Problem Description
        You are given an array A of N integers and an integer B.
        Count the number of pairs (i,j) such that A[i] - A[j] = B and i ≠ j.

        Since the answer can be very large, return the remainder after dividing the count with 109+7.


        Problem Constraints
        1 <= N <= 105
        1 <= A[i] <= 109
        1 <= B <= 109


        Input Format
        First argument A is an array of integers and second argument B is an integer.


        Output Format
        Return an integer.


        Example Input
        Input 1:

        A = [3, 5, 1, 2]
        B = 4
        Input 2:

        A = [1, 2, 1, 2]
        B = 1


        Example Output
        Output 1:

        1
        Output 2:

        4

*/
public class CountPairDifference {


    public int solve(ArrayList<Integer> A, int B) {

    long count = 0;

    HashMap<Integer, Integer> hMap = new HashMap<>();

        for(int i = 0;i<A.size();i++){

        int key1 = B + A.get(i);
        int key2 = A.get(i) - B;

        if (hMap.containsKey(key1)) {
            count = count + hMap.get(key1);
        }

        if (hMap.containsKey(key2)) {
                count = count + hMap.get(key2);
        }

        if (hMap.containsKey(A.get(i))) {

            hMap.put(A.get(i), hMap.get(A.get(i)) + 1);

        } else {

            hMap.put(A.get(i), 1);

        }


    }

    count =(count%1000000007);

        return(int)count;
    }
}
