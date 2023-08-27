package com.swaroop.dsa.harshasir.Strings2;

/*

Problem Description
        Determine the "GOOD"ness of a given string A, where the "GOOD"ness is defined by the length of the longest substring that contains no repeating characters. The greater the length of this unique-character substring, the higher the "GOOD"ness of the string.

        Your task is to return an integer representing the "GOOD"ness of string A.

        Note: The solution should be achieved in O(N) time complexity, where N is the length of the string.



        Problem Constraints
        1 <= size(A) <= 106

        String consists of lowerCase,upperCase characters and digits are also present in the string A.



        Input Format
        Single Argument representing string A.



        Output Format
        Return an integer denoting the maximum possible length of substring without repeating characters.



        Example Input
        Input 1:

        A = "abcabcbb"
        Input 2:

        A = "AaaA"


        Example Output
        Output 1:

        3
        Output 2:

        2

*/

import java.util.HashSet;

/*To solve this problem in O(N) time complexity, you can use the sliding window technique along with a hash set to keep track of the characters in the current substring.
        Here's the Java code to implement this solution:*/
public class LongestSubstringWithoutRepeat {
    public static int lengthOfLongestSubstring(String A) {
        int n = A.length();
        int maxLength = 0;
        int left = 0, right = 0;
        HashSet<Character> charSet = new HashSet<>();

        while (right < n) {
            if (!charSet.contains(A.charAt(right))) {
                charSet.add(A.charAt(right));
                maxLength = Math.max(maxLength, charSet.size());
                right++;
            } else {
                charSet.remove(A.charAt(left));
                left++;
            }
        }

        return maxLength;
    }

    public static void main(String[] args) {
       // Solution solution = new Solution();
        String A1 = "abcabcbb";
        String A2 = "AaaA";
        System.out.println(lengthOfLongestSubstring(A1)); // Output: 3
        System.out.println(lengthOfLongestSubstring(A2)); // Output: 2
    }
}
