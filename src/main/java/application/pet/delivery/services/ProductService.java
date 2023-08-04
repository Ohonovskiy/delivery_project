package application.pet.delivery.services;

import application.pet.delivery.entities.Product;
import application.pet.delivery.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public void save(Product product){
        repository.save(product);
    }
    public void remove(Product product){
        repository.delete(product);
    }
    public void remove(Long id){
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Product getById(Long id){
        return repository.getReferenceById(id);
    }

    public void deleteAll(){
        repository.deleteAll();
    }
}
