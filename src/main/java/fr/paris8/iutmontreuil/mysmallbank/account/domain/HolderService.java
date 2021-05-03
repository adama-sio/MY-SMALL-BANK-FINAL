package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HolderService {

    private final HolderRepository holderRepository;

    public HolderService(HolderRepository holderRepository) {
        this.holderRepository = holderRepository;
    }

    public Holder getHolder(String id) {
        return holderRepository.getHolder(id);
    }

    public List<Holder> listHolders() {
        return holderRepository.listHolders();
    }

    public Holder create(Holder holder) {
        List<ValidationError> validationErrors = validateHolder(holder);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }

        return holderRepository.create(holder);
    }
    
    public void delete(String id) {
    	if(this.holderRepository.holderExist(id)) {
    		Holder holder = getHolder(id);
    		if(holder.getAccounts().size() == 0) {
    			this.holderRepository.delete(HolderMapper.toEntity(holder));
    		}
    	}
    }

    public Holder updateAddress(String id, Address address) {
    	Holder oldHolder = getHolder(id);
    	Holder newHolder = oldHolder.updateAddress(address);
    	return holderRepository.save(newHolder);
    }
    
    public Holder updateHolder(String id, Holder holder) {
    	Holder oldHolder = getHolder(id);
    	Holder newHolder = oldHolder.merge(holder);
    	return holderRepository.save(newHolder);
    }
    
    private List<ValidationError> validateHolder(Holder holder) {
    	List<ValidationError> listError = new ArrayList<>();
    	List<ValidationError> listErrorAddress = validateAddress(holder.getAddress());
    	LocalDate date = LocalDate.now();
    	if(holder.getFirstName() == null || holder.getFirstName().equals("null") || holder.getFirstName().equals("")) {
    		ValidationError e = new ValidationError("First Name is missing");
    		listError.add(e);
    	}
    	if(holder.getLastName() == null || holder.getLastName().equals("null") || holder.getLastName().equals("")) {
    		ValidationError e = new ValidationError("Last Name is missing");
    		listError.add(e);
    	}
    	if(holder.getBirthDate() == null || holder.getBirthDate().equals("null") || holder.getBirthDate().equals("")) {
    		ValidationError e = new ValidationError("Birth Date is missing");
    		listError.add(e);
    	}
    	if(!(date.getYear() - holder.getBirthDate().getYear() >= 18)) {
    		ValidationError e = new ValidationError("Holder is not in age");
        	listError.add(e);
    	}
    	if(date.getYear() - holder.getBirthDate().getYear() == 18) {
    		if(date.getMonth().compareTo(holder.getBirthDate().getMonth()) < 0) {
    			ValidationError e = new ValidationError("Holder is not in age");
            	listError.add(e);
        	}
    		else {
    			if(date.getMonth().compareTo(holder.getBirthDate().getMonth()) == 0) {
    				if(date.getDayOfMonth() < holder.getBirthDate().getDayOfMonth()) {
        				ValidationError e = new ValidationError("Holder is not in age");
                    	listError.add(e);
        			}
    			}
    		}
    	}
    	
    	if(listErrorAddress == null) {
        	return listError;
        }
        else {
            for(ValidationError erreurAddress : listErrorAddress) {
            	 ValidationError newErrorAddress = new ValidationError(erreurAddress.getMessage());
            	 listError.add(newErrorAddress);
            }
        }
    	
        return listError;
    }
    
    public List<ValidationError> validateAddress(Address address) {
        // TODO
    	List<ValidationError> listError = new ArrayList<>();
    	
        if (address.getCity() == null || address.getCity().equals("") || address.getCity().equals("null")) {     
            ValidationError e1 = new ValidationError("City is missing");
            listError.add(e1);     
        }
        if (address.getCountry() == null || address.getCountry().equals("") || address.getCountry().equals("null")) {     
            ValidationError e2 = new ValidationError("Country is missing");
            listError.add(e2);     
        }
        if (address.getStreet() == null || address.getStreet().equals("") || address.getStreet().equals("null")) {     
            ValidationError e3 = new ValidationError("Street is missing");
            listError.add(e3);     
        }
        if (address.getZipCode() == null || address.getZipCode().equals("") || address.getZipCode().equals("null")) {     
            ValidationError e4 = new ValidationError("ZipCode is missing");
            listError.add(e4);     
        }
        
        if (listError.size() == 0) {
        	return null;
        }
        else {
        	return listError;
        }
    }
    
    

}
