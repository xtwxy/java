package pc;

import java.util.LinkedList;
import java.util.Queue;

public class ProductQueue {
    private final int capacity;
    private final Queue<Product> queue;

    public ProductQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public synchronized void enqueue(Product p) {
        do {
            if (queue.size() < capacity) {
                queue.add(p);
                notify();
                return;
            } else {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (true);
    }

    public synchronized Product dequeue() {
        do {
           if(!queue.isEmpty()) {
               Product p = queue.remove();
               notify();
               return p;
           } else {
               try {
                   wait();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        } while (true);
    }
}
