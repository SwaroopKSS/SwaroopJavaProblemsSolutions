package com.swaroop.dsa.harshasir.TwoPointers;

public class ContainerWithMostWater {

    public int maxArea(int[] A) {
        int max_area = 0; //for returning maximum area
        int i = 0; // we are pointing our first pointer at 0th index
        int j = A.length - 1; // we are pointing our last pointer at A.length-1(last) index
        while (i < j) { // we will stop our condition when i will be greater than j
            int volume = 0; // for counting volume of current space
            if (A[i] < A[j]) {
                volume = A[i] * (j - i);
                i++;
            } else {
                volume = A[j] * (j - i);
                j--;
            }

            if (volume > max_area) {
                max_area = volume;
            }
        }

        return max_area;
    }
}
