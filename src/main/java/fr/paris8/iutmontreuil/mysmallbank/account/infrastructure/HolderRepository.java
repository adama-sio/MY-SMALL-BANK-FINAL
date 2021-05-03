package fr.paris8.iutmontreuil.mysmallbank.account.infrastructure;

import fr.paris8.iutmontreuil.mysmallbank.account.HolderMapper;
import fr.paris8.iutmontreuil.mysmallbank.account.domain.model.Holder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HolderRepository {

    private final HolderDAO holderDAO;

    public HolderRepository(HolderDAO holderDAO) {
        this.holderDAO = holderDAO;
    }

    public Holder getHolder(String id) {
        HolderEntity holder = holderDAO.getOne(id);
        return HolderMapper.toHolder(holder); 
    }

    public List<Holder> listHolders() {
        List<HolderEntity> all = holderDAO.findAll();
        return all.stream().map(HolderMapper::toHolder).collect(Collectors.toList());
    }

    public Holder create(Holder holder) {
        HolderEntity entityToSave = HolderMapper.toEntity(holder);
        HolderEntity createdHolder = holderDAO.save(entityToSave);
        return HolderMapper.toHolder(createdHolder);
    }
    
    
    public Holder save(Holder holder) {
    	HolderEntity entityToSave = HolderMapper.toEntity(holder);
        HolderEntity updatedHolder = holderDAO.save(entityToSave);
        return HolderMapper.toHolder(updatedHolder);
    }
    
    public boolean holderExist(String id) {
    	boolean holderExist = holderDAO.existsById(id);
    	return holderExist;
    }
    
    public void delete(HolderEntity holder) {
    	holderDAO.delete(holder);;
    }

}
