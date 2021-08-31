import java.util.Arrays;

class MyPriorityQueue {

    private static final int INITIAL_SIZE = 11;

    private Element[] queue;
    private int size;

    public MyPriorityQueue(){
        this.queue = new Element[INITIAL_SIZE];
        this.size = 0;
    }

    /**
     * Make sure Heap is built correctly at root node. If not adjust structure so heap is built correctly.
     * @param first first element in array
     * @param last last element in array
     * @param root root element that needs to be heapified
     * @return heapified array
     */
    public void heapify(int first, int last, int root){
        int successorLeft = first + (root << 1) + 1;    // index of successor left
        int successorRight = first + (root << 1) + 2;   // index of successor right
        int smallest = root;    // index of largest element

        // Find largest element of the 3 (root, successorLeft, successorRight)
        // check if still in bounds (as it's a left-complete tree only required for right end of tree)
        if(successorRight <= last && queue[successorRight].key < queue[smallest].key) smallest = successorRight;
        if(successorLeft <= last && queue[successorLeft].key < queue[smallest].key) smallest = successorLeft;

        // only swap largest and root if root is not the largest already
        if(smallest != root){
            swap(queue, smallest, root);
            // correct heap if necessary (root is now at position of largest and might need to move down)
            heapify(first, last, smallest);
        }
    }

    /**
     * Insert a key into priority queue
     * @param key key to be inserted
     */
    public void insert(int key, Object data){
        // increase size of array if necessary (* 2 probably not the best value but will do here)
        if(size + 1 > this.queue.length){
            this.queue = Arrays.copyOf(queue, size * 2);
        }

        // insert element at the end
        this.queue[size] = new Element(key, data);
        // increase size
        size++;

        // recreate heap
        int predessorLast = (size - 1) >> 1;
        for(int i = predessorLast; i >= 0; i--){
            heapify(0, size - 1, i);
        }
    }

    /**
     * Get minimum value in priority queue.
     * @return min. value
     */
    public Element getMin(){
        if(size == 0) throw new RuntimeException("No element in queue");
        // top element is smallest element
        return this.queue[0];
    }

    /**
     * Get minimum value in priority queue and delete it.
     * @return min. value
     */
    public Element extractMin(){
        if(size == 0) throw new RuntimeException("No element in queue");
        var first = queue[0];
        // swap last element with first
        swap(this.queue, 0, size - 1);
        // delete last element
        queue[size - 1] = null;
        size--;
        // recreate heap
        int predecessor = (size - 1) >> 1;
        for(int i = predecessor; i >= 0; i--){
            heapify(0, size - 1, i);
        }
        return first;
    }

    /**
     * Decrease key
     * @param x
     * @param newKey
     */
    public void decreaseKey(int x, int newKey){
        if(x < 0 || x >= size) throw new RuntimeException("Position x not available in queue");
        var oldKey= this.queue[x].key;
        if(newKey >= oldKey) throw new RuntimeException("Key must be smaller than previous key");
        this.queue[x].key = newKey;

        // recreate heap
        int predecessor = (x - 1) >> 1;
        for(int i = predecessor; i >= 0; i--){
            heapify(0, size - 1, i);
        }
    }

    public void print(){
        System.out.println(Arrays.toString(queue));
    }

    private static Element[] swap (Element[] array, int i, int j){
        var temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return array;
    }

}
