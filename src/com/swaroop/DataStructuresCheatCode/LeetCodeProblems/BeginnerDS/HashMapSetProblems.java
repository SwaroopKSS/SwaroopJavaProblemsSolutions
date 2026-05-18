package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.BeginnerDS;

import java.util.*;

/**
 * HASH MAP & SET PROBLEMS - BEGINNER LEVEL
 * 
 * Problems covered:
 * 1. LeetCode 383: Ransom Note
 * 2. LeetCode 349: Intersection of Two Arrays
 * 3. LeetCode 202: Happy Number
 * 4. LeetCode 205: Isomorphic Strings
 * 5. LeetCode 290: Word Pattern
 */

public class HashMapSetProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 383 - Ransom Note
    // ========================================
    
    /**
     * LeetCode 383: Ransom Note
     * 
     * Given two strings ransomNote and magazine, return true if ransomNote
     * can be constructed by using the letters from magazine.
     * Each letter in magazine can only be used once in ransomNote.
     * 
     * Example:
     * Input: ransomNote = "a", magazine = "b"
     * Output: false
     * 
     * Input: ransomNote = "aa", magazine = "ab"
     * Output: false
     * 
     * Input: ransomNote = "aa", magazine = "aab"
     * Output: true
     * 
     * Time: O(m + n)
     * Space: O(1)
     */
    public static boolean canConstruct(String ransomNote, String magazine) {
        int[] count = new int[26];
        
        // Count characters in magazine
        for (char c : magazine.toCharArray()) {
            count[c - 'a']++;
        }
        
        // Check if ransom note can be constructed
        for (char c : ransomNote.toCharArray()) {
            if (count[c - 'a'] == 0) {
                return false;
            }
            count[c - 'a']--;
        }
        
        return true;
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 349 - Intersection of Two Arrays
    // ========================================
    
    /**
     * LeetCode 349: Intersection of Two Arrays
     * 
     * Given two integer arrays nums1 and nums2, return an array of their
     * intersection. Each element in the result must be unique and you may
     * return the result in any order.
     * 
     * Example:
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2]
     * 
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [4,9] or [9,4]
     * 
     * Time: O(m + n)
     * Space: O(min(m, n))
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
        // Convert smaller array to set
        Set<Integer> set1 = new HashSet<>();
        int[] shorter = nums1.length < nums2.length ? nums1 : nums2;
        int[] longer = nums1.length < nums2.length ? nums2 : nums1;
        
        for (int num : shorter) {
            set1.add(num);
        }
        
        Set<Integer> result = new HashSet<>();
        for (int num : longer) {
            if (set1.contains(num)) {
                result.add(num);
            }
        }
        
        int[] res = new int[result.size()];
        int i = 0;
        for (int num : result) {
            res[i++] = num;
        }
        
        return res;
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 202 - Happy Number
    // ========================================
    
    /**
     * LeetCode 202: Happy Number
     * 
     * Write an algorithm to determine if a number n is happy.
     * A happy number is defined by the following process:
     * 1. Start with any positive integer
     * 2. Replace the number by the sum of the squares of its digits
     * 3. Repeat the process until the number equals 1 or loops infinitely
     * 
     * Example:
     * Input: n = 19
     * Output: true
     * Explanation: 1² + 9² = 82 → 8² + 2² = 68 → 6² + 8² = 100 → 1² = 1
     * 
     * Input: n = 2
     * Output: false
     * 
     * Time: O(log n)
     * Space: O(1)
     */
    public static boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        
        while (n != 1) {
            if (seen.contains(n)) {
                return false; // Cycle detected
            }
            
            seen.add(n);
            n = getSumOfSquares(n);
        }
        
        return true;
    }
    
    private static int getSumOfSquares(int n) {
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        return sum;
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 205 - Isomorphic Strings
    // ========================================
    
    /**
     * LeetCode 205: Isomorphic Strings
     * 
     * Given two strings s and t, determine if they are isomorphic.
     * Two strings are isomorphic if the characters in s can be replaced to get t.
     * All occurrences of a character must be replaced with another character
     * while preserving the order of characters.
     * 
     * Example:
     * Input: s = "egg", t = "add"
     * Output: true
     * 
     * Input: s = "foo", t = "bar"
     * Output: false
     * 
     * Input: s = "badc", t = "baba"
     * Output: false
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Character> sToT = new HashMap<>();
        Map<Character, Character> tToS = new HashMap<>();
        
        for (int i = 0; i < s.length(); i++) {
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);
            
            if (sToT.containsKey(sChar)) {
                if (sToT.get(sChar) != tChar) {
                    return false;
                }
            } else {
                sToT.put(sChar, tChar);
            }
            
            if (tToS.containsKey(tChar)) {
                if (tToS.get(tChar) != sChar) {
                    return false;
                }
            } else {
                tToS.put(tChar, sChar);
            }
        }
        
        return true;
    }
    
    // ========================================
    // PROBLEM 5: LeetCode 290 - Word Pattern
    // ========================================
    
    /**
     * LeetCode 290: Word Pattern
     * 
     * Given a pattern and a string s, find if s follows the same pattern.
     * Here follow means a full match, such that there is a bijection between
     * a letter in pattern and a non-empty word in s.
     * 
     * Example:
     * Input: pattern = "abba", s = "redbluebluered"
     * Output: true
     * 
     * Input: pattern = "abba", s = "redbluebluegreen"
     * Output: false
     * 
     * Input: pattern = "aaaa", s = "asdasdasdasd"
     * Output: true
     * 
     * Time: O(n + m)
     * Space: O(n + m)
     */
    public static boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        
        if (pattern.length() != words.length) {
            return false;
        }
        
        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();
        
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];
            
            if (charToWord.containsKey(c)) {
                if (!charToWord.get(c).equals(word)) {
                    return false;
                }
            } else {
                charToWord.put(c, word);
            }
            
            if (wordToChar.containsKey(word)) {
                if (wordToChar.get(word) != c) {
                    return false;
                }
            } else {
                wordToChar.put(word, c);
            }
        }
        
        return true;
    }
    
    // ========================================
    // MAIN - Test Hash Map & Set Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== HASH MAP & SET PROBLEMS - BEGINNER ===\n");
        
        // Problem 1: Ransom Note
        System.out.println("1. LeetCode 383: Ransom Note");
        System.out.println("Input: ransomNote = \"a\", magazine = \"b\"");
        System.out.println("Output: " + canConstruct("a", "b"));
        System.out.println("Input: ransomNote = \"aa\", magazine = \"aab\"");
        System.out.println("Output: " + canConstruct("aa", "aab") + "\n");
        
        // Problem 2: Intersection of Two Arrays
        System.out.println("2. LeetCode 349: Intersection of Two Arrays");
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        System.out.println("Input: nums1 = " + Arrays.toString(nums1) + ", nums2 = " + Arrays.toString(nums2));
        System.out.println("Output: " + Arrays.toString(intersection(nums1, nums2)) + "\n");
        
        // Problem 3: Happy Number
        System.out.println("3. LeetCode 202: Happy Number");
        System.out.println("Input: n = 19");
        System.out.println("Output: " + isHappy(19));
        System.out.println("Input: n = 2");
        System.out.println("Output: " + isHappy(2) + "\n");
        
        // Problem 4: Isomorphic Strings
        System.out.println("4. LeetCode 205: Isomorphic Strings");
        System.out.println("Input: s = \"egg\", t = \"add\"");
        System.out.println("Output: " + isIsomorphic("egg", "add"));
        System.out.println("Input: s = \"foo\", t = \"bar\"");
        System.out.println("Output: " + isIsomorphic("foo", "bar") + "\n");
        
        // Problem 5: Word Pattern
        System.out.println("5. LeetCode 290: Word Pattern");
        System.out.println("Input: pattern = \"abba\", s = \"redbluebluered\"");
        System.out.println("Output: " + wordPattern("abba", "redbluebluered"));
        System.out.println("Input: pattern = \"aaaa\", s = \"asdasdasdasd\"");
        System.out.println("Output: " + wordPattern("aaaa", "asdasdasdasd"));
    }
}
