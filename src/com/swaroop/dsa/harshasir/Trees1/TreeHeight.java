package com.swaroop.dsa.harshasir.Trees1;


/*

Q2. Tree Height
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        You are given the root node of a binary tree A. You have to find the height of the given tree.

        A binary tree's height is the number of nodes along the longest path from the root node down to the farthest leaf node.



        Problem Constraints
        1 <= Number of nodes in the tree <= 105

        0 <= Value of each node <= 109



        Input Format
        The first and only argument is a tree node A.



        Output Format
        Return an integer denoting the height of the tree.



        Example Input
        Input 1:

        Values =  1
        / \
        4   3
        Input 2:


        Values =  1
        / \
        4   3
        /
        2


        Example Output
        Output 1:

        2
        Output 2:

        3
*/

/*
*
 * Definition for binary tree

 */


public class TreeHeight {

    public static void main (String args[]){

    }

    public static int height(TreeNode root){

        if(root == null){
            return 0;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);

        return Math.max(leftHeight, rightHeight) + 1;

    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
            left=null;
            right=null;
        }
    }

}
