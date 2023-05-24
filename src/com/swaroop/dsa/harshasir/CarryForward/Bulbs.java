package com.swaroop.dsa.harshasir.CarryForward;

/*

Problem Description
        A wire connects N light bulbs.

        Each bulb has a switch associated with it; however, due to faulty wiring, a switch also changes the state of all the bulbs to the right of the current bulb.

        Given an initial state of all bulbs, find the minimum number of switches you have to press to turn on all the bulbs.

        You can press the same switch multiple times.

        Note: 0 represents the bulb is off and 1 represents the bulb is on.



        Problem Constraints
        0 <= N <= 5×105
        0 <= A[i] <= 1



        Input Format
        The first and the only argument contains an integer array A, of size N.



        Output Format
        Return an integer representing the minimum number of switches required.

*/

import java.util.ArrayList;

public class Bulbs {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(0);
        input.add(1);
        input.add(0);
        input.add(1);
        input.add(1);

        System.out.println(bulbs(input));


    }

    public static int bulbs(ArrayList<Integer> A) {

        int count=0;

    for(int i=0;i<A.size();i++){

      if(A.get(i)==0 && count%2==0){
          count++;
      }
      if(A.get(i)==1 && count%2==1){
          count++;
      }

    }
        return count;
    }

}
