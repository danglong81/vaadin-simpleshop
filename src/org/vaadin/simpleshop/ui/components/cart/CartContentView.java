package org.vaadin.simpleshop.ui.components.cart;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.events.CartUpdatedEvent;
import org.vaadin.simpleshop.events.EventHandler;
import org.vaadin.simpleshop.events.CartUpdatedEvent.CartUpdateListener;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.checkout.CheckoutView;
import org.vaadin.simpleshop.ui.controllers.CartController;
import org.vaadin.simpleshop.util.ConfigUtil;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class defines the layout for the cart content. The cart content consists
 * of two parts, a list of items in the cart and a summary containing the total
 * sum of the cart content including a checkout button.
 * 
 * @author Kim
 * 
 */
public class CartContentView extends AbstractView<VerticalLayout> implements
        CartUpdateListener {

    private static final long serialVersionUID = 8401760557369059696L;

    private CartItems cartContent;

    /**
     * Constructor
     */
    public CartContentView() {
        super(new VerticalLayout());
        // Take as much space as there is available
        content.setSizeFull();

        // Initialize the content
        initContent();

        EventHandler.addListener(this);
    }

    /**
     * Initializes the cart items listing panel
     */
    private void initContent() {
        // Create the cart items panel and add it to the main layout
        cartContent = new CartItems();
        content.addComponent(cartContent);

        // The items panel should take all the available space
        content.setExpandRatio(cartContent, 1);
    }

    /**
     * Initializes the cart content summary layout
     */
  

    @Override
    public void cartUpdated(CartUpdatedEvent event) {
      
        cartContent.refresh();

      
    }

    @Override
    public void activated(Object... params) {
      
    }

   

}
