package com.swaroop.dsa.harshasir.matrix2d1;

/*

Q4. Find in matrix

Problem Description
        Given an matrix A of size NxN, which is row-wise and column-wise sorted. Find if the number B exists in the matrix.

        Note :
        0 represents False and 1 represents True


        Problem Constraints
        1 <= N <= 103
        -105 <= A[i][j] <= 105


        Input Format
        First argument A is an matrix of integers.
        Second argument is B.


        Output Format
        Return a boolean value stating if the number exists or not


        Example Input
        Input 1:

        A = [[1, 5, 6],
        [4, 8, 11],
        [7, 9, 14]]
        B = 14
        Input 2:

        A = [[4, 10],
        [4, 12]]
        B = 5


        Example Output
        Output 1:

        True
        Output 2:

        False


        Example Explanation
        Explanation 1:

        14 is found at the last index in the matrix.
        Explanation 2:

        5 is not present in the matrix.

*/
public class FindInMatrix {
    public int solve(int[][] A, int B) {


        for(int i=0;i<A.length;i++){

            for(int j=0;j<A[i].length;j++){

                if(A[i][j]==B){
                    return 1;
                }

            }

        }
        return 0;
    }
}
