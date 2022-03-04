public class SquareTable {
    public static void printSquareTable(int n) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        int k = (int) Math.sqrt(n);
        for(int i=0;i<k; i++){
            printOneSquare(i+1, (i+1)*(i+1));
        }
    }

    private static void printOneSquare(int a, int b) {
        System.out.printf("%d times %d = %d\n", a, a, b);
    }
}
