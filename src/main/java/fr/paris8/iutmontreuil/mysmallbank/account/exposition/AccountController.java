package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.AccountService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountDTO> listAllAccounts() { 
        List<Account> accounts = accountService.listAllAccount();
        return AccountMapper.toDTOs(accounts);
    }
 
    
    @GetMapping("/{account-uid}")
    public AccountDTO getAccount(@PathVariable("account-uid") String accountUid) {
    	Account a = accountService.getAccount(accountUid); 
        return AccountMapper.toAccountDTO(a);
    }
    

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        // TODO
        /*
            Transform the AccountDTO to Account object with the AccountMapper and call the right of the service
         */
    	Account createdAccount = accountService.createAccounts(AccountMapper.toAccount(accountDTO));
    	AccountDTO res = AccountMapper.toAccountDTO(createdAccount);
        return ResponseEntity.created(new URI("/accounts/" + res.getUid())).build();
    } 

    @PostMapping("/batch")
    public List<AccountDTO> createAccount(@RequestBody List<AccountDTO> accountDTO) {
        List<Account> account = AccountMapper.toAccount(accountDTO);
        List<Account> createdAccount = accountService.createAccounts(account);
        return AccountMapper.toDTOs(createdAccount);
    }

    @DeleteMapping("/{account-uid}")
    public void deleteAccount(@PathVariable("account-uid") String accountUid) {
        this.accountService.delete(accountUid);
    }

}
