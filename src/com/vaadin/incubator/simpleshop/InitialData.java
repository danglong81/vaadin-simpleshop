package com.vaadin.incubator.simpleshop;

import com.vaadin.incubator.simpleshop.data.ActionLock;
import com.vaadin.incubator.simpleshop.data.Product;
import com.vaadin.incubator.simpleshop.data.ProductCategory;
import com.vaadin.incubator.simpleshop.data.Role;
import com.vaadin.incubator.simpleshop.data.User;
import com.vaadin.incubator.simpleshop.facade.FacadeFactory;
import com.vaadin.incubator.simpleshop.util.PasswordUtil;

/**
 * This class's only purpose is to import initial test data to the application.
 * 
 * @author Kim
 * 
 */
public class InitialData {

    public static void init() {
        // Create the admin role
        Role adminRole = new Role();
        adminRole.setName("Administrator");
        for (ActionLock lock : ActionLock.values()) {
            adminRole.addLock(lock);
        }

        FacadeFactory.getFacade().store(adminRole);

        // Root user
        User adminUser = new User();
        adminUser.setUsername("demo");
        adminUser.setPassword(PasswordUtil.generateHashedPassword("demo"));
        adminUser.setName("Joe Demo");
        adminUser.addRole(adminRole);

        FacadeFactory.getFacade().store(adminUser);

        ProductCategory volvo = new ProductCategory();
        volvo.setName("Volvo");
        volvo.setRootCategory(true);

        // Add S-series
        ProductCategory sSeries = new ProductCategory();
        sSeries.setName("S-series");
        volvo.addSubCategory(sSeries);

        Product s40 = new Product();
        s40.setName("S40");
        s40
                .setDescription("Lorem ipsum dolor sit amet, "
                        + "consectetuer adipiscing elit, sed diam nonummy nibh "
                        + "euismod tincidunt ut laoreet dolore magna aliquam erat "
                        + "volutpat. Ut wisi enim ad minim veniam, quis nostrud "
                        + "exerci tation ullamcorper suscipit lobortis nisl ut a"
                        + "liquip ex ea commodo consequat. Duis autem vel eum iriure "
                        + "dolor in hendrerit in vulputate velit esse molestie "
                        + "consequat, vel illum dolore eu feugiat nulla facilisis at "
                        + "vero eros et accumsan et iusto odio dignissim qui blandit "
                        + "praesent luptatum zzril delenit augue duis dolore te "
                        + "feugait nulla facilisi. Nam liber tempor cum soluta nobis "
                        + "eleifend option congue nihil imperdiet doming id quod mazim "
                        + "placerat facer possim assum.");
        s40.setPrice(32000d);

        Product s80 = new Product();
        s80.setName("S80");
        s80.setPrice(38000d);

        sSeries.addProduct(s40);
        sSeries.addProduct(s80);

        // Add V-series
        ProductCategory vSeries = new ProductCategory();
        vSeries.setName("V-series");
        volvo.addSubCategory(vSeries);

        Product v50 = new Product();
        v50.setName("V50");
        v50.setPrice(28000d);

        Product v70 = new Product();
        v70.setName("V70");
        v70.setPrice(45500d);

        vSeries.addProduct(v50);
        vSeries.addProduct(v70);

        // Add XC-series
        ProductCategory xcSeries = new ProductCategory();
        xcSeries.setName("XC-series");
        volvo.addSubCategory(xcSeries);

        Product xc60 = new Product();
        xc60.setName("XC60");
        xc60.setPrice(53200d);

        Product xc70 = new Product();
        xc70.setName("XC70");
        xc70.setPrice(55200d);

        Product xc90 = new Product();
        xc90.setName("XC90");
        xc90.setPrice(67800d);

        xcSeries.addProduct(xc60);
        xcSeries.addProduct(xc70);
        xcSeries.addProduct(xc90);

        // Add C-series
        // Add V-series
        ProductCategory cSeries = new ProductCategory();
        cSeries.setName("C-series");
        volvo.addSubCategory(cSeries);

        Product c30 = new Product();
        c30.setName("C30");
        c30.setPrice(24100d);

        Product c70 = new Product();
        c70.setName("C70");
        c70.setPrice(45600d);

        cSeries.addProduct(c30);
        cSeries.addProduct(c70);

        FacadeFactory.getFacade().store(volvo);
    }
}
