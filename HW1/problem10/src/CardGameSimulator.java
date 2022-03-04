public class CardGameSimulator {
	private static final Player[] players = new Player[2];

	public static void simulateCardGame(String inputA, String inputB) {
		// DO NOT change the skeleton code.
		// You can add codes anywhere you want.

		Card[] deckA = new Card[10];
		Card[] deckB = new Card[10];

		for (int i=0; i<10;i++){
			deckA[i] = new Card(inputA.charAt(3*i)-48, inputA.charAt(3*i+1), 1);
			deckB[i] = new Card(inputB.charAt(3*i)-48, inputB.charAt(3*i+1), 1);
		}

		players[0] = new Player("A", deckA);
		players[1] = new Player("B", deckB);

		String firstCard = Card.First(players[0],deckA);
		int num = firstCard.charAt(0)-48;
		char chr = firstCard.charAt(1);

		int Winner;

		do {
			String aCard = Card.Then(players[1],deckB, num, chr);
			int num1 = aCard.charAt(0)-48;
			char chr1 = aCard.charAt(1);
			Winner = 0;

			if(num1 >= 10){
				break;
			} else{
				String bCard = Card.Then(players[0],deckA, num1, chr1);
				num = bCard.charAt(0)-48;
				chr = bCard.charAt(1);
				Winner = 1;
			}

		} while(num < 10);

		printWinMessage(players[Winner]);
	}

	private static void printWinMessage(Player player) {
		System.out.printf("Player %s wins the game!\n", player);
	}
}


class Player {
	private final String name;
	public final Card[] deck;

	Player(String name, Card[] deck){
		this.name = name;
		this.deck = deck;
	}

	public void playCard(Card card) {

		System.out.printf("Player %s: %s\n", name, card);
	}

	@Override
	public String toString() {
		return name;
	}
}


class Card {
	private final int number;
	private final char shape;
	private int remain;

	Card(int number, char shape, int remain){
		this.number = number;
		this.shape = shape;
		this.remain = remain;

	}

	static String First(Player p, Card[] deck){
		int max = -1;
		char shape0 = '0';
		for (int i=0; i<10; i++ ){
			if(deck[i].number> max){
				max = deck[i].number;
				shape0 = deck[i].shape;
			} else if (deck[i].number == max){
				if (deck[i].shape == 'O'){
					max = deck[i].number;
					shape0 = 'X';
				} else{
					max = deck[i].number;
					shape0 = deck[i].shape;
				}
			}
		}
		for (int h =0; h<10; h++){
			if (deck[h].number == max &&  deck[h].shape == shape0){
				p.playCard(deck[h]);
				deck[h].remain = 0;
			}
		}
		return "" + max + shape0;
	}

	static String Then(Player p, Card[] deck, int num0 , char chr0) {
		int max2 = -1;
		int f = 0;
		char shape1 = 'O';
		int num00 = 0;
		char chr00 = 'O';

		for (int i = 0; i < 10; i++) {
			if (deck[i].number == num0 && deck[i].remain == 1) {
				p.playCard(deck[i]);
				num00 = deck[i].number;
				chr00 = deck[i].shape;
				deck[i].remain = 0;
				f = 1;
			}
		}

		if (f == 0) {
			for (int j = 0; j < 10; j++) {
				if (deck[j].number > max2 && deck[j].remain == 1 && deck[j].shape == chr0) {
					max2 = deck[j].number;
				}
			}
			if (max2 == -1) {
				return "" + 'L' + chr0;
			} else {
				for (int k = 0; k < 10; k++) {
					if (deck[k].number == max2 && deck[k].shape == chr0) {
						p.playCard(deck[k]);
						shape1 = deck[k].shape;
						deck[k].remain = 0;
					}
				}
				num00 = max2;
				chr00 = shape1;
			}
		}

			return "" + num00 + chr00;
	}

	@Override
	public String toString() {
		return "" + number + shape;
	}
}
