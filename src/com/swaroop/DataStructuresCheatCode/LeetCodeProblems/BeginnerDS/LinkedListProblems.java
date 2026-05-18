package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.BeginnerDS;

import java.util.*;

/**
 * LINKED LIST PROBLEMS - BEGINNER LEVEL
 * 
 * Problems covered:
 * 1. LeetCode 203: Remove Linked List Elements
 * 2. LeetCode 206: Reverse Linked List
 * 3. LeetCode 141: Linked List Cycle
 * 4. LeetCode 160: Intersection of Two Linked Lists
 * 5. LeetCode 237: Delete Node in a Linked List
 */

public class LinkedListProblems {
    
    // ========================================
    // LINKED LIST NODE DEFINITION
    // ========================================
    
    public static class ListNode {
        public int val;
        public ListNode next;
        
        public ListNode(int val) {
            this.val = val;
        }
        
        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    // ========================================
    // PROBLEM 1: LeetCode 203 - Remove Linked List Elements
    // ========================================
    
    /**
     * LeetCode 203: Remove Linked List Elements
     * 
     * Given the head of a linked list and an integer val, remove all the nodes
     * of the linked list that have Node.val == val, and return the new head.
     * 
     * Example:
     * Input: head = [1,2,6,3,4,5,6], val = 6
     * Output: [1,2,3,4,5]
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static ListNode removeElements(ListNode head, int val) {
        // Create dummy node to handle removing head element
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;
        
        while (current.next != null) {
            if (current.next.val == val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return dummy.next;
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 206 - Reverse Linked List
    // ========================================
    
    /**
     * LeetCode 206: Reverse Linked List
     * 
     * Given the head of a singly linked list, reverse the list,
     * and return the reversed list.
     * 
     * Example:
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode nextTemp = current.next;
            current.next = prev;
            prev = current;
            current = nextTemp;
        }
        
        return prev;
    }
    
    /**
     * Alternative recursive approach
     * Time: O(n), Space: O(n) due to recursion stack
     */
    public static ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 141 - Linked List Cycle
    // ========================================
    
    /**
     * LeetCode 141: Linked List Cycle
     * 
     * Given head of a linked list, determine if the linked list has a cycle in it.
     * There is a cycle in a linked list if there is some node in the list that
     * can be reached again by following the next pointer.
     * 
     * Example:
     * Input: head = [3,2,0,-4], pos = 1
     * Output: true (cycle exists)
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head.next;
        
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return true;
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 160 - Intersection of Two Linked Lists
    // ========================================
    
    /**
     * LeetCode 160: Intersection of Two Linked Lists
     * 
     * Given the heads of two singly linked-lists headA and headB,
     * return the node at which the two lists intersect.
     * If the two linked lists have no intersection at all, return null.
     * 
     * Example:
     * Input: headA = [4,1,8,4,5], headB = [5,6,1,8,4,5], intersectVal = 8
     * Output: Reference to the node with value 8
     * 
     * Time: O(m + n)
     * Space: O(1)
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode a = headA;
        ListNode b = headB;
        
        // Two pointers approach
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        
        return a;
    }
    
    // ========================================
    // PROBLEM 5: LeetCode 237 - Delete Node in a Linked List
    // ========================================
    
    /**
     * LeetCode 237: Delete Node in a Linked List
     * 
     * There is a singly-linked list head and we want to delete a node node
     * in it. You are given the node to be deleted node. You will not be given
     * access to the first node of head.
     * 
     * All the values of the linked list are unique, and it is guaranteed that
     * the given node node is not the last node in the linked list.
     * 
     * Example:
     * Input: node = [4,5,1,9], node.val = 5
     * Output: [4,1,9]
     * 
     * Time: O(1)
     * Space: O(1)
     */
    public static void deleteNode(ListNode node) {
        // Copy next node's value to current node
        node.val = node.next.val;
        // Skip the next node
        node.next = node.next.next;
    }
    
    // ========================================
    // UTILITY METHODS
    // ========================================
    
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        return head;
    }
    
    public static void printList(ListNode head) {
        System.out.print("[");
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(",");
            }
            current = current.next;
        }
        System.out.println("]");
    }
    
    // ========================================
    // MAIN - Test Linked List Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== LINKED LIST PROBLEMS - BEGINNER ===\n");
        
        // Problem 1: Remove Elements
        System.out.println("1. LeetCode 203: Remove Linked List Elements");
        ListNode head1 = createList(new int[]{1, 2, 6, 3, 4, 5, 6});
        System.out.print("Input: head = ");
        printList(head1);
        ListNode result1 = removeElements(head1, 6);
        System.out.print("Output: ");
        printList(result1);
        System.out.println();
        
        // Problem 2: Reverse Linked List
        System.out.println("2. LeetCode 206: Reverse Linked List");
        ListNode head2 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Input: head = ");
        printList(head2);
        ListNode result2 = reverseList(head2);
        System.out.print("Output: ");
        printList(result2);
        System.out.println();
        
        // Problem 3: Linked List Cycle
        System.out.println("3. LeetCode 141: Linked List Cycle");
        ListNode head3 = createList(new int[]{3, 2, 0, -4});
        System.out.print("Input: head = ");
        printList(head3);
        System.out.println("Output: " + hasCycle(head3) + "\n");
        
        // Problem 5: Delete Node
        System.out.println("5. LeetCode 237: Delete Node in a Linked List");
        ListNode head5 = createList(new int[]{4, 5, 1, 9});
        System.out.print("Input: ");
        printList(head5);
        deleteNode(head5.next); // Delete node with value 5
        System.out.print("After deleting node 5: ");
        printList(head5);
    }
}
