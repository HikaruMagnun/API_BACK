package enterprise.general_back_api.user;

import java.util.EnumSet;

public enum Role {

    // CLIENT ROLE
    CLIENT(EnumSet.of(
            // USER TABLE
            Permission.USER_CREATE,
            Permission.USER_EDIT,

            // PRODUCT
            Permission.PRODUCT_VIEW,

            // ORDER
            Permission.ORDER_CREATE,
            Permission.ORDER_VIEW)),

    // PERSONAL DE SERVICIO ROLE
    PERSONAL_SERVICE(EnumSet.of(

            // USER TABLE
            Permission.USER_CREATE,
            Permission.USER_EDIT,

            // PRODUCT
            Permission.PRODUCT_VIEW,
            Permission.PRODUCT_EDIT,
            Permission.PRODUCT_CREATE,
            Permission.PRODUCT_DELETE,
            // ORDER
            Permission.ORDER_VIEW,
            Permission.ORDER_CREATE,
            Permission.ORDER_VIEW_LIST,
            Permission.ORDER_EDIT,
            Permission.ORDER_CANCEL)),

    // ADMIN ROLE
    ADMIN(EnumSet.allOf(Permission.class));

    private final EnumSet<Permission> permissions;

    Role(EnumSet<Permission> permissions) {
        this.permissions = permissions;
    }

    public EnumSet<Permission> getPermissions() {
        return permissions;
    }

}