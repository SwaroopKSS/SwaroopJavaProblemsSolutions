package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.AdvancedDS;

import java.util.*;

/**
 * TRIE / PREFIX TREE - LEETCODE PROBLEMS
 * 
 * Problems covered:
 * 1. LeetCode 208: Implement Trie (Prefix Tree)
 * 2. LeetCode 212: Word Search II
 * 3. LeetCode 720: Longest Word in Dictionary
 * 4. LeetCode 642: Design Search Autocomplete System
 */

public class TrieProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 208 - Implement Trie
    // ========================================
    
    /**
     * LeetCode 208: Implement Trie (Prefix Tree)
     * 
     * Implement a trie with insert, search, and startsWith methods.
     * 
     * Example:
     * Trie trie = new Trie();
     * trie.insert("apple");
     * trie.search("apple");   // true
     * trie.search("app");     // false
     * trie.startsWith("app"); // true
     * trie.insert("app");
     * trie.search("app");     // true
     * 
     * Time: Insert O(m), Search O(m), StartsWith O(m) where m = word length
     * Space: O(ALPHABET_SIZE * N)
     */
    public static class Trie {
        private TrieNode root;
        
        private static class TrieNode {
            TrieNode[] children = new TrieNode[26];
            boolean isWord = false;
        }
        
        public Trie() {
            root = new TrieNode();
        }
        
        public void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isWord = true;
        }
        
        public boolean search(String word) {
            TrieNode node = find(word);
            return node != null && node.isWord;
        }
        
        public boolean startsWith(String prefix) {
            return find(prefix) != null;
        }
        
        private TrieNode find(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    return null;
                }
                node = node.children[idx];
            }
            return node;
        }
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 212 - Word Search II
    // ========================================
    
    /**
     * LeetCode 212: Word Search II
     * 
     * Given a 2D board and a list of words, find all words in the board.
     * Each word must be constructed from letters in sequential adjacent cells
     * (horizontally or vertically adjacent, not diagonal).
     * 
     * Example:
     * board = [["o","a","a"],["e","t","a"],["t","a","t"]]
     * words = ["oath","pea","eat","rain"]
     * Output: ["eat","oath"]
     * 
     * Time: O(m*n * 4^L) where m,n = board size, L = max word length
     * Space: O(sum of word lengths) for Trie
     */
    public static List<String> findWords(char[][] board, String[] words) {
        Set<String> result = new HashSet<>();
        Trie trie = new Trie();
        
        // Build trie from words
        for (String word : words) {
            trie.insert(word);
        }
        
        // Search in board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j, trie.root, "", result);
            }
        }
        
        return new ArrayList<>(result);
    }
    
    private static void dfs(char[][] board, int i, int j, 
                           Trie.TrieNode node, String path, Set<String> result) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) {
            return;
        }
        
        char c = board[i][j];
        if (c == '#') return; // Already visited
        
        int idx = c - 'a';
        if (node.children[idx] == null) return; // Not in trie
        
        Trie.TrieNode child = node.children[idx];
        String newPath = path + c;
        
        if (child.isWord) {
            result.add(newPath);
        }
        
        // Mark visited
        board[i][j] = '#';
        
        // Explore 4 directions
        dfs(board, i+1, j, child, newPath, result);
        dfs(board, i-1, j, child, newPath, result);
        dfs(board, i, j+1, child, newPath, result);
        dfs(board, i, j-1, child, newPath, result);
        
        // Backtrack
        board[i][j] = c;
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 720 - Longest Word in Dictionary
    // ========================================
    
    /**
     * LeetCode 720: Longest Word in Dictionary
     * 
     * Given an array of strings words, find the longest word that can be built
     * one character at a time by other words in the array.
     * A word must be built from left to right.
     * 
     * Example:
     * Input: words = ["w","wo","wor","world","worlda"]
     * Output: "world"
     * Explanation: "world" is built as: "w" -> "wo" -> "wor" -> "world"
     * 
     * Time: O(sum of word lengths)
     * Space: O(sum of word lengths)
     */
    public static String longestWord(String[] words) {
        Set<String> validWords = new HashSet<>();
        Arrays.sort(words, (a, b) -> {
            if (a.length() != b.length()) {
                return b.length() - a.length();
            }
            return a.compareTo(b);
        });
        
        String result = "";
        
        for (String word : words) {
            // Check if word can be built from other words
            if (word.length() == 1 || validWords.contains(word.substring(0, word.length() - 1))) {
                validWords.add(word);
                result = word; // Since sorted by length desc, first match is longest
            }
        }
        
        return result;
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 642 - Design Search Autocomplete System
    // ========================================
    
    /**
     * LeetCode 642: Design Search Autocomplete System
     * 
     * Design a search autocomplete system for a search engine.
     * Users may input a sentence. For each character typed, return top 3 most
     * frequently searched sentences that have that prefix.
     * 
     * Example:
     * AutocompleteSystem system = new AutocompleteSystem(
     *     ["mobile phone","mango","manzana"], [5, 2, 3]);
     * system.input('m') -> ["mobile phone","manzana","mango"]
     * system.input('a') -> ["mango","manzana"]
     * system.input('n') -> ["mango","manzana"]
     * system.input('g') -> ["mango"]
     * system.input('o') -> ["mango"]
     * system.input('#') -> [] (store the sentence)
     */
    public static class AutocompleteSystem {
        private TrieNode root;
        private String currentSearch;
        
        private class TrieNode {
            Map<Character, TrieNode> children = new HashMap<>();
            Map<String, Integer> frequency = new HashMap<>();
        }
        
        public AutocompleteSystem(String[] sentences, int[] times) {
            root = new TrieNode();
            currentSearch = "";
            
            // Insert all sentences into trie
            for (int i = 0; i < sentences.length; i++) {
                insert(sentences[i], times[i]);
            }
        }
        
        private void insert(String sentence, int time) {
            TrieNode node = root;
            for (char c : sentence.toCharArray()) {
                node.children.putIfAbsent(c, new TrieNode());
                node = node.children.get(c);
                node.frequency.put(sentence, node.frequency.getOrDefault(sentence, 0) + time);
            }
        }
        
        public List<String> input(char c) {
            if (c == '#') {
                insert(currentSearch, 1);
                currentSearch = "";
                return new ArrayList<>();
            }
            
            currentSearch += c;
            TrieNode node = root;
            
            // Navigate to the node representing current search
            for (char ch : currentSearch.toCharArray()) {
                if (!node.children.containsKey(ch)) {
                    return new ArrayList<>();
                }
                node = node.children.get(ch);
            }
            
            // Get top 3 sentences by frequency
            List<String> result = new ArrayList<>(node.frequency.keySet());
            result.sort((a, b) -> {
                int freqDiff = node.frequency.get(b) - node.frequency.get(a);
                if (freqDiff != 0) return freqDiff;
                return a.compareTo(b);
            });
            
            return result.subList(0, Math.min(3, result.size()));
        }
    }
    
    // ========================================
    // MAIN - Test Trie Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== TRIE LEETCODE PROBLEMS ===\n");
        
        // Problem 1: Implement Trie
        System.out.println("1. LeetCode 208: Implement Trie");
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println("search(apple): " + trie.search("apple"));
        System.out.println("search(app): " + trie.search("app"));
        System.out.println("startsWith(app): " + trie.startsWith("app"));
        trie.insert("app");
        System.out.println("search(app): " + trie.search("app") + "\n");
        
        // Problem 3: Longest Word in Dictionary
        System.out.println("3. LeetCode 720: Longest Word in Dictionary");
        String[] words = {"w","wo","wor","world","worlda"};
        System.out.println("words: " + Arrays.toString(words));
        System.out.println("Result: " + longestWord(words) + "\n");
        
        // Problem 4: Autocomplete System
        System.out.println("4. LeetCode 642: Design Search Autocomplete System");
        String[] sentences = {"mobile phone","mango","manzana"};
        int[] times = {5, 2, 3};
        AutocompleteSystem system = new AutocompleteSystem(sentences, times);
        System.out.println("input('m'): " + system.input('m'));
        System.out.println("input('a'): " + system.input('a'));
        System.out.println("input('n'): " + system.input('n'));
        System.out.println("input('g'): " + system.input('g'));
        System.out.println("input('o'): " + system.input('o'));
        System.out.println("input('#'): " + system.input('#'));
    }
}
