package application.pet.delivery.enums.users;

/**
 * Enum representing various permissions that can be assigned to users in the application.
 */
public enum Permission {

    ADMIN_ROLE("admin"),
    USER_ROLE("user"),
    DELIVERY_MAN_ROLE("delivery_man"),
    SHOP_ROLE("shop");

    private final String permission;

    /**
     * Constructs a Permission enum with the given permission string.
     *
     * @param permission The permission string.
     */
    Permission(String permission) {
        this.permission = permission;
    }

    /**
     * Get the permission string.
     *
     * @return The permission string.
     */
    public String getPermission() {
        return permission;
    }
}
