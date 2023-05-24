package com.swaroop.dsa.harshasir.PrefixSumAndSubArrays;

import java.util.List;
public class MaxSumContiguousSubArray {

    public int maxSubArray(final List<Integer> A) {

        //kadane algorithm

        int maxSum = Integer.MIN_VALUE;
        int currentSum = 0;

        for(int i=0;i<A.size();i++) {

            currentSum = currentSum + A.get(i);

        if(currentSum > maxSum ){

            maxSum = currentSum;

        }
        if(currentSum < 0){
            currentSum = 0;
        }

        }

     return maxSum;
    }
}
