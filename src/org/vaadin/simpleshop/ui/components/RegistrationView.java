package org.vaadin.simpleshop.ui.components;

import org.vaadin.simpleshop.SimpleshopApplication;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.controllers.UserController;
import org.vaadin.simpleshop.ui.controllers.UserController.RegistrationError;
import org.vaadin.simpleshop.ui.views.View;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class RegistrationView extends View<VerticalLayout> implements
        ClickListener {

    private static final long serialVersionUID = -1557284716632681422L;

    private final Label feedbackLabel;
    private final TextField username;
    private final TextField password;
    private final TextField verifyPassword;

    private final Button registerBtn;
    private final Button cancelBtn;

    public RegistrationView() {
        super(new VerticalLayout());

        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        feedbackLabel = new Label();
        mainLayout.addComponent(feedbackLabel);

        username = new TextField(SystemMsg.GENERIC_USERNAME.get());
        username.setNullRepresentation("");
        username.setWidth("100%");
        username.focus();
        mainLayout.addComponent(username);

        password = new TextField(SystemMsg.GENERIC_PASSWORD.get());
        password.setSecret(true);
        password.setNullRepresentation("");
        password.setWidth("100%");
        mainLayout.addComponent(password);

        verifyPassword = new TextField(SystemMsg.REGISTER_VERIFY_PASSWORD.get());
        verifyPassword.setSecret(true);
        verifyPassword.setNullRepresentation("");
        verifyPassword.setWidth("100%");
        mainLayout.addComponent(verifyPassword);

        registerBtn = new Button(SystemMsg.REGISTER_REGISTER_BTN.get(), this);
        registerBtn.setSizeUndefined();
        cancelBtn = new Button(SystemMsg.GENERIC_CANCEL.get(), this);
        cancelBtn.setSizeUndefined();

        HorizontalLayout buttonLayout = new HorizontalLayout();
        HorizontalLayout spacer = new HorizontalLayout();
        buttonLayout.setWidth("100%");
        buttonLayout.setSpacing(true);

        buttonLayout.addComponent(spacer);
        buttonLayout.addComponent(cancelBtn);
        buttonLayout.addComponent(registerBtn);

        buttonLayout.setExpandRatio(spacer, 1);

        mainLayout.addComponent(buttonLayout);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(cancelBtn)) {
            username.setValue(null);
            password.setValue(null);
            verifyPassword.setValue(null);
            SimpleshopApplication.getViewHandler()
                    .activateView(LoginView.class);
        } else if (event.getButton().equals(registerBtn)) {
            RegistrationError error = UserController.registerUser(
                    (String) username.getValue(), (String) password.getValue(),
                    (String) verifyPassword.getValue());
            if (error.equals(RegistrationError.REGISTRATION_COMPLETED)) {
                getApplication().getMainWindow().showNotification(
                        error.getMessage(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
                SimpleshopApplication.getViewHandler().activateView(
                        LoginView.class);
            } else {
                feedbackLabel.setValue(error.getMessage());
                password.setValue(null);
                verifyPassword.setValue(null);
            }
        }
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

}
