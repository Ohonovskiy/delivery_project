package application.pet.delivery.services;

import application.pet.delivery.entities.Shop;
import application.pet.delivery.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing shop-related operations.
 */
@Service
@Transactional
public class ShopService {
    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /**
     * Retrieves a shop optional object by its ID.
     *
     * @param id The ID of the shop.
     * @return The optional shop entity.
     */
    @Transactional(readOnly = true)
    public Optional<Shop> getById(Long id){
        return Optional.of(shopRepository.getReferenceById(id));
    }

    @Transactional(readOnly = true)
    public List<Shop> getAll(){
        return shopRepository.findAll();
    }

    /**
     * Removes a shop from the database.
     *
     * @param shop The shop entity to be removed.
     */
    public void remove(Shop shop){
        shopRepository.delete(shop);
    }

    /**
     * Removes a shop from the database based on its ID.
     *
     * @param id The ID of the shop to be removed.
     */
    public void remove(Long id){
        shopRepository.deleteById(id);
    }

    /**
     * Saves a shop to the database.
     *
     * @param shop The shop entity to be saved.
     */
    public void save(Shop shop){
        shopRepository.save(shop);
    }
}
