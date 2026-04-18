# 📖 STACKS & QUEUES - COMPLETE LEARNING GUIDE

## 🎯 What are Stacks and Queues?

### STACK (Last-In-First-Out)
**Real-world:** Stack of plates - you take from top, add to top

```
Push 1: [1]
Push 2: [1, 2]
Push 3: [1, 2, 3]  ← Top

Pop:    [1, 2]     ← Removed 3
```

**Operations:**
- `push(x)` - Add to top O(1)
- `pop()` - Remove from top O(1)
- `peek()` - View top O(1)
- `isEmpty()` - Check if empty O(1)

### QUEUE (First-In-First-Out)
**Real-world:** Queue at bank - first person served first

```
Enqueue 1: [1]
Enqueue 2: [1, 2]
Enqueue 3: [1, 2, 3]  ← Rear

Dequeue:   [2, 3]     ← Removed 1
```

**Operations:**
- `enqueue(x)` - Add to rear O(1)
- `dequeue()` - Remove from front O(1)
- `peek()` - View front O(1)
- `isEmpty()` - Check if empty O(1)

---

## 🔥 Key Concepts

### STACK USE CASES:
1. **Function Call Stack** - How recursion works internally
2. **Undo/Redo** - Ctrl+Z functionality
3. **Parentheses Validation** - Check if brackets balanced
4. **Backtracking** - DFS uses stack
5. **Expression Evaluation** - Infix to postfix

### QUEUE USE CASES:
1. **BFS (Breadth-First Search)** - Graph traversal
2. **Print Queue** - Jobs waiting to print
3. **CPU Scheduling** - Processes waiting
4. **Message Queues** - Async processing
5. **Level-order Tree Traversal**

---

## 🎓 Important Stack Patterns

### Pattern 1: Balanced Parentheses
```
Check if "({[]})" is valid

Push open brackets: ( { [
When closing: } ] ) - must match!

Code:
Stack<Character> stack = new Stack<>();
for (char c : s.toCharArray()) {
    if (isOpen(c)) stack.push(c);
    else if (!stack.isEmpty() && matches(stack.peek(), c)) {
        stack.pop();
    } else {
        return false;
    }
}
return stack.isEmpty();
```

### Pattern 2: Monotonic Stack
Keeps elements in increasing/decreasing order

**Example: Next Greater Element**
```
Array: [1, 3, 2, 4]
NGE:   [3, 4, 4, -1]

Use decreasing stack:
- 1: stack=[1]
- 3: 3>1, pop 1, answer[0]=3, stack=[3]
- 2: 2<3, stack=[3,2]
- 4: 4>2, pop 2, answer[2]=4
    4>3, pop 3, answer[1]=4, stack=[4]
```

### Pattern 3: Min Stack
Maintain minimum while supporting push/pop

```java
Stack<Integer> stack = new Stack<>();
Stack<Integer> minStack = new Stack<>();

push(x):
    stack.push(x);
    minStack.push(Math.min(x, minStack.peek()));

pop():
    stack.pop();
    minStack.pop();

getMin():
    return minStack.peek();
```

---

## 🎓 Important Queue Patterns

### Pattern 1: Circular Queue
Reuse space by wrapping around

```
Array: [_, _, _, _]  capacity = 4
Add 1: [1, _, _, _]  front=0, rear=0
Add 2: [1, 2, _, _]  front=0, rear=1
Remove: [_, 2, _, _] front=1, rear=1
Add 3,4: [3, 2, 4, 4] front=1, rear=0 (wraps!)

isEmpty: front == rear
isFull: (rear+1)%n == front
```

### Pattern 2: Sliding Window Maximum
Use deque to find max in each window

```
Array: [1,3,-1,-3,5,3,6,7], window=3

Window [1,3,-1]: max=3
Window [3,-1,-3]: max=3
Window [-1,-3,5]: max=5
...
```

### Pattern 3: Priority Queue (Heap)
Queue where elements retrieved by priority

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.offer(3);
pq.offer(1);
pq.offer(2);
pq.poll();  // Returns 1 (smallest)
```

---

## 💡 Implementation Details

### Stack Using Array (Dynamic)
```java
class Stack<T> {
    private T[] elements = new T[10];
    private int top = -1;
    
    void push(T x) {
        if (top == elements.length - 1) {
            resize();  // Double size
        }
        elements[++top] = x;
    }
    
    T pop() {
        return elements[top--];
    }
}
```

### Queue Using Circular Array
```java
class Queue<T> {
    private T[] elements = new T[10];
    private int front = 0, rear = -1;
    private int size = 0;
    
    void enqueue(T x) {
        if (size == elements.length) resize();
        rear = (rear + 1) % elements.length;
        elements[rear] = x;
        size++;
    }
    
    T dequeue() {
        T x = elements[front];
        front = (front + 1) % elements.length;
        size--;
        return x;
    }
}
```

---

## 🎯 Interview Questions

### EASY ⭐
1. **Valid Parentheses** - Basic stack
2. **Min Stack** - Track minimum
3. **Implement Stack** - Basic implementation
4. **Implement Queue** - Basic implementation
5. **Reverse String** - Stack or recursion

### MEDIUM ⭐⭐
1. **Next Greater Element** - Monotonic stack
2. **Sliding Window Maximum** - Deque
3. **Daily Temperatures** - Monotonic stack
4. **Trapping Rain Water** - Stack optimization
5. **Evaluate Reverse Polish** - Stack evaluation

### HARD ⭐⭐⭐
1. **Largest Rectangle** - Monotonic stack
2. **Maximal Rectangle** - Stack + DP
3. **Minimum Window Substring** - Complex algorithm
4. **LRU Cache** - Queue + HashMap

---

## ⚡ Time & Space Complexity

| Operation | Stack | Queue |
|-----------|-------|-------|
| Push/Enqueue | O(1) | O(1) |
| Pop/Dequeue | O(1) | O(1) |
| Peek | O(1) | O(1) |
| Space | O(n) | O(n) |

---

## 🧠 Key Differences

| Feature | Stack | Queue |
|---------|-------|-------|
| Order | LIFO | FIFO |
| Add | Top | Rear |
| Remove | Top | Front |
| Use | Undo/Recursion | BFS/Scheduling |
| Real World | Browser back | Bank queue |

---

## ✨ Key Takeaways

1. **Stack = LIFO** - Last one in, first one out
2. **Queue = FIFO** - First in, first out
3. **Both O(1)** for all basic operations
4. **Monotonic Stack** solves "next greater" type problems
5. **Deque** (double-ended queue) useful for sliding window
6. **Priority Queue** useful when order matters
7. **Always handle empty/full cases** - common bugs

---

## 🎯 Practice Plan

**Week 1:**
- Implement stack and queue from scratch
- Solve: valid parentheses, min stack

**Week 2:**
- Master monotonic stack
- Solve: next greater, daily temperatures

**Week 3:**
- Solve 5 easy problems on LeetCode
- Learn sliding window maximum thoroughly

**Week 4:**
- Solve 5 medium problems
- Understand deque and priority queue

---

**Next:** Trees - they use queues for level-order traversal!
