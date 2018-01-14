package blockingqueue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class LinkedBlockingQueueTest {
    private final static int CAPACITY = 10;
    private static LinkedBlockingQueue<Integer> queue;
    private static Integer exampleData;
    private static Integer exampleData1;
    private static Integer exampleData2;
    private static Integer exampleData3;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void createEmptyQueue() {
        queue = new LinkedBlockingQueue<>(CAPACITY);
        exampleData = 1;
        exampleData1 = 2;
        exampleData2 = 3;
        exampleData3 = 4;
    }

    @Test
    public void givenNewlyCreatedQueueWhenEmptyMethodCalledThenItReturnsTrue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void givenNonEmptyQueueWhenEmptyMethodCalledThenItReturnsFalse() throws InterruptedException {
        queue.push(exampleData);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void givenEmptyQueueWhenNewElementPushedThenItIsReturnedByPeek() throws InterruptedException {
        queue.push(exampleData);
        assertEquals(exampleData, queue.peek());
    }

    @Test
    public void givenNonEmptyQueueWhenPeekCalledTheTopElementDoesNotChange() throws InterruptedException {
        queue.push(exampleData);
        queue.push(exampleData1);
        queue.peek();
        assertEquals(exampleData, queue.peek());
    }

    @Test
    public void givenEmptyQueueWhenNewElementPushedThenItIsReturnedByTake() throws InterruptedException {
        queue.push(exampleData);
        assertEquals(exampleData, queue.take());
    }

    @Test
    public void givenOneElementQueueWhenTakeCalledThenItBecomesEmpty() throws InterruptedException {
        queue.push(exampleData);
        queue.take();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void givenMultiplePushesWhenPopCalledThenTheFifoOrderIsPreserved() throws InterruptedException {
        List<Integer> elementsToInsert = new ArrayList<>();
        elementsToInsert.add(exampleData1);
        elementsToInsert.add(exampleData2);
        elementsToInsert.add(exampleData3);

        for (int i=0; i<elementsToInsert.size(); i++) {
            queue.push(elementsToInsert.get(i));
        }

        for (int i=0; i<elementsToInsert.size(); i++) {
            assertEquals(elementsToInsert.get(i), queue.take());
        }
    }

    @Test
    public void givenQueueWhenConsecutivePushesAndTakesThenFifoOrderIsPreserved() throws InterruptedException {
        queue.push(exampleData);
        queue.push(exampleData1);
        queue.push(exampleData2);
        queue.take();
        queue.push(exampleData3);

        assertEquals(exampleData1, queue.take());
        assertEquals(exampleData2, queue.take());
        assertEquals(exampleData3, queue.take());
    }

    @Test
    public void givenQueueWhenEqualElementsPushedThenTheyAppearMultipleTimes() throws InterruptedException {
        queue.push(exampleData1);
        queue.push(exampleData1);
        queue.push(exampleData);

        assertEquals(exampleData1, queue.take());
        assertEquals(exampleData1, queue.take());
    }

    @Test
    public void givenQueueWhenCapacityReachedThenFullReturnsTrue() throws InterruptedException {
        for (int i=0; i < CAPACITY; i++) {
            queue.push(exampleData);
        }

        assertTrue(queue.full());
    }
}
