package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

/*

Q1. Find Perfect Numbers
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
You are given an integer A. You have to tell whether it is a perfect number or not.

Perfect number is a positive integer which is equal to the sum of its proper positive divisors.

A proper divisor of a natural number is the divisor that is strictly less than the number.



Problem Constraints
1 <= A <= 106

*/

public class FindPerfectNumbers {

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
