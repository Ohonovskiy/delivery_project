package application.pet.delivery.repositories;

import application.pet.delivery.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Manufacturer entities.
 */
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    /**
     * Retrieves a manufacturer by its name.
     *
     * @param name The name of the manufacturer.
     * @return The Manufacturer entity corresponding to the provided name, or null if not found.
     */
    Manufacturer getManufacturerByName(String name);
}
