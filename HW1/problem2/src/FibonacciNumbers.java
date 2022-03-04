public class FibonacciNumbers {
    public static void printFibonacciNumbers(int n) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
        int f1 = 0;
        int f2 = 1;
        int sum = 0;
        for(int i=0; i<n; i++){
            if (i<n-1){
                System.out.print(f1+ " ");
                sum += f1;
                int tmp = f1;
                f1 = f2;
                f2 = tmp+ f2;
            } else {
                System.out.print(f1);
                sum += f1;
            }
        }
        System.out.println();
        if(sum < 100000){
            System.out.println("sum" + " = " + sum);
        } else {
            String str = Integer.toString(sum);
            String str2 = str.substring(str.length()-5);
            System.out.println("last five digits of sum " + "= " + str2);
        }
    }
}
