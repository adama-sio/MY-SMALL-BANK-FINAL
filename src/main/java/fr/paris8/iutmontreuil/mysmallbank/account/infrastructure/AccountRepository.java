package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;

@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;
    private final HolderDAO holderDAO;

    public AccountRepository(AccountDAO accountDAO, HolderDAO holderDAO) {
        this.accountDAO = accountDAO;
        this.holderDAO = holderDAO;
    }

    // TODO: on the Holder Repository model
    public List<Account> listAccounts() {
        List<AccountEntity> all = accountDAO.findAll();
        return all.stream().map(AccountMapper::toAccount).collect(Collectors.toList());
    }
    
    public Account getAccount(String id) {
        AccountEntity account = accountDAO.getOne(id);
        return AccountMapper.toAccount(account); 
    }
    
    
    public Account create(Account account) {
        AccountEntity entityToSave = AccountMapper.toEntity(account);
        AccountEntity createdAccount = accountDAO.save(entityToSave);
        return AccountMapper.toAccount(createdAccount);
    }
    
    public void delete(AccountEntity account) {
    	accountDAO.delete(account);
    }
    
    public boolean holderExist(String id) {
    	boolean holderExist = holderDAO.existsById(id);
    	return holderExist;
    }
    
    public boolean accountExist(String id) {
    	return accountDAO.existsById(id);
    }
    
}
