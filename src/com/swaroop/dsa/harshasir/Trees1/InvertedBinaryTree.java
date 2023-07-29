package com.swaroop.dsa.harshasir.Trees1;

/*

Q7. Invert the Binary Tree
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        Given a binary tree A, invert the binary tree and return it.

        Inverting refers to making the left child the right child and vice versa.



        Problem Constraints
        1 <= size of tree <= 100000



        Input Format
        First and only argument is the head of the tree A.



        Output Format
        Return the head of the inverted tree.



        Example Input
        Input 1:


        1
        /   \
        2     3
        Input 2:


        1
        /   \
        2     3
        / \   / \
        4   5 6   7


        Example Output
        Output 1:


        1
        /   \
        3     2
        Output 2:


        1
        /   \
        3     2
        / \   / \
        7   6 5   4

*/
public class InvertedBinaryTree {
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
    public static void main(String args[]){

    }


    public static TreeNode invertTree(TreeNode A){

        if(A == null){
            return null;
        }
        TreeNode temp = A.left;
        A.left= invertTree(A.right);
        A.right = invertTree(temp);

        return A;
    }


}
