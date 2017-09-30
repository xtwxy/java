package pc;

import static java.lang.System.out;

public class Producer implements Runnable {
    private final ProductQueue queue;
    private final int COUNT;
    public Producer(ProductQueue queue, int count) {
        this.queue = queue;
        this.COUNT = count;
    }

    @Override
    public void run() {
        for(int i = 0; i < COUNT; ++i) {
            Product product = new Product("item-" + i, "" + (1000 + i));
            queue.enqueue(product);
            out.println("produced: " + product);
        }
    }
}
