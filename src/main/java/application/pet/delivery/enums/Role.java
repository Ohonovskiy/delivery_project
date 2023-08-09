package application.pet.delivery.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enum representing various roles that can be assigned to users in the application.
 */
public enum Role {

    /**
     * Administrator role with associated permissions.
     */
    ROLE_ADMIN(Set.of(
            Permission.DEVELOPERS_WRITE,
            Permission.DEVELOPERS_DELETE,
            Permission.DEVELOPERS_BAN,
            Permission.DEVELOPERS_ADMIN_PAGE)),

    /**
     * User role with associated permissions.
     */
    ROLE_USER(Set.of(Permission.USER_PLACE_ORDER, Permission.USER_CANCEL_ORDER)),

    /**
     * Delivery man role with associated permissions.
     */
    ROLE_DELIVERY_MAN(Set.of(Permission.DELIVERY_MAN_TAKE_ORDER, Permission.DELIVERY_MAN_CANCEL_ORDER)),

    /**
     * Shop role with associated permissions.
     */
    ROLE_SHOP(Set.of(Permission.SHOP_ADD, Permission.SHOP_REMOVE));

    private final Set<Permission> permissions;

    /**
     * Constructs a Role enum with the given set of permissions.
     *
     * @param permissions The set of permissions associated with the role.
     */
    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Get the set of permissions associated with the role.
     *
     * @return The set of permissions.
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Get the authorities for Spring Security based on the permissions associated with the role.
     *
     * @return The set of SimpleGrantedAuthority objects.
     */
    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
