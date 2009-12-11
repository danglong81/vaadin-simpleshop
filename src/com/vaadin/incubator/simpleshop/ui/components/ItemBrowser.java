package com.vaadin.incubator.simpleshop.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.data.ProductCategory;
import com.vaadin.incubator.simpleshop.ui.controllers.ItemController;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class ItemBrowser extends VerticalLayout implements ClickListener {

    private static final long serialVersionUID = 5368868329646304718L;

    private final Panel categoryHierarchy = new Panel();

    private final Panel productsLayout = new Panel();

    private final CssLayout spacerLayout = new CssLayout();

    private final ItemController controller = new ItemController();

    private final List<Button> categoryHierharchyList = new ArrayList<Button>();

    private Button rootCategoryBtn = new Button("Simple Shop", this);

    public ItemBrowser() {
        setSizeFull();
        setStyleName("item-browser-background");

        categoryHierarchy.setWidth("100%");
        categoryHierarchy.setHeight(null);
        categoryHierarchy.setStyleName(Panel.STYLE_LIGHT);
        ((Layout) categoryHierarchy.getContent()).setMargin(false);

        productsLayout.setWidth("100%");
        productsLayout.setStyleName(Panel.STYLE_LIGHT);
        ((Layout) productsLayout.getContent()).setMargin(false);

        addComponent(categoryHierarchy);
        addComponent(productsLayout);
        addComponent(spacerLayout);

        setExpandRatio(spacerLayout, 1);

        rootCategoryBtn.setWidth("100%");
        categoryHierarchy.addComponent(rootCategoryBtn);
        categoryHierharchyList.add(rootCategoryBtn);

        initRoot();
    }

    private void initRoot() {
        List<ProductCategory> categories = controller.getRootCategories();
        for (ProductCategory category : categories) {
            Button categoryBtn = new Button(category.getName(), this);
            categoryBtn.setData(category);
            categoryBtn.setWidth("100%");
            productsLayout.addComponent(categoryBtn);
        }
    }

    private void drawCategory(ProductCategory category) {
        if (category != null) {
            productsLayout.removeAllComponents();
            List<ProductCategory> subcategories = category.getSubcategories();
            if (subcategories != null && subcategories.size() > 0) {
                for (ProductCategory subCategory : subcategories) {
                    Button categoryBtn = new Button(subCategory.getName());
                    categoryBtn.setData(subCategory);
                    categoryBtn.addListener(this);
                    categoryBtn.setWidth("100%");
                    productsLayout.addComponent(categoryBtn);
                }
            }

            List<Product> products = category.getProducts();
            if (products != null && products.size() > 0) {
                for (Product product : products) {
                    ProductViewer productViewer = new ProductViewer(product);
                    productsLayout.addComponent(productViewer);
                }
            }
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {
        ProductCategory category = (ProductCategory) event.getButton()
                .getData();
        if (category == null) {
            categoryHierarchy.removeAllComponents();
            categoryHierarchy.addComponent(rootCategoryBtn);

            categoryHierharchyList.clear();
            categoryHierharchyList.add(rootCategoryBtn);

            productsLayout.removeAllComponents();
            initRoot();
        } else if (categoryHierharchyList.contains(event.getButton())) {
            List<Button> buttonsToRemove = new ArrayList<Button>();
            int index = categoryHierharchyList.indexOf(event.getButton());

            for (int i = index + 1; i < categoryHierharchyList.size(); i++) {
                Button button = categoryHierharchyList.get(i);
                buttonsToRemove.add(button);
                categoryHierarchy.removeComponent(button);
            }

            categoryHierharchyList.removeAll(buttonsToRemove);
        } else {
            Button categoryBtn = new Button(category.getName(), this);
            categoryBtn.setData(category);
            categoryBtn.setWidth("100%");
            categoryHierharchyList.add(categoryBtn);
            categoryHierarchy.addComponent(categoryBtn);
        }

        drawCategory(category);
    }
}
