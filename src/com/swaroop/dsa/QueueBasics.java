package com.swaroop.dsa.queues;

import java.util.*;

/**
 * QUEUES - FIFO (First In First Out)
 * 
 * 💡 CHEAT MEMORY:
 * Queue = FIFO (think line at bank - first come, first served)
 * Operations: offer/enqueue O(1), poll/dequeue O(1), peek O(1)
 * Use for: BFS, level-order traversal, scheduling
 * 
 * KEY TYPES:
 * 1. Simple Queue - basic FIFO
 * 2. Circular Queue - fixed size, wraps around
 * 3. Deque - Double ended queue
 * 4. Priority Queue - heap-based, ordered by priority
 */

public class QueueBasics {
    
    // ========================================
    // 1. SIMPLE QUEUE IMPLEMENTATION
    // ========================================
    
    /**
     * Queue using Linked List
     */
    public static class Queue<T> {
        private Node<T> front;
        private Node<T> rear;
        private int size;
        
        private static class Node<T> {
            T data;
            Node<T> next;
            Node(T data) { this.data = data; }
        }
        
        public void enqueue(T data) {
            Node<T> newNode = new Node<>(data);
            if (isEmpty()) {
                front = newNode;
            } else {
                rear.next = newNode;
            }
            rear = newNode;
            size++;
        }
        
        public T dequeue() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            T data = front.data;
            front = front.next;
            size--;
            return data;
        }
        
        public T peek() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            return front.data;
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int size() {
            return size;
        }
    }
    
    // ========================================
    // 2. CIRCULAR QUEUE IMPLEMENTATION
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
            
            rear = (rear + 1) % capacity;  // Wrap around
            arr[rear] = data;
            size++;
        }
        
        public int dequeue() {
            if (size == 0) {
                throw new RuntimeException("Queue is empty");
            }
            
            int data = arr[front];
            front = (front + 1) % capacity;  // Wrap around
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
        
        public int size() {
            return size;
        }
    }
    
    // ========================================
    // 3. DEQUE (Double Ended Queue)
    // ========================================
    
    /**
     * Deque - Can add/remove from both ends
     * Java has built-in Deque interface
     */
    public static class Deque<T> {
        private Node<T> front;
        private Node<T> rear;
        private int size;
        
        private static class Node<T> {
            T data;
            Node<T> prev, next;
            Node(T data) { this.data = data; }
        }
        
        public void addFirst(T data) {
            Node<T> newNode = new Node<>(data);
            if (isEmpty()) {
                front = rear = newNode;
            } else {
                newNode.next = front;
                front.prev = newNode;
                front = newNode;
            }
            size++;
        }
        
        public void addLast(T data) {
            Node<T> newNode = new Node<>(data);
            if (isEmpty()) {
                front = rear = newNode;
            } else {
                rear.next = newNode;
                newNode.prev = rear;
                rear = newNode;
            }
            size++;
        }
        
        public T removeFirst() {
            if (isEmpty()) throw new RuntimeException("Deque is empty");
            T data = front.data;
            if (size == 1) {
                front = rear = null;
            } else {
                front = front.next;
                front.prev = null;
            }
            size--;
            return data;
        }
        
        public T removeLast() {
            if (isEmpty()) throw new RuntimeException("Deque is empty");
            T data = rear.data;
            if (size == 1) {
                front = rear = null;
            } else {
                rear = rear.prev;
                rear.next = null;
            }
            size--;
            return data;
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
    }
    
    // ========================================
    // 4. SLIDING WINDOW MAXIMUM (Deque Pattern)
    // ========================================
    
    /**
     * Sliding Window Maximum
     * Find maximum in each window of size k
     * 
     * Example: [1,3,-1,-3,5,3,6,7], k=3
     * Output: [3,3,5,5,6,7]
     * 
     * Windows:
     * - [1,3,-1] → 3
     * - [3,-1,-3] → 3
     * - [-1,-3,5] → 5
     * - [-3,5,3] → 5
     * - [5,3,6] → 6
     * - [3,6,7] → 7
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) return new int[0];
        
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];
        int index = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // Remove elements outside window
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }
            
            // Remove smaller elements (they can't be max)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            deque.offer(i);
            
            // Window is full, store max
            if (i >= k - 1) {
                result[index++] = nums[deque.peek()];
            }
        }
        
        return result;
    }
    
    // ========================================
    // 5. PRIORITY QUEUE (Min/Max Heap)
    // ========================================
    
    /**
     * Priority Queue - Elements served by priority
     * Can be min-heap or max-heap
     */
    public static void priorityQueueDemo() {
        // Min Heap (default)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(5);
        minHeap.offer(3);
        minHeap.offer(7);
        minHeap.offer(1);
        
        System.out.println("Min Heap poll order: ");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.poll() + " ");  // 1, 3, 5, 7
        }
        System.out.println("\n");
        
        // Max Heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.offer(5);
        maxHeap.offer(3);
        maxHeap.offer(7);
        maxHeap.offer(1);
        
        System.out.println("Max Heap poll order: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " ");  // 7, 5, 3, 1
        }
        System.out.println();
    }
    
    // ========================================
    // 6. K FREQUENT ELEMENTS (Priority Queue)
    // ========================================
    
    /**
     * Top K Frequent Elements
     * Find k most frequent elements
     * 
     * Example: [1,1,1,2,2,3], k=2
     * Output: [1,2]
     */
    public static int[] topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // Min heap by frequency (size = k)
        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> freq.get(a) - freq.get(b));
        
        for (int num : freq.keySet()) {
            heap.offer(num);
            if (heap.size() > k) {
                heap.poll();  // Remove least frequent
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
    // MAIN - Test all functions
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== QUEUE BASICS ===\n");
        
        // 1. Simple Queue
        System.out.println("1. SIMPLE QUEUE");
        Queue<Integer> q = new Queue<>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        System.out.println("After enqueue 1,2,3: peek = " + q.peek());
        System.out.println("Dequeue: " + q.dequeue());
        System.out.println("Size: " + q.size() + "\n");
        
        // 2. Circular Queue
        System.out.println("2. CIRCULAR QUEUE");
        CircularQueue cq = new CircularQueue(3);
        cq.enqueue(10);
        cq.enqueue(20);
        cq.enqueue(30);
        System.out.println("Peek: " + cq.peek());
        System.out.println("Dequeue: " + cq.dequeue());
        cq.enqueue(40);  // Reuse slot
        System.out.println("After dequeue and enqueue 40, peek: " + cq.peek() + "\n");
        
        // 3. Sliding Window Maximum
        System.out.println("3. SLIDING WINDOW MAXIMUM");
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] maxWindow = maxSlidingWindow(nums, 3);
        System.out.println("Array: " + Arrays.toString(nums) + ", k=3");
        System.out.println("Result: " + Arrays.toString(maxWindow) + "\n");
        
        // 4. Priority Queue
        System.out.println("4. PRIORITY QUEUE (Min & Max Heap)");
        priorityQueueDemo();
        System.out.println();
        
        // 5. Top K Frequent
        System.out.println("5. TOP K FREQUENT ELEMENTS");
        int[] arr = {1, 1, 1, 2, 2, 3};
        int[] topK = topKFrequent(arr, 2);
        System.out.println("Array: " + Arrays.toString(arr) + ", k=2");
        System.out.println("Result: " + Arrays.toString(topK));
    }
}
