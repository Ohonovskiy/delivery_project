package application.pet.delivery.repositories;

import application.pet.delivery.entities.Order;
import application.pet.delivery.enums.order.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Transactional(readOnly = true)
    List<Order> findAllByDeliveryManIsNullAndStatusEquals(Status status);
    @Transactional(readOnly = true)
    List<Order> findAllByStatusEquals(Status status);
}
