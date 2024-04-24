import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

class ProducerConsumerExample {
    private static final int BUFFER_SIZE = 5; // Size of the shared buffer
    private static final int DELAY = 1000; // Delay in milliseconds

    private static Queue<Integer> buffer = new LinkedList<>();
    private static Semaphore emptySlots = new Semaphore(BUFFER_SIZE); // Semaphore for empty slots
    private static Semaphore filledSlots = new Semaphore(0); // Semaphore for filled slots

    static class Producer implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(random.nextInt(DELAY)); // Simulate producing data
                    int data = random.nextInt(100); // Generate a random data item
                    emptySlots.acquire(); // Acquire an empty slot
                    buffer.offer(data); // Add data to the buffer
                    System.out.println("Produced: " + data);
                    filledSlots.release(); // Release a filled slot
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            Random random = new Random();
            while (true) {
                try {
                    Thread.sleep(random.nextInt(DELAY)); // Simulate consuming data
                    filledSlots.acquire(); // Acquire a filled slot
                    int data = buffer.poll(); // Remove data from the buffer
                    System.out.println("Consumed: " + data);
                    emptySlots.release(); // Release an empty slot
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());

        producer.start();
        consumer.start();
    }
}