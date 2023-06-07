package com.swaroop.dsa.harshasir.Recursion2;

/*

Q1. Is magic?

Problem Description
        Given a number A, check if it is a magic number or not.

        A number is said to be a magic number if the sum of its digits is calculated till a single digit recursively by adding the sum of the digits after every addition. If the single digit comes out to be 1, then the number is a magic number.



        Problem Constraints
        1 <= A <= 109



        Input Format
        The first and only argument is an integer A.



        Output Format
        Return an 1 if the given number is magic else return 0.



        Example Input
        Input 1:

        A = 83557
        Input 2:

        A = 1291


        Example Output
        Output 1:

        1
        Output 2:

        0

*/
public class IsMagic {

    public static void main(String[] args) {

        System.out.print(solve(1234510021));
    }

    public static int solve(int A) {
       return isMagic(A)==1 ? 1 : 0;
    }

    public static int isMagic(int A){
        if(A>=1 && A<=9){
         return A;
        }
        A = sumOfDigits(A);
        return isMagic(A);
    }


    public static int sumOfDigits(int A) {

        if(A/10 == 0){
            return A;
        }

        int lastDigit = A%10;

        A = A/10;

        return lastDigit + sumOfDigits(A);
    }
}
