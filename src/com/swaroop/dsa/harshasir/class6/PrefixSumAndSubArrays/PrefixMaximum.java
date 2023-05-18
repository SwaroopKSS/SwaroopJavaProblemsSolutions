package com.swaroop.dsa.harshasir.class6.PrefixSumAndSubArrays;

/*
Q2. Prefix maximum
        Unsolved
        character backgroundcharacter
        Stuck somewhere?
        Ask for help from a TA and get it resolved.
        Get help from TA.
        Problem Description
        Kamal is a software developer and he's working on a new feature for an e-commerce website. The website has a list of prices for different products, and Kamal needs to find the maximum price of all products up to a given index.

        He has the list of prices in an array A of length N, and he needs to write a program that will return the maximum price occurring in the subarray from 0 to i for every index i. Kamal needs your help to implement this function.



        Problem Constraints
        1 <= N <= 105
        1 <= A[i] <= 109


        Input Format
        Only argument A is an array of integers.


        Output Format
        Return an array of integers where the i-th element of the array represents the maximum value occurring in the subarray from 0 to i in the input array A.

*/

import java.util.ArrayList;

public class PrefixMaximum {

    public static void main(String[] args) {

		ArrayList<Integer> input = new ArrayList<>();
        input.add(16);
        input.add(17);
        input.add(4);
        input.add(3);
        input.add(5);
        input.add(2);

        System.out.println(solve(input));


    }

    public static ArrayList<Integer> solve(ArrayList<Integer> A) {

        ArrayList<Integer> response = new ArrayList<>();
        response.add(A.get(0));
        for(int i=1;i<A.size();i++){
            if(A.get(i)>response.get(i-1)){
                response.add(A.get(i));
            }else{
                response.add(response.get(i-1));
            }
        }
        return response;
    }

}
