package fr.paris8.iutmontreuil.mysmallbank.account.domain.model;

public class Address {

    private final String street;
    private final String zipCode;
    private final String city;
    private final String country;

    public Address(String street, String zipCode, String city, String country) {
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }
    
    public Address Merge(Address address) {
    	String newStreet, newZipCode, newCity, newCountry;
    	if(address != null && address.getStreet() != null) {
    		newStreet = address.getStreet();
    	}
    	else {
    		newStreet = this.street;
    	}
    	
    	if(address != null && address.zipCode != null) {
    		newZipCode = address.zipCode;
    	}
    	else {
    		newZipCode = this.zipCode;
    	}
    	
    	if(address != null && address.city != null) {
    		newCity = address.city;
    	}
    	else {
    		newCity = this.city;
    	}
    	
    	if(address != null && address.country != null) {
    		newCountry = address.country;
    	}
    	else {
    		newCountry = this.country;
    	}
    	
    	return new Address(newStreet, newZipCode, newCity, newCountry);
    }
    
    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
