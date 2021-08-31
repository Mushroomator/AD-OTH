import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Excercise04 {

    public static void main(String[] args) {
        var testArr = new int[]{ 1, -3, 10, 4,75, 6, 23, 8, 9, 10 };
        var targetSum = 7;
        System.out.printf("""
                Array: %s,
                Target sum: %d
                """, Arrays.toString(testArr), targetSum);

        var resultLinear = findSum(testArr, targetSum);
        System.out.println("> Result (linear with hashing): " + resultLinear);
        var resultSuperLinear = findSumWithoutHashing(testArr, targetSum);
        System.out.println("> Result (super-linear without hashing): " + resultSuperLinear);
    }

    /**
     * Find sum within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, two values should have
     * @return true if there is a pair of values that add up to sum, false otherwise
     */
    public static boolean findSum(int[] array, int sum){
        // keep here all values that need to be found so the sum can be reached
        var required = new HashSet<Integer>();
        for (int current : array) {
            // check if the current value is required to reach the sum and whether we have encountered that value already
            if (required.contains(current)) {
                // yes we hava already encountered a value that together with the current value adds up to the sum
                return true;
            }
            // No we have not yet encountered a value which together with the current value adds up to the sum
            // save value which we need to encounter in the future to
            required.add(sum - current);
        }
        return false;
    }

    /**
     * Find sum within array.
     * Runtime:
     * - O(n * log n): Search with merge sort in O(n * log n) and then loop over n
     * @param array array with values
     * @param sum target sum, two values should have
     * @return true if there is a pair of values that add up to sum, false otherwise
     */
    public static boolean findSumWithoutHashing(int[] array, int sum){
        if(array.length < 2) return false;
        var sorted = mergeSort(array);

        var low = 0;
        var high = array.length - 1;

        while (low < high){
            int curSum = sorted[low] + sorted[high];
            if(curSum == sum) return true;
            if (curSum < sum) low++;
            else high--;
        }
        return false;
    }

    public static int[] mergeSort(int[] array){
        return mergeSort(array, 0, array.length - 1);
    }

    private static int[] mergeSort(int[] array, int first, int last){
        if(first < last){
            int middle = first + ((last - first) >> 1); //Alternative (first + last + 1)/2 but worse because first + last could be > Integer.MAX_VALUE -> Bug!
            mergeSort(array, first, middle);
            mergeSort(array, middle + 1, last);
            return mergeExSitu(array, first, last, middle);
        }
        return array;
    }

    public static int[] mergeExSitu(int[] array, int first, int last, int middle){
        int startLeft = first, startRight = middle + 1, endLeft = middle, endRight = last;
        int intervalLen = last - first + 1;
        int[] merge = new int[intervalLen];

        for(int i = 0; i < merge.length; i++){
            if(startLeft <= endLeft){
                if(startRight <= endRight){
                    if(array[startLeft] <= array[startRight]) merge[i] = array[startLeft++];
                    else merge[i] = array[startRight++];
                }
                else merge[i] = array[startLeft++];
            }
            else merge[i] = array[startRight++];
        }

        // copy values back
        for(int i = 0; i < intervalLen; i++){
            array[first + i] = merge[i];
        }
        return array;
    }
}


