public class NumberCounter {
    public static void countNumbers(String str0, String str1, String str2) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
        int num = Integer.parseInt(str0) * Integer.parseInt(str1) * Integer.parseInt(str2);
        String stn = Integer.toString(num);
        int[] arr = new int[10];
        for (int i = 48; i <= 57; i++) {
            for (int j = 0; j < stn.length(); j++) {
                if (stn.charAt(j) == i) {
                    arr[i - 48]++;
                }
            }
        }
        int k =0;
        for (int i = 0; i < 10; i++) {
            if (arr[i] != 0) {
                printNumberCount(i, arr[i]);
                k += arr[i];
            }
        }
        System.out.println("length: " + k);
    }

    private static void printNumberCount(int number, int count) {
        System.out.printf("%d: %d times\n", number, count);
    }
}
