package cat.udl.eps.ed.practica1.benchmark;

import cat.udl.eps.ed.practica1.sorting.IntArraySorter;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Random;

import static java.util.concurrent.TimeUnit.NANOSECONDS;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(NANOSECONDS)
public class MyBenchmark {

    @State(Scope.Benchmark)
    public static class BenchMarkState {

        @Param({"100", "200", "400", "800"})
        private int N;

        private int[] data;

        @Setup(Level.Invocation)
        public void fillArray() {
            data = new int[N];
            for (int i = 0; i < N; i++) data[i] = i;
            // Shuffle via Fisher-Yates algorithm
            Random rg = new Random();
            for (int i = N - 1; i >= 1; i--) {
                int j = rg.nextInt(i + 1);
                int tmp = data[i];
                data[i] = data[j];
                data[j] = tmp;
            }
        }
    }

    @Benchmark
    public void testBubbleSort(Blackhole blackhole, BenchMarkState state) {
        IntArraySorter sorter = new IntArraySorter(state.data);
        sorter.bubbleSort();
        blackhole.consume(state.data);
    }

    @Benchmark
    public void testSelectionSort(Blackhole blackhole, BenchMarkState state) {
        IntArraySorter sorter = new IntArraySorter(state.data);
        sorter.selectionSort();
        blackhole.consume(state.data);
    }

    @Benchmark
    public void testInsertionSort(Blackhole blackhole, BenchMarkState state) {
        IntArraySorter sorter = new IntArraySorter(state.data);
        sorter.insertionSort();
        blackhole.consume(state.data);
    }

    @Benchmark
    public void testQuickSort(Blackhole blackhole, BenchMarkState state) {
        IntArraySorter sorter = new IntArraySorter(state.data);
        sorter.quickSort();
        blackhole.consume(state.data);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .warmupTime(TimeValue.seconds(1L))
                .measurementTime(TimeValue.seconds(1L))
                .build();

        new Runner(opt).run();
    }
}
