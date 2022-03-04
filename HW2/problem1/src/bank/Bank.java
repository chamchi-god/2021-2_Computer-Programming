package bank;

import bank.event.*;
import security.*;
import security.key.*;

public class Bank {
    private int numAccounts = 0;
    final static int maxAccounts = 100;
    private BankAccount[] accounts = new BankAccount[maxAccounts];
    private String[] ids = new String[maxAccounts];

    public void createAccount(String id, String password) {
        createAccount(id, password, 0);
    }

    public void createAccount(String id, String password, int initBalance) {
        //TODO: Problem 1.1
        if(find(id) == null) {
            accounts[numAccounts] = new BankAccount(id, password, initBalance);
        }
        ids[numAccounts] = id;
        numAccounts++;
    }

    public boolean deposit(String id, String password, int amount) {
        //TODO: Problem 1.1
        if(find(id) != null && find(id).authenticate(password)){
            find(id).deposit(amount);
            return true;
        } else{
            return false;
        }
    }

    public boolean withdraw(String id, String password, int amount) {
        //TODO: Problem 1.1
        if(find(id) != null && find(id).authenticate(password)){
            return find(id).withdraw(amount);
        } else{
            return false;
        }
    }

    public boolean transfer(String sourceId, String password, String targetId, int amount) {
        //TODO: Problem 1.1
        if (find(sourceId) != null &&find(targetId) != null && find(sourceId).authenticate(password) && find(sourceId).send(amount)){
            find(targetId).receive(amount);
            return true;
        } else {
            return false;
        }
    }

    public Event[] getEvents(String id, String password) {
        //TODO: Problem 1.1
        if(find(id) != null && find(id).authenticate(password)){
            return find(id).getevent();
        } else {
            return null;
        }
    }

    public int getBalance(String id, String password) {
        //TODO: Problem 1.1
        if(find(id) != null && find(id).authenticate(password)){
            return find(id).balance;
        }
        return -1;
    }

    private static String randomUniqueStringGen(){
        return Encryptor.randomUniqueStringGen();
    }
    private BankAccount find(String id) {
        for (int i = 0; i < numAccounts; i++) {
            if(ids[i].equals(id)){return accounts[i];};
        }
        return null;
    }
    final static int maxSessionKey = 100;
    int numSessionKey = 0;
    String[] sessionKeyArr = new String[maxSessionKey];
    BankAccount[] bankAccountmap = new BankAccount[maxSessionKey];
    String generateSessionKey(String id, String password){
        BankAccount account = find(id);
        if(account == null || !account.authenticate(password)){
            return null;
        }
        String sessionkey = randomUniqueStringGen();
        sessionKeyArr[numSessionKey] = sessionkey;
        bankAccountmap[numSessionKey] = account;
        numSessionKey += 1;
        return sessionkey;
    }
    BankAccount getAccount(String sessionkey){
        for(int i = 0 ;i < numSessionKey; i++){
            if(sessionKeyArr[i] != null && sessionKeyArr[i].equals(sessionkey)){
                return bankAccountmap[i];
            }
        }
        return null;
    }

    boolean deposit(String sessionkey, int amount) {
        //TODO: Problem 1.2
        if(getAccount(sessionkey) != null) {
            getAccount(sessionkey).deposit(amount);
            return true;
        } else{
            return false;
        }
    }

    boolean withdraw(String sessionkey, int amount) {
        //TODO: Problem 1.2
        if (getAccount(sessionkey) != null && getAccount(sessionkey).balance >= amount){
            return getAccount(sessionkey).withdraw(amount);
        } else{
            return false;
        }
    }

    boolean transfer(String sessionkey, String targetId, int amount) {
        //TODO: Problem 1.2
        if (getAccount(sessionkey) != null && getAccount(sessionkey).send(amount) && find(targetId) != null){
            find(targetId).receive(amount);
            return true;
        } else {
            return false;
        }
    }

    private BankSecretKey secretKey;
    public BankPublicKey getPublicKey(){
        BankKeyPair keypair = Encryptor.publicKeyGen(); // generates two keys : BankPublicKey, BankSecretKey
        secretKey = keypair.deckey; // stores BankSecretKey internally
        return keypair.enckey;
    }

    int maxHandshakes = 10000;
    int numSymmetrickeys = 0;
    BankSymmetricKey[] bankSymmetricKeys = new BankSymmetricKey[maxHandshakes];
    String[] AppIds = new String[maxHandshakes];

    public int getAppIdIndex(String AppId){
        for(int i=0; i<numSymmetrickeys; i++){
            if(AppIds[i].equals(AppId)){
                return i;
            }
        }
        return -1;
    }


    public void fetchSymKey(Encrypted<BankSymmetricKey> encryptedKey, String AppId){
        //TODO: Problem 1.3
        int j =0;
        if (getAppIdIndex(AppId) != -1){
            j = 1;
            bankSymmetricKeys[getAppIdIndex(AppId)] = encryptedKey.decrypt(secretKey);
        }

        if (encryptedKey != null && encryptedKey.decrypt(secretKey) != null && j==0){
           bankSymmetricKeys[numSymmetrickeys] = encryptedKey.decrypt(secretKey);
           AppIds[numSymmetrickeys] = AppId;
           numSymmetrickeys++;
       }
    }

    public Encrypted<Boolean> processRequest(Encrypted<Message> messageEnc, String AppId) {
        //TODO: Problem 1.3
        int k =0;
        BankSymmetricKey lastKey = null;
        if (getAppIdIndex(AppId) != -1){
            k = 1;
            lastKey = bankSymmetricKeys[getAppIdIndex(AppId)];
        }
        boolean result;
             if(messageEnc != null && lastKey != null && k != 0){
                 result = false;
                 if (messageEnc.decrypt(lastKey).getRequestType() == "deposit"){
                     result = deposit(messageEnc.decrypt(lastKey).getId(),messageEnc.decrypt(lastKey).getPassword(), messageEnc.decrypt(lastKey).getAmount());
                 } else if (messageEnc.decrypt(lastKey).getRequestType() == "withdraw"){
                     result = withdraw(messageEnc.decrypt(lastKey).getId(),messageEnc.decrypt(lastKey).getPassword(), messageEnc.decrypt(lastKey).getAmount());
                 }
                 return new Encrypted<Boolean>(result, lastKey);
             } else{
                 return null;
             }

       }
    }