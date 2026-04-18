package com.swaroop.DataStructuresCheatCode;

/**
 * STRING ALGORITHMS - TEXT PROCESSING
 * 
 * 💡 KEY TECHNIQUES:
 * 1. Pattern Matching (KMP, Boyer-Moore)
 * 2. Anagrams & Palindromes
 * 3. Longest Substring/Subsequence
 * 4. Word Ladder
 * 5. Regular Expressions
 */

public class StringAlgorithms {
    
    // ========================================
    // 1. KMP (KNUTH-MORRIS-PRATT)
    // ========================================
    
    /**
     * KMP Pattern Matching
     * Find first occurrence of pattern in text
     * 
     * Time: O(n + m), Space: O(m)
     * Better than brute force O(n*m)
     */
    public static int kmpSearch(String text, String pattern) {
        int[] lps = buildLPS(pattern);
        int i = 0, j = 0;
        
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }
            
            if (j == pattern.length()) {
                return i - j;  // Pattern found
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        
        return -1;  // Not found
    }
    
    private static int[] buildLPS(String pattern) {
        int n = pattern.length();
        int[] lps = new int[n];
        int len = 0;
        int i = 1;
        
        while (i < n) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        
        return lps;
    }
    
    // ========================================
    // 2. PALINDROME PROBLEMS
    // ========================================
    
    /**
     * Check if string is palindrome
     */
    public static boolean isPalindrome(String s) {
        String clean = s.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0, right = clean.length() - 1;
        
        while (left < right) {
            if (clean.charAt(left) != clean.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    /**
     * Longest Palindromic Substring
     * 
     * Approach: Expand around center
     * Time: O(n²), Space: O(1)
     */
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        
        int maxLen = 0;
        String result = "";
        
        for (int i = 0; i < s.length(); i++) {
            // Odd length palindrome (center is single char)
            String p1 = expandAroundCenter(s, i, i);
            if (p1.length() > maxLen) {
                maxLen = p1.length();
                result = p1;
            }
            
            // Even length palindrome (center is between two chars)
            String p2 = expandAroundCenter(s, i, i + 1);
            if (p2.length() > maxLen) {
                maxLen = p2.length();
                result = p2;
            }
        }
        
        return result;
    }
    
    private static String expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }
    
    // ========================================
    // 3. ANAGRAMS
    // ========================================
    
    /**
     * Check if two strings are anagrams
     * 
     * Time: O(n log n) sorting or O(n) with count
     */
    public static boolean areAnagrams(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        
        java.util.Arrays.sort(arr1);
        java.util.Arrays.sort(arr2);
        
        return java.util.Arrays.equals(arr1, arr2);
    }
    
    /**
     * Find all anagrams of pattern in text
     * Time: O(n)
     */
    public static java.util.List<Integer> findAnagrams(String text, String pattern) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        if (pattern.length() > text.length()) return result;
        
        int[] patternCount = new int[26];
        int[] windowCount = new int[26];
        
        for (char c : pattern.toCharArray()) {
            patternCount[c - 'a']++;
        }
        
        for (int i = 0; i < text.length(); i++) {
            windowCount[text.charAt(i) - 'a']++;
            
            if (i >= pattern.length()) {
                windowCount[text.charAt(i - pattern.length()) - 'a']--;
            }
            
            if (java.util.Arrays.equals(patternCount, windowCount)) {
                result.add(i - pattern.length() + 1);
            }
        }
        
        return result;
    }
    
    // ========================================
    // 4. LONGEST SUBSTRING WITHOUT REPEATING
    // ========================================
    
    /**
     * Longest Substring Without Repeating Characters
     * Already in HashingTechniques.java but included here
     * 
     * Time: O(n), Space: O(min(n, charset))
     */
    public static int lengthOfLongestSubstring(String s) {
        java.util.Map<Character, Integer> charIndex = new java.util.HashMap<>();
        int maxLen = 0;
        int left = 0;
        
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            
            if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
                left = charIndex.get(c) + 1;
            }
            
            charIndex.put(c, right);
            maxLen = Math.max(maxLen, right - left + 1);
        }
        
        return maxLen;
    }
    
    // ========================================
    // 5. VALID PARENTHESES & SEQUENCES
    // ========================================
    
    /**
     * Check if parentheses are valid
     * Already in StackBasics.java
     */
    public static boolean isValidParentheses(String s) {
        java.util.Stack<Character> stack = new java.util.Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (!isMatching(top, c)) return false;
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean isMatching(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
    
    // ========================================
    // 6. WORD LADDER
    // ========================================
    
    /**
     * Word Ladder - Shortest path from start to end word
     * Can only change one letter at a time
     * All intermediate words must be in wordList
     * 
     * Time: O(n * l² * 26) where n = word count, l = word length
     */
    public static int ladderLength(String beginWord, String endWord, 
                                   java.util.List<String> wordList) {
        java.util.Set<String> wordSet = new java.util.HashSet<>(wordList);
        if (!wordSet.contains(endWord)) return 0;
        
        java.util.Queue<String> queue = new java.util.LinkedList<>();
        queue.offer(beginWord);
        int level = 1;
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                String current = queue.poll();
                
                if (current.equals(endWord)) {
                    return level;
                }
                
                // Try changing each letter
                for (int j = 0; j < current.length(); j++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == current.charAt(j)) continue;
                        
                        String neighbor = current.substring(0, j) + c + current.substring(j + 1);
                        
                        if (wordSet.contains(neighbor)) {
                            queue.offer(neighbor);
                            wordSet.remove(neighbor);  // Avoid revisiting
                        }
                    }
                }
            }
            level++;
        }
        
        return 0;
    }
    
    // ========================================
    // MAIN - Test String Algorithms
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== STRING ALGORITHMS ===\n");
        
        // 1. KMP Search
        System.out.println("1. KMP PATTERN MATCHING");
        System.out.println("'ABABDABACDABABCABAB' search 'ABABCABAB': " + 
                          kmpSearch("ABABDABACDABABCABAB", "ABABCABAB") + "\n");
        
        // 2. Palindrome
        System.out.println("2. PALINDROME");
        System.out.println("'A man, a plan, a canal: Panama' is palindrome: " + 
                          isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("Longest in 'babad': " + longestPalindrome("babad") + "\n");
        
        // 3. Anagrams
        System.out.println("3. ANAGRAMS");
        System.out.println("'listen' and 'silent' are anagrams: " + 
                          areAnagrams("listen", "silent") + "\n");
        
        // 4. Longest Substring
        System.out.println("4. LONGEST SUBSTRING WITHOUT REPEATING");
        System.out.println("'abcabcbb': " + lengthOfLongestSubstring("abcabcbb") + "\n");
        
        // 5. Valid Parentheses
        System.out.println("5. VALID PARENTHESES");
        System.out.println("'()[]{}': " + isValidParentheses("()[]{}"));
        System.out.println("'([)]': " + isValidParentheses("([)]") + "\n");
        
        // 6. Word Ladder
        System.out.println("6. WORD LADDER");
        java.util.List<String> words = java.util.Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
        System.out.println("'hit' to 'cog' distance: " + ladderLength("hit", "cog", words));
    }
}
