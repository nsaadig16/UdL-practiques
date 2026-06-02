/* ---------------------------------------------------------------
Práctica 1.
Código fuente: SolverRecursive.java
Grau Informàtica
--------------------------------------------------------------- */
package de.sfuhrm.sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Solves a partially filled Sudoku recursively with threads.
 * Can find multiple solutions if they are there.
 *
 * @author Stephan Fuhrmann
 */
public final class SolverRecursive {

    /**
     * Current working copy.
     */
    private final CachedGameMatrixImpl riddle;

    /**
     * The possible solutions for this riddle.
     */
    private final List<GameMatrix> possibleSolutions;

    /** The default limit.
     * @see #limit
     */
    public static final int DEFAULT_LIMIT = 1;

    /**
     * The maximum number of solutions to search.
     */
    private int limit;

    /**
     * Counter for recursive calls on bactrack algorithm
     */
    private static long recursive_calls=0;
    public static long start;

    private static final long DEFAULT_STATS_STEP = 1000000;

    /**
     * Granularity for the creation of threads.
     */
    private static final int GRANULARITY = 240;

    /**
     * Maximum threads that can be created.
     */
    private static int maxThreads;

    /**
     * Number of active threads
     */
    private AtomicInteger activeThreads = new AtomicInteger(0);

    /**
     * Is true if the program has been stopped.
     */
    private volatile boolean cancel = false;

    private final List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());

    /**
     * Creates a solver for the given riddle.
     *
     * @param solveMe the riddle to solve.
     */
    public SolverRecursive(final GameMatrix solveMe) {
        Objects.requireNonNull(solveMe, "solveMe is null");
        limit = DEFAULT_LIMIT;
        riddle = new CachedGameMatrixImpl(solveMe.getSchema());
        riddle.setAll(solveMe.getArray());
        possibleSolutions = new ArrayList<>();
    }

    public static synchronized long getRecursive_calls() {
        return recursive_calls;
    }

    public static synchronized void setRecursive_calls(long recursive_calls) {
        SolverRecursive.recursive_calls = recursive_calls;
    }

    public static synchronized long incRecursive_calls() {
        return ++SolverRecursive.recursive_calls;
    }

    /** Set the limit for maximum results.
     * @param set the new limit.
     */
    public void setLimit(final int set) {
        this.limit = set;
    }

    /**
     * Runnable class to implement the concurrent version.
     */
    private class BacktrackRunnable implements Runnable {
        int freeCells;
        CellIndex minimumCell;
        CachedGameMatrixImpl copyRiddle;

        BacktrackRunnable(int freeCells, CellIndex minimumCell, CachedGameMatrixImpl copyRiddle){
            this.freeCells = freeCells;
            this.minimumCell = minimumCell;
            this.copyRiddle = copyRiddle;
        }

        @Override
        public void run(){
            try{
                backtrack(freeCells,minimumCell,copyRiddle);
            }finally {
                activeThreads.decrementAndGet();
            }
        }
    }

    private void cancel(){
        cancel = true;
        synchronized (threadList){
            for (Thread th : threadList){
                if (th.isAlive() && th != null){
                    th.interrupt();
                }
            }
        }
    }

    /**
     * Solves the Sudoku problem.
     *
     * @param maxThreads The maximum of threads to generate.
     * @return the found solutions. Should be only one.
     */
    public List<GameMatrix> solve(int maxThreads) {
        SolverRecursive.maxThreads = maxThreads;
        start = System.currentTimeMillis();
        possibleSolutions.clear();
        int freeCells = riddle.getSchema().getTotalFields()
                - riddle.getSetCount();

        backtrack(freeCells, new CellIndex(),riddle);

        long end = System.currentTimeMillis();
        System.out.printf("[%3.3f] CONCURRENT - RECURSIVE\nSUDOKU DONE. Recursive Calls: %d. Free Cells: %d. Solutions Found: %d.\n\n",(end-start)/1000.0, this.getRecursive_calls(), freeCells, possibleSolutions.size());

        return Collections.unmodifiableList(possibleSolutions);
    }


    /**
     * Solves a Sudoku using backtracking.
     *
     * @param freeCells number of free cells, abort criterion.
     * @param minimumCell coordinates to the so-far found minimum cell.
     * @param currentRiddle The cached copy of the riddle.
     * @return the total number of solutions.
     */
    private int backtrack(final int freeCells, final CellIndex minimumCell, CachedGameMatrixImpl currentRiddle) {
        assert freeCells >= 0 : "freeCells is negative";

        if(cancel || Thread.currentThread().isInterrupted()){
            return 0;
        }
        if ((this.incRecursive_calls()%DEFAULT_STATS_STEP)==0) {
            long end = System.currentTimeMillis();
            System.out.printf("[%3.3f] Recursive Calls: %d. Free Cells: %d. Solutions Found: %d.\n",(end-start)/1000.0, this.getRecursive_calls(), freeCells, possibleSolutions.size());
        }
        // don't recurse further if already at limit
        if (possibleSolutions.size() >= limit) {
            this.cancel();
            return 0;
        }

        if (cancel) return 0;

        // just one result, we have no more to choose
        if (freeCells == 0) {
            GameMatrix gmi = new GameMatrixImpl(currentRiddle.getSchema());
            gmi.setAll(currentRiddle.getArray());
            possibleSolutions.add(gmi);

            return 1;
        }

        GameMatrixImpl.FreeCellResult freeCellResult =
                currentRiddle.findLeastFreeCell(minimumCell);
        if (freeCellResult != GameMatrixImpl.FreeCellResult.FOUND) {
            // no solution
            return 0;
        }

        int result = 0;
        int minimumRow = minimumCell.row;
        int minimumColumn = minimumCell.column;
        int minimumFree = currentRiddle.getFreeMask(minimumRow, minimumColumn);
        int minimumBits = Integer.bitCount(minimumFree);
        List<Thread> threads = new ArrayList<>();

        if (freeCells > GRANULARITY){
            for (int bit = 0; bit < minimumBits; bit++) {
                int index = Creator.getSetBitOffset(minimumFree, bit);
                assert index > 0;
                CachedGameMatrixImpl currentRiddleCopy = new CachedGameMatrixImpl(currentRiddle.getSchema());
                currentRiddleCopy.setAll(currentRiddle.getArray());
                currentRiddleCopy.set(minimumRow,minimumColumn,(byte) index);

                if (activeThreads.get() < SolverRecursive.maxThreads){
                    // We add to the total number of threads
                    activeThreads.incrementAndGet();
                    // We create the thread, set the handler and add it to the list.
                    Thread th = new Thread(new BacktrackRunnable(freeCells - 1, new CellIndex(),currentRiddleCopy));
                    th.setUncaughtExceptionHandler(new RecursiveExceptionHandler());
                    threads.add(th);
                    threadList.add(th);
                    th.start();
                } else {
                    // If already has max threads, do it sequentially.
                    result += backtrack(freeCells - 1, new CellIndex(), currentRiddleCopy);
                }
            }

            for (Thread t : threads){
                try {
                    // Join the threads
                    t.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return result;
                }
            }

        } else {
            // else we are done
            // now try each number
            for (int bit = 0; bit < minimumBits; bit++) {
                if (cancel) return 0;
                int index = Creator.getSetBitOffset(minimumFree, bit);
                assert index > 0;

                // Asignamos número index a la celda
                currentRiddle.set(minimumRow, minimumColumn, (byte) index);
                int resultCount = backtrack(freeCells - 1, minimumCell, riddle);
                result += resultCount;
                // Antes de volver marcamos la celda como no asignada
                riddle.set(minimumRow,
                        minimumColumn,
                        riddle.getSchema().getUnsetValue()
                );
            }

        }
        return result;
    }
}
