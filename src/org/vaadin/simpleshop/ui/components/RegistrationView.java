package org.vaadin.simpleshop.ui.components;

import org.vaadin.appfoundation.authentication.util.UserUtil;
import org.vaadin.appfoundation.authentication.util.UserUtil.RegistrationMsg;
import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.lang.EnumMsgMapper;
import org.vaadin.simpleshop.lang.SystemMsg;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

public class RegistrationView extends AbstractView<VerticalLayout> implements
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

        content.setWidth("100%");
        content.setMargin(true);
        content.setSpacing(true);

        feedbackLabel = new Label();
        content.addComponent(feedbackLabel);

        username = new TextField(SystemMsg.GENERIC_USERNAME.get());
        username.setNullRepresentation("");
        username.setWidth("100%");
        username.focus();
        content.addComponent(username);

        password = new TextField(SystemMsg.GENERIC_PASSWORD.get());
        password.setSecret(true);
        password.setNullRepresentation("");
        password.setWidth("100%");
        content.addComponent(password);

        verifyPassword = new TextField(SystemMsg.REGISTER_VERIFY_PASSWORD.get());
        verifyPassword.setSecret(true);
        verifyPassword.setNullRepresentation("");
        verifyPassword.setWidth("100%");
        content.addComponent(verifyPassword);

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

        content.addComponent(buttonLayout);
    }

    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(cancelBtn)) {
            username.setValue(null);
            password.setValue(null);
            verifyPassword.setValue(null);
            ViewHandler.activateView(LoginView.class);
        } else if (event.getButton().equals(registerBtn)) {
            RegistrationMsg error = UserUtil.registerUser((String) username
                    .getValue(), (String) password.getValue(),
                    (String) verifyPassword.getValue());
            if (error.equals(RegistrationMsg.REGISTRATION_COMPLETED)) {
                getApplication().getMainWindow().showNotification(
                        EnumMsgMapper.getMsg(error).get(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
                ViewHandler.activateView(LoginView.class);
            } else {
                feedbackLabel.setValue(EnumMsgMapper.getMsg(error).get());
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
