package cat.udl.eps.ed.practica2.fibonacci;

import cat.udl.eps.ed.practica2.stack.ArrayStack;

public class Fibonacci {

    public static int fibonacciOriginal(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        if (n <= 1)
            return n;
        else
            return fibonacciOriginal(n - 1) + fibonacciOriginal(n - 2);
    }

    public static int fibonacciRec(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        // CALL
        if (n <= 1)
            return n;
        else {
            int f1 = fibonacciRec(n - 1);
            // RESUME1
            int f2 = fibonacciRec(n - 2);
            // RESUME2
            return f1 + f2;
        }
    }

    private static class Context {
        final int n;
        int f1;
        int f2;
        EntryPoint entryPoint;

        public Context(int n) {
            this.n = n;
            this.f1 = 0;
            this.f2 = 0;
            this.entryPoint = EntryPoint.CALL;
        }
    }

    private enum EntryPoint {
        CALL, RESUME1, RESUME2
    }

    public static int fibonacciIter(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        int return_ = 0;
        var stack = new ArrayStack<Context>();
        stack.push(new Context(n));
        while (!stack.isEmpty()) {
            var context = stack.top();
            switch (context.entryPoint) {
                case CALL -> {
                    if (context.n <= 1) {
                        return_ = context.n;
                        stack.pop();
                    } else {
                        context.entryPoint = EntryPoint.RESUME1;
                        stack.push(new Context(context.n - 1));
                    }
                }
                case RESUME1 -> {
                    context.f1 = return_;
                    context.entryPoint = EntryPoint.RESUME2;
                    stack.push(new Context(context.n - 2));
                }
                case RESUME2 -> {
                    context.f2 = return_; // I can remove it
                    return_ = context.f1 + context.f2;
                    stack.pop();
                }
            }
        }
        return return_;
    }
}
