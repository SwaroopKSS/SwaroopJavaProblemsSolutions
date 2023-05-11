package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

import java.util.ArrayList;
import java.util.List;

/*
Q5. Array Rotation
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
Given an integer array A of size N and an integer B, you have to return the same array after rotating it B times towards the right.


Problem Constraints
1 <= N <= 105
1 <= A[i] <=109
1 <= B <= 109


Input Format
The first argument given is the integer array A.
The second argument given is the integer B.

*/

public class ArrayRotation {

	public static void main(String[] args) {
		
		ArrayList<Integer> A = new ArrayList<>();
		A.add(1);
		A.add(2);
		A.add(3);
		A.add(2);
		A.add(1);
		

	    System.out.println(solve(A,3));
		

	}
	
	

public static ArrayList<Integer> solve(ArrayList<Integer> A, int B) {
	
// reverse entire array
	// reverse first k elements
	
// reverse remaining elements from k to n-1
	
	int n = A.size();
	
	B=B%n;
	
	reverseArray(A,0,n-1);
	reverseArray(A,0,B-1);
	reverseArray(A,B,n-1);
	
	return A;
}

public static ArrayList<Integer> reverseArray(ArrayList<Integer> A, int B, int C) {
	
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
