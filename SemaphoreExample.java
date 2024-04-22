import java.util.concurrent.Semaphore;

class Task extends Thread {
    private Semaphore semaphore;
    private String name;

    public Task(Semaphore semaphore, String name) {
        this.semaphore = semaphore;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); // Acquire a permit from the semaphore
            for (int i = 0; i < 5; i++) {
                System.out.println(name + ": Working: " + i);
                Thread.sleep(1000); // Simulate some work
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // Release the permit
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); // Initialize semaphore with 3 permits

        // Create and start 5 threads
        Thread t1 = new Task(semaphore, "Thread-1");
        Thread t2 = new Task(semaphore, "Thread-2");
        Thread t3 = new Task(semaphore, "Thread-3");
        Thread t4 = new Task(semaphore, "Thread-4");
        Thread t5 = new Task(semaphore, "Thread-5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }
}