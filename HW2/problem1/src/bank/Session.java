package bank;

public class Session {

    private String sessionKey;
    private Bank bank;
    private boolean valid;
    private int transLimit = 3;
    private int work = 0;

    Session(String sessionKey,Bank bank){
        this.sessionKey = sessionKey;
        this.bank = bank;
        valid = true;
    }

    public boolean validation(){
        return valid;
    }

    public void unvalid(){
        valid = false;
    }

    public boolean deposit(int amount) {
        //TODO: Problem 1.2
        if (!valid){
            return false;
        } else {
            bank.deposit(sessionKey,amount);
            work++;
            if (work>=transLimit){
                valid = false;
            }
            return true;
        }
    }

    public boolean withdraw(int amount) {
        //TODO: Problem 1.2
        if (!valid){
            return false;
        } else {
            bank.withdraw(sessionKey,amount);
            work++;
            if (work>=transLimit){
                valid = false;
            }
            return true;
        }
    }

    public boolean transfer(String targetId, int amount) {
        //TODO: Problem 1.2
        if (!valid){
            return false;
        } else {
            work++;
            if (work>=transLimit){
                valid = false;
            }
            return bank.transfer(sessionKey,targetId, amount);
        }
    }
}
