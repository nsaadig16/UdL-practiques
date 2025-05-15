package cat.udl.eps.ed.practica2.stack;

import java.util.NoSuchElementException;

/**
 * An implementation of a stack using an extensible array
 *
 * @param <E> the type of elements in the stack
 * @see Stack
 */
public class ArrayStack<E> implements Stack<E> {
    private static final int DEFAULT_SIZE = 10;
    private Object[] elements;
    private int size;

    /**
     * Creates an empty stack
     * @implNote
     * Uses a default size of 10 to create the array
     */

    public ArrayStack() {
        elements = new Object[DEFAULT_SIZE];
        size = 0;
    }

    /**
     * Adds an element to the top of the stack.
     *
     * @implNote
     *  If there is no room in the stack, the size of the array is doubled
     *
     * @param elem the element to be added
     */
    @Override
    public void push(E elem) {
        if (size == elements.length) {
            resize();
        }
        elements[size] = elem;
        size++;
    }

    /**
     * Returns the element at the top of the stack.
     *
     * @return the element at the top of the stack
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    @SuppressWarnings("unchecked")
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException("The stack is empty");
        } else {
            return (E) elements[size - 1];
        }
    }

    /**
     * Removes the element at the top of the stack.
     *
     * @throws NoSuchElementException if the stack is empty
     */
    @Override
    public void pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("The stack is empty");
        } else{
            size--;
            elements[size] = null;
        }
    }

    /**
     * Returns true if the stack is empty, false otherwise.
     *
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(){
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }
}
