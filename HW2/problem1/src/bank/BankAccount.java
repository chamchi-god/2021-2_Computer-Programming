package bank;

import bank.event.*;

class BankAccount {
    private Event[] events = new Event[maxEvents];
    final static int maxEvents = 100;
    String id;
    String password;
    int balance;
    int i = 0;


    BankAccount(String id, String password, int balance) {
        //TODO: Problem 1.1
        this.id = id;
        this.password = password;
        this.balance = balance;
    }


    boolean authenticate(String password) {
        //TODO: Problem 1.1
        if (password.equals(this.password)){
            return true;
        } else {
            return false;
        }
    }

    void deposit(int amount) {
        //TODO: Problem 1.1
        balance += amount;
        events[i++] = new DepositEvent();
    }

    boolean withdraw(int amount) {
        //TODO: Problem 1.1
        if (balance >= amount){
            balance -= amount;
            events[i++] = new WithdrawEvent();
            return true;
        } else{
            return false;
        }
    }

    void receive(int amount) {
        //TODO: Problem 1.1
        balance += amount;
        events[i++] = new ReceiveEvent();
    }

    boolean send(int amount) {
        //TODO: Problem 1.1
        if (balance >= amount){
            balance -= amount;
            events[i++] = new SendEvent();
            return true;
        } else {
            return false;
        }
    }

    Event[] getevent() {
        Event[] revent = new Event[i];
        for (int j=0; j<i;j++){
            revent[j] = events[j];
        }
        return revent;
    }

}

