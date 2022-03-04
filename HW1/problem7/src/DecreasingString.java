public class DecreasingString {
    public static void printLongestDecreasingSubstringLength(String inputString) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
       int[] arr = new int[inputString.length()];
       int sum = 0;
       int max = 0;
        for(int i=0; i<inputString.length();i++){
            arr[i] = inputString.charAt(i);
        }
        for(int j=0; j<inputString.length()-1;j++){
            if(arr[j]>arr[j+1]){
                sum++;
                if(sum>=max){
                    max = sum;
                }
            } else {
                sum = 0;
            }
        }
        System.out.println(max+1);
    }
}
