package com.swaroop.dsa.harshasir.Hashing1;

import java.util.ArrayList;
import java.util.HashMap;

/*

Problem Description
        Given an array of integers A, find and return whether the given array contains a non-empty subarray with a sum equal to 0.

        If the given array contains a sub-array with sum zero return 1, else return 0.



        Problem Constraints
        1 <= |A| <= 100000

        -10^9 <= A[i] <= 10^9



        Input Format
        The only argument given is the integer array A.



        Output Format
        Return whether the given array contains a subarray with a sum equal to 0.



        Example Input
        Input 1:

        A = [1, 2, 3, 4, 5]
        Input 2:

        A = [4, -1, 1]


        Example Output
        Output 1:

        0
        Output 2:

        1

*/
public class SubArrayWithZeroSum {

    // Do not write code to include libraries, main() function or accept any input from the console.
    // Initialization code is already written and hidden from you. Do not write code for it again.
    public static int solve(ArrayList<Integer> A) {
        // Just write your code below to complete the function. Required input is available to you as the function arguments.
        // Do not print the result or any output. Just return the result via this function.

        ArrayList<Long> ps = getPrefixSumArray(A);
        HashMap<Long, Integer> hMap =getFrequencyMap(ps);

        for(int i=0;i<ps.size();i++){
            if(ps.get(i) == 0 || hMap.get(ps.get(i)) > 1){
                return 1;
            }
        }
        return 0;
    }

    public static ArrayList<Long> getPrefixSumArray(ArrayList<Integer> A){

        ArrayList<Long> prefixSum = new ArrayList<>();

        prefixSum.add(Long.valueOf(A.get(0)));

        for(int i=1;i<A.size();i++){

           prefixSum.add((A.get(i)+prefixSum.get(i-1)));

        }
      return prefixSum;
    }

    public static HashMap<Long, Integer> getFrequencyMap(ArrayList<Long> ps) {
        HashMap<Long, Integer> hMap = new HashMap<>();

        for (int i = 0; i < ps.size(); i++) {

            if (hMap.containsKey(ps.get(i))) {
                hMap.put(ps.get(i), hMap.get(ps.get(i)) + 1);
            } else {
                hMap.put(ps.get(i), 1);
            }
        }
        return hMap;
    }



}
