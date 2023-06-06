package com.swaroop.dsa.harshasir.Recursion1;

/*

Problem Description
        Write a recursive function that checks whether string A is a palindrome or Not.
        Return 1 if the string A is a palindrome, else return 0.

        Note: A palindrome is a string that's the same when read forward and backward.



        Problem Constraints
        1 <= |A| <= 50000

        String A consists only of lowercase letters.



        Input Format
        The first and only argument is a string A.



        Output Format
        Return 1 if the string A is a palindrome, else return 0.



        Example Input
        Input 1:

        A = "naman"
        Input 2:

        A = "strings"


        Example Output
        Output 1:

        1
        Output 2:

        0

*/
public class checkPalindrome {

    public static void main(String[] args) {

       // System.out.println(solve("naman"));
        System.out.println(solve("palindrome"));
    }

    public static int solve(String A) {
        char [] ch = A.toCharArray();

        if(ch.length==1){
            return 1;
        }

        int s = 0;
        int e = ch.length-1;



        return isPali(ch,s,e) ? 1 : 0;
    }

    public static boolean isPali(char[] ch,int s, int e){

        if(s>=e){
            return true;
        }

        return (ch[s]==ch[e] && isPali(ch, s+1, e-1));
    }



}
