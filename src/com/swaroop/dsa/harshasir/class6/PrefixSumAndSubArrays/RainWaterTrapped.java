package com.swaroop.dsa.harshasir.class6.PrefixSumAndSubArrays;

import java.util.ArrayList;
import java.util.List;

public class RainWaterTrapped {

    public static int trap(final List<Integer> A) {

       // [0,1,0,2,1,0,1,3,2,1,2,1]

        ArrayList<Integer> leftMax = new ArrayList<>();
        leftMax.add(A.get(0));
        for(int i=1;i<A.size();i++){
            if(A.get(i)>leftMax.get(i-1)){
                leftMax.add(A.get(i));
            }else{
                leftMax.add(leftMax.get(i-1));
            }
        }

        ArrayList<Integer> rightMax = new ArrayList<>(A);
        rightMax.set(A.size()-1,A.get(A.size()-1));

        for(int i=A.size()-2;i>=0;i--){
            if(A.get(i)>rightMax.get(i+1)){
                rightMax.set(i,A.get(i));
            }else{
                rightMax.set(i,rightMax.get(i+1));
            }
        }

        // finding rain water units by traversing each house
        // formula is
        // min (leftMax(0,i-1),rightMax(i+1,n-1) - height of building
        // the above formula failed


        // working formula is

        //min (leftMax(i),rightMax(i) - height of building

        int rainWaterTotal = 0;

        for(int i=1;i<A.size()-1;i++){

            rainWaterTotal = rainWaterTotal + (Math.min(leftMax.get(i),rightMax.get(i)) - A.get(i));

        }



      return rainWaterTotal;

    }




}
