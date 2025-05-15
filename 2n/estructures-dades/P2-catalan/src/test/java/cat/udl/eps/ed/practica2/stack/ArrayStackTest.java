package cat.udl.eps.ed.practica2.stack;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ArrayStackTest {
    @Test
    @DisplayName("isEmpty() on empty stack should return true")
    void test1() {
        var emptyStack = new ArrayStack<Integer>();
        assertTrue(emptyStack.isEmpty());
    }

    @Test
    @DisplayName("isEmpty() on non-empty stack should return false")
    void test2() {
        var stack = new ArrayStack<Integer>();
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("pop() on empty stack should throw a NoSuchElement exception")
    void test3() {
        var emptyStack = new ArrayStack<Integer>();
        assertThrows(NoSuchElementException.class, () -> {
            emptyStack.pop();
        });
    }

    @Test
    @DisplayName("top() on empty stack should throw a NoSuchElement exception")
    void test4(){
        var emptyStack = new ArrayStack<Integer>();
        assertThrows(NoSuchElementException.class, () -> {
            emptyStack.top();
        });
    }

    @Test
    @DisplayName("pop() on one-element stack should leave the stack empty")
    void test5(){
        var stack = new ArrayStack<Integer>();
        stack.push(3);
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("top() on one-element stack should return the element and leave the stack non-empty")
    void test6(){
        var stack = new ArrayStack<Integer>();
        stack.push(5);
        assertEquals(5,(int) stack.top());
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("top() on two-element stack should return the second element and leave the stack non-empty")
    void test7 (){
        var stack = new ArrayStack<Integer>();
        stack.push(1);
        stack.push(2);
        assertEquals(2,(int) stack.top());
        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("top() on two-element stack should return the second element and after pop() and top() it should return the first")
    void test8(){
        var stack = new ArrayStack<Integer>();
        stack.push(9);
        stack.push(5);
        assertEquals(5, (int) stack.top());
        stack.pop();
        assertEquals(9, (int) stack.top());
    }

}
