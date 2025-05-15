package cat.udl.eps.ed.practica2.stack;

import java.util.NoSuchElementException;

/**
 * A stack is a collection of elements that are added and removed according to
 * the last-in first-out (LIFO) principle.
 *
 * @param <E> the type of elements in the stack
 */
public interface Stack<E> {
    /**
     * Adds an element to the top of the stack.
     *
     * @param elem the element to be added
     */
    void push(E elem);

    /**
     * Returns the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    E top();

    /**
     * Removes the element at the top of the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    void pop();

    /**
     * Returns true if the stack is empty, false otherwise.
     *
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();
}
