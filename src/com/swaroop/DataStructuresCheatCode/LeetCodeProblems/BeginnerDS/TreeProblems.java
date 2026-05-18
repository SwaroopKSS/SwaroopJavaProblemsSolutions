package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.BeginnerDS;

import java.util.*;

/**
 * TREE PROBLEMS - BEGINNER LEVEL
 * 
 * Problems covered:
 * 1. LeetCode 226: Invert Binary Tree
 * 2. LeetCode 101: Symmetric Tree
 * 3. LeetCode 104: Maximum Depth of Binary Tree
 * 4. LeetCode 111: Minimum Depth of Binary Tree
 * 5. LeetCode 100: Same Tree
 */

public class TreeProblems {
    
    // ========================================
    // TREE NODE DEFINITION
    // ========================================
    
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        
        public TreeNode(int val) {
            this.val = val;
        }
        
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    // ========================================
    // PROBLEM 1: LeetCode 226 - Invert Binary Tree
    // ========================================
    
    /**
     * LeetCode 226: Invert Binary Tree
     * 
     * Given the root of a binary tree, invert the tree, and return its root.
     * Invert means swap left and right subtrees of every node.
     * 
     * Example:
     *     4              4
     *    / \      =>    / \
     *   2   7          7   2
     *  / \ / \        / \ / \
     * 1  3 6 9       9 6 3  1
     * 
     * Time: O(n) where n is number of nodes
     * Space: O(h) where h is height (recursion stack)
     */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        
        // Swap left and right subtrees
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        
        // Recursively invert left and right subtrees
        invertTree(root.left);
        invertTree(root.right);
        
        return root;
    }
    
    /**
     * Iterative approach using Queue
     * Time: O(n), Space: O(w) where w is maximum width
     */
    public static TreeNode invertTreeIterative(TreeNode root) {
        if (root == null) return null;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap left and right
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        return root;
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 101 - Symmetric Tree
    // ========================================
    
    /**
     * LeetCode 101: Symmetric Tree
     * 
     * Given the root of a binary tree, check whether it is a mirror of itself
     * (i.e., symmetric around its center).
     * 
     * Example:
     *     1           1
     *    / \         / \
     *   2   2   ->  2   2
     *  / \ / \     / \ / \
     * 3  4 4  3   3  4 4  3
     * 
     * Time: O(n)
     * Space: O(h) for recursion stack
     */
    public static boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left, root.right);
    }
    
    private static boolean isMirror(TreeNode left, TreeNode right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;
        
        return left.val == right.val && 
               isMirror(left.left, right.right) && 
               isMirror(left.right, right.left);
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 104 - Maximum Depth of Binary Tree
    // ========================================
    
    /**
     * LeetCode 104: Maximum Depth of Binary Tree
     * 
     * Given the root of a binary tree, return its maximum depth.
     * The maximum depth is the number of nodes along the longest path
     * from the root node down to the farthest leaf node.
     * 
     * Example:
     *     3
     *    / \
     *   9  20
     *      /  \
     *     15   7
     * Maximum depth = 3
     * 
     * Time: O(n)
     * Space: O(h) for recursion stack
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
    
    /**
     * Iterative approach using Queue (BFS)
     * Time: O(n), Space: O(w) where w is maximum width
     */
    public static int maxDepthIterative(TreeNode root) {
        if (root == null) return 0;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            depth++;
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return depth;
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 111 - Minimum Depth of Binary Tree
    // ========================================
    
    /**
     * LeetCode 111: Minimum Depth of Binary Tree
     * 
     * Given a binary tree, find its minimum depth.
     * The minimum depth is the number of nodes along the shortest path
     * from the root node down to the nearest leaf node.
     * 
     * Note: A leaf is a node with no children.
     * 
     * Example:
     *     3
     *    / \
     *   9  20
     *      /  \
     *     15   7
     * Minimum depth = 2 (3 -> 9)
     * 
     * Time: O(n) worst case, O(h) average (with early termination)
     * Space: O(h) for recursion stack
     */
    public static int minDepth(TreeNode root) {
        if (root == null) return 0;
        
        // If one subtree is null, we need to go down the other
        if (root.left == null) return minDepth(root.right) + 1;
        if (root.right == null) return minDepth(root.left) + 1;
        
        // Both subtrees exist, take minimum
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }
    
    // ========================================
    // PROBLEM 5: LeetCode 100 - Same Tree
    // ========================================
    
    /**
     * LeetCode 100: Same Tree
     * 
     * Given the roots of two binary trees p and q, return true if the trees
     * are the same. Two binary trees are the same if they are structurally
     * identical and the nodes have the same values.
     * 
     * Example:
     *   p =     1           q =    1
     *          / \                / \
     *         2   3              2   3
     * Output: true
     * 
     * Time: O(min(m, n))
     * Space: O(min(h1, h2)) for recursion stack
     */
    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        
        return p.val == q.val && 
               isSameTree(p.left, q.left) && 
               isSameTree(p.right, q.right);
    }
    
    // ========================================
    // UTILITY METHODS
    // ========================================
    
    public static void inorder(TreeNode root) {
        if (root == null) return;
        inorder(root.left);
        System.out.print(root.val + " ");
        inorder(root.right);
    }
    
    public static void levelOrder(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                System.out.print(node.val + " ");
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        System.out.println();
    }
    
    // ========================================
    // MAIN - Test Tree Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== TREE PROBLEMS - BEGINNER ===\n");
        
        // Problem 1: Invert Binary Tree
        System.out.println("1. LeetCode 226: Invert Binary Tree");
        TreeNode root1 = new TreeNode(4);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(7);
        root1.left.left = new TreeNode(1);
        root1.left.right = new TreeNode(3);
        root1.right.left = new TreeNode(6);
        root1.right.right = new TreeNode(9);
        
        System.out.print("Original tree (inorder): ");
        inorder(root1);
        System.out.println();
        
        invertTree(root1);
        System.out.print("Inverted tree (inorder): ");
        inorder(root1);
        System.out.println("\n");
        
        // Problem 2: Symmetric Tree
        System.out.println("2. LeetCode 101: Symmetric Tree");
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(4);
        root2.right.left = new TreeNode(4);
        root2.right.right = new TreeNode(3);
        
        System.out.println("Is symmetric: " + isSymmetric(root2) + "\n");
        
        // Problem 3: Maximum Depth
        System.out.println("3. LeetCode 104: Maximum Depth of Binary Tree");
        TreeNode root3 = new TreeNode(3);
        root3.left = new TreeNode(9);
        root3.right = new TreeNode(20);
        root3.right.left = new TreeNode(15);
        root3.right.right = new TreeNode(7);
        
        System.out.println("Maximum depth: " + maxDepth(root3) + "\n");
        
        // Problem 4: Minimum Depth
        System.out.println("4. LeetCode 111: Minimum Depth of Binary Tree");
        System.out.println("Minimum depth: " + minDepth(root3) + "\n");
        
        // Problem 5: Same Tree
        System.out.println("5. LeetCode 100: Same Tree");
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);
        p.right = new TreeNode(3);
        
        TreeNode q = new TreeNode(1);
        q.left = new TreeNode(2);
        q.right = new TreeNode(3);
        
        System.out.println("Are trees same: " + isSameTree(p, q));
    }
}
