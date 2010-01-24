package org.vaadin.simpleshop;

import org.vaadin.simpleshop.data.ActionLock;
import org.vaadin.simpleshop.data.DeliveryMethod;
import org.vaadin.simpleshop.data.Price;
import org.vaadin.simpleshop.data.Product;
import org.vaadin.simpleshop.data.ProductCategory;
import org.vaadin.simpleshop.data.Role;
import org.vaadin.simpleshop.data.User;
import org.vaadin.simpleshop.data.Vat;
import org.vaadin.simpleshop.facade.FacadeFactory;
import org.vaadin.simpleshop.util.PasswordUtil;

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

        // Initialize VAT objects
        Vat vat0 = new Vat();
        vat0.setPercentage(0);
        vat0.setName("VAT0");
        FacadeFactory.getFacade().store(vat0);

        Vat vat8 = new Vat();
        vat8.setPercentage(8);
        vat8.setName("VAT8");
        FacadeFactory.getFacade().store(vat8);

        Vat vat17 = new Vat();
        vat17.setPercentage(17);
        vat17.setName("VAT17");
        FacadeFactory.getFacade().store(vat17);

        Vat vat22 = new Vat();
        vat22.setPercentage(22);
        vat22.setName("VAT22");
        FacadeFactory.getFacade().store(vat22);

        ProductCategory volvo = new ProductCategory();
        volvo.setName("Volvo");
        volvo.setRootCategory(true);

        // Create a couple of delivery methods
        DeliveryMethod dm1 = new DeliveryMethod();
        dm1.setName("DHL");
        dm1.setDescription("Your order will be delivered to "
                + "your door step by DHL");
        Price price = new Price("dhl", 25, vat22);
        dm1.setPrice(price);
        FacadeFactory.getFacade().store(dm1);

        DeliveryMethod dm2 = new DeliveryMethod();
        dm2.setName("Postal");
        dm2.setDescription("Your order will be delivered to "
                + "the closest postal office");
        price = new Price("post", 15, vat22);
        dm2.setPrice(price);
        FacadeFactory.getFacade().store(dm2);

        DeliveryMethod dm3 = new DeliveryMethod();
        dm3.setName("Pickup");
        dm3.setDescription("You want to pick up your delivery from "
                + "our retail store");
        price = new Price("post", 0, vat22);
        dm3.setPrice(price);
        FacadeFactory.getFacade().store(dm3);

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
        s40.setPrice(new Price("", 32000d, vat22));

        Product s80 = new Product();
        s80.setName("S80");
        s80.setPrice(new Price("", 38000d, vat22));

        sSeries.addProduct(s40);
        sSeries.addProduct(s80);

        // Add V-series
        ProductCategory vSeries = new ProductCategory();
        vSeries.setName("V-series");
        volvo.addSubCategory(vSeries);

        Product v50 = new Product();
        v50.setName("V50");
        v50.setPrice(new Price("", 28000d, vat22));

        Product v70 = new Product();
        v70.setName("V70");
        v70.setPrice(new Price("", 45500d, vat22));

        vSeries.addProduct(v50);
        vSeries.addProduct(v70);

        // Add XC-series
        ProductCategory xcSeries = new ProductCategory();
        xcSeries.setName("XC-series");
        volvo.addSubCategory(xcSeries);

        Product xc60 = new Product();
        xc60.setName("XC60");
        xc60.setPrice(new Price("", 53200d, vat22));

        Product xc70 = new Product();
        xc70.setName("XC70");
        xc70.setPrice(new Price("", 55200d, vat22));

        Product xc90 = new Product();
        xc90.setName("XC90");
        xc90.setPrice(new Price("", 67800d, vat22));

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
        c30.setPrice(new Price("", 24100d, vat22));

        Product c70 = new Product();
        c70.setName("C70");
        c70.setPrice(new Price("", 45600d, vat22));

        cSeries.addProduct(c30);
        cSeries.addProduct(c70);

        FacadeFactory.getFacade().store(volvo);
    }
}
