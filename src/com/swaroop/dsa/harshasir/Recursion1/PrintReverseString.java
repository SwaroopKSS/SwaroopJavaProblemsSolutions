package com.swaroop.dsa.harshasir.Recursion1;

/*

Q1. Print reverse string

Problem Description
        Write a recursive function that takes a string, S, as input and prints the characters of S in reverse order.



        Problem Constraints
        1 <= |s| <= 1000



        Input Format
        First line of input contains a string S.



        Output Format
        Print the character of the string S in reverse order.



        Example Input
        Input 1:

        scaleracademy
        Input 2:

        cool


        Example Output
        Output 1:

        ymedacarelacs
        Output 2:

        looc

*/

import java.util.Scanner;

public class PrintReverseString {

    public static void main(String[] args) {
        // YOUR CODE GOES HERE
        // Please take input and print output to standard input/output (stdin/stdout)
        // DO NOT USE ARGUMENTS FOR INPUTS
        // E.g. 'Scanner' for input & 'System.out' for output

        Scanner sc = new Scanner(System.in);

		String A = sc.nextLine();

        solve(A);

        //solve("naman oja");
        //solve("a");

    }

    public  static void solve(String A){

        char[] ch = A.toCharArray();

        int index = ch.length-1;

        reverse(ch,index);

    }

    public static void reverse(char[] ch,int index){

        if(index == 0){
            System.out.print(ch[index]);
            return;
        }

        System.out.print(ch[index]);

        reverse(ch, index-1);

    }
}
