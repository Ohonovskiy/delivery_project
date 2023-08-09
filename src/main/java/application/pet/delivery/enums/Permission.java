package application.pet.delivery.enums;

/**
 * Enum representing various permissions that can be assigned to users in the application.
 */
public enum Permission {
    /**
     * Read access for developers.
     */
    DEVELOPERS_READ("developers:read"),

    /**
     * Write access for developers.
     */
    DEVELOPERS_WRITE("developers:write"),

    /**
     * Delete access for developers.
     */
    DEVELOPERS_DELETE("developers:delete"),

    /**
     * User deletion permission for developers.
     */
    DEVELOPERS_DELETE_USER("developers:user"),

    /**
     * Ban user permission for developers.
     */
    DEVELOPERS_BAN("developers:ban"),

    /**
     * Access to the admin page for developers.
     */
    DEVELOPERS_ADMIN_PAGE("developers:admin_page"),

    /**
     * Add product to the shop permission.
     */
    SHOP_ADD("shop:add"),

    /**
     * Remove product from the shop permission.
     */
    SHOP_REMOVE("shop:remove"),

    /**
     * Place an order permission for users.
     */
    USER_PLACE_ORDER("user:place_order"),

    /**
     * Cancel an order permission for users.
     */
    USER_CANCEL_ORDER("user:cancel_order"),

    /**
     * Take an order permission for delivery men.
     */
    DELIVERY_MAN_TAKE_ORDER("delivery_man:take_order"),

    /**
     * Cancel an order permission for delivery men.
     */
    DELIVERY_MAN_CANCEL_ORDER("delivery_man:cancel_order");

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
