package com.swaroop.dsa.harshasir.Searching2;

/*

Problem Description
        Given an integer A. Compute and return the square root of A.
        If A is not a perfect square, return floor(sqrt(A)).

        The value of A can cross the range of Integer.

        NOTE:
        Do not use the sqrt function from the standard library.
        Users are expected to solve this in O(log(A)) time.


        Problem Constraints
        0 <= A <= 1010


        Input Format
        The first and only argument given is the integer A.


        Output Format
        Return floor(sqrt(A))


        Example Input
        Input 1:

        11
        Input 2:

        9


        Example Output
        Output 1:

        3
        Output 2:

        3

*/
public class SquareRootOfInteger {

    public static void main(String args[]){

        System.out.println(sqrt(2147483647));
        //System.out.println(sqrt(625));

    }
    public static int sqrt(int A) {

        if(A==0 || A==1){
            return A;
        }


        long l = 1;
        long h = A;

        long ans = -1;

        while(l<=h){
            long m = (l+h)/2;
            if(m*m==A){
                return (int)m;
            }
            if(m*m>A){
                h=m-1;
            }
            else{
                ans=m;
                l=m+1;
            }
        }
        return (int)ans;
    }
}
