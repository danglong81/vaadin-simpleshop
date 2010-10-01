package org.vaadin.simpleshop.ui.components;

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
import com.vaadin.ui.Button.ClickEvent;

public class Summary extends HorizontalLayout implements CartUpdateListener, Button.ClickListener {

	private static final long serialVersionUID = -6578618679491943392L;
	
	private Label totalSumLabel;
	private Button checkoutBtn;
	
	public Summary() {
		setMargin(true);
		
		 // Create the sum label
        totalSumLabel = new Label(CartController.getFormattedTotalPrice(
                ShoppingCart.getOrder(), ConfigUtil
                        .getBoolean("product.showPriceIncludingTakes")));
        totalSumLabel.addStyleName("total_sum");

        if (ConfigUtil.getBoolean("product.showPriceIncludingTakes")) {
            totalSumLabel.setCaption(SystemMsg.CART_TOTAL_SUM_CAPTION_INCL_VAT
                    .get());
        } else {
            totalSumLabel.setCaption(SystemMsg.CART_TOTAL_SUM_CAPTION_EXCL_VAT
                    .get());
        }
		
        // Initialize the checkout button
        checkoutBtn = new Button();
        checkoutBtn.addStyleName("checkout");
        checkoutBtn.addListener(this);

        // Button should be disabled when there are no items in the cart and
        // since the cart will be empty when this view is initialized, this
        // button should then be disabled.
        checkoutBtn.setEnabled(false);

		
		addComponent(totalSumLabel);
		addComponent(checkoutBtn);
		
		setWidth(100, UNITS_PERCENTAGE);
		setComponentAlignment(totalSumLabel, Alignment.BOTTOM_LEFT);
		setComponentAlignment(checkoutBtn, Alignment.BOTTOM_RIGHT);
		
		EventHandler.addListener(this);
		
		
	}

	  @Override
	    public void cartUpdated(CartUpdatedEvent event) {
	        totalSumLabel.setValue(CartController.getFormattedTotalPrice(
	                ShoppingCart.getOrder(), ConfigUtil
	                        .getBoolean("product.showPriceIncludingTakes")));
	        

	        // Check if the checkout button exists, as it is removed in some cases.
	        if (checkoutBtn != null) {
	            // Disable the checkout button if there aren't any items in the cart
	            if (checkoutBtn.isEnabled()
	                    && ShoppingCart.getOrder().getOrderedProducts().size() <= 0) {
	                checkoutBtn.setEnabled(false);
	            } else if (!checkoutBtn.isEnabled()
	                    && ShoppingCart.getOrder().getOrderedProducts().size() > 0) {
	                // Enable button if cart contains items
	                checkoutBtn.setEnabled(true);
	            }
	        }
	    }

	    @Override
	    public void buttonClick(ClickEvent event) {
	        // We want to proceed to the checkout process. Activate the checkout
	        // view.
	        ViewHandler.activateView(CheckoutView.class);
	    }
	


}
