package org.vaadin.simpleshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ui.components.ItemBrowser;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.UriFragmentUtility;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedEvent;
import com.vaadin.ui.UriFragmentUtility.FragmentChangedListener;

public class UriHandler implements TransactionListener, FragmentChangedListener {

    private static final long serialVersionUID = -7133376911691422990L;

    private final Application application;

    private static ThreadLocal<UriHandler> instance = new ThreadLocal<UriHandler>();

    private final UriFragmentUtility util = new UriFragmentUtility();

    public UriHandler(Application application) {
        this.application = application;
        instance.set(this);
        util.addListener(this);
    }

    @Override
    public void transactionEnd(Application application, Object transactionData) {
        // Clear thread local instance at the end of the transaction
        if (this.application == application) {
            instance.set(null);
        }
    }

    @Override
    public void transactionStart(Application application, Object transactionData) {
        // Set the thread local instance
        if (this.application == application) {
            instance.set(this);
        }
    }

    /**
     * Get the UriFragmentUtility for this application instance
     * 
     * @return
     */
    public static UriFragmentUtility getUriFragmentUtility() {
        return instance.get().util;
    }

    public static void setFragment(String fragment) {
        fragment = fragment.replaceAll("\\s", "-");
        fragment = fragment.replaceAll("åä", "a");
        fragment = fragment.replaceAll("ÅÄ", "A");
        fragment = fragment.replaceAll("ö", "o");
        fragment = fragment.replaceAll("Ö", "O");
        fragment = fragment.replaceAll("[^a-zA-Z0-9\\-]", "");
        instance.get().util.setFragment(fragment, false);
    }

    @Override
    public void fragmentChanged(FragmentChangedEvent source) {
        Pattern category = Pattern.compile("(C)(\\d+)(-.*)");
        Matcher m = category.matcher(util.getFragment());
        if (m.matches()) {
            Long id = Long.parseLong(m.group(2));
            ViewHandler.activateView(ItemBrowser.class, "category", id);
        }

        Pattern product = Pattern.compile("(P)(\\d+)(-.*)");
        m = product.matcher(util.getFragment());
        if (m.matches()) {
            Long id = Long.parseLong(m.group(2));
            ViewHandler.activateView(ItemBrowser.class, "product", id);
        }

    }

}
