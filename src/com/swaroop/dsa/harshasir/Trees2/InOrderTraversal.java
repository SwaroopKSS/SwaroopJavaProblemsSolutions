package com.swaroop.dsa.harshasir.Trees2;

import com.swaroop.dsa.harshasir.Trees1.PreOrderTraversal;

/*

Q4. Inorder Traversal
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        Given a binary tree, return the inorder traversal of its nodes' values.



        Problem Constraints
        1 <= number of nodes <= 105



        Input Format
        First and only argument is root node of the binary tree, A.



        Output Format
        Return an integer array denoting the inorder traversal of the given binary tree.



        Example Input
        Input 1:

        1
        \
        2
        /
        3
        Input 2:

        1
        / \
        6   2
        /
        3


        Example Output
        Output 1:

        [1, 3, 2]
        Output 2:

        [6, 1, 3, 2]

*/
public class InOrderTraversal {

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

    int index = 0;

    public int size(TreeNode A){

        if( A == null){
            return 0;
        }

        int leftCount = size(A.left);
        int rightCount = size(A.right);

        return leftCount + rightCount + 1;

    }

    public int[] inorderTraversal(TreeNode A){

        int[] res = new int[size(A)];

        solve(A,res);

        return res;
    }

    public void solve(TreeNode A, int[] res){

        if(A == null){
            return;
        }

        solve(A.left, res);
        res[index] = A.val;
        index++;
        solve(A.right, res);
    }

}
