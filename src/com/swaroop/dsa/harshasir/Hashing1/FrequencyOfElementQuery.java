package com.swaroop.dsa.harshasir.Hashing1;

import java.util.ArrayList;
import java.util.HashMap;

/*

 Frequency of element query Unsolved Hint bulb icon Stuck somewhere?

         Problem Description Given an array A.

         You have some integers given in the array B. For the i-th number,

         find the frequency of B[i] in the array A and return a list containing all the frequencies.


Problem Constraints
1 <= |A| <= 105
1 <= |B| <= 105
1 <= A[i] <= 105
1 <= B[i] <= 105


Input Format
First argument A is an array of integers.
Second argument B is an array of integers denoting the queries.


Output Format
Return an array of integers containing frequency of the each element in B.

*/
public class FrequencyOfElementQuery {


 //A=[6,3,3,6,7,8,7,3,7]

 //B=[10,9,8]

 public static void main(String[] args) {

  ArrayList<Integer> A = new ArrayList<>();

  A.add(6);
  A.add(3);
  A.add(3);
  A.add(6);
  A.add(7);
  A.add(8);
  A.add(7);
  A.add(3);
  A.add(7);


  ArrayList<Integer> B = new ArrayList<>();

  B.add(10);
  B.add(9);
  B.add(8);

  System.out.println(solve(A,B));

 }

  public static ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B) {




   ArrayList<Integer> response = new ArrayList<>();

   HashMap<Integer,Integer> map = new HashMap<>();

   for(int i=0;i<A.size();i++){

    if(map.containsKey(A.get(i))){

     map.put(A.get(i),map.get(A.get(i))+1);

    }else{
     map.put(A.get(i),1);
    }

   }

   for(int q=0;q<B.size();q++){

    if(map.containsKey(B.get(q))){
     response.add(map.get(B.get(q)));
    }else{
     response.add(0);
    }

   }

   return response;
  }

}
