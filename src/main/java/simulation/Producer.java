package simulation;

import blockingqueue.LinkedBlockingQueue;

import java.util.Random;

/**
 * Represents the producer in the producer-consumer pattern.
 */
class Producer implements Runnable {
    private static final int MAX_NUMBER = 120;
    private static int lastFreeId = 0;

    private final LinkedBlockingQueue<Integer> integerQueue;
    private final  int numbersToInsert;
    private Random integerGenerator = new Random();
    private int id = lastFreeId++;

    Producer(
            final LinkedBlockingQueue<Integer> integerQueue,
            final int numbersToInsert) {

        this.integerQueue = integerQueue;
        this.numbersToInsert = numbersToInsert;
    }

    public synchronized void run() {
        for (int i = 0; i < numbersToInsert; i++) {
            produceElement();
        }
        System.out.println(String.format("Producer %d finished.", id));
    }

    private synchronized void produceElement() {
        try {
            int generatedNumber = integerGenerator.nextInt(MAX_NUMBER);
            integerQueue.push(generatedNumber);
        } catch (InterruptedException e) {
            String message = String.format("Producer %d was interrupted"
                    + "while waiting to push an element", id);
            System.out.println(message);
        }
    }
}
