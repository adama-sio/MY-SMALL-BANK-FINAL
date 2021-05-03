package fr.paris8.iutmontreuil.mysmallbank.account.domain;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.AccountType;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> listAllAccount() {
        return this.accountRepository.listAccounts();
    } 

    public Account getAccount(String accountUid) {
        return this.accountRepository.getAccount(accountUid);
    }

    public Account createAccounts(Account account) {

        // TODO validate account before creating:
        /*
            For accounts:
             - All account fields but id should be present
             - Minimum balance depending of account type
            For holders:
             - Holder is must be present
             - Holder must exist in database
         */
    	List<ValidationError> validationErrors = validate(account);
        if (!validationErrors.isEmpty()) {
            throw new ValidationErrorException(validationErrors);
        }

        return this.accountRepository.create(account);
    }

    public List<Account> createAccounts(List<Account> account) {

        // TODO validate account before creating:
        /*
            For accounts:
             - All account fields but id should be present
             - Minimum balance depending of account type
            For holders:
             - Holder is must be present
             - Holder must exist in database
         */

        return null;
    }

    public void delete(String accountUid) {
        // TODO
        // Can delete only if balance == 0
    	if(this.accountRepository.accountExist(accountUid)) {
    		Account account = getAccount(accountUid);
    		if(account.getBalance() > 0) {
    			this.accountRepository.delete(AccountMapper.toEntity(account));
    		}
    	}
    }


    private List<ValidationError> validate(Account account) {
    	List<ValidationError> listError = new ArrayList<>();

        if (account.getHolder().getId() == null) {
            ValidationError e = new ValidationError("Holder ID is missing");
            listError.add(e);
        }
        if (account.getHolder().getId().equals("") || account.getHolder().getId().equals("null")) {
            ValidationError e = new ValidationError("Holder ID is empty");
            listError.add(e);
        }
        if(!this.accountRepository.holderExist(account.getHolder().getId())) {
        	ValidationError e = new ValidationError("Holder ID does not exist");
            listError.add(e);
        }
        if (account.getCategory().equals(AccountType.TRANSACTION) && account.getBalance() < -1000) {
             ValidationError e = new ValidationError("TRANSFERT : MinimumBalance must be more than -1000");
             listError.add(e);
         } 
        if (account.getCategory().equals(AccountType.PEL) && account.getBalance() < 300) {
            ValidationError e = new ValidationError("PEL : MinimumBalance must be more than 300");
            listError.add(e);
        } 
        if (account.getCategory().equals(AccountType.SAVINGS) && account.getBalance() < 0) {
            ValidationError e = new ValidationError("SAVING : MinimumBalance must be more than 0");
            listError.add(e);
        }

        return listError;
    }

}
