package application.pet.delivery.services;

import application.pet.delivery.entities.Order;
import application.pet.delivery.enums.order.Status;
import application.pet.delivery.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(Order order){
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getById(Long id){
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllUnsignedOrders(){
        return orderRepository.findAllByDeliveryManIsNullAndStatusEquals(Status.WAITING);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllDoneOrders(){
        return orderRepository.findAllByStatusEquals(Status.DONE);
    }

    public void remove(Order order){
        order.getDeliveryMan().removeOrder();
        order.getUser().getOrders().remove(order);

        orderRepository.delete(order);
    }

    public void remove(Long id){
        orderRepository.deleteById(id);

    }
}
