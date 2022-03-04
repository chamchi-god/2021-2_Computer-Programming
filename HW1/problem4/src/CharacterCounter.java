public class CharacterCounter {
    public static void countCharacter(String str) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        int[] cin = new int[52];
        for (int i = 65; i <= 90; i++) {
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == i) {
                    cin[i - 65]++;
                }
            }
        }
        for (int i = 97; i <= 122; i++) {
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == i) {
                    cin[i - 71]++;
                }
            }
        }
        for (int i = 0; i < 26; i++) {
            if (cin[i] != 0) {
                System.out.printf("%c: %d times", (char) (i + 65), cin[i]);
                if (cin[i+26] != 0) {
                    System.out.print(", ");
                    printCount((char) (i + 97), cin[i+26]);
                } else {
                    System.out.println();
                }
            } else if (cin[i] == 0 && cin[i+26] != 0) {
                printCount((char) (i + 97), cin[i+26]);
            }
        }
    }

    private static void printCount(char character, int count) {
        System.out.printf("%c: %d times\n", character, count);
    }
}




