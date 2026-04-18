package com.swaroop.DataStructuresCheatCode;

/**
 * ADVANCED GRAPH ALGORITHMS - BEYOND BASICS
 * 
 * 💡 ADVANCED TECHNIQUES:
 * 1. Minimum Spanning Tree (Kruskal, Prim)
 * 2. Shortest Path (Floyd-Warshall, Bellman-Ford)
 * 3. Strong Connectivity (Tarjan, Kosaraju)
 * 4. Network Flow
 * 5. Bipartite Matching
 */

public class AdvancedGraphAlgorithms {
    
    // ========================================
    // 1. KRUSKAL'S ALGORITHM (MST)
    // ========================================
    
    /**
     * Kruskal's - Find Minimum Spanning Tree
     * Uses Union-Find data structure
     * 
     * Steps:
     * 1. Sort edges by weight
     * 2. Pick minimum edge if it doesn't form cycle
     * 3. Repeat until all vertices connected
     * 
     * Time: O(E log E) for sorting
     * Space: O(V + E)
     */
    public static int kruskalMST(int vertices, int[][] edges) {
        // edges: [u, v, weight]
        java.util.Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        
        UnionFind uf = new UnionFind(vertices);
        int totalCost = 0;
        int edgesAdded = 0;
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
                totalCost += weight;
                edgesAdded++;
                
                if (edgesAdded == vertices - 1) break;
            }
        }
        
        return totalCost;
    }
    
    private static class UnionFind {
        int[] parent, rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }
        
        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);  // Path compression
            }
            return parent[x];
        }
        
        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            
            if (rank[px] < rank[py]) {
                parent[px] = py;
            } else if (rank[px] > rank[py]) {
                parent[py] = px;
            } else {
                parent[py] = px;
                rank[px]++;
            }
        }
    }
    
    // ========================================
    // 2. PRIM'S ALGORITHM (MST)
    // ========================================
    
    /**
     * Prim's Algorithm - Find Minimum Spanning Tree
     * Grows tree incrementally from starting vertex
     * 
     * Time: O(E log V) with min heap
     * Space: O(V + E)
     */
    public static int primMST(int vertices, int[][] graph) {
        // graph[i][j] = weight of edge from i to j
        boolean[] visited = new boolean[vertices];
        java.util.PriorityQueue<int[]> minHeap = new java.util.PriorityQueue<>(
            (a, b) -> a[0] - b[0]  // {weight, u, v}
        );
        
        int totalCost = 0;
        minHeap.offer(new int[]{0, 0, -1});
        
        while (!minHeap.isEmpty()) {
            int[] curr = minHeap.poll();
            int weight = curr[0];
            int u = curr[1];
            
            if (visited[u]) continue;
            
            visited[u] = true;
            totalCost += weight;
            
            // Add all edges from u
            for (int v = 0; v < vertices; v++) {
                if (!visited[v] && graph[u][v] != 0) {
                    minHeap.offer(new int[]{graph[u][v], v, u});
                }
            }
        }
        
        return totalCost;
    }
    
    // ========================================
    // 3. FLOYD-WARSHALL (All Pairs Shortest Path)
    // ========================================
    
    /**
     * Floyd-Warshall Algorithm
     * Find shortest path between ALL pairs of vertices
     * 
     * Can handle negative weights (no negative cycles)
     * 
     * Time: O(V³)
     * Space: O(V²)
     */
    public static int[][] floydWarshall(int[][] graph) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        
        // Initialize distance matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        
        // For each intermediate vertex
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Relax edge
                    if (dist[i][k] != Integer.MAX_VALUE && 
                        dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        
        return dist;
    }
    
    // ========================================
    // 4. BELLMAN-FORD (Negative Weight Handling)
    // ========================================
    
    /**
     * Bellman-Ford Algorithm
     * Single source shortest path with negative weights
     * Detects negative cycles
     * 
     * Time: O(V * E)
     * Space: O(V)
     */
    public static int[] bellmanFord(int vertices, int[][] edges, int source) {
        int[] dist = new int[vertices];
        java.util.Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        
        // Relax edges V-1 times
        for (int i = 0; i < vertices - 1; i++) {
            for (int[] edge : edges) {
                int u = edge[0];
                int v = edge[1];
                int weight = edge[2];
                
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                }
            }
        }
        
        // Check for negative cycle
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];
            
            if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                System.out.println("⚠️  Negative cycle detected!");
                return null;
            }
        }
        
        return dist;
    }
    
    // ========================================
    // 5. STRONGLY CONNECTED COMPONENTS (Kosaraju)
    // ========================================
    
    /**
     * Kosaraju's Algorithm - Find Strongly Connected Components
     * 
     * Steps:
     * 1. DFS on original graph, store vertices by finish time
     * 2. DFS on transposed graph in reverse finish order
     * 3. Each DFS tree is one SCC
     * 
     * Time: O(V + E)
     */
    public static java.util.List<java.util.List<Integer>> kosaraju(
        int vertices, 
        java.util.List<Integer>[] adj) {
        
        // Step 1: DFS and get finish order
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        boolean[] visited = new boolean[vertices];
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsKosaraju1(i, visited, stack, adj);
            }
        }
        
        // Step 2: Create transposed graph
        java.util.List<Integer>[] transpose = new java.util.ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            transpose[i] = new java.util.ArrayList<>();
        }
        
        for (int u = 0; u < vertices; u++) {
            for (int v : adj[u]) {
                transpose[v].add(u);
            }
        }
        
        // Step 3: DFS on transposed in reverse order
        visited = new boolean[vertices];
        java.util.List<java.util.List<Integer>> sccs = new java.util.ArrayList<>();
        
        while (!stack.isEmpty()) {
            int u = stack.pop();
            if (!visited[u]) {
                java.util.List<Integer> scc = new java.util.ArrayList<>();
                dfsKosaraju2(u, visited, transpose, scc);
                sccs.add(scc);
            }
        }
        
        return sccs;
    }
    
    private static void dfsKosaraju1(int u, boolean[] visited, 
                                     java.util.Stack<Integer> stack,
                                     java.util.List<Integer>[] adj) {
        visited[u] = true;
        for (int v : adj[u]) {
            if (!visited[v]) {
                dfsKosaraju1(v, visited, stack, adj);
            }
        }
        stack.push(u);
    }
    
    private static void dfsKosaraju2(int u, boolean[] visited,
                                     java.util.List<Integer>[] transpose,
                                     java.util.List<Integer> scc) {
        visited[u] = true;
        scc.add(u);
        for (int v : transpose[u]) {
            if (!visited[v]) {
                dfsKosaraju2(v, visited, transpose, scc);
            }
        }
    }
    
    // ========================================
    // 6. BRIDGE FINDING
    // ========================================
    
    /**
     * Find all bridges in undirected graph
     * A bridge is an edge whose removal disconnects the graph
     * 
     * Time: O(V + E)
     */
    public static java.util.List<int[]> findBridges(int vertices, int[][] edges) {
        java.util.List<Integer>[] adj = new java.util.ArrayList[vertices];
        for (int i = 0; i < vertices; i++) {
            adj[i] = new java.util.ArrayList<>();
        }
        
        for (int[] edge : edges) {
            adj[edge[0]].add(edge[1]);
            adj[edge[1]].add(edge[0]);
        }
        
        boolean[] visited = new boolean[vertices];
        int[] disc = new int[vertices];
        int[] low = new int[vertices];
        java.util.List<int[]> bridges = new java.util.ArrayList<>();
        int[] time = {0};
        
        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfsBridge(i, -1, visited, disc, low, time, adj, bridges);
            }
        }
        
        return bridges;
    }
    
    private static void dfsBridge(int u, int parent, boolean[] visited,
                                  int[] disc, int[] low, int[] time,
                                  java.util.List<Integer>[] adj,
                                  java.util.List<int[]> bridges) {
        visited[u] = true;
        disc[u] = low[u] = time[0]++;
        
        for (int v : adj[u]) {
            if (!visited[v]) {
                dfsBridge(v, u, visited, disc, low, time, adj, bridges);
                low[u] = Math.min(low[u], low[v]);
                
                // If low[v] > disc[u], then (u,v) is a bridge
                if (low[v] > disc[u]) {
                    bridges.add(new int[]{u, v});
                }
            } else if (v != parent) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }
    
    // ========================================
    // MAIN - Test Advanced Graph Algorithms
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED GRAPH ALGORITHMS ===\n");
        
        // 1. Kruskal's MST
        System.out.println("1. KRUSKAL'S MST");
        int[][] edges = {{0,1,4}, {0,2,2}, {1,2,1}, {1,3,5}, {2,3,8}};
        System.out.println("MST cost: " + kruskalMST(4, edges) + "\n");
        
        // 2. Floyd-Warshall
        System.out.println("2. FLOYD-WARSHALL");
        int INF = Integer.MAX_VALUE / 2;
        int[][] graph = {
            {0, 3, INF, 7},
            {8, 0, 2, INF},
            {5, INF, 0, 1},
            {2, INF, INF, 0}
        };
        int[][] result = floydWarshall(graph);
        System.out.println("Shortest path 0->3: " + result[0][3] + "\n");
        
        // 3. Bellman-Ford
        System.out.println("3. BELLMAN-FORD");
        int[][] edges2 = {{0,1,4}, {0,2,2}, {1,2,1}, {1,3,5}, {2,3,8}};
        int[] dist = bellmanFord(4, edges2, 0);
        System.out.println("Distance from 0: " + java.util.Arrays.toString(dist));
    }
}
