package application.pet.delivery.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    ROLE_ADMIN(Set.of(
            Permission.DEVELOPERS_WRITE,
            Permission.DEVELOPERS_DELETE,
            Permission.DEVELOPERS_BAN,
            Permission.DEVELOPERS_ADMIN_PAGE)),
    ROLE_USER(Set.of(Permission.USER_PLACE_ORDER, Permission.USER_CANCEL_ORDER)),
    ROLE_DELIVERY_MAN(Set.of(Permission.DELIVERY_MAN_TAKE_ORDER, Permission.DELIVERY_MAN_CANCEL_ORDER)),
    ROLE_SHOP(Set.of(Permission.SHOP_ADD, Permission.SHOP_REMOVE));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
