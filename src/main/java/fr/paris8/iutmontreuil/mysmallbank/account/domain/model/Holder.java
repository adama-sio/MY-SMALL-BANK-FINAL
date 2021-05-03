package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Holder {

    private final String id;
    private final String lastName;
    private final String firstName;
    private final Address address;
    private final LocalDate birthDate;

    private final List<Account> accounts;


    public Holder(String id, String lastName, String firstName, Address address, LocalDate birthDate, List<Account> accounts) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.birthDate = birthDate;
        this.accounts = accounts;
    }
    
    public Holder updateAddress(Address address) {
    	return new Holder(this.id, this.lastName, this.firstName, address, this.birthDate, this.accounts);
    }
    
    public Holder merge(Holder Newholder) {
    	String newId, newLastName, newFirstName;
    	Address newAddress;
    	LocalDate newBirthDate;
    	List<Account> newAccounts;
    	if(Newholder.id!=null) {
    		newId = Newholder.id;
    	}else {
    		newId = this.id;
    	}
    	
    	if(Newholder.lastName!=null) {
    		newLastName = Newholder.lastName;
    	}else {
    		newLastName = this.lastName;
    	}
    	
    	if(Newholder.firstName!=null) {
    		newFirstName = Newholder.firstName;
    	}else {
    		newFirstName = this.firstName;
    	}
    	
    	newAddress = this.address.Merge(Newholder.address);
    	
    	if(Newholder.birthDate!=null) {
    		newBirthDate = Newholder.birthDate;
    	}
    	else {
    		newBirthDate = this.birthDate;
    	}
    	if(Newholder.accounts!=null) {
    		newAccounts = Newholder.accounts;
    	}
    	else {
    		newAccounts = this.accounts;
    	}
    	
    	return new Holder(newId, newLastName, newFirstName, newAddress, newBirthDate, newAccounts);
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
