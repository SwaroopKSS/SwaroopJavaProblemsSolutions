package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

/*

Q1. Find Perfect Numbers


Problem Constraints
1 <= A <= 106

Problem Description
You are given an integer A. You have to tell whether it is a perfect number or not.

Perfect number is a positive integer which is equal to the sum of its proper positive divisors.

A proper divisor of a natural number is the divisor that is strictly less than the number.



Problem Constraints
1 <= A <= 106



Input Format
First and only argument contains a single positive integer A.



Output Format
Return 1 if A is a perfect number and 0 otherwise.



Example Input
Input 1:

A = 4
Input 2:

A = 6


Example Output
Output 1:

0
Output 2:

1

*/

public class FindPerfectNumbers {

	public static void main(String[] args) {
		
		System.out.println(solve(19));
		

	}
	
public static int solve(int A) {
		
		int sum = 0;
		
		for (int i = 1; i<A; i++) {
			if (A % i == 0) {
				sum = sum + i;
			}

		}

		return (A==sum) ? 1 : 0;
    }

}
