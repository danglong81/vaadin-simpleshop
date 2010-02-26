package org.vaadin.simpleshop.ui.checkout;

import org.vaadin.appfoundation.authentication.SessionHandler;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.ShoppingCart;
import org.vaadin.simpleshop.data.Order;
import org.vaadin.simpleshop.data.User;
import org.vaadin.simpleshop.lang.SystemMsg;
import org.vaadin.simpleshop.ui.GenericFieldFactory;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.TextField;

/**
 * This is the view for collecting contact information of the person placing the
 * order and information about the delivery address.
 * 
 * @author Kim
 * 
 */
public class ContactInfoView extends AbstractCheckoutStepView {

    private static final long serialVersionUID = 9042506874851287277L;

    private final Form form;

    public ContactInfoView() {
        ShoppingCart.prefillContactInformation((User) SessionHandler.get());

        // Get the current order
        Order order = ShoppingCart.getOrder();

        // Create a BeanItem of the order
        BeanItem<Order> item = new BeanItem<Order>(order);

        // Create a new form and assign the BeanItem as the form's data source.
        form = new Form();
        form.setItemDataSource(item);

        form.setCaption(SystemMsg.CHECKOUT_DELIVERY_ADDRESS.get());

        // Set the form field factory which will provide us with the translated
        // field captions.
        form.setFormFieldFactory(new OrderFieldFactory());

        // Define which fields we are showing in the form
        form.setVisibleItemProperties(new Object[] { "name", "streetName",
                "zip", "city", "phone", "email", "comments" });

        mainPanel.addComponent(form);
    }

    @Override
    public void activated(Object... params) {
        // Focus the first field in the form when the view is activated
        form.focus();
    }

    @Override
    protected void next() {
        if (form.isValid()) {
            form.commit();
            ViewHandler.activateView(DeliveryMethodView.class);
        } else {
            form.setValidationVisible(true);
        }
    }

    @Override
    protected void previous() {
        ViewHandler.activateView(VerifyContentView.class);
    }

    /**
     * Field factory for the order form.
     * 
     * @author Kim
     * 
     */
    private class OrderFieldFactory extends GenericFieldFactory {

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            // Create the field
            Field field = super.createField(item, propertyId, uiContext);

            // Set the translated caption
            field.setCaption(getFieldTranslation(Order.class,
                    (String) propertyId));

            field.setWidth("350px");

            // We don't want to show the ugly "null"-text in the TextFields.
            // We'll rather show an empty string.
            ((TextField) field).setNullRepresentation("");

            // By default, all fields are required
            field.setRequired(true);

            if (propertyId.equals("email")) {
                // Add a validator to make sure that the email address is of
                // valid format.
                field.addValidator(new EmailValidator(
                        SystemMsg.CHECKOUT_INVALID_EMAIL.get()));
                // Email address is an optional field
                field.setRequired(false);
            } else if (propertyId.equals("phone")) {
                // Phone numer is an optional field
                field.setRequired(false);
            } else if (propertyId.equals("comments")) {
                ((TextField) field).setRows(6);
                // Comments are optional
                field.setRequired(false);
            } else if (propertyId.equals("zip")) {
                field.setWidth("150px");
            }

            return field;
        }

        private static final long serialVersionUID = 4382706597722765118L;

    }

    @Override
    public int getStep() {
        return 2;
    }
}
