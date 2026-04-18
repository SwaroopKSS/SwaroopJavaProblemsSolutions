package com.swaroop.DataStructuresCheatCode;

/**
 * BIT MANIPULATION - BINARY OPERATIONS
 * 
 * 💡 KEY TRICKS:
 * 1. XOR Properties: a ^ a = 0, a ^ 0 = a, a ^ b = b ^ a
 * 2. AND Properties: a & a = a, a & 0 = 0
 * 3. OR Properties: a | a = a, a | 0 = a
 * 4. Check bit: (n >> i) & 1
 * 5. Set bit: n | (1 << i)
 * 6. Clear bit: n & ~(1 << i)
 * 7. Toggle bit: n ^ (1 << i)
 * 
 * USE FOR:
 * - Find single number in array
 * - Count set bits
 * - Power of 2 check
 * - Subsets generation
 * - Game theory problems
 */

public class BitManipulation {
    
    // ========================================
    // 1. FIND SINGLE NUMBER
    // ========================================
    
    /**
     * Single Number I - All appear twice except one
     * 
     * Trick: XOR all, duplicates cancel out, single remains
     * Time: O(n), Space: O(1)
     */
    public static int singleNumberI(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;  // XOR all, pairs become 0
        }
        return result;
    }
    
    /**
     * Single Number II - All appear 3 times except one
     * 
     * Trick: Track bits that appear once and twice
     * Time: O(n), Space: O(1)
     */
    public static int singleNumberII(int[] nums) {
        int ones = 0;   // Bits appeared once
        int twos = 0;   // Bits appeared twice
        
        for (int num : nums) {
            twos |= ones & num;      // If bit in ones and num, move to twos
            ones ^= num;              // Toggle bit in ones
            int threes = ones & twos; // Find bits that appeared 3 times
            ones &= ~threes;          // Remove from ones
            twos &= ~threes;          // Remove from twos
        }
        
        return ones;
    }
    
    /**
     * Single Number III - All appear twice except two
     * 
     * Trick: XOR groups by same bit position
     * Time: O(n), Space: O(1)
     */
    public static int[] singleNumberIII(int[] nums) {
        int xorResult = 0;
        for (int num : nums) {
            xorResult ^= num;
        }
        
        // Find rightmost set bit (differentiates the two singles)
        int rightBit = xorResult & -xorResult;
        
        int num1 = 0, num2 = 0;
        for (int num : nums) {
            if ((num & rightBit) == 0) {
                num1 ^= num;
            } else {
                num2 ^= num;
            }
        }
        
        return new int[]{num1, num2};
    }
    
    // ========================================
    // 2. BIT COUNT & POSITION
    // ========================================
    
    /**
     * Count number of 1s in binary representation
     * 
     * Time: O(k) where k = number of set bits
     */
    public static int countSetBits(int n) {
        int count = 0;
        while (n > 0) {
            count += n & 1;
            n >>= 1;
        }
        return count;
    }
    
    /**
     * Count set bits using Brian Kernighan's Algorithm
     * 
     * Trick: n & (n-1) removes rightmost set bit
     * Time: O(k) where k = number of set bits
     */
    public static int countSetBitsOptimal(int n) {
        int count = 0;
        while (n > 0) {
            n &= (n - 1);  // Remove rightmost set bit
            count++;
        }
        return count;
    }
    
    /**
     * Check if number is power of 2
     * 
     * Trick: Power of 2 has only one set bit
     * n & (n-1) == 0 for powers of 2
     */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
    /**
     * Find position of rightmost set bit
     */
    public static int getRightmostBitPosition(int n) {
        // Position is log2(n & -n) + 1, or simply:
        return Integer.numberOfTrailingZeros(n & -n);
    }
    
    // ========================================
    // 3. POWER OF NUMBERS
    // ========================================
    
    /**
     * Check if number is power of 3
     */
    public static boolean isPowerOfThree(int n) {
        if (n <= 0) return false;
        while (n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }
    
    /**
     * Check if number is power of 4
     * 
     * Trick: Power of 4 has form 4^k = 2^(2k)
     * Only powers of 4 have bit pattern matching 0x55555555
     */
    public static boolean isPowerOfFour(int n) {
        if (n <= 0) return false;
        // 0x55555555 in binary for 32-bit: 01010101...
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }
    
    // ========================================
    // 4. BIT MANIPULATION TRICKS
    // ========================================
    
    /**
     * Get bit at position i (0-indexed from right)
     */
    public static int getBit(int n, int i) {
        return (n >> i) & 1;
    }
    
    /**
     * Set bit at position i
     */
    public static int setBit(int n, int i) {
        return n | (1 << i);
    }
    
    /**
     * Clear bit at position i
     */
    public static int clearBit(int n, int i) {
        return n & ~(1 << i);
    }
    
    /**
     * Toggle bit at position i
     */
    public static int toggleBit(int n, int i) {
        return n ^ (1 << i);
    }
    
    /**
     * Check if bit at position i is set
     */
    public static boolean isBitSet(int n, int i) {
        return ((n >> i) & 1) == 1;
    }
    
    // ========================================
    // 5. SUBSET GENERATION (Bit Masking)
    // ========================================
    
    /**
     * Generate all subsets using bit masking
     * 
     * For array of size n, there are 2^n subsets
     * Use binary representation (0 to 2^n - 1)
     * 
     * Time: O(n * 2^n)
     */
    public static java.util.List<java.util.List<Integer>> allSubsets(int[] nums) {
        java.util.List<java.util.List<Integer>> result = new java.util.ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n;  // 2^n
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            java.util.List<Integer> subset = new java.util.ArrayList<>();
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            
            result.add(subset);
        }
        
        return result;
    }
    
    // ========================================
    // 6. MISSING NUMBER
    // ========================================
    
    /**
     * Find missing number in array [0, 1, 2, ..., n]
     * 
     * Approach 1: XOR all indices with all numbers
     * Time: O(n), Space: O(1)
     */
    public static int findMissing(int[] nums) {
        int n = nums.length;
        int xor = 0;
        
        // XOR all numbers
        for (int num : nums) {
            xor ^= num;
        }
        
        // XOR with all indices
        for (int i = 0; i <= n; i++) {
            xor ^= i;
        }
        
        return xor;
    }
    
    // ========================================
    // 7. HAMMING DISTANCE
    // ========================================
    
    /**
     * Hamming Distance - Count differing bits
     * 
     * Time: O(1) for 32-bit integers
     */
    public static int hammingDistance(int x, int y) {
        int xor = x ^ y;
        return countSetBitsOptimal(xor);
    }
    
    /**
     * Total Hamming Distance - Sum of distances between all pairs
     * 
     * Time: O(n * 32) = O(n)
     */
    public static int totalHammingDistance(int[] nums) {
        int total = 0;
        
        for (int i = 0; i < 32; i++) {
            int ones = 0;
            
            for (int num : nums) {
                if (((num >> i) & 1) == 1) {
                    ones++;
                }
            }
            
            int zeros = nums.length - ones;
            total += ones * zeros;
        }
        
        return total;
    }
    
    // ========================================
    // MAIN - Test Bit Manipulation
    // ========================================
    
    public static void main(String[] args) {
        System.out.println("=== BIT MANIPULATION ===\n");
        
        // 1. Single Number
        System.out.println("1. SINGLE NUMBER PROBLEMS");
        System.out.println("Single I [4,1,2,1,2]: " + singleNumberI(new int[]{4, 1, 2, 1, 2}));
        System.out.println("Single II [0,1,0,1,0,1,99]: " + 
                          singleNumberII(new int[]{0, 1, 0, 1, 0, 1, 99}));
        System.out.println("Single III [1,2,1,3,2,5]: " + 
                          java.util.Arrays.toString(singleNumberIII(new int[]{1, 2, 1, 3, 2, 5})) + "\n");
        
        // 2. Bit Counting
        System.out.println("2. BIT COUNTING");
        System.out.println("Count set bits in 9: " + countSetBits(9));
        System.out.println("Is 16 power of 2: " + isPowerOfTwo(16));
        System.out.println("Is 27 power of 3: " + isPowerOfThree(27) + "\n");
        
        // 3. Bit Operations
        System.out.println("3. BIT OPERATIONS");
        int n = 5;  // 0101 in binary
        System.out.println("Original: " + n);
        System.out.println("Set bit 1: " + setBit(n, 1));
        System.out.println("Clear bit 0: " + clearBit(n, 0));
        System.out.println("Toggle bit 2: " + toggleBit(n, 2) + "\n");
        
        // 4. Subsets
        System.out.println("4. ALL SUBSETS");
        java.util.List<java.util.List<Integer>> subsets = allSubsets(new int[]{1, 2});
        System.out.println("Subsets of [1,2]: " + subsets.size() + " subsets\n");
        
        // 5. Missing Number
        System.out.println("5. MISSING NUMBER");
        System.out.println("Missing in [3,0,1]: " + findMissing(new int[]{3, 0, 1}) + "\n");
        
        // 6. Hamming Distance
        System.out.println("6. HAMMING DISTANCE");
        System.out.println("Distance 1 (001) and 4 (100): " + hammingDistance(1, 4));
        System.out.println("Total distance [4,14,2]: " + 
                          totalHammingDistance(new int[]{4, 14, 2}));
    }
}
