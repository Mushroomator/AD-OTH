public class SieveOfEratosthenes {

    public static void main(String[] args){
        sieveOfEratosthenes(10000);
    }

    /**
     * Calculates and prints all prime numbers up to an upper bound using
     * the Sieve of Eratosthenes.
     * @param upperBound upper bound
     */
    public static void sieveOfEratosthenes(int upperBound){
        if(upperBound < 2) return;
        boolean[] deleted = new boolean[upperBound];
        for (int i = 2; i < deleted.length; i++){
            if(!deleted[i - 1]) System.out.println(i);
            else continue;
            for(int j = i + i; j <= upperBound; j += i){
                if(!deleted[j - 1]) deleted[j - 1] = true;
            }
        }
    }
}
