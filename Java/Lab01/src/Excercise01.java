public class Excercise01 {

    public static void main(String[] args){
        int[] aVals = new int[]{ 25, 7, 34, 87, 91, 80 };
        int[] bVals = new int[]{ 5, 9, 53, 19, 87, 25 };

        if(aVals.length != bVals.length) throw new RuntimeException("Arrays must have same size");
        System.out.printf("""
        |   A   |   B   |  GCD  |  LCM  |  A * B  |
        +-----------------------------------------+
        """);
        for (int i = 0; i < aVals.length; i++){
            int a = aVals[i];
            int b = bVals[i];
            System.out.printf("| %5d | %5d | %5d | %5d | %7d |\n", a, b, greatestCommonDivisorIter(a, b), leastCommonMultiple(a, b), a * b);
        }
    }


    /**
     * Returns greatest common divisor using iterative implementation of algorithm of euclid.
     * @param a a number
     * @param b another number
     * @return greatest common divisor
     */
    public static int greatestCommonDivisorIter(int a, int b){
        if(a < 1 || b < 1) throw new IllegalArgumentException("a and b must be natural numbers!");
        int rest = 1; // set to anything but 0 (will be overwritten anyway)
        while (rest != 0){
            rest = a % b;
            a = b;
            b = rest;
        }
        return a;
    }

    /**
     * Returns greatest common divisor using recursive implementation of algorithm of euclid.
     * @param a a natural number
     * @param b another natural number
     * @return greatest common divisor
     */
    public static int greatestCommonDivisorRec(int a, int b){
        // base case
        if(b == 0) return a;
        return greatestCommonDivisorRec(b, a % b);
    }

    /**
     * Calculate least common multiple of two natural numbers
     * @param a a natural number
     * @param b another natural number
     * @return least common multiple
     */
    public static int leastCommonMultiple(int a, int b){
        if(a < 1 || b < 1) throw new IllegalArgumentException("a and b must be natural numbers!");

        int bigger, smaller;
        boolean aIsSmaller;

        if(a < b){
            aIsSmaller = true;
            smaller = a;
            bigger = b;
        } else {
            aIsSmaller = false;
            smaller = b;
            bigger = a;
        }
        int currentSmaller = smaller;
        int currentBigger = bigger;
        int currentAdditon = smaller;

        // Always increase the smaller number by itself until smaller number is bigger than bigger number then play the "game" the other way round or they are the same -> least common multiple found
        while (true){
            while (currentSmaller < currentBigger){
                currentSmaller += currentAdditon;
            }
            if(currentSmaller == currentBigger) return currentBigger;
            // currentSmaller is now bigger than currentBigger
            // -> change values
            if(aIsSmaller) currentAdditon = b;
            else currentAdditon = a;
            aIsSmaller = !aIsSmaller;

            int temp = currentBigger;
            currentBigger = currentSmaller;
            currentSmaller = temp;
        }
    }
}
