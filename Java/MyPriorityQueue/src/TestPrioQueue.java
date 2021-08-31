import java.util.Arrays;

public class TestPrioQueue {

    public static void main(String[] args){
        var pq = new MyPriorityQueue();

        pq.insert(10, new Object());
        pq.insert(20, new Object());
        pq.insert(1, new Object());
        pq.insert(100, new Object());
        pq.insert(50, new Object());
        pq.insert(2, new Object());
        pq.insert(4, new Object());
        pq.print();

        pq.extractMin();
        pq.print();

        pq.decreaseKey(2, 1);
        pq.print();

    }
}
