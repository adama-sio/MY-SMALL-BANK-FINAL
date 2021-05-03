package fr.paris8.iutmontreuil.mysmallbank.transfer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.paris8.iutmontreuil.mysmallbank.account.infrastructure.HolderEntity;

@Repository
public interface TransferDAO extends JpaRepository<TransferEntity, String> {

}
