package enterprise.general_back_api.user;

public enum Permission {

    // Clientes permisos
    USER_CHANGE_ROLE,
    USER_VIEW_LIST,
    USER_DELETE,
    USER_EDIT,
    USER_CREATE,

    // Product Permissions
    PRODUCT_DELETE,
    PRODUCT_CREATE,
    PRODUCT_EDIT,
    PRODUCT_VIEW,

    // Order Permissions
    ORDER_CANCEL,
    ORDER_EDIT,
    ORDER_VIEW_LIST,
    ORDER_CREATE,
    ORDER_VIEW,

}
