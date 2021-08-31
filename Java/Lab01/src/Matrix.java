import java.util.Scanner;

public class Matrix {

    private int[][] m;

    /**
     * Create matrix object
     */
    public Matrix() {
    }

    /**
     * Creates 2-dimensional, quadratic matrix with dimension dim (dim x dim- matrix) and initializes it with zeroes.
     * @param dim
     */
    public Matrix(int dim) {
        this.m = new int[dim][dim];
    }

    public Matrix(int[][] matrix) {
        init(matrix);
    }

    /**
     * Sets creates dim x dim matrix and initializes it with zeroes.
     * @param dim
     */
    public void init(int dim){
        this.m = new int[dim][dim];
    }

    public void init(int[][] matrix){
        for(int[] row : matrix){
            if(row.length != matrix.length) throw new IllegalArgumentException(String.format("Matrix must have be quadratic! E.g.: %dx%d", matrix.length, matrix.length));
        }
        this.m = matrix;
    }

    public static void print(int[][] matrix){
        int sizePerNum = 5;
        var horizontalDiv = "-".repeat(matrix.length * (sizePerNum + 1) + 1);
        System.out.printf("%dx%d Matrix:\n", matrix.length, matrix.length);
        System.out.println(horizontalDiv);
        var formatString = "|%" + sizePerNum + "d";

        // Print values
        for(int i = 0; i < matrix.length; i++){
            if(matrix.length != matrix[i].length) {
                System.out.printf("Matrix must have be quadratic! E.g.: %dx%d", matrix.length, matrix.length);
                return;
            }
            // print row
            for(int j = 0; j < matrix.length; j++){
                System.out.printf(formatString, matrix[i][j]);
            }
            System.out.println("|");
        }
        System.out.println(horizontalDiv);
    }

    public void print(){
        if(this.m == null) {
            System.out.println("Please init matrix first by calling init()");
            return;
        }
        print(this.m);
    }

    /**
     * Creates dim x dim matrix and initializes it from user input.
     * @param dim d
     */
    public void input(int dim){
        this.init(dim);
        this.input();
    }

    public void input(){
        if(this.m == null) {
            System.out.println("Please init matrix first by calling init()");
            return;
        }
        // make sure matrix is initialized with zeroes
        this.init(this.m.length);
        System.out.println("""
        Please provide values when asked for them...
        - Press 'a' to abort (values will not be saved)
        - Press 'c' to leave values as they are (unentered values will be 0)
        
        [row][column]
        """);

        var sc = new Scanner(System.in);
        for(int i = 0; i < m.length; i++){
            // print row
            for(int j = 0; j < m.length; j++){
                System.out.printf("[%d][%d]: ", i + 1, j + 1);
                try {
                    var line = sc.nextLine();
                    switch (line){
                        case "a":
                            this.init(this.m.length);
                            System.out.println("Matrix filling has been aborted.");
                            this.print();
                            return;
                        case "c":
                            System.out.println("Matrix has successfully been filled.");
                            this.print();
                            return;
                    }
                    m[i][j] = Integer.parseInt(line);
                }
                catch (NumberFormatException e){
                    System.out.println("Please provide an integer value! Try again...");
                    //sc.nextLine();
                    // decrease j so same value will be asked for
                    j--;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Matrix has successfully been filled.");
        this.print();
    }

    public int[][] add(int[][] matrix){
        if(this.m == null) throw new IllegalArgumentException("Please init matrix first by calling init()");
        if(matrix.length != m.length) throw new IllegalArgumentException(String.format("Matrix must have dimensions %dx%d", this.m.length, this.m.length));

        var result = new int[m.length][m.length];
        System.out.println("Adding two matrices:");
        print(m);
        System.out.println(" ".repeat((m.length * 5) >> 1) + "+");
        print(matrix);

        for(int i = 0; i < m.length; i++){
            if(matrix[i].length != m.length) throw new IllegalArgumentException(String.format("Matrix must have be quadratic! E.g.: %dx%d", this.m.length, this.m.length));
            for(int j = 0; j < m.length; j++) result[i][j] = m[i][j] + matrix[i][j];
        }
        System.out.println("Result:");
        print(result);
        return result;
    }

    public int[][] multiply(int[][] matrix){
        if(this.m == null) throw new IllegalArgumentException("Please init matrix first by calling init()");
        if(matrix.length != m.length) throw new IllegalArgumentException(String.format("Matrix must have dimensions %dx%d", this.m.length, this.m.length));

        var result = new int[m.length][m.length];
        System.out.println("Multiplying two matrices:");
        print(m);
        System.out.println(" ".repeat((m.length * 5) >> 1) + "+");
        print(matrix);

        for(int i = 0; i < m.length; i++){
            if(matrix[i].length != m.length) throw new IllegalArgumentException(String.format("Matrix must have be quadratic! E.g.: %dx%d", this.m.length, this.m.length));
            for(int j = 0; j < m.length; j++) {
                int sum = 0;
                for(int k = 0; k < m.length; k++){
                   sum += m[i][k] * matrix[k][j];
                }
                result[i][j] = sum;
            }
        }
        System.out.println("Result:");
        print(result);
        return result;
    }
}
