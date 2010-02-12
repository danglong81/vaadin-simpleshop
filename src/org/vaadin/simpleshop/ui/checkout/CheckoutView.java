package org.vaadin.simpleshop.ui.checkout;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.appfoundation.view.ViewContainer;
import org.vaadin.appfoundation.view.ViewHandler;
import org.vaadin.simpleshop.lang.SystemMsg;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * This view is the main view for the checkout process.
 * 
 * @author Kim
 * 
 */
public class CheckoutView extends AbstractView<VerticalLayout> implements
        ViewContainer {

    private static final long serialVersionUID = 1700835878617090430L;

    // Process steps
    private Label verifyContent;
    private Label chooseDeliveryMethod;
    private Label contactInformation;
    private Label payment;

    private AbstractView<?> currentView = null;

    public CheckoutView() {
        super(new VerticalLayout());
        content.setSizeFull();

        // Initialize the checkout process step captions
        initStepCaptions();

        // Register child views
        ViewHandler.addView(VerifyContentView.class, this);
        ViewHandler.addView(ContactInfoView.class, this);
        ViewHandler.addView(DeliveryMethodView.class, this);
        ViewHandler.addView(PaymentView.class, this);

        ViewHandler.activateView(VerifyContentView.class, true);
    }

    private void initStepCaptions() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setWidth("100%");

        // Add the step captions
        verifyContent = new Label("1. "
                + SystemMsg.CHECKOUT_VERIFY_CONTENT.get());
        layout.addComponent(verifyContent);

        contactInformation = new Label("2. "
                + SystemMsg.CHECKOUT_CONTACT_INFORMATION.get());
        contactInformation.setEnabled(false);
        layout.addComponent(contactInformation);

        chooseDeliveryMethod = new Label("3. "
                + SystemMsg.CHECKOUT_CHOOSE_DELIVERY_METHOD.get());
        chooseDeliveryMethod.setEnabled(false);
        layout.addComponent(chooseDeliveryMethod);

        payment = new Label("4. " + SystemMsg.CHECKOUT_PAYMENT.get());
        payment.setEnabled(false);
        layout.addComponent(payment);

        content.addComponent(layout);
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void activate(AbstractView<?> view) {
        if (currentView == null) {
            content.addComponent(view, 1);
        } else {
            content.replaceComponent(currentView, view);
        }

        currentView = view;
        content.setExpandRatio(view, 1);

        setStep(((AbstractCheckoutStepView) view).getStep());
    }

    private void setStep(int step) {
        verifyContent.setEnabled(step == 1 ? true : false);
        contactInformation.setEnabled(step == 2 ? true : false);
        chooseDeliveryMethod.setEnabled(step == 3 ? true : false);
        payment.setEnabled(step == 4 ? true : false);
    }
}
