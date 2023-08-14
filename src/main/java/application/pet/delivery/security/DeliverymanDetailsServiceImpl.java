package application.pet.delivery.security;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.repositories.DeliverymanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("deliverymanDetailsServiceImpl")
public class DeliverymanDetailsServiceImpl implements UserDetailsService {
    private final DeliverymanRepository deliverymanRepository;

    @Autowired
    public DeliverymanDetailsServiceImpl(DeliverymanRepository deliverymanRepository) {
        this.deliverymanRepository = deliverymanRepository;
    }

    /**
     * Loads deliveryman details by username (email).
     *
     * @param username The email address of the deliveryman.
     * @return UserDetails object representing the deliveryman's details.
     * @throws UsernameNotFoundException if the deliveryman does not exist.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DeliveryMan deliveryMan = deliverymanRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Deliveryman does not exist"));
        return SecurityDeliveryman.fromDeliveryman(deliveryMan);
    }
}
