package hand.market;

import hand.agent.Buyer;
import hand.agent.Seller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Pair<K,V> {
    public K key;
    public V value;
    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

public class Market {
    public ArrayList<Buyer> buyers;
    public ArrayList<Seller> sellers;

    public Market(int nb, ArrayList<Double> fb, int ns, ArrayList<Double> fs) {
        buyers = createBuyers(nb, fb);
        sellers = createSellers(ns, fs);
    }
    
    private ArrayList<Buyer> createBuyers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        ArrayList<Buyer> buy = new ArrayList<>();
        for (int i =1; i<= n; i++){
            double x = i/(double) n;
            double m = 1;
            double v = 0;
            for (int j =0; j < f.size(); j++){
                v += f.get(j) * m;
                m *= x;
            }
            buy.add(i-1, new Buyer(v));
        }
        return buy;
    }


    private ArrayList<Seller> createSellers(int n, ArrayList<Double> f) {
        //TODO: Problem 2.3
        ArrayList<Seller> sell = new ArrayList<>();
        for (int i =1; i<= n; i++){
            double x = i/(double) n;
            double m = 1;
            double v = 0;
            for (int j =0; j < f.size(); j++){
                v += f.get(j) * m;
                m *= x;
            }
            sell.add(i-1, new Seller(v));
        }
        return sell;
    }

    private ArrayList<Pair<Seller, Buyer>> matchedPairs(int day, int round) {
        ArrayList<Seller> shuffledSellers = new ArrayList<>(sellers);
        ArrayList<Buyer> shuffledBuyers = new ArrayList<>(buyers);
        Collections.shuffle(shuffledSellers, new Random(71 * day + 43 * round + 7));
        Collections.shuffle(shuffledBuyers, new Random(67 * day + 29 * round + 11));
        ArrayList<Pair<Seller, Buyer>> pairs = new ArrayList<>();
        for (int i = 0; i < shuffledBuyers.size(); i++) {
            if (i < shuffledSellers.size()) {
                pairs.add(new Pair<>(shuffledSellers.get(i), shuffledBuyers.get(i)));
            }
        }
        return pairs;
    }

    public double simulate() {
        //TODO: Problem 2.2 and 2.3
        double sum = 0;
        int transact = 0;
        for (int day = 1; day <= 3000; day++) { // do not change this line
            for (int round = 1; round <= 5; round++) { // do not change this line
                ArrayList<Pair<Seller, Buyer>> pairs = matchedPairs(day, round); // do not change this line
                for (int i = 0; i < pairs.size(); i++){
                    if (pairs.get(i).value.willTransact(pairs.get(i).key.getExpectedPrice())  && !pairs.get(i).key.hadTrans()){
                        pairs.get(i).value.makeTransaction();
                        pairs.get(i).key.makeTransaction();
                        if (day == 3000){
                            sum += pairs.get(i).key.getExpectedPrice();
                            transact++;
                        }
                    }
                }
            }
            for (int j = 0; j <buyers.size(); j++){
                buyers.get(j).reflect();
            }
            for (int k = 0; k < sellers.size(); k++){
                sellers.get(k).reflect();
            }
        }
        if (transact !=0){
            return sum/transact;
        } else{
            return 0;
        }
    }
}

