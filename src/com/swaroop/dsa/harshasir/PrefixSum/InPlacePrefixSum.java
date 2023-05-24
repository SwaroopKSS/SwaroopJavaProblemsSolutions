package com.swaroop.dsa.harshasir.PrefixSum;

import java.util.ArrayList;

public class InPlacePrefixSum {

	public static void main(String[] args) {
		
		ArrayList<Integer> input = new ArrayList<>();
		input.add(1);
		input.add(2);
		input.add(3);
		input.add(4);
		input.add(5);
		
		System.out.println(solve(input));
		

	}
	
	public static ArrayList<Integer> solve(ArrayList<Integer> A) {
		
		for(int i=0;i<A.size();i++) {
			
			if(i>0) {
				A.set(i, A.get(i-1)+A.get(i));
			}
			
		}
		
		return A;
    }

}
