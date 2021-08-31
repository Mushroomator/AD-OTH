import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class PartialSum {

    public static void main(String[] args){
        var randArr = fillRand(100, -100, 100);
        var lectureArr = new int[]{ -13, 25, 34, 12, -3, 7, -87, 28, -77, 11};
        int[] test = lectureArr;
        System.out.println("Test values: " + Arrays.toString(test));

        // linear runtime
        System.out.println("Starting algorithm with linear runtime behavior...");
        long startLinear = System.currentTimeMillis();
        System.out.println("Linear: " + maxPartialSumLinear(test));
        long endLinear = System.currentTimeMillis();
        System.out.println("Time elapsed (Linear): " + Double.toString(startLinear - startLinear) + "ms\n");

        // superlinear runtime
        System.out.println("Starting algorithm with superlinear runtime behavior...");
        long startSupLin = System.currentTimeMillis();
        System.out.println("Superlinear: " + maxPartialSumSuperlinearDivAndConq(test, 0, test.length - 1));
        long endSupLin = System.currentTimeMillis();
        System.out.println("Time elapsed (Superlinear): " + Double.toString(endSupLin - startSupLin) + "ms\n");

        // quadratic runtime
        System.out.println("Starting algorithm with quadratic runtime behavior...");
        long startQuadratic = System.currentTimeMillis();
        System.out.println("Quadratic: " + maxPartialSumQuadratic(test));
        long endQuadratic = System.currentTimeMillis();
        System.out.println("Time elapsed (Quadratic): " + Double.toString(endQuadratic - startQuadratic) + "ms\n");

        // kubic runtime
        System.out.println("Starting algorithm with kubic runtime behavior...");
        long startKubic = System.currentTimeMillis();
        System.out.println("Kubic: " + maxPartialSumKubic(test));
        long endKubic = System.currentTimeMillis();
        System.out.println("Time elapsed (Kubic): " + Double.toString(endKubic - startKubic) + "ms\n");

    }

    public static int[] fillRand(int size, int start, int end){
        int[] testVals = new int[size];
        for(int i = 0; i < testVals.length; i++){
            testVals[i] = (int)((end - start) * Math.random() + start);
        }
        return testVals;
    }

    public static int[] fillRand(int size){
        return fillRand(size, 0, 100);
    }

    /**
     * Calculate max. partial sum of array the naive way in O(n³)
     * @param array array with ints
     * @return max. partial sum
     */
    public static int maxPartialSumKubic(int[] array){
        int sum, max = Integer.MIN_VALUE;
        int[] curIndices;
        int[] maxIndices = new int[array.length];

        for(int i = 0; i < array.length; i++){
            for(int j = i; j < array.length; j++){
                sum = 0;
                // save indices
                curIndices = new int[j - i + 1];
                int counter = 0;
                for(int k = i; k <= j; k++) {   //here "<=" as always at least one value must be computed!
                    curIndices[counter] = k;
                    sum += array[k];
                    counter++;
                }
                if(sum > max) {
                    max = sum;
                    maxIndices = curIndices;
                }
            }
        }

        int[] maxSumVals = new int[maxIndices.length];
        int i = 0;
        for(int idx: maxIndices){
            maxSumVals[i] = array[idx];
            i++;
        }
        System.out.println("Indices to form max. sum: " + Arrays.toString(maxIndices) + "\nValues to form max. sum:  " + Arrays.toString(maxSumVals));
        return max;
    }

    /**
     * Calculate max. partial sum of array by adding on to previously computed sums in O(n²).
     * @param array array with ints
     * @return max. partial sum
     */
    public static int maxPartialSumQuadratic(int[] array){
        int sum, max = Integer.MIN_VALUE;

        for(int i = 0; i < array.length; i++){
            sum = 0;
            for(int j = i; j < array.length; j++){
                sum += array[j];
                if(sum > max) max = sum;
            }
        }

        return max;
    }

    /**
     * Calculate max. partial sum of array by computing local and global maximums by using either the value at an position or everything "left" from it in O(n)
     * @param array array with ints
     * @return max. partial sum
     */
    public static int maxPartialSumLinear(int[] array){
        int curSum = 0, nextSum = 0, max = Integer.MIN_VALUE;

        for(int i = 0; i < array.length; i++){
            // "left Sum" + "current Value"
            nextSum = curSum + array[i];
            // if "new sum" > to value in array -> set "current sum" to "new sum"
            if(nextSum > array[i]) curSum = nextSum;
            // if "new sum" is smaller -> set "current sum" to value in array
            else curSum = array[i];
            // set global maximum if necessary
            if(curSum > max) max = curSum;
        }

        return max;
    }

    /**
     * Calculate max. partial sum using divide & conquer algorithm (dividing array into 2 sub arrays).
     * Runtime: O(n * log(n))
     * Note: Using 5 subarrays and some trick a linear d&d algorithm (= O(n)) is possible.
     * @param array array with ints
     * @param first index of first element (starts at 0)
     * @param last index of last element (length - 1)
     * @return max. partial sum
     */
    public static int maxPartialSumSuperlinearDivAndConq(int[] array, int first, int last){
        int length = last - first + 1;
        if(length == 1) return array[first];
        // divide array in two sub arrays (half them)
        int newLength = length % 2 == 0 ? length >> 1 : (length >> 1) + 1;

        // calculate indices for recursion
        int end1stHalf = first + newLength - 1;
        int start2ndHalf = end1stHalf + 1;

        // calculate sum "in middle" in case max. partial sum spans across both subarrays

        // Another way is to do this is to decrement end1stHalf and increment start2ndHalf by 1 to initialize the sum1stHalf to array[end1stHalf] and sum2ndHalf to array[start2ndHalf].
        // This would save one operation per while loop as the initial value is computed already, making it one tiny bit faster than this implementation.
        int i = end1stHalf;
        int sum1stHalf = Integer.MIN_VALUE,
            curSum = 0;

        // calculate max. partial sum from middle to left
        // while loops responsible for n iterations each time this function is called (= log(n) times)
        while(i >= first){
            curSum += array[i];
            i--;
            if(curSum > sum1stHalf) sum1stHalf = curSum;
        }

        // reset current sum
        int j = start2ndHalf, sum2ndHalf = Integer.MIN_VALUE;
        curSum = 0;

        // calculate max. partial sum from middle to right
        while(j <= last){
            curSum += array[j];
            j++;
            if(curSum > sum2ndHalf) sum2ndHalf = curSum;
        }

        // return max. partial sum of the three calculated sums
        return Math.max(
                maxPartialSumSuperlinearDivAndConq(array, first, end1stHalf), Math.max( // max. partial sum in 1st subarray
                        maxPartialSumSuperlinearDivAndConq(array, start2ndHalf, last), // max. partial sum in 2nd subarray
                        sum1stHalf + sum2ndHalf));  // max. partial sum in middle
    }
}
