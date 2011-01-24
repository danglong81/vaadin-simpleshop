package org.vaadin.simpleshop.ui.views;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.View;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.components.InformationView;
import org.vaadin.simpleshop.ui.components.ItemBrowser;

import com.vaadin.ui.HorizontalLayout;

/**
 * Main view for the shop. Consists of the item browser and the information view
 * (containing the shopping cart content).
 * 
 * @author Kim
 * 
 */
public class ShopView extends AbstractView<HorizontalLayout> implements
        ViewContainer {

    private static final long serialVersionUID = 5345221919713457136L;

    private final InformationView cart;
    private final ItemBrowser browser;

    public ShopView() {
        super(new HorizontalLayout());
        getContent().setSizeFull();

        // Set the caption for this view. The caption is used as the caption in
        // the tabsheet for this tab.
        setCaption(SystemMsg.SHOP_CAPTION.get());
        browser = (ItemBrowser) ViewHandler.addView(ItemBrowser.class, this)
                .getView();

        cart = new InformationView();

        getContent().addComponent(browser);
        getContent().addComponent(cart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate(View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deactivate(View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deactivated(Object... params) {
        // TODO Auto-generated method stub

    }

}
