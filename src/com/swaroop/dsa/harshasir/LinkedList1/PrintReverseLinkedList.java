package com.swaroop.dsa.harshasir.LinkedList1;

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

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }
}


public class PrintReverseLinkedList {
    /*public void solve(ListNode A) {
    }*/

    public static void printReverse(ListNode head) {
        if (head == null) {
            return;
        }
        printReverse(head.next);
        System.out.print(head.val + " ");
    }

    public static void main(String[] args) {
        // Create linked list
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        // Print linked list in reverse
       // ReverseLinkedListPrint solution = new ReverseLinkedListPrint();
        printReverse(head);
        System.out.println(); // Print newline at the end
    }
}
