package com.swaroop.dsa.harshasir.Searching2;


/*

Problem Description
        Given a sorted array of integers A where every element appears twice except for one element which appears once, find and return this single element that appears only once.

        Elements which are appearing twice are adjacent to each other.

        NOTE: Users are expected to solve this in O(log(N)) time.



        Problem Constraints
        1 <= |A| <= 100000

        1 <= A[i] <= 10^9



        Input Format
        The only argument given is the integer array A.



        Output Format
        Return the single element that appears only once.



        Example Input
        Input 1:

        A = [1, 1, 7]
        Input 2:

        A = [2, 3, 3]


        Example Output
        Output 1:

        7
        Output 2:

        2

*/
public class SingleElementInSortedArray{

    public int solve(int[] A) {

        //A = 1,1,2,2,3

        int n=A.length;
        int l=0;
        int r = n-1;

        while(l<=r){

            int m = (l+r)/2;

            if(m==0 || m==(n-1) || ( A[m]!=A[m-1] && A[m]!=A[m+1] )){
                return A[m];
            }

            if(A[m]!=A[m+1]){ //checking if this is 2nd occurance

                m=m-1;

            }

                if(m%2==0){ // if first occurance is even, then move to right for searching

                    l=m+2;

                }else{
                    r=m-1;
                }

        }
        return -1;
    }


}
