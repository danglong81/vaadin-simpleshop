package com.vaadin.incubator.simpleshop.data;

import javax.persistence.Entity;

@Entity
public class Product extends AbstractPojo {

    private String name;

    private double price;

    private String description;

    public Product() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
