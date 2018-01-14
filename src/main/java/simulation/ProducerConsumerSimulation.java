package simulation;

import blockingqueue.LinkedBlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Provides functionality for testing a blocking queue by multiple consumer and
 * producer threads.
 */
public final class ProducerConsumerSimulation {
    private ProducerConsumerSimulation() { };

    /**
     * Starts a producer-consumer simulation.
     * @param queue the queue to be used for the simulation
     * @param amountToProduce number of produced elements per producer
     * @param amountToConsume number of consumed elements per consumer
     * @param numberOfProducers number of producers
     * @param numberOfConsumers number of consumers
     */
    public static void startSimulation(
            final LinkedBlockingQueue queue,
            final int amountToProduce,
            final int amountToConsume,
            final int numberOfProducers,
            final int numberOfConsumers) {

        int threadsNeeded = numberOfConsumers + numberOfProducers;
        ExecutorService executor = Executors.newFixedThreadPool(threadsNeeded);

        for (int i = 0; i < numberOfConsumers; i++) {
            executor.execute(new Consumer(queue, amountToConsume));
        }

        for (int i = 0; i < numberOfProducers; i++) {
            executor.execute(new Producer(queue, amountToProduce));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }

    public static void main(final String[] args) {
        final int queueCapacity = 40;
        final int amountToProduce = 120;
        final int amountToConsume = 120;
        final int numberOfProducers = 7;
        final int numberOfConsumers = 7;

        LinkedBlockingQueue<Integer> queue =
                new LinkedBlockingQueue<>(queueCapacity);

        startSimulation(queue, amountToProduce, amountToConsume,
                numberOfProducers, numberOfConsumers);
    }
}
