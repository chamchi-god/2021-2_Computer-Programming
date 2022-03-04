public class FractionalNumberCalculator {
	public static void printCalculationResult(String equation) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.

		int num10 = 0;
		int num1 = 0;
		int num2 = 0;
		int num30 = 0;
		int num3 = 0;
		int num4 = 0;

		String[] str =  equation.split(" ");
		String str1 = str[0];
		String str2 = str[1];
		String str3 = str[2];

		if (str1.contains("/")) {
			String[] str00 =  str1.split("/");
			String str21 = str00[0];
			String str22 = str00[1];
			num1 = Integer.parseInt(str21);
			num2 = Integer.parseInt(str22);
		} else {
			num10 = Integer.parseInt(str1);
		}

		if (str3.contains("/")) {
			String[] str01 =  str3.split("/");
			String str31 = str01[0];
			String str32 = str01[1];
			num3= Integer.parseInt(str31);
			num4 = Integer.parseInt(str32);
		} else {
			num30 = Integer.parseInt(str3);
		}

		switch (str2) {
			case "+": if (!str1.contains("/") && !str3.contains("/")) {
				System.out.println(num10 + num30);
			} else if (!str1.contains("/") && str3.contains("/")) {
				int num22 = num3 + num4 * num10;
				FractionalNumber.gcd(num22, num4);
			} else if (str1.contains("/") && !str3.contains("/")) {
				int num33 = num1 + num2 * num30;
				FractionalNumber.gcd(num33, num2);
			} else {
				int num44 = num1 * num4 + num2 * num3;
				FractionalNumber.gcd(num44, num2 * num4);
			}
			break;


			case "-": if (!str1.contains("/") && !str3.contains("/")) {
				System.out.println(num10 - num30);
			} else if (!str1.contains("/") && str3.contains("/")) {
				int num222 = num10 * num4 - num3;
				FractionalNumber.gcd(num222, num4);
			} else if (str1.contains("/") && !str3.contains("/")) {
				int num333 = num1 - num2 * num30;
				FractionalNumber.gcd(num333, num2);
			} else {
				int num444 = num1 * num4 - num2 * num3;
				FractionalNumber.gcd(num444, num2 * num4);
			}
				break;

			case "*": if (!str1.contains("/") && !str3.contains("/")) {
				System.out.println(num10 * num30);
			} else if (!str1.contains("/") && str3.contains("/")) {
				int num2222 = num10 * num3;
				FractionalNumber.gcd(num2222, num4);
			} else if (str1.contains("/") && !str3.contains("/")) {
				int num3333 = num1 * num30;
				FractionalNumber.gcd(num3333, num2);
			} else {
				FractionalNumber.gcd(num1 * num3, num2 * num4);
			}
				break;


			case "/": if (!str1.contains("/") && !str3.contains("/")) {
				FractionalNumber.gcd(num10, num30);
			} else if (str1.contains("/") && !str3.contains("/")) {
				FractionalNumber.gcd(num1, num2 * num30);
			} else if (!str1.contains("/") && str3.contains("/")) {
				FractionalNumber.gcd(num4 * num10, num3);
			} else {
				FractionalNumber.gcd(num1 * num4, num2 * num3);
			}
				break;

		}

	}
}


	class FractionalNumber {

		static void gcd(int numerator, int denominator) {
			if (numerator == 0) {
				System.out.println(0);
			} else {
				if (Math.abs(numerator) > Math.abs(denominator)) {
					if (gcd2(Math.abs(numerator), Math.abs(denominator)) == 1) {
						if(denominator / gcd2(Math.abs(numerator), Math.abs(denominator)) == 1){
							System.out.println(numerator);
						} else {
							System.out.println(numerator + "/" + denominator);
						}
					} else {
						if (denominator / gcd2(Math.abs(numerator), Math.abs(denominator)) == 1) {
							System.out.println(numerator / gcd2(Math.abs(numerator), Math.abs(denominator)));
						} else {
							int n = numerator / gcd2(Math.abs(numerator), Math.abs(denominator));
							int d = denominator / gcd2(Math.abs(numerator), Math.abs(denominator));
							System.out.println(n + "/" + d);
						}
					}
				}
				else if (Math.abs(numerator) == Math.abs(denominator)) {
						if (numerator >= 0) {
							System.out.println(1);
						} else {
							System.out.println(-1);
						}
					} else {
						if (gcd2(Math.abs(numerator), Math.abs(denominator)) == 1) {
							System.out.println(numerator + "/" + denominator);
						} else {
							if (denominator / gcd2(Math.abs(numerator), Math.abs(denominator)) == 1) {
								System.out.println(numerator / gcd2(Math.abs(numerator), Math.abs(denominator)));
							} else {
								int n = numerator / gcd2(Math.abs(numerator), Math.abs(denominator));
								int d = denominator / gcd2(Math.abs(numerator), Math.abs(denominator));
								System.out.println(n + "/" + d);
							}
						}

					}
				}

			}



		 static int gcd2(int a, int b) {
			while (b != 0) {
				int c = a % b;
				a = b;
				b = c;
			}
			return a;
		}
}

