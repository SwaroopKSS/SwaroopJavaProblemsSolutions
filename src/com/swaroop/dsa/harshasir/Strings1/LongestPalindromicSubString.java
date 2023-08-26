package com.swaroop.dsa.harshasir.Strings1;

public class LongestPalindromicSubString {

    public static void main(String[] args) {

        String A1 = "aaaabaaa";
        System.out.println(longestPalindrome(A1)); // Output: "aaabaaa"

        String A2 = "abba";
        System.out.println(longestPalindrome(A2)); // Output: "abba"
    }

    public static String longestPalindrome(String A) {
        if (A == null || A.length() == 0) {
            return "";
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < A.length(); i++) {
            int len1 = expandAroundCenter(A, i, i); // Odd length palindrome
            int len2 = expandAroundCenter(A, i, i + 1); // Even length palindrome

            int len = Math.max(len1, len2);

            if (len > end - start+1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return A.substring(start, end + 1);
    }

        private static int expandAroundCenter(String s, int left, int right) {
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            return right - left - 1;
        }
}


