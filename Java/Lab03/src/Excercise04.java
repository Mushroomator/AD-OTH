import java.util.*;

public class Excercise04 {

    public static void main(String[] args) {
        var testArr = new int[]{ 1, -3, 10, 4,75, 6, 23, 8, 9, 10 };
        var targetSum = 7;
        System.out.printf("""
                +-------------------------------------
                | Sum with 2 summands                |
                +-------------------------------------
                Array: %s,
                Target sum: %d
                """, Arrays.toString(testArr), targetSum);

        var resultLinear = findSum2Summands(testArr, targetSum);
        System.out.println("> Result (linear with hashing): " + resultLinear);
        var resultSuperLinear = findSumWithoutHashing(testArr, targetSum);
        System.out.println("> Result (super-linear without hashing): " + resultSuperLinear);

        System.out.printf("""
                
                +-------------------------------------
                | Sum with 3 summands                |
                +-------------------------------------
                Array: %s,
                Target sum: %d
                """, Arrays.toString(testArr), targetSum);

        var resultQuadratic3summands = findSum3Summands(testArr, targetSum);
        System.out.println("> Result (quadratic with hashing): " + resultQuadratic3summands);
    }

    /**
     * Has sum of 2 summands within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>two</em> values should have
     * @return true, if sum can be created from three values within the array, false otherwise
     */
    public static boolean hasSum2Summands(int[] array, int sum){
        return findSum2Summands(array, sum, 0, array.length - 1).isPresent();
    }

    /**
     * Find sum of two summands within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>two</em> values should have
     * @return List of integers that build sum or empty Optional
     */
    public static Optional<List<Integer>> findSum2Summands(int[] array, int sum){
        return findSum2Summands(array, sum, 0, array.length - 1);
    }

    /**
     * Find sum within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>two</em> values should have
     * @param first index to start search for summands
     * @param last index when to end search for summands
     * @return List of integers that build sum or empty Optional
     */
    public static Optional<List<Integer>> findSum2Summands(int[] array, int sum, int first, int last){
        if(array.length < 2) return Optional.empty();
        if(first < 0 || first >= array.length || last <= first || last >= array.length) throw new IllegalArgumentException("first and last must be within array bounds!");

        // keep here all values that need to be found so the sum can be reached
        var required = new HashSet<Integer>();
        for (int i = first; i <= last; i++) {
            // check if the current value is required to reach the sum and whether we have encountered that value already
            if (required.contains(sum - array[i])) {
                // yes we hava already encountered a value that together with the current value adds up to the sum
                // pair is found
                var listOfVals = new LinkedList<Integer>();
                listOfVals.add(sum - array[i]);
                listOfVals.add(array[i]);
                return Optional.of(listOfVals);
            }
            // No we have not yet encountered a value which together with the current value adds up to the sum
            // save value which we need to encounter in the future to
            required.add(array[i]);
        }
        return Optional.empty();
    }

    /**
     * Has sum of 3 summands within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>three</em> values should have
     * @return true, if sum can be created from three values within the array, false otherwise
     */
    public static boolean hasSum3Summands(int[] array, int sum){
        return findSum3Summands(array, sum, 0, array.length - 1).isPresent();
    }

    /**
     * Find sum within array.
     * Runtime:
     * - O(n): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>two</em> values should have
     * @return List of integers that build sum or empty Optional
     */
    public static Optional<List<Integer>> findSum3Summands(int[] array, int sum){
        return findSum3Summands(array, sum, 0, array.length - 1);
    }


    /**
     * Find sum within array.
     * Runtime:
     * - BC: O(n): in case the first fixed is one of the three summands
     * - WC: O(n²): in case last three elements in array build the sum; Gaussian summation!
     * - AC: O(n²): assuming equal distribution on average we will be searching half of the array
     * - O(n²): Loop over n and lookup in HashTable needs O(1) time
     * @param array array with values
     * @param sum target sum, <em>three</em> values should have
     * @param first index to start search for summands
     * @param last index when to end search for summands
     * @return List of integers that build sum or empty Optional
     */
    public static Optional<List<Integer>> findSum3Summands(int[] array, int sum, int first, int last){
        // loop over all elements except last two as 3 values are required per iteration:
        // if 3 from last has been processed we can be sure there are only two values left so it is impossible to achieve the sum with 3 summands
        for (int i = first; i <= last - 2; i++) {
            var fixed = array[i];
            // "fix" one value then proceed exactly the same as with two summand, except that we can decrease the effective length of the array by one
            // for every iteration as in first iteration array[0] is fixed and all possible combinations using array[0] have been found. So in next iteration
            // we can ignore array[i]
            var result = findSum2Summands(array, sum - fixed, i, last);
            if(result.isPresent()) {
                result.get().add(fixed);
                return result;
            }
        }
        return Optional.empty();
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


