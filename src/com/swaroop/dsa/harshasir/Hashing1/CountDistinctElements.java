package com.swaroop.dsa.harshasir.Hashing1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/*

Q2. Count distinct elements

Problem Description
        Given an array A of N integers, return the number of unique elements in the array.


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
        3
        Output 2:
        4
*/

public class CountDistinctElements {

    public static void main(String[] args) {

        ArrayList<Integer> A = new ArrayList<>();

        A.add(6);
        A.add(3);
        A.add(3);
        A.add(6);
        A.add(7);
        A.add(8);

        System.out.println(solve(A));

    }

    public static int solve(ArrayList<Integer> A) {

        int count = 0;

        HashSet<Integer> hSet = new HashSet<>();

        for(int i=0;i<A.size();i++){


          if(!hSet.contains(A.get(i))){
              hSet.add(A.get(i));
          }
        }

        count= hSet.size();

        return count;
    }

}
