
/*

Problem Description
You are given a singly linked list having head node A. You have to reverse the linked list and return the head node of that reversed list.

NOTE: You have to do it in-place and in one-pass.



Problem Constraints
1 <= Length of linked list <= 105

Value of each node is within the range of a 32-bit integer.



Input Format
First and only argument is a linked-list node A.



Output Format
Return a linked-list node denoting the head of the reversed linked list.



Example Input
Input 1:

 A = 1 -> 2 -> 3 -> 4 -> 5 -> NULL
Input 2:

 A = 3 -> NULL


Example Output
Output 1:

 5 -> 4 -> 3 -> 2 -> 1 -> NULL
Output 2:

 3 -> NULL

 */

 /**
  * Definition for singly-linked list.
  * class ListNode {
  *     public int val;
  *     public ListNode next;
  *     ListNode(int x) { val = x; next = null; }
  * }
  */
 public class ReverseLinkedList {
     public ListNode reverseList(ListNode head) {
         ListNode prev = null;
         ListNode current = head;
         ListNode nextNode;

         while (current != null) {
             nextNode = current.next; // Store the next node
             current.next = prev; // Reverse the current node's pointer

             prev = current; // Move prev to the current node
             current = nextNode; // Move current to the next node
         }

         return prev; // The new head of the reversed linked list
     }
 }


 /*

 Certainly! Let's break down the logic step by step:

         Initialize Pointers:

         prev: This pointer will initially be set to null. It will keep track of the node that will become the new head of the reversed list.
         current: This pointer will start at the head of the original list and will be used to traverse through the original list.
         nextNode: This pointer will be used to temporarily store the next node in the original list before we modify the current node's next pointer.
         Traverse the Original List:
         We will iterate through the original list using the current pointer. We want to reverse the direction of the pointers in the nodes as we traverse the list.

         Store the Next Node:
         Before we modify the current node's next pointer, we need to store the reference to the next node. We do this by assigning current.next to the nextNode pointer. This ensures that we don't lose the reference to the remaining portion of the original list.

         Reverse the Pointer:
         The key step in reversing the list is to change the current node's next pointer to point to the prev node. This effectively reverses the direction of the pointer.

         Update prev:
         After reversing the pointer, we move the prev pointer to point to the current node. This effectively extends the reversed portion of the list.

         Move current:
         Now, we move the current pointer to the nextNode that we stored earlier. This allows us to continue iterating through the original list.

         Repeat Until End:
         We repeat steps 3-6 until we reach the end of the original list (i.e., current becomes null). This way, we reverse the pointers for all nodes in the original list.

         Return the New Head:
         Once we have reversed all pointers and current is null, the prev pointer will be pointing to the last node in the original list (which becomes the new head of the reversed list). We return this prev pointer as the head of the reversed list.

         By following these steps, we iterate through the original list in a single pass and reverse the pointers to create the reversed linked list in-place. This algorithm has a time complexity of O(n), where n is the number of nodes in the original list.

*/