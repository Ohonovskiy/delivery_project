package application.pet.delivery.services;

import application.pet.delivery.DTO.ManufacturerDTO;
import application.pet.delivery.entities.Manufacturer;
import application.pet.delivery.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class for managing Manufacturer entities.
 */
@Service
@Transactional
public class ManufacturerService {

    private final ManufacturerRepository repository;

    @Autowired
    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a manufacturer entity.
     *
     * @param manufacturer The Manufacturer entity to be saved.
     */
    public void save(Manufacturer manufacturer){
        repository.save(manufacturer);
    }

    /**
     * Removes a manufacturer entity.
     *
     * @param manufacturer The Manufacturer entity to be removed.
     */
    public void remove(Manufacturer manufacturer){
        repository.delete(manufacturer);
    }

    /**
     * Removes a manufacturer entity by its ID.
     *
     * @param id The ID of the Manufacturer entity to be removed.
     */
    public void remove(Long id){
        repository.deleteById(id);
    }

    /**
     * Deletes all manufacturer entities.
     */
    public void deleteAll(){
        repository.deleteAll();
    }

    /**
     * Retrieves a reference to a manufacturer entity by its ID.
     *
     * @param id The ID of the Manufacturer entity.
     * @return A reference to the Manufacturer entity.
     */
    public Manufacturer getById(Long id){
        return repository.getReferenceById(id);
    }

    /**
     * Retrieves a manufacturer entity by its name.
     *
     * @param name The name of the manufacturer.
     * @return The Manufacturer entity corresponding to the provided name, or null if not found.
     */
    public Manufacturer getByName(String name){
        return repository.getManufacturerByName(name);
    }

    /**
     * Converts a Manufacturer entity to a ManufacturerDTO.
     *
     * @param manufacturer The Manufacturer entity to be converted.
     * @return A ManufacturerDTO representing the Manufacturer entity.
     */
    public static ManufacturerDTO convertManufacturerToDTO(Manufacturer manufacturer) {
        ManufacturerDTO manufacturerDTO = new ManufacturerDTO();
        manufacturerDTO.setId(manufacturer.getId());
        manufacturerDTO.setName(manufacturer.getName());

        return manufacturerDTO;
    }
}
