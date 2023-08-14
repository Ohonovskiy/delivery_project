package application.pet.delivery.security;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.entities.User;
import application.pet.delivery.repositories.DeliverymanRepository;
import application.pet.delivery.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the UserDetailsService interface for loading user details during authentication.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final DeliverymanRepository deliverymanRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, DeliverymanRepository deliverymanRepository) {
        this.userRepository = userRepository;
        this.deliverymanRepository = deliverymanRepository;
    }

    /**
     * Loads user details by username (email).
     *
     * @param username The email address of the user.
     * @return UserDetails object representing the user's details.
     * @throws UsernameNotFoundException if the user does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userRepository.findByEmail(username).isPresent()) {
            User user = userRepository.findByEmail(username).get();
            return SecurityUser.fromUser(user);
        } else {
            DeliveryMan deliveryMan = deliverymanRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException("User does not exist"));
            return SecurityDeliveryman.fromDeliveryman(deliveryMan);
        }
    }
}
