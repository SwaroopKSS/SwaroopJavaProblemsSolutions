package com.swaroop.DataStructuresCheatCode;

/**
 * TRIE (PREFIX TREE) - COMPREHENSIVE GUIDE
 * 
 * 🎯 WHAT IS A TRIE?
 * A tree-like data structure that stores strings efficiently for fast prefix matching.
 * 
 * 💡 KEY INSIGHTS:
 * - Each node has 26 children (for lowercase a-z) or more
 * - Root node is empty (no character)
 * - Each path from root to node represents a prefix
 * - Marked nodes indicate end of a valid word
 * 
 * EXAMPLE:
 * Words: ["apple", "app", "application", "apply"]
 * 
 *        root
 *         |
 *         a
 *        / \
 *       p   (other letters)
 *      /
 *     p (end of "app")
 *    / \
 *   l   y (end of "apply")
 *  /|
 * e i
 * | | (end of "apple" & "application")
 * 
 * 📊 COMPLEXITY:
 * Insert: O(m) where m = word length
 * Search: O(m)
 * Delete: O(m)
 * Space: O(ALPHABET_SIZE * N * M) - can be huge!
 * 
 * 🎓 WHEN TO USE:
 * ✓ Autocomplete/suggestions
 * ✓ Spell checking
 * ✓ IP routing
 * ✓ Dictionary implementations
 * ✓ Word games (Scrabble, Wordle)
 * ✓ Longest common prefix
 * ✓ T9 input (mobile phones)
 * ✓ Search engine suggestions
 */

public class TrieDataStructure {
    
    // ========================================
    // 1. BASIC TRIE IMPLEMENTATION
    // ========================================
    
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];  // 'a' to 'z'
        boolean isEndOfWord = false;
        String word = null;  // Store actual word for easy retrieval
    }
    
    static class Trie {
        TrieNode root;
        
        Trie() {
            root = new TrieNode();
        }
        
        /**
         * Insert a word into trie
         * Time: O(m) where m = word length
         */
        void insert(String word) {
            TrieNode node = root;
            
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                
                node = node.children[idx];
            }
            
            node.isEndOfWord = true;
            node.word = word;  // Store actual word
        }
        
        /**
         * Search for exact word
         * Time: O(m)
         */
        boolean search(String word) {
            TrieNode node = find(word);
            return node != null && node.isEndOfWord;
        }
        
        /**
         * Check if prefix exists (even if not a complete word)
         * Time: O(m)
         */
        boolean startsWith(String prefix) {
            return find(prefix) != null;
        }
        
        /**
         * Find node at end of prefix/word
         */
        private TrieNode find(String prefix) {
            TrieNode node = root;
            
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                
                if (node.children[idx] == null) {
                    return null;
                }
                
                node = node.children[idx];
            }
            
            return node;
        }
        
        /**
         * Delete a word from trie
         * Time: O(m)
         */
        void delete(String word) {
            deleteHelper(root, word, 0);
        }
        
        private boolean deleteHelper(TrieNode node, String word, int idx) {
            if (idx == word.length()) {
                if (!node.isEndOfWord) return false;  // Word doesn't exist
                
                node.isEndOfWord = false;
                node.word = null;
                return true;  // Node has no children, can be deleted
            }
            
            int childIdx = word.charAt(idx) - 'a';
            TrieNode child = node.children[childIdx];
            
            if (child == null) return false;  // Word doesn't exist
            
            boolean shouldDelete = deleteHelper(child, word, idx + 1);
            
            if (shouldDelete) {
                node.children[childIdx] = null;
                // Return true if node is empty (no children, not end of word)
                return !node.isEndOfWord;
            }
            
            return false;
        }
    }
    
    // ========================================
    // 2. AUTOCOMPLETE (Common Interview Qs)
    // ========================================
    
    /**
     * Autocomplete - Find all words with given prefix
     * Time: O(n) where n = total chars in all words with prefix
     */
    static class AutocompleteSystem {
        TrieNode root;
        
        AutocompleteSystem() {
            root = new TrieNode();
        }
        
        void insert(String word) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    node.children[idx] = new TrieNode();
                }
                node = node.children[idx];
            }
            node.isEndOfWord = true;
            node.word = word;
        }
        
        java.util.List<String> autocomplete(String prefix) {
            java.util.List<String> results = new java.util.ArrayList<>();
            TrieNode node = root;
            
            // Navigate to end of prefix
            for (char c : prefix.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) {
                    return results;  // No words with this prefix
                }
                node = node.children[idx];
            }
            
            // DFS to find all words with this prefix
            dfsAutocomplete(node, results);
            return results;
        }
        
        private void dfsAutocomplete(TrieNode node, java.util.List<String> results) {
            if (node.isEndOfWord) {
                results.add(node.word);
            }
            
            for (TrieNode child : node.children) {
                if (child != null) {
                    dfsAutocomplete(child, results);
                }
            }
        }
    }
    
    // ========================================
    // 3. LONGEST COMMON PREFIX
    // ========================================
    
    /**
     * Find longest common prefix in array of strings
     * Time: O(S) where S = sum of all characters
     */
    static String longestCommonPrefix(String[] words) {
        if (words.length == 0) return "";
        
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        
        // Traverse trie, count nodes with only one child
        StringBuilder prefix = new StringBuilder();
        TrieNode node = trie.root;
        
        while (true) {
            int childCount = 0;
            int nextChildIdx = -1;
            
            // Count non-null children
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    childCount++;
                    nextChildIdx = i;
                }
            }
            
            // Stop if:
            // 1. Node is end of a word (divergence point)
            // 2. Multiple children (divergence point)
            // 3. No children (end of trie)
            if (childCount != 1 || node.isEndOfWord) {
                break;
            }
            
            prefix.append((char) ('a' + nextChildIdx));
            node = node.children[nextChildIdx];
        }
        
        return prefix.toString();
    }
    
    // ========================================
    // 4. WORD SEARCH IN BOARD (Interview Classic)
    // ========================================
    
    /**
     * Word Search II - Find all words from word list in 2D board
     * Words can be formed by adjacent cells (not necessarily connected)
     * 
     * Time: O(M*N*4^L) where L = max word length
     * Space: O(L) - call stack
     */
    static class WordSearchII {
        TrieNode trieRoot;
        java.util.List<String> result;
        int[][] dirs = {{0,1}, {0,-1}, {1,0}, {-1,0}};
        
        WordSearchII(String[] words) {
            trieRoot = new TrieNode();
            result = new java.util.ArrayList<>();
            
            // Build trie
            for (String word : words) {
                TrieNode node = trieRoot;
                for (char c : word.toCharArray()) {
                    int idx = c - 'a';
                    if (node.children[idx] == null) {
                        node.children[idx] = new TrieNode();
                    }
                    node = node.children[idx];
                }
                node.isEndOfWord = true;
                node.word = word;
            }
        }
        
        java.util.List<String> findWords(char[][] board) {
            if (board.length == 0) return result;
            
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, i, j, trieRoot);
                }
            }
            
            return result;
        }
        
        private void dfs(char[][] board, int row, int col, TrieNode node) {
            char c = board[row][col];
            int idx = c - 'a';
            
            // Boundary or already visited
            if (c == '#' || node.children[idx] == null) {
                return;
            }
            
            TrieNode nextNode = node.children[idx];
            
            // Found a word
            if (nextNode.isEndOfWord) {
                result.add(nextNode.word);
                nextNode.isEndOfWord = false;  // Avoid duplicates
            }
            
            // Mark as visited
            board[row][col] = '#';
            
            // Explore all 4 directions
            for (int[] dir : dirs) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                if (newRow >= 0 && newRow < board.length &&
                    newCol >= 0 && newCol < board[0].length) {
                    dfs(board, newRow, newCol, nextNode);
                }
            }
            
            // Restore
            board[row][col] = c;
        }
    }
    
    // ========================================
    // 5. REPLACE WORDS
    // ========================================
    
    /**
     * Replace Words - Replace with shortest root from dictionary
     * "a" is root of "apple", "apply"
     * 
     * Time: O(N*L) where N = words, L = word length
     */
    static String replaceWords(java.util.List<String> dictionary, String sentence) {
        Trie trie = new Trie();
        for (String word : dictionary) {
            trie.insert(word);
        }
        
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
            }
            
            // Find shortest root
            String shortest = word;
            
            for (int i = 1; i <= word.length(); i++) {
                String prefix = word.substring(0, i);
                if (trie.search(prefix)) {
                    shortest = prefix;
                    break;
                }
            }
            
            result.append(shortest);
        }
        
        return result.toString();
    }
    
    // ========================================
    // 6. PALINDROME PAIRS (Hard Problem)
    // ========================================
    
    /**
     * Palindrome Pairs - Find pairs of indices that form palindrome when concatenated
     * 
     * Time: O(N*L²) - complex but optimal with Trie
     */
    static java.util.List<int[]> palindromePairs(String[] words) {
        java.util.List<int[]> result = new java.util.ArrayList<>();
        if (words.length == 0) return result;
        
        // Build reverse word mapping
        java.util.Map<String, Integer> wordMap = new java.util.HashMap<>();
        for (int i = 0; i < words.length; i++) {
            wordMap.put(words[i], i);
        }
        
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            
            // Case 1: Empty string paired with palindrome
            if (wordMap.containsKey("") && isPalindrome(word)) {
                if (i != wordMap.get("")) {
                    result.add(new int[]{i, wordMap.get("")});
                    result.add(new int[]{wordMap.get(""), i});
                }
            }
            
            // Case 2: Try all split points
            for (int j = 0; j < word.length(); j++) {
                String left = word.substring(0, j);
                String right = word.substring(j);
                
                // If left is palindrome, right reversed should exist
                String reversedRight = new StringBuilder(right).reverse().toString();
                if (isPalindrome(left) && wordMap.containsKey(reversedRight)) {
                    int idx = wordMap.get(reversedRight);
                    if (idx != i) {
                        result.add(new int[]{idx, i});
                    }
                }
                
                // If right is palindrome, left reversed should exist
                String reversedLeft = new StringBuilder(left).reverse().toString();
                if (isPalindrome(right) && wordMap.containsKey(reversedLeft)) {
                    int idx = wordMap.get(reversedLeft);
                    if (idx != i) {
                        result.add(new int[]{i, idx});
                    }
                }
            }
        }
        
        return result;
    }
    
    private static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // ========================================
    // MAIN - Test Trie
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== TRIE DATA STRUCTURE ===\n");
        
        // 1. Basic Trie
        System.out.println("1. BASIC TRIE OPERATIONS");
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        trie.insert("application");
        System.out.println("Search 'apple': " + trie.search("apple"));
        System.out.println("Search 'app': " + trie.search("app"));
        System.out.println("StartsWith 'ap': " + trie.startsWith("ap"));
        System.out.println("Search 'appl': " + trie.search("appl") + "\n");
        
        // 2. Autocomplete
        System.out.println("2. AUTOCOMPLETE");
        AutocompleteSystem ac = new AutocompleteSystem();
        for (String word : new String[]{"apple", "app", "application", "apply"}) {
            ac.insert(word);
        }
        System.out.println("Words with 'app': " + ac.autocomplete("app") + "\n");
        
        // 3. Longest Common Prefix
        System.out.println("3. LONGEST COMMON PREFIX");
        String[] words = {"apple", "application", "apply"};
        System.out.println("LCP of {apple, application, apply}: '" + 
                          longestCommonPrefix(words) + "'\n");
        
        // 4. Word Search II
        System.out.println("4. WORD SEARCH II");
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        String[] searchWords = {"oath","pea","eat","rain"};
        WordSearchII search = new WordSearchII(searchWords);
        System.out.println("Words found: " + search.findWords(board) + "\n");
        
        // 5. Replace Words
        System.out.println("5. REPLACE WORDS");
        java.util.List<String> dict = java.util.Arrays.asList("cat","bat","rat");
        String sentence = "the cattle was rattled by the battery";
        System.out.println("Original: " + sentence);
        System.out.println("Replaced: " + replaceWords(dict, sentence) + "\n");
        
        // 6. Delete from Trie
        System.out.println("6. DELETE FROM TRIE");
        trie.delete("app");
        System.out.println("After deleting 'app':");
        System.out.println("Search 'app': " + trie.search("app"));
        System.out.println("Search 'apple': " + trie.search("apple"));
    }
}
