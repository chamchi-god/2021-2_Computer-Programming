package bank;

import security.key.BankPublicKey;
import security.key.BankSymmetricKey;
import security.*;

public class MobileApp {

    private String randomUniqueStringGen() {
        return Encryptor.randomUniqueStringGen();
    }

    private final String AppId = randomUniqueStringGen();

    public String getAppId() {
        return AppId;
    }


    String id, password;

    public MobileApp(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public BankSymmetricKey symmetricKey = null;


    public Encrypted<BankSymmetricKey> sendSymKey(BankPublicKey publickey) {
        //TODO: Problem 1.3
        symmetricKey = new BankSymmetricKey(randomUniqueStringGen());
        Encrypted<BankSymmetricKey> encryptedSymkey = new Encrypted(symmetricKey, publickey);
        return encryptedSymkey;
    }


    public Encrypted<Message> deposit(int amount) {
        //TODO: Problem 1.3
        Message message = new Message("deposit", id, password, amount);
        Encrypted<Message> dipositKey = new Encrypted<Message>(message, symmetricKey);
        return dipositKey;
    }

    public Encrypted<Message> withdraw(int amount) {
        //TODO: Problem 1.3
        Message message = new Message("withdraw", id, password, amount);
        Encrypted<Message> withdrawKey = new Encrypted(message, symmetricKey);
        return withdrawKey;
    }

    public boolean processResponse(Encrypted<Boolean> obj) {
        //TODO: Problem 1.3
        if (obj == null) {
            return false;
        } else {
            if (obj.decrypt(symmetricKey) == null) {
                return false;
            } else {
                return obj.decrypt(symmetricKey);
            }
        }
    }
}

