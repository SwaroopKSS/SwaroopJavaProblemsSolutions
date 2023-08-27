
/*

Problem Description
You are given a singly linked list having head node A. You need to print the linked list in reverse order.

Note :- The node values must be space separated. You must give a newline after printing all the nodes.



Problem Constraints
1 <= Length of linked list <= 105

1 <= Value of each linked list node <= 109



Input Format
First and only argument is a linked-list node A.



Output Format
Print the linked list in reverse order



Example Input
Input 1:

 A = 1 -> 2 -> 3 -> 4 -> 5 -> NULL
Input 2:

 A = 3 -> NULL


Example Output
Output 1:

 5 4 3 2 1
Output 2:

 3

*/

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     public int val;
 *     public ListNode next;
 *     ListNode(int x) { val = x; next = null; }
 * }
 */
public class Solution {

    public void solve(ListNode A) {
        printReverseLinkedList(A);
        System.out.println();

    }

    private void printReverseLinkedList(ListNode node) {
        if (node == null) {
            return;
        }

        printReverseLinkedList(node.next);
        System.out.print(node.val + " ");
    }
}
