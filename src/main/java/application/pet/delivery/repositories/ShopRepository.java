package application.pet.delivery.repositories;

import application.pet.delivery.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Shop entities.
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
