package com.swaroop.dsa.harshasir.Recursion2;

/*

Q1. Sum of Digits!

Problem Description
        Given a number A, we need to find the sum of its digits using recursion.


        Problem Constraints
        1 <= A <= 109


        Input Format
        The first and only argument is an integer A.


        Output Format
        Return an integer denoting the sum of digits of the number A.


        Example Input
        Input 1:

        A = 46
        Input 2:

        A = 11


        Example Output
        Output 1:

        10
        Output 2:

        2

*/

public class SumOfDigits {

    public static void main(String[] args) {

        System.out.print(solve(12345));
    }

    public static int solve(int A) {
        return SumOfDigits(A);
    }


    public static int SumOfDigits(int A) {

        if(A/10 == 0){
            return A;
        }

        int lastDigit = A%10;

        return lastDigit + SumOfDigits(A/10);
    }

}
