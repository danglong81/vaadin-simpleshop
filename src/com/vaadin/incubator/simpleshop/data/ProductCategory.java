package com.vaadin.incubator.simpleshop.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.eclipse.persistence.annotations.PrivateOwned;

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

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setSubcategories(List<ProductCategory> subcategories) {
        this.subcategories = subcategories;
    }

    public List<ProductCategory> getSubcategories() {
        return subcategories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addProduct(Product product) {
        getProducts().add(product);
    }

    public void addSubCategory(ProductCategory category) {
        getSubcategories().add(category);
    }

    public void setRootCategory(boolean rootCategory) {
        this.rootCategory = rootCategory;
    }

    public boolean isRootCategory() {
        return rootCategory;
    }

}
