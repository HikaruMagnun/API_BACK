package enterprise.general_back_api.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    // Clientes permisos
    USER_CHANGE_ROLE("user:change_role"),
    USER_VIEW_LIST("user:view_list"),
    USER_DELETE("user:delete"),
    USER_EDIT("user:edit"),
    USER_CREATE("user:create"),

    // Product Permissions
    PRODUCT_DELETE("product:delete"),
    PRODUCT_CREATE("product:create"),
    PRODUCT_EDIT("product:edit"),
    PRODUCT_VIEW("product:view"),

    // Order Permissions
    ORDER_CANCEL("order:cancel"),
    ORDER_EDIT("order:edit"),
    ORDER_VIEW_LIST("order:view_list"),
    ORDER_CREATE("order:create"),
    ORDER_VIEW("order:view");

    @Getter
    private final String permission;

}
