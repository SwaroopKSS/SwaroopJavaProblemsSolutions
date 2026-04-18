package com.swaroop.dsa.trees;

import java.util.*;

/**
 * TREES - HIERARCHICAL DATA STRUCTURE
 * 
 * 💡 CHEAT MEMORY:
 * Tree = Hierarchical, root at top, leaves at bottom
 * Binary Tree: Each node has max 2 children
 * BST: Left < Root < Right (for search)
 * 
 * KEY TRAVERSALS:
 * InOrder: Left → Root → Right (sorted for BST)
 * PreOrder: Root → Left → Right (copy tree)
 * PostOrder: Left → Right → Root (delete tree)
 * LevelOrder: Top to bottom, left to right (BFS)
 */

public class TreeBasics {
    
    // ========================================
    // TREE NODE CLASS
    // ========================================
    
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        public TreeNode(int val) {
            this.val = val;
        }
    }
    
    // ========================================
    // 1. TREE TRAVERSALS
    // ========================================
    
    /**
     * InOrder Traversal: Left → Root → Right
     * For BST: gives sorted sequence
     * Time: O(n), Space: O(h) where h = height
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }
    
    private static void inorderHelper(TreeNode node, List<Integer> result) {
        if (node == null) return;
        inorderHelper(node.left, result);       // Left
        result.add(node.val);                   // Root
        inorderHelper(node.right, result);      // Right
    }
    
    /**
     * PreOrder Traversal: Root → Left → Right
     * Good for copying tree
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.add(root.val);                      // Root
            result.addAll(preorderTraversal(root.left));   // Left
            result.addAll(preorderTraversal(root.right));  // Right
        }
        return result;
    }
    
    /**
     * PostOrder Traversal: Left → Right → Root
     * Good for deleting tree
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root != null) {
            result.addAll(postorderTraversal(root.left));   // Left
            result.addAll(postorderTraversal(root.right));  // Right
            result.add(root.val);                      // Root
        }
        return result;
    }
    
    /**
     * Level Order Traversal (BFS)
     * Visit nodes level by level
     */
    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            
            result.add(level);
        }
        
        return result;
    }
    
    // ========================================
    // 2. BINARY SEARCH TREE OPERATIONS
    // ========================================
    
    /**
     * Validate Binary Search Tree
     * 
     * BST Property: Left < Root < Right
     * Need to track min/max bounds
     */
    public static boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        // Check if current node violates BST property
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        // Check left and right subtrees with updated bounds
        return isValidBSTHelper(node.left, min, node.val) &&
               isValidBSTHelper(node.right, node.val, max);
    }
    
    /**
     * Search in BST
     * Time: O(log n) average, O(n) worst case
     */
    public static TreeNode searchBST(TreeNode root, int target) {
        if (root == null) return null;
        
        if (root.val == target) {
            return root;
        } else if (target < root.val) {
            return searchBST(root.left, target);  // Search left
        } else {
            return searchBST(root.right, target);  // Search right
        }
    }
    
    /**
     * Insert into BST
     */
    public static TreeNode insertBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        if (val < root.val) {
            root.left = insertBST(root.left, val);
        } else if (val > root.val) {
            root.right = insertBST(root.right, val);
        }
        
        return root;
    }
    
    /**
     * Delete from BST
     * Three cases:
     * 1. Leaf node - just delete
     * 2. One child - replace with child
     * 3. Two children - replace with inorder successor/predecessor
     */
    public static TreeNode deleteBST(TreeNode root, int val) {
        if (root == null) return null;
        
        if (val < root.val) {
            root.left = deleteBST(root.left, val);
        } else if (val > root.val) {
            root.right = deleteBST(root.right, val);
        } else {
            // Found node to delete
            
            // Case 1: No children or 1 child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;
            
            // Case 2: Two children - find inorder successor
            TreeNode minNode = findMin(root.right);
            root.val = minNode.val;
            root.right = deleteBST(root.right, minNode.val);
        }
        
        return root;
    }
    
    private static TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
    
    // ========================================
    // 3. TREE PROBLEMS
    // ========================================
    
    /**
     * Maximum Depth of Binary Tree
     * Longest path from root to leaf
     */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
    
    /**
     * Lowest Common Ancestor (LCA)
     * Find common ancestor of two nodes
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;
        
        // If both p and q are in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // If both p and q are in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // One on each side or root is one of them
        return root;
    }
    
    /**
     * Path Sum
     * Check if path from root to leaf has target sum
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        
        // Leaf node
        if (root.left == null && root.right == null) {
            return root.val == targetSum;
        }
        
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }
    
    // ========================================
    // 4. HELPER FUNCTIONS
    // ========================================
    
    public static TreeNode createBST(int[] arr) {
        TreeNode root = null;
        for (int val : arr) {
            root = insertBST(root, val);
        }
        return root;
    }
    
    // ========================================
    // MAIN - Test all functions
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== TREE BASICS ===\n");
        
        // Create BST: 4, 2, 6, 1, 3, 5, 7
        TreeNode root = new TreeNode(4);
        root = insertBST(root, 2);
        root = insertBST(root, 6);
        root = insertBST(root, 1);
        root = insertBST(root, 3);
        root = insertBST(root, 5);
        root = insertBST(root, 7);
        
        // 1. Traversals
        System.out.println("1. TREE TRAVERSALS");
        System.out.println("InOrder: " + inorderTraversal(root) + " (Sorted)");
        System.out.println("PreOrder: " + preorderTraversal(root));
        System.out.println("PostOrder: " + postorderTraversal(root));
        System.out.println("Level Order: " + levelOrderTraversal(root) + "\n");
        
        // 2. Validate BST
        System.out.println("2. VALIDATE BST");
        System.out.println("Is Valid BST: " + isValidBST(root) + "\n");
        
        // 3. Search in BST
        System.out.println("3. SEARCH IN BST");
        TreeNode found = searchBST(root, 3);
        System.out.println("Search for 3: " + (found != null ? "Found" : "Not found"));
        System.out.println("Search for 8: " + (searchBST(root, 8) != null ? "Found" : "Not found") + "\n");
        
        // 4. Tree Depth
        System.out.println("4. TREE DEPTH");
        System.out.println("Max Depth: " + maxDepth(root) + "\n");
        
        // 5. Path Sum
        System.out.println("5. PATH SUM");
        System.out.println("Path sum = 9 exists: " + hasPathSum(root, 9));
        System.out.println("Path sum = 15 exists: " + hasPathSum(root, 15));
    }
}
