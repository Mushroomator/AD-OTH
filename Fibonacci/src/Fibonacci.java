import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;

public class Fibonacci {

    private static int n = 50;

    public static void main(String[] args){
        if(args.length > 1){
            try{
                n = Integer.parseInt(args[1]);
            } catch (NumberFormatException e){
                System.err.println("Argument must be a positive number.");
                return;
            }
        }

        // iterative
        System.out.println("Starting iterative calculation...");
        var itStart = System.currentTimeMillis();
        var itFib = fibIterative(n);
        var itStop = System.currentTimeMillis();
        System.out.println("""
                Iterative impl. : 
                Result: %d \t Time elapsed: %dms
                """
                .formatted(itFib, itStop - itStart));

        // recursive
        System.out.println("Starting recursive calculation...");
        var recStart = System.currentTimeMillis();
        var recFib = fibRecursive(n);
        var recStop = System.currentTimeMillis();

        System.out.println("""
                Recursive impl. : 
                Result: %d \t Time elapsed: %dms
                """
                .formatted(recFib, recStop - recStart));
        return;
    }

    /**
     * Recursive implementation for calculation of Fibonacci numbers.
     * Note: First two Fibonacci numbers are defined to be 1. For simplicity any number
     * n < 3 will return 1.
     * Runtime: O(2^n)
     * Memory: O(2^n)
     * @param n nth Fibonacci number
     * @return nth Fibonacci number
     */
    public static long fibRecursive(int n){
        // fib(1) = fib(2) = 1
        if(n < 3) return 1;
        return fibRecursive(n-1) + fibRecursive(n - 2);
    }

    /**
     * Iterative implementation for calculation of Fibonacci numbers.
     * Runtime: O(n)
     * Memory: O(1)
     * Note: First two Fibonacci numbers are defined to be 1. For simplicity any number
     * n < 3 will return 1.
     * @param n nth Fibonacci number
     * @return nth Fibonacci number
     */
    public static long fibIterative(int n){
        long result = 1, fib1 = 1, fib2 = 1;
        int i = 2;

        if(n < 3) return result;

        while (i < n){
            result = fib1 + fib2;
            i++;
            fib2 = fib1;
            fib1 = result;
        }

        return result;
    }
}
