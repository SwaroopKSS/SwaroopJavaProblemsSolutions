package com.swaroop.DataStructuresCheatCode.LeetCodeProblems.BeginnerDS;

import java.util.*;

/**
 * STACK & QUEUE PROBLEMS - BEGINNER LEVEL
 * 
 * Problems covered:
 * 1. LeetCode 20: Valid Parentheses
 * 2. LeetCode 155: Min Stack
 * 3. LeetCode 232: Implement Queue using Stacks
 * 4. LeetCode 225: Implement Stack using Queues
 * 5. LeetCode 921: Minimum Add to Make Parentheses Valid
 */

public class StackQueueProblems {
    
    // ========================================
    // PROBLEM 1: LeetCode 20 - Valid Parentheses
    // ========================================
    
    /**
     * LeetCode 20: Valid Parentheses
     * 
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']',
     * determine if the input string is valid.
     * An input string is valid if:
     * 1. Open brackets must be closed by the same type of brackets
     * 2. Open brackets must be closed in the correct order
     * 
     * Example:
     * Input: s = "()"
     * Output: true
     * 
     * Input: s = "()[]{}"
     * Output: true
     * 
     * Input: s = "([)]"
     * Output: false
     * 
     * Time: O(n)
     * Space: O(n)
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');
        
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.pop() != map.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        
        return stack.isEmpty();
    }
    
    // ========================================
    // PROBLEM 2: LeetCode 155 - Min Stack
    // ========================================
    
    /**
     * LeetCode 155: Min Stack
     * 
     * Design a stack that supports push, pop, top, and retrieving the minimum element.
     * All operations must run in O(1) time.
     * 
     * Example:
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin(); // return -3
     * minStack.pop();
     * minStack.top();    // return 0
     * minStack.getMin(); // return -2
     */
    public static class MinStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack;
        
        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }
        
        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty()) {
                minStack.push(val);
            } else {
                minStack.push(Math.min(val, minStack.peek()));
            }
        }
        
        public void pop() {
            stack.pop();
            minStack.pop();
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return minStack.peek();
        }
    }
    
    // ========================================
    // PROBLEM 3: LeetCode 232 - Implement Queue using Stacks
    // ========================================
    
    /**
     * LeetCode 232: Implement Queue using Stacks
     * 
     * Implement a first in first out (FIFO) queue using only two stacks.
     * 
     * Example:
     * MyQueue queue = new MyQueue();
     * queue.push(1);
     * queue.push(2);
     * queue.peek();   // return 1
     * queue.pop();    // return 1
     * queue.empty();  // return false
     */
    public static class MyQueue {
        private Stack<Integer> inStack;
        private Stack<Integer> outStack;
        
        public MyQueue() {
            inStack = new Stack<>();
            outStack = new Stack<>();
        }
        
        public void push(int x) {
            inStack.push(x);
        }
        
        public int pop() {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            return outStack.pop();
        }
        
        public int peek() {
            if (outStack.isEmpty()) {
                while (!inStack.isEmpty()) {
                    outStack.push(inStack.pop());
                }
            }
            return outStack.peek();
        }
        
        public boolean empty() {
            return inStack.isEmpty() && outStack.isEmpty();
        }
    }
    
    // ========================================
    // PROBLEM 4: LeetCode 225 - Implement Stack using Queues
    // ========================================
    
    /**
     * LeetCode 225: Implement Stack using Queues
     * 
     * Implement a last in first out (LIFO) stack using only two queues.
     * 
     * Example:
     * MyStack stack = new MyStack();
     * stack.push(1);
     * stack.push(2);
     * stack.top();   // return 2
     * stack.pop();   // return 2
     * stack.empty(); // return false
     */
    public static class MyStack {
        private Queue<Integer> queue;
        
        public MyStack() {
            queue = new LinkedList<>();
        }
        
        public void push(int x) {
            queue.offer(x);
            // Move all elements before x to the back
            for (int i = 0; i < queue.size() - 1; i++) {
                queue.offer(queue.poll());
            }
        }
        
        public int pop() {
            return queue.poll();
        }
        
        public int top() {
            return queue.peek();
        }
        
        public boolean empty() {
            return queue.isEmpty();
        }
    }
    
    // ========================================
    // PROBLEM 5: LeetCode 921 - Minimum Add to Make Parentheses Valid
    // ========================================
    
    /**
     * LeetCode 921: Minimum Add to Make Parentheses Valid
     * 
     * A parentheses string is a non-empty string consisting only of '(' and ')'.
     * It is valid if:
     * 1. Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * 2. Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * 
     * Return the minimum number of parentheses we must add to make the string valid.
     * 
     * Example:
     * Input: s = "())"
     * Output: 1 (add one '(' at the beginning)
     * 
     * Input: s = "((("
     * Output: 3 (add three ')' at the end)
     * 
     * Time: O(n)
     * Space: O(1)
     */
    public static int minAddToMakeValid(String s) {
        int openNeeded = 0;  // Number of '(' to add
        int closeNeeded = 0; // Number of ')' to add
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                openNeeded++;
            } else if (c == ')') {
                if (openNeeded > 0) {
                    openNeeded--;
                } else {
                    closeNeeded++;
                }
            }
        }
        
        return openNeeded + closeNeeded;
    }
    
    // ========================================
    // MAIN - Test Stack & Queue Problems
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== STACK & QUEUE PROBLEMS - BEGINNER ===\n");
        
        // Problem 1: Valid Parentheses
        System.out.println("1. LeetCode 20: Valid Parentheses");
        System.out.println("Input: s = \"()\"");
        System.out.println("Output: " + isValid("()"));
        System.out.println("Input: s = \"()[]{}\"\n");
        System.out.println("Output: " + isValid("()[]{}\n"));
        System.out.println("Input: s = \"([)]\"");
        System.out.println("Output: " + isValid("([)]") + "\n");
        
        // Problem 2: Min Stack
        System.out.println("2. LeetCode 155: Min Stack");
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("After push(-2, 0, -3):");
        System.out.println("getMin(): " + minStack.getMin());
        minStack.pop();
        System.out.println("After pop():");
        System.out.println("top(): " + minStack.top());
        System.out.println("getMin(): " + minStack.getMin() + "\n");
        
        // Problem 3: Queue using Stacks
        System.out.println("3. LeetCode 232: Implement Queue using Stacks");
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        System.out.println("After push(1, 2):");
        System.out.println("peek(): " + queue.peek());
        System.out.println("pop(): " + queue.pop());
        System.out.println("empty(): " + queue.empty() + "\n");
        
        // Problem 4: Stack using Queues
        System.out.println("4. LeetCode 225: Implement Stack using Queues");
        MyStack stack = new MyStack();
        stack.push(1);
        stack.push(2);
        System.out.println("After push(1, 2):");
        System.out.println("top(): " + stack.top());
        System.out.println("pop(): " + stack.pop());
        System.out.println("empty(): " + stack.empty() + "\n");
        
        // Problem 5: Minimum Add to Make Valid
        System.out.println("5. LeetCode 921: Minimum Add to Make Parentheses Valid");
        System.out.println("Input: s = \"())\"");
        System.out.println("Output: " + minAddToMakeValid("())"));
        System.out.println("Input: s = \"(((\"");
        System.out.println("Output: " + minAddToMakeValid("((("));
    }
}
