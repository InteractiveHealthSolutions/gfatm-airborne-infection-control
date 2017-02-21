/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

package com.ihsinformatics.gfaicweb.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.gfaicweb.shared.CustomMessage;
import com.ihsinformatics.gfaicweb.shared.ErrorType;
import com.ihsinformatics.gfaicweb.shared.model.PersonAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */

public class PersonAttributeTypeComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable personAttributeTypeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();
	FlexTable validationFlexTable = new FlexTable();

	Grid personAttributeTypeGrid = new Grid(6, 2);

	Label formTitleLabel = new Label("Person Attribute Type");
	Label personAttributeTypeIdLabel = new Label("Person Attribute Type ID:");
	Label personAttributeTypeId = new Label("");
	Label personAttributeTypeNameLabel = new Label(
			"Person Attribute Type Name:");
	Label dataType = new Label("Data Type:");
	Label validationRegex = new Label("Validation Regex:");
	Label required = new Label("Required:");
	Label description = new Label("Description:");

	ListBox dataTypeListBox = new ListBox();
	ListBox validationRegexTypeListBox = new ListBox();

	TextBox personAttributeTypeNameTextBox = new TextBox();
	TextBox validationRegexTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();

	CheckBox requiredCheckBox = new CheckBox();

	ListBox personAttributeTypelistBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	String validationRegexString;

	List<PersonAttributeTypeClient> personAttributeType = new ArrayList<PersonAttributeTypeClient>();
	List<String> dataTypeList = new ArrayList<String>(Arrays.asList("String",
			"Integer", "Double", "Date", "DateTime"));
	ArrayList<String> validationTypeList = new ArrayList<String>(Arrays.asList(
			"list", "regex", "relation", "range"));
	PersonAttributeTypeClient current = null;

	public PersonAttributeTypeComposite() {
		TBR.setCurrentForm("PERSON_ATTRIBUTE_TYPE");
		webService
				.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("ERROR!" + caught.getMessage());
					}

					@Override
					public void onSuccess(List<PrivilegeClient> result) {
						for (PrivilegeClient privilege : result) {
							if (privilege.getPrivilege().equals(
									"CREATE " + TBR.getCurrentFormName())) {
								canCreate = true;
							} else if (privilege.getPrivilege().equals(
									"EDIT " + TBR.getCurrentFormName())) {
								canEdit = true;
							} else if (privilege.getPrivilege().equals(
									"VIEW " + TBR.getCurrentFormName())) {
								canView = true;
							}
						}
						createButton.setEnabled(canCreate);
						saveButton.setEnabled(canEdit);
						deleteButton.setEnabled(false);
					}

				});
		initWidget(personAttributeTypeFlexTable);
		personAttributeTypeFlexTable.setStyleName("flextable");
		formTitleLabel.setStyleName("title");
		personAttributeTypeFlexTable.setWidget(0, 1, formTitleLabel);
		personAttributeTypelistBox.setStyleName("list");
		personAttributeTypeFlexTable
				.setWidget(1, 0, personAttributeTypelistBox);
		personAttributeTypelistBox.setVisibleItemCount(5);
		personAttributeTypelistBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String personAttributeType = personAttributeTypelistBox
						.getItemText(personAttributeTypelistBox
								.getSelectedIndex());
				webService.getPersonAttributeTypeByName(personAttributeType,
						new AsyncCallback<PersonAttributeTypeClient>() {

							@Override
							public void onSuccess(
									PersonAttributeTypeClient result) {
								personAttributeTypeId.setText(result
										.getPersonAttributeTypeId().toString());
								personAttributeTypeNameTextBox.setText(result
										.getAttributeName());
								dataTypeListBox.setSelectedIndex(dataTypeList
										.indexOf(result.getDataType()));
								validationRegexString = result
										.getValidationRegex();
								if (validationRegexString != null) {
									String[] parts = validationRegexString
											.split("=");
									validationRegexTypeListBox
											.setSelectedIndex(validationTypeList
													.indexOf(parts[0]));
									if (parts.length > 1) {
										validationRegexTextBox
												.setText(parts[1]);
									} else {
										validationRegexTextBox.setText("");
									}
								} else {
									validationRegexTypeListBox
											.setSelectedIndex(validationTypeList
													.indexOf(validationRegexString));
								}
								requiredCheckBox.setValue(result.getRequired());
								descriptionTextBox.setText(result
										.getDescription());
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Failed to process Person Attribute Type");
							}
						});
			}
		});
		personAttributeTypeFlexTable.setWidget(1, 1, personAttributeTypeGrid);
		personAttributeTypeIdLabel.setStyleName("label");
		personAttributeTypeGrid.setWidget(0, 0, personAttributeTypeIdLabel);
		personAttributeTypeId.setStyleName("label");
		personAttributeTypeGrid.setWidget(0, 1, personAttributeTypeId);
		personAttributeTypeNameLabel.setStyleName("label");
		personAttributeTypeGrid.setWidget(1, 0, personAttributeTypeNameLabel);
		personAttributeTypeNameTextBox.setVisibleLength(25);
		personAttributeTypeNameTextBox.setMaxLength(45);
		personAttributeTypeNameTextBox.setStyleName("textbox");
		personAttributeTypeGrid.setWidget(1, 1, personAttributeTypeNameTextBox);
		personAttributeTypeGrid.setWidget(2, 0, dataType);
		dataType.setStyleName("label");
		for (String item : dataTypeList) {
			dataTypeListBox.addItem(item);
		}
		personAttributeTypeGrid.setWidget(2, 1, dataTypeListBox);
		personAttributeTypeGrid.setWidget(3, 0, validationRegex);
		validationRegex.setStyleName("label");
		for (String item : validationTypeList) {
			validationRegexTypeListBox.addItem(item);
		}
		validationFlexTable.setWidget(0, 0, validationRegexTypeListBox);
		validationFlexTable.setWidget(0, 1, validationRegexTextBox);
		validationRegexTextBox.setStyleName("textbox");
		personAttributeTypeGrid.setWidget(3, 1, validationFlexTable);
		validationRegexTextBox.setStyleName("textbox");
		personAttributeTypeGrid.setWidget(4, 0, required);
		required.setStyleName("label");
		personAttributeTypeGrid.setWidget(4, 1, requiredCheckBox);
		personAttributeTypeGrid.setWidget(5, 0, description);
		description.setStyleName("label");
		personAttributeTypeGrid.setWidget(5, 1, descriptionTextBox);
		descriptionTextBox.setVisibleLength(35);
		descriptionTextBox.setMaxLength(45);
		descriptionTextBox.setStyleName("textbox");
		personAttributeTypeFlexTable.setWidget(6, 1, bottomFlexTable);
		bottomFlexTable.setWidth("100%");
		createButton.setStyleName("toggle-button");
		bottomFlexTable.setWidget(0, 0, createButton);
		createButton.setWidth("80%");
		saveButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 1, saveButton);
		saveButton.setWidth("80%");
		deleteButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 2, deleteButton);
		deleteButton.setWidth("80%");
		clearButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 3, clearButton);
		clearButton.setWidth("80%");
		closeButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 4, closeButton);
		closeButton.setWidth("80%");
		personAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(1,
				1, HasVerticalAlignment.ALIGN_TOP);
		personAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(1,
				0, HasVerticalAlignment.ALIGN_TOP);
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			personAttributeTypelistBox.setEnabled(false);
			clear(personAttributeTypeGrid);
			personAttributeTypeId.setText(null);
		}
		if (!createButton.isDown()) {
			personAttributeTypelistBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!personAttributeTypeId.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select User Attribute type from list");
				}
			}
		} else if (source == clearButton) {
			createButton.setDown(false);
			personAttributeTypeId.setText("");
			requiredCheckBox.setValue(false);
			clear(personAttributeTypeGrid);
			personAttributeTypelistBox.setEnabled(true);
			personAttributeTypelistBox.setSelectedIndex(0);
		} else if (source == deleteButton) {
			delete();
		} else if (source == closeButton) {
			close();
		}
	}

	@Override
	public void init() {
		createButton.addClickHandler(this);
		saveButton.addClickHandler(this);
		clearButton.addClickHandler(this);
		deleteButton.addClickHandler(this);
		closeButton.addClickHandler(this);
		fill();
	}

	@Override
	public void fill() {
		webService
				.getAllPersonAttributeType(new AsyncCallback<List<PersonAttributeTypeClient>>() {

					@Override
					public void onSuccess(List<PersonAttributeTypeClient> result) {
						for (PersonAttributeTypeClient item : result) {
							personAttributeTypelistBox.addItem(item
									.getAttributeName());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch Person Attributes Type");
					}
				});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		if (get(personAttributeTypeNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ personAttributeTypeNameTextBox.getName());
			valid = false;
		}
		if (!valid) {
			Window.alert(errorMessage.toString());
		}
		return valid;
	}

	@Override
	public void setPrivileges() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		if (validate()) {
			PersonAttributeTypeClient newPersonAttributeType = new PersonAttributeTypeClient();
			newPersonAttributeType
					.setAttributeName(personAttributeTypeNameTextBox.getText());
			newPersonAttributeType.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			newPersonAttributeType.setValidationRegex(validationRegexString);
			newPersonAttributeType.setRequired(requiredCheckBox.getValue());
			newPersonAttributeType.setDescription(descriptionTextBox.getText());
			webService.savePersonAttributeType(newPersonAttributeType,
					new AsyncCallback<PersonAttributeTypeClient>() {

						@Override
						public void onSuccess(PersonAttributeTypeClient result) {
							current = result;
							Window.alert("Person Attribute Type has been saved successfully");
							personAttributeType.add(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save Person Attribute Type");
						}
					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			PersonAttributeTypeClient personAttributeTypeClient = new PersonAttributeTypeClient();
			personAttributeTypeClient.setPersonAttributeTypeId(Integer
					.parseInt(personAttributeTypeId.getText()));
			personAttributeTypeClient
					.setAttributeName(personAttributeTypeNameTextBox.getText());
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			personAttributeTypeClient.setValidationRegex(validationRegexString);
			personAttributeTypeClient.setRequired(requiredCheckBox.getValue());
			personAttributeTypeClient.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			personAttributeTypeClient.setDescription(descriptionTextBox
					.getText());

			webService.updatePersonAttributeType(personAttributeTypeClient,
					new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Successfully updated Person Attribute Type");
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to Update Person Attribute Type");
						}
					});
		}
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
