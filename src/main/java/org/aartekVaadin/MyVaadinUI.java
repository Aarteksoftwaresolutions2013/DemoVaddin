package org.aartekVaadin;

import javax.servlet.annotation.WebServlet;

import org.dataVaadin.Registration;
import org.dataVaadin.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	@Autowired
	public RegistrationService serviceRegistration;

	@Autowired
	public RegistrationForm registrationForm;

	@WebServlet(urlPatterns = "/*")
	@VaadinServletConfiguration(ui = MyVaadinUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		configureComponents();
		buildLayout();
	}

	TextField filter = new TextField();
	Grid registrationList = new Grid();
	Button newRegistration = new Button("New Registration");

	private void configureComponents() {
		newRegistration.addClickListener(e -> registrationForm.edit(new Registration()));
		filter.setInputPrompt("Filter by Name...");
		filter.addTextChangeListener(e -> refreshRegistartion(e.getText()));
		registrationList.setContainerDataSource(new BeanItemContainer<>(Registration.class));
		registrationList.setColumnOrder("firstName", "lastName", "email", "phone");
		registrationList.removeColumn("id");
		registrationList.removeColumn("birthDate");
		registrationList.setSelectionMode(Grid.SelectionMode.SINGLE);
		registrationList.addSelectionListener(
						e -> registrationForm.edit((Registration) registrationList.getSelectedRow()));
		refreshRegistartion();
	}

	private void buildLayout() {

		HorizontalLayout actions = new HorizontalLayout(filter, newRegistration);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, registrationList);
		left.setSizeFull();
		registrationList.setSizeFull();
		left.setExpandRatio(registrationList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, registrationForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);
		// Split and allow resizing
		setContent(mainLayout);
	}

	void refreshRegistartion() {
		refreshRegistartion(filter.getValue());
	}

	private void refreshRegistartion(String stringFilter) {
		registrationList.setContainerDataSource(
						new BeanItemContainer<>(Registration.class, serviceRegistration.findAll(stringFilter)));
		registrationForm.setVisible(false);
	}

}
