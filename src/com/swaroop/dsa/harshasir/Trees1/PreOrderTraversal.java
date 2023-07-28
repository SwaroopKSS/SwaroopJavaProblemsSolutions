package com.swaroop.dsa.harshasir.Trees1;


/*

Q5. Preorder Traversal
        Unsolved
        feature icon
        Using hints is now penalty free
        Use Hint
        Problem Description
        Given a binary tree, return the preorder traversal of its nodes values.



        Problem Constraints
        1 <= number of nodes <= 105



        Input Format
        First and only argument is root node of the binary tree, A.



        Output Format
        Return an integer array denoting the preorder traversal of the given binary tree.



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

        [1, 2, 3]
        Output 2:

        [1, 6, 2, 3]


        Example Explanation
        Explanation 1:

        The Preoder Traversal of the given tree is [1, 2, 3].
        Explanation 2:

        The Preoder Traversal of the given tree is [1, 6, 2, 3].

*/
public class PreOrderTraversal {

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

    static int index = 0;

    public static int size(TreeNode A){

        if( A == null){
            return 0;
        }

        int leftCount = size(A.left);
        int rightCount = size(A.right);

        return leftCount + rightCount + 1;

    }

    public static int[] preOrder(TreeNode A){

        int[] res = new int[size(A)];

        solve(A,res);

        return res;
    }

    public static void solve(TreeNode A, int[] res){

        if(A == null){
           return;
        }

        res[index] = A.val;
        index++;
        solve(A.left, res);
        solve(A.right, res);
    }



}
