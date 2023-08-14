package application.pet.delivery.security;

import application.pet.delivery.entities.DeliveryMan;
import application.pet.delivery.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class SecurityDeliveryman implements UserDetails {
    /**
     * The email address of the user.
     */
    private final String email;

    /**
     * The password of the user.
     */
    private final String password;

    /**
     * The authorities (permissions) associated with the user.
     */
    private final List<SimpleGrantedAuthority> authorities;

    /**
     * Indicates whether the user account is active.
     */
    private final boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromDeliveryman(DeliveryMan deliveryMan){
        boolean isActive = deliveryMan.getStatus().equals(Status.ACTIVE);

        return new User(
                deliveryMan.getEmail(),
                deliveryMan.getPassword(),
                isActive,
                isActive,
                isActive,
                isActive,
                deliveryMan.getRole().getAuthorities()
        );
    }
}