package cat.udl.eps.ed.practica2.catalan;

import cat.udl.eps.ed.practica2.fibonacci.Fibonacci;
import cat.udl.eps.ed.practica2.stack.ArrayStack;
import cat.udl.eps.ed.practica2.stack.Stack;

public class Catalan {

    public static long catalanRecFor(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        if (n == 0) {
            return 1L;
        } else {
            long res = 0L;
            for (int i = 0; i < n; i++) {
                res += catalanRecFor(i) * catalanRecFor(n - i - 1);
            }
            return res;
        }
    }

    public static long catalanRecWhile(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        if (n == 0) {
            return 1L;
        } else {
            long res = 0L;
            int i = 0;
            while (i < n) {
                res += catalanRecWhile(i) * catalanRecWhile(n - i - 1);
                i++;
            }
            return res;
        }
    }

    public static long catalanRecWhileSeparateCalls(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        long res = 0L;
        int i = 0;
        long catalan1 = 0L;
        long catalan2 = 0L;
        // CALL
        if (n == 0) {
            return 1L;
        } else {
            // LOOP
            while (i < n) {
                catalan1 = catalanRecWhile(i);
                // RESUME1
                catalan2 = catalanRecWhile(n - i - 1);
                // RESUME2
                res += catalan1 * catalan2;
                i++;
            }
            return res;
        }
    }

    /*
            Pseudo Java, but it's how its executed (think about it)

            public static long catalanRecWhileSeparateCalls(int n) {
                assert n >= 0 : "n must be greater or equal to 0";
                long res = 0L;
                int i = 0;
                long catalan1 = 0L;
                long catalan2 = 0L;
                // CALL
                if (n == 0) {
                    return 1L;
                } else {
                    // LOOP
                    if (i < n) {
                        catalan1 = catalanRecWhileSeparateCalls(i);
                        // RESUME1
                        catalan2 = catalanRecWhileSeparateCalls(n - i - 1);
                        // RESUME2
                        res += catalan1 * catalan2;
                        i++;
                        goto LOOP
                    } else {
                        return res;
                    }
                }
            }
     */

    private enum EntryPoint {
        CALL, LOOP, RESUME1, RESUME2
    }

    private static class Context {
        final int n;
        int i;
        long catalan1;
        long catalan2;
        Catalan.EntryPoint entryPoint;

        public Context(int n) {
            this.n = n;
            this.i = 0;
            this.catalan1 = 0L;
            this.catalan2 = 0L;
            this.entryPoint = Catalan.EntryPoint.CALL;
        }
    }

    public static long catalanIter(int n) {
        assert n >= 0 : "n must be greater or equal to 0";
        long return_ = 0L;
        var stack = new ArrayStack<Context>();
        stack.push(new Context(n));
        while(!stack.isEmpty()){
            var context = stack.top();
            switch(context.entryPoint){
                case CALL -> {
                    if(context.n == 0){
                        return_ = 1L;
                        stack.pop();
                    }else{
                        context.entryPoint = EntryPoint.LOOP;
                    }
                }
                case LOOP -> {
                    context.entryPoint = EntryPoint.RESUME1;
                    stack.push(new Context(context.i));
                }
                case RESUME1 -> {
                    context.catalan1 = return_;
                    context.entryPoint = Catalan.EntryPoint.RESUME2;
                    stack.push(new Catalan.Context(context.n - context.i - 1));
                }
                case RESUME2 ->  {
                    context.i++;
                    context.catalan1 = context.catalan1 * return_;
                    context.catalan2 += context.catalan1;
                    return_ = context.catalan2;;
                    if(context.i < context.n){
                        context.entryPoint = EntryPoint.LOOP;
                    }else{
                        stack.pop();
                    }
                }
            }
        }
        return return_;
    }
}

