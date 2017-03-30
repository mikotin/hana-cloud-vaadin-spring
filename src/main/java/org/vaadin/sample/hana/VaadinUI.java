package org.vaadin.sample.hana;

import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import org.vaadin.sample.hana.data.Customer;
import org.vaadin.sample.hana.data.CustomerRepository;
import org.vaadin.sample.hana.util.DBInformation;

import javax.sql.DataSource;

@SpringUI
@Theme("sap")
public class VaadinUI extends UI {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomerRepository repo;

	@Autowired
	private CustomerEditor editor;

	final Grid<Customer> grid;

	final TextField filter;

	private final Button addNewBtn;



	public VaadinUI() {
		this.grid = new Grid<>(Customer.class);

        grid.setStyleName("image-grid");

        Grid.Column<Customer, ExternalResource> foo = grid.addColumn(
                p -> new ExternalResource(p.getImgUrlSafe()),
                new ImageRenderer()).setWidth(180.0);
        foo.setCaption("Image");
        foo.setId("Image");
        foo.setSortable(false);
        grid.setColumns("Image", "firstName", "lastName");
        grid.setColumnOrder("Image", "firstName", "lastName");


		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
		setContent(mainLayout);

        Label info = new Label();

        try {
            DBInformation dbInfo = new DBInformation(dataSource);
            info.setValue("I got db: " + dbInfo.getUrl());
        } catch (Exception e) {
            info.setValue("Failed: " + e.toString());
        }
        mainLayout.addComponent(info);


		grid.setHeight(300, Unit.PIXELS);
        grid.setSizeFull();

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			editor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}
	// end::listCustomers[]

}
