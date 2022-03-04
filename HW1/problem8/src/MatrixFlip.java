public class MatrixFlip {
    public static void printFlippedMatrix(char[][] matrix) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        char[][] arr = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                arr[i][j] = matrix[i][j];
            }
        }
        for (int i = matrix.length  - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
}
