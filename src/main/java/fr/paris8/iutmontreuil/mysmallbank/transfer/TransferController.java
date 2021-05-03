package fr.paris8.iutmontreuil.mysmallbank.transfer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.paris8.iutmontreuil.mysmallbank.account.AccountMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Account;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AccountDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
@RestController
@RequestMapping("/transfers")
public class TransferController {


    // TODO
    /*
        Perform a money transfer from an account to another one
        You must validate the transfer:
         - Both account must exist
         - Debit account balance should but greater than its minimum balance AFTER applying the transfer
         - Transfer minimum amount is 1€

     */
	
	private final TransferService transferService;
	
	public TransferController(TransferService transferService) {	
		this.transferService = transferService;
	}

	@PostMapping
    public TransferDTO createTransfer(@RequestBody TransferDTO transferDTO){
        // TODO
        /*
            Transform the AccountDTO to Account object with the AccountMapper and call the right of the service
        */
    	Transfer createdTransfer = this.transferService.createTransfer(TransferMapper.toTransfer(transferDTO));
    	return TransferMapper.toDTO(createdTransfer); 
    } 
	/* Nous n'avons pas trouvé la solution à cette méthode
	@GetMapping("/{order}")
	public List<TransferDTO> getAllBy(@PathVariable("order") Sort.Order order){
		List<Transfer> list = this.transferService.getAllBy(order);
		return list.stream().map(TransferMapper::toDTO).collect(Collectors.toList());
	}
	*/
}
