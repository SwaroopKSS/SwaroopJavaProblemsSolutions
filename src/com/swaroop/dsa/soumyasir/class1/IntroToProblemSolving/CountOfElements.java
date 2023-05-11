package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

/*

Q4. Count of elements
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
Given an array A of N integers. Count the number of elements that have at least 1 elements greater than itself.


Problem Constraints
1 <= N <= 105
1 <= A[i] <= 109


Input Format
First and only argument is an array of integers A.

*/

public class CountOfElements {

	public static void main(String[] args) {
		
		
		System.out.println(solve(19));
		

	}
	
public static int solve(int A) {
		
		int count = 0;
		
		for (int i = 1; (i * i) <= A; i++) {
			if (A % i == 0) {
				if (i == A / i) {
					count = count + 1;
				} else {
					count = count + 2;
				}
			}

		}		
		return count;
    }


}
