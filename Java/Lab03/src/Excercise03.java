import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;

public class Excercise03 {

    static boolean[] pivots = new boolean[10];

    public static void main(String[] args) {
        var testArr = fillArrayRand(10, -30, 50);

        System.out.printf("Unsorted array: %s%n", Arrays.toString(testArr));
        var sorted = bubbleSortMin(testArr.clone());
        System.out.printf("Sorted array: %s%n", Arrays.toString(sorted));
        System.out.println("Correct? " + isSortedCorrectly(testArr, sorted, false));
    }

    public static int[] quickSort(int[] array){
        return quickSort(array, 0, array.length - 1);
    }

    private static int[] quickSort(int[] array, int first, int last){
        if(first < last){
            int part = preparePartition(array, first, last);
            pivots[part] = true;
            for(int i = 0; i < array.length; i++){
                System.out.printf("| %3d ", array[i]);
            }
            System.out.println(" |");
            for(int i = 0; i < array.length; i++){
                if(pivots[i]) System.out.print("  --- ");
                else System.out.print("      ");
            }
            System.out.println();

            quickSort(array, first, part - 1);
            quickSort(array, part + 1, last);
        }

        return array;
    }

    private static int preparePartition(int[] array, int first, int last){
        // determine random pivot element
        int pivotIdx = first;//randomIntBetween(first, last);
        // swap pivot to front
        swap(array, first, pivotIdx);

        // Procede as usual
        int splitAt = first - 1;
        int pivot = array[first];

        for(int i = first; i <= last; i++){
            if(array[i] <= pivot){
                splitAt++;
                swap(array, i, splitAt);
            }
        }
        swap(array, first, splitAt);
        return splitAt;
    }

    private static int randomIntBetween(int start, int end){
        return (int)( (end - start) * Math.random() + start);
    }

    private static void swap(int[] array, int idxA, int idxB){
        int temp =  array[idxA];
        array[idxA] = array[idxB];
        array[idxB] = temp;
    }

    public static int[] fillArrayRand(int size, int randStart, int randEnd){
        int[] randArray = new int[size];
        for(int i = 0; i < size; i++){
            randArray[i] = randomIntBetween(randStart, randEnd);
        }
        return randArray;
    }

    public static int[] selectionSortMax(int[] array){
        // start from back: after each iteration array.length - i values are already sorted
        for(int i = array.length - 1; i > 0; i--){
            int max = i;
            for(int j = 0; j <= i; j++){
                if(array[j] > array[max]) max = j;
            }
            // swap max value to back
            swap(array, max, i);
        }
        return array;
    }

    public static int[] insertionSortMax(int[] array){

        /*
        // Insertion sort as shown in lecture
        for(int j = 1; j < array.length; j++){
            var key = array[j];
            int i = j-1;
            while (i >= 0 && array[i] > key){
                array[i+1] = array[i];
                i--;
            }
            array[i+1] = key;
            // print out array (array till index j is sorted)
            //System.out.println(Arrays.toString(array));
        }
        return array;

         */



        for (int j = array.length - 2; j >= 0; j--){
            int key = array[j];
            int i = j + 1;
            while (i < array.length && array[i] < key){
                array[i-1] = array[i];
                i++;
            }
            array[i-1] = key;
            // print out array (array.length - j elements from back are sorted)
            System.out.println(Arrays.toString(array));
        }
        return array;
    }

    /**
     * Check if sorting is correct depending on ascending and descending order and also checks whether all the original values are still there.
     * @param unsortedArray  unsorted array
     * @param sortedArray sorted array (created by sorting the unsorted array)
     * @param checkAsc when true, ascending order is checked, descending order otherwise
     * @return true, if sorted correctly false otherwise
     */
    public static boolean isSortedCorrectly(int[] unsortedArray, int[] sortedArray, boolean checkAsc){
        if( (sortedArray.length != unsortedArray.length)) return false;
        // arrays have same length
        if(sortedArray.length == 0) return true;
        if(sortedArray.length == 1) return sortedArray[0] == unsortedArray[0];


        Predicate<Integer> checkSorting;
        if(checkAsc) checkSorting = i -> sortedArray[i-1] <= sortedArray[i];
        else checkSorting = i -> sortedArray[i-1] > sortedArray[i];

        var allSortedElements = new HashSet<Integer>();
        // arrays both have at least two elements
        // check if values are in ascending order and put all values in sorted array in a map inorder to check if all
        // values are still there
        for(int i = 1; i < sortedArray.length; i++){
            if(checkSorting.test(i)) return false;
            // add all elements to map (except last -> this is done after loop)
            allSortedElements.add(sortedArray[i-1]);
        }
        // add last element
        allSortedElements.add(sortedArray[sortedArray.length - 1]);

        // Check if all elements from original array are in sorted array
        for(var cur: unsortedArray){
            if(!allSortedElements.contains(cur)) return false;
        }
        return true;
    }

    public static int[] bubbleSortMin(int[] array){
        for (int j = 0; j < array.length; j++) {
            for(int i = array.length - 2; i >= j; i--){
                if(array[i+1] <= array[i]) swap(array, i, i+1);
            }
            //
            System.out.println(Arrays.toString(array));
        }
        return array;
    }

    public static int[] bubbleSortMax(int[] array){
        for(int j = array.length - 2; j >= 0; j--){
            for (int i = 0; i <= j; i++){
                if(array[i] > array[i+1]){
                    swap(array, i, i+1);
                }
            }
            // print out array
            // in each iteration maxmium of values within array.length - j will be moved to the back
            System.out.println(Arrays.toString(array));
        }
        return array;
    }
}
