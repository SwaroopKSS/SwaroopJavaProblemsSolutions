package com.swaroop.dsa.harshasir.LinkedList1;
/*
Problem Description
        You are given A which is the head of a linked list. Also given is the value B and position C. Complete the function that should insert a new node with the said value at the given position.

        Notes:

        In case the position is more than length of linked list, simply insert the new node at the tail only.
        In case the pos is 0, simply insert the new node at head only.
        Follow 0-based indexing for the node numbering.


        Problem Constraints
        1 <= size of linked list <= 105

        1 <= value of nodes <= 109

        1 <= B <= 109

        0 <= C <= 105



        Input Format
        The first argument A is the head of a linked list.

        The second argument B is an integer which denotes the value of the new node

        The third argument C is an integer which denotes the position of the new node



        Output Format
        Return the head of the linked list

*/

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        this.val = x;
        this.next = null;
    }
}
public class InsertInLinkedList {
    public ListNode solve(ListNode A, int B, int C) {
    ListNode newNode = new ListNode(B);

        if(C ==0)

    {
        newNode.next = A;
        return newNode;
    }

    ListNode current = A;
    int count = 0;

        while(count<C -1&&current !=null)

    {
        current = current.next;
        count++;
    }

        if(current !=null)

    {
        newNode.next = current.next;
        current.next = newNode;
    } else

    {
        // If the position is beyond the length of the list, insert at tail
        ListNode tail = A;
        while (tail.next != null) {
            tail = tail.next;
        }
        tail.next = newNode;
    }

        return A;
}
}
