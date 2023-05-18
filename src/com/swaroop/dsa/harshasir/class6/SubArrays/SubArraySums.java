package com.swaroop.dsa.harshasir.class6.SubArrays;

/*

Problem Description

        Given an integer array A of length N. Print array of numbers in which each element is sum of a subarray.
        Problem Constraints

        1 <= N <= 1000
        1 <= A[i] <= 105
        Input Format

        First line is N, size of array
        Next line contains N integers
        Output Format

        Print single line containing subarray sums separated by space
        Example Input

        Input 1:
        3
        11 12 6
        Input 2:
        5
        10 27 12 25 24
        Example Output

        Output 1:
        11 23 29 12 18 6
        Output 2:
        10 37 49 74 98 27 39 64 88 12 37 61 25 49 24

*/

import java.util.Scanner;

public class SubArraySums {

    public static void main(String[] args) {
        // YOUR CODE GOES HERE
        // Please take input and print output to standard input/output (stdin/stdout)
        // DO NOT USE ARGUMENTS FOR INPUTS
        // E.g. 'Scanner' for input & 'System.out' for output

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] array = new int[n];
        for(int i=0;i<array.length;i++) {
            array[i]=sc.nextInt();
        }
//input 1 2 3 4 5

        for(int i=0;i<array.length;i++) {
            for(int j=i;j<array.length;j++) {
            int innerSum = 0;
                for(int k=i;k<=j;k++) {

                    innerSum = innerSum + array[k];

                }
                  System.out.print(innerSum);

                  System.out.print(" ");
            }

        }

    }

}
