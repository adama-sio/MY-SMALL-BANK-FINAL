package fr.paris8.iutmontreuil.mysmallbank.transfer;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.AccountService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.AccountType;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.common.ValidationError;
import fr.paris8.iutmontreuil.mysmallbank.common.exception.ValidationErrorException;
import fr.paris8.iutmontreuil.mysmallbank.transfer.TransferRepository;
@Service
public class TransferService {

    // TODO
    /*
        Perform a money transfer from an account to another one
        You must validate the transfer:
         - Both account must exist
         - Debit account balance should but greater than its minimum balance AFTER applying the transfer
         - Transfer minimum amount is 1€

     */
	private final TransferRepository transferRepository;
	private final AccountService accountService;

    public TransferService(TransferRepository transferRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }
    
	public Transfer createTransfer(Transfer transfer) {
		Account accountFrom = this.accountService.getAccount(transfer.getAccountIdFrom());
		List<ValidationError> validationErrors = verificationTransfert(accountFrom, transfer);
	    if (!validationErrors.isEmpty()) {
	    	throw new ValidationErrorException(validationErrors);
	    }
		Account accountTo = this.accountService.getAccount(transfer.getAccountIdTo());
        Account newaccountFrom = accountFrom.debit(transfer);
    	this.transferRepository.save(newaccountFrom);
    	Account newaccountTo = accountTo.credit(transfer);
    	this.transferRepository.save(newaccountTo);
    	return this.transferRepository.create(transfer);
	}

	public List<ValidationError> verificationTransfert(Account accountFrom, Transfer transfer) {
		List<ValidationError> listError = new ArrayList<>();
		
		if(transfer.getAccountIdFrom().equals(transfer.getAccountIdTo())) {
			ValidationError e = new ValidationError("Account Sender is same to account receiver");
			listError.add(e);
		}
		if(!(this.transferRepository.accountExist(transfer.getAccountIdFrom()))) {
			ValidationError e = new ValidationError("Account Sender does not exist");
			listError.add(e);
		}
		if(!(this.transferRepository.accountExist(transfer.getAccountIdTo()))) {
			ValidationError e = new ValidationError("Account Receiver does not exist");
			listError.add(e);
		}
		if(transfer.getAmount() < 1) {
			ValidationError e = new ValidationError("Amount might be upper or egal 1");
			listError.add(e);
		}
		if(accountFrom.getCategory().equals(AccountType.PEL)) {
			if(accountFrom.getBalance() - transfer.getAmount() < 300) {
				ValidationError e = new ValidationError("Balance is not enough for PEL");
				listError.add(e);
			}
		}
		if(accountFrom.getCategory().equals(AccountType.TRANSACTION)) {
			if(accountFrom.getBalance() - transfer.getAmount() < -1000) {
				ValidationError e = new ValidationError("Balance is not enough for TRANSACTION");
				listError.add(e);
			}
		}
		if(accountFrom.getCategory().equals(AccountType.SAVINGS)) {
			if(accountFrom.getBalance() - transfer.getAmount() < 0) {
				ValidationError e = new ValidationError("Balance is not enough for SAVINGS");
				listError.add(e);
			}
		}
		return listError;
	}
	
	public List<Transfer> getAllBy(Order order){
		Sort sort;
		if(order.equals(Order.asc("executiondate"))) {
			sort = Sort.by(Sort.Direction.ASC, "executiondate");
			return transferRepository.listTransfers(sort);
		}
		sort = Sort.by(Sort.Direction.DESC, "executiondate");
		return transferRepository.listTransfers(sort);
	}
	
}
