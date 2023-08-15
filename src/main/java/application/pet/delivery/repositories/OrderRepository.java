package application.pet.delivery.repositories;

import application.pet.delivery.entities.Order;
import application.pet.delivery.enums.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByDeliveryManIsNullAndStatusEquals(Status status);
}
