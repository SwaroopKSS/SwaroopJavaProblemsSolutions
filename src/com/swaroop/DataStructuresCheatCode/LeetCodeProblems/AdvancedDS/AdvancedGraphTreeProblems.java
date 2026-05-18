package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.AdvancedDS;

import java.util.*;

/**
 * ADVANCED GRAPH & TREE PROBLEMS - LEETCODE
 * 
 * Problems covered:
 * 1. LeetCode 1584: Min Cost to Connect All Points
 * 2. LeetCode 207: Course Schedule (Cycle Detection)
 * 3. LeetCode 1192: Critical Connections in a Network
 * 4. LeetCode 1697: Checking Existence of Edge Length Limited Paths
 */

public class AdvancedGraphTreeProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 1584 - Min Cost to Connect All Points
    // ========================================
    
    /**
     * LeetCode 1584: Min Cost to Connect All Points
     * 
     * Given n points on a 2D plane, find minimum cost to connect all points.
     * Cost to connect two points is Manhattan distance.
     * 
     * Example:
     * points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
     * Output: 20
     * 
     * Uses: Kruskal's Algorithm with Union-Find
     * Time: O(n² log n)
     * Space: O(n²)
     */
    public static int minCostConnectPoints(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();
        
        // Create all edges with Manhattan distance as weight
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int dist = Math.abs(points[i][0] - points[j][0]) +
                          Math.abs(points[i][1] - points[j][1]);
                edges.add(new int[]{dist, i, j});
            }
        }
        
        // Sort edges by weight
        edges.sort((a, b) -> Integer.compare(a[0], b[0]));
        
        // Kruskal's algorithm with Union-Find
        UnionFind uf = new UnionFind(n);
        int cost = 0;
        int edgeCount = 0;
        
        for (int[] edge : edges) {
            int weight = edge[0];
            int u = edge[1];
            int v = edge[2];
            
            if (!uf.connected(u, v)) {
                uf.union(u, v);
                cost += weight;
                edgeCount++;
                
                if (edgeCount == n - 1) break;
            }
        }
        
        return cost;
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 207 - Course Schedule (Cycle Detection)
    // ========================================
    
    /**
     * LeetCode 207: Course Schedule
     * 
     * There are n courses labeled 0 to n-1 and some prerequisite relationships.
     * Determine if you can finish all courses.
     * A prerequisite relationship is represented as [a, b] meaning you must
     * take course b before course a.
     * 
     * Example:
     * numCourses = 2, prerequisites = [[1,0]]
     * Output: true
     * Explanation: Take course 0 first, then course 1
     * 
     * Uses: Topological Sort with cycle detection
     * Time: O(V + E) where V = courses, E = prerequisites
     * Space: O(V + E)
     */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        // Build adjacency list
        List<List<Integer>> graph = new ArrayList<>();
        int[] inDegree = new int[numCourses];
        
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] pre : prerequisites) {
            graph.get(pre[1]).add(pre[0]);
            inDegree[pre[0]]++;
        }
        
        // Topological sort using Kahn's algorithm
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        
        int finishedCourses = 0;
        while (!queue.isEmpty()) {
            int course = queue.poll();
            finishedCourses++;
            
            for (int nextCourse : graph.get(course)) {
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }
        
        return finishedCourses == numCourses;
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 1192 - Critical Connections in a Network
    // ========================================
    
    /**
     * LeetCode 1192: Critical Connections in a Network (Bridges)
     * 
     * Find all critical connections (bridges) in the network.
     * A bridge is a connection that would disconnect the graph if removed.
     * 
     * Example:
     * n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
     * Output: [[1,3]]
     * Explanation: Removing [1,3] disconnects node 3
     * 
     * Uses: Tarjan's Algorithm for bridge detection
     * Time: O(V + E)
     * Space: O(V + E)
     */
    public static List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer>[] graph = new List[n];
        
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (List<Integer> conn : connections) {
            int u = conn.get(0);
            int v = conn.get(1);
            graph[u].add(v);
            graph[v].add(u);
        }
        
        // Tarjan's algorithm
        int[] disc = new int[n];
        int[] low = new int[n];
        boolean[] visited = new boolean[n];
        int[] timer = {0};
        
        dfs(0, -1, graph, disc, low, visited, timer, result);
        
        return result;
    }
    
    private static void dfs(int u, int parent, List<Integer>[] graph, 
                           int[] disc, int[] low, boolean[] visited, 
                           int[] timer, List<List<Integer>> result) {
        visited[u] = true;
        disc[u] = low[u] = timer[0]++;
        
        for (int v : graph[u]) {
            if (!visited[v]) {
                dfs(v, u, graph, disc, low, visited, timer, result);
                low[u] = Math.min(low[u], low[v]);
                
                // If low[v] > disc[u], then u-v is a bridge
                if (low[v] > disc[u]) {
                    result.add(Arrays.asList(u, v));
                }
            } else if (v != parent) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 1697 - Checking Existence of Edge Length Limited Paths
    // ========================================
    
    /**
     * LeetCode 1697: Checking Existence of Edge Length Limited Paths
     * 
     * Given a weighted undirected graph with n nodes and a list of queries.
     * For each query [p, q, limit], check if path exists from p to q
     * where all edges have weight < limit.
     * 
     * Example:
     * n = 3, edgeList = [[0,1,2],[1,2,4],[2,0,8],[1,0,16]],
     * queries = [[0,1,2],[0,2,5]]
     * Output: [false, true]
     * 
     * Uses: Offline Query with Union-Find + Sorting
     * Time: O((V + Q) log (V + Q))
     * Space: O(V + E + Q)
     */
    public static boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        // Sort edges by weight
        Arrays.sort(edgeList, (a, b) -> Integer.compare(a[2], b[2]));
        
        // Create query with original indices
        Integer[] queryIndices = new Integer[queries.length];
        for (int i = 0; i < queries.length; i++) {
            queryIndices[i] = i;
        }
        
        // Sort queries by limit
        Arrays.sort(queryIndices, (i, j) -> 
            Integer.compare(queries[i][2], queries[j][2]));
        
        boolean[] result = new boolean[queries.length];
        UnionFind uf = new UnionFind(n);
        int edgeIdx = 0;
        
        for (int qIdx : queryIndices) {
            int p = queries[qIdx][0];
            int q = queries[qIdx][1];
            int limit = queries[qIdx][2];
            
            // Add all edges with weight < limit
            while (edgeIdx < edgeList.length && edgeList[edgeIdx][2] < limit) {
                uf.union(edgeList[edgeIdx][0], edgeList[edgeIdx][1]);
                edgeIdx++;
            }
            
            result[qIdx] = uf.connected(p, q);
        }
        
        return result;
    }
    
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
    // MAIN - Test Advanced Graph & Tree Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED GRAPH & TREE PROBLEMS ===\n");
        
        // Problem 1: Min Cost to Connect All Points
        System.out.println("1. LeetCode 1584: Min Cost to Connect All Points");
        int[][] points = {{0,0},{2,2},{3,10},{5,2},{7,0}};
        System.out.println("points: " + Arrays.deepToString(points));
        System.out.println("Min Cost: " + minCostConnectPoints(points) + "\n");
        
        // Problem 2: Course Schedule
        System.out.println("2. LeetCode 207: Course Schedule (Cycle Detection)");
        int numCourses = 2;
        int[][] prerequisites = {{1,0}};
        System.out.println("numCourses: " + numCourses);
        System.out.println("prerequisites: " + Arrays.deepToString(prerequisites));
        System.out.println("Can Finish: " + canFinish(numCourses, prerequisites) + "\n");
        
        // Problem 3: Critical Connections
        System.out.println("3. LeetCode 1192: Critical Connections");
        List<List<Integer>> connections = new ArrayList<>();
        connections.add(Arrays.asList(0, 1));
        connections.add(Arrays.asList(1, 2));
        connections.add(Arrays.asList(2, 0));
        connections.add(Arrays.asList(1, 3));
        System.out.println("Critical Connections (Bridges): " + criticalConnections(4, connections) + "\n");
        
        // Problem 4: Distance Limited Paths
        System.out.println("4. LeetCode 1697: Distance Limited Paths");
        int[][] edgeList = {{0,1,2},{1,2,4},{2,0,8},{1,0,16}};
        int[][] queries = {{0,1,2},{0,2,5}};
        System.out.println("Result: " + Arrays.toString(distanceLimitedPathsExist(3, edgeList, queries)));
    }
}
