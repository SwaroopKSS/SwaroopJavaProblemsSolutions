package com.swaroop.dsa.linkedlists;

/**
 * LINKED LISTS - LINEAR DATA STRUCTURE
 * 
 * 💡 CHEAT MEMORY:
 * LinkedList = O(1) insertion/deletion at head, O(n) random access
 * Use for: Frequent insertions/deletions at beginning
 * 
 * KEY TECHNIQUES:
 * 1. Two Pointers (Slow/Fast) - detect cycles, find middle
 * 2. Reversal - reverse list in-place
 * 3. Merge - merge two sorted lists
 * 4. Floyd's Algorithm - cycle detection
 */

public class LinkedListBasics {
    
    // ========================================
    // NODE CLASS
    // ========================================
    
    public static class Node {
        int data;
        Node next;
        
        public Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
    
    // ========================================
    // 1. REVERSE LINKED LIST
    // ========================================
    
    /**
     * Reverse Linked List (Iterative)
     * 
     * Example: 1→2→3→null becomes 3→2→1→null
     * 
     * LOGIC:
     * - Three pointers: prev, curr, next
     * - At each step: curr.next = prev (reverse the link)
     * - Move prev and curr forward
     */
    public static Node reverseList(Node head) {
        Node prev = null;
        Node curr = head;
        
        while (curr != null) {
            Node next = curr.next;  // Save next node
            curr.next = prev;       // Reverse the link
            prev = curr;            // Move prev forward
            curr = next;            // Move curr forward
        }
        
        return prev;  // New head
    }
    
    /**
     * Reverse Linked List (Recursive)
     */
    public static Node reverseListRecursive(Node head) {
        // Base case
        if (head == null || head.next == null) {
            return head;
        }
        
        // Reverse rest of list
        Node newHead = reverseListRecursive(head.next);
        
        // Make next node point back to current
        head.next.next = head;
        head.next = null;
        
        return newHead;
    }
    
    // ========================================
    // 2. FIND MIDDLE OF LINKED LIST
    // ========================================
    
    /**
     * Find Middle of Linked List (Slow/Fast Pointers)
     * 
     * Example: 1→2→3→4→5 returns Node(3)
     * 
     * LOGIC:
     * - Slow pointer moves 1 step
     * - Fast pointer moves 2 steps
     * - When fast reaches end, slow is at middle
     */
    public static Node findMiddle(Node head) {
        Node slow = head;
        Node fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
        }
        
        return slow;  // Middle node
    }
    
    // ========================================
    // 3. DETECT CYCLE (FLOYD'S ALGORITHM)
    // ========================================
    
    /**
     * Detect Cycle in Linked List
     * 
     * LOGIC:
     * - If slow == fast, there's a cycle
     * - If fast reaches null, no cycle
     */
    public static boolean hasCycle(Node head) {
        if (head == null) return false;
        
        Node slow = head;
        Node fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
            
            if (slow == fast) {
                return true;  // Cycle detected
            }
        }
        
        return false;  // No cycle
    }
    
    /**
     * Find Start of Cycle
     * 
     * After detecting cycle with Floyd's algorithm:
     * - Distance from head to cycle start = distance from meeting point to cycle start
     */
    public static Node findCycleStart(Node head) {
        if (head == null) return null;
        
        Node slow = head, fast = head;
        
        // Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        
        if (fast == null || fast.next == null) {
            return null;  // No cycle
        }
        
        // Find cycle start
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;  // Cycle start
    }
    
    // ========================================
    // 4. MERGE TWO SORTED LISTS
    // ========================================
    
    /**
     * Merge Two Sorted Linked Lists
     * 
     * Example:
     * List1: 1→3→5
     * List2: 2→4→6
     * Result: 1→2→3→4→5→6
     */
    public static Node mergeTwoLists(Node l1, Node l2) {
        Node dummy = new Node(0);  // Dummy node to simplify logic
        Node curr = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.data <= l2.data) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        // Attach remaining elements
        curr.next = (l1 != null) ? l1 : l2;
        
        return dummy.next;
    }
    
    // ========================================
    // 5. REMOVE NTH NODE FROM END
    // ========================================
    
    /**
     * Remove Nth Node From End of List
     * 
     * Example: 1→2→3→4→5, n=2
     * Result: 1→2→3→5 (removed 4)
     * 
     * LOGIC:
     * - Use two pointers n positions apart
     * - Move both until fast reaches end
     * - Remove node
     */
    public static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node fast = dummy;
        Node slow = dummy;
        
        // Move fast pointer n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            if (fast == null) return head;
            fast = fast.next;
        }
        
        // Move both pointers until fast reaches end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // Remove the node
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    // ========================================
    // UTILITY FUNCTIONS
    // ========================================
    
    public static Node createList(int[] arr) {
        if (arr.length == 0) return null;
        Node head = new Node(arr[0]);
        Node curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new Node(arr[i]);
            curr = curr.next;
        }
        return head;
    }
    
    public static void printList(Node head) {
        Node curr = head;
        while (curr != null) {
            System.out.print(curr.data + "→");
            curr = curr.next;
        }
        System.out.println("null");
    }
    
    // ========================================
    // MAIN - Test all functions
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== LINKED LIST BASICS ===\n");
        
        // Create list: 1→2→3→4→5
        int[] arr = {1, 2, 3, 4, 5};
        Node head = createList(arr);
        
        // 1. Reverse List
        System.out.println("1. REVERSE LINKED LIST");
        Node head2 = createList(arr);
        System.out.print("Before: ");
        printList(head2);
        head2 = reverseList(head2);
        System.out.print("After: ");
        printList(head2);
        System.out.println();
        
        // 2. Find Middle
        System.out.println("2. FIND MIDDLE");
        Node head3 = createList(arr);
        Node middle = findMiddle(head3);
        System.out.println("List: 1→2→3→4→5");
        System.out.println("Middle: " + middle.data + "\n");
        
        // 3. Detect Cycle
        System.out.println("3. DETECT CYCLE");
        Node head4 = createList(arr);
        System.out.println("List without cycle: " + hasCycle(head4));
        
        // Create cycle
        Node tail = head4;
        while (tail.next != null) tail = tail.next;
        tail.next = head4.next;  // Point to 2nd node
        System.out.println("List with cycle: " + hasCycle(head4) + "\n");
        
        // 4. Merge Two Lists
        System.out.println("4. MERGE TWO SORTED LISTS");
        Node l1 = createList(new int[]{1, 3, 5});
        Node l2 = createList(new int[]{2, 4, 6});
        System.out.print("List1: ");
        printList(l1);
        System.out.print("List2: ");
        printList(l2);
        Node merged = mergeTwoLists(l1, l2);
        System.out.print("Merged: ");
        printList(merged);
        System.out.println();
        
        // 5. Remove Nth From End
        System.out.println("5. REMOVE NTH NODE FROM END");
        Node head5 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Before: ");
        printList(head5);
        head5 = removeNthFromEnd(head5, 2);
        System.out.print("After removing 2nd from end: ");
        printList(head5);
    }
}
