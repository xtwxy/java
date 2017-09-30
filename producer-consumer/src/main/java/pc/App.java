package pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

public class App {
    public static void main(String[] args) {
        if(args.length < 1) {
            out.println("usage: java -jar <this jar> <count>");
            return;
        }
        final int COUNT = Integer.parseInt(args[0]);
        ProductQueue queue = new ProductQueue(10);
        Producer producer = new Producer(queue, COUNT);
        Consumer consumer = new Consumer(queue, COUNT);

        ExecutorService pool = Executors.newFixedThreadPool(2);

        pool.submit(producer);
        pool.submit(consumer);
        pool.shutdown();
        try {
            if(!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if(!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                    out.println("ERROR: Thread pool did not terminate!");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            pool.shutdownNow();
        }
    }
}
