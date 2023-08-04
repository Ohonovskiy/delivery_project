package application.pet.delivery.services;

import application.pet.delivery.entities.Manufacturer;
import application.pet.delivery.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManufacturerService {
    private final ManufacturerRepository repository;

    @Autowired
    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public void save(Manufacturer manufacturer){
        repository.save(manufacturer);
    }

    public void remove(Manufacturer manufacturer){
        repository.delete(manufacturer);
    }

    public void remove(Long id){
        repository.deleteById(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

    public Manufacturer getById(Long id){
        return repository.getReferenceById(id);
    }

    public Manufacturer getByName(String name){
        return repository.getManufacturerByName(name);
    }
}
