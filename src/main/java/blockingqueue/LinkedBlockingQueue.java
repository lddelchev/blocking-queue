package blockingqueue;

/**
 * Represents a linked blocking queue.
 * @param <E> the type of the elements in the queue
 */
public final class LinkedBlockingQueue<E> {
    private Node<E> front;
    private Node<E> back;
    private int size;
    private final int capacity;

    /**
     *
     * @param capacity the capacity of the blocking queue. If a thread adds an
     *                 element that would cause the size of the queue to exceed
     *                 its capacity then the thread is blocked until there is
     *                 free space in the queue.
     */
    public LinkedBlockingQueue(final int capacity) {
        assert capacity > 0;

        this.front = null;
        this.back = null;
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Checks if this queue is empty.
     * @return true if this queue is empty and false otherwise.
     */
    public synchronized boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if this queue reached its capacity.
     * @return true if this queue reached its capacity and false otherwise.
     */
    public synchronized boolean full() {
        return size >= capacity;
    }

    /**
     * Looks for the element at the front of this queue without removing it.
     * @return the element at the front of this queue. If the queue is empty
     * then null is returned.
     */
    public synchronized E peek() {
        if (isEmpty()) {
            return null;
        }

        return front.getData();
    }

    /**
     * Pushes a new element at the back of this queue. If putting the new
     * element would cause the queue to exceed its capacity then the thread is
     * blocked until there is free space in the queue.
     * @param newElement the element to be added at the back of this queue.
     * @throws InterruptedException if the current thread is interrupted by
     * another thread while waiting for free space in the queue.
     */
    public synchronized void push(final E newElement)
            throws InterruptedException {
        while (full()) {
            wait();
        }

        Node<E> newNode = new Node<>(newElement);

        if (isEmpty()) {
            notifyAll();
            front = newNode;
        } else {
            back.setNext(newNode);
        }

        back = newNode;
        size++;
    }

    /**
     * Removes the element at the front of the this queue and returns it. If the
     * queue is empty the thread is blocked until there are elements in the
     * queue.
     * @return the element at the front of this queue.
     * @throws InterruptedException if the current thread is interrupted by
     * another thread while waiting for elements in the queue.
     */
    public synchronized E take()
            throws InterruptedException {
        while (isEmpty()) {
            wait();
        }

        if (full()) {
            notifyAll();
        }

        E result = front.getData();
        front = front.getNext();
        size--;

        if (front == null) {
            back = null;
        }

        return result;
    }
}
