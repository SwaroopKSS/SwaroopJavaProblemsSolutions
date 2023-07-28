package com.swaroop.dsa.harshasir.Trees1;


/*

Q3. Sum of nodes of a Binary Tree
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        Given the root of a binary tree A. Return the sum of all the nodes of the binary tree.


        Problem Constraints
        1 <= Number of nodes in A <= 104

        1 <= value of each node <= 104



        Input Format
        First argument is the root of binary tree A.



        Output Format
        Return an integer denoting the sum of all the nodes



        Example Input
        Input 1:

        A =   2
        / \
        1   4
        /
        5


        Input 2:

        A =   3
        / \
        6  1
        \   \
        2   7


        Example Output
        Output 1:

        12
        Output 2:

        19


*/
public class SumOfNodesInBt {
    public static void main(String args[]){

    }

    public static int sumOfNodes(NodesCount.TreeNode A){

        if( A == null){
            return 0;
        }

        int leftSum = sumOfNodes(A.left);
        int rightSum = sumOfNodes(A.right);

        return leftSum + rightSum + A.val;

    }


    class TreeNode {
        int val;
        NodesCount.TreeNode left;
        NodesCount.TreeNode right;
        TreeNode(int x) {
            val = x;
            left=null;
            right=null;
        }
    }
}
