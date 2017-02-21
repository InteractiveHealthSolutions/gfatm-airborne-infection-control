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
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserAttributeTypeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */

public class UserAttributeTypeComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable userAttributeTypeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();
	FlexTable validationFlexTable = new FlexTable();

	Grid userAttributeTypeGrid = new Grid(6, 2);

	Label formTitleLabel = new Label("User Attribute Type");
	Label userAttributeTypeIdLabel = new Label("User Attribute Type ID:");
	Label userAttributeTypeId = new Label("");
	Label userAttributeTypeNameLabel = new Label("User Attribute Type Name:");
	Label dataType = new Label("Data Type:");
	Label validationRegex = new Label("Validation Rule:");
	Label required = new Label("Required:");
	Label description = new Label("Description:");

	TextBox userAttributeTypeNameTextBox = new TextBox();
	TextBox validationRegexTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();

	CheckBox requiredCheckBox = new CheckBox();

	ListBox validationRegexTypeListBox = new ListBox();
	ListBox userAttributeTypelistBox = new ListBox();
	ListBox dataTypeListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");
	String validationRegexString;

	List<UserAttributeTypeClient> userAttributeType = new ArrayList<UserAttributeTypeClient>();
	List<String> dataTypeList = new ArrayList<String>(Arrays.asList("String",
			"Integer", "Double", "Date", "DateTime"));
	ArrayList<String> validationTypeList = new ArrayList<String>(Arrays.asList(
			"list", "regex", "relation", "range"));
	UserAttributeTypeClient current = null;

	public UserAttributeTypeComposite() {
		TBR.setCurrentForm("USER_ATTRIBUTE_TYPE");
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
		initWidget(userAttributeTypeFlexTable);
		formTitleLabel.setStyleName("title");
		userAttributeTypeFlexTable.setWidget(0, 1, formTitleLabel);
		userAttributeTypelistBox.setStyleName("list");
		userAttributeTypeFlexTable.setWidget(1, 0, userAttributeTypelistBox);
		userAttributeTypelistBox.setVisibleItemCount(5);
		userAttributeTypelistBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String userAttributeType = userAttributeTypelistBox
						.getItemText(userAttributeTypelistBox
								.getSelectedIndex());
				webService.getUserAttributeByName(userAttributeType,
						new AsyncCallback<UserAttributeTypeClient>() {

							@Override
							public void onSuccess(UserAttributeTypeClient result) {
								userAttributeTypeId.setText(result
										.getUserAttributeTypeId().toString());
								userAttributeTypeNameTextBox.setText(result
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
								Window.alert("Unable to fetch");
							}
						});

			}
		});
		userAttributeTypeFlexTable.setWidget(1, 1, userAttributeTypeGrid);
		userAttributeTypeIdLabel.setStyleName("label");
		userAttributeTypeGrid.setWidget(0, 0, userAttributeTypeIdLabel);
		userAttributeTypeId.setStyleName("label");
		userAttributeTypeGrid.setWidget(0, 1, userAttributeTypeId);
		userAttributeTypeNameLabel.setStyleName("label");
		userAttributeTypeGrid.setWidget(1, 0, userAttributeTypeNameLabel);
		userAttributeTypeNameTextBox.setVisibleLength(25);
		userAttributeTypeNameTextBox.setMaxLength(45);
		userAttributeTypeNameTextBox.setStyleName("textbox");
		userAttributeTypeGrid.setWidget(1, 1, userAttributeTypeNameTextBox);
		userAttributeTypeGrid.setWidget(2, 0, dataType);
		dataType.setStyleName("label");
		for (String item : dataTypeList) {
			dataTypeListBox.addItem(item);
		}
		userAttributeTypeGrid.setWidget(2, 1, dataTypeListBox);
		userAttributeTypeGrid.setWidget(3, 0, validationRegex);
		validationRegex.setStyleName("label");
		for (String item : validationTypeList) {
			validationRegexTypeListBox.addItem(item);
		}
		validationFlexTable.setWidget(0, 0, validationRegexTypeListBox);
		validationFlexTable.setWidget(0, 1, validationRegexTextBox);
		validationRegexTextBox.setStyleName("textbox");
		userAttributeTypeGrid.setWidget(3, 1, validationFlexTable);
		validationRegexTextBox.setStyleName("textbox");
		userAttributeTypeGrid.setWidget(4, 0, required);
		required.setStyleName("label");
		userAttributeTypeGrid.setWidget(4, 1, requiredCheckBox);
		userAttributeTypeGrid.setWidget(5, 0, description);
		description.setStyleName("label");
		userAttributeTypeGrid.setWidget(5, 1, descriptionTextBox);
		descriptionTextBox.setVisibleLength(35);
		descriptionTextBox.setMaxLength(45);
		descriptionTextBox.setStyleName("textbox");
		userAttributeTypeFlexTable.setWidget(6, 1, bottomFlexTable);
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
		deleteButton.setEnabled(false);
		clearButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 3, clearButton);
		clearButton.setWidth("80%");
		closeButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 4, closeButton);
		closeButton.setWidth("80%");
		userAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(1,
				1, HasVerticalAlignment.ALIGN_TOP);
		userAttributeTypeFlexTable.getCellFormatter().setVerticalAlignment(1,
				0, HasVerticalAlignment.ALIGN_TOP);
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			userAttributeTypelistBox.setEnabled(false);
			clear(userAttributeTypeGrid);
			userAttributeTypeId.setText(null);
		}
		if (!createButton.isDown()) {
			userAttributeTypelistBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!userAttributeTypeId.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select User Attribute type from list");
				}
			}
		} else if (source == clearButton) {
			clear();
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
				.getAllUserAttribute(new AsyncCallback<List<UserAttributeTypeClient>>() {

					@Override
					public void onSuccess(List<UserAttributeTypeClient> result) {
						userAttributeType = result;
						for (UserAttributeTypeClient type : userAttributeType) {
							userAttributeTypelistBox.addItem(type
									.getAttributeName(), type
									.getAttributeName().toString());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch UserAttribute");
					}
				});

	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (get(userAttributeTypeNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ userAttributeTypeNameTextBox.getName());
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
			UserAttributeTypeClient newUserAttributeType = new UserAttributeTypeClient();
			newUserAttributeType.setAttributeName(userAttributeTypeNameTextBox
					.getText());
			newUserAttributeType.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			newUserAttributeType.setValidationRegex(validationRegexString);
			newUserAttributeType.setRequired(requiredCheckBox.getValue());
			newUserAttributeType.setDescription(descriptionTextBox.getText());
			webService.saveUserAttributeType(newUserAttributeType,
					new AsyncCallback<UserAttributeTypeClient>() {

						@Override
						public void onSuccess(UserAttributeTypeClient result) {
							Window.alert("UserAttributeType :"
									+ userAttributeTypeNameTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							userAttributeType.add(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save UserAttributeType");
						}
					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			UserAttributeTypeClient userAttributeTypeClient = new UserAttributeTypeClient();
			userAttributeTypeClient.setUserAttributeTypeId(Integer
					.parseInt(userAttributeTypeId.getText()));
			userAttributeTypeClient
					.setAttributeName(userAttributeTypeNameTextBox.getText());
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			userAttributeTypeClient.setValidationRegex(validationRegexString);
			userAttributeTypeClient.setRequired(requiredCheckBox.getValue());
			userAttributeTypeClient.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			userAttributeTypeClient
					.setDescription(descriptionTextBox.getText());

			webService.updateUserAttributeType(userAttributeTypeClient,
					new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							Window.alert("Successfully updated UserAttributeType");
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to update UserAttributeType");
						}
					});
		}
	}

	@Override
	public void delete() {

	}

	public void clear() {
		createButton.setDown(false);
		userAttributeTypeId.setText("");
		requiredCheckBox.setValue(false);
		clear(userAttributeTypeGrid);
	}
}
