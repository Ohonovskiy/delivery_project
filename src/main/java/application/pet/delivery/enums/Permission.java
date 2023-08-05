package application.pet.delivery.enums;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write"),
    DEVELOPERS_DELETE("developers:delete"),
    DEVELOPERS_DELETE_USER("developers:user"),
    DEVELOPERS_BAN("developers:ban"),
    DEVELOPERS_ADMIN_PAGE("developers:admin_page"),
    SHOP_ADD("shop:add"),
    SHOP_REMOVE("shop:remove"),
    USER_PLACE_ORDER("user:place_order"),
    USER_CANCEL_ORDER("user:cancel_order"),
    DELIVERY_MAN_TAKE_ORDER("delivery_man:take_order"),
    DELIVERY_MAN_CANCEL_ORDER("delivery_man:cancel_order");





    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
