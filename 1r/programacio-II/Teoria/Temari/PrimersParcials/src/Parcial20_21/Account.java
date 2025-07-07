package Parcial20_21;

public class Account {

    private String id;
    private int balance;

    public Account (String idAccount, int initialBalance){
        this.id = idAccount;
        this.balance = initialBalance;
    }

    public String getId(){
        return this.id;
    }

    public int getBalance(){
        return this.balance;
    }

    public void deposit(int amount){
        this.balance += amount;
    }

    public boolean withdrawal(int amount){
        if(amount <= this.balance){
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
