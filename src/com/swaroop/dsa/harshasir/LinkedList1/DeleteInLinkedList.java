package com.swaroop.dsa.harshasir.LinkedList1;

/*
Problem Description
        You are given the head of a linked list A and an integer B. Delete the B-th node from the linked list.

        Note : Follow 0-based indexing for the node numbering.



        Problem Constraints
        1 <= size of linked list <= 105
        1 <= value of nodes <= 109
        0 <= B < size of linked list



        Input Format
        The first argument A is the head of a linked list.

        The second arguement B is an integer.



        Output Format
        Return the head of the linked list after deletion



        Example Input
        Input 1:
        A = 1 -> 2 -> 3
        B = 1
        Input 2:
        A = 4 -> 3 -> 2 -> 1
        B = 0


        Example Output
        Output 1:
        1 -> 3
        Output 2:
        3 -> 2 -> 1

*/
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }
}
public class DeleteInLinkedList {

    public ListNode solve(ListNode A, int B) {
        if (B == 0) {
            return A.next; // Special case: Delete the first node head = A
        }

        ListNode prev = null;
        ListNode current = A;

        for (int i = 0; i < B; i++) {
            prev = current;
            current = current.next;
        }

        if (prev != null) {
            prev.next = current.next;
        }

        return A;
    }

    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example input
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        int B1 = 1;

        ListNode head2 = new ListNode(4);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(2);
        head2.next.next.next = new ListNode(1);
        int B2 = 0;

        LinkedListDeletion solution = new LinkedListDeletion();

        ListNode newHead1 = solution.deleteBthNode(head1, B1);
        printList(newHead1); // Output: 1 3

        ListNode newHead2 = solution.deleteBthNode(head2, B2);
        printList(newHead2); // Output: 3 2 1
    }
}
