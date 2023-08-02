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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        if (A == null) {
            return new int[0][];
        }

        List<List<Integer>> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(A);

        while (!queue.isEmpty()) {
            List<Integer> levelValues = new ArrayList<>();
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelValues.add(node.val);

                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            result.add(levelValues);
        }

        int[][] levelOrderTraversal = new int[result.size()][];
        for (int i = 0; i < result.size(); i++) {
            List<Integer> level = result.get(i);
            levelOrderTraversal[i] = new int[level.size()];

            for (int j = 0; j < level.size(); j++) {
                levelOrderTraversal[i][j] = level.get(j);
            }
        }

        return levelOrderTraversal;


    }

}



//Certainly! Let's break down the solve method step by step:
//
//        First, we check if the input TreeNode A is null. If it's null, there are no nodes in the tree, so we return an empty 2D array new int[0][].
//
//        Next, we create a list of lists result to store the level order traversal. Each inner list will represent a level in the tree, and the outer list will contain all the levels.
//
//        We use a Queue to perform a breadth-first search (BFS) traversal of the binary tree. We add the root node A to the queue.
//
//        While the queue is not empty, we process each level of the tree:
//
//        a. We initialize an empty list levelValues to store the node values at the current level.
//
//        b. We get the size of the queue at the beginning of each level. This will be used to process all the nodes at the current level before moving to the next level.
//
//        c. We loop through the current level nodes (based on the level size) and dequeue each node from the queue. We add the node's value to the levelValues list.
//
//        d. For each dequeued node, we enqueue its left and right child nodes if they exist, as we need to process them in the next level.
//
//        e. After processing all nodes at the current level, we add the levelValues list to the result list, representing a level of the tree.
//
//        f. If there are more nodes in the queue, we increment the level variable to move to the next level.
//
//        After the BFS traversal is complete, we have the result list containing the level order traversal of the tree. However, the problem requires the output to be in the form of a 2D integer array. Therefore, we convert the result list of lists into a 2D array levelOrderTraversal.
//
//        We iterate through each level in the result list and convert the corresponding list of integers to an array using the toArray method. We store each level array in the levelOrderTraversal 2D array.
//
//        Finally, we return the levelOrderTraversal 2D array, which represents the level order traversal of the binary tree.
