package fr.paris8.iutmontreuil.mysmallbank.transfer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountDAO;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.AccountEntity;
import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;

@Repository
public class TransferRepository {
	
	private final AccountDAO accountDAO;
	private final TransferDAO transferDAO;
	
	public TransferRepository(AccountDAO accountDAO, TransferDAO transferDAO) {
		this.accountDAO = accountDAO;
		this.transferDAO = transferDAO;
	}

	
	public boolean accountExist(String id) {
		return this.accountDAO.existsById(id);
	}
	
	public Transfer create(Transfer transfer) {
        TransferEntity entityToSave = TransferMapper.toEntity(transfer);
        TransferEntity createdTransfer = this.transferDAO.save(entityToSave);
        return TransferMapper.toTransfer(createdTransfer);
    }
	
	public Account save(Account account) {
        AccountEntity entityToSave = AccountMapper.toEntity(account);
        AccountEntity updatedAccount = accountDAO.save(entityToSave);
        return AccountMapper.toAccount(updatedAccount);
    }
	
	public List<Transfer> listTransfers(Sort sort){
		List<TransferEntity> list = this.transferDAO.findAll(sort);
		return list.stream().map(TransferMapper::toTransfer).collect(Collectors.toList()); 
	}
}
