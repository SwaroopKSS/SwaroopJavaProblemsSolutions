package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

import java.util.ArrayList;
import java.util.List;

/*

Given an array A of N integers. Also given are two integers B and C. Reverse the array A in the given range [B, C]


Problem Constraints
1 <= N <= 105
1 <= A[i] <= 109
0 <= B <= C <= N - 1


Input Format
The first argument A is an array of integer.
The second and third arguments are integers B and C

*/

public class ReversePartOfArray {

	public static void main(String[] args) {
		
		ArrayList<Integer> A = new ArrayList<>();
		A.add(1);
		A.add(2);
		A.add(3);
		A.add(2);
		A.add(1);
		
		int B=1;
		int C=3;

	    System.out.println(solve(A, B, C));

	}

public static ArrayList<Integer> solve(ArrayList<Integer> A, int B, int C) {
		
		/*int s=0;
		int e=A.size()-1;*/
		
			while(B<C) {
				
				//swap the array first and last elements and increment s and decrement e
				
				int temp = A.get(B);
                A.set(B, A.get(C));
				A.set(C, temp);
				
                B++;
                C--;
			}
				
		return A;	
	}
	
	}
