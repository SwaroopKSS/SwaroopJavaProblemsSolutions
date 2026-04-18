package com.swaroop.DataStructuresCheatCode;

/**
 * ADVANCED DATA STRUCTURES - EXPERT LEVEL
 * 
 * 💡 ADVANCED CONCEPTS:
 * 1. Union-Find (Disjoint Set Union)
 * 2. Segment Trees (Range queries)
 * 3. Fenwick Trees (Binary Indexed Trees)
 * 4. Trie (Prefix trees)
 * 5. AVL Trees (Self-balancing)
 * 6. Suffix Arrays
 */

public class AdvancedDataStructures {
    
    // ========================================
    // 1. UNION-FIND (DISJOINT SET UNION)
    // ========================================
    
    /**
     * Union-Find Data Structure
     * Fast operations for connected components
     * 
     * Uses: Cycle detection in graphs, connected components, 
     *       Kruskal's algorithm, LCA problems
     */
    public static class UnionFind {
        private int[] parent;
        private int[] rank;
        private int components;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        /**
         * Find with path compression O(α(n)) amortized
         */
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);  // Path compression
            }
            return parent[x];
        }
        
        /**
         * Union by rank O(α(n)) amortized
         */
        public void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            
            if (px == py) return;
            
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
            components--;
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
        
        public int getComponents() {
            return components;
        }
    }
    
    // ========================================
    // 2. SEGMENT TREE
    // ========================================
    
    /**
     * Segment Tree for Range Queries
     * Example: Range Sum Query, Range Max Query
     * 
     * Time: Build O(n), Query O(log n), Update O(log n)
     * Space: O(n)
     */
    public static class SegmentTree {
        private int[] tree;
        private int n;
        
        public SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 0, 0, n - 1);
        }
        
        private void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(arr, 2 * node + 1, start, mid);
                build(arr, 2 * node + 2, mid + 1, end);
                tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
            }
        }
        
        /**
         * Range Sum Query
         */
        public int rangeQuery(int left, int right) {
            return query(0, 0, n - 1, left, right);
        }
        
        private int query(int node, int start, int end, int left, int right) {
            if (right < start || end < left) {
                return 0;  // Out of range
            }
            if (left <= start && end <= right) {
                return tree[node];  // Completely within range
            }
            int mid = (start + end) / 2;
            int p1 = query(2 * node + 1, start, mid, left, right);
            int p2 = query(2 * node + 2, mid + 1, end, left, right);
            return p1 + p2;
        }
        
        /**
         * Point Update
         */
        public void update(int idx, int val) {
            update(0, 0, n - 1, idx, val);
        }
        
        private void update(int node, int start, int end, int idx, int val) {
            if (start == end) {
                tree[node] = val;
            } else {
                int mid = (start + end) / 2;
                if (idx <= mid) {
                    update(2 * node + 1, start, mid, idx, val);
                } else {
                    update(2 * node + 2, mid + 1, end, idx, val);
                }
                tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
            }
        }
    }
    
    // ========================================
    // 3. FENWICK TREE (BINARY INDEXED TREE)
    // ========================================
    
    /**
     * Binary Indexed Tree (Fenwick Tree)
     * Efficient prefix sum and range queries
     * 
     * Time: Update O(log n), Query O(log n)
     * Space: O(n)
     * Simpler and faster than Segment Tree for range sum
     */
    public static class FenwickTree {
        private int[] tree;
        private int n;
        
        public FenwickTree(int[] arr) {
            n = arr.length;
            tree = new int[n + 1];
            for (int i = 0; i < n; i++) {
                update(i, arr[i]);
            }
        }
        
        /**
         * Update value at index
         */
        public void update(int idx, int val) {
            idx++;  // 1-indexed
            while (idx <= n) {
                tree[idx] += val;
                idx += idx & (-idx);  // Add last set bit
            }
        }
        
        /**
         * Get prefix sum [0...idx]
         */
        public int prefixSum(int idx) {
            idx++;  // 1-indexed
            int sum = 0;
            while (idx > 0) {
                sum += tree[idx];
                idx -= idx & (-idx);  // Remove last set bit
            }
            return sum;
        }
        
        /**
         * Range sum [left...right]
         */
        public int rangeSum(int left, int right) {
            if (left == 0) {
                return prefixSum(right);
            }
            return prefixSum(right) - prefixSum(left - 1);
        }
    }
    
    // ========================================
    // 4. TRIE (PREFIX TREE)
    // ========================================
    
    /**
     * Trie Data Structure
     * For efficient string searching and autocomplete
     * 
     * Time: Insert O(m), Search O(m), where m = string length
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
        
        /**
         * Insert word into trie
         */
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
        
        /**
         * Search for exact word
         */
        public boolean search(String word) {
            TrieNode node = find(word);
            return node != null && node.isWord;
        }
        
        /**
         * Search for prefix
         */
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
    // MAIN - Test Advanced DS
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED DATA STRUCTURES ===\n");
        
        // 1. Union-Find
        System.out.println("1. UNION-FIND");
        UnionFind uf = new UnionFind(5);
        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(3, 4);
        System.out.println("0 and 2 connected: " + uf.connected(0, 2));
        System.out.println("0 and 3 connected: " + uf.connected(0, 3));
        System.out.println("Components: " + uf.getComponents() + "\n");
        
        // 2. Segment Tree
        System.out.println("2. SEGMENT TREE (Range Sum)");
        int[] arr = {1, 3, 5, 7, 9};
        SegmentTree st = new SegmentTree(arr);
        System.out.println("Range sum [1,3]: " + st.rangeQuery(1, 3) + "\n");
        
        // 3. Fenwick Tree
        System.out.println("3. FENWICK TREE (Binary Indexed)");
        FenwickTree ft = new FenwickTree(arr);
        System.out.println("Range sum [1,3]: " + ft.rangeSum(1, 3) + "\n");
        
        // 4. Trie
        System.out.println("4. TRIE (Prefix Tree)");
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("app");
        System.out.println("Search 'apple': " + trie.search("apple"));
        System.out.println("Search 'app': " + trie.search("app"));
        System.out.println("Starts with 'ap': " + trie.startsWith("ap"));
    }
}
