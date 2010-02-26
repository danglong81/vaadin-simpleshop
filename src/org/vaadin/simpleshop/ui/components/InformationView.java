package org.vaadin.simpleshop.ui.components;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.appfoundation.view.ViewItem;
import org.vaadin.simpleshop.data.User;
import org.vaadin.simpleshop.events.EventHandler;
import org.vaadin.simpleshop.events.UserSessionEvent;
import org.vaadin.simpleshop.events.UserSessionListener;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.Icons;
import org.vaadin.simpleshop.ui.admin.AdminWindow;
import org.vaadin.simpleshop.ui.components.cart.CartContentView;
import org.vaadin.simpleshop.util.PermissionsUtil;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This view is placed on the right side of the item browser. This view consist
 * of navigation buttons and the subviews (cart content, user information, ...)
 * 
 * @author Kim
 * 
 */
public class InformationView extends AbstractView<VerticalLayout> implements
        ClickListener, UserSessionListener, ViewContainer {

    private static final long serialVersionUID = 8401760557369059696L;

    // Navigation buttons
    private Button profileBtn;
    private Button shoppingCartBtn;
    private Button adminBtn = null;
    private Button logoutBtn = null;

    // Layout for navigation buttons
    private HorizontalLayout buttonLayout;

    private AbstractView<?> currentView;

    public InformationView() {
        super(new VerticalLayout());
        setStyleName("infoview");

        content.setSizeFull();

        // Initialize navigation buttons
        initButtons();

        // Register child views
        ViewHandler.addView(RegistrationView.class, this);
        ViewHandler.addView(LoginView.class, this);
        ViewHandler.addView(UserProfileView.class, this);
        ViewItem cartContentView = ViewHandler.addView(CartContentView.class,
                this);

        // Set the cart content view as the current view
        currentView = cartContentView.getView();

        // Add buttons to layout
        content.addComponent(buttonLayout);

        // Add current view to layout
        content.addComponent(currentView);

        // The sub view should take as much space as there is available and
        // navigation button's should only reserve as much space as they need.
        content.setExpandRatio(currentView, 1);
        EventHandler.addListener(this);
    }

    /**
     * Initialize navigation buttons
     */
    private void initButtons() {
        // Create the button layout
        buttonLayout = new HorizontalLayout();
        buttonLayout.setHeight(null);
        buttonLayout.setSpacing(true);
        buttonLayout.setMargin(false, true, false, true);

        // Create and add buttons to layout
        profileBtn = new Button(null, this);
        profileBtn.setStyleName(Button.STYLE_LINK);
        profileBtn.setWidth("64px");
        profileBtn.setHeight("64px");
        profileBtn.setIcon(Icons.USER_PROFILE.getResource());
        profileBtn.setDescription(SystemMsg.GENERIC_USER_PROFILE.get());
        buttonLayout.addComponent(profileBtn);

        shoppingCartBtn = new Button(null, this);
        shoppingCartBtn.setStyleName(Button.STYLE_LINK);
        shoppingCartBtn.setWidth("64px");
        shoppingCartBtn.setHeight("64px");
        shoppingCartBtn.setIcon(Icons.SHOPPING_CART.getResource());
        shoppingCartBtn.setDescription(SystemMsg.GENERIC_SHOPPING_CART.get());
        buttonLayout.addComponent(shoppingCartBtn);

        adminBtn = new Button(null, this);
        adminBtn.setStyleName(Button.STYLE_LINK);
        adminBtn.setWidth("64px");
        adminBtn.setHeight("64px");
        adminBtn.setIcon(Icons.ADMIN.getResource());
        adminBtn.setDescription(SystemMsg.GENERIC_ADMINISTRATION.get());

        logoutBtn = new Button(null, this);
        logoutBtn.setStyleName(Button.STYLE_LINK);
        logoutBtn.setWidth("64px");
        logoutBtn.setHeight("64px");
        logoutBtn.setIcon(Icons.LOGOUT.getResource());
        logoutBtn.setDescription(SystemMsg.GENERIC_LOGOUT.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(profileBtn)
                && SessionHandler.get() == null) {
            ViewHandler.activateView(LoginView.class);
        } else if (event.getButton().equals(profileBtn)
                && SessionHandler.get() != null) {
            ViewHandler.activateView(UserProfileView.class);
        } else if (event.getButton().equals(shoppingCartBtn)) {
            ViewHandler.activateView(CartContentView.class);
        } else if (event.getButton().equals(logoutBtn)) {
            User user = (User) SessionHandler.get();
            SessionHandler.logout();
            EventHandler.dispatchLogoutEvent(new UserSessionEvent(user));
        } else if (event.getButton().equals(adminBtn)) {
            getApplication().getMainWindow().addWindow(new AdminWindow());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void loginEvent(UserSessionEvent event) {
        ViewHandler.activateView(CartContentView.class);

        if (PermissionsUtil.isAdmin((User) SessionHandler.get())) {
            buttonLayout.addComponent(adminBtn);
        }

        buttonLayout.addComponent(logoutBtn);
    }

    /**
     * {@inheritDoc}
     */
    public void logoutEvent(UserSessionEvent event) {
        ViewHandler.activateView(CartContentView.class);

        buttonLayout.removeComponent(adminBtn);
        buttonLayout.removeComponent(logoutBtn);

        // If the user logs out, clear the profile view so that if another user
        // uses the same session, he will see his own details and not the
        // previous user's details.
        ViewHandler.removeView(UserProfileView.class);
    }

    @Override
    public void activate(AbstractView<?> view) {
        content.replaceComponent(currentView, view);
        currentView = view;
        content.setExpandRatio(currentView, 1);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }
}
