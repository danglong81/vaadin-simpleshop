package org.vaadin.simpleshop.ui.views;

import org.vaadin.appfoundation.view.AbstractView;
import org.vaadin.simpleshop.lang.SystemMsg;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class TermsView extends AbstractView<VerticalLayout> {

    private static final long serialVersionUID = 1675253504610539696L;

    public TermsView() {
        super(new VerticalLayout());
        setCaption(SystemMsg.TERMS_VIEW.get());
        getContent().addComponent(new Label(SystemMsg.TODO.get()));
    }

    @Override
    public void activated(Object... params) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deactivated(Object... params) {
        // TODO Auto-generated method stub

    }
}