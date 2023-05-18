package com.swaroop.dsa.harshasir.class6.SubArrays;

import java.util.ArrayList;

public class SumOfAllSubArrays {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();

        input.add(1);
        input.add(2);
        input.add(3);


        System.out.println(subarraySum(input));


    }

    public static Long subarraySum(ArrayList<Integer> A) {

        Long sum =0l;

        for(int i=0;i<A.size();i++){

            sum = sum +  ( (long) A.get(i) * (i+1) * (A.size()-i) );

            // formula, sum + = A[i] * (i+1) * (n-i)
        }

        return sum;
    }



}
