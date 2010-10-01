package org.vaadin.simpleshop.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.simpleshop.UriHandler;
import org.vaadin.simpleshop.data.Product;
import org.vaadin.simpleshop.data.ProductCategory;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.controllers.ItemController;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * Main layout for the item browser. The item browser is where the user browses
 * through categories and products.
 * 
 * @author Kim
 * 
 */
public class ItemBrowser extends AbstractView<VerticalLayout> implements
        ClickListener {

    private static final long serialVersionUID = 5368868329646304718L;

    private final Panel categoryHierarchy = new Panel();

    private final Panel productsLayout = new Panel();

    private final CssLayout spacerLayout = new CssLayout();

    private final List<Button> categoryHierharchyList = new ArrayList<Button>();

    private final Button rootCategoryBtn = new Button(
            SystemMsg.APPLICATION_TITLE.get(), this);

    public ItemBrowser() {
        super(new VerticalLayout());
        setSizeFull();
        content.setSizeFull();
        content.setStyleName("item-browser-background");

        categoryHierarchy.setWidth("100%");
        categoryHierarchy.setHeight(null);
        categoryHierarchy.setStyleName(Panel.STYLE_LIGHT);
        ((Layout) categoryHierarchy.getContent()).setMargin(false);

        productsLayout.setWidth("100%");
        productsLayout.setStyleName(Panel.STYLE_LIGHT);
        productsLayout.addStyleName("products-browser");
        ((Layout) productsLayout.getContent()).setMargin(false);

        content.addComponent(categoryHierarchy);
        content.addComponent(productsLayout);
        content.addComponent(spacerLayout);

        content.setExpandRatio(spacerLayout, 1);

        rootCategoryBtn.setWidth("100%");
        rootCategoryBtn.addStyleName("category_button");
        categoryHierarchy.addComponent(rootCategoryBtn);
        categoryHierharchyList.add(rootCategoryBtn);

        initRoot();
    }

    private void initRoot() {
        List<ProductCategory> categories = ItemController.getRootCategories();
        for (ProductCategory category : categories) {
            Button categoryBtn = generateCategoryButton(category);
            categoryBtn.setData(category);
            productsLayout.addComponent(categoryBtn);
        }
    }

    private void drawCategory(ProductCategory category, Long expandedProductId) {
        if (category != null) {
            productsLayout.removeAllComponents();
            List<ProductCategory> subcategories = category.getSubcategories();
            if (subcategories != null && subcategories.size() > 0) {
                for (ProductCategory subCategory : subcategories) {
                    Button categoryBtn = generateCategoryButton(subCategory);
                    categoryBtn.setData(subCategory);
                    productsLayout.addComponent(categoryBtn);
                }
            }

            List<Product> products = category.getProducts();
            if (products != null && products.size() > 0) {
                for (Product product : products) {
                    ProductViewer productViewer = new ProductViewer(product);
                    productsLayout.addComponent(productViewer);

                    if (expandedProductId != null
                            && expandedProductId.equals(product.getId())) {
                        productViewer.setExpanded(true);
                    }
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
            Button categoryBtn = generateCategoryButton(category);
           
            categoryHierharchyList.add(categoryBtn);
            categoryHierarchy.addComponent(categoryBtn);
        }

        if (category != null) {
            String name = category.getName();
            UriHandler.setFragment("C" + category.getId() + "-" + name);
        }
        
        for(Button categoryButton : categoryHierharchyList) {
        	categoryButton.addStyleName("selected");
        }

        drawCategory(category, null);
    }

    @Override
    public void activated(Object... params) {
        // We can expect two parameters, the second parameter is an id and the
        // first parameter defines if the id is for a category or for a product
        if (params.length == 2 && params[1] instanceof Long) {
            // Parse the id
            Long id = (Long) params[1];

            Long categoryId = null;
            Long productId = null;
            // Check if the id is for a category
            if (params[0] != null && params[0].equals("category")) {
                categoryId = id;
            } else if (params[0] != null && params[0].equals("product")) {
                // We are browsing a product
                productId = id;
                ProductCategory category = ItemController
                        .getCategory(productId);
                if (category != null) {
                    categoryId = category.getId();
                }
            }

            if (categoryId != null) {
                // Get a list of all parent categories for the given category id
                List<ProductCategory> categories = ItemController
                        .getPathToCategory(categoryId);
                if (categories != null && categories.size() > 0) {
                    categoryHierarchy.removeAllComponents();
                    categoryHierarchy.addComponent(rootCategoryBtn);

                    categoryHierharchyList.clear();
                    categoryHierharchyList.add(rootCategoryBtn);

                    productsLayout.removeAllComponents();
                    initRoot();
                    for (ProductCategory category : categories) {
                    	Button categoryBtn = generateCategoryButton(category);
                        categoryHierharchyList.add(categoryBtn);
                        categoryHierarchy.addComponent(categoryBtn);
                    }

                    drawCategory(categories.get(categories.size() - 1),
                            productId);
                }
            }
        }
    }
    
    private Button generateCategoryButton(ProductCategory category) {
    	 Button categoryBtn = new Button(category.getName(), this);
         categoryBtn.setData(category);
         categoryBtn.addStyleName("category_button");
         categoryBtn.setWidth(100, UNITS_PERCENTAGE);
         
         return categoryBtn;
    }
}
