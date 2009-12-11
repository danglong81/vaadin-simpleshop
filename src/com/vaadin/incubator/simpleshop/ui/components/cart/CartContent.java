package com.vaadin.incubator.simpleshop.ui.components.cart;

import java.math.RoundingMode;
import java.text.NumberFormat;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.CartUpdateListener;
import com.vaadin.incubator.simpleshop.events.CartUpdatedEvent.EventType;
import com.vaadin.incubator.simpleshop.ui.components.CartItems;
import com.vaadin.incubator.simpleshop.ui.controllers.CartController;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CartContent extends VerticalLayout implements CartUpdateListener,
        Property.ValueChangeListener {

    private static final long serialVersionUID = 8401760557369059696L;

    private HorizontalLayout summaryLayout;

    private Label totalSumLabel;

    private Button paymentBtn;

    private CartItems content;

    private final CartController controller;

    public CartContent(CartController controller) {
        this.controller = controller;
        setSizeFull();

        initContent();
        initSummary();
        SimpleshopApplication.getEventHandler().addListener(this);
    }

    private void initContent() {
        content = new CartItems();
        content.setContainer(controller.getContainer());
        addComponent(content);
        setExpandRatio(content, 1);
    }

    private void initSummary() {
        summaryLayout = new HorizontalLayout();
        summaryLayout.setWidth("100%");
        summaryLayout.setMargin(true);
        summaryLayout.setSpacing(true);

        totalSumLabel = new Label("0,00 €");
        paymentBtn = new Button("Proceed");

        summaryLayout.addComponent(totalSumLabel);
        summaryLayout.addComponent(paymentBtn);

        summaryLayout.setComponentAlignment(totalSumLabel,
                Alignment.MIDDLE_LEFT);
        summaryLayout.setComponentAlignment(paymentBtn, Alignment.MIDDLE_RIGHT);
        addComponent(summaryLayout);
    }

    @Override
    public void cartUpdated(CartUpdatedEvent event) {
        if (event.getType().equals(EventType.PRODUCT_ADDED)) {
            Item item = controller.addProduct(event.getProduct(), 1);
            ((Property.ValueChangeNotifier) item.getItemProperty("sum"))
                    .addListener(this);
        }

        recalculateSum(controller.getContainer());
    }

    private void recalculateSum(Container container) {
        double totalSum = 0;
        for (Object itemId : container.getItemIds()) {
            Product product = (Product) itemId;
            Integer quantity = (Integer) container.getItem(itemId)
                    .getItemProperty("quantity").getValue();

            totalSum += product.getPrice() * quantity;
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        totalSumLabel.setValue(nf.format(totalSum));
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        recalculateSum(controller.getContainer());
    }
}
