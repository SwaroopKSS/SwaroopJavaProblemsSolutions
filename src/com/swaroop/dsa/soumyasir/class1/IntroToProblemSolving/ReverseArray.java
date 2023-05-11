package com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ReverseArray {

public static void main(String[] args) {
		
		/*Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		
        System.out.println(solve(n));
        
        sc.close();*/
	
	List<Integer> A = new ArrayList<>();
	A.add(1);
	A.add(2);
	A.add(3);
	A.add(2);
	A.add(1);
	

    System.out.println(solve(A));
	
		
	}
	
	public static ArrayList<Integer> solve(final List<Integer> A) {
		
		ArrayList<Integer> B = new ArrayList<>(A);
		
		int s=0;
		int e=A.size()-1;
		
			while(s<e) {
				
				//swap the array first and last elements and increment s and decrement e
				
				int temp = B.get(s);
                B.set(s, A.get(e));
				B.set(e, temp);
				
                s++;
                e--;
			}
				
		return B;	
	}
    

}
