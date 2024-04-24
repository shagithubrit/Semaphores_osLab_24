import java.util.concurrent.Semaphore;

class ReaderWriterExample {
    private int data = 0; // Shared resource
    private int readCount = 0; // Number of active readers

    private Semaphore mutex = new Semaphore(1); // Binary semaphore for mutual exclusion on readCount
    private Semaphore wrt = new Semaphore(1); // Binary semaphore for read/write access

class Reader implements Runnable {
    @Override
    public void run() {
        try {
            while (true) {
                // Acquire mutex to update readCount
                mutex.acquire();
                readCount++;
                if (readCount == 1) {
                    wrt.acquire(); // Acquire wrt semaphore if first reader
                }
                mutex.release();

                // Reading section
                System.out.println("Thread " + Thread.currentThread().getName() + " is reading data: " + data);

                // Acquire mutex to update readCount
                mutex.acquire();
                readCount--;
                if (readCount == 0) {
                    wrt.release(); // Release wrt semaphore if last reader
                    synchronized (wrt) {
                        wrt.notify(); // Notify writers
                    }
                }
                mutex.release();

                Thread.sleep(1000); // Simulate reading operation
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

    class Writer implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    wrt.acquire(); // Acquire wrt semaphore before writing
                    synchronized (wrt) {
                        while (readCount > 0) {
                            wrt.wait(); // Wait if there are active readers
                        }
                    }

                    // Writing section
                    data++; // Increment shared data
                    System.out.println("Thread " + Thread.currentThread().getName() + " is writing data: " + data);

                    wrt.release(); // Release wrt semaphore after writing

                    Thread.sleep(2000); // Simulate writing operation
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ReaderWriterExample example = new ReaderWriterExample();

        // Create reader threads
        Thread reader1 = new Thread(example.new Reader(), "Reader1");
        Thread reader2 = new Thread(example.new Reader(), "Reader2");

        // Create writer thread
        Thread writer = new Thread(example.new Writer(), "Writer");

        // Start threads
        reader1.start();
        reader2.start();
        writer.start();
    }
}