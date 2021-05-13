import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Random;

public class PartialSum {

    public static void main(String[] args){
        int[] test = fillRand(1000, -100, 100);
        System.out.println("Test values: " + Arrays.toString(test));

        // kubic runtime
        long startKubic = System.currentTimeMillis();
        System.out.println("Kubic: " + maxPartialSumKubic(test));
        long endKubic = System.currentTimeMillis();

        // quadratic runtime
        long startQuadratic = System.currentTimeMillis();
        System.out.println("Quadratic: " + maxPartialSumQuadratic(test));
        long endQuadratic = System.currentTimeMillis();

        // linear runtime
        long startLinear = System.currentTimeMillis();
        System.out.println("Linear: " + maxPartialSumLinear(test));
        long endLinear = System.currentTimeMillis();

        System.out.println("\nTime elapsed (Kubic): " + Double.toString(endKubic - startKubic) + "ms");
        System.out.println("Time elapsed (Quadratic): " + Double.toString(endQuadratic - startQuadratic) + "ms");
        System.out.println("Time elapsed (Linear): " + Double.toString(startLinear - startLinear) + "ms");
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
                curIndices = new int[j - i];
                int counter = 0;
                for(int k = i; k < j; k++) {
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
}
