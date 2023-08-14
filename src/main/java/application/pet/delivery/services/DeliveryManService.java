package application.pet.delivery.services;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.repositories.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryManService {
    private final DeliverymanRepository deliverymanRepository;

    @Autowired
    public DeliveryManService(DeliverymanRepository deliverymanRepository) {
        this.deliverymanRepository = deliverymanRepository;
    }

    public Optional<DeliveryMan> getById(Long id){
        return deliverymanRepository.findById(id);
    }

    public List<DeliveryMan> getAll(){
        return deliverymanRepository.findAll();
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
}
