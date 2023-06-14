package com.swaroop.dsa.harshasir.Recursion2;

/*

Q3. Kth Symbol - Easy

Problem Description
        On the first row, we write a 0. Now in every subsequent row, we look at the previous row and replace each occurrence of 0 with 01, and each occurrence of 1 with 10.

        Given row number A and index B, return the Bth indexed symbol in row A. (The values of B are 0-indexed.).



        Problem Constraints
        1 <= A <= 20

        0 <= B < 2A - 1



        Input Format
        First argument is an integer A.

        Second argument is an integer B.



        Output Format
        Return an integer denoting the Bth indexed symbol in row A.



        Example Input
        Input 1:

        A = 3
        B = 0
        Input 2:

        A = 4
        B = 4


        Example Output
        Output 1:

        0
        Output 2:

        1

*/

/*

hint:

We will try to generate each of the rows through recursion.
        The first row will have a single integer 0.
        The subsequent rows can be found by replacing 0 with 0 and 1 and
        replacing 1 with 1 and 0.

        Time Complexity : O(2A)
        Space Complexity : O(2A)

*/
public class KthSymbolEasy {

   /* public int solve(int A, int B) {
    }


    public */
}
