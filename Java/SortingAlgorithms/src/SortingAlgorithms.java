import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class SortingAlgorithms {

    public static void main(String[] args) {
        int[] arrayToSortLecture = {34, 45, 12, 34, 23, 18, 38, 17, 43, 7};
        int[] test = new int[]{21, -6, -46, -14, 9, 43, -18, 9, 21, 20};
        int[] randArray = fillArrayRand(10, -50, 50);

        // working array
        int[] curArray = arrayToSortLecture;

        System.out.printf("""
                +-------------------------------------------+
                | Sorting Algorithms and their runtime      |
                +-------------------------------------------+
                Unsorted array: %s
                        
                """, Arrays.toString(curArray));

        // Bubble sort
        String algName = "Bubble sort";
        var bsCurArray = curArray.clone();
        printAlgStart(algName, bsCurArray);
        var bsStart = System.currentTimeMillis();
        var bsSorted = bubbleSort(bsCurArray, true);
        var bsEnd = System.currentTimeMillis();
        printResult(algName, bsEnd - bsStart, bsSorted);

        // Selection sort
        algName = "Selection sort";
        var ssCurArray = curArray.clone();
        printAlgStart(algName, ssCurArray);
        var ssStart = System.currentTimeMillis();
        var ssSorted = selectionSort(ssCurArray, true);
        var ssEnd = System.currentTimeMillis();
        printResult(algName, ssEnd - ssStart, ssSorted);

        // Insertion sort
        algName = "Insertion sort";
        var isCurArray = curArray.clone();
        printAlgStart(algName, isCurArray);
        var isStart = System.currentTimeMillis();
        var isSorted = insertionSort(isCurArray, true);
        var isEnd = System.currentTimeMillis();
        printResult(algName, ssEnd - ssStart, ssSorted);

        // Quick sort
        algName = "Quick sort";
        var qsCurArray = curArray.clone();
        printAlgStart(algName, qsCurArray);
        var qsStart = System.currentTimeMillis();
        var qsSorted = quickSort(qsCurArray, 0, qsCurArray.length - 1, true, PivotElement.LAST);
        var qsEnd = System.currentTimeMillis();
        printResult(algName, qsEnd - qsStart, qsSorted);

        // Ex-situ merge sort
        algName = "Merge sort (ex-situ)";
        var esmsCurArray = curArray.clone();
        printAlgStart(algName, esmsCurArray);
        var esmsStart = System.currentTimeMillis();
        var esmsSorted = mergeSortExSitu(esmsCurArray, 0, esmsCurArray.length - 1, true);
        var esmsEnd = System.currentTimeMillis();
        printResult(algName, esmsEnd - esmsStart, esmsSorted);


        // In-situ merge sort
        algName = "Merge sort (in-situ)";
        var ismsCurArray = curArray.clone();
        printAlgStart(algName, ismsCurArray);
        var ismsStart = System.currentTimeMillis();
        var ismsSorted = mergeSortInSitu(ismsCurArray, 0, ismsCurArray.length - 1);
        var ismsEnd = System.currentTimeMillis();
        printResult(algName, ismsEnd - ismsStart, ismsSorted);

        // Heap sort
        algName = "Heap sort";
        var hsCurArray = curArray.clone();
        printAlgStart(algName, hsCurArray);
        var hsStart = System.currentTimeMillis();
        var hsSorted = heapSort(hsCurArray, 0, ismsCurArray.length - 1, true);
        var hsEnd = System.currentTimeMillis();
        printResult(algName, hsEnd - hsStart, hsSorted);

        // Count sort
        algName = "Count sort";
        var csCurArray = curArray.clone();
        printAlgStart(algName, csCurArray);
        var csStart = System.currentTimeMillis();
        var csSorted = countSortWithoutMax(hsCurArray, true);
        var csEnd = System.currentTimeMillis();
        printResult(algName, csEnd - csStart, csSorted);

        // Map sort
        algName = "Map sort";
        var msCurArray = curArray.clone();
        printAlgStart(algName, msCurArray);
        var msStart = System.currentTimeMillis();
        var msSorted = mapSort(msCurArray, 1, true); // 1.25 is the optimal value (see proof in topic hashing)
        var msEnd = System.currentTimeMillis();
        printResult(algName, msEnd - msStart, msSorted);

        // Test binary search on sorted array
        var toBeFound = 17;
        System.out.printf("""
                +-------------------------------------------+
                | Binary Search in sorted array             |
                +-------------------------------------------+
                Sorted array: %s
                Value to be found: %d
                """, Arrays.toString(msSorted), toBeFound);

        var rekBinSearch = binarySearchRek(msSorted, toBeFound);
        System.out.printf("""
                > %s:
                \t Result: %d
                """, "Recursive Binary Search", rekBinSearch);
        var iterBinSearch = binarySearchIter(msSorted, toBeFound);
        System.out.printf("""
                > %s:
                \t Result: %d
                """, "Iterative Binary Search", iterBinSearch);

    }

    /**
     * Print header line before starting algorithm.
     * @param algName name of algorithm
     * @param unsortedArray array with unsorted values
     */
    public static void printAlgStart(String algName, int[] unsortedArray) {
        System.out.printf("""
                ---------------------------------------------------------
                 %s
                ---------------------------------------------------------
                Unsorted: %s
                """, algName, Arrays.toString(unsortedArray));
    }

    /**
     * Print result of an algorithm.
     * @param algName name of algorithm
     * @param timeElapsed time elapsed between start and end of algorithm
     * @param sorted array with sorted values
     */
    public static void printResult(String algName, long timeElapsed, int[] sorted) {
        System.out.println("""
                > %s:
                \t Time elapsed: %9dms\t Result: %s
                
                
                """.formatted(algName, timeElapsed, Arrays.toString(sorted)));
    }

    public static int[] fillArrayRand(int size, int randStart, int randEnd) {
        int[] randArray = new int[size];
        for (int i = 0; i < size; i++) {
            randArray[i] = (int) ((randEnd - randStart) * Math.random() + randStart);
        }
        return randArray;
    }

    /**
     * Bubble sort algorithm sorting in ascending order.
     * Here: Move mininum from back to front
     * Alternative: Move maximum from front to back
     * Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)
     *
     * @param array array to be sorted
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] bubbleSort(int[] array, boolean print) {
        // Loop over every position to fill correctly; start at 0
        for (int i = 0; i < array.length; i++) {
            // start at back of array and compare last with the one before
            // then step by step compare till the smallest value is at the front
            for (int j = array.length - 2; j >= i; j--) {
                if (array[j + 1] < array[j]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            if (print) System.out.println(Arrays.toString(array));
        }

        return array;
    }

    /**
     * Selection sort algorithm sorting in ascending order.
     * Here: Move minimum to front
     * Alternative: Move maximum to back.
     * Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)
     *
     * @param array to be sorted
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] selectionSort(int[] array, boolean print) {
        // loop over every position so each position can be filled with appropriate value
        for (int i = 0; i < array.length; i++) {
            // holds index of element in array with global/ local minimum
            int min = i;
            // loop over array and find minimum starting from i
            // in second iteration global min is already found and at index 0 -> start search for next minimum at index 1 and so on
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[min]) min = j; // save index of new minimum
            }
            // switch new found minimum and first not yet sorted position
            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
            if (print) System.out.println(Arrays.toString(array));

        }
        return array;
    }

    /**
     * Insertion sort algorithm sorting in ascending order.
     * Runtime:
     * - Best Case: O(n) -> All elements are already in sorted order
     * - Average Case: O(n^2) -> Simplification: values of array are evenly distributed (while loop will run approx. half as many times as in worst case but still because of gaussian summation formula O(n^2))
     * - Worst Case: O(n^2)    -> All elements are sorted in descending order (while loop will run "to full extend" -> gaussian summation formula -> O(n^2))
     *
     * @param array to be sorted
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] insertionSort(int[] array, boolean print) {
        if (array.length == 1) return array;
        // assume for element is sorted -> start at index 1
        for (int j = 1; j < array.length; j++) {
            // save current value (to create a "gap")
            int key = array[j];
            // start left from current value
            int i = j - 1;

            // check all values left of current if they are bigger than the current value
            while (i >= 0 && array[i] > key) {
                // current value is smaller or equal to element at the current position
                // move current position one to left (shift "gap")
                array[i + 1] = array[i];
                // decrement i
                i--;
            }
            // right position for current value found (close "gap")
            array[i + 1] = key;
            if (print) System.out.println(Arrays.toString(array));
        }

        return array;
    }

    /**
     * Quick sort algorithm for sorting in ascending order.
     * Runtime:
     * - Best Case: O(n log n) -> Pivot elements splits array in two equally sized subarrays every time
     * - Worst Case: O(N²) -> Elements already in sorted order (asc or desc): subarray only reduced by one each iteration => gaussian summation formula
     * - Average Case: O(n log n) -> Effort depends on choice of pivot. Assume choice of pivot is equally distributed between elements of array.
     *
     * @param array to be sorted
     * @param first index of first element
     * @param last  index of last element
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @param pivotChoice which pivot element is used
     * @return sorted array
     */
    public static int[] quickSort(int[] array, int first, int last, boolean print, PivotElement pivotChoice) {
        var allPivots = new HashMap<PivotElement, BiFunction<Integer, Integer, Integer>>();
        allPivots.put(PivotElement.FIRST, (firstIdx, lastIdx) -> firstIdx);
        allPivots.put(PivotElement.LAST, (firstIdx, lastIdx) -> lastIdx);
        allPivots.put(PivotElement.RANDOM, (firstIdx, lastIdx) -> new SecureRandom().nextInt(firstIdx, lastIdx));
        var pivotFunc = allPivots.get(pivotChoice);

        // Only sort if at least two elements are there to sort
        if (first < last) {
            // Split array in two halfs: [Values smaller than pivot elem., ..., Pivot elem, ... , Values higher than pivot elem.]
            int part = qsPreparePartition(array, first, last, print, pivotFunc);
            if(print) System.out.printf("Pivot element: %s at index %s%n", array[part], part);
            // sort all smaller elements
            quickSort(array, first, part - 1, print, pivotChoice);
            // sort all greater elements
            quickSort(array, part + 1, last, print, pivotChoice);
            // Note: Dont touch pivot as it is already at the spot it is supposed to be
        }
        return array;
    }

    /**
     * Split array logically in two halfs. Split element is so called pivot element.
     * Moves all values lower than pivot to left and all items greater than pivot to right.
     * Note: First element is chosen as pivot, which is the worst possible choice.
     *
     * @param array to be sorted
     * @param first index of first element
     * @param last  index of last element
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @param choosePivot function that returns pivot value given last and first
     * @return sorted array
     */
    public static int qsPreparePartition(int[] array, int first, int last, boolean print, BiFunction<Integer, Integer, Integer> choosePivot) {
        int part = first - 1; // Alternative: set part to first and swap order within if
        int pivotIdx = choosePivot.apply(first, last); // Choose pivot element as specified by the function (first, last or random)
        // swap pivot to first
        swap(array, pivotIdx, first);
        int pivot = array[first];
        for (int i = first; i <= last; i++) {
            if (array[i] <= pivot) {  // important <= ! otherwise
                // increment size of "smaller partition"
                part++;
                // Swap smaller value to left side of pivot
                swap(array, part, i);
            }
        }
        // Move pivot to spot it belongs
        // Pivot is first element in this implementation.
        swap(array, part, first);
        if (print) System.out.println(Arrays.toString(array));
        return part;
    }

    /**
     * Swap values of two indices in an array.
     * In place swap, so no new array is created.
     *
     * @param array array which contains both values
     * @param i     index to be swapped
     * @param j     index to be swapped
     * @return array with swapped indices
     */
    public static int[] swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return array;
    }

    /**
     * Ex-situ Merge sort algorithm for sorting in ascending order.
     * Runtime: Average Case = Best Case = Worst Case = O(n * log n): log n recursive calls (dividing array into two arrays every time) and effort n in merge
     *
     * @param array to be sorted
     * @param first index of first element
     * @param last  index of last element
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] mergeSortExSitu(int[] array, int first, int last, boolean print) {
        // Check if there is more than one element
        if (first < last) {
            int middle = first + ((last - first + 1) / 2);
            // Sort each half of the array (if array.length mod 2 != 0 -> make left half smaller)
            mergeSortExSitu(array, first, middle - 1, print);
            mergeSortExSitu(array, middle, last, print);
            // At this stage both array halfs are sorted properly, now merge them together
            mergeExSitu(array, first, last, middle, print);
        }
        return array;
    }

    /**
     * Merges array with two already order halfs into one array that is completely ordered ex-situ.
     *
     * @param array  array with two already sorted halfs
     * @param first  index of first element
     * @param last   index of last element
     * @param middle middle of the array
     * @param print  prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] mergeExSitu(int[] array, int first, int last, int middle, boolean print) {
        int startLeft = first, endLeft = middle - 1, startRight = middle, endRight = last;
        int intervalLength = last - first + 1;
        // array to insert values in sorted order (this is why it is ex-situ = required extra memory)
        int[] insertArray = new int[intervalLength];

        // Fill each positon of the array with the appropiate value from either the right side or the left side
        for (int i = 0; i < intervalLength; i++) {
            if (startLeft <= endLeft) {
                if (startRight <= endRight) {
                    // compare left side and right side
                    if (array[startLeft] <= array[startRight]) {
                        // Left is smaller
                        // Insert into insert array and increase the starting index for left half
                        // as first value of left half is inserted correctly
                        insertArray[i] = array[startLeft];
                        startLeft++;
                    } else {
                        // Right is smaller
                        // Insert into insert array and increase the starting index for right half
                        // as first value of right half is inserted correctly
                        insertArray[i] = array[startRight];
                        startRight++;
                    }
                } else {
                    // all values in right half have already been inserted at their correct position
                    // Fill in all the values from left half one by one
                    // (fill in and increase left start index; for loop will take care of filling all values)
                    insertArray[i] = array[startLeft];
                    startLeft++;
                }
            } else {
                // all values in left half have already been inserted at their correct position
                // Fill in all the values from right half one by one (fill in and increase left start index)
                insertArray[i] = array[startRight];
                startRight++;
            }
        }
        ;

        // Copy values back into original array in correct order
        for (int i = 0; i < intervalLength; i++) {
            array[first + i] = insertArray[i];
        }
        if (print) System.out.println(Arrays.toString(array));

        return array;
    }

    /**
     * In-situ Merge sort algorithm for sorting in ascending order.
     * Runtime:
     * - Best Case: O(n log n): All elements are already in sorted order
     * - Average Case: ?
     * - Worst Case: O(n²)
     *
     * @param array to be sorted
     * @param first index of first element
     * @param last  index of last element
     * @return sorted array
     */
    public static int[] mergeSortInSitu(int[] array, int first, int last) {
        // Check if there is more than one element
        if (first < last) {
            int middle = (first + last) >> 1;
            // Sort each half of the array (if array.length mod 2 != 0 -> make left half smaller)
            mergeSortInSitu(array, first, middle);
            mergeSortInSitu(array, middle + 1, last);
            // At this stage both array halfs are sorted properly, now merge them together
            mergeInSitu(array, first, last, middle);
        }
        return array;
    }

    /**
     * Merges array with two already order halfs into one array that is completely ordered in-situ.
     *
     * @param array  array with two already sorted halfs
     * @param first  index of first element
     * @param last   index of last element
     * @param middle middle of the array
     * @return sorted array
     */
    public static int[] mergeInSitu(int[] array, int first, int last, int middle) {
        int startLeft = first, endLeft = middle, startRight = middle + 1, endRight = last;
        int intervalLength = last - first + 1;

        // Fill each positon of the array with the appropriate value from either the right side or the left side
        for (int i = 0; i < intervalLength; i++) {
            // position in array that has to be filled
            int currentPosition = first + i;
            if (startLeft <= endLeft) {
                if (startRight <= endRight) {
                    // compare left side and right side
                    if (array[startLeft] <= array[startRight]) {
                        // Left is smaller
                        // Insert into original array with respect to the offset given by first
                        array[currentPosition] = array[startLeft];
                        // increment left as a value of the left half is now at the correct position
                        startLeft++;
                    } else {
                        // Right is smaller
                        // array[startRight] needs to be at current position array[first + i]
                        int toBeInserted = array[currentPosition];
                        array[currentPosition] = array[startRight];
                        // similar to insertion sort move the gap from left to right and insert at the correct position
                        // there now is a "gap" at array[startRight]
                        // start one value next to start right as we toBeInserted is bigger than start right (otherwise we wouldnt be in this else branch)
                        int j = startRight + 1;
                        // find position where toBeInserted has to go
                        // shift value to left as long as it is smaller or equal to toBeInserted and j is not out of bounds
                        while (j <= endRight && array[j] <= toBeInserted) {
                            array[j - 1] = array[j];
                            j++;
                        }
                        // correct position where array[j-2] < toBeInserted < array[j]
                        array[j - 1] = toBeInserted;
                        // one more value from left is sorted in correct place
                        startLeft++;
                    }
                } else {
                    // all values in right half have already been inserted at their correct position
                    // Fill in all the values from left half one by one
                    // (fill in and increase left start index; for loop will take care of filling all values)
                    array[currentPosition] = array[startLeft];
                    startLeft++;
                }
            } else {
                // all values in left half have already been inserted at their correct position
                // Fill in all the values from right half one by one (fill in and increase left start index)
                array[currentPosition] = array[startRight];
                startRight++;
            }
        }

        return array;
    }

    /**
     * Heap sort algortihm for sorting in ascending order.
     * Runtime: Average Case = Best Case = Worst Case = O(n * log n)
     *
     * @param array array to be sorted
     * @param first first element in array
     * @param last  last element in array
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] heapSort(int[] array, int first, int last, boolean print) {
        // first build the heap
        buildHeap(array, first, last);
        if(print) {
            System.out.println(Arrays.toString(array));
            printHeap(array);
        }

        // At this stage and in every iteration the array is a correct heap
        // --> max. element is at root = array[0]
        for (int i = last; i > first; i--) {
            // swap first element (biggest) with last one currently to sort in ascending order
            // each time one element will be inserted at the correct position
            // --> last element is one smaller each iteration
            swap(array, first, i);
            // top element now needs to go to the correct position within the heap
            // last element of heap is now biggest element and in the correct position so it can be ignored
            heapify(array, first, i - 1, first);
            if(print) {
                System.out.println(Arrays.toString(array));
                printHeap(array);
            }
        }
        return array;
    }

    /**
     * Calculate logarithm of n to base 2.
     * @param n
     * @return logarithm or NaN if logarithm is not defined for n
     */
    public static double log2(int n){
        return Math.log10(n) / Math.log10(2);
    }

    /**
     * Print heap by layers.
     * @param array heap (in array form) to be printed.
     */
    public static void printHeap(int[] array){
        int heapHeight = (int) log2(array.length);
        int noNodesLastLayer = (int) (Math.pow(2, heapHeight));
        int middle = ((noNodesLastLayer + noNodesLastLayer - 1) >> 1);

        int startNode = 0;
        int nodesInPrevLayers = 0;
        for (int i = 0; i <= heapHeight; i++) {
            var noNodesInLayer = (int) Math.pow(2, i);
            System.out.printf("Layer %d:      ", i);
            int end = nodesInPrevLayers + noNodesInLayer - 1;
            printNodesInLayer(array, startNode, end, middle);
            startNode = noNodesInLayer + nodesInPrevLayers;
            middle = middle >> 1;
            nodesInPrevLayers += noNodesInLayer;
        }
    }

    /**
     * Print one layer within a heap.
     * @param array array
     * @param start start index of layer (array index)
     * @param end end index of layer (array index)
     * @param offset spaces before the values of a layer
     */
    public static void printNodesInLayer(int[] array, int start, int end, int offset){
        System.out.print(" ".repeat(offset));
        for (int i = start; i <= end; i++) {
            if(i < array.length) System.out.printf("%s ", array[i]);
        }
        System.out.println();
    }

    /**
     * Build a left-complete heap from an array
     *
     * @param array array (binary tree) which needs to be transformed to a heap
     * @param first first element in interval
     * @param last  last element in interval
     * @return array to be turned into a heap
     */
    public static int[] buildHeap(int[] array, int first, int last) {
        // Alternativ way used number of elements instead of last
        // Length of interval to work on
        //int intervalLength = last - first + 1;

        // start at predessor of last node to build the heap as leafs are heaps already
        int predessorLast = first + (last - 1) >> 1;
        for (int i = predessorLast; i >= first; i--) {
            heapify(array, first, last, i);
        }
        return array;
    }

    /**
     * Make sure Heap is built correctly at root node. If not adjust structure so heap is built correctly.
     *
     * @param array array (binary tree) which needs to be checked to conform heap conditions
     * @param first first element in array
     * @param last  last element in array
     * @param root  root element that needs to be heapified
     * @return heapified array
     */
    public static int[] heapify(int[] array, int first, int last, int root) {
        int successorLeft = first + (root << 1) + 1;    // index of successor left
        int successorRight = first + (root << 1) + 2;   // index of successor right
        int largest = root;    // index of largest element

        // Find largest element of the 3 (root, successorLeft, successorRight)
        // check if still in bounds (as it's a left-complete tree only required for right end of tree)
        if (successorRight <= last && array[successorRight] > array[largest]) largest = successorRight;
        if (successorLeft <= last && array[successorLeft] > array[largest]) largest = successorLeft;

        // only swap largest and root if root is not the largest already
        if (largest != root) {
            swap(array, largest, root);
            // correct heap if necessary (root is now at position of largest and might need to move down)
            heapify(array, first, last, largest);
        }

        return array;
    }

    /**
     * Count sort algorithm to sort array in ascending order.
     * Runtime:
     * - Best Case: O(n) or O(2k + 4n) when k = max - min + 1
     * - Worst Case: there's no upper limit on how bad the runtime can get. Linear time is only guaranteed when k is limited to k = O(n).
     * <p>
     * Note: Max. and min. value could easily be computed in O(n) time, adding one constant factor of n to runtime (at least in java).
     * In C++ or C one can calculate the values for max. and min. when initializing the array to zero.
     *
     * @param array array to be sorted
     * @param max   max. value in sorted arrray
     * @param min   min. value in sorted array
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] countSort(int[] array, int max, int min, boolean print) {
        // one could use this to make count sort work for any values and not needing to provide max and min
        // Adds a constant factor of n to runtime (still O(n))
        /*
        int maxixum = Integer.MIN_VALUE, minimum = Integer.MAX_VALUE;
        for(int i = 0; i < array.length; i++){
            if(array[i] > maxixum) maxixum = array[i];
            if(array[i] < minimum) minimum = array[i];
        }
         */


        // calculate number of elements required
        int[] bin = new int[max - min + 1]; // effort max - min + 1 for initialization
        int j = 0;

        // count how many times each value
        for (int i = 0; i < array.length; i++) bin[array[i] - min]++;    // effort n (could also be counted as 3n)
        if(print) System.out.printf("-Count Array: %s\n", Arrays.toString(bin));
        for (int i = 0; i < array.length; i++) { // effort n
            // skip value that do not occur
            while (bin[j] == 0)
                j++; // effort worst case = max - min + 1 for setting to next bin, effort n for checking for 0
            // write value one time
            array[i] = j + min;  // effort n
            // decrement amount of this value
            bin[j]--;   // effort n
        }

        return array;
    }

    /**
     * Improved count sort algorithm to sort array in ascending order. Add up TODO
     * Runtime:
     * - Best Case: O(n) or O(2k + 4n) when k = max - min + 1
     * - Worst Case: there's no upper limit on how bad the runtime can get. Linear time is only guaranteed when k is limited to k = O(n).
     * <p>
     * Note: Max. and min. value could easily be computed in O(n) time, adding one constant factor of n to runtime (at least in java).
     * In C++ or C one can calculate the values for max. and min. when initializing the array to zero.
     *
     * @param array array to be sorted
     * @param max   max. value in sorted arrray
     * @param min   min. value in sorted array
     * @param print prints partial results to screen if true (for comparison algorithm done by hand)
     * @return sorted array
     */
    public static int[] countSortImproved(int[] array, int max, int min, boolean print) {
        int k = max - min + 1;
        int[] countArr = new int[k];

        for (int i = 0; i < array.length; i++) countArr[array[i] - min]++;
        if(print) System.out.printf("              Count Array: %s%n", Arrays.toString(countArr));
        for (int i = 1; i < countArr.length; i++) countArr[i] += countArr[i - 1];
        if(print) System.out.printf(" Count Array (aggregated): %s%n%n", Arrays.toString(countArr));

        // new array, otherwise values in array[] would be overwritten, but they are still required
        int[] output = new int[array.length];
        // Iterate from back, otherwise count sort is NOT stable,
        // e.g. count[] = [0, 2(a), 2(b), 3]
        // from front: 2(a) will place the spot of array[1] will be index 2; then count is decreased so 2(b) will be placed a spot 1 = before 2(a) -> NOT STABLE
        // from back: 2(b) wil be placed at spot 2; count of 2s will be decreased and 2(a) wil be placed one spot before 2(b) -> STABLE
        for (int i = array.length - 1; i >= 0; i--) {
            int lookupVal = array[i] - min;
            output[countArr[lookupVal] - 1] = array[i];
            countArr[lookupVal]--;
        }

        for (int i = 0; i < array.length; i++) array[i] = output[i];
        return output;
    }

    /**
     * Helper method to conveniently determine max. and min. value of array to keep count sort implemented as in lecture.
     * Calls count sort.
     *
     * @param array array to be sorted
     * @return sorted array
     */
    public static int[] countSortWithoutMax(int[] array, boolean print) {
        int maxixum = Integer.MIN_VALUE, minimum = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maxixum) maxixum = array[i];
            if (array[i] < minimum) minimum = array[i];
        }
        return countSortImproved(array, maxixum, minimum, print);
    }

    /**
     * Map sort algorithm for sorting in ascending order.
     * Runtime:
     * - Best Case: O(n) all values are mapped to an empty field in the bin array; no conflicts;
     * - Worst Case: O(n²) all values are the same and map to 0 index is new array -> a conflict occurs every single time
     * -> as they are all <= every time all values need to be shifted to the right to accomodate one more value
     * -> shifted by 1, 2, 3, ... , n-1 (gaussian summation formula)
     * -> same priciple if values are mapped to last element of array
     * Note: Using -1 as indicator wheter a value is filled or not is not a very good choice as -1 might be a real valid value. Better use a class with a value and a control flag wheter value exits or not.
     * Here we assume all valid values in the array are not equal to -1.
     * Note 2: Values for c
     * - Smaller than 1 will result in an infinity loop as less "places" in bin array are available then needed to store all values, so better check for c < 1
     * - Optimal value for c is 1.25 (proof later in topic hashing)
     *
     * @param array array to be sorted
     * @param c     growth factor for array
     * @return sorted array
     */
    public static int[] mapSort(int[] array, double c, boolean print) {
        int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        var notExisting = -1;

        // calculate min. and max. value
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) min = array[i];
            if (array[i] > max) max = array[i];
        }

        // calculate distance between values
        // this will be divided from
        double d = (double) (max - min) / (c * array.length - 1);
        // create new array for values to be copied to
        int[] bin = new int[(int) ((double) array.length * c)];
        if(print) {
            System.out.printf("""
                    Min: %s
                    D:   %s
                    
                    +----------------+--------------+-------------+--------
                    | Value array[i] | Mapped index | Final index | Array
                    +----------------+--------------+-------------+--------
                    """, min, d);
        }

        // Initialize newly created array with value to indicate this is not a valid value
        for (int i = 0; i < bin.length; i++) bin[i] = notExisting;
        // Note: Java specific alternative
        //Arrays.fill(bin, notExisting);

        for (int i = 0; i < array.length; i++) {
            // index of element i
            // e.g. if values have avg. distance of 2 they have to be mapped on indexes half of that
            int index = (int) ((double) (array[i] - min) / d);
            System.out.printf("| %14s | %12s | ", array[i], index);
            int toBeInserted = array[i];
            boolean goToLeft = false;

            // Check where the value needs to be inserted; can be inserted spot on or on the left or right hand side?
            if (bin[index] != notExisting && toBeInserted <= bin[index]) goToLeft = true;
            while (bin[index] != notExisting) {
                if (goToLeft) {
                    // value is smaller or equal --> needs to go to the left
                    // When switching from left to right swap bigger values to right and keep them
                    if (toBeInserted > bin[index]) {
                        // Set larger value and move on to insert the smaller or equal value on the left hand side
                        int temp = toBeInserted;
                        toBeInserted = bin[index];
                        bin[index] = temp;
                    }
                    // as long as index is not out of bounds keep going left, when out of bounds move to right
                    if (index > 0) index--;
                    else goToLeft = false;
                } else {
                    // value is bigger --> needs to go to the right
                    if (toBeInserted <= bin[index]) {
                        // Set smaller value and move on to insert the bigger one further right hand side
                        int temp = toBeInserted;
                        toBeInserted = bin[index];
                        bin[index] = temp;
                    }
                    // as long as index is not out of bounds keep going right, when out of bounds move to left
                    if (index < bin.length - 1) index++;
                    else goToLeft = true;
                }
            }
            // do actual insert operation
            bin[index] = toBeInserted;
            if(print) System.out.printf("%11s | %s %n", index, Arrays.toString(bin));
        }

        // copy back into original array
        for (int i = 0; i < bin.length; i++) {
            if (bin[i] != notExisting) array[i] = bin[i];
        }
        if(print) System.out.println();

        return array;
    }

    /**
     * Binary serach for an element in a !!!sorted!!! array. Recursive implementation.
     * Returns index of element in array if present or -1 otherwise.
     *
     * @param array     sorted array which should be searched
     * @param toBeFound value to be searched for
     * @return index of element in array if present or -1 otherwise
     */
    public static int binarySearchRek(int[] array, int toBeFound) {
        return binarySearchRek(array, 0, array.length - 1, toBeFound);
    }

    /**
     * Binary serach for an element in a <em>sorted</em> array. Recursive implementation.
     * Returns index of element in array if present or -1 otherwise.
     *
     * @param array     sorted array which should be searched
     * @param first     start index for search
     * @param last      end index for search
     * @param toBeFound value to be searched for
     * @return index of element in array if present or -1 otherwise
     */
    private static int binarySearchRek(int[] array, int first, int last, int toBeFound) {
        if (last >= first) {
            int middle = first + ((last - first) >> 1);
            if (array[middle] == toBeFound)
                return middle;
            if (toBeFound < array[middle])
                return binarySearchRek(array, first, middle - 1, toBeFound);
            return binarySearchRek(array, middle + 1, last, toBeFound);
        }
        return -1;
    }

    /**
     * Binary serach for an element in a !!!sorted!!! array. Iterative implementation.
     * Returns index of element in array if present or -1 otherwise.
     *
     * @param array     sorted array which should be searched
     * @param toBeFound value to be searched for
     * @return index of element in array if present or -1 otherwise
     */
    public static int binarySearchIter(int[] array, int toBeFound) {
        int last = array.length - 1;
        int first = 0;
        while (last >= first) {
            int middle = first + ((last - first) >> 1);
            if (array[middle] == toBeFound)
                return middle;
            if (toBeFound < array[middle]) {
                last = middle - 1;
            } else {
                first = middle + 1;
            }
        }
        return -1;
    }


}
