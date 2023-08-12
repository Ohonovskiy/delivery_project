package application.pet.delivery.services;

import application.pet.delivery.DTO.ProductDTO;
import application.pet.delivery.DTO.UserDTO;
import application.pet.delivery.entities.User;
import application.pet.delivery.enums.Status;
import application.pet.delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing User entities.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a user entity, encrypting the password.
     *
     * @param user The User entity to be saved.
     */
    public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }

    /**
     * Removes a user entity.
     *
     * @param user The User entity to be removed.
     */
    public void remove(User user){
        repository.delete(user);
    }

    /**
     * Removes a user entity by its ID.
     *
     * @param id The ID of the User entity to be removed.
     */
    public void remove(Long id){
        repository.deleteById(id);
    }

    /**
     * Retrieves a reference to a user entity by its ID.
     *
     * @param id The ID of the User entity.
     * @return A reference to the User entity.
     */
    @Transactional(readOnly = true)
    public User getById(Long id){
        return repository.getReferenceById(id);
    }

    /**
     * Retrieves a user entity by email.
     *
     * @param email The email of the User entity.
     * @return An Optional containing the User entity, or empty if not found.
     */
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email){
        return repository.findByEmail(email);
    }

    /**
     * Retrieves a list of all user entities.
     *
     * @return A sorted list of User entities.
     */
    @Transactional(readOnly = true)
    public List<User> getAll(){
        List<User> users = repository.findAll();
        Collections.sort(users);
        return users;
    }

    /**
     * Deletes all user entities.
     */
    public void removeAll(){
        repository.deleteAll();
    }

    /**
     * Bans a user by changing their status to BANNED.
     *
     * @param user The User entity to be banned.
     */
    public void ban(User user){
        user.setStatus(Status.BANNED);
        save(user);
    }

    /**
     * Bans a user by ID, changing their status to BANNED.
     *
     * @param id The ID of the User entity to be banned.
     */
    public void ban(Long id){
        User user = getById(id);
        user.setStatus(Status.BANNED);
        save(user);
    }

    /**
     * Unbans a user by changing their status to ACTIVE.
     *
     * @param user The User entity to be unbanned.
     */
    public void unban(User user){
        user.setStatus(Status.ACTIVE);
        save(user);
    }

    /**
     * Unbans a user by ID, changing their status to ACTIVE.
     *
     * @param id The ID of the User entity to be unbanned.
     */
    public void unban(Long id){
        User user = getById(id);
        user.setStatus(Status.ACTIVE);
        save(user);
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to be converted.
     * @return A UserDTO representing the User entity.
     */
    public UserDTO convertToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setBirthdayDate(user.getBirthdayDate());
        userDTO.setStatus(user.getStatus());

        List<ProductDTO> productDTOs = user.getProducts().stream()
                .map(ProductService::convertToDTO)
                .collect(Collectors.toList());
        userDTO.setProducts(productDTOs);

        return userDTO;
    }
}
