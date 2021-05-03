package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.transfer.Transfer;

public class Account {

    private final String uid;
    private final Holder holder;

    private final AccountType type;
    private final double balance;

    public Account(String uid, Holder holder, AccountType type, double balance) {
        this.uid = uid;
        this.holder = holder;
        this.type = type;
        this.balance = balance;
    }

    public Account debit(Transfer transfer) {
		return new Account(this.uid, this.holder, this.type, (this.balance - transfer.getAmount()));
	}
	
	public Account credit(Transfer transfer) {
		return new Account(this.uid, this.holder, this.type, (this.balance + transfer.getAmount()));
	}
     
    public String getUid() {
        return uid;
    }

    public Holder getHolder() {
        return holder;
    }

    public AccountType getCategory() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

}
