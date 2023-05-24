package com.swaroop.dsa.harshasir.PrefixSum;


import java.util.ArrayList;

//Equilibrium index of an array
public class EquilibriumIndex {

    public static void main(String[] args) {

        ArrayList<Integer> input = new ArrayList<>();
        input.add(1);
        input.add(2);
        input.add(3);
        input.add(4);
        input.add(5);

        System.out.println(solve(input));

    }

    public static int solve(ArrayList<Integer> A) {

        ArrayList<Long> ps = new ArrayList<>();

        ps.add(Long.valueOf(A.get(0)));


        for(int i=1;i<A.size();i++){

            ps.add(Long.valueOf(ps.get(i-1) + A.get(i) ));

        }
// above logic is to form prefix sum array


        int count = 0;
        int n  = A.size();
        int minIndex=-1;

        for(int i=0;i<n;i++){

            long leftSum = 0;
            long rightSum = 0;

            if(i==0){
                leftSum = 0;
            }else{
                leftSum = ps.get(i-1);
            }
            if(i==n-1){
                rightSum = 0;
            }else{
                rightSum = ps.get(n-1) - ps.get(i);
            }

            if(leftSum == rightSum){
                count++;
                minIndex=i;
                return i;
            }

        }

        if(count==0){
            return -1;
        }else{
            return minIndex;
        }
    }

}
