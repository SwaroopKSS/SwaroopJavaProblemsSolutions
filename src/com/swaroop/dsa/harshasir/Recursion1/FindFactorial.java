package com.swaroop.dsa.harshasir.Recursion1;

/*

Find Factorial!

Problem Description
        Write a program to find the factorial of the given number A using recursion.

        Note: The factorial of a number N is defined as the product of the numbers from 1 to N.


        Problem Constraints
        0 <= A <= 12



        Input Format
        First and only argument is an integer A.



        Output Format
        Return an integer denoting the factorial of the number A.



        Example Input
        Input 1:

        A = 4
        Input 2:

        A = 1


        Example Output
        Output 1:

        24
        Output 2:

        1

*/
public class FindFactorial {

    public static void main(String[] args) {

        System.out.println(solve(2));
    }

    public static int solve(int A) {
        return fact(A);
    }

    public static int fact(int A){

        if(A == 0){
            return 1;
        }

        return A * fact(A-1);

    }

}
