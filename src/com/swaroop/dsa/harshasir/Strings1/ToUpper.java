package com.swaroop.dsa.harshasir.Strings1;

import java.util.Arrays;

/*

Problem Description
        You are given a function to_upper() consisting of a character array A.

        Convert each character of A into Uppercase character if it exists. If the Uppercase of a character does not exist, it remains unmodified.
        The lowercase letters from a to z is converted to uppercase letters from A to Z respectively.

        Return the uppercase version of the given character array.



        Problem Constraints
        1 <= |A| <= 105



        Input Format
        Only argument is a character array A.



        Output Format
        Return the uppercase version of the given character array.



        Example Input
        Input 1:

        A = ['S', 'c', 'A', 'L', 'E', 'r', 'A', 'c', 'a', 'D', 'e', 'm', 'y']
        Input 2:

        A = ['S', 'c', 'a', 'L', 'e', 'R', '#', '2', '0', '2', '0']


        Example Output
        Output 1:

        ['S', 'C', 'A', 'L', 'E', 'R', 'A', 'C', 'A', 'D', 'E', 'M', 'Y']
        Output 2:

        ['S', 'C', 'A', 'L', 'E', 'R', '#', '2', '0', '2', '0']

*/
public class ToUpper {

    public static char[] to_upper(char[] A) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 'a' && A[i] <= 'z') {
               // A[i] = (char) (A[i] - 'a' + 'A');
                A[i] = (char) ((char)A[i] - 32); // a=97, A=65  difference 32
            }
        }
        return A;
    }

    public static void main(String[] args) {
        char[] input1 = {'S', 'c', 'A', 'L', 'E', 'r', 'A', 'c', 'a', 'D', 'e', 'm', 'y'};
        char[] input2 = {'S', 'c', 'a', 'L', 'e', 'R', '#', '2', '0', '2', '0'};

        char[] output1 = to_upper(input1);
        char[] output2 = to_upper(input2);

        System.out.println("Output 1: " + Arrays.toString(output1));
        System.out.println("Output 2: " + Arrays.toString(output2));
    }
}
