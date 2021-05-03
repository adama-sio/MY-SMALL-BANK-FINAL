package fr.paris8.iutmontreuil.mysmallbank.account.exposition;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.HolderService;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.AddressDTO;
import fr.paris8.iutmontreuil.mysmallbank.account.exposition.dto.HolderDTO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/holders")
public class HolderController {

    private final HolderService holderService;

    public HolderController(HolderService holderService) {
        this.holderService = holderService;
    }

    @GetMapping
    public List<HolderDTO> findAll() {
    	List<Holder> holders = holderService.listHolders();
        return HolderMapper.toDto(holders);
    }
 
    @GetMapping("/{id}")
    public HolderDTO getOne(@PathVariable("id") String id) {
        Holder holder = holderService.getHolder(id);
        return HolderMapper.toDTO(holder);
    }

    @PostMapping
    public HolderDTO create(@RequestBody HolderDTO holderDTO) {
        Holder holder = holderService.create(HolderMapper.toHolder(holderDTO));
        return HolderMapper.toDTO(holder);
    }

    @PutMapping("/{id}/address")
    public HolderDTO updateAdress(@PathVariable("id") String id, @RequestBody AddressDTO address) {
    	Holder holder = holderService.updateAddress(id, HolderMapper.toAddress(address));
        return HolderMapper.toDTO(holder);
    }
    
    @PatchMapping("/{id}")
    public HolderDTO updateHolder(@PathVariable("id") String id, @RequestBody HolderDTO holderDTO) {
    	Holder holder = holderService.updateHolder(id, HolderMapper.toHolder(holderDTO));
        return HolderMapper.toDTO(holder);
    }
    
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") String id) {
        this.holderService.delete(id);
    }
} 
