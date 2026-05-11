package ui;

import net.serenitybdd.screenplay.targets.Target;

public class InventoryPage {

    public static final Target PRODUCT_LIST =
            Target.the("contenedor de productos").locatedBy(".inventory_container");

    public static final Target PRODUCT_ITEM =
            Target.the("item de producto").locatedBy(".inventory_item");

    public static final Target CART_BUTTON =
            Target.the("boton del carrito").locatedBy(".shopping_cart_link");

    public static final Target MENU_BUTTON =
            Target.the("boton de menu").locatedBy("#react-burger-menu-btn");

    public static final Target LOGOUT_LINK =
            Target.the("enlace de logout").locatedBy("#logout_sidebar_link");
}
