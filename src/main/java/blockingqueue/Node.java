package blockingqueue;

/**
 * Represents a node in a linear linked data structure.
 * @param <E> the type of the data contained in the node.
 */
final class Node<E> {
    private final E data;
    private Node<E> next;

    Node(final E data) {
        this.data = data;
        this.next = null;
    }

    Node(final E data, final Node<E> next) {
        this.data = data;
        this.next = next;
    }

    E getData() {
        return data;
    }

    Node<E> getNext() {
        return next;
    }

    void setNext(final Node<E> newNext) {
        this.next = newNext;
    }
}
