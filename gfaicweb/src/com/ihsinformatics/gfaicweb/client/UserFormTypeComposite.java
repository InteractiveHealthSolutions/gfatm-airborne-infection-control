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
import com.ihsinformatics.gfaicweb.shared.model.UserFormTypeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */

public class UserFormTypeComposite extends AbstractComposite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable userFormTypeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	// Grid userFormTypeGrid = new Grid(3, 2);

	Grid userFormTypeGrid = new Grid(2, 2);

	Label formTitleLabel = new Label("User Form Types:");
	Label userFormTypeIdLabel = new Label("UserForm Type ID:");
	Label userFormTypeId = new Label("");
	Label userFormTypeNameLabel = new Label("UserForm Type Name:");
	Label userFormTypeDescription = new Label("Description:");

	TextBox userFormTypeNameTextBox = new TextBox();
	TextBox userFormTypeDescriptionTextBox = new TextBox();

	ListBox userFormTypelistBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	List<UserFormTypeClient> userFormType = new ArrayList<UserFormTypeClient>();
	UserFormTypeClient current = null;

	public UserFormTypeComposite() {
		TBR.setCurrentForm("USER_FORM_TYPE");
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
		initWidget(userFormTypeFlexTable);
		userFormTypeFlexTable.setStyleName("flextable");
		formTitleLabel.setStyleName("title");
		userFormTypeFlexTable.setWidget(0, 1, formTitleLabel);
		userFormTypelistBox.setStyleName("list");
		userFormTypeFlexTable.setWidget(1, 0, userFormTypelistBox);
		userFormTypelistBox.setVisibleItemCount(5);
		userFormTypelistBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String userFormTypeName = userFormTypelistBox
						.getItemText(userFormTypelistBox.getSelectedIndex());
				webService.getUserFormTypeByName(userFormTypeName,
						new AsyncCallback<UserFormTypeClient>() {

							@Override
							public void onSuccess(UserFormTypeClient result) {
								userFormTypeId.setText(result
										.getUserFormTypeId().toString());
								userFormTypeNameTextBox.setText(result
										.getUserFormType());
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Unable to fetch User Form Type");
							}
						});
			}
		});
		userFormTypeFlexTable.setWidget(1, 1, userFormTypeGrid);
		userFormTypeGrid.setWidget(0, 0, userFormTypeIdLabel);
		userFormTypeIdLabel.setStyleName("label");
		userFormTypeId.setStyleName("label");
		userFormTypeGrid.setWidget(0, 1, userFormTypeId);
		userFormTypeNameLabel.setStyleName("label");
		userFormTypeGrid.setWidget(1, 0, userFormTypeNameLabel);
		userFormTypeNameTextBox.setStyleName("textbox");
		userFormTypeNameTextBox.setVisibleLength(25);
		userFormTypeNameTextBox.setMaxLength(45);
		userFormTypeGrid.setWidget(1, 1, userFormTypeNameTextBox);
		userFormTypeDescriptionTextBox.setStyleName("textbox");
		userFormTypeFlexTable.setWidget(2, 1, bottomFlexTable);
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
		userFormTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		userFormTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			userFormTypelistBox.setEnabled(false);
			clear(userFormTypeGrid);
			userFormTypeId.setText(null);
		}
		if (!createButton.isDown()) {
			userFormTypelistBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!userFormTypeId.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select User Attribute type from list");
				}
			}
		} else if (source == clearButton) {
			createButton.setDown(false);
			clear(userFormTypeGrid);
			userFormTypeId.setText("");
			userFormTypelistBox.setEnabled(true);
			userFormTypelistBox.setSelectedIndex(0);
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
		userFormType = new ArrayList<UserFormTypeClient>();
		webService
				.getUserFormTypes(new AsyncCallback<List<UserFormTypeClient>>() {
					@Override
					public void onSuccess(List<UserFormTypeClient> result) {
						userFormType = result;
						for (UserFormTypeClient type : userFormType) {
							userFormTypelistBox.addItem(type.getUserFormType(),
									type.getUserFormTypeId().toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Unable to fetch UserFormTypes");
					}

				});

	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		if (get(userFormTypeNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ userFormTypeNameTextBox.getName());
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
			webService.saveUserFormType(userFormTypeNameTextBox.getText(),
					new AsyncCallback<UserFormTypeClient>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR" + caught.getMessage());

						}

						@Override
						public void onSuccess(UserFormTypeClient result) {
							Window.alert("UserForm type :"
									+ userFormTypeNameTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							userFormType.add(result);
						}

					});
		}
	}

	@Override
	public void update() {
		webService.updateUserFormType(
				Integer.parseInt(userFormTypeId.getText()),
				userFormTypeNameTextBox.getText(), new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						Window.alert("UserForm type :"
								+ userFormTypeNameTextBox.getText()
								+ " has been Updated suceessfully");
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to update User Form Type");

					}
				});
	}

	@Override
	public void delete() {

	}

}
