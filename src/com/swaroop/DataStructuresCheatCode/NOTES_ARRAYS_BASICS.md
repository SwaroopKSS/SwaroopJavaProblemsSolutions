# 📖 ARRAYS BASICS - COMPLETE LEARNING GUIDE

## 🎯 What is an Array?

An **array** is a collection of elements stored in **contiguous memory locations**. Think of it like a row of boxes, each containing data.

```
Array: [10, 20, 30, 40, 50]
Index:  0   1   2   3   4
```

**Key Point:** Each element can be accessed directly using its index in O(1) time!

---

## 📊 Why Use Arrays?

### Advantages ✅
- **Fast Access**: O(1) time to access any element
- **Simple**: Easy to understand and use
- **Memory Efficient**: Contiguous memory, no extra pointers
- **Cache Friendly**: Predictable memory layout helps CPU cache

### Disadvantages ❌
- **Fixed Size**: Size is decided at creation (in many languages)
- **Slow Insertion/Deletion**: O(n) - need to shift elements
- **Wasteful**: If you allocate 100 but only use 10

---

## ⚡ Time Complexity

| Operation | Time | Why |
|-----------|------|-----|
| **Access** | O(1) | Direct index lookup |
| **Search** | O(n) | Need to check each element |
| **Insert** | O(n) | Need to shift elements right |
| **Delete** | O(n) | Need to shift elements left |

---

## 🔥 Key Techniques for Array Problems

### 1️⃣ TWO-POINTER TECHNIQUE

**Idea:** Use two pointers moving from opposite ends toward the middle.

**When to use:**
- Sorted arrays
- Finding pairs
- Removing duplicates
- Reversing arrays

**Example: Reverse Array**
```
Original: [1, 2, 3, 4, 5]

left = 0, right = 4
Swap arr[0] with arr[4]: [5, 2, 3, 4, 1]

left = 1, right = 3
Swap arr[1] with arr[3]: [5, 4, 3, 2, 1]

Done! Array is reversed
```

**Code Pattern:**
```java
int left = 0, right = arr.length - 1;
while (left < right) {
    // Do something with arr[left] and arr[right]
    swap(left, right);
    left++;
    right--;
}
```

**Interview Questions:**
- Reverse array
- Two sum in sorted array
- Container with most water
- Valid palindrome

---

### 2️⃣ SLIDING WINDOW TECHNIQUE

**Idea:** Maintain a window of elements and slide it across the array.

**When to use:**
- Finding subarray/substring with specific property
- Maximum/minimum in window
- Count elements matching condition

**Example: Maximum Sum of K Consecutive Elements**

```
Array: [1, 3, 2, 6, 4, 1], K = 3

Window: [1, 3, 2] = 6
Move: [3, 2, 6] = 11  ← Max so far
Move: [2, 6, 4] = 12  ← New max
Move: [6, 4, 1] = 11

Answer: 12
```

**Code Pattern:**
```java
// Initial window
int sum = 0;
for (int i = 0; i < k; i++) {
    sum += arr[i];
}
int maxSum = sum;

// Slide the window
for (int i = k; i < arr.length; i++) {
    sum = sum - arr[i-k] + arr[i];  // Remove left, add right
    maxSum = Math.max(maxSum, sum);
}
```

**Time Complexity: O(n)** instead of O(n*k)!

**Interview Questions:**
- Maximum sum of k elements
- Longest substring without repeating
- Sliding window maximum
- Minimum window substring

---

### 3️⃣ PREFIX SUM TECHNIQUE

**Idea:** Precompute cumulative sums to answer range sum queries in O(1).

**When to use:**
- Range sum queries
- Subarrays with target sum
- 2D matrix sum queries

**Example: Range Sum Query**

```
Array: [1, 3, 5, 7, 9]

Prefix: [0, 1, 4, 9, 16, 25]
         ↑  ↑  ↑  ↑   ↑   ↑
         0  1  1+3 1+3+5...

Query: Sum from index 1 to 3?
Answer: prefix[4] - prefix[1] = 16 - 1 = 15
(which is 3 + 5 + 7 = 15 ✓)
```

**Code Pattern:**
```java
// Build prefix sum array
int[] prefix = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefix[i+1] = prefix[i] + arr[i];
}

// Query range sum [left, right]
int sum = prefix[right+1] - prefix[left];
```

**Time Complexity:**
- Building: O(n)
- Each query: O(1)

**Interview Questions:**
- Range sum query
- Subarray sum equals K
- Contiguous array
- Matrix 2D sum

---

### 4️⃣ KADANE'S ALGORITHM (Maximum Subarray)

**Idea:** Keep track of maximum sum ending at current position.

**When to use:**
- Maximum subarray sum
- Stock buy/sell problems
- Maximum product subarray

**Example: [−2, 1, −3, 4, −1, 2, 1, −5, 4]**

```
Index  Element  Max Sum Ending Here  Max So Far
0      -2       -2                   -2
1      1        1                    1
2      -3       -2                   1
3      4        4                    4
4      -1       3                    4
5      2        5                    5
6      1        6                    6  ← Answer
7      -5       1                    6
8      4        5                    6

Maximum subarray: [4, -1, 2, 1] = 6
```

**Code Pattern:**
```java
int maxCurrent = arr[0];
int maxGlobal = arr[0];

for (int i = 1; i < arr.length; i++) {
    maxCurrent = Math.max(arr[i], maxCurrent + arr[i]);
    maxGlobal = Math.max(maxGlobal, maxCurrent);
}
return maxGlobal;
```

**Time Complexity: O(n), Space: O(1)**

**Key Insight:** At each position, decide: extend previous subarray or start new?

**Interview Questions:**
- Maximum subarray sum
- Buy stock and sell once
- House robber
- Palindrome partitioning

---

## 🧠 Common Array Patterns

### Pattern 1: Remove Duplicates
**Idea:** Use two pointers, one to read, one to write
```java
int writeIdx = 1;
for (int i = 1; i < arr.length; i++) {
    if (arr[i] != arr[i-1]) {
        arr[writeIdx] = arr[i];
        writeIdx++;
    }
}
return writeIdx;
```

### Pattern 2: Rotate Array
**Idea:** Reverse three parts
```
Original: [1,2,3,4,5], rotate by 2
Reverse all: [5,4,3,2,1]
Reverse [0,k): [4,5,3,2,1]
Reverse [k,n): [4,5,1,2,3] ✓
```

### Pattern 3: Product of Array Except Self
**Idea:** Use prefix and suffix products
```
[1,2,3,4]
Prefix: [1,1,2,6]
Suffix: [24,12,4,1]
Result: [24,12,8,6]
```

---

## 💡 Tips & Tricks

### 1. Handle Edge Cases
```java
if (arr == null || arr.length == 0) {
    return;  // Handle empty array
}
```

### 2. Integer Overflow
```java
long sum = 0;  // Use long for sum
int result = (int) sum;
```

### 3. In-Place Modifications
When problem says "modify in-place", don't use extra space:
```java
// ❌ Wrong - uses O(n) extra space
List<Integer> result = new ArrayList<>();
for (int x : arr) { result.add(x); }

// ✓ Right - O(1) extra space
int writeIdx = 0;
for (int i = 0; i < arr.length; i++) {
    arr[writeIdx++] = arr[i];
}
```

### 4. Comparing Adjacent Elements
```java
for (int i = 0; i < arr.length - 1; i++) {
    if (arr[i] > arr[i+1]) {
        // Compare consecutive elements
    }
}
```

---

## 🎓 Interview Questions - Difficulty Levels

### EASY ⭐
1. **Reverse Array** - Basic two-pointer
2. **Find Maximum/Minimum** - Simple loop
3. **Remove Duplicates** - Two pointer, sorted array
4. **Move Zeros** - Two pointer
5. **Valid Palindrome** - Two pointer from ends

### MEDIUM ⭐⭐
1. **Two Sum** - HashMap or two pointer (sorted)
2. **Maximum Subarray** - Kadane's algorithm
3. **Container with Most Water** - Two pointer
4. **Best Time to Buy Stock** - Kadane's variant
5. **Product of Array Except Self** - Prefix/suffix

### HARD ⭐⭐⭐
1. **Trapping Rain Water** - Two pointer + dynamic programming
2. **Longest Substring Without Repeating** - Sliding window
3. **Median of Two Sorted Arrays** - Binary search on arrays
4. **Minimum Window Substring** - Sliding window + HashMap

---

## 🔍 How to Solve Array Problems

### Step 1: Understand the Problem
- What's the input? What's the output?
- Any constraints? (sorted, in-place, etc.)
- Is time/space critical?

### Step 2: Identify the Pattern
```
"Find pair" → Two-pointer or HashMap
"Find subarray" → Sliding window or Prefix sum
"Maximum/minimum sequence" → Kadane's or DP
"Sorted array" → Binary search
```

### Step 3: Write Solution
- Start with brute force (understand the logic)
- Optimize using techniques above
- Handle edge cases

### Step 4: Test & Verify
- Empty array
- Single element
- All same elements
- Negative numbers

---

## 📚 Real-World Applications

- **Databases**: Arrays for storing table data
- **Graphics**: 2D arrays for images
- **Games**: Arrays for game board state
- **Search Engines**: Arrays for ranking results
- **Compilers**: Arrays for storing variables

---

## ✨ Key Takeaways

1. **Arrays are fast for access** (O(1)) but slow for insertion/deletion (O(n))
2. **Two-pointer technique** is essential for many array problems
3. **Sliding window** solves most subarray/substring problems
4. **Prefix sum** makes range queries instant
5. **Kadane's algorithm** is elegant solution to max subarray
6. **Always consider edge cases** - empty, single element, duplicates

---

## 🎯 Practice Plan

**Week 1:**
- Master two-pointer (reverse, palindrome, two sum)
- Understand sliding window with examples

**Week 2:**
- Solve 5 easy array problems on LeetCode
- Implement Kadane's algorithm 3 times from scratch

**Week 3:**
- Solve 5 medium problems (two sum, max subarray, etc.)
- Learn when to use which technique

**Week 4:**
- Solve 5 hard problems
- Time yourself (goal: 10-15 min per problem)

---

**Next:** Learn LinkedLists and see why arrays aren't always the best choice!
