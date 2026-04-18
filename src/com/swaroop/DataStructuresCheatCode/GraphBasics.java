package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * GRAPHS - NETWORKS OF VERTICES AND EDGES
 * 
 * 💡 CHEAT MEMORY:
 * Graph = Vertices + Edges
 * DFS (Depth-First) = Use Stack/Recursion
 * BFS (Breadth-First) = Use Queue
 * Dijkstra = Find shortest path (single source)
 * Topological Sort = DAG ordering
 */

public class GraphBasics {
    
    public static class Graph {
        private Map<Integer, List<Integer>> adjList;
        
        public Graph(int vertices) {
            adjList = new HashMap<>();
            for (int i = 0; i < vertices; i++) {
                adjList.put(i, new ArrayList<>());
            }
        }
        
        public void addEdge(int u, int v, boolean directed) {
            adjList.get(u).add(v);
            if (!directed) {
                adjList.get(v).add(u);
            }
        }
        
        public List<Integer> getNeighbors(int vertex) {
            return adjList.get(vertex);
        }
    }
    
    // ========================================
    // 1. DEPTH-FIRST SEARCH (DFS)
    // ========================================
    
    /**
     * DFS - Explore as far as possible before backtracking
     * Time: O(V+E), Space: O(V)
     */
    public static void dfs(Graph graph, int start, Set<Integer> visited) {
        visited.add(start);
        System.out.print(start + " ");
        
        for (int neighbor : graph.getNeighbors(start)) {
            if (!visited.contains(neighbor)) {
                dfs(graph, neighbor, visited);
            }
        }
    }
    
    public static void dfsTraversal(Graph graph, int start) {
        Set<Integer> visited = new HashSet<>();
        System.out.print("DFS: ");
        dfs(graph, start, visited);
        System.out.println();
    }
    
    // ========================================
    // 2. BREADTH-FIRST SEARCH (BFS)
    // ========================================
    
    /**
     * BFS - Explore level by level
     * Time: O(V+E), Space: O(V)
     */
    public static void bfsTraversal(Graph graph, int start) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        
        visited.add(start);
        queue.offer(start);
        
        System.out.print("BFS: ");
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            System.out.print(vertex + " ");
            
            for (int neighbor : graph.getNeighbors(vertex)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        System.out.println();
    }
    
    // ========================================
    // 3. CYCLE DETECTION
    // ========================================
    
    /**
     * Detect Cycle in Undirected Graph
     */
    public static boolean hasCycleUndirected(Graph graph, int vertices) {
        Set<Integer> visited = new HashSet<>();
        
        for (int i = 0; i < vertices; i++) {
            if (!visited.contains(i)) {
                if (hasCycleDFS(graph, i, -1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private static boolean hasCycleDFS(Graph graph, int vertex, int parent, Set<Integer> visited) {
        visited.add(vertex);
        
        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                if (hasCycleDFS(graph, neighbor, vertex, visited)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true;
            }
        }
        return false;
    }
    
    // ========================================
    // 4. TOPOLOGICAL SORT
    // ========================================
    
    /**
     * Topological Sort - Order vertices such that edges go from earlier to later
     * Only works for Directed Acyclic Graphs (DAG)
     */
    public static List<Integer> topologicalSort(Graph graph, int vertices) {
        Set<Integer> visited = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        
        for (int i = 0; i < vertices; i++) {
            if (!visited.contains(i)) {
                topologicalDFS(graph, i, visited, stack);
            }
        }
        
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }
    
    private static void topologicalDFS(Graph graph, int vertex, Set<Integer> visited, Stack<Integer> stack) {
        visited.add(vertex);
        
        for (int neighbor : graph.getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                topologicalDFS(graph, neighbor, visited, stack);
            }
        }
        
        stack.push(vertex);
    }
    
    // ========================================
    // 5. DIJKSTRA'S ALGORITHM
    // ========================================
    
    /**
     * Dijkstra's Algorithm - Find shortest path from source
     * Time: O((V+E)logV) with min-heap
     */
    public static Map<Integer, Integer> dijkstra(int[][] edges, int n, int start) {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }
        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        Map<Integer, Integer> distances = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            distances.put(i, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new int[]{0, start});
        
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int dist = curr[0], vertex = curr[1];
            
            if (dist > distances.get(vertex)) {
                continue;
            }
            
            for (int[] neighbor : graph.get(vertex)) {
                int next = neighbor[0], weight = neighbor[1];
                int newDist = dist + weight;
                
                if (newDist < distances.get(next)) {
                    distances.put(next, newDist);
                    pq.offer(new int[]{newDist, next});
                }
            }
        }
        
        return distances;
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== GRAPH BASICS ===\n");
        
        // Create graph
        Graph g = new Graph(4);
        g.addEdge(0, 1, false);
        g.addEdge(0, 2, false);
        g.addEdge(1, 3, false);
        g.addEdge(2, 3, false);
        
        System.out.println("1. TRAVERSALS");
        dfsTraversal(g, 0);
        bfsTraversal(g, 0);
        System.out.println();
        
        System.out.println("2. CYCLE DETECTION");
        System.out.println("Has cycle (undirected): " + hasCycleUndirected(g, 4) + "\n");
        
        System.out.println("3. TOPOLOGICAL SORT");
        Graph dag = new Graph(6);
        dag.addEdge(0, 1, true);
        dag.addEdge(0, 2, true);
        dag.addEdge(1, 3, true);
        dag.addEdge(2, 3, true);
        dag.addEdge(3, 4, true);
        System.out.println("Topological order: " + topologicalSort(dag, 6) + "\n");
        
        System.out.println("4. DIJKSTRA'S ALGORITHM");
        int[][] edges = {{0, 1, 4}, {0, 2, 2}, {1, 2, 1}, {1, 3, 5}, {2, 3, 8}};
        Map<Integer, Integer> distances = dijkstra(edges, 4, 0);
        System.out.println("Shortest distances from 0: " + distances);
    }
}
