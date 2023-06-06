package com.swaroop.dsa.harshasir.Hashing1;

import com.swaroop.dsa.soumyasir.class1.IntroToProblemSolving.ArrayRotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommonElements {


    public static ArrayList<Integer> solve(ArrayList<Integer> A, ArrayList<Integer> B) {
        // Just write your code below to complete the function. Required input is available to you as the function arguments.
        // Do not print the result or any output. Just return the result via this function.

        ArrayList<Integer> response = new ArrayList<>();

        HashMap<Integer, Integer> fqMapA = getFrequencyMap(A);

        HashMap<Integer, Integer> fqMapB = getFrequencyMap(B);



         for(Map.Entry<Integer,Integer>  entry : fqMapA.entrySet()){

             Integer key = entry.getKey();


             if(fqMapA.get(B.get(key)) == fqMapB.get(B.get(key))){

                 for(int i=0;i<fqMapA.get(key);i++){
                     response.add(key);
                 }

             }
             if(fqMapA.get(B.get(key)) < fqMapB.get(B.get(key))){

                 for(int i=0;i<fqMapA.get(key);i++){
                     response.add(key);
                 }

             }else{
                 for(int i=0;i<fqMapB.get(key);i++){
                     response.add(key);
                 }
             }

         }
       return response;
    }
    public static HashMap<Integer, Integer> getFrequencyMap(ArrayList<Integer> A) {
        HashMap<Integer, Integer> hMap = new HashMap<>();

        for (int i = 0; i < A.size(); i++) {

            if (hMap.containsKey(A.get(i))) {
                hMap.put(A.get(i), hMap.get(A.get(i)) + 1);
            } else {
                hMap.put(A.get(i), 1);
            }
        }
        return hMap;
    }
}
