package com.swaroop.dsa.harshasir.class5.PrefixSum;

import java.util.ArrayList;

/*

4. Range Sum Query
        Unsolved
        character backgroundcharacter
        Stuck somewhere?
        Ask for help from a TA and get it resolved.
        Get help from TA.
        Problem Description
        You are given an integer array A of length N.
        You are also given a 2D integer array B with dimensions M x 2, where each row denotes a [L, R] query.
        For each query, you have to find the sum of all elements from L to R indices in A (0 - indexed).
        More formally, find A[L] + A[L + 1] + A[L + 2] +... + A[R - 1] + A[R] for each query.



        Problem Constraints
        1 <= N, M <= 105
        1 <= A[i] <= 109
        0 <= L <= R < N


Input Format
        The first argument is the integer array A.
        The second argument is the 2D integer array B.


        Output Format
        Return an integer array of length M where ith element is the answer for ith query in B.

Input 1:
A = [1, 2, 3, 4, 5]
B = [[0, 3], [1, 2]]

Output 1:
[10, 5]

*/

public class RangeSumQuery {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);

        ArrayList<ArrayList<Integer>> queryArray = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> query1 = new ArrayList<>();

        query1.add(0);
        query1.add(3);

        ArrayList<Integer> query2 = new ArrayList<>();

        query2.add(1);
        query2.add(2);

        queryArray.add(query1);
        queryArray.add(query2);


        System.out.println(solve(input,queryArray));


    }

    public static ArrayList<Long> solve(ArrayList<Integer> A, ArrayList<ArrayList<Integer>> B) {

        ArrayList<Long> response = new ArrayList<>();

        ArrayList<Long> ps = new ArrayList<>();

        ps.add(Long.valueOf(A.get(0)));


        for(int i=1;i<A.size();i++){

            ps.add(Long.valueOf(ps.get(i-1) + A.get(i) ));

        }
// above logic is to form prefix sum array


        for(int i=0;i<B.size();i++){

            Long sum = 0L;

// range sum query

            int s =  B.get(i).get(0);
            int e =  B.get(i).get(1);

            if(s == 0){
                sum = ps.get(e);
                response.add(sum);
            }else {
                sum = ps.get(e) - ps.get(s-1);
                response.add(sum);
            }

        }
        return response;
    }

}
