package org.aartekVaadin;

import org.dataVaadin.Registration;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Notification.Type;

@SuppressWarnings("serial")
public class RegistrationForm extends FormLayout {

	TextField firstName = new TextField("First name");
	TextField lastName = new TextField("Last name");
	TextField phone = new TextField("Phone");
	TextField email = new TextField("Email");
	DateField birthDate = new DateField("Birth date");

	Button save = new Button("Save", this::save);
	Button cancel = new Button("Cancel", this::cancel);

	Registration registration;

	// Easily bind forms to beans and manage validation and buffering
	BeanFieldGroup<Registration> formFieldBindings;

	public RegistrationForm() {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		addComponents(actions, firstName, lastName, phone, email, birthDate);
	}

	public void save(Button.ClickEvent event) {
		try {
			formFieldBindings.commit();
			getUI().serviceRegistration.save(registration);
			String msg = String.format("Saved '%s %s'.", registration.getFirstName(), registration.getLastName());
			Notification.show(msg, Type.TRAY_NOTIFICATION);
			getUI().refreshRegistartion();
		} catch (FieldGroup.CommitException e) {
		}
	}

	public void cancel(Button.ClickEvent event) {
		// Place to call business logic.
		Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
		getUI().registrationList.select(null);
	}

	void edit(Registration registration) {
		this.registration = registration;
		if (registration != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(registration, this);
			firstName.focus();
		}
		setVisible(registration != null);
	}

	@Override
	public MyVaadinUI getUI() {
		return (MyVaadinUI) super.getUI();
	}
}
