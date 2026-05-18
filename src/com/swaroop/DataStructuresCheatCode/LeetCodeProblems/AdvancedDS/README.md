# Advanced Data Structures - LeetCode Problems

This package contains comprehensive LeetCode problem solutions using advanced data structures.

## Package Structure

### 1. **UnionFindProblems.java**
Union-Find (Disjoint Set Union) problems

**Problems:**
- **LeetCode 323**: Number of Connected Components in an Undirected Graph
  - Find number of connected components in graph
  - Time: O(n + e * α(n)), Space: O(n)

- **LeetCode 684**: Redundant Connection
  - Find the edge that creates a cycle in a tree
  - Time: O(n * α(n)), Space: O(n)

- **LeetCode 547**: Number of Provinces
  - Count provinces based on city connections
  - Time: O(n² * α(n)), Space: O(n)

- **LeetCode 721**: Accounts Merge
  - Merge accounts that share emails
  - Time: O(n * k * log(n*k)), Space: O(n * k)

---

### 2. **TrieProblems.java**
Trie (Prefix Tree) problems

**Problems:**
- **LeetCode 208**: Implement Trie (Prefix Tree)
  - Full Trie implementation with insert, search, startsWith
  - Time: O(m) per operation where m = word length

- **LeetCode 212**: Word Search II
  - Find all words from dictionary in 2D board
  - Time: O(m*n * 4^L), Space: O(sum of word lengths)

- **LeetCode 720**: Longest Word in Dictionary
  - Find longest word that can be built from other words
  - Time: O(sum of word lengths), Space: O(sum of word lengths)

- **LeetCode 642**: Design Search Autocomplete System
  - Autocomplete system with top-3 suggestions by frequency
  - Time: O(L * log(L)) per query where L = prefix matches

---

### 3. **SegmentTreeFenwickProblems.java**
Segment Tree and Fenwick Tree problems

**Problems:**
- **LeetCode 303**: Range Sum Query - Immutable
  - Efficient range sum queries on static array
  - Time: Build O(n), Query O(1), Space: O(n)

- **LeetCode 307**: Range Sum Query - Mutable
  - Range sum queries with point updates using Segment Tree
  - Time: Build O(n), Update O(log n), Query O(log n), Space: O(n)

- **LeetCode 308**: Range Sum Query 2D - Mutable
  - 2D range sum queries with point updates using 2D Fenwick Tree
  - Time: Build O(mn log m log n), Update O(log m log n), Query O(log m log n)

- **LeetCode 493**: Reverse Pairs
  - Count pairs (i,j) where nums[i] > 2*nums[j]
  - Time: O(n log n), Space: O(n)

---

### 4. **AdvancedGraphTreeProblems.java**
Advanced graph and tree problems using Union-Find and Tarjan's algorithm

**Problems:**
- **LeetCode 1584**: Min Cost to Connect All Points
  - Find minimum cost to connect all points using Manhattan distance
  - Uses Kruskal's Algorithm with Union-Find
  - Time: O(n² log n), Space: O(n²)

- **LeetCode 207**: Course Schedule
  - Detect cycle in directed graph (check if all courses can be finished)
  - Uses topological sort with Kahn's algorithm
  - Time: O(V + E), Space: O(V + E)

- **LeetCode 1192**: Critical Connections in a Network
  - Find all bridges in undirected graph
  - Uses Tarjan's Algorithm
  - Time: O(V + E), Space: O(V + E)

- **LeetCode 1697**: Checking Existence of Edge Length Limited Paths
  - Answer multiple path queries with weight constraints
  - Uses offline query processing with Union-Find
  - Time: O((V + Q) log (V + Q)), Space: O(V + E + Q)

---

## Key Techniques

### Union-Find Optimizations
- **Path Compression**: O(α(n)) amortized time for find operation
- **Union by Rank**: Ensures tree height remains logarithmic
- **Applications**: Connected components, cycle detection, MST algorithms

### Trie Optimization
- **Space Efficient**: O(ALPHABET_SIZE * depth)
- **Fast Searches**: O(word_length) regardless of dictionary size
- **Applications**: Autocomplete, spell checking, word prefix problems

### Segment Tree
- **Range Operations**: Efficient range queries and updates
- **Binary Tree Structure**: O(log n) query and update time
- **Flexible**: Can be adapted for different operations (sum, min, max, etc.)

### Fenwick Tree (Binary Indexed Tree)
- **Space Efficient**: O(n) space for O(log n) operations
- **Simpler Implementation**: Less code than Segment Tree
- **Best for**: Prefix sum and range sum queries

### Tarjan's Algorithm
- **Single Pass**: O(V + E) for finding bridges and SCCs
- **Uses DFS**: Discovery and low-link values
- **Applications**: Bridge detection, articulation points, SCCs

---

## Complexity Summary

| Data Structure | Insert | Search | Delete | Space |
|---|---|---|---|---|
| Union-Find | - | O(α(n)) | - | O(n) |
| Trie | O(m) | O(m) | O(m) | O(ALPHA*N) |
| Segment Tree | - | O(log n) | O(log n) | O(n) |
| Fenwick Tree | O(log n) | O(log n) | - | O(n) |

---

## How to Run

Each file contains a `main` method with example test cases:

```bash
javac UnionFindProblems.java
java UnionFindProblems

javac TrieProblems.java
java TrieProblems

javac SegmentTreeFenwickProblems.java
java SegmentTreeFenwickProblems

javac AdvancedGraphTreeProblems.java
java AdvancedGraphTreeProblems
```

---

## References

- **Union-Find**: https://leetcode.com/tag/union-find/
- **Trie**: https://leetcode.com/tag/trie/
- **Segment Tree**: https://en.wikipedia.org/wiki/Segment_tree
- **Fenwick Tree**: https://en.wikipedia.org/wiki/Fenwick_tree
- **Tarjan's Algorithm**: https://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm

---

## Tips for Interview Preparation

1. **Union-Find**: Master path compression and union by rank
2. **Trie**: Understand both insert and search patterns
3. **Segment Tree**: Practice building and querying on different operations
4. **Fenwick Tree**: Know the bit manipulation tricks (idx & (-idx))
5. **Graph Algorithms**: Practice DFS-based algorithms like Tarjan's

---

**Last Updated**: May 2026
