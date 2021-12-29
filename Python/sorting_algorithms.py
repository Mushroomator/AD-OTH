import random
from timeit import default_timer


def init():
    arrayToSortLecture = [34, 45, 12, 34, 23, 18, 38, 17, 43, 7]
    randArray = fill_array_rand(10, -100, 100)
    test_arr = [21, -6, -46, -14, 9, 43, -18, 9, 21, 20]
    arrayToSort = randArray

    print(f"""
    +-------------------------------------------+
    | Sorting Algorithms and their runtime      |
    +-------------------------------------------+
    Unsorted array: {arrayToSort}
                        
    """)

    # Insertion sort
    is_arr = arrayToSort.copy()
    is_start = default_timer()
    insertion_sort(is_arr)
    is_end = default_timer()
    print_result("Insertion sort", is_end - is_start, is_arr)

    # Selection sort
    ss_arr = arrayToSort.copy()
    ss_start = default_timer()
    selection_sort(ss_arr)
    ss_end = default_timer()
    print_result("Selection sort", ss_end - ss_start, ss_arr)

    # Bubble sort
    bs_arr = arrayToSort.copy()
    bs_start = default_timer()
    bubble_sort(bs_arr)
    bs_end = default_timer()
    print_result("Bubble sort", bs_end - bs_start, bs_arr)

    # Quick sort
    qs_arr = arrayToSort.copy()
    qs_start = default_timer()
    quick_sort(qs_arr, 0, len(qs_arr) - 1)
    qs_end = default_timer()
    print_result("Quick sort", qs_end - qs_start, qs_arr)


def fill_array_rand(size, randStart, randEnd):
    """
    Create an array with certain size filled with random values within a specified range.

    :param size: size of array
    :type size: int
    :param randStart: start of random value range
    :type randStart: int
    :param randEnd: end of random value range
    :type randEnd: int
    :returns: array of size with values between randStart and randEnd
    """
    return [random.randint(randStart, randEnd) for _ in range(size)]


def swap(array, idx_x, idx_y):
    """
    Swap two values within an array. Mutates array!

    :param array: array
    :type array: list[any]
    :param idx_x: index of a value
    :type idx_x: int
    :param idx_y: index of another value
    :type idx_y: int
    :return: array with swapped elements
    """
    temp = array[idx_x]
    array[idx_x] = array[idx_y]
    array[idx_y] = temp
    return array


def print_result(alg_name, time_elapsed, sorted_arr):
    """
    Print result of a sorting algorithm.

    :param alg_name: algorithm name
    :param time_elapsed: time elapsed in seconds (timeit.default_timer())
    :param sorted_arr: sorted array
    :return:
    """
    time_elapsed_ms = time_elapsed * 1000
    print(f"""
    > {alg_name}:
    \t Time elapsed: {time_elapsed_ms:.2f}ms\t Result: {sorted_arr}
    """)


def insertion_sort(array):
    """
    Insertion sort algorithm sorting in ascending order.
    Runtime:
    - Best Case: O(n) -> All elements are already in sorted order
    - Average Case: O(n^2) -> Simplification: values of array are evenly distributed (while loop will run approx. half as many times as in worst case but still because of gaussian summation formula O(n^2))
    - Worst Case: O(n^2)    -> All elements are sorted in descending order (while loop will run "to full extend" -> gaussian summation formula -> O(n^2))

    :param array: array to be sorted
    :type array: list[int]
    :return: sorted array
    """
    step_counter = 0
    swap_counter = 0
    if len(array) == 1:
        return array

    for j in range(1, len(array)):
        key = array[j]
        i = j - 1
        step_counter += 2
        while i >= 0 and array[i] > key:
            array[i + 1] = array[i]
            swap_counter += 1
            i -= 1
            step_counter += 4 # two comparisms and two assignments
        array[i + 1] = key
        step_counter += 3 # two comparisms and assignment
    print(f"Steps: {step_counter}, Swaps: {swap_counter}")
    return array


def selection_sort(array):
    """
    Selection sort algorithm sorting in ascending order.
    Here: Move minimum to front
    Alternative: Move maximum to back.
    Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)

    :param array: array to be sorted
    :type array: list[int]
    :return: sorted array
    """

    for j in range(len(array)):
        min_idx = j
        for i in range(j, len(array)):
            if array[i] < array[min_idx]:
                min_idx = i
        swap(array, j, min_idx)
    return array


def bubble_sort(array):
    """
    Bubble sort algorithm sorting in ascending order.
    Here: Move mininum from back to front
    Alternative: Move maximum from front to back
    Runtime: Average Case = Best Case = Worst Case = O(n^2) (gaussian summation formula!)

    :param array: array to be sorted
    :type array: list[int]
    :return: sorted array
    """
    for j in range(len(array)):
        for i in range(len(array) - 2, j-1, -1):
            if array[i] > array[i+1]:
                swap(array, i, i+1)
    return array


def quick_sort(array, first, last):
    if first < last:
        pivot_elem = prepare_partition(array, first, last)
        print(array)
        print("Pivot:", pivot_elem, array[pivot_elem])
        quick_sort(array, first, pivot_elem - 1)
        quick_sort(array, pivot_elem + 1, last)
    return array


def prepare_partition(array, first, last):
    # Choose random pivot element and move it to front of array
    pivot_idx = first # random.randint(first, last)
    swap(array, pivot_idx, first)
    part = first - 1
    pivot = array[first]

    for i in range(first, last+1):
        if array[i] <= pivot:
            part += 1
            swap(array, part, i)

    # Swap pivot back to where it belongs
    swap(array, first, part)

    # return index of pivot element (left from it --> everything is lower, right from it -> everything is greater)
    return part

if __name__ == '__main__':
    init()
