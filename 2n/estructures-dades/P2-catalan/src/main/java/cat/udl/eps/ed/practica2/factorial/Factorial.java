package cat.udl.eps.ed.practica2.factorial;

import cat.udl.eps.ed.practica2.stack.ArrayStack;

public class Factorial {

    public static long factorialOriginal(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        if (n == 0)
            return 1L;
        else
            return n * factorialOriginal(n - 1);
    }

    public static long factorialRec(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        long f = 0;
        // CALL
        if (n == 0)
            return 1L;
        else {
            f = factorialRec(n - 1); // recurse
            // RESUME
            return n * f;
        }
    }

    private static class Context {
        final int n;
        long f;
        EntryPoint entryPoint;

        Context(int n) {
            this.n = n;
            this.f = 0L;
            this.entryPoint = EntryPoint.CALL;
        }
    }

    private enum EntryPoint {
        CALL, RESUME
    }

    public static long factorialIter(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        long return_ = 0L;
        var stack = new ArrayStack<Context>();
        stack.push(new Context(n));
        while (!stack.isEmpty()) {
            var context = stack.top();
            switch (context.entryPoint) {
                case CALL -> {
                    if (context.n == 0) { // simple
                        return_ = 1L;
                        stack.pop();
                    } else {
                        context.entryPoint = EntryPoint.RESUME;
                        stack.push(new Context(context.n - 1));
                    }
                }
                case RESUME -> {
                    context.f = return_;
                    return_ = context.f * context.n;
                    stack.pop();
                }
            }
        }
        return return_;
    }
}
