package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

/*

2. Count of primes
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
You will be given an integer n. You need to return the count of prime numbers less than or equal to n.


Problem Constraints
0 <= n <= 10^3


Input Format
Single input parameter n in function.

*/

/*

Example Explanation
Explanation 1: Primes <= 19 are 2, 3, 5, 7, 11, 13, 17, 19
Explanation 2: There are no primes <= 1 

*/

public class CountOfPrimes {

	public static void main(String[] args) {
		
		System.out.println(solve(19));
		
	}
	
	public static int solve(int A) {
		
		int count = 0;
		
		if(A<=1) {
			return 0;
		}
		else {
		for (int i = 2; i<=A; i++) {
			if(isPrime(i)) {
				count = count + 1;
			}
		}
		}
			
		return count;
    }
	
	public static boolean isPrime(int A)
	{
	
	int count = 0;
	//int N = A.intValue();
	for (int i = 1; (i * i) <= A; i++) {
		if (A % i == 0) {
			if (i == A / i) {
				count = count + 1;
			} else {
				count = count + 2;
			}
		}

	}		
	if(count==2) {
		return true;
	}else {
		return false;
	}
	
	}

}
