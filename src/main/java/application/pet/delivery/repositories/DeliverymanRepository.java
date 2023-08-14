package application.pet.delivery.repositories;

import application.pet.delivery.entities.DeliveryMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliverymanRepository extends JpaRepository<DeliveryMan, Long> {
    /**
     * Retrieves a deliveryman by their email address.
     *
     * @param email The email address of the deliveryman.
     * @return An Optional containing the Deliveryman entity corresponding to the provided email, or an empty Optional if not found.
     */
    Optional<DeliveryMan> findByEmail(String email);
}
