package com.swaroop.dsa.harshasir.Hashing1;


/*

Problem Description
        Given an integer array A of size N, find the first repeating element in it.

        We need to find the element that occurs more than once and whose index of the first occurrence is the smallest.

        If there is no repeating element, return -1.



        Problem Constraints
        1 <= N <= 105

        1 <= A[i] <= 109



        Input Format
        The first and only argument is an integer array A of size N.



        Output Format
        Return an integer denoting the first repeating element.



        Example Input
        Input 1:

        A = [10, 5, 3, 4, 3, 5, 6]
        Input 2:

        A = [6, 10, 5, 4, 9, 120]


        Example Output
        Output 1:

        5
        Output 2:

        -1


        Example Explanation
        Explanation 1:

        5 is the first element that repeats
        Explanation 2:

        There is no repeating element, output -1


*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FirstRepeatingElement {

    public static void main(String[] args) {

        ArrayList<Integer> A = new ArrayList<>();

        //A=[6,10,5,4,9,120]

        A.add(6);
        A.add(10);
        A.add(5);
        A.add(4);
        A.add(9);
        A.add(9);

        System.out.println(solve(A));

    }

    public static int solve(ArrayList<Integer> A) {

        //A=[6,10,5,4,9,120]

        int firstRepeatingElement = -1;

        int currentIndex = -1;

        HashMap<Integer,Integer> hMap = new HashMap<>();

        for(int i=0;i<A.size();i++){

            if(hMap.containsKey(A.get(i))){
                hMap.put(A.get(i),hMap.get(A.get(i))+1);
            }else{
                hMap.put(A.get(i),1);
            }
        }

        for(int i=A.size()-1;i>=0;i--){

            int value = hMap.get(A.get(i));

            if(hMap.get(A.get(i))>1){
                currentIndex = i;
            }

        }

        if(currentIndex<0){
            return -1;
        }

        firstRepeatingElement= A.get(currentIndex);

        return firstRepeatingElement;
    }
}
