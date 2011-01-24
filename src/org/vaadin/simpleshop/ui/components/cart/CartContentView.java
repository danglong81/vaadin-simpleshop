package org.vaadin.simpleshop.ui.components.cart;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.simpleshop.events.CartUpdatedEvent;
import org.vaadin.simpleshop.events.EventHandler;
import org.vaadin.simpleshop.events.CartUpdatedEvent.CartUpdateListener;
import org.vaadin.simpleshop.ui.components.Summary;

import com.vaadin.ui.VerticalLayout;

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
        this(true);
    }

    public CartContentView(boolean includeSummary) {
        super(new VerticalLayout());
        // Take as much space as there is available
        getContent().setSizeFull();

        // Initialize the content
        initContent(includeSummary);

        EventHandler.addListener(this);
        addStyleName("no-box-drag-hints");
        addStyleName("no-horizontal-drag-hints");
        addStyleName("no-vertical-drag-hints");
    }

    /**
     * Initializes the cart items listing panel
     */
    private void initContent(boolean includeSummary) {
        // Create the cart items panel and add it to the main layout
        cartContent = new CartItems();
        getContent().addComponent(cartContent);

        if (includeSummary) {
            // The items panel should take all the available space
            getContent().setExpandRatio(cartContent, 1);
            getContent().addComponent(new Summary());
        }
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

    @Override
    public void deactivated(Object... params) {
        // TODO Auto-generated method stub

    }

}
