package com.swaroop.dsa.stacks;

import java.util.*;

/**
 * STACKS - LIFO (Last In First Out)
 * 
 * 💡 CHEAT MEMORY:
 * Stack = LIFO (think stack of plates - take from top)
 * Operations: push O(1), pop O(1), peek O(1), empty O(1)
 * Use for: Undo/Redo, expression evaluation, backtracking
 * 
 * KEY TECHNIQUES:
 * 1. Parentheses Matching
 * 2. Expression Evaluation (Infix → Postfix)
 * 3. Monotonic Stack - for next greater/smaller element
 * 4. DFS using explicit stack
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
     * Example: "({[}])" returns false
     * 
     * LOGIC:
     * - Push opening brackets
     * - For closing bracket, check if matches top of stack
     * - At end, stack should be empty
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
     * Explanation:
     * - 1's next greater: 3
     * - 3's next greater: 4
     * - 2's next greater: 4
     * - 4's next greater: -1 (none)
     * 
     * LOGIC (Monotonic Stack):
     * - Iterate from right to left
     * - Maintain decreasing stack
     * - Top of stack is next greater element
     * - Remove smaller elements from stack
     */
    public static int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();
        
        for (int i = n - 1; i >= 0; i--) {
            // Remove elements smaller than current
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }
            
            result[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(nums[i]);
        }
        
        return result;
    }
    
    /**
     * Largest Rectangle in Histogram
     * Find area of largest rectangle in histogram
     * 
     * Example: [2,1,5,6,2,3]
     * Output: 10 (height 5 with width 2)
     */
    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            stack.push(i);
        }
        
        return maxArea;
    }
    
    // ========================================
    // 3. INFIX TO POSTFIX CONVERSION
    // ========================================
    
    /**
     * Convert Infix Expression to Postfix
     * Example: "2+3*4" → "234*+"
     * 
     * LOGIC:
     * - Operands go directly to output
     * - Operators depend on precedence
     * - Use stack for operators
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
                if (!stack.isEmpty()) stack.pop();  // Remove '('
            } else {  // Operator
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
    
    /**
     * Evaluate Postfix Expression
     * Example: "23*4+" → 10 (2+3*4)
     */
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> stack = new Stack<>();
        
        for (char c : postfix.toCharArray()) {
            if (Character.isDigit(c)) {
                stack.push(Character.getNumericValue(c));
            } else {
                int b = stack.pop();
                int a = stack.pop();
                stack.push(applyOperator(a, b, c));
            }
        }
        
        return stack.pop();
    }
    
    private static int applyOperator(int a, int b, char op) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: return 0;
        }
    }
    
    // ========================================
    // 4. MIN STACK (Track Minimum)
    // ========================================
    
    /**
     * Min Stack - Supports push, pop, top, getMin in O(1)
     * 
     * LOGIC:
     * - Keep two stacks: regular and min
     * - Min stack tracks minimum at each level
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
    // MAIN - Test all functions
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== STACK BASICS ===\n");
        
        // 1. Valid Parentheses
        System.out.println("1. VALID PARENTHESES");
        String[] tests = {"({[]})", "({[}])", "([])", ""};
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
        System.out.println("3. INFIX TO POSTFIX CONVERSION");
        String infix = "2+3*4";
        String postfix = infixToPostfix(infix);
        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + postfix);
        System.out.println("Evaluation: " + evaluatePostfix(postfix) + "\n");
        
        // 4. Min Stack
        System.out.println("4. MIN STACK");
        MinStack minStack = new MinStack();
        minStack.push(3);
        minStack.push(1);
        minStack.push(2);
        System.out.println("After push 3,1,2:");
        System.out.println("Top: " + minStack.top());
        System.out.println("Min: " + minStack.getMin());
        minStack.pop();
        System.out.println("After pop:");
        System.out.println("Top: " + minStack.top());
        System.out.println("Min: " + minStack.getMin());
    }
}
