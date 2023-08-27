
/*

Problem Description
        Given a linked list of integers, find and return the middle element of the linked list.

        NOTE: If there are N nodes in the linked list and N is even then return the (N/2 + 1)th element.



        Problem Constraints
        1 <= length of the linked list <= 100000

        1 <= Node value <= 109



        Input Format
        The only argument given head pointer of linked list.



        Output Format
        Return the middle element of the linked list.



        Example Input
        Input 1:

        1 -> 2 -> 3 -> 4 -> 5
        Input 2:

        1 -> 5 -> 6 -> 2 -> 3 -> 4


        Example Output
        Output 1:

        3
        Output 2:

        2
*/


/**
 * Definition for singly-linked list.
 * class ListNode {
 *     public int val;
 *     public ListNode next;
 *     ListNode(int x) { val = x; next = null; }
 * }
 */

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }
}
public class Solution {
    /*public int solve(ListNode A) {
    }*/
    public int findMiddle(ListNode head) {
        if (head == null) {
            return -1; // Handle empty list case
        }

        ListNode slowPointer = head;
        ListNode fastPointer = head;

        while (fastPointer != null && fastPointer.next != null) {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next.next;
        }

        return slowPointer.val;
    }

    public static void main(String[] args) {
        // Create the linked list
        ListNode head = new ListNode(1);
        head.next = new ListNode(5);
        head.next.next = new ListNode(6);
        head.next.next.next = new ListNode(2);
        head.next.next.next.next = new ListNode(3);
        head.next.next.next.next.next = new ListNode(4);

        MiddleElementOfLinkedList solution = new MiddleElementOfLinkedList();
        int middle = solution.findMiddle(head);
        System.out.println("Middle element: " + middle);
    }
}

/*

Certainly! The logic for finding the middle element of a linked list is based on the two-pointer approach, specifically the "slow and fast pointer" technique. Here's how it works step by step:

        Initialize two pointers, slowPointer and fastPointer, both starting at the head of the linked list.

        Traverse the linked list with the fastPointer moving twice as fast as the slowPointer. In each iteration, move the slowPointer one step and the fastPointer two steps.

        Continue this traversal until the fastPointer reaches the end of the linked list or goes beyond the end (reaches a null node).

        At this point, the slowPointer will be pointing to the middle element of the linked list.

        Let's go through an example to illustrate the process. Consider the linked list: 1 -> 2 -> 3 -> 4 -> 5.

        Initially, slowPointer and fastPointer both start at the head (1).
        After the first iteration, slowPointer moves to 2, and fastPointer moves to 3.
        After the second iteration, slowPointer moves to 3, and fastPointer moves to 5 (two steps).
        Since fastPointer is not null and fastPointer.next is null, we stop the traversal.
        At this point, slowPointer is pointing to the middle element, which is 3. This works because the fastPointer moves twice as fast as the slowPointer, so when the fastPointer reaches the end of the list, the slowPointer will be approximately halfway through the list.

        Now let's consider an example where the length of the linked list is even: 1 -> 5 -> 6 -> 2 -> 3 -> 4.

        Initially, slowPointer and fastPointer both start at the head (1).
        After the first iteration, slowPointer moves to 5, and fastPointer moves to 6.
        After the second iteration, slowPointer moves to 6, and fastPointer moves to 3 (two steps).
        After the third iteration, slowPointer moves to 2, and fastPointer moves to 4 (two steps).
        Since fastPointer is not null and fastPointer.next is not null, we stop the traversal.
        At this point, slowPointer is pointing to the middle element, which is 2. Since the length of the linked list is even, we return the second middle element, which is the element at index (N/2 + 1).

        This approach ensures that we find the middle element efficiently in a single pass through the linked list.

*/