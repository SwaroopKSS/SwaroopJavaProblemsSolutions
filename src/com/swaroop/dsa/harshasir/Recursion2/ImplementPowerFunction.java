package com.swaroop.dsa.harshasir.Recursion2;

/*

Q2. Implement Power Function

Problem Description
        Implement pow(A, B) % C.
        In other words, given A, B and C, Find (AB % C).
        Note: The remainders on division cannot be negative. In other words, make sure the answer you return is non-negative.


        Problem Constraints
        -109 <= A <= 109
        0 <= B <= 109
        1 <= C <= 109


        Input Format
        Given three integers A, B, C.


        Output Format
        Return an integer.


        Example Input
        Input 1:
        A = 2
        B = 3
        C = 3
        Input 2:
        A = 3
        B = 3
        C = 1


        Example Output
        Output 1:
        2
        Output 2:
        0

*/
public class ImplementPowerFunction {



    public static void main(String[] args) {

        System.out.println(pow(10000009,10000009,100009));
        //System.out.println(pow(10,3,1000));

        System.out.println(pow(-1,1,20));

        //System.out.println(-1%20);

       // Math.floorMod(-1,20);

        System.out.println(Math.floorMod(-1,20));

        // since java gives wrong values for % symbol on negative numbers, I am using Math.floodMod(int x, int y )  method
    }

    public static int pow(int A, int B, int C) {
        // Just write your code below to complete the function. Required input is available to you as the function arguments.
        // Do not print the result or any output. Just return the result via this function.

        if(A==0){
            return 0;
        }

        if(B==0){
            return 1;
        }

        long x = pow(A,B/2, C);

        long y =  (x%C * x%C)%C;

        if(B%2==0){
            return (int)(y);
        }

        return (int)(Math.floorMod(A, C) * y%C)%C;

        //used inbuild java flood mod method t avoid incorrect modulus operator result for negative numbers

    }

}
