package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

import java.util.Scanner;

/*
 Problem Description
Given an Integer A. Return 1 if A is prime and return 0 if not.


Problem Constraints
1 <= A <= 1012
 */

public class IsPrime {

	public static void main(String[] args) {
			
			Scanner sc = new Scanner(System.in);
			//67010446483  - failing - this is actually prime number, but giving wrong answer
			
			long n = sc.nextLong();
			
			Long m = new Long(n);
			
	        System.out.println(solve(m));
	        
	        sc.close();
			
		}
		
		public static int solve(Long A) {
			
			int count = 0;
			//int N = A.intValue();
			for (long i = 1L; (i * i) <= A; i++) {
				if (A % i == 0) {
					if (i == A / i) {
						count = count + 1;
					} else {
						count = count + 2;
					}
				}

			}		
			if(count==2) {
				return 1;
			}else {
				return 0;
			}
	    }

}
