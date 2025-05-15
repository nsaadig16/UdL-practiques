package Parcial20_21;
import acm.program.*;

import java.util.StringTokenizer;

public class AccountsProgram extends CommandLineProgram{

    private AccountsDB accountsDB;
    private int numErrors;
    public void run() {
        initializeResults();
        readTransfers();
    }
    private void initializeResults() {
        accountsDB = loadAccounts();
        numErrors = 0;
    }

    private void readTransfers(){
        String userLine = readLine("Enter your transfers: ");
        StringTokenizer transfers = new StringTokenizer(userLine);
        while (transfers.hasMoreTokens()){
            String transfer = transfers.nextToken();
            StringTokenizer transferParts = new StringTokenizer(transfer,"#");
            String idFrom = transferParts.nextToken();
            String idTo = transferParts.nextToken();
            int amount = Integer.parseInt(transferParts.nextToken());
            if(!accountsDB.transfer(idFrom,idTo,amount)){
                numErrors += 1;
            }
        }
        String accountToShow = readLine("Which account would you like to see? ");
        int accountAmount = accountsDB.getAccount(accountToShow).getBalance();
        print("The balance of " + accountToShow + " is " + accountAmount);
        println(" and there's been " + numErrors + " errors.");
    }

    public static void main(String[] args) {
        new AccountsProgram().start(args);
    }

    /*
    These methods DON'T have to be implemented
    -------------------------------------------------
     */
    private AccountsDB loadAccounts() {
        AccountsDB database = new AccountsDB(3, 1);
        database.addAccount("CC1", 500);
        database.addAccount("CA4", 300);
        database.addAccount("CI21", 0);
        return database;
    }


}
