package application.pet.delivery.services;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.repositories.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class DeliveryManService {
    private final DeliverymanRepository deliverymanRepository;

    @Autowired
    public DeliveryManService(DeliverymanRepository deliverymanRepository) {
        this.deliverymanRepository = deliverymanRepository;
    }

    @Transactional(readOnly = true)
    public Optional<DeliveryMan> getById(Long id){
        return deliverymanRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DeliveryMan> getAll(){
        return deliverymanRepository.findAll();
    }

    public void save(DeliveryMan deliveryMan){
        deliverymanRepository.save(deliveryMan);
    }
    public void remove(Long id){
        deliverymanRepository.deleteById(id);
    }
    public void remove(DeliveryMan deliveryMan){
        deliverymanRepository.delete(deliveryMan);
    }

    public void removeAll(){
        deliverymanRepository.deleteAll();
    }

    public Optional<DeliveryMan> getByEmail(String email) {
        return deliverymanRepository.findByEmail(email);
    }
}
