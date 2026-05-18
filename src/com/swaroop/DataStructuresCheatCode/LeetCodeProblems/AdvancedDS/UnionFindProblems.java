package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.AdvancedDS;

import java.util.*;

/**
 * UNION-FIND / DISJOINT SET UNION - LEETCODE PROBLEMS
 * 
 * Problems covered:
 * 1. LeetCode 323: Number of Connected Components in an Undirected Graph
 * 2. LeetCode 684: Redundant Connection
 * 3. LeetCode 721: Accounts Merge
 * 4. LeetCode 547: Number of Provinces
 */

public class UnionFindProblems {
    
    // ========================================
    // UNION-FIND UTILITY CLASS
    // ========================================
    
    public static class UnionFind {
        public int[] parent;
        public int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
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
        }
        
        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }
    
    // ========================================
    // PROBLEM 1: LeetCode 323 - Connected Components
    // ========================================
    
    /**
     * LeetCode 323: Number of Connected Components in an Undirected Graph
     * 
     * Given n nodes labeled from 0 to n - 1 and a list of undirected edges 
     * (each edge is a pair of nodes), return the number of connected components.
     * 
     * Example:
     * Input: n = 5, edges = [[0,1],[1,2],[3,4]]
     * Output: 2
     * Explanation: {0,1,2} and {3,4}
     * 
     * Time: O(n + e * α(n)) where e is edges count
     * Space: O(n)
     */
    public static int countComponents(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }
        
        Set<Integer> components = new HashSet<>();
        for (int i = 0; i < n; i++) {
            components.add(uf.find(i));
        }
        
        return components.size();
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 684 - Redundant Connection
    // ========================================
    
    /**
     * LeetCode 684: Redundant Connection
     * 
     * Given a list of edges representing an undirected graph that started as a tree,
     * with one additional edge that created a cycle. Find and return that edge.
     * 
     * Example:
     * Input: edges = [[1,2],[1,3],[2,3]]
     * Output: [2,3]
     * 
     * Time: O(n * α(n))
     * Space: O(n)
     */
    public static int[] findRedundantConnection(int[][] edges) {
        UnionFind uf = new UnionFind(edges.length + 1);
        
        for (int[] edge : edges) {
            // If both nodes already in same component, this edge creates cycle
            if (uf.connected(edge[0], edge[1])) {
                return edge;
            }
            uf.union(edge[0], edge[1]);
        }
        
        return new int[]{};
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 547 - Number of Provinces
    // ========================================
    
    /**
     * LeetCode 547: Number of Provinces
     * 
     * There are n cities. Some are directly connected, some are not.
     * A province is a group of directly or indirectly connected cities.
     * Given adjacency matrix, return number of provinces.
     * 
     * Example:
     * Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
     * Output: 2
     * Explanation: {0,1} and {2}
     * 
     * Time: O(n² * α(n))
     * Space: O(n)
     */
    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        
        Set<Integer> provinces = new HashSet<>();
        for (int i = 0; i < n; i++) {
            provinces.add(uf.find(i));
        }
        
        return provinces.size();
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 721 - Accounts Merge
    // ========================================
    
    /**
     * LeetCode 721: Accounts Merge
     * 
     * Given a list of accounts (name, emails), merge accounts that share an email.
     * Each account has a name and list of emails.
     * 
     * Example:
     * Input: accounts = [["David","david0@email.com","david1@email.com"],
     *                    ["David","david3@email.com","david4@email.com"],
     *                    ["David","david4@email.com","david5@email.com"]]
     * Output: [["David","david0@email.com","david1@email.com",
     *                    "david3@email.com","david4@email.com","david5@email.com"]]
     * 
     * Time: O(n * k * log(n*k)) where n = accounts, k = emails per account
     * Space: O(n * k)
     */
    public static List<List<String>> accountsMerge(List<List<String>> accounts) {
        // Map email to owner index
        Map<String, Integer> emailToOwner = new HashMap<>();
        // Map email to UnionFind parent
        Map<String, String> emailParent = new HashMap<>();
        
        // Initialize UnionFind for emails
        for (List<String> account : accounts) {
            String owner = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailParent.putIfAbsent(email, email);
                emailToOwner.putIfAbsent(email, accounts.indexOf(account));
                
                // Union first email with all others in this account
                union(emailParent, account.get(1), email);
            }
        }
        
        // Group emails by their root parent
        Map<String, TreeSet<String>> rootToEmails = new HashMap<>();
        for (String email : emailParent.keySet()) {
            String root = find(emailParent, email);
            rootToEmails.putIfAbsent(root, new TreeSet<>());
            rootToEmails.get(root).add(email);
        }
        
        // Build result
        List<List<String>> result = new ArrayList<>();
        for (String root : rootToEmails.keySet()) {
            List<String> merged = new ArrayList<>();
            merged.add(accounts.get(emailToOwner.get(root)).get(0)); // Owner name
            merged.addAll(rootToEmails.get(root)); // Sorted emails
            result.add(merged);
        }
        
        return result;
    }
    
    private static void union(Map<String, String> parent, String a, String b) {
        String pa = find(parent, a);
        String pb = find(parent, b);
        if (!pa.equals(pb)) {
            parent.put(pa, pb);
        }
    }
    
    private static String find(Map<String, String> parent, String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent, parent.get(x)));
        }
        return parent.get(x);
    }
    
    // ========================================
    // MAIN - Test Union-Find Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== UNION-FIND LEETCODE PROBLEMS ===\n");
        
        // Problem 1: Connected Components
        System.out.println("1. LeetCode 323: Connected Components");
        int n = 5;
        int[][] edges1 = {{0,1},{1,2},{3,4}};
        System.out.println("n=" + n + ", edges=" + Arrays.deepToString(edges1));
        System.out.println("Result: " + countComponents(n, edges1) + "\n");
        
        // Problem 2: Redundant Connection
        System.out.println("2. LeetCode 684: Redundant Connection");
        int[][] edges2 = {{1,2},{1,3},{2,3}};
        System.out.println("edges=" + Arrays.deepToString(edges2));
        System.out.println("Result: " + Arrays.toString(findRedundantConnection(edges2)) + "\n");
        
        // Problem 3: Number of Provinces
        System.out.println("3. LeetCode 547: Number of Provinces");
        int[][] isConnected = {{1,1,0},{1,1,0},{0,0,1}};
        System.out.println("isConnected=" + Arrays.deepToString(isConnected));
        System.out.println("Result: " + findCircleNum(isConnected) + "\n");
        
        // Problem 4: Accounts Merge
        System.out.println("4. LeetCode 721: Accounts Merge");
        List<List<String>> accounts = new ArrayList<>();
        accounts.add(Arrays.asList("David", "david0@email.com", "david1@email.com"));
        accounts.add(Arrays.asList("David", "david3@email.com", "david4@email.com"));
        accounts.add(Arrays.asList("David", "david4@email.com", "david5@email.com"));
        System.out.println("Result:");
        accountsMerge(accounts).forEach(System.out::println);
    }
}
