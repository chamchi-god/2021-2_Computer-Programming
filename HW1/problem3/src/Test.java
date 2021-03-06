import java.util.Scanner;

public class Test {
    // DO NOT change anything in this file.
    public static void main(String[] args) {
        // TestCases
        System.out.println("/***** TestCase *****/");
        System.out.println("> After you implementation, the output should be same.");
        test(4);
        test(3);

        // Test your own inputs
        System.out.println("Enter your own inputs:");
        Scanner sc = new Scanner(System.in);
        int inputNumber = sc.nextInt();
        sc.close();

        DrawingFigure.drawFigure(inputNumber);
    }

    private static void test(int inputNumber) {
        System.out.println("---------- Input -----------");
        System.out.println(inputNumber);
        System.out.println("---------- Output ----------");
        DrawingFigure.drawFigure(inputNumber);
        System.out.println("\n----------------------------\n");
    }
}
