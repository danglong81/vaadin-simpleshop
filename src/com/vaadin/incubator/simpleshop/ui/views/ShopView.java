package com.vaadin.incubator.simpleshop.ui.views;

import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.ParentView;
import com.vaadin.incubator.simpleshop.ui.components.InformationView;
import com.vaadin.incubator.simpleshop.ui.components.ItemBrowser;
import com.vaadin.ui.HorizontalLayout;

/**
 * Main view for the shop. Consists of the item browser and the information view
 * (containing the shopping cart content).
 * 
 * @author Kim
 * 
 */
public class ShopView extends View<HorizontalLayout> implements ParentView {

    private static final long serialVersionUID = 5345221919713457136L;

    private final InformationView cart;

    private final ItemBrowser browser;

    public ShopView() {
        super(new HorizontalLayout());
        mainLayout.setSizeFull();
        // Set the caption for this view. The caption is used as the caption in
        // the tabsheet for this tab.
        setCaption(SystemMsg.SHOP_CAPTION.get());
        browser = new ItemBrowser();
        cart = new InformationView();

        mainLayout.addComponent(browser);
        mainLayout.addComponent(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate(View<?> view) {
        // TODO Auto-generated method stub

    }

}
