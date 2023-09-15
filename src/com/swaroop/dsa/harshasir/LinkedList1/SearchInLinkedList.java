package com.swaroop.dsa.harshasir.LinkedList1;

/*

Problem Description
        You are given the head of a linked list A and an integer B, check if there is any node which contains this value B.

        Return 1 if such a node is present else return 0.



        Problem Constraints
        1 <= size of linked list <= 105
        1 <= value of nodes <= 109
        1 <= B <= 109



        Input Format
        The first argument A is the head of a linked list.

        The second arguement B is an integer.



        Output Format
        Return 1 if such a node is present otherwise return 0.


        Example Input
        Input 1:
        A = 1 -> 2 -> 3
        B = 4
        Input 2:
        A = 4 -> 3 -> 2 -> 1
        B = 4


        Example Output
        Output 1:
        0
        Output 2:
        1

*/

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }
}

public class SearchInLinkedList {
    /*public int solve(ListNode A, int B) {
    }*/
    public static int searchLinkedList(ListNode head, int B) {
        ListNode current = head;

        while (current != null) {
            if (current.val == B) {
                return 1; // Found the value B in the linked list
            }
            current = current.next;
        }

        return 0; // Value B not found in the linked list
    }

    public static void main(String[] args) {
        //Solution solution = new Solution();

        // Example Input 1
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        int B1 = 4;
        System.out.println(searchLinkedList(head1, B1)); // Output: 0

        // Example Input 2
        ListNode head2 = new ListNode(4);
        head2.next = new ListNode(3);
        head2.next.next = new ListNode(2);
        head2.next.next.next = new ListNode(1);
        int B2 = 4;
        System.out.println(searchLinkedList(head2, B2)); // Output: 1
    }
}
