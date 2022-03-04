public class DrawingFigure {
    public static void drawFigure(int n) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.
        for(int i=0; i<2*n+1; i += 2){
            for(int j=2*n; j> i; j -= 2){
                System.out.print("  ");
            }
            for(int k=0; k<=i; k++){
                if(k==i){
                    System.out.print("*");
                } else {
                    System.out.print("* ");
                }
            }
            for(int j=2*n; j> i; j -= 2){
                System.out.print("  ");
            }
            System.out.println();
        }
        for(int i=0; i<2*n-1; i += 2){
            for(int j=2*n; j>=2*n-i; j -= 2){
                System.out.print("  ");
            }
            for(int k=2*n-2-i; k>=0; k--){
                if(k==0){
                    System.out.print("*");
                } else {
                    System.out.print("* ");
                }
            }
            for(int j=2*n; j>=2*n-i; j -= 2) {
                System.out.print("  ");
            }
            System.out.println();
        }

    }
}
