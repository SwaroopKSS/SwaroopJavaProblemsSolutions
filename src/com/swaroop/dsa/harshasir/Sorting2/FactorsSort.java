package com.swaroop.dsa.harshasir.Sorting2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*

Problem Description
        You are given an array A of N elements. Sort the given array in increasing order of number of distinct factors of each element, i.e., element having the least number of factors should be the first to be displayed and the number having highest number of factors should be the last one. If 2 elements have same number of factors, then number with less value should come first.

        Note: You cannot use any extra space


        Problem Constraints
        1 <= N <= 104
        1 <= A[i] <= 104


        Input Format
        First argument A is an array of integers.


        Output Format
        Return an array of integers.


        Example Input
        Input 1:
        A = [6, 8, 9]
        Input 2:
        A = [2, 4, 7]


        Example Output
        Output 1:
        [9, 6, 8]
        Output 2:
        [2, 7, 4]

*/
public class FactorsSort {

    public ArrayList<Integer> solve(ArrayList<Integer> A) {

        Collections.sort(A, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int c1=CountFactors(o1);
                int c2=CountFactors(o2);
                if(c1==c2){
                    return o1-o2;

                }else{
                   return c1-c2;
                }
            }
        });

        return A;
    }

    public int CountFactors(Integer n){
        int count = 0;
        for(int i=1;i*i<=n;i++){
            if(n%i==0){
                if(i==n/i){
                    count++;
                }else{
                    count = count + 2;
                }
            }
        }
        return count;
    }


}
