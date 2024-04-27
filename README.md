READER WRITER PROBLEM EXPLAINATION --->
Sure, let's go through the code step by step:

The code starts by importing the java.util.concurrent.Semaphore class, which provides a simple way to implement semaphores in Java.
The ReaderWriterExample class is defined, which contains the following members:

data: An integer variable representing the shared resource that readers will read and writers will modify.
readCount: An integer variable to keep track of the number of active readers.
mutex: A binary semaphore used for mutual exclusion when updating the readCount variable.
wrt: A binary semaphore used for controlling read/write access to the shared resource.


The Reader class is defined as an inner class of ReaderWriterExample. It implements the Runnable interface, which means it can be executed as a separate thread. The run() method contains the following logic:

The reader thread enters an infinite loop.
The mutex semaphore is acquired to update the readCount variable safely.
The readCount is incremented, and if it's the first reader, the wrt semaphore is acquired to prevent writers from accessing the shared resource.
The mutex semaphore is released.
The reader thread reads the shared data data and prints a message.
The mutex semaphore is acquired again to update the readCount variable.
The readCount is decremented, and if it's the last reader, the wrt semaphore is released, and any waiting writers are notified.
The mutex semaphore is released.
The reader thread sleeps for 1 second to simulate a reading operation.


The Writer class is defined as an inner class of ReaderWriterExample. It implements the Runnable interface, which means it can be executed as a separate thread. The run() method contains the following logic:

The writer thread enters an infinite loop.
The wrt semaphore is acquired to ensure exclusive access to the shared resource.
If there are active readers (readCount > 0), the writer thread waits on the wrt semaphore until all readers have finished.
The writer thread modifies the shared data data by incrementing it and prints a message.
The wrt semaphore is released, allowing readers or other writers to access the shared resource.
The writer thread sleeps for 2 seconds to simulate a writing operation.


In the main method, an instance of the ReaderWriterExample class is created.
Two reader threads (reader1 and reader2) and one writer thread (writer) are created using the Thread class and the inner classes Reader and Writer.
The reader and writer threads are started using the start() method.

The purpose of this code is to demonstrate the Reader-Writer problem, where multiple reader threads can read the shared data concurrently, but only one writer thread can modify the shared data at a time. The semaphores mutex and wrt are used to ensure proper synchronization and mutual exclusion between readers and writers.
When a reader thread wants to read the shared data, it acquires the wrt semaphore to ensure no writers are currently modifying the data. It also acquires the mutex semaphore to safely increment the readCount. If it's the first reader, it acquires the wrt semaphore again to prevent writers from accessing the shared resource.
When a writer thread wants to modify the shared data, it acquires the wrt semaphore to ensure exclusive access. If there are active readers (readCount > 0), the writer thread waits on the wrt semaphore until all readers have finished.
After reading or writing the shared data, the reader and writer threads release the acquired semaphores, allowing other threads to access the shared resource.
