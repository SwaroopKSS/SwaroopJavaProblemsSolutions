package com.swaroop.dsa.harshasir.class6.PrefixSumAndSubArrays;

import java.util.ArrayList;

public class SuffixMaximum {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(19);
        input.add(14);
        input.add(21);
        input.add(17);

        System.out.println(solve(input));


    }


    public static ArrayList<Integer> solve(ArrayList<Integer> A) {

        ArrayList<Integer> response = new ArrayList<>(A);
        response.set(A.size()-1,A.get(A.size()-1));

        for(int i=A.size()-2;i>=0;i--){
            if(A.get(i)>response.get(i+1)){
                response.set(i,A.get(i));
            }else{
                response.set(i,response.get(i+1));
            }
        }
        return response;
    }

}
