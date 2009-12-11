package com.vaadin.incubator.simpleshop.ui.controllers;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.incubator.simpleshop.data.Product;

public class CartController implements Property.ValueChangeListener {

    private static final long serialVersionUID = 1510068834927523940L;
    private final IndexedContainer container = new IndexedContainer();

    private Map<Property, Product> propertyMap = new HashMap<Property, Product>();

    public CartController() {
        container.addContainerProperty("product", Product.class, "");
        container.addContainerProperty("productName", String.class, "");
        container.addContainerProperty("quantity", Integer.class, "");
        container.addContainerProperty("sum", String.class, "");
    }

    public IndexedContainer getContainer() {
        return container;
    }

    public Item addProduct(Product product, int quantity) {
        if (container.containsId(product)) {
            Item item = container.getItem(product);
            quantity += (Integer) item.getItemProperty("quantity").getValue();
        }
        return addOrUpdateItem(product, quantity);
    }

    public void removeProduct(Product product) {
        if (container.containsId(product)) {
            propertyMap.remove(container.getContainerProperty(product,
                    "quantity"));
            container.removeItem(product);
        }
    }

    private Item addOrUpdateItem(Product p, int quantity) {
        Item item = null;
        if (!container.containsId(p)) {
            item = container.addItem(p);
        } else {
            item = container.getItem(p);
        }

        item.getItemProperty("product").setValue(p);
        item.getItemProperty("productName").setValue(p.getName());
        item.getItemProperty("quantity").setValue(quantity);
        updateSum(p, quantity);
        ((Property.ValueChangeNotifier) item.getItemProperty("quantity"))
                .addListener(this);

        propertyMap.put(item.getItemProperty("quantity"), p);
        return item;
    }

    private void updateSum(Product product, int quantity) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.HALF_UP);

        double sum = product.getPrice() * quantity;
        container.getItem(product).getItemProperty("sum").setValue(
                nf.format(sum) + " €");
    }

    @Override
    public void valueChange(ValueChangeEvent event) {
        Product product = propertyMap.get(event.getProperty());
        updateSum(product, (Integer) event.getProperty().getValue());
    }
}
