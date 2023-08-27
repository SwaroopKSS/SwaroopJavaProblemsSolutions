
/*

Problem Description
        Given a sorted array of integers A (0-indexed) of size N, find the left most and the right most index of a given integer B in the array A.

        Return an array of size 2, such that
        First element = Left most index of B in A
        Second element = Right most index of B in A.
        If B is not found in A, return [-1, -1].

        Note : Your algorithm's runtime complexity must be in the order of O(log n).


        Problem Constraints
        1 <= N <= 106
        1 <= A[i], B <= 109


        Input Format
        The first argument given is the integer array A.
        The second argument given is the integer B.


        Output Format
        Return the left most and right most index (0-based) of B in A as a 2-element array. If B is not found in A, return [-1, -1].


        Example Input
        Input 1:

        A = [5, 7, 7, 8, 8, 10]
        B = 8
        Input 2:

        A = [5, 17, 100, 111]
        B = 3


        Example Output
        Output 1:

        [3, 4]
        Output 2:

        [-1, -1]

*/
public class Solution {
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public ArrayList<Integer> searchRange(final List<Integer> A, int B) {
    }

    public static int[] searchRange(int[] A, int B) {
        int leftIndex = findLeftIndex(A, B);
        int rightIndex = findRightIndex(A, B);

        if (leftIndex > rightIndex) {
            return new int[]{-1, -1};
        }

        return new int[]{leftIndex, rightIndex};
    }

    private static int findLeftIndex(int[] A, int B) {
        int left = 0;
        int right = A.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;  // Calculate middle index
            if (A[mid] >= B) {
                right = mid - 1;  // Update right boundary to search left half
                result = mid;      // Update result to store potential leftmost index
            } else {
                left = mid + 1;    // Update left boundary to search right half
            }
        }

        return result;
    }

    private static int findRightIndex(int[] A, int B) {
        int left = 0;
        int right = A.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;  // Calculate middle index
            if (A[mid] <= B) {
                left = mid + 1;    // Update left boundary to search right half
                result = mid;      // Update result to store potential rightmost index
            } else {
                right = mid - 1;   // Update right boundary to search left half
            }
        }

        return result;
    }
}