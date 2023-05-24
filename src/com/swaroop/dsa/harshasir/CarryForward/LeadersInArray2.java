package com.swaroop.dsa.harshasir.CarryForward;

import java.util.ArrayList;

public class LeadersInArray2 {

	public static void main(String[] args) {
		
		/*int[] A = { 16, 17, 4, 3, 5, 2	};
		
		int[] res= solve(A);*/
		
		ArrayList<Integer> input = new ArrayList<>();
		input.add(16);
		input.add(17);
		input.add(4);
		input.add(3);
		input.add(5);
		input.add(2);
		
		System.out.println(solve(input));
		
		/*for(int j=0;j<res.length-1;j++) {
		System.out.println(res[j]);
		}*/
		
		
	}
	
	/*public static int[] solve(int[] A) {
		
		int[] res = new int[A.length];
		int max=A[A.length-1];
		int j=1;
		res[0]=max;
		for(int i=A.length-2;i>=0;i--) {
			if(A[i]>max) {
				max=A[i];
				res[j]=max;
				j++;
			}
			
		}
				
		return res;
    }*/
	
		
public static ArrayList<Integer> solve(ArrayList<Integer> A) {
		
	ArrayList<Integer> res = new ArrayList<>();
	
		int max=A.get(A.size()-1);
		
		int j=1;
		//res.set(0, max);
		res.add(max);
		for(int i=A.size()-2;i>=0;i--) {
			if(A.get(i)>max) {
				max=A.get(i);
				//res.set(j, max);
				res.add(max);
				j++;
			}
			
		}
				
		return res;
    }

}
