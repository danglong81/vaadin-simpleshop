package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.incubator.simpleshop.CurrentUser;
import com.vaadin.incubator.simpleshop.SimpleshopApplication;
import com.vaadin.incubator.simpleshop.events.UserSessionEvent;
import com.vaadin.incubator.simpleshop.events.UserSessionListener;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.Icons;
import com.vaadin.incubator.simpleshop.ui.ParentView;
import com.vaadin.incubator.simpleshop.ui.ViewHandler;
import com.vaadin.incubator.simpleshop.ui.ViewItem;
import com.vaadin.incubator.simpleshop.ui.admin.AdminWindow;
import com.vaadin.incubator.simpleshop.ui.components.cart.CartContentView;
import com.vaadin.incubator.simpleshop.ui.views.View;
import com.vaadin.incubator.simpleshop.util.PermissionsUtil;
import com.vaadin.incubator.simpleshop.util.SessionUtil;
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
public class InformationView extends View<VerticalLayout> implements
        ClickListener, UserSessionListener, ParentView {

    private static final long serialVersionUID = 8401760557369059696L;

    // Navigation buttons
    private Button profileBtn;
    private Button shoppingCartBtn;
    private Button adminBtn = null;
    private Button logoutBtn = null;

    // Layout for navigation buttons
    private HorizontalLayout buttonLayout;

    private View<?> currentView;

    public InformationView() {
        super(new VerticalLayout());
        setStyleName("infoview");

        mainLayout.setSizeFull();

        // Initialize navigation buttons
        initButtons();

        // Register child views
        ViewHandler vh = SimpleshopApplication.getViewHandler();
        vh.addView(RegistrationView.class, this);
        vh.addView(LoginView.class, this);
        vh.addView(UserProfileView.class, this);
        ViewItem cartContentView = vh.addView(CartContentView.class, this);

        // Set the cart content view as the current view
        currentView = cartContentView.getView();

        // Add buttons to layout
        mainLayout.addComponent(buttonLayout);

        // Add current view to layout
        mainLayout.addComponent(currentView);

        // The sub view should take as much space as there is available and
        // navigation button's should only reserve as much space as they need.
        mainLayout.setExpandRatio(currentView, 1);
        SimpleshopApplication.getEventHandler().addListener(this);
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
        if (event.getButton().equals(profileBtn) && CurrentUser.get() == null) {
            SimpleshopApplication.getViewHandler()
                    .activateView(LoginView.class);
        } else if (event.getButton().equals(profileBtn)
                && CurrentUser.get() != null) {
            SimpleshopApplication.getViewHandler().activateView(
                    UserProfileView.class);
        } else if (event.getButton().equals(shoppingCartBtn)) {
            SimpleshopApplication.getViewHandler().activateView(
                    CartContentView.class);
        } else if (event.getButton().equals(logoutBtn)) {
            SessionUtil.logout();
        } else if (event.getButton().equals(adminBtn)) {
            getApplication().getMainWindow().addWindow(new AdminWindow());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void loginEvent(UserSessionEvent event) {
        SimpleshopApplication.getViewHandler().activateView(
                CartContentView.class);

        if (PermissionsUtil.isAdmin(CurrentUser.get())) {
            buttonLayout.addComponent(adminBtn);
        }

        buttonLayout.addComponent(logoutBtn);
    }

    /**
     * {@inheritDoc}
     */
    public void logoutEvent(UserSessionEvent event) {
        SimpleshopApplication.getViewHandler().activateView(
                CartContentView.class);

        buttonLayout.removeComponent(adminBtn);
        buttonLayout.removeComponent(logoutBtn);

        // If the user logs out, clear the profile view so that if another user
        // uses the same session, he will see his own details and not the
        // previous user's details.
        SimpleshopApplication.getViewHandler()
                .removeView(UserProfileView.class);
    }

    @Override
    public void activate(View<?> view) {
        mainLayout.replaceComponent(currentView, view);
        currentView = view;
        mainLayout.setExpandRatio(currentView, 1);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }
}
