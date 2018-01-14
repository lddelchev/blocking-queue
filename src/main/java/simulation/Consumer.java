package simulation;

import blockingqueue.LinkedBlockingQueue;

/**
 * Represents a consumer in the producer-consumer pattern.
 */
class Consumer implements Runnable {
    private static int lastFreeId = 0;
    private final LinkedBlockingQueue<Integer> integerQueue;
    private final  int numbersToTake;
    private int id = lastFreeId++;

    Consumer(
            final LinkedBlockingQueue<Integer> integerQueue,
            final int numbersToTake) {

        this.integerQueue = integerQueue;
        this.numbersToTake = numbersToTake;
    }

    public void run() {
        for (int i = 0; i < numbersToTake; i++) {
            takeElement();
        }
        System.out.println(
                String.format("Consumer %d finished.", id));
    }

    private synchronized void takeElement() {
        try {
            int takenNumber = integerQueue.take();
        } catch (InterruptedException e) {
            String message = String.format("Consumer %d was interrupted"
                    + "while waiting to take an element", id);
            System.out.println(message);
        }
    }
}
