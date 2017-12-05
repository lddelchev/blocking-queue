## Linked implementation of a blocking queue.

A blocking queue is a queue with a specific capcity that can be accessed by many threads simultaneously. The queue blocks a thread when it tries to pop an element and the queue is empty, or if it tries to push an element and the queue is full.