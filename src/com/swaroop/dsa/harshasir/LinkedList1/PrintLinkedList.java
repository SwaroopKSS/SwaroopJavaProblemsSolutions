package com.swaroop.dsa.harshasir.LinkedList1;

/*

Problem Description
        You are given A which is the head of a linked list. Print the linked list in space separated manner.

        Note : The last node value must also be succeeded by a space and after printing the entire list you should print a new line



        Problem Constraints
        1 <= size of linked list <= 105

        1 <= value of nodes <= 109



        Input Format
        The first argument A is the head of a linked list.


        Output Format
        You dont need to return anything


        Example Input
        Input 1:
        A = 1 -> 2 -> 3
        Input 2:
        A = 4 -> 3 -> 2 -> 1


        Example Output
        Output 1:
        1 2 3
        Output 2:
        4 3 2 1

*/

/*
*
 * Definition for singly-linked list.
 */
 class ListNode {
      public int val;
      public ListNode next;
      ListNode(int x) { val = x; next = null; }
  }

public class PrintLinkedList {
    public void solve(ListNode A) {
        ListNode current = A;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println(); // Print a new line after printing the entire list
    }
}
