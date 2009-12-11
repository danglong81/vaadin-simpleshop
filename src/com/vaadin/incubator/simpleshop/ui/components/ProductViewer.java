package com.vaadin.incubator.simpleshop.ui.components;

import java.math.RoundingMode;
import java.text.NumberFormat;

import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.EventType;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ProductViewer extends CustomComponent implements ClickListener {

    private static final long serialVersionUID = 9208012559967162736L;

    private final VerticalLayout mainLayout = new VerticalLayout();
    private final HorizontalLayout header;

    private final Label name;
    private final Label price;
    private final Label description;
    private final Button addToCartBtn;
    private final Button expandBtn;

    private Product product;

    public ProductViewer(Product product) {
        setProduct(product);
        setCompositionRoot(mainLayout);
        setWidth("100%");
        header = new HorizontalLayout();
        header.setWidth("100%");
        header.setSpacing(true);

        expandBtn = new Button("+", this);
        expandBtn.setWidth(null);
        expandBtn.setValue(false);
        header.addComponent(expandBtn);

        name = new Label(product.getName());
        header.addComponent(name);
        header.setExpandRatio(name, 1);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        price = new Label(nf.format(product.getPrice()) + " €");
        price.setWidth(null);
        header.addComponent(price);

        addToCartBtn = new Button("Add", this);
        addToCartBtn.setWidth(null);
        header.addComponent(addToCartBtn);

        description = new Label(product.getDescription());
        mainLayout.addComponent(header);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(expandBtn)) {
            boolean expand = !(Boolean) expandBtn.getValue();
            expandBtn.setValue(expand);
            if (expand) {
                expandBtn.setCaption("-");
                mainLayout.addComponent(description);
            } else {
                expandBtn.setCaption("+");
                mainLayout.removeComponent(description);
            }
        } else if (event.getButton().equals(addToCartBtn)) {
            CartUpdatedEvent updateEvent = new CartUpdatedEvent(
                    EventType.PRODUCT_ADDED, getProduct());
            SimpleshopApplication.getEventHandler().dispatchEvent(updateEvent);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

}
