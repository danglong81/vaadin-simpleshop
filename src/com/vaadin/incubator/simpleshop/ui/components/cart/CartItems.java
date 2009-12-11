package com.vaadin.incubator.simpleshop.ui.components.cart;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.incubator.simpleshop.ShoppingCart;
import com.vaadin.incubator.simpleshop.data.OrderRow;
import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.controllers.CartController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class is a panel containing a listing of all the products in the
 * shopping cart
 * 
 * @author Kim
 * 
 */
public class CartItems extends Panel implements ValueChangeListener,
        ClickListener {

    private static final long serialVersionUID = -7560154972179454453L;

    private final GridLayout layout = new GridLayout(4, 1);

    public CartItems() {
        // Remove margins from this panel
        ((Layout) getContent()).setMargin(false);
        // Maximize the size of this panel
        setSizeFull();

        // Initialize the list header
        initHeader();

        // The GridLayout should maximize its width
        layout.setWidth("100%");

        // Set the column size ratios in the layout
        layout.setColumnExpandRatio(0, 4);
        layout.setColumnExpandRatio(1, 1);
        layout.setColumnExpandRatio(2, 2);
        layout.setColumnExpandRatio(3, 1);

        // Add the GridLayout to the panel
        addComponent(layout);
    }

    /**
     * Initializes the headers for the listing
     */
    private void initHeader() {
        // Create labels
        Label productName = new Label(SystemMsg.CART_PRODUCT.get());
        Label quantity = new Label(SystemMsg.CART_QTY.get());
        Label sum = new Label(SystemMsg.CART_SUM.get());

        // A label takes up 100% of the available width by default. Hence we
        // must set its width to null, so that it won't take up more space than
        // we need. We won't be able to align the label correctly if we do not
        // set the width to null.
        sum.setWidth(null);

        // Add component
        layout.addComponent(productName);
        layout.addComponent(quantity);
        layout.addComponent(sum);

        // Align the sum to the right
        layout.setComponentAlignment(sum, Alignment.TOP_RIGHT);

        // Add a spacer label. The header doesn't contain anything in the last
        // column, but in the actual listing there is a remove button in the
        // last column.
        Label spacer = new Label("");
        layout.addComponent(spacer);
    }

    /**
     * Refreshes the layout
     */
    public void refresh() {
        // Remove all components from the layout
        layout.removeAllComponents();

        // Reinitialize the headers
        initHeader();

        for (OrderRow row : ShoppingCart.getOrder().getOrderedProducts()) {
            addProduct(row);
        }
    }

    /**
     * Adds a new product to the listing
     * 
     * @param row
     */
    private void addProduct(OrderRow row) {

        // Create the product name label
        final Label productName = new Label(row.getProductName());
        productName.setWidth(null);

        // Create the textfield for modifying the quantity
        final TextField quantityField = new TextField();
        quantityField.setImmediate(true);
        quantityField.setValue(row.getQuantity());
        quantityField.addListener(this);
        quantityField.setWidth("40px");
        // Set the product object as the textfield's data, so that we can in the
        // valueChangeEvent match the event to a specific product.
        quantityField.setData(row.getProduct());

        // Add sum label
        final Label sumLabel = new Label(CartController.formatSum(row.getSum()));
        sumLabel.setWidth(null);

        // Add a remove button
        final Button remove = new Button("X", this);
        // Set the product object as the button's data, so that we can in the
        // buttonClick event match the event to a specific product.
        remove.setData(row.getProduct());

        // Add the components to the layout and set proper alignments
        layout.addComponent(productName);
        layout.addComponent(quantityField);
        layout.addComponent(sumLabel);
        layout.setComponentAlignment(sumLabel, Alignment.TOP_RIGHT);

        layout.addComponent(remove);
        layout.setComponentAlignment(remove, Alignment.TOP_RIGHT);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // Get the product
        Product product = (Product) event.getButton().getData();
        // Remove it from the cart
        ShoppingCart.removeProduct(product);
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        // Get the product
        Product product = (Product) ((TextField) event.getProperty()).getData();
        // Update its quantity
        ShoppingCart.setQuantity(product, Integer.valueOf((String) event
                .getProperty().getValue()));
    }

}
