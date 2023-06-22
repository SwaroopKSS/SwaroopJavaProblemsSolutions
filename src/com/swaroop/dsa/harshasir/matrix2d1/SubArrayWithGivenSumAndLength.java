package com.swaroop.dsa.harshasir.matrix2d1;

/*

Subarray with given sum and length

Problem Description
        Given an array A of length N. Also given are integers B and C.

        Return 1 if there exists a subarray with length B having sum C and 0 otherwise



        Problem Constraints
        1 <= N <= 105

        1 <= A[i] <= 104

        1 <= B <= N

        1 <= C <= 109



        Input Format
        First argument A is an array of integers.

        The remaining arguments B and C are integers



        Output Format
        Return 1 if such a subarray exist and 0 otherwise


        Example Input
        Input 1:
        A = [4, 3, 2, 6, 1]
        B = 3
        C = 11
        Input 2:

        A = [4, 2, 2, 5, 1]
        B = 4
        C = 6


        Example Output
        Output 1:
        1
        Output 2:

        0

*/
public class SubArrayWithGivenSumAndLength {


    public static void main(String args[]){

        int[] A = {6,3,3,6,7,8,7,3,7};

        //int[] A = {4,3,2,6,1};

        int B = 2;

       // int B = 3;

        int C = 10;

       // int C = 11;



        System.out.println(solve(A,B,C));
    }


//sliding window technique. calculating sum of length

    public static int solve(int[] A, int B, int C) {

        //B is lenth of subarray
        //C sum to match with

        int s = 0;
        int e = B-1;

        long sum = 0;

        for(int i=0;i<=e;i++){
            sum = sum + A[i];
        }

        s++; e++;

        if(sum == C){
            return 1;
        }


        while(e<A.length){
            sum = sum - A[s-1] + A[e];

            if(sum==C){
                return 1;
            }

            s++;e++;

        }
        return 0;
    }

}
