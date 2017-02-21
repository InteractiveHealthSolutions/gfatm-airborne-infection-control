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
import com.ihsinformatics.gfaicweb.shared.model.LocationAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class LocationAttributeTypeComposite extends AbstractComposite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable locationAttributeTypeFlexTable = new FlexTable();
	FlexTable validationFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	Grid locationAttributeTypeGrid = new Grid(6, 2);

	Label formTitleLabel = new Label("Location Attribute Type");
	Label locationAttributeTypeIdLabel = new Label(
			"Location Attribute Type ID:");
	Label locationAttributeTypeId = new Label();
	Label locationAttributeTypeNameLabel = new Label(
			"Location Attribute Type Name:");
	Label validationRegex = new Label("Validation Rule:");
	Label required = new Label("Required:");
	Label description = new Label("Description:");
	Label dataType = new Label("Data Type:");

	TextBox locationAttributeTypeNameTextBox = new TextBox();
	TextBox validationRegexTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();

	CheckBox requiredCheckBox = new CheckBox();

	ListBox locationAttributeTypelistBox = new ListBox();
	ListBox validationRegexTypeListBox = new ListBox();
	ListBox dataTypeListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	String validationRegexString;

	ArrayList<String> dataTypeList = new ArrayList<String>();
	ArrayList<String> validationTypeList = new ArrayList<String>();

	ToggleButton createButton = new ToggleButton("Create New");

	List<LocationAttributeTypeClient> locationAttributeType = new ArrayList<LocationAttributeTypeClient>();
	LocationAttributeTypeClient current = null;

	public LocationAttributeTypeComposite() {
		TBR.setCurrentForm("LOCATION_ATTRIBUTE_TYPE");
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
		initWidget(locationAttributeTypeFlexTable);
		formTitleLabel.setStyleName("title");
		locationAttributeTypeFlexTable.setWidget(0, 1, formTitleLabel);
		locationAttributeTypelistBox.setStyleName("list");
		locationAttributeTypelistBox.setHeight("250px");
		locationAttributeTypelistBox.setWidth("150px");
		locationAttributeTypelistBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String locationAttributeTypeName = locationAttributeTypelistBox
						.getItemText(locationAttributeTypelistBox
								.getSelectedIndex());
				locationAttributeTypeNameTextBox
						.setText(locationAttributeTypeName);
				webService.getLocationAttributeTypeFromAttributeName(
						locationAttributeTypeName,
						new AsyncCallback<LocationAttributeTypeClient>() {
							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(
									LocationAttributeTypeClient result) {
								locationAttributeTypeId.setText(result
										.getLocationAttributeTypeId()
										.toString());

								validationRegexString = result
										.getValidationRegex();
								String[] parts = validationRegexString
										.split("=");
								validationRegexTypeListBox
										.setSelectedIndex(validationTypeList
												.indexOf(parts[0]));
								validationRegexTextBox.setText(parts[1]);
								requiredCheckBox.setValue(result.getRequired());
								dataTypeListBox.setSelectedIndex(dataTypeList
										.indexOf(result.getDataType()));
								descriptionTextBox.setText(result
										.getDescription());
							}
						});
			}
		});

		locationAttributeTypeFlexTable.setWidget(1, 0,
				locationAttributeTypelistBox);
		locationAttributeTypelistBox.setVisibleItemCount(5);
		locationAttributeTypeFlexTable.setWidget(1, 1,
				locationAttributeTypeGrid);
		locationAttributeTypeIdLabel.setStyleName("label");
		locationAttributeTypeGrid.setWidget(0, 0, locationAttributeTypeIdLabel);
		locationAttributeTypeId.setStyleName("label");
		locationAttributeTypeGrid.setWidget(0, 1, locationAttributeTypeId);
		locationAttributeTypeNameLabel.setStyleName("label");
		locationAttributeTypeGrid.setWidget(1, 0,
				locationAttributeTypeNameLabel);
		locationAttributeTypeNameTextBox.setVisibleLength(25);
		locationAttributeTypeNameTextBox.setMaxLength(45);
		locationAttributeTypeNameTextBox.setStyleName("textbox");
		locationAttributeTypeGrid.setWidget(1, 1,
				locationAttributeTypeNameTextBox);
		locationAttributeTypeGrid.setWidget(2, 0, validationRegex);
		validationRegex.setStyleName("label");
		validationTypeList.add("List");
		validationTypeList.add("Regex");
		validationTypeList.add("Relation");
		validationTypeList.add("Range");
		for (int j = 0; j < validationTypeList.size(); j++) {
			validationRegexTypeListBox.addItem(validationTypeList.get(j));
		}
		validationFlexTable.setWidget(0, 0, validationRegexTypeListBox);
		validationFlexTable.setWidget(0, 1, validationRegexTextBox);
		validationRegexTextBox.setStyleName("textbox");
		locationAttributeTypeGrid.setWidget(2, 1, validationFlexTable);
		locationAttributeTypeGrid.setWidget(3, 0, required);
		required.setStyleName("label");
		locationAttributeTypeGrid.setWidget(3, 1, requiredCheckBox);
		locationAttributeTypeGrid.setWidget(4, 0, dataType);
		dataType.setStyleName("label");
		locationAttributeTypeGrid.setWidget(4, 1, dataTypeListBox);
		dataTypeList.add("Date");
		dataTypeList.add("Time");
		dataTypeList.add("DateTime");
		dataTypeList.add("Integer");
		dataTypeList.add("Double");
		dataTypeList.add("Boolean");
		dataTypeList.add("Character");
		dataTypeList.add("String");
		for (int i = 0; i < dataTypeList.size(); i++) {
			dataTypeListBox.addItem(dataTypeList.get(i));
		}
		locationAttributeTypeGrid.setWidget(5, 0, description);
		description.setStyleName("label");
		locationAttributeTypeGrid.setWidget(5, 1, descriptionTextBox);
		descriptionTextBox.setVisibleLength(35);
		descriptionTextBox.setMaxLength(45);
		descriptionTextBox.setStyleName("textbox");
		locationAttributeTypeFlexTable.setWidget(6, 1, bottomFlexTable);
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
		locationAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(
				1, 1, HasVerticalAlignment.ALIGN_TOP);
		locationAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(
				1, 0, HasVerticalAlignment.ALIGN_TOP);
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			clear(locationAttributeTypeGrid);
			locationAttributeTypeId.setText(null);
			locationAttributeTypelistBox.setEnabled(false);
		}
		if (!createButton.isDown()) {
			locationAttributeTypelistBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!locationAttributeTypeId.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select Location Attribute type from list");
				}
			}
		} else if (source == clearButton) {
			locationAttributeTypeId.setText(null);
			requiredCheckBox.setValue(false);
			dataTypeListBox.setItemSelected(0, true);
			clear(locationAttributeTypeGrid);
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
				.getLocationAttributeType(new AsyncCallback<List<LocationAttributeTypeClient>>() {
					@Override
					public void onSuccess(
							List<LocationAttributeTypeClient> result) {
						locationAttributeType = result;
						for (LocationAttributeTypeClient type : locationAttributeType) {
							locationAttributeTypelistBox.addItem(type
									.getAttributeName(), type
									.getLocationAttributeTypeId().toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Unable to fetch Location Attribute Type"
								+ "\n" + arg0.getMessage());
					}

				});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		if (get(locationAttributeTypeNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ locationAttributeTypeNameTextBox.getName());
			valid = false;
		}
		if (!valid) {
			Window.alert(errorMessage.toString());
		}
		return valid;
	}

	@Override
	public void setPrivileges() {
	}

	@Override
	public void save() {
		if (validate()) {
			LocationAttributeTypeClient newLocationAttributeType = new LocationAttributeTypeClient();
			newLocationAttributeType
					.setAttributeName(locationAttributeTypeNameTextBox
							.getText());
			newLocationAttributeType.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			newLocationAttributeType.setValidationRegex(validationRegexString);
			newLocationAttributeType.setRequired(requiredCheckBox.getValue());
			newLocationAttributeType.setDescription(descriptionTextBox
					.getText());
			webService.saveLocationAttributType(newLocationAttributeType,
					new AsyncCallback<LocationAttributeTypeClient>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR, Failed to save Location attribute Type "
									+ "\n" + caught.getMessage());

						}

						@Override
						public void onSuccess(LocationAttributeTypeClient result) {
							Window.alert(" Location Attribute type :"
									+ locationAttributeTypeNameTextBox
											.getText()
									+ " has been added suceessfully");
							current = result;
							locationAttributeType.add(result);
						}

					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			LocationAttributeTypeClient locationAttributeTypeClient = new LocationAttributeTypeClient();
			locationAttributeTypeClient.setLocationAttributeTypeId(Integer
					.parseInt(locationAttributeTypeId.getText()));
			locationAttributeTypeClient
					.setAttributeName(locationAttributeTypeNameTextBox
							.getText());
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			locationAttributeTypeClient
					.setValidationRegex(validationRegexString);
			locationAttributeTypeClient
					.setRequired(requiredCheckBox.getValue());
			locationAttributeTypeClient.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			locationAttributeTypeClient.setDescription(descriptionTextBox
					.getText());

			webService.updateLocationAttributeType(locationAttributeTypeClient,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR, Failed to update Location attribute Type "
									+ "\n" + caught.getMessage());

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert(" Location Attribute type :"
									+ locationAttributeTypeNameTextBox
											.getText()
									+ " has been updated suceessfully");

						}
					});
		}
	}

	@Override
	public void delete() {
		// Delete restriction for meta data entities
	}

}
