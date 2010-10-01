package org.vaadin.simpleshop.ui.components;

import java.math.RoundingMode;
import java.text.NumberFormat;

import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.UriHandler;
import org.vaadin.simpleshop.data.Product;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.util.ConfigUtil;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This is class defines how a single product should be represented in the item
 * browser.
 * 
 * @author Kim
 * 
 */
public class ProductViewer extends VerticalLayout implements ClickListener, LayoutClickListener {

    private static final long serialVersionUID = 9208012559967162736L;

    // The root layout of this component
    private final HorizontalLayout mainLayout = new HorizontalLayout();

    // The header which is show when the product viewer is collapsed
    private  HorizontalLayout details;

    // Product information labels
    private final Label name;
    private final Label price;
    private final Label description;

    // Action buttons
    private final Button addToCartBtn;
    

    // The product being show in this component
    private final Product product;
    
    private boolean expanded;

    /**
     * Constructor. Takes as parameter the product being showed in this
     * component.
     * 
     * @param product
     */
    public ProductViewer(Product product) {
        setStyleName("product");

        // Set the product
        this.product = product;

        // Set the root layout
        addListener(this);
        mainLayout.setMargin(true);
        mainLayout.setWidth(100, UNITS_PERCENTAGE);
        mainLayout.setHeight(30, UNITS_PIXELS);

        // Start building the layout

        // This component should take all the width available
        setWidth("100%");

        // The second component shown in the header should be the product's
        // name. This label should take all the space there is available.
        name = new Label(product.getName());
        name.setWidth(350, UNITS_PIXELS);
     


        // Create a number formatter for formating the prices.
        NumberFormat nf = NumberFormat.getInstance();
        // Always show exactly two digits
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        // Create the price label with the currency
        if (ConfigUtil.getBoolean("product.showPriceIncludingTakes")) {
            price = new Label(nf.format(product.getPriceIncludingTaxes())
                    + " EUR");
        } else {
            price = new Label(nf.format(product.getPriceExcludingTaxes())
                    + " EUR");
        }

        // The price label shouldn't take more space than what it needs
        price.setWidth(null);
        price.addStyleName("price");
      
        // Create the description label, but do not attach it to any layout at
        // this point
        description = new Label(product.getDescription());
        
        addComponent(mainLayout);
        
        mainLayout.addComponent(name);
        mainLayout.addComponent(price);
        mainLayout.setExpandRatio(price, 1);
        mainLayout.setComponentAlignment(name, Alignment.MIDDLE_LEFT);
        mainLayout.setComponentAlignment(price, Alignment.MIDDLE_RIGHT);
        
        // Create the header which is shown when the component is collapsed.
        details = new HorizontalLayout();
        details.setWidth("100%");
        details.setSpacing(true);
        
        // The last component in the header is the add button
        addToCartBtn = new Button();
        addToCartBtn.addStyleName("add_to_cart");
        addToCartBtn.addListener(this);
      
        details.addComponent(description);
        description.setWidth(300, UNITS_PIXELS);
        
        details.setExpandRatio(description, 1);
        details.addComponent(addToCartBtn);
        
        addComponent(details);
        details.setVisible(false);
    }

    
    @Override
    public void buttonClick(ClickEvent event) {
        // Check which button has been pressed
    
    	if (event.getButton().equals(addToCartBtn)) {
            // Add button was pressed. Add this product to the cart
            ShoppingCart.addProduct(product);
        }
    }

    public void setExpanded(boolean expanded) {
    	details.setVisible(expanded);
    	
        if (expanded) {
            UriHandler.setFragment("P" + product.getId() + "-"
                    + product.getName());
        } else {
            mainLayout.removeComponent(description);
        }
    }

	@Override
	public void layoutClick(LayoutClickEvent event) {
		expanded = !expanded;
		setExpanded(expanded);
	}
}
