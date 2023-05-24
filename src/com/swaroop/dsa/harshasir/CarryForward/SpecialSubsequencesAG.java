package com.swaroop.dsa.harshasir.CarryForward;

/*

Q1. Special Subsequences "AG" - 2
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
You have given a string A having Uppercase English letters.

You have to find the number of pairs (i, j) such that A[i] = 'A', A[j] = 'G' and i < j.



Problem Constraints
1 <= length(A) <= 105



Input Format
First and only argument is a string A.

*/

public class SpecialSubsequencesAG {

	public static void main(String[] args) {

	}
	
	public static Long solve(String A) {
		
		char [] ch = A.toCharArray();
		
		int countG=0;
		Long count=0L;
		for(int i=ch.length-1;i>=0;i--) {
			
			if(ch[i]=='G') {
				countG++;
			}
			if(ch[i]=='A') {
				count=count+countG;
			}
			
		}
		
		return count;
    }

}
