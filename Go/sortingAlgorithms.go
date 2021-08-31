package main

import (
	"fmt"
	"time"
)

func main() {
	fmt.Printf("Sorting Algorithms\n------------------\nImplemenation of different sorting algorithms all sorting the same orignial unsorted array. \nEach implementation will be timed to give an idea of how fast each algorithm is/ can be.\n")
	unsortedArray := []int{4, -34, 23, 50, 0, -7}
	fmt.Printf("\nUnsorted slice: %v\n\n", unsortedArray)

	// Bubble sort using minimum
	bsStart := time.Now()
	bsArr := bubbleSortMin(unsortedArray)
	fmt.Printf("Bubble sort (Min.): \n\t> Input: %v\n\t> Result: %v\n\t> Time: %v\n", unsortedArray, bsArr, time.Since(bsStart))

	// Selection sort using minimum
	ssStart := time.Now()
	ssArr := selectionSort(unsortedArray)
	fmt.Printf("Selection sort (Min.): \n\t> Input: %v\n\t> Result: %v\n\t> Time: %v\n", unsortedArray, ssArr, time.Since(ssStart))

	// Merge sort (ex-situ variant)
	msesStart := time.Now()
	msesArr := mergeSortExSitu(unsortedArray)
	fmt.Printf("Merge sort (ex-situ): \n\t> Input: %v\n\t> Result: %v\n\t> Time: %v\n", unsortedArray, msesArr, time.Since(msesStart))
}

func swap(a, b *int) {
	temp := *a
	*a = *b
	*b = temp
}

func bubbleSortMin(unsorted []int) []int {
	// Copy slice to leave original array untouched
	sorted := make([]int, len(unsorted))
	copy(sorted, unsorted)

	// Sort
	for i := 0; i < len(sorted); i++ {
		for j := len(unsorted) - 2; j >= i; j-- {
			if sorted[j] > sorted[j+1] {
				swap(&sorted[j], &sorted[j+1])
			}
		}
	}
	return sorted
}

// Selection sort algorithms selecting the minimum each iteration.
// Runtime complexity: BC = WC = AC = O(n²)
func selectionSort(unsorted []int) []int {
	// Copy slice to leave original array untouched
	sorted := make([]int, len(unsorted))
	copy(sorted, unsorted)

	// Sort
	for i := 0; i < len(sorted); i++ {
		min := i
		for j := i; j < len(sorted); j++ {
			if sorted[j] < sorted[min] {
				min = j
			}
		}
		swap(&sorted[i], &sorted[min])
	}
	return sorted
}

// Merge sort algorithm. This is the typical ex-situ implementation which has ideal runtime complexity.
// Runtime complexity: O(n*log(n))
func mergeSortExSitu(unsorted []int) []int {
	// Copy slice to leave original array untouched
	sorted := make([]int, len(unsorted))
	copy(sorted, unsorted)
	return mergeSortExSituInternal(sorted, 0, len(sorted)-1)
}

// INTERNAL: function called recursively to divide array into two halfs and merge those two halfs back together as required for merge sort.
func mergeSortExSituInternal(unsorted []int, first, last int) []int {
	if first < last {
		middle := first + ((last - first) >> 1)
		// sort left half
		mergeSortExSituInternal(unsorted, first, middle)
		// sort right half
		mergeSortExSituInternal(unsorted, middle+1, last)
		// merge
		mergeExSitu(unsorted, first, middle, last)
	}
	return unsorted
}

// Merges values of left and right half of slice which are given by [first:middle+1] [middle+1:last+1]
// This is an ex-situ implementation which gurantees a runtime complexity of O(n) which is not possible with in-situ version
// Memory complexity: O(n)
// Runtime complexity: O(n)
func mergeExSitu(unsorted []int, first, middle, last int) []int {
	// length of current interval
	invalLen := last - first + 1
	// Ex-situ algorithms -> requires additional storage O(n) to run in O(n*log(n)) in BC/ AC/ WC
	sorted := make([]int, invalLen)
	// Define indices pointing to current element in left and right half of array
	left, right := first, middle+1

	// Merge values together in slice "sorted"
	for i := 0; i < invalLen; i++ {
		if left <= middle {
			if right <= last {
				if unsorted[left] <= unsorted[right] {
					sorted[i] = unsorted[left]
					left++
				} else {
					sorted[i] = unsorted[right]
					right++
				}
			} else {
				sorted[i] = unsorted[left]
				left++
			}
		} else {
			sorted[i] = unsorted[right]
			right++
		}
	}
	// copy values back to original array
	for i := 0; i < invalLen; i++ {
		unsorted[first+i] = sorted[i]
	}

	return unsorted
}
