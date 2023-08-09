package application.pet.delivery.repositories;

import application.pet.delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return An Optional containing the User entity corresponding to the provided email, or an empty Optional if not found.
     */
    Optional<User> findByEmail(String email);
}
