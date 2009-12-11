package com.vaadin.incubator.simpleshop.ui.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.incubator.simpleshop.data.ProductCategory;
import com.vaadin.incubator.simpleshop.facade.FacadeFactory;

public class ItemController {

    public List<ProductCategory> getRootCategories() {
        String query = "SELECT pc FROM ProductCategory pc WHERE pc.rootCategory = :isRoot";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("isRoot", true);
        return FacadeFactory.getFacade().list(query, parameters);
    }
}
