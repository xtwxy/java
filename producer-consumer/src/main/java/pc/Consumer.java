package pc;

import static java.lang.System.out;

public class Consumer implements Runnable {
    private final ProductQueue queue;
    private final int COUNT;
    public Consumer(ProductQueue queue, int count) {
        this.queue = queue;
        this.COUNT = count;
    }

    @Override
    public void run() {
        for(int i = 0; i < COUNT; ++i) {
            Product product = queue.dequeue();
            out.println("consumed: " + product);
        }
    }
}
