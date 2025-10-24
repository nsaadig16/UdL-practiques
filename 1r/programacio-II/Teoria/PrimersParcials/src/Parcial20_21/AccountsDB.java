package Parcial20_21;

public class AccountsDB {

    private Account[] database;
    private int numAcc;
    private final int sizeIncrement;

    public AccountsDB (int initialSize, int sizeIncrement){
        this.database = new Account[initialSize];
        this.numAcc = 0;
        this.sizeIncrement = sizeIncrement;
    }

    public Account getAccount (String id){
        for(int i = 0; i < this.numAcc; i++){
            if(this.database[i].getId().equals(id)){
                return this.database[i];
            }
        }
        return null;
    }

    public boolean addAccount(String id, int initialBalance){
        if( id == null || existsAccount(id) || initialBalance < 0){
            return false;
        }
        if(this.numAcc == this.database.length){
            widenDatabase();
        }
        this.database[numAcc] = new Account(id,initialBalance);
        this.numAcc += 1;
        return true;
    }

    private boolean existsAccount (String id){
        for(int i = 0; i < this.numAcc; i++){
            if(this.database[i].getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    private void widenDatabase(){
        Account[] newDB = new Account[this.database.length + sizeIncrement];
        for (int i = 0; i < newDB.length; i++){
            newDB[i] = this.database[i];
        }
        this.database = newDB;
    }

    public boolean transfer(String idFrom, String idTo, int amount){
        if(
                idFrom == null || idTo == null ||
                !existsAccount(idFrom) || !existsAccount(idTo) ||
                amount < 0 || amount > getAccount(idFrom).getBalance()
        ){
            return false;
        }
        getAccount(idFrom).withdrawal(amount);
        getAccount(idTo).deposit(amount);
        return true;
    }
}
