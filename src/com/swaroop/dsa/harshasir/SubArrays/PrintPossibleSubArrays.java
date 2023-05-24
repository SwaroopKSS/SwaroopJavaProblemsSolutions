package com.swaroop.dsa.harshasir.SubArrays;

import java.util.Scanner;

public class PrintPossibleSubArrays {

	public static void main(String[] args) {
		// YOUR CODE GOES HERE
        // Please take input and print output to standard input/output (stdin/stdout)
        // DO NOT USE ARGUMENTS FOR INPUTS
        // E.g. 'Scanner' for input & 'System.out' for output
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] array = new int[n];
		for(int i=0;i<array.length;i++) {
			array[i]=sc.nextInt();
		}
		
		for(int i=0;i<array.length;i++) {
			
			for(int j=i;j<array.length;j++) {
				
				
				for(int k=i;k<=j;k++) {
					System.out.print(array[k]);
					if(!(i==j&&array[array.length-1]==array[k])) {
					System.out.print(" ");
					}
				}
				
				System.out.println();
			}
			
		}

	}

}
