package graphs;

import graphs.priority_queue.Prioritisable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class PrimPriorityQueue<T extends Prioritisable> {

    public static final int NOT_EXISTING = -1;
    /**
     * Holds index values for keys to be able to check if value is in Heap/ or return index of a value in O(1).
     */
    private HashMap<T, Integer> elem2idx;
    /**
     * Min-Heap to always get the element with the lowest priority.
     */
    private ArrayList<T> heap;

    public PrimPriorityQueue(Collection<T> c) {
        this.elem2idx = new HashMap<>();
        this.heap = new ArrayList<>(c.size());

        int idx = 0;
        for (var elem : c) {
            // only use unique keys!
            if (!elem2idx.containsKey(elem)) {
                heap.add(idx, elem);
                elem2idx.put(elem, idx);
                idx++;
            }
        }
        if (c.size() > 1) buildMinHeap(0, c.size() - 1);
    }

    public PrimPriorityQueue() {
        this(new ArrayList<>());
    }

    public int indexOf(T key) {
        return elem2idx.getOrDefault(key, PrimPriorityQueue.NOT_EXISTING);
    }

    public boolean contains(T key) {
        return elem2idx.containsKey(key);
    }

    public void setPriority(int idx, T prio) {
        heap.get(idx).setPriority(prio.getPriority());

    }

    private void buildMinHeap(int first, int last) {
        int length = last - first + 1;
        int start = first + (length - 2) >> 1;
        for (int i = start; i >= first; i--) heapifyMinHeap(first, last, i);
    }

    private void heapifyMinHeap(int first, int last, int root) {
        int left = first + ((root - first) << 1) + 1;
        int right = first + ((root - first) << 1) + 2;
        // Assume root is smallest
        int smallest = root;

        // change smallest to left if left is smaller than root
        if (left <= last && heap.get(left).getPriority() < heap.get(root).getPriority()) smallest = left;
        // compare root to whatever smallest is (root node or left successor)
        if (right <= last && heap.get(right).getPriority() < heap.get(smallest).getPriority()) smallest = right;
        // a swap and heap correction only needs to be done when the root is changed (i. e. a successor is smaller)
        if (smallest != root) {
            swap(heap, smallest, root);
            // update elements index, so
            elem2idx.put(heap.get(smallest), smallest);
            // now correct the heap (with the successor that had the smallest value as new root value) as the root has changed!
            heapifyMinHeap(first, last, smallest);
        }
    }

//    Just as a test ;)
//    private void heapSort(T[] array){
//        // build a min-heap from given array
//        buildMinHeap(array, 0, array.length - 1);
//        for (int i = 1; i < array.length - 1; i++) heapifyMinHeap(array, i, array.length - 1, i);
//    }

    private void swap(List<T> array, int posX, int posY) {
        var temp = array.get(posX);
        array.set(posX, array.get(posY));
        array.set(posY, temp);
    }
}
