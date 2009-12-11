package com.vaadin.incubator.simpleshop.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * Products can be grouped into categories. This entity class defines the
 * category for products. Product categories are hierarchical, so any product
 * category can contain an unlimited amount of subcategories, which each can
 * contain their own subcategories.
 * 
 * @author Kim
 * 
 */
@Entity
public class ProductCategory extends AbstractPojo {

    @OneToMany(cascade = CascadeType.ALL)
    @PrivateOwned
    private List<Product> products = new ArrayList<Product>();

    @OneToMany(cascade = CascadeType.ALL)
    @PrivateOwned
    private List<ProductCategory> subcategories = new ArrayList<ProductCategory>();

    private String name;

    private boolean rootCategory = false;

    /**
     * Set the list of all products belonging to this category.
     * 
     * @param products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Get all the products belonging to this category.
     * 
     * @return
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Define the subcategories of this category.
     * 
     * @param subcategories
     */
    public void setSubcategories(List<ProductCategory> subcategories) {
        this.subcategories = subcategories;
    }

    /**
     * Get all the subcategories of this category.
     * 
     * @return
     */
    public List<ProductCategory> getSubcategories() {
        return subcategories;
    }

    /**
     * Set the name of this product category. The name is used as the title for
     * the category.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the product category.
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * A transient helper method for adding a single product to this product
     * category.
     * 
     * @param product
     */
    public void addProduct(Product product) {
        getProducts().add(product);
    }

    /**
     * A transient helper method for adding a single product category as a
     * subcategory of this category.
     * 
     * @param category
     */
    public void addSubCategory(ProductCategory category) {
        getSubcategories().add(category);
    }

    /**
     * Define if this category is a root category. A root category should never
     * be the subcategory of any product category. Root categories are the
     * default categories shown in the item browser in the shop.
     * 
     * @param rootCategory
     */
    public void setRootCategory(boolean rootCategory) {
        this.rootCategory = rootCategory;
    }

    /**
     * Returns true if this category is a root category in the shop, in which
     * case it should be shown as a top-level category in the item browser.
     * 
     * Returns false if this category is a subcategory of another product
     * category.
     * 
     * @return
     */
    public boolean isRootCategory() {
        return rootCategory;
    }

}
