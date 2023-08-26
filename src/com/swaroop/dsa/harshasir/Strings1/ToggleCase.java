package com.swaroop.dsa.harshasir.Strings1;

/*

Problem Description
        You are given a character string A having length N, consisting of only lowercase and uppercase latin letters.

        You have to toggle case of each character of string A. For e.g 'A' is changed to 'a', 'e' is changed to 'E', etc.



        Problem Constraints
        1 <= N <= 105

        A[i] ∈ ['a'-'z', 'A'-'Z']



        Input Format
        First and only argument is a character string A.



        Output Format
        Return a character string.



        Example Input
        Input 1:

        A = "Hello"
        Input 2:

        A = "tHiSiSaStRiNg"


        Example Output
        Output 1:

        hELLO
        Output 2:

        ThIsIsAsTrInG

*/
public class ToggleCase {
    public String solve(String A) {
        StringBuilder result = new StringBuilder();

        for (char c : A.toCharArray()) {
            if (Character.isUpperCase(c)) {
                result.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
