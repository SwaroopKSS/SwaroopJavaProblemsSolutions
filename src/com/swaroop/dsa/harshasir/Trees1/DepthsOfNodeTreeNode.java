package com.swaroop.dsa.harshasir.Trees1;

/*

Q8. Depth of Each Node
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description

        You are given the root node of a binary tree A. Each node has a value val and depth depth.

        Initially depth of each node is set to -1. You have to fill the depth of each node with its correct value.
        Depth of a node T is the number of nodes along the longest path from the root node down to node T. Also, depth of root node is always equal to 1.



        Problem Constraints

        1 <= Number of nodes <= 105

        0 <= Value of each node <= 109

        Initially, Depth of each node(depth) is set to -1.



        Input Format

        First and only argument is a tree node A.



        Output Format

        There is no output required.



        Example Input

        Input 1:


        Values =  1        Depths = -1
        / \                / \
        4   3             -1  -1
        Input 2:


        Values =  1        Depths = -1
        / \                / \
        4   3             -1  -1
        /                  /
        2                 -1


        Example Output

        Output 1:


        Values =  1        Depths =  1
        / \                / \
        4   3              2   2
        Output 2:


        Values =  1        Depths =  1
        / \                / \
        4   3              2   2
        /                  /
        2                  3



*/


/*
    Definition for TreeNode.
    static public class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; this.depth = -1;}
    }
    */
public class DepthsOfNodeTreeNode {
    static public class TreeNode {
        public int val;
        public int depth;
        public TreeNode left;
        public TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; this.depth = -1;}
    }


    static void solve(TreeNode A){

        updateNodesDepths(A, 1);

    }

    private static void updateNodesDepths(TreeNode A, int depth) {

        if(null == A){
            return ;
        }

        A.depth = depth;

        updateNodesDepths(A.left, depth+1);
        updateNodesDepths(A.right, depth+1);

    }
}
