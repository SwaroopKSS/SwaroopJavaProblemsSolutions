package com.swaroop.dsa.harshasir.Trees2;

/*

Q2. Level Order
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        Given a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).



        Problem Constraints
        1 <= number of nodes <= 105



        Input Format
        First and only argument is root node of the binary tree, A.



        Output Format
        Return a 2D integer array denoting the level order traversal of the given binary tree.



        Example Input
        Input 1:

        3
        / \
        9  20
        /  \
        15   7
        Input 2:

        1
        / \
        6   2
        /
        3


        Example Output
        Output 1:

        [
        [3],
        [9, 20],
        [15, 7]
        ]
        Output 2:

        [
        [1]
        [6, 2]
        [3]
        ]

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
public class LevelOrderTreeNode {


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


    public int[][] solve(TreeNode A) {


    }

}
