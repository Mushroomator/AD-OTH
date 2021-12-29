public class AdjMatrixGraph {
    private int[][] adjMatrix;

    public AdjMatrixGraph(int noNodes) {
        this(new int[noNodes][noNodes]);
    }

    public AdjMatrixGraph(int[][] adjMatrix) {
        this.adjMatrix = adjMatrix;
    }



    /**
     * Print adjacent matrix.
     */
    public void print() {
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix[i].length; j++) {
                if (j == adjMatrix[i].length - 1) System.out.printf("| %d ", adjMatrix[i][j]);
                else System.out.printf(" %d |\n", adjMatrix[i][j]);
            }
        }
    }
}