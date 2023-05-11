package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

import java.util.Scanner;

/*

Problem Description
Given an integer A, you need to find the count of it's factors.

Factor of a number is the number which divides it perfectly leaving no remainder.

Example : 1, 2, 3, 6 are factors of 6


Problem Constraints
1 <= A <= 109
*/

public class CountFactorsOfNumberN {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
        System.out.println(solve(n));
        
        sc.close();
		
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
