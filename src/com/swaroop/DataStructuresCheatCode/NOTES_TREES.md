# 📖 TREES - COMPLETE LEARNING GUIDE

## 🎯 What is a Tree?

A **tree** is a hierarchical data structure with:
- **Root** - top node with no parent
- **Nodes** - contain data
- **Edges** - connections between nodes
- **Leaves** - nodes with no children
- **Parent/Child** - hierarchical relationship

```
        1 (Root)
       / \
      2   3
     / \
    4   5 (Leaves)
```

---

## 📊 Tree Types

### 1. Binary Tree
Each node has at most 2 children (left, right)

```
    1
   / \
  2   3
 / \
4   5
```

### 2. Binary Search Tree (BST)
- Left child < Parent
- Right child > Parent
- Enables fast searching

```
    4
   / \
  2   6
 / \ / \
1  3 5  7
(Sorted!)
```

### 3. Balanced Tree
Difference in height between subtrees ≤ 1
- AVL Trees
- Red-Black Trees

---

## 🔄 Tree Traversals

### 1. In-Order (Left, Root, Right)
```
    1
   / \
  2   3

Order: 2 → 1 → 3
(For BST: gives SORTED output!)
```

**Code:**
```java
void inOrder(TreeNode root) {
    if (root == null) return;
    inOrder(root.left);
    System.out.println(root.val);
    inOrder(root.right);
}
```

### 2. Pre-Order (Root, Left, Right)
```
    1
   / \
  2   3

Order: 1 → 2 → 3
```

**Use:** Tree copying, serialization, BST construction

### 3. Post-Order (Left, Right, Root)
```
    1
   / \
  2   3

Order: 2 → 3 → 1
```

**Use:** Tree deletion, postfix evaluation

### 4. Level-Order (BFS)
```
    1
   / \
  2   3
 / \
4   5

Order: 1 → 2 → 3 → 4 → 5
```

**Uses Queue!**

---

## 🔥 Key Tree Concepts

### Height vs Depth
```
      1
     / \
    2   3    ← Depth 1 (distance from root)
   /
  4          ← Depth 2

Height of node 2: 1 (longest path to leaf)
Height of root: 2 (longest path in tree)
```

### Subtree Property
For any node, its subtree is also a tree

```
If node 2 is subtree:
    2
   / \
  4   5
```

### Complete Binary Tree
All levels filled except possibly last (filled left to right)

---

## 💡 Common Tree Patterns

### Pattern 1: DFS (Depth-First)
Go deep before going wide

```java
void dfs(TreeNode root) {
    if (root == null) return;
    
    // Process root
    System.out.println(root.val);
    
    // Recurse children
    dfs(root.left);
    dfs(root.right);
}
```

### Pattern 2: BFS (Breadth-First)
Go wide before going deep

```java
void bfs(TreeNode root) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        System.out.println(node.val);
        
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

### Pattern 3: Recursive with Return Value
```java
int getHeight(TreeNode root) {
    if (root == null) return 0;
    
    int leftHeight = getHeight(root.left);
    int rightHeight = getHeight(root.right);
    
    return Math.max(leftHeight, rightHeight) + 1;
}
```

### Pattern 4: Path Sum (Root to Leaf)
```java
boolean hasPathSum(TreeNode root, int target) {
    if (root == null) return false;
    if (root.left == null && root.right == null) {
        return root.val == target;
    }
    
    return hasPathSum(root.left, target - root.val) ||
           hasPathSum(root.right, target - root.val);
}
```

---

## 🎯 BST Operations

### Search
```java
TreeNode search(TreeNode root, int val) {
    if (root == null) return null;
    
    if (val < root.val) {
        return search(root.left, val);
    } else if (val > root.val) {
        return search(root.right, val);
    } else {
        return root;
    }
}

// Time: O(log n) average, O(n) worst (unbalanced)
```

### Insert
```java
TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    
    if (val < root.val) {
        root.left = insert(root.left, val);
    } else {
        root.right = insert(root.right, val);
    }
    
    return root;
}
```

### Delete
```
Case 1: No children
    Just remove

Case 2: One child
    Replace with that child

Case 3: Two children
    Find in-order successor (min in right subtree)
    Replace and delete successor
```

---

## ⚡ Time & Space Complexity

| Operation | Balanced BST | Unbalanced BST |
|-----------|--------------|----------------|
| Search | O(log n) | O(n) |
| Insert | O(log n) | O(n) |
| Delete | O(log n) | O(n) |
| Space | O(n) | O(n) |

**Key:** Balanced trees keep operations fast!

---

## 🎓 Interview Questions

### EASY ⭐
1. **Inorder/Preorder/Postorder** - Traversals
2. **Level Order** - BFS
3. **Maximum Depth** - DFS
4. **Symmetric Tree** - Mirror check
5. **Binary Tree Path Sum** - Recursive

### MEDIUM ⭐⭐
1. **Lowest Common Ancestor** - Complex DFS
2. **Validate BST** - Bounds tracking
3. **Serialize/Deserialize** - Encode/decode
4. **Construct from Traversals** - Reconstruction
5. **Zigzag Level Order** - Modified BFS

### HARD ⭐⭐⭐
1. **Binary Tree Maximum Path Sum** - Complex recursion
2. **Recover BST** - Tricky deletion
3. **Vertical Order Traversal** - Grouping
4. **Morris Traversal** - O(1) space!

---

## 🧠 Common Mistakes

### ❌ Mistake 1: Null Check
```java
// ❌ Wrong
int val = root.val;  // NPE if root is null

// ✓ Right
if (root == null) return;
int val = root.val;
```

### ❌ Mistake 2: Not Returning Value
```java
// ❌ Wrong
void insert(TreeNode root, int val) {
    root = new TreeNode(val);  // Doesn't update tree!
}

// ✓ Right
TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    // ...
    return root;
}
```

### ❌ Mistake 3: Forgetting to Recurse
```java
// ❌ Wrong - doesn't visit all nodes
void traverse(TreeNode root) {
    System.out.println(root.val);
    // Forgot to recurse on children!
}

// ✓ Right
void traverse(TreeNode root) {
    if (root == null) return;
    System.out.println(root.val);
    traverse(root.left);
    traverse(root.right);
}
```

---

## ✨ Key Takeaways

1. **DFS for depth** - recursion is natural
2. **BFS for breadth** - use queue
3. **In-Order on BST** - gives sorted result!
4. **Always null check** - most common bug
5. **Return values properly** - recursive trees need it
6. **Balanced trees** are crucial for performance
7. **Traversals** are fundamental - practice all

---

## 🎯 Practice Plan

**Week 1:**
- Implement all 4 traversals
- Solve: max depth, symmetric tree

**Week 2:**
- Master DFS pattern
- Solve: path sum, valid BST

**Week 3:**
- Learn BFS pattern
- Solve: level order, zigzag

**Week 4:**
- Solve 5 medium problems
- Understand serialization and LCA

---

**Next:** Graphs - trees are just special graphs!
