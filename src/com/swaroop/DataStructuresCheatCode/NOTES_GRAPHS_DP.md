# 📖 GRAPHS & DYNAMIC PROGRAMMING - LEARNING GUIDE

## 🎯 GRAPHS

### What is a Graph?

A **graph** is a collection of **vertices (nodes)** connected by **edges**.

```
Vertices: {1, 2, 3, 4}
Edges: {1-2, 1-3, 2-3, 3-4}

    1 --- 2
    |  \ |
    3 -- 4
```

### Graph Types

**Directed vs Undirected:**
- Undirected: roads between cities
- Directed: Twitter followers

**Weighted vs Unweighted:**
- Unweighted: number of hops
- Weighted: distance, cost

**Cyclic vs Acyclic:**
- Cyclic: has loops
- Acyclic: no loops (DAG)

---

### Representations

**Adjacency List (Preferred):**
```java
List<Integer>[] adj = new List[V];
// adj[0] = [1, 2]  vertices 0 connects to 1, 2
```
Space: O(V + E), Fast for sparse graphs

**Adjacency Matrix:**
```java
int[][] adj = new int[V][V];
// adj[0][1] = 1  if edge exists
```
Space: O(V²), Fast for dense graphs

---

### Graph Traversal

**DFS (Depth-First):**
```java
void dfs(int node, boolean[] visited) {
    visited[node] = true;
    System.out.println(node);
    
    for (int neighbor : adj[node]) {
        if (!visited[neighbor]) {
            dfs(neighbor, visited);
        }
    }
}
```
Time: O(V + E)

**BFS (Breadth-First):**
```java
void bfs(int start) {
    Queue<Integer> queue = new LinkedList<>();
    boolean[] visited = new boolean[V];
    
    queue.offer(start);
    visited[start] = true;
    
    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.println(node);
        
        for (int neighbor : adj[node]) {
            if (!visited[neighbor]) {
                queue.offer(neighbor);
                visited[neighbor] = true;
            }
        }
    }
}
```
Time: O(V + E)

---

### Key Algorithms

**Dijkstra (Shortest Path):**
- Use priority queue
- Time: O(E log V)
- No negative weights

**Topological Sort:**
- DFS or Kahn's algorithm
- For DAGs only
- Time: O(V + E)

---

## 💡 DYNAMIC PROGRAMMING

### What is DP?

**Dynamic Programming** = Breaking problem into subproblems + Storing results

**Requirement:**
1. Overlapping subproblems
2. Optimal substructure

### Example: Fibonacci

**Without DP (Exponential):**
```
fib(5) calls fib(4) and fib(3)
fib(4) calls fib(3) and fib(2)
fib(3) is called multiple times! ← Waste
```

**With DP (Linear):**
```
fib[1] = 1
fib[2] = 1
fib[3] = 2
fib[4] = 3
fib[5] = 5
```
Time: O(n) instead of O(2ⁿ)!

---

### Two Approaches

**1. Memoization (Top-Down)**
```java
Map<Integer, Integer> memo = new HashMap<>();

int fib(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    
    int result = fib(n-1) + fib(n-2);
    memo.put(n, result);
    return result;
}
```
- Recursive
- Cache results as you go
- Only computes needed values

**2. Tabulation (Bottom-Up)**
```java
int[] dp = new int[n+1];
dp[0] = 0;
dp[1] = 1;

for (int i = 2; i <= n; i++) {
    dp[i] = dp[i-1] + dp[i-2];
}

return dp[n];
```
- Iterative
- Build from base cases up
- All values computed

---

### Common DP Patterns

**Pattern 1: 1D Array**
```
dp[i] = result for subproblem i
Example: LIS, climbing stairs
```

**Pattern 2: 2D Array**
```
dp[i][j] = result using i items, capacity j
Example: Knapsack, LCS
```

**Pattern 3: State Machine**
```
States with transitions
Example: Stock trading
```

---

### Classic DP Problems

**Easy:**
- Climbing stairs
- Min cost stairs
- House robber

**Medium:**
- 0/1 Knapsack
- Coin change
- Longest increasing subsequence
- Edit distance

**Hard:**
- Matrix chain multiplication
- Palindrome partitioning
- Shortest path in grid

---

## 🎓 Problem Recognition

### "When to use..."

**DFS/BFS:**
- "Connected components"
- "Is there a path?"
- "Shortest path (unweighted)"

**Dijkstra:**
- "Shortest path"
- Weighted graph

**Topological Sort:**
- "Course schedule" (prerequisites)
- "Task ordering"

**DP:**
- "Maximum/minimum"
- "Number of ways"
- "Can we achieve?"

---

## ⚡ Complexity Summary

| Algorithm | Time | Space |
|-----------|------|-------|
| DFS | O(V+E) | O(V) |
| BFS | O(V+E) | O(V) |
| Dijkstra | O(E log V) | O(V) |
| Bellman-Ford | O(VE) | O(V) |
| DP Fib | O(n) | O(n) |

---

## ✨ Key Takeaways

**Graphs:**
1. Choose adjacency list vs matrix based on density
2. DFS for depth, BFS for breadth
3. Always track visited to avoid cycles
4. Dijkstra for weighted shortest path

**Dynamic Programming:**
1. Identify overlapping subproblems
2. Define state clearly (what does dp[i] mean?)
3. Find transition (how to compute from previous)
4. Memoization for top-down, tabulation for bottom-up
5. Optimize space if possible

---

**Practice these fundamentals well - they're building blocks for advanced algorithms!**
