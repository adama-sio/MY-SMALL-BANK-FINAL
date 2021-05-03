package fr.paris8.iutmontreuil.mysmallbank.transfer;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Address;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransferMapper {
	
    private TransferMapper() { }

    public static TransferDTO toDTO(Transfer transfer) {
        return new TransferDTO(transfer.getId(), transfer.getAccountIdFrom(), transfer.getAccountIdTo(), transfer.getAmount());
    }
    
    public static Transfer toTransfer(TransferDTO transferDTO) {
    	return new Transfer(transferDTO.getFrom(), transferDTO.getTo(), transferDTO.getAmount());
    } 

    public static Account toAccount(AccountEntity accountEntity) {
    	return new Account(accountEntity.getUid(), toHolder(accountEntity.getHolder()), accountEntity.getAccountType(), accountEntity.getBalance());
    }

    public static AccountEntity toAccountEntity(Account account) {
    	AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(account.getBalance());
        accountEntity.setHolder(toHolderEntity(account.getHolder()));
        accountEntity.setAccountType(account.getCategory());
        accountEntity.setUid(account.getUid());
        return accountEntity;
    }

    public static HolderEntity toHolderEntity(Holder holder) {
    	HolderEntity holderEntity = new HolderEntity();
        holderEntity.setId(holder.getId());
        return holderEntity;
    } 
    
    private static List<Account> toAccount(List<AccountEntity> entities) {
        if(entities == null) {
            return Collections.emptyList();
        }
        // Call toAccount method with java 8 stream
        List<Account> list = entities.stream().map(TransferMapper::toAccount).collect(Collectors.toList());
        return list;
    }
    
    public static Holder toHolder(HolderEntity entity) {
    	Address address = new Address(entity.getStreet(),entity.getZipCode(),entity.getCity(),entity.getCountry());
        return new Holder(entity.getId(), entity.getLastName(), entity.getFirstName(), address, entity.getBirthDate(), toAccount(entity.getAccounts()));
    }

    public static List<AccountEntity> toAccountEntities(List<Account> accounts) {
        // Use java 8 streams
    	return accounts.stream().map(TransferMapper::toAccountEntity).collect(Collectors.toList());
    }
    
    public static TransferEntity toEntity(Transfer transfer) {
    	TransferEntity transferEntity = new TransferEntity();
    	transferEntity.setId(transfer.getId());
    	transferEntity.setUidFrom(transfer.getAccountIdFrom());
    	transferEntity.setUidTo(transfer.getAccountIdTo());
    	transferEntity.setAmount(transfer.getAmount());
    	transferEntity.setExecutionDate(transfer.getExecutionDate());
    	return transferEntity;
    }
    
    public static Transfer toTransfer(TransferEntity transferEntity) {
    	return new Transfer(transferEntity.getId(), transferEntity.getUidFrom(), transferEntity.getUidTo(), transferEntity.getAmount(), transferEntity.getExecutionDate());
    }

}
