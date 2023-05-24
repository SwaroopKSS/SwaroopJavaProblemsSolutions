package com.swaroop.dsa.harshasir.class5.PrefixSum;

import java.util.ArrayList;

public class SumOfEvenIndices {


    public ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<ArrayList<Integer>> B) {

        ArrayList<Integer> response = new ArrayList<>();

        ArrayList<Integer> ps = new ArrayList<>();

        ps.add((A.get(0)));

        for(int i=1;i<A.size();i++){
            if(i%2 == 0){
                ps.add((ps.get(i-1) + A.get(i)));
            }else{
                ps.add(ps.get(i-1));
            }

        }

        // above logic is to form prefix sum array


        for(int i=0;i<B.size();i++){

            int sum = 0;

            //even indices  in range below

            int s =  B.get(i).get(0);
            int e =  B.get(i).get(1);

            if(s == 0){
                sum = ps.get(e);
                response.add(sum);
            }else {
                sum = ps.get(e) - ps.get(s-1);
                response.add(sum);
            }

        }
        return response;

    }

}
