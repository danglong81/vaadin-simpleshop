package org.vaadin.simpleshop.ui.components;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.authentication.exceptions.InvalidCredentialsException;
import org.vaadin.appfoundation.authentication.exceptions.PasswordRequirementException;
import org.vaadin.appfoundation.authentication.exceptions.PasswordsDoNotMatchException;
import org.vaadin.appfoundation.authentication.exceptions.TooShortPasswordException;
import org.vaadin.appfoundation.authentication.util.UserUtil;
import org.vaadin.appfoundation.i18n.TranslationUtil;
import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.simpleshop.data.User;
import org.vaadin.simpleshop.lang.SystemMsg;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.Notification;
import com.vaadin.ui.themes.Reindeer;

/**
 * Implementation of the user profile view where the user can change his contact
 * information or password.
 * 
 * @author Kim
 * 
 */
public class UserProfileView extends AbstractView<Panel> implements
        ClickListener {

    private static final long serialVersionUID = -397461258975322708L;

    private FormLayout passwordLayout;

    private Label feedbackLabel;
    private TextField currentPassword;
    private TextField newPassword;
    private TextField verifyNewPassword;

    private Button changePasswordBtn;

    private Form contactInfoForm;
    private Button updateProfileBtn;

    public UserProfileView() {
        super(new Panel());

        getContent().setStyleName(Reindeer.PANEL_LIGHT);
        getContent().setSizeFull();

        getContent().setContent(new VerticalLayout());
        ((Layout) getContent().getContent()).setMargin(true);
        getContent().setWidth("100%");

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

        BeanItem<User> item = new BeanItem<User>((User) SessionHandler.get());
        List<String> visibleFields = new ArrayList<String>();
        visibleFields.add("name");
        visibleFields.add("streetName");
        visibleFields.add("zip");
        visibleFields.add("city");
        visibleFields.add("email");

        contactInfoForm.setItemDataSource(item, visibleFields);

        updateProfileBtn = new Button(SystemMsg.PROFILE_UPDATE_PROFILE.get(),
                this);

        contactInfoForm.getLayout().addComponent(updateProfileBtn);

        getContent().addComponent(contactInfoForm);
    }

    /**
     * Builds the layout for the password changing form
     */
    private void initPasswordFields() {
        passwordLayout = new FormLayout();
        passwordLayout.setCaption(SystemMsg.PROFILE_CHANGE_PASSWORD.get());

        getContent().addComponent(passwordLayout);

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
            try {
                UserUtil.changePassword((User) SessionHandler.get(),
                        (String) currentPassword.getValue(),
                        (String) newPassword.getValue(),
                        (String) verifyNewPassword.getValue());

                // Show a notification that the password has been changed
                getApplication().getMainWindow().showNotification(
                        SystemMsg.PROFILE_PASSWORD_CHANGED.get(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
                // Clear any possible error messages
                feedbackLabel.setValue(null);
            } catch (InvalidCredentialsException e) {
                feedbackLabel.setValue(SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH
                        .get());
            } catch (TooShortPasswordException e) {
                feedbackLabel.setValue(SystemMsg.ACCOUNT_TOO_SHORT_PASSWORD
                        .get());
            } catch (PasswordsDoNotMatchException e) {
                feedbackLabel.setValue(SystemMsg.ACCOUNT_PASSWORDS_DO_NOT_MATCH
                        .get());
            } catch (PasswordRequirementException e) {
                feedbackLabel
                        .setValue(SystemMsg.ACCOUNT_PASSWORD_DOESNT_MEET_REQUIREMENTS
                                .get());
            }
            // No matter what the result was, clear all password fields
            currentPassword.setValue(null);
            newPassword.setValue(null);
            verifyNewPassword.setValue(null);
        } else if (event.getButton().equals(updateProfileBtn)) {
            // Check that the form is filled properly
            if (contactInfoForm.isValid()) {
                // Commit any changes made to the actual object
                contactInfoForm.commit();
                // Store the user object
                UserUtil.storeUser((User) ((BeanItem<?>) contactInfoForm
                        .getItemDataSource()).getBean());
                getApplication().getMainWindow().showNotification(
                        SystemMsg.PROFILE_UPDATED.get(), "",
                        Notification.TYPE_TRAY_NOTIFICATION);
            } else {
                // The form had some errors, so show the error messages to the
                // user.
                contactInfoForm.setValidationVisible(true);
            }
        }
    }

    /**
     * FieldFactory class for generating the fields for the profile form
     * 
     * @author Kim
     * 
     */
    class ProfileFieldFactory extends DefaultFieldFactory {

        private static final long serialVersionUID = -3092775590821924860L;

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            // Create the field
            Field field = super.createField(item, propertyId, uiContext);
            // Set width
            field.setWidth("100%");

            // Some fields have special requirements
            if (propertyId.equals("zip")) {
                field.setWidth("150px");
            } else if (propertyId.equals("name")) {
                field.setRequired(true);
            } else if (propertyId.equals("email")) {
                field.addValidator(new EmailValidator(
                        SystemMsg.PROFILE_INVALID_EMAIL.get()));
            }

            // Show an empty string instead of the text "null"
            ((TextField) field).setNullRepresentation("");

            // Set the translated caption
            field.setCaption(TranslationUtil.getFieldTranslation(User.class,
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

    @Override
    public void deactivated(Object... params) {
        // TODO Auto-generated method stub

    }
}
