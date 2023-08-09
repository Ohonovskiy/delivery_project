package application.pet.delivery.services;

import application.pet.delivery.entities.Product;
import application.pet.delivery.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing Product entities.
 */
@Service
@Transactional
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /**
     * Saves a product entity.
     *
     * @param product The Product entity to be saved.
     */
    public void save(Product product){
        repository.save(product);
    }

    /**
     * Removes a product entity.
     *
     * @param product The Product entity to be removed.
     */
    public void remove(Product product){
        repository.delete(product);
    }

    /**
     * Removes a product entity by its ID.
     *
     * @param id The ID of the Product entity to be removed.
     */
    public void remove(Long id){
        repository.deleteById(id);
    }

    /**
     * Deletes all product entities.
     */
    public void deleteAll(){
        repository.deleteAll();
    }

    /**
     * Retrieves a list of all product entities.
     *
     * @return A list of Product entities.
     */
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a reference to a product entity by its ID.
     *
     * @param id The ID of the Product entity.
     * @return A reference to the Product entity.
     */
    @Transactional(readOnly = true)
    public Product getById(Long id){
        return repository.getReferenceById(id);
    }
}
