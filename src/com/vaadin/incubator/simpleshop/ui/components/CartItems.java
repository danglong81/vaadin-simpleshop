package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.data.Item;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.EventType;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class CartItems extends Panel implements ItemSetChangeListener,
        ClickListener {

    private static final long serialVersionUID = -7560154972179454453L;

    private IndexedContainer container = null;

    private GridLayout layout = new GridLayout(4, 1);

    public CartItems() {
        ((Layout) getContent()).setMargin(false);
        setSizeFull();
        initHeader();
        addComponent(layout);
        layout.setWidth("100%");
        layout.setColumnExpandRatio(0, 4);
        layout.setColumnExpandRatio(1, 1);
        layout.setColumnExpandRatio(2, 2);
        layout.setColumnExpandRatio(3, 1);
    }

    public void setContainer(IndexedContainer newContainer) {
        container = newContainer;
        container.addListener((ItemSetChangeListener) this);
        refresh();
    }

    private void initHeader() {
        Label productName = new Label("Product");
        Label quantity = new Label("Qty");
        Label sum = new Label("Sum");
        Label spacer = new Label("");
        sum.setWidth(null);

        layout.addComponent(productName);

        layout.addComponent(quantity);

        layout.addComponent(sum);
        layout.setComponentAlignment(sum, Alignment.TOP_RIGHT);

        layout.addComponent(spacer);
    }

    private void refresh() {
        layout.removeAllComponents();
        initHeader();

        if (container != null) {
            for (Object itemId : container.getItemIds()) {
                addProduct(container.getItem(itemId));
            }
        }
    }

    @Override
    public void containerItemSetChange(ItemSetChangeEvent event) {
        refresh();
    }

    public void addProduct(final Item item) {

        final Label productName = new Label();
        productName.setWidth(null);
        productName.setPropertyDataSource(item.getItemProperty("productName"));

        final TextField quantityField = new TextField();
        quantityField.setImmediate(true);
        quantityField.setPropertyDataSource(item.getItemProperty("quantity"));
        quantityField.setWriteThrough(true);
        quantityField.setWidth("40px");

        final Label sumLabel = new Label("");
        sumLabel.setWidth(null);
        sumLabel.setPropertyDataSource(item.getItemProperty("sum"));

        final Button remove = new Button("X", this);
        remove.setData(item);

        layout.addComponent(productName);

        layout.addComponent(quantityField);

        layout.addComponent(sumLabel);
        layout.setComponentAlignment(sumLabel, Alignment.TOP_RIGHT);

        layout.addComponent(remove);
        layout.setComponentAlignment(remove, Alignment.TOP_RIGHT);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        Item item = (Item) event.getButton().getData();
        Product product = (Product) item.getItemProperty("product").getValue();
        container.removeItem(product);

        CartUpdatedEvent updateEvent = new CartUpdatedEvent(
                EventType.PRODUCT_REMOVED, product);
        SimpleshopApplication.getEventHandler().dispatchEvent(updateEvent);
    }

}
