package org.vaadin.simpleshop.ui.controllers;

import java.io.Serializable;
import java.util.ArrayList;
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
    public static List<ProductCategory> getRootCategories() {
        String query = "SELECT pc FROM ProductCategory pc WHERE pc.rootCategory = :isRoot";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("isRoot", true);
        return FacadeFactory.getFacade().list(query, parameters);
    }

    /**
     * Find the path to a ProductCategory
     * 
     * @param id
     *            Id of the ProductCategory we want to browse to
     * @return A list of parent ProductCategories, list includes the
     *         ProductCategory object for the given id
     */
    public static List<ProductCategory> getPathToCategory(Long id) {
        List<ProductCategory> path = new ArrayList<ProductCategory>();
        ProductCategory category = FacadeFactory.getFacade().find(
                ProductCategory.class, id);

        if (category != null) {
            path.add(category);
            ProductCategory parent = null;
            do {
                Long parentId = parent == null ? category.getId() : parent
                        .getId();
                String query = "SELECT pc FROM ProductCategory pc "
                        + "INNER JOIN pc.subcategories AS sub WHERE "
                        + "sub.id = :id";
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("id", parentId);
                parent = FacadeFactory.getFacade().find(query, parameters);
                if (parent != null) {
                    path.add(0, parent);
                }
            } while (parent != null);

        }

        return path;
    }

    /**
     * Fetches the ProductCategory object for the category of a Product.
     * 
     * @param productId
     *            The id of the product whose category we want to fetch
     * @return The category of the product
     */
    public static ProductCategory getCategory(Long productId) {
        if (productId != null) {
            // Fetch the category for the given product id
            String query = "SELECT pc FROM ProductCategory pc "
                    + "INNER JOIN pc.products AS p WHERE " + "p.id = :id";
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", productId);
            return FacadeFactory.getFacade().find(query, parameters);
        }
        return null;
    }
}
