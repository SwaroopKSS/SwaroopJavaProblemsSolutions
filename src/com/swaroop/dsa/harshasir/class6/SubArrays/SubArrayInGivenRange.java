package com.swaroop.dsa.harshasir.class6.SubArrays;

import java.util.ArrayList;

/*

Q7. Subarray in given range
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
Given an array A of length N, return the subarray from B to C.


Problem Constraints
1 <= N <= 105

1 <= A[i] <= 109

0 <= B <= C < N



Input Format
The first argument A is an array of integers

The remaining argument B and C are integers.



Output Format
Return a subarray
*/

public class SubArrayInGivenRange {

	public static void main(String[] args) {
		
		

	}
	
	public static ArrayList<Integer> solve(ArrayList<Integer> A, int B, int C) {
		
		ArrayList<Integer> result = new ArrayList<>();
		
		for(int i=B;i<=C;i++) {
			result.add(A.get(i));
		}
		
		return result;
    }

}
