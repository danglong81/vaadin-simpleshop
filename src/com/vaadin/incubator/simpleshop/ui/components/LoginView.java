package com.vaadin.incubator.simpleshop.ui.components;

import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.Action.Handler;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.util.SessionUtil;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

/**
 * This class contains the layout for the login view. This class is based on a
 * Panel for us to be able to catch the enter press.
 * 
 * @author Kim
 * 
 */
public class LoginView extends Panel implements ClickListener, Handler {

    private static final long serialVersionUID = -7254800457211263522L;

    private Label feedbackLabel;
    private TextField username;
    private TextField password;

    private Button loginBtn;

    private Button forgotPasswordBtn;
    private Button registerBtn;

    private final ShortcutAction enterKey = new ShortcutAction("Login",
            ShortcutAction.KeyCode.ENTER, null);

    public LoginView() {
        // Remove all extra stryling
        setStyleName(STYLE_LIGHT);

        // The inner layout should be a VerticalLayout
        VerticalLayout panelLayout = new VerticalLayout();
        panelLayout.setMargin(true);
        panelLayout.setSpacing(true);

        setContent(panelLayout);

        initForm();
    }

    /**
     * Initializes the login form
     */
    private void initForm() {
        // Create a label where we can add feedback messages, such as
        // "login failed"
        feedbackLabel = new Label();

        addComponent(feedbackLabel);

        // Add the username textfield. It should be focused by default
        username = new TextField(SystemMsg.GENERIC_USERNAME.get());
        username.setWidth("100%");
        username.focus();
        addComponent(username);

        // Add the password textfield. Set the type to secret
        password = new TextField(SystemMsg.GENERIC_PASSWORD.get());
        password.setSecret(true);
        password.setWidth("100%");
        addComponent(password);

        // Create a layout containing the buttons we need
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        buttons.setWidth("100%");

        // Add the "forgot password"-link
        forgotPasswordBtn = new Button(SystemMsg.LOGIN_FORGOT_PASSWORD.get(),
                this);
        forgotPasswordBtn.setStyleName(Button.STYLE_LINK);
        buttons.addComponent(forgotPasswordBtn);

        // Add the "register"-link
        registerBtn = new Button(SystemMsg.LOGIN_REGISTER_BTN.get(), this);
        registerBtn.setStyleName(Button.STYLE_LINK);
        buttons.addComponent(registerBtn);

        // Add the Login button
        loginBtn = new Button(SystemMsg.LOGIN_LOGIN_BTN.get(), this);
        buttons.addComponent(loginBtn);
        buttons.setComponentAlignment(loginBtn, Alignment.MIDDLE_RIGHT);

        addComponent(buttons);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(loginBtn)) {
            // The login-button has been clicked, perform the login action.
            login();
        } else if (event.getButton().equals(forgotPasswordBtn)) {
            // TODO: switch to forgot password view
        } else if (event.getButton().equals(registerBtn)) {

        }
    }

    /**
     * Method which tries to log in the user
     */
    protected void login() {
        // Get the username and password from the textfields
        // Try to log in the user
        boolean successfull = SessionUtil.login((String) username.getValue(),
                (String) password.getValue());
        // If the login failed, give the user some feedback and reset the fields
        if (!successfull) {
            feedbackLabel.setValue(SystemMsg.LOGIN_LOGIN_FAILED.get());
            username.setValue("");
            password.setValue("");
        } else {
            // Login was successfull, hence remove the enter listener
            // TODO
        }
    }

    public Action[] getActions(Object target, Object sender) {
        return new Action[] { enterKey };
    }

    public void handleAction(Action action, Object sender, Object target) {
        login();
    }
}