package com.swaroop.dsa.harshasir.PrefixSum;

import java.util.ArrayList;

public class EvenNumbersInRange {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);

        ArrayList<ArrayList<Integer>> queryArray = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> query1 = new ArrayList<>();

        query1.add(0);
        query1.add(3);

        ArrayList<Integer> query2 = new ArrayList<>();

        query2.add(1);
        query2.add(2);

        queryArray.add(query1);
        queryArray.add(query2);


        System.out.println(solve(input,queryArray));


    }
    public static ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<ArrayList<Integer>> B) {

        ArrayList<Integer> response = new ArrayList<>();

        ArrayList<Integer> evenArray = new ArrayList<>();

        for(int i=0;i<A.size();i++){

            if(A.get(i) % 2 == 0){
                evenArray.add(1);
            }else{
                evenArray.add(0);
            }

        }

        // above one is even array from original array  even replaced by 1 and rest 0

        ArrayList<Integer> ps = new ArrayList<>();

        ps.add((evenArray.get(0)));

        for(int i=1;i<evenArray.size();i++){

            ps.add((ps.get(i-1) + evenArray.get(i) ));

        }

           // above logic is to form prefix sum array


        for(int i=0;i<B.size();i++){

            int sum = 0;

            //even numbers in range below

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
