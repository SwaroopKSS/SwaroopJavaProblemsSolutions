package com.swaroop.dsa.harshasir.Strings1;

import java.util.ArrayList;

/*
Problem Description
        You are given a function to_lower() which takes a character array A as an argument.

        Convert each character of A into lowercase characters if it exists. If the lowercase of a character does not exist, it remains unmodified.
        The uppercase letters from A to Z are converted to lowercase letters from a to z respectively.

        Return the lowercase version of the given character array.



        Problem Constraints
        1 <= |A| <= 105



        Input Format
        The only argument is a character array A.



        Output Format
        Return the lowercase version of the given character array.


*/
public class ToLower {

    public static void main(String[] args) {
        char[] input = {'H', 'e', 'L', 'l', 'o', 'W', 'o', 'r', 'L', 'd'};
        char[] result = to_lower(input);
        System.out.println(result); // Output: "helloWorld"
    }

    public static char[] to_lower(char[] A) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= 'A' && A[i] <= 'Z') {
                char lowercase = (char) (A[i] + ('a' - 'A'));
                if (lowercase >= 'a' && lowercase <= 'z') {
                    A[i] = lowercase;
                }
            }
        }
        return A;
    }


   /* public static char[] to_lower(char[] A) {
        for (int i = 0; i < A.length; i++) {
            if (Character.isUpperCase(A[i])) {
                A[i] = Character.toLowerCase(A[i]);
            }
        }
        return A;
    }*/
}
