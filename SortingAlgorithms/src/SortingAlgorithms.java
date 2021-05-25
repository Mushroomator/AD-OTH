import java.util.Arrays;

public class SortingAlgorithms {

    public static void main(String[] args){
        int[] arrayToSortLecture = { 34, 45, 12, 34, 23, 18, 38, 17, 43, 7};
        int[] randArray = fillArrayRand(10000, 0, 100);

        System.out.println("""
                +-------------------------------------------+
                | Sorting Algorithms and their runtime      |
                +-------------------------------------------+
                Unsorted array: %s
                
                """.formatted(Arrays.toString(arrayToSortLecture)));
        Arrays.toString(arrayToSortLecture);

        // Bubble sort
        var bsStart = System.currentTimeMillis();
        var bsSorted = bubbleSort(arrayToSortLecture);
        var bsEnd = System.currentTimeMillis();
        printResult("Bubble sort", bsEnd - bsStart, bsSorted);

        // Selection sort
        var ssStart = System.currentTimeMillis();
        var ssSorted = bubbleSort(arrayToSortLecture);
        var ssEnd = System.currentTimeMillis();
        printResult("Selection sort", ssEnd - ssStart, ssSorted);

        // Insertion sort
        var isStart = System.currentTimeMillis();
        var isSorted = bubbleSort(arrayToSortLecture);
        var isEnd = System.currentTimeMillis();
        printResult("Insertion sort", ssEnd - ssStart, ssSorted);

        return;
    }

    public static void printResult(String algName, long timeElapsed, int[] sorted){
        System.out.println("""
                > %s:
                \t Time elapsed: %9dms\t Result: %s
                """.formatted(algName, timeElapsed, Arrays.toString(sorted)));
    }

    public static int[] fillArrayRand(int size, int randStart, int randEnd){
        int[] randArray = new int[size];
        for(var cur : randArray){
            cur = (int)( (randEnd - randStart) * Math.random() + randStart);
        }
        return randArray;
    }

    /**
     * Bubble sort algorithm sorting in ascending order.
     * Here: Move mininum from back to front
     * Alternative: Move maximum from front to back
     * Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)
     * @param array array to be sorted
     * @return sorted array
     */
    public static int[] bubbleSort(int[] array){

        // Loop over every position to fill correctly; start at 0
        for(int i = 0; i < array.length; i++){
            // start at back of array and compare last with the one before
            // then step by step compare till the smallest value is at the front
            for(int j = array.length - 2; j >= i; j--){
                if(array[j+1] < array[j]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }

        return array;
    }

    /**
     * Selection sort algorithm sorting in ascending order.
     * Here: Move minimum to front
     * Alternative: Move maximum to back.
     * Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)
     * @param array to be sorted
     * @return sorted array
     */
    public static int[] selectionSort(int[] array){
        // loop over every position so each position can be filled with appropriate value
        for(int i = 0; i < array.length; i++){
            // holds index of element in array with global/ local minimum
            int min = i;
            // loop over array and find minimum starting from i
            // in second iteration global min is already found and at index 0 -> start search for next minimum at index 1 and so on
            for(int j = i; i < array.length; j++){
                if(array[j] < array[min]) min = j; // save index of new minimum
            }
            // switch new found minimum and first not yet sorted position
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
        return array;
    }

    /**
     * Insertion sort algorithm sorting in ascending order.
     * Runtime:
     *  - Best Case: O(n) -> All elements are already in sorted order
     *  - Average Case: O(n^2) -> Simplification: values of array are evenly distributed (while loop will run approx. half as many times as in worst case but still because of gaussian summation formula O(n^2))
     *  - Worst Case: O(n^2)    -> All elements are sorted in descending order (while loop will run "to full extend" -> gaussian summation formula -> O(n^2))
     * @param array to be sorted
     * @return sorted array
     */
    public static int[] insertionSort(int[] array){

        // assume for element is sorted -> start at index 1
        for(int j = 1; j < array.length; j++){
            // save current value (to create a "gap")
            int key = array[j];
            // start left from current value
            int i = j - 1;

            // check all values left of current if they are bigger than the current value
            while (i >= 0 && array[i] > key){
                // current value is smaller or equal to element at the current position
                // move current position one to left (shift "gap")
                array[i+1] = array[i];
                // decrement i
                i--;
            }
            // right position for current value found (close "gap")
            array[i] = key;
        }

        return array;
    }


}
