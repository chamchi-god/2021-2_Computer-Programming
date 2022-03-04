public class PrimeNumbers {
    public static void printPrimeNumbers(int m, int n) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
        if(m==2){
            System.out.print(2+ " ");
            for(int i = m+1; i <= n; i++){
                int[] arr = new int[n-m];
                for(int j = 2; j < i; j++){
                    if(i%j != 0){
                        arr[i-m-1]++;
                    }
                }
                for (int k=0; k<=n-m-1;k++){
                    if (arr[k] == i-2){
                        System.out.print(k+m+1 +" ");
                    }
                }
            }
        } else {
            for(int i = m; i <= n; i++){
                int[] arr = new int[n-m+1];
                for(int j = 2; j < i; j++){
                    if(i%j != 0){
                        arr[i-m]++;
                    }
                }
                for (int k=0; k<=n-m;k++){
                    if (arr[k] == i-2){
                        System.out.print(k+m+" ");
                    }
                }
            }
        }

    }
}
