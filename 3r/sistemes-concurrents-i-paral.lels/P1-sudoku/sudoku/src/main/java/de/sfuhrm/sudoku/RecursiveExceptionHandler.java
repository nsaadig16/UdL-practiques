/* ---------------------------------------------------------------
Práctica 1.
Código fuente: RecursiveExceptionHandler.java
Grau Informàtica
--------------------------------------------------------------- */

package de.sfuhrm.sudoku;

public class RecursiveExceptionHandler implements Thread.UncaughtExceptionHandler{
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Thread " + t.threadId() + "has thrown an exception: " + e.getMessage());
    }
}
