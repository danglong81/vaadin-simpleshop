package com.vaadin.incubator.simpleshop.ui.components;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.incubator.simpleshop.CurrentUser;
import com.vaadin.incubator.simpleshop.data.User;
import com.vaadin.incubator.simpleshop.lang.SystemMsg;
import com.vaadin.incubator.simpleshop.ui.GenericFieldFactory;
import com.vaadin.incubator.simpleshop.ui.controllers.UserController;
import com.vaadin.incubator.simpleshop.ui.controllers.UserController.ProfileError;
import com.vaadin.incubator.simpleshop.ui.views.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;

/**
 * Implementation of the user profile view where the user can change his contact
 * information or password.
 * 
 * @author Kim
 * 
 */
public class UserProfileView extends View<VerticalLayout> implements
        ClickListener {

    private static final long serialVersionUID = -397461258975322708L;

    private FormLayout passwordLayout;

    private final Panel content;

    private Label feedbackLabel;
    private TextField currentPassword;
    private TextField newPassword;
    private TextField verifyNewPassword;

    private Button changePasswordBtn;

    private Form contactInfoForm;
    private Button updateProfileBtn;

    public UserProfileView() {
        super(new VerticalLayout());

        content = new Panel();
        content.setStyleName(Panel.STYLE_LIGHT);
        content.setWidth("100%");

        mainLayout.setMargin(true);
        mainLayout.setWidth("100%");

        setCompositionRoot(content);
        content.setContent(mainLayout);

        initContactInfoForm();
        initPasswordFields();
    }

    /**
     * Initialize the form for updating user contact information
     */
    private void initContactInfoForm() {
        contactInfoForm = new Form();
        contactInfoForm.setCaption(SystemMsg.PROFILE_CHANGE_CONTACT_INFORMATION
                .get());
        contactInfoForm.setFormFieldFactory(new ProfileFieldFactory());

        BeanItem item = new BeanItem(CurrentUser.get());
        List<String> visibleFields = new ArrayList<String>();
        visibleFields.add("name");

        contactInfoForm.setItemDataSource(item, visibleFields);

        updateProfileBtn = new Button(SystemMsg.PROFILE_UPDATE_PROFILE.get(),
                this);

        contactInfoForm.getLayout().addComponent(updateProfileBtn);

        mainLayout.addComponent(contactInfoForm);
    }

    /**
     * Builds the layout for the password changing form
     */
    private void initPasswordFields() {
        passwordLayout = new FormLayout();
        passwordLayout.setCaption(SystemMsg.PROFILE_CHANGE_PASSWORD.get());

        mainLayout.addComponent(passwordLayout);

        feedbackLabel = new Label();
        feedbackLabel.setStyleName("error");
        passwordLayout.addComponent(feedbackLabel);

        currentPassword = new TextField(SystemMsg.PROFILE_CURRENT_PASSWORD
                .get());
        currentPassword.setSecret(true);
        currentPassword.setNullRepresentation("");
        currentPassword.setWidth("100%");
        passwordLayout.addComponent(currentPassword);

        newPassword = new TextField(SystemMsg.PROFILE_NEW_PASSWORD.get());
        newPassword.setSecret(true);
        newPassword.setNullRepresentation("");
        newPassword.setWidth("100%");
        passwordLayout.addComponent(newPassword);

        verifyNewPassword = new TextField(SystemMsg.PROFILE_VERIFY_NEW_PASSWORD
                .get());
        verifyNewPassword.setSecret(true);
        verifyNewPassword.setNullRepresentation("");
        verifyNewPassword.setWidth("100%");
        passwordLayout.addComponent(verifyNewPassword);

        changePasswordBtn = new Button(SystemMsg.PROFILE_CHANGE_PWD_BTN.get(),
                this);
        passwordLayout.addComponent(changePasswordBtn);
    }

    @Override
    public void buttonClick(ClickEvent event) {
        // Check which button was pressed so that we know which action to
        // perform
        if (event.getButton().equals(changePasswordBtn)) {
            ProfileError msg = UserController.changePassword(CurrentUser.get(),
                    (String) currentPassword.getValue(), (String) newPassword
                            .getValue(), (String) verifyNewPassword.getValue());

            // No matter what the result was, clear all password fields
            currentPassword.setValue(null);
            newPassword.setValue(null);
            verifyNewPassword.setValue(null);

            if (msg.equals(ProfileError.PASSWORD_CHANGED)) {
                // Show a notification that the password has been changed
                getApplication().getMainWindow().showNotification(
                        msg.getMessage(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
                // Clear any possible error messages
                feedbackLabel.setValue(null);
            } else {
                // Show error message to the user
                feedbackLabel.setValue(msg.getMessage());
            }
        } else if (event.getButton().equals(updateProfileBtn)) {
            // Commit any changes made to the actual object
            contactInfoForm.commit();
            // Store the user object
            UserController.storeUser((User) ((BeanItem) contactInfoForm
                    .getItemDataSource()).getBean());
            getApplication().getMainWindow().showNotification(
                    SystemMsg.PROFILE_UPDATED.get(), "",
                    Notification.TYPE_TRAY_NOTIFICATION);
        }
    }

    /**
     * FieldFactory class for generating the fields for the profile form
     * 
     * @author Kim
     * 
     */
    class ProfileFieldFactory extends GenericFieldFactory {

        private static final long serialVersionUID = -3092775590821924860L;

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            // Create the field
            Field field = super.createField(item, propertyId, uiContext);
            // Set width
            field.setWidth("100%");

            // Set the translated caption
            field.setCaption(getFieldTranslation(User.class,
                    (String) propertyId));

            return field;
        }
    }

    @Override
    public void activated(Object... params) {
        // Clear the password fields, since we don't want anyone to forget that
        // they had filled the forms
        currentPassword.setValue(null);
        newPassword.setValue(null);
        verifyNewPassword.setValue(null);
    }
}
