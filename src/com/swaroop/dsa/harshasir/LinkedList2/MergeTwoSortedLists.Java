
/*

Problem Description
        Merge two sorted linked lists, A and B, and return it as a new list.

        The new list should be made by splicing together the nodes of the first two lists and should also be sorted.



        Problem Constraints
        0 <= |A|, |B| <= 105



        Input Format
        The first argument of input contains a pointer to the head of linked list A.

        The second argument of input contains a pointer to the head of linked list B.



        Output Format
        Return a pointer to the head of the merged linked list.



        Example Input
        Input 1:

        A = 5 -> 8 -> 20
        B = 4 -> 11 -> 15
        Input 2:

        A = 1 -> 2 -> 3
        B = Null


        Example Output
        Output 1:

        4 -> 5 -> 8 -> 11 -> 15 -> 20
        Output 2:

        1 -> 2 -> 3

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
    public ListNode mergeTwoLists(ListNode A, ListNode B) {
    }

    public ListNode mergeTwoLists(ListNode A, ListNode B) {
        ListNode dummy = new ListNode(0); // Dummy node to simplify merging
        ListNode current = dummy; // Pointer to the current node in the merged list

        while (A != null && B != null) {
            if (A.val < B.val) {
                current.next = A;
                A = A.next;
            } else {
                current.next = B;
                B = B.next;
            }
            current = current.next;
        }

        // If any list still has remaining nodes, add them to the merged list
        if (A != null) {
            current.next = A;
        } else {
            current.next = B;
        }

        return dummy.next; // Skip the dummy node and return the merged list
    }

    public static void main(String[] args) {
        MergeSortedLists merger = new MergeSortedLists();

        // Example 1
        ListNode A1 = new ListNode(5);
        A1.next = new ListNode(8);
        A1.next.next = new ListNode(20);

        ListNode B1 = new ListNode(4);
        B1.next = new ListNode(11);
        B1.next.next = new ListNode(15);

        ListNode mergedList1 = merger.mergeTwoLists(A1, B1);
        printList(mergedList1);

        // Example 2
        ListNode A2 = new ListNode(1);
        A2.next = new ListNode(2);
        A2.next.next = new ListNode(3);

        ListNode B2 = null;

        ListNode mergedList2 = merger.mergeTwoLists(A2, B2);
        printList(mergedList2);
    }

    // Helper function to print a linked list
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

/*

Certainly! The problem requires merging two sorted linked lists, A and B, into a new sorted linked list. Let's break down the logic step by step:

        Create a Dummy Node: To simplify the merging process, we create a dummy node at the beginning of the merged list. This dummy node will be used as the starting point, and we'll attach the merged nodes to it.

        Initialize Pointers: We create a pointer called current initially pointing to the dummy node. This pointer will help us build the merged list while comparing nodes from A and B.

        Merging Process:

        While both lists A and B have remaining nodes:
        Compare the values of the current nodes in A and B.
        If the value in list A is smaller, attach the current node from A to the current pointer in the merged list. Move the current and A pointers to the next nodes.
        If the value in list B is smaller or equal, attach the current node from B to the current pointer in the merged list. Move the current and B pointers to the next nodes.
        This ensures that the merged list is constructed in a sorted manner.
        Attach Remaining Nodes: After the above loop, either list A or B might have remaining nodes. If A has remaining nodes, attach them to the end of the merged list. If B has remaining nodes, attach them.

        Return Result: Return the next node of the dummy node, which will be the actual head of the merged and sorted linked list.

*/