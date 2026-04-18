# 📖 LINKED LISTS - COMPLETE LEARNING GUIDE

## 🎯 What is a Linked List?

A **linked list** is a sequence of nodes where each node contains:
1. **Data** (value)
2. **Next pointer** (link to next node)

Think of it as a chain of boxes connected by arrows.

```
Head → [1|→] → [2|→] → [3|→] → [4|null]
```

**Difference from Array:**
| Property | Array | Linked List |
|----------|-------|------------|
| Access | O(1) - direct | O(n) - must traverse |
| Insert | O(n) - shift | O(1) - just relink |
| Delete | O(n) - shift | O(1) - just relink |
| Space | Fixed | Dynamic |

---

## 🤔 Why Use Linked Lists?

### Advantages ✅
- **Dynamic Size**: Grow/shrink easily
- **Fast Insertion/Deletion**: O(1) if position known
- **No Wasted Space**: Use only what you need
- **No Reallocation**: Don't need to copy when resizing

### Disadvantages ❌
- **Slow Access**: O(n) to reach an element
- **Extra Memory**: Need pointers (extra space per node)
- **Cache Unfriendly**: Non-contiguous memory

---

## 🔗 Node Structure

```java
class ListNode {
    int val;           // Data
    ListNode next;     // Pointer to next node
    
    ListNode(int val) {
        this.val = val;
        this.next = null;  // Initially points to nothing
    }
}
```

---

## 🔄 Basic Operations

### 1. Traversal (Visit Every Node)

```java
ListNode current = head;
while (current != null) {
    System.out.println(current.val);  // Process node
    current = current.next;            // Move to next
}
```

**Time: O(n), Space: O(1)**

### 2. Insert at Beginning

```
Original: head → [2|→] → [3|null]
Insert 1: 1 → [2|→] → [3|null]

Code:
ListNode newNode = new ListNode(1);
newNode.next = head;
head = newNode;
```

**Time: O(1)**

### 3. Insert at End

```
Original: head → [1|→] → [2|null]
Insert 3: head → [1|→] → [2|→] → [3|null]

Code:
ListNode current = head;
while (current.next != null) {
    current = current.next;
}
current.next = new ListNode(3);
```

**Time: O(n)** - Must traverse to end

### 4. Delete a Node

```
Original: head → [1|→] → [2|→] → [3|null]
Delete 2: head → [1|→] → [3|null]

Code:
ListNode prev = head;
while (prev.next.val != 2) {
    prev = prev.next;
}
prev.next = prev.next.next;  // Skip the node
```

**Time: O(n)** - Must find the node

---

## 🔥 Key Techniques for Linked List Problems

### 1️⃣ FAST & SLOW POINTER (Tortoise & Hare)

**Idea:** Two pointers move at different speeds (1 step vs 2 steps)

**Uses:**
- Detect cycles
- Find middle element
- Find kth last element

**Example: Find Middle**
```
List: 1 → 2 → 3 → 4 → 5

slow: 1 → 2 → 3
fast: 1 → 3 → 5

When fast reaches end, slow is at middle!
```

**Code:**
```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;  // Move 2 steps
}
return slow;  // slow is now at middle
```

**Time: O(n), Space: O(1)**

### 2️⃣ REVERSAL TECHNIQUE

**Idea:** Reverse the direction of pointers

**Example: Reverse 1 → 2 → 3**
```
Step 0: prev=null, curr=1, next=2
Step 1: null ← 1, curr=2, next=3
Step 2: null ← 1 ← 2, curr=3
Step 3: null ← 1 ← 2 ← 3

Result: 3 → 2 → 1
```

**Code:**
```java
ListNode prev = null;
ListNode current = head;
while (current != null) {
    ListNode next = current.next;  // Save next before changing link
    current.next = prev;            // Reverse the link
    prev = current;                 // Move prev forward
    current = next;                 // Move current forward
}
return prev;  // new head
```

**Time: O(n), Space: O(1)** - No extra space!

### 3️⃣ CYCLE DETECTION

**Idea:** Use fast/slow pointers. If they meet, there's a cycle.

**Why it works:**
- If no cycle: fast reaches null
- If cycle: fast will eventually catch slow (both in cycle)

**Code:**
```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) {
        return true;  // Cycle detected!
    }
}
return false;  // No cycle
```

**Time: O(n), Space: O(1)**

### 4️⃣ MERGE SORTED LISTS

**Idea:** Compare nodes from both lists, add smaller one to result

**Example:**
```
List 1: 1 → 3 → 5
List 2: 2 → 4 → 6

Merge: 1 → 2 → 3 → 4 → 5 → 6
```

**Code:**
```java
ListNode dummy = new ListNode(0);
ListNode current = dummy;
ListNode l1ptr = list1, l2ptr = list2;

while (l1ptr != null && l2ptr != null) {
    if (l1ptr.val < l2ptr.val) {
        current.next = l1ptr;
        l1ptr = l1ptr.next;
    } else {
        current.next = l2ptr;
        l2ptr = l2ptr.next;
    }
    current = current.next;
}

// Attach remaining
current.next = (l1ptr != null) ? l1ptr : l2ptr;
return dummy.next;
```

**Time: O(n+m), Space: O(1)** - No extra nodes created

---

## 🧠 Common Linked List Patterns

### Pattern 1: Dummy Node
Always create a dummy node to simplify edge cases:
```java
ListNode dummy = new ListNode(0);
dummy.next = head;
// Now you don't have to handle head specially
```

### Pattern 2: Two Pointer Technique
Keep two pointers for different purposes:
```java
ListNode slow = head, fast = head;
ListNode prev = null, current = head;
```

### Pattern 3: Save Next Before Modifying
```java
ListNode next = current.next;  // Always save!
current.next = something;       // Then modify
current = next;                 // Then move
```

---

## 💡 Common Mistakes

### ❌ Mistake 1: Null Pointer Exception
```java
// ❌ Wrong
while (current != null) {
    current = current.next;  // What if next is null?
    System.out.println(current.val);  // NPE!
}

// ✓ Right
while (current != null) {
    System.out.println(current.val);
    current = current.next;
}
```

### ❌ Mistake 2: Infinite Loop
```java
// ❌ Wrong - forgot to move pointer
ListNode current = head;
while (current != null) {
    // current never changes → infinite loop!
}

// ✓ Right
while (current != null) {
    current = current.next;  // Always move!
}
```

### ❌ Mistake 3: Lost Reference
```java
// ❌ Wrong
ListNode current = head;
head = head.next;  // Lost reference to head!
// Can't traverse anymore

// ✓ Right
ListNode dummy = new ListNode(0);
dummy.next = head;
// Work with dummy instead
```

---

## 🎓 Interview Questions - By Difficulty

### EASY ⭐
1. **Reverse Linked List** - Classic
2. **Merge Two Sorted Lists** - Standard
3. **Delete Node** - Simple removal
4. **Linked List Cycle** - Fast/slow pointers
5. **Middle of List** - Fast/slow pointers

### MEDIUM ⭐⭐
1. **Reverse Nodes in K-Group** - Harder reversal
2. **Remove Nth Node from End** - Two pointer
3. **Reorder List** - Find middle, reverse, merge
4. **Copy List with Random Pointer** - Tricky
5. **Odd Even Linked List** - Reorganization

### HARD ⭐⭐⭐
1. **LRU Cache** - Linked list + HashMap
2. **Merge K Sorted Lists** - Heap + merge
3. **Palindrome Linked List** - Reverse + comparison
4. **Swap Nodes in Pairs** - Careful pointer management

---

## 🎯 Interview Approach

### For Reversal Problems:
1. Draw it out (3 nodes minimum)
2. Save next pointer before modifying
3. Reverse the link
4. Move pointers forward
5. Return new head

### For Fast/Slow Problems:
1. Initialize: `slow = fast = head`
2. Move: `slow by 1, fast by 2`
3. Check for cycle or position

### For Merging Problems:
1. Create dummy node
2. Compare and attach smaller
3. Attach remaining list
4. Return `dummy.next`

---

## 📊 Time & Space Summary

| Operation | Time | Space | Notes |
|-----------|------|-------|-------|
| Access | O(n) | O(1) | Must traverse |
| Reverse | O(n) | O(1) | In-place |
| Find Middle | O(n) | O(1) | Fast/slow pointers |
| Detect Cycle | O(n) | O(1) | Fast/slow pointers |
| Merge Sorted | O(n+m) | O(1) | Two pointers |
| Delete Nth from End | O(n) | O(1) | Two pointers |

---

## ✨ Key Takeaways

1. **Linked lists are flexible** - can insert/delete without shifting
2. **Fast/slow pointers** solve many problems elegantly
3. **Always handle null carefully** - most bugs are NPE
4. **Draw it out** - visualizing helps immensely
5. **Save references** before modifying pointers
6. **Use dummy nodes** to simplify edge cases

---

## 🎯 Practice Plan

**Week 1:**
- Implement basic operations (insert, delete, traverse)
- Reverse a linked list 5 times from scratch

**Week 2:**
- Master fast/slow pointer technique
- Solve: find middle, detect cycle, remove nth

**Week 3:**
- Solve 5 easy problems on LeetCode
- Learn merge operation thoroughly

**Week 4:**
- Solve 5 medium problems
- Time yourself (goal: 12-15 min per problem)

---

**Next:** Learn Stacks and Queues - they use linked lists internally!
