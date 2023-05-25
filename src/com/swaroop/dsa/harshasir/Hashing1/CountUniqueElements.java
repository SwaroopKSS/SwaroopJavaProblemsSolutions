package com.swaroop.dsa.harshasir.Hashing1;

/*

Problem Description
        You are given an array A of N integers. Return the count of elements with frequncy 1 in the given array.


        Problem Constraints
        1 <= N <= 105
        1 <= A[i] <= 109


        Input Format
        First argument A is an array of integers.


        Output Format
        Return an integer.


        Example Input
        Input 1:
        A = [3, 4, 3, 6, 6]
        Input 2:
        A = [3, 3, 3, 9, 0, 1, 0]


        Example Output
        Output 1:
        1
        Output 2:
        2

*/

import java.util.ArrayList;
import java.util.HashMap;

public class CountUniqueElements {

    public static int solve(ArrayList<Integer> A) {

        int count = 0;

        HashMap<Integer, Integer>  fqMap = getFrequencyMap(A);

        for(int i=0;i<A.size();i++){

            if(fqMap.get(A.get(i))==1){
               count = count + 1;
            }

        }

        return count;
    }

    public static HashMap<Integer, Integer> getFrequencyMap(ArrayList<Integer> A) {
        HashMap<Integer, Integer> hMap = new HashMap<>();

        for (int i = 0; i < A.size(); i++) {

            if (hMap.containsKey(A.get(i))) {
                hMap.put(A.get(i), hMap.get(A.get(i)) + 1);
            } else {
                hMap.put(A.get(i), 1);
            }
        }
        return hMap;
    }


}
