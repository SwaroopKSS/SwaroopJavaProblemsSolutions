package com.swaroop.DataStructuresCheatCode;

import java.util.*;

/**
 * QUEUES - FIFO (First In First Out)
 * 
 * 💡 CHEAT MEMORY:
 * Queue = FIFO (think line at bank - first come, first served)
 * Operations: offer/enqueue O(1), poll/dequeue O(1), peek O(1)
 * Use for: BFS, level-order traversal, scheduling
 */

public class QueueBasics {
    
    // ========================================
    // CIRCULAR QUEUE IMPLEMENTATION
    // ========================================
    
    /**
     * Circular Queue using Array
     * Space efficient, reuses slots
     */
    public static class CircularQueue {
        private int[] arr;
        private int front;
        private int rear;
        private int size;
        private int capacity;
        
        public CircularQueue(int capacity) {
            this.capacity = capacity;
            this.arr = new int[capacity];
            this.front = -1;
            this.rear = -1;
            this.size = 0;
        }
        
        public void enqueue(int data) {
            if (size == capacity) {
                throw new RuntimeException("Queue is full");
            }
            
            if (front == -1) {
                front = 0;
            }
            
            rear = (rear + 1) % capacity;
            arr[rear] = data;
            size++;
        }
        
        public int dequeue() {
            if (size == 0) {
                throw new RuntimeException("Queue is empty");
            }
            
            int data = arr[front];
            front = (front + 1) % capacity;
            size--;
            
            if (size == 0) {
                front = -1;
                rear = -1;
            }
            
            return data;
        }
        
        public int peek() {
            if (size == 0) {
                throw new RuntimeException("Queue is empty");
            }
            return arr[front];
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
    }
    
    // ========================================
    // SLIDING WINDOW MAXIMUM (Deque Pattern)
    // ========================================
    
    /**
     * Sliding Window Maximum
     * Find maximum in each window of size k
     * 
     * Example: [1,3,-1,-3,5,3,6,7], k=3
     * Output: [3,3,5,5,6,7]
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }
            
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offer(i);
            
            if (i >= k - 1) {
                result[index++] = nums[deque.peek()];
            }
        }
        
        return result;
    }
    
    // ========================================
    // TOP K FREQUENT ELEMENTS (Priority Queue)
    // ========================================
    
    /**
     * Top K Frequent Elements
     * Find k most frequent elements
     * 
     * Example: [1,1,1,2,2,3], k=2
     * Output: [1,2]
     */
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> freq.get(a) - freq.get(b));
        
        for (int num : freq.keySet()) {
            heap.offer(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        
        int[] result = new int[k];
        int index = 0;
        while (!heap.isEmpty()) {
            result[index++] = heap.poll();
        }
        
        return result;
    }
    
    // ========================================
    // MAIN - Test
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== QUEUE BASICS ===\n");
        
        // 1. Circular Queue
        System.out.println("1. CIRCULAR QUEUE");
        CircularQueue cq = new CircularQueue(3);
        cq.enqueue(10);
        cq.enqueue(20);
        cq.enqueue(30);
        System.out.println("Peek: " + cq.peek());
        System.out.println("Dequeue: " + cq.dequeue());
        cq.enqueue(40);
        System.out.println("After dequeue and enqueue 40, peek: " + cq.peek() + "\n");
        
        // 2. Sliding Window Maximum
        System.out.println("2. SLIDING WINDOW MAXIMUM");
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] maxWindow = maxSlidingWindow(nums, 3);
        System.out.println("Array: " + Arrays.toString(nums) + ", k=3");
        System.out.println("Result: " + Arrays.toString(maxWindow) + "\n");
        
        // 3. Top K Frequent
        System.out.println("3. TOP K FREQUENT ELEMENTS");
        int[] arr = {1, 1, 1, 2, 2, 3};
        int[] topK = topKFrequent(arr, 2);
        System.out.println("Array: " + Arrays.toString(arr) + ", k=2");
        System.out.println("Result: " + Arrays.toString(topK));
    }
}
