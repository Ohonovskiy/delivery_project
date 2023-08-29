package application.pet.delivery.services;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.Order;
import application.pet.delivery.entities.Product;
import application.pet.delivery.enums.order.Status;
import application.pet.delivery.repositories.OrderRepository;
import application.pet.delivery.services.GeoUtils.googleMapsApi.DirectionsApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final DirectionsApiClient directionsApiClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, DirectionsApiClient directionsApiClient) {
        this.orderRepository = orderRepository;
        this.directionsApiClient = directionsApiClient;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllUnsignedOrders() {
        return orderRepository.findAllByDeliveryManIsNullAndStatusEquals(Status.WAITING);
    }

    @Transactional(readOnly = true)
    public Double calculateOrderPrice(Order order) {
        Double res = 0d;

        for (Product product : order.getProducts()) {
            res += product.getPrice();
        }

        return res;
    }

    public void remove(Order order) {
        order.getDeliveryMan().removeOrder();
        order.getUser().getOrders().remove(order);

        orderRepository.delete(order);
    }

    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    public HashMap<Order, String> getOrderAndOrderInfoMap(DeliveryMan deliveryMan) {

        HashMap<Order, String> orderOrderInfo = new HashMap<>();

        for(Order order : getAllUnsignedOrders()){
            String[] info = directionsApiClient.getTripInfo(deliveryMan, order);
            if(info.length >= 3){
                orderOrderInfo.put(order, info[3]);
            }
        }

        return orderOrderInfo;
    }
}
