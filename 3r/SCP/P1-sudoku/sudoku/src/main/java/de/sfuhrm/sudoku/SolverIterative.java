/* ---------------------------------------------------------------
Práctica 1.
Código fuente: SolverIterative.java
Grau Informàtica
--------------------------------------------------------------- */
package de.sfuhrm.sudoku;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Solves a partially filled Sudoku. Can find multiple solutions if they are there.
 */
public final class SolverIterative {

    private final CachedGameMatrixImpl riddle;
    private final List<GameMatrix> possibleSolutions;
    public static final int DEFAULT_LIMIT = 1;
    private int limit;
    private static long recursive_calls = 0;
    public static long start;
    private static int GRANULARITY;
    private static final long DEFAULT_STATS_STEP = 1000000;

    public SolverIterative(final GameMatrix solveMe) {
        Objects.requireNonNull(solveMe, "solveMe is null");
        limit = DEFAULT_LIMIT;
        riddle = new CachedGameMatrixImpl(solveMe.getSchema());
        riddle.setAll(solveMe.getArray());
        possibleSolutions = new CopyOnWriteArrayList<>();
    }

    public static synchronized long getRecursive_calls() { return recursive_calls; }
    public static synchronized void setRecursive_calls(long recursive_calls) { SolverIterative.recursive_calls = recursive_calls; }
    public static synchronized long incRecursive_calls() { return ++SolverIterative.recursive_calls; }
    public void setLimit(final int set) { this.limit = set; }

    private static final class Task {
        final CachedGameMatrixImpl state;
        final int freeCells;
        Task(CachedGameMatrixImpl state, int freeCells) {
            this.state = state;
            this.freeCells = freeCells;
        }
    }

    public List<GameMatrix> solveConcurrentIterative(final int threadCount) {
        start = System.currentTimeMillis();
        possibleSolutions.clear();
        setRecursive_calls(0);

        final int initialFreeCells = riddle.getSchema().getTotalFields() - riddle.getSetCount();
        GRANULARITY = initialFreeCells - 20;
        final LinkedBlockingQueue<Task> queue = new LinkedBlockingQueue<>();
        final AtomicInteger workInProgress = new AtomicInteger(0);
        final AtomicBoolean done = new AtomicBoolean(false);
        final ConcurrentLinkedQueue<Exception> errors = new ConcurrentLinkedQueue<>();
        final CellIndex minimumCell = new CellIndex();
        final GameMatrixImpl.FreeCellResult freeCellResult = riddle.findLeastFreeCell(minimumCell);

        if (freeCellResult == GameMatrixImpl.FreeCellResult.NONE_FREE) {
            GameMatrix gmi = new GameMatrixImpl(riddle.getSchema());
            gmi.setAll(riddle.getArray());
            possibleSolutions.add(gmi);
            return possibleSolutions;
        } else if (freeCellResult == GameMatrixImpl.FreeCellResult.CONTRADICTION) {
            return possibleSolutions;
        }

        final int minFreeMask = riddle.getFreeMask(minimumCell.row, minimumCell.column);
        final int minBits = Integer.bitCount(minFreeMask);
        for (int bit = 0; bit < minBits; bit++) {
            final int index = Creator.getSetBitOffset(minFreeMask, bit);
            CachedGameMatrixImpl clone = new CachedGameMatrixImpl(riddle.getSchema());
            clone.setAll(riddle.getArray());
            clone.set(minimumCell.row, minimumCell.column, (byte) index);
            queue.add(new Task(clone, initialFreeCells - 1));
            workInProgress.incrementAndGet();
        }

        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.execute(() -> {
                final CellIndex cellBuffer = new CellIndex();

                try {
                    while (!done.get()) {
                        Task task = queue.poll();

                        if (task == null) {
                            if (workInProgress.get() == 0) {
                                done.set(true);
                                break;
                            }
                            Thread.sleep(1);
                            continue;
                        }

                        if (done.get()) {
                            workInProgress.decrementAndGet();
                            break;
                        }

                        if (task.freeCells <= GRANULARITY) {
                            backtrackSequential(task.freeCells, cellBuffer, task.state, done);
                            workInProgress.decrementAndGet();
                            continue;
                        }

                        GameMatrixImpl.FreeCellResult res = task.state.findLeastFreeCell(cellBuffer);

                        if (res != GameMatrixImpl.FreeCellResult.FOUND) {
                            workInProgress.decrementAndGet();
                            continue;
                        }

                        final int freeMask = task.state.getFreeMask(cellBuffer.row, cellBuffer.column);
                        final int bits = Integer.bitCount(freeMask);
                        workInProgress.addAndGet(bits - 1);

                        for (int b = 0; b < bits; b++) {
                            if (done.get()) {
                                workInProgress.addAndGet(-(bits - b));
                                break;
                            }
                            int idx = Creator.getSetBitOffset(freeMask, b);
                            CachedGameMatrixImpl child = new CachedGameMatrixImpl(task.state.getSchema());
                            child.setAll(task.state.getArray());
                            child.set(cellBuffer.row, cellBuffer.column, (byte) idx);
                            queue.add(new Task(child, task.freeCells - 1));
                        }
                    }
                } catch (Exception e) {
                    errors.add(e);
                    System.err.println("Hilo " + Thread.currentThread().getName() + " ha fallado:");
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(7, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long end = System.currentTimeMillis();
        System.out.printf("[%3.3f] SUDOKU DONE (Iterative). Recursive Calls: %d. Free Cells: %d. Solutions Found: %d.%n%n",
                (end - start) / 1000.0,
                getRecursive_calls(),
                initialFreeCells,
                possibleSolutions.size());

        if (!errors.isEmpty()) {
            System.err.println("Se han producido errores en algunos hilos:");
            errors.forEach(Throwable::printStackTrace);
        }

        return possibleSolutions;
    }

    private int backtrackSequential(int freeCells, CellIndex minimumCell,
                                    CachedGameMatrixImpl currentRiddle,
                                    AtomicBoolean done) {
        incRecursive_calls();

        if (possibleSolutions.size() >= limit) {
            done.set(true);
            return 0;
        }

        if (freeCells == 0) {
            GameMatrix gmi = new GameMatrixImpl(currentRiddle.getSchema());
            gmi.setAll(currentRiddle.getArray());

            if (possibleSolutions.size() < limit) {
                possibleSolutions.add(gmi);
                if (possibleSolutions.size() >= limit) {
                    done.set(true);
                }
            }
            return 1;
        }

        if (done.get()) return 0;

        GameMatrixImpl.FreeCellResult freeCellResult = currentRiddle.findLeastFreeCell(minimumCell);
        if (freeCellResult != GameMatrixImpl.FreeCellResult.FOUND) return 0;

        int minimumRow = minimumCell.row;
        int minimumColumn = minimumCell.column;
        int minimumFree = currentRiddle.getFreeMask(minimumRow, minimumColumn);
        int minimumBits = Integer.bitCount(minimumFree);

        int result = 0;
        for (int bit = 0; bit < minimumBits; bit++) {
            if (done.get()) break;
            if (possibleSolutions.size() >= limit) {
                done.set(true);
                break;
            }

            int index = Creator.getSetBitOffset(minimumFree, bit);
            currentRiddle.set(minimumRow, minimumColumn, (byte) index);
            result += backtrackSequential(freeCells - 1, minimumCell, currentRiddle, done);
            currentRiddle.set(minimumRow, minimumColumn, currentRiddle.getSchema().getUnsetValue());
        }

        return result;
    }
}
