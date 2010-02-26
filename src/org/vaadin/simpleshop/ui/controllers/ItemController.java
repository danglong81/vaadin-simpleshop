package org.vaadin.simpleshop.ui.controllers;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.appfoundation.persistence.facade.FacadeFactory;
import org.vaadin.simpleshop.data.ProductCategory;

/**
 * Controller class for handing items
 * 
 * @author Kim
 * 
 */
public class ItemController implements Serializable {

    private static final long serialVersionUID = 6996400907248048986L;

    /**
     * Fetch all product categories which are at the root level.
     * 
     * @return
     */
    public List<ProductCategory> getRootCategories() {
        String query = "SELECT pc FROM ProductCategory pc WHERE pc.rootCategory = :isRoot";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("isRoot", true);
        return FacadeFactory.getFacade().list(query, parameters);
    }
}
