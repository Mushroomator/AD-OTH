public class Exercise03 {

    public static void main(String[] args) {
        var matrix = new Matrix();

        matrix.init(2);
        matrix.print();
        matrix.input();

        var test = new int[][]{
                { 1, 2},
                { 1, 2}
        };

        var addResult = matrix.add(test);
        var mulResult = matrix.multiply(test);
    }
}
