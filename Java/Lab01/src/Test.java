import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        var initM = new int[][]{
                { 0, 1, 1, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 1, 1, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 1, 0, 0, 0, 0, 1, 0, 0, 1 },
                { 0, 0, 1, 1, 1, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 0, 1 },
                { 0, 0, 0, 0, 0, 1, 0, 0, 0 },
        };

        var anotherM = new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0},
        };

        var matrix = new Matrix(anotherM);

        var prevMatrix = anotherM;
        for (int i = 1; i <= 9; i++) {
            System.out.printf("k = %d%n", i);
            Matrix.print(prevMatrix);
            prevMatrix = matrix.multiply(anotherM, false);
        }


    }
}
