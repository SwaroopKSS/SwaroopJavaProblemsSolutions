package com.swaroop.dsa.harshasir.class6.SubArrays;

import java.util.ArrayList;
import java.util.Scanner;

/*

Q2. Generate all subarrays
Unsolved
character backgroundcharacter
Stuck somewhere?
Ask for help from a TA and get it resolved.
Get help from TA.
Problem Description
You are given an array A of N integers.
Return a 2D array consisting of all the subarrays of the array


Problem Constraints
1 <= N <= 100
1 <= A[i] <= 105


Input Format
First argument A is an array of integers.


Output Format
Return a 2D array of integers


Example Input
Input 1:
A = [1, 2, 3]
Input 2:
A = [5, 2, 1, 4]


Example Output
Output 1:
[[1], [1, 2], [1, 2, 3], [2], [2, 3], [3]]
Output 2:
[[1 ], [1 4 ], [2 ], [2 1 ], [2 1 4 ], [4 ], [5 ], [5 2 ], [5 2 1 ], [5 2 1 4 ] ]
		
		*/

public class GenerateAllSubArrays {

	public static void main(String[] args) {

	}

	public static ArrayList<ArrayList<Integer>> solve(ArrayList<Integer> A) {


         /*Scanner sc = new Scanner(System.in);
		 int n = sc.nextInt();
		 int[] array = new int[n];
		 for(int i=0;i<array.length;i++) {
			 array[i]=sc.nextInt();
		 }*/

		ArrayList<ArrayList<Integer>> respose = new ArrayList<ArrayList<Integer>>();

		for(int i=0;i< A.size();i++) {

			for(int j=i;j<A.size();j++) {

				ArrayList<Integer>  subarray = new ArrayList<>();
				for(int k=i;k<=j;k++) {
					subarray.add(A.get(k));
					// System.out.print(array[k]);
                     /*if(!(i==j&&array[array.length-1]==array[k])) {
						 System.out.print(" ");
					 }*/
				}
				respose.add(subarray);
				//System.out.println();
			}

		}

		return respose;
	}

}
