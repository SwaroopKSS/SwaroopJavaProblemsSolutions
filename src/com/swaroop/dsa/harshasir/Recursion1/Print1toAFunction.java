package com.swaroop.dsa.harshasir.Recursion1;

import java.util.ArrayList;

/*

Print A to 1 function


Problem Description
        You are given an integer A, print A to 1 using using recursion.

        Note :- After printing all the numbers from A to 1, print a new line.



        Problem Constraints
        1 <= A <= 104



        Input Format
        First argument A is an integer.



        Output Format
        Print A space-separated integers A to 1.
        Note: There should be exactly one space after each integer. Print a new line after printing the A integers



        Example Input
        Input 1:

        10
        Input 2:

        5


        Example Output
        Output 1:

        10 9 8 7 6 5 4 3 2 1
        Output 2:

        5 4 3 2 1

*/
public class Print1toAFunction {

    public static void main(String[] args) {

        solve(5);


    }

    public static void solve(int A) {
        prin(A);
        System.out.println();
    }

    public static void prin(int A){

        if(A == 1){
            System.out.print(A);
            //if(A>1) {
                System.out.print(" ");
            //}
            return;
        }

        prin(A-1);

        System.out.print(A);
        System.out.print(" ");

    }

}
