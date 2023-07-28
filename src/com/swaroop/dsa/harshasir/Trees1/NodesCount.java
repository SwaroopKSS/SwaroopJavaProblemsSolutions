package com.swaroop.dsa.harshasir.Trees1;

/*
Q1. Nodes Count
Unsolved
feature icon
Using hints is now penalty free
Use Hint
Problem Description
You are given the root node of a binary tree A. You have to find the number of nodes in this tree.



Problem Constraints
1 <= Number of nodes in the tree <= 105

0 <= Value of each node <= 107



Input Format
The first and only argument is a tree node A.



Output Format
Return an integer denoting the number of nodes of the tree.



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

 3
Output 2:

 4

 */


/**
 * Definition for binary tree
 * class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) {
 *      val = x;
 *      left=null;
 *      right=null;
 *     }
 * }
 */

public class NodesCount {

    public static void main(String args[]){

    }

    public static int countNodes(TreeNode A){

        if( A == null){
            return 0;
        }

        int leftCount = countNodes(A.left);
        int rightCount = countNodes(A.right);

        return leftCount + rightCount + 1;

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
