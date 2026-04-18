package com.swaroop.DataStructuresCheatCode;

/**
 * ADVANCED TREE PROBLEMS - BEYOND BASIC TRAVERSALS
 * 
 * 💡 ADVANCED TECHNIQUES:
 * 1. Self-Balancing Trees (AVL, Red-Black concepts)
 * 2. Lowest Common Ancestor (LCA)
 * 3. Tree Serialization
 * 4. Binary Search Tree Validation
 * 5. Path Sum Problems
 * 6. Vertical Order Traversal
 * 7. Morris Traversal
 */

public class AdvancedTreeProblems {
    
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int v) { val = v; }
    }
    
    // ========================================
    // 1. LOWEST COMMON ANCESTOR (LCA)
    // ========================================
    
    /**
     * LCA - Lowest Common Ancestor
     * 
     * Approach 1: Simple recursive (if both p and q in tree)
     * Time: O(n)
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }
        
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        
        // Both found in left subtree
        if (left != null && right != null) return root;
        
        // Both found in one subtree
        return left != null ? left : right;
    }
    
    /**
     * LCA with parent pointers
     * Time: O(h) where h = height
     */
    public static TreeNode lcaWithParent(TreeNode p, TreeNode q) {
        int depthP = getDepth(p);
        int depthQ = getDepth(q);
        
        // Make p the deeper node
        if (depthP < depthQ) {
            return lcaWithParent(q, p);
        }
        
        // Bring p to same level as q
        for (int i = 0; i < depthP - depthQ; i++) {
            // p = p.parent;  // would need parent pointer
        }
        
        // Move both up until they meet
        while (p != q) {
            // p = p.parent;
            // q = q.parent;
        }
        
        return p;
    }
    
    private static int getDepth(TreeNode node) {
        int depth = 0;
        // while (node.parent != null) { depth++; node = node.parent; }
        return depth;
    }
    
    // ========================================
    // 2. TREE SERIALIZATION & DESERIALIZATION
    // ========================================
    
    /**
     * Serialize tree to string (level-order)
     * 
     * Time: O(n)
     */
    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Deserialize string to tree
     */
    public static TreeNode deserialize(String data) {
        String[] values = data.split(",");
        if (values[0].equals("null")) return null;
        
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // Left child
            if (!values[i].equals("null")) {
                node.left = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(node.left);
            }
            i++;
            
            // Right child
            if (i < values.length && !values[i].equals("null")) {
                node.right = new TreeNode(Integer.parseInt(values[i]));
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    // ========================================
    // 3. VALIDATE BINARY SEARCH TREE
    // ========================================
    
    /**
     * Validate if tree is valid BST
     * 
     * Approach: Track min/max bounds
     * Time: O(n)
     */
    public static boolean isValidBST(TreeNode root) {
        return validateBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private static boolean validateBST(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        return validateBST(node.left, min, node.val) &&
               validateBST(node.right, node.val, max);
    }
    
    // ========================================
    // 4. MORRIS TRAVERSAL (No Extra Space)
    // ========================================
    
    /**
     * Morris In-Order Traversal
     * Space: O(1) instead of O(h)
     * 
     * Trick: Create temporary links to inorder successor
     */
    public static java.util.List<Integer> morrisInOrder(TreeNode root) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left == null) {
                result.add(curr.val);
                curr = curr.right;
            } else {
                // Find inorder predecessor
                TreeNode prev = curr.left;
                while (prev.right != null && prev.right != curr) {
                    prev = prev.right;
                }
                
                if (prev.right == null) {
                    // Link to current
                    prev.right = curr;
                    curr = curr.left;
                } else {
                    // Unlink and process
                    prev.right = null;
                    result.add(curr.val);
                    curr = curr.right;
                }
            }
        }
        
        return result;
    }
    
    // ========================================
    // 5. PATH SUM PROBLEMS
    // ========================================
    
    /**
     * Path Sum - Does path root->leaf equal target?
     * Time: O(n)
     */
    public static boolean hasPathSum(TreeNode root, int target) {
        if (root == null) return false;
        
        if (root.left == null && root.right == null) {
            return root.val == target;
        }
        
        return hasPathSum(root.left, target - root.val) ||
               hasPathSum(root.right, target - root.val);
    }
    
    /**
     * Path Sum II - Find all paths with target sum
     */
    public static java.util.List<java.util.List<Integer>> pathSum(TreeNode root, int target) {
        java.util.List<java.util.List<Integer>> result = new java.util.ArrayList<>();
        pathSumHelper(root, target, new java.util.ArrayList<>(), result);
        return result;
    }
    
    private static void pathSumHelper(TreeNode node, int remaining,
                                      java.util.List<Integer> path,
                                      java.util.List<java.util.List<Integer>> result) {
        if (node == null) return;
        
        path.add(node.val);
        
        if (node.left == null && node.right == null && remaining == node.val) {
            result.add(new java.util.ArrayList<>(path));
        } else {
            pathSumHelper(node.left, remaining - node.val, path, result);
            pathSumHelper(node.right, remaining - node.val, path, result);
        }
        
        path.remove(path.size() - 1);
    }
    
    /**
     * Path Sum III - Any path (not just root to leaf)
     * 
     * Time: O(n²) - prefix sum approach
     */
    public static int pathSumIII(TreeNode root, int target) {
        java.util.Map<Integer, Integer> prefixSum = new java.util.HashMap<>();
        prefixSum.put(0, 1);
        return dfsPathSum(root, 0, target, prefixSum);
    }
    
    private static int dfsPathSum(TreeNode node, int currentSum, int target,
                                 java.util.Map<Integer, Integer> prefixSum) {
        if (node == null) return 0;
        
        currentSum += node.val;
        int count = prefixSum.getOrDefault(currentSum - target, 0);
        
        prefixSum.put(currentSum, prefixSum.getOrDefault(currentSum, 0) + 1);
        
        count += dfsPathSum(node.left, currentSum, target, prefixSum);
        count += dfsPathSum(node.right, currentSum, target, prefixSum);
        
        prefixSum.put(currentSum, prefixSum.get(currentSum) - 1);
        
        return count;
    }
    
    // ========================================
    // 6. VERTICAL ORDER TRAVERSAL
    // ========================================
    
    /**
     * Vertical Order Traversal of Tree
     * 
     * Group nodes by vertical position (column)
     * Time: O(n log n)
     */
    public static java.util.List<java.util.List<Integer>> verticalOrder(TreeNode root) {
        java.util.Map<Integer, java.util.List<Integer>> map = new java.util.TreeMap<>();
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        java.util.Queue<Integer> colQueue = new java.util.LinkedList<>();
        
        queue.offer(root);
        colQueue.offer(0);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int col = colQueue.poll();
            
            map.putIfAbsent(col, new java.util.ArrayList<>());
            map.get(col).add(node.val);
            
            if (node.left != null) {
                queue.offer(node.left);
                colQueue.offer(col - 1);
            }
            
            if (node.right != null) {
                queue.offer(node.right);
                colQueue.offer(col + 1);
            }
        }
        
        return new java.util.ArrayList<>(map.values());
    }
    
    // ========================================
    // 7. MAXIMUM PATH SUM
    // ========================================
    
    /**
     * Binary Tree Maximum Path Sum
     * 
     * Path = any sequence of nodes from any node to any node
     * Time: O(n)
     */
    static class Result {
        int maxSum;
    }
    
    public static int maxPathSum(TreeNode root) {
        Result result = new Result();
        result.maxSum = Integer.MIN_VALUE;
        maxPathSumHelper(root, result);
        return result.maxSum;
    }
    
    private static int maxPathSumHelper(TreeNode node, Result result) {
        if (node == null) return 0;
        
        // Max path sum including this node
        int left = Math.max(0, maxPathSumHelper(node.left, result));
        int right = Math.max(0, maxPathSumHelper(node.right, result));
        
        // Max path through this node
        int throughNode = left + node.val + right;
        result.maxSum = Math.max(result.maxSum, throughNode);
        
        // Return max path that can be extended upward
        return node.val + Math.max(left, right);
    }
    
    // ========================================
    // MAIN - Test Advanced Tree Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== ADVANCED TREE PROBLEMS ===\n");
        
        // Build test tree
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        
        // 1. LCA
        System.out.println("1. LOWEST COMMON ANCESTOR");
        TreeNode p = root.left;
        TreeNode q = root.left.right;
        System.out.println("LCA of 5 and 2: " + lowestCommonAncestor(root, p, q).val + "\n");
        
        // 2. Serialization
        System.out.println("2. TREE SERIALIZATION");
        String serialized = serialize(root);
        System.out.println("Serialized (first 50): " + serialized.substring(0, Math.min(50, serialized.length())));
        TreeNode deserialized = deserialize(serialized);
        System.out.println("Deserialized root: " + deserialized.val + "\n");
        
        // 3. Morris Traversal
        System.out.println("3. MORRIS IN-ORDER TRAVERSAL");
        System.out.println("Morris traversal: " + morrisInOrder(root) + "\n");
        
        // 4. Path Sum
        System.out.println("4. PATH SUM");
        System.out.println("Has path sum 11: " + hasPathSum(root, 11) + "\n");
        
        // 5. Vertical Order
        System.out.println("5. VERTICAL ORDER");
        System.out.println("Vertical order: " + verticalOrder(root) + "\n");
        
        // 6. Max Path Sum
        System.out.println("6. MAXIMUM PATH SUM");
        System.out.println("Max path sum: " + maxPathSum(root));
    }
}
