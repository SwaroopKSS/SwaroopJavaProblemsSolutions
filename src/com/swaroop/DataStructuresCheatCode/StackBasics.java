package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * STACKS - LIFO (Last In First Out)
 * 
 * 💡 CHEAT MEMORY:
 * Stack = LIFO (think stack of plates - take from top)
 * Operations: push O(1), pop O(1), peek O(1)
 * Use for: Undo/Redo, expression evaluation, backtracking
 */

public class StackBasics {
    
    // ========================================
    // 1. VALID PARENTHESES
    // ========================================
    
    /**
     * Valid Parentheses Problem
     * Check if brackets are balanced
     * 
     * Example: "({[]})" returns true
     */
    public static boolean isValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) return false;
                
                char top = stack.pop();
                if (!isMatching(top, c)) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    private static boolean isMatching(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
    
    // ========================================
    // 2. NEXT GREATER ELEMENT (Monotonic Stack)
    // ========================================
    
    /**
     * Next Greater Element
     * Find next greater element for each element
     * 
     * Example: [1,3,2,4]
     * Output: [3,4,4,-1]
     */
    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        
        return result;
    }
    
    // ========================================
    // 3. INFIX TO POSTFIX CONVERSION
    // ========================================
    
    /**
     * Convert Infix Expression to Postfix
     * Example: "2+3*4" → "234*+"
     */
    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        
        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop();
            } else {
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                    postfix.append(stack.pop());
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }
        
        return postfix.toString();
    }
    
    private static int getPrecedence(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }
    
    // ========================================
    // 4. MIN STACK
    // ========================================
    
    /**
     * Min Stack - Track Minimum in O(1)
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
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }
        
        public void pop() {
            if (stack.pop().equals(minStack.peek())) {
                minStack.pop();
            }
        }
        
        public int top() {
            return stack.peek();
        }
        
        public int getMin() {
            return minStack.peek();
        }
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== STACK BASICS ===\n");
        
        // 1. Valid Parentheses
        System.out.println("1. VALID PARENTHESES");
        String[] tests = {"({[]})", "({[}])", "([])"};
        for (String test : tests) {
            System.out.println("\"" + test + "\": " + isValidParentheses(test));
        }
        System.out.println();
        
        // 2. Next Greater Element
        System.out.println("2. NEXT GREATER ELEMENT");
        int[] nums = {1, 3, 2, 4};
        int[] result = nextGreaterElement(nums);
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Output: " + Arrays.toString(result) + "\n");
        
        // 3. Infix to Postfix
        System.out.println("3. INFIX TO POSTFIX");
        String infix = "2+3*4";
        String postfix = infixToPostfix(infix);
        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + postfix + "\n");
        
        // 4. Min Stack
        System.out.println("4. MIN STACK");
        MinStack minStack = new MinStack();
        minStack.push(3);
        minStack.push(1);
        minStack.push(2);
        System.out.println("Min: " + minStack.getMin());
        minStack.pop();
        System.out.println("After pop, Min: " + minStack.getMin());
    }
}
