import java.util.Scanner;

public class GreatestCommonDivisor {

    public static void main(String[] args) {
        System.out.print(
        """
        +-------------------------------------------+
        | Euclidean Algorithm                       |
        +-------------------------------------------+
        Please provide the two number greatest common divisor should be found for:
        """);
        var sc = new Scanner(System.in);
        System.out.print("A: ");
        var a = sc.nextInt();
        System.out.print("B: ");
        var b = sc.nextInt();
        var result = euclideanAlgorithm(a, b);
        System.out.printf("Greatest common divisor for %d and %d: %d", a, b, result);
    }

    /**
     * Find greatest common divisor using euclidean algorithm.
     * @param a a integer
     * @param b another integer
     * @return greates common divisor
     */
    public static int euclideanAlgorithm(int a, int b){
        if(a == 0) return Math.abs(b);

        while (b != 0){
            var rest = a % b;
            a = b;
            b = rest;
        }
        return Math.abs(a);
    }
}
