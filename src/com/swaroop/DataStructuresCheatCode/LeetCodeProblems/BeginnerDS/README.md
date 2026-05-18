# Beginner-Level LeetCode Problems

Comprehensive collection of beginner-friendly LeetCode problems organized by data structure. Perfect for learning fundamentals and building interview preparation foundation.

## Package Structure

### 1. **ArrayStringProblems.java**
Array and String manipulation problems

**Problems:**
- **LeetCode 1**: Two Sum
  - Find two numbers that add up to target using HashMap
  - Time: O(n), Space: O(n)

- **LeetCode 121**: Best Time to Buy and Sell Stock
  - Track minimum price and maximum profit while iterating
  - Time: O(n), Space: O(1)

- **LeetCode 217**: Contains Duplicate
  - Check if array has duplicates using HashSet
  - Time: O(n), Space: O(n)

- **LeetCode 242**: Valid Anagram
  - Compare character frequency between strings
  - Time: O(n), Space: O(1)

- **LeetCode 26**: Remove Duplicates from Sorted Array
  - Two-pointer technique for in-place removal
  - Time: O(n), Space: O(1)

---

### 2. **HashMapSetProblems.java**
Hash Map and Set based problems

**Problems:**
- **LeetCode 383**: Ransom Note
  - Count character availability from magazine
  - Time: O(m + n), Space: O(1)

- **LeetCode 349**: Intersection of Two Arrays
  - Find common elements between two arrays
  - Time: O(m + n), Space: O(min(m,n))

- **LeetCode 202**: Happy Number
  - Detect cycles using sum of digit squares
  - Time: O(log n), Space: O(1)

- **LeetCode 205**: Isomorphic Strings
  - Bidirectional character mapping validation
  - Time: O(n), Space: O(1)

- **LeetCode 290**: Word Pattern
  - Pattern matching with word sequences
  - Time: O(n + m), Space: O(n + m)

---

### 3. **LinkedListProblems.java**
Linked List manipulation and traversal

**Problems:**
- **LeetCode 203**: Remove Linked List Elements
  - Remove nodes with specific value using dummy node
  - Time: O(n), Space: O(1)

- **LeetCode 206**: Reverse Linked List
  - Iterative list reversal with pointer manipulation
  - Time: O(n), Space: O(1)
  - Recursive version: Space O(n) for call stack

- **LeetCode 141**: Linked List Cycle
  - Detect cycle using slow/fast pointer technique
  - Time: O(n), Space: O(1)

- **LeetCode 160**: Intersection of Two Linked Lists
  - Find intersection using two-pointer approach
  - Time: O(m + n), Space: O(1)

- **LeetCode 237**: Delete Node in a Linked List
  - Delete node without access to previous node
  - Time: O(1), Space: O(1)

---

### 4. **StackQueueProblems.java**
Stack and Queue implementation and usage

**Problems:**
- **LeetCode 20**: Valid Parentheses
  - Validate bracket matching using stack
  - Time: O(n), Space: O(n)

- **LeetCode 155**: Min Stack
  - Design stack with O(1) minimum retrieval
  - Uses two stacks: one for values, one for minimums
  - All operations: O(1)

- **LeetCode 232**: Implement Queue using Stacks
  - Build FIFO queue from two LIFO stacks
  - Push: O(1), Pop/Peek: Amortized O(1)

- **LeetCode 225**: Implement Stack using Queues
  - Build LIFO stack using single queue
  - Push: O(n), Pop: O(1)

- **LeetCode 921**: Minimum Add to Make Parentheses Valid
  - Count unmatched parentheses
  - Time: O(n), Space: O(1)

---

### 5. **TreeProblems.java**
Binary Tree traversal and manipulation

**Problems:**
- **LeetCode 226**: Invert Binary Tree
  - Swap left and right subtrees recursively
  - Time: O(n), Space: O(h)

- **LeetCode 101**: Symmetric Tree
  - Check if tree is mirror of itself
  - Time: O(n), Space: O(h)

- **LeetCode 104**: Maximum Depth of Binary Tree
  - Find longest path from root to leaf
  - Time: O(n), Space: O(h)
  - Iterative BFS version available

- **LeetCode 111**: Minimum Depth of Binary Tree
  - Find shortest path to leaf node
  - Time: O(n), Space: O(h)

- **LeetCode 100**: Same Tree
  - Check if two trees are structurally identical
  - Time: O(min(m,n)), Space: O(h)

---

## Learning Progression

### Week 1: Arrays & Strings
- Start with Two Sum (basic hash usage)
- Best Time to Buy Stock (single pass tracking)
- Contains Duplicate (hash set basics)
- Valid Anagram (character counting)
- Remove Duplicates (two pointer technique)

### Week 2: Hash Maps & Sets
- Ransom Note (character frequency)
- Intersection (set operations)
- Happy Number (cycle detection)
- Isomorphic Strings (character mapping)
- Word Pattern (pattern matching)

### Week 3: Linked Lists
- Remove Elements (traversal with removal)
- Reverse Linked List (pointer manipulation)
- Detect Cycle (fast/slow pointers)
- Intersection (multiple list traversal)
- Delete Node (in-place deletion)

### Week 4: Stacks & Queues
- Valid Parentheses (stack matching)
- Min Stack (auxiliary data structure)
- Queue using Stacks (two-stack FIFO)
- Stack using Queues (queue to LIFO)
- Minimum Add Parentheses (counter logic)

### Week 5: Trees
- Invert Tree (post-order traversal)
- Symmetric Tree (pair comparison)
- Max Depth (recursion depth tracking)
- Min Depth (early termination)
- Same Tree (tree comparison)

---

## Complexity Cheat Sheet

| Topic | Operation | Time | Space |
|-------|-----------|------|-------|
| Array | Access | O(1) | - |
| Array | Search | O(n) | O(1) |
| HashMap | Insert/Search | O(1) avg | O(n) |
| LinkedList | Access | O(n) | - |
| LinkedList | Insert/Delete | O(1) with ref | O(1) |
| Stack | Push/Pop | O(1) | O(n) |
| Queue | Enqueue/Dequeue | O(1) | O(n) |
| Tree | Traversal | O(n) | O(h) |

---

## Key Techniques Used

### Two Pointer
- Arrays: Remove Duplicates, Intersection
- Linked Lists: Cycle Detection, Reverse

### Hash Map/Set
- Two Sum, Anagram, Isomorphic Strings
- Word Pattern, Intersection, Happy Number

### Stack/Queue
- Valid Parentheses, Min Stack
- FIFO/LIFO implementations

### Recursion vs Iteration
- Trees: Both approaches provided where applicable
- Linked Lists: Reverse has both versions

### Dummy Node
- Linked Lists: Helps handle edge cases
- Used in Remove Elements

---

## Interview Tips

### Before You Code
1. Clarify the problem completely
2. Discuss approach with interviewer
3. Start with brute force, then optimize

### Common Patterns
- **Hash Map**: Used for quick lookups and counting
- **Two Pointers**: Used for arrays, strings, linked lists
- **Recursion**: Natural for tree and string problems
- **Stack**: For matching/grouping problems

### Optimization Strategies
- **Space**: Use single pass when possible
- **Time**: Avoid nested loops where possible
- **Trade-off**: Sometimes O(n) space for O(n) time is worth it

### Testing
- Always test edge cases:
  - Empty input
  - Single element
  - All duplicates
  - Already sorted/reversed

---

## How to Run

Each file contains a `main` method with working examples:

```bash
javac ArrayStringProblems.java
java ArrayStringProblems

javac HashMapSetProblems.java
java HashMapSetProblems

javac LinkedListProblems.java
java LinkedListProblems

javac StackQueueProblems.java
java StackQueueProblems

javac TreeProblems.java
java TreeProblems
```

---

## Common Mistakes to Avoid

| Problem | Mistake | Solution |
|---------|---------|----------|
| Two Sum | Modifying original array | Create new array for result |
| Stock | Not checking array length | Always check length > 1 |
| Duplicates | O(n²) approach | Use hash set |
| Anagram | Forgetting length check | Check length first |
| List Reverse | Not handling null | Check null at each step |
| Valid Parens | Wrong bracket types | Use HashMap for mapping |
| Min Stack | Forgetting to pop min | Always pop both stacks |
| Trees | Not handling null | Check null at each recursion |

---

## References

- **LeetCode**: https://leetcode.com
- **NeetCode**: https://neetcode.io (excellent visual explanations)
- **GeeksforGeeks**: https://geeksforgeeks.org

---

## Progression to Advanced

After mastering these beginner problems, progress to:
1. Medium problems in each topic
2. Data Structure specific advanced problems
3. Algorithm patterns (sliding window, binary search, etc.)
4. Dynamic Programming basics
5. Graph and Advanced Data Structures

See `../AdvancedDS/` for intermediate to advanced problems.

---

**Last Updated**: May 2026
**Total Problems**: 25 (5 per category)
**Estimated Learning Time**: 4-5 weeks
