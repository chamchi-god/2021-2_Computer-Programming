package hand.agent;

public class Buyer extends Agent {

    public Buyer(double maximumPrice) {
        super(maximumPrice);
    }

    @Override
    public boolean willTransact(double price) {
        //TODO: Problem 2.1
        if(hadTransaction == false && price <= expectedPrice){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public void reflect() {
        //TODO: Problem 2.1
        if (hadTransaction == true){
            expectedPrice -= adjustment;
            if(adjustment<= adjustmentLimit-5){
                adjustment += 5;
            } else {
                adjustment = adjustmentLimit;
            }
        } else {
            if(expectedPrice <= priceLimit -adjustment){
            expectedPrice += adjustment;
            if (adjustment<5){
                adjustment = 0;
            } else{
                adjustment -=5;
            }
        } else {
                expectedPrice = priceLimit;
            }
        }
        hadTransaction = false;
    }
}
