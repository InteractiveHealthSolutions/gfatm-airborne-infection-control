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
import com.ihsinformatics.gfaicweb.shared.model.DefinitionTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class DefinitionTypeComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable definitionTypeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	Grid definitionTypeGrid = new Grid(2, 2);

	Label lblDefinitionTypes = new Label("Definition Types");
	Label lblDefinitionTypeId = new Label("Definition Type ID:");
	Label definitionTypeIdLabel = new Label();
	Label lblDefinitionType = new Label("Definition Type:");

	TextBox definitionTypeTextBox = new TextBox();

	ListBox definitionTypeListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	List<DefinitionTypeClient> definitionTypes = new ArrayList<DefinitionTypeClient>();
	DefinitionTypeClient current = null;

	public DefinitionTypeComposite() {
		TBR.setCurrentForm("DEFINITION_TYPE");
		webService
				.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch Current User Privileges");
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

		initWidget(definitionTypeFlexTable);
		definitionTypeFlexTable.setStyleName("flextable");
		lblDefinitionTypes.setStyleName("title");
		definitionTypeFlexTable.setWidget(0, 1, lblDefinitionTypes);
		definitionTypeListBox.setStyleName("list");
		definitionTypeListBox.setHeight("100px");
		definitionTypeListBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String DefinitionName = definitionTypeListBox
						.getItemText(definitionTypeListBox.getSelectedIndex());
				definitionTypeTextBox.setText(DefinitionName);
				webService.getDefinitionTypeId(DefinitionName,
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(Integer result) {
								definitionTypeIdLabel.setText(result.toString());
							}

						});
			}
		});

		definitionTypeFlexTable.setWidget(1, 0, definitionTypeListBox);
		definitionTypeListBox.setVisibleItemCount(5);
		definitionTypeFlexTable.setWidget(1, 1, definitionTypeGrid);
		lblDefinitionTypeId.setStyleName("label");
		definitionTypeGrid.setWidget(0, 0, lblDefinitionTypeId);
		definitionTypeIdLabel.setStyleName("label");
		definitionTypeGrid.setWidget(0, 1, definitionTypeIdLabel);
		lblDefinitionType.setStyleName("label");
		definitionTypeGrid.setWidget(1, 0, lblDefinitionType);
		definitionTypeTextBox.setVisibleLength(25);
		definitionTypeTextBox.setMaxLength(45);
		definitionTypeGrid.setWidget(1, 1, definitionTypeTextBox);
		definitionTypeFlexTable.setWidget(2, 1, bottomFlexTable);
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
		definitionTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		definitionTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		init();
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
		definitionTypes = new ArrayList<DefinitionTypeClient>();
		webService
				.getDefinitionTypes(new AsyncCallback<List<DefinitionTypeClient>>() {

					@Override
					public void onSuccess(List<DefinitionTypeClient> result) {
						definitionTypes = result;
						for (DefinitionTypeClient type : definitionTypes) {
							definitionTypeListBox.addItem(type
									.getDefinitionType(), type
									.getDefinitionTypeId().toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("");
					}
				});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (get(definitionTypeTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ definitionTypeTextBox.getName());
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
			webService.saveDefinitionType(definitionTypeTextBox.getText(),
					new AsyncCallback<DefinitionTypeClient>() {

						@Override
						public void onSuccess(DefinitionTypeClient result) {
							Window.alert("Definition type :"
									+ definitionTypeTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							definitionTypes.add(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR" + caught.getMessage());
						}
					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			DefinitionTypeClient definitionTypeClient = new DefinitionTypeClient();
			definitionTypeClient.setDefinitionTypeId(Integer
					.parseInt(definitionTypeIdLabel.getText()));
			definitionTypeClient.setDefinitionType(definitionTypeTextBox
					.getText());
			webService.updateDefinitionType(definitionTypeClient,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR, Failed to update Defintion Type "
									+ "\n" + caught.getMessage());

						}

						@Override
						public void onSuccess(Void result) {
							Window.alert(" Defintion type :"
									+ definitionTypeTextBox.getText()
									+ " has been updated suceessfully");

						}
					});
		}

	}

	@Override
	public void delete() {

	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			clear(definitionTypeGrid);
			definitionTypeIdLabel.setText(null);
			definitionTypeListBox.setEnabled(false);
		}
		if (!createButton.isDown()) {
			definitionTypeListBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!definitionTypeIdLabel.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select Definition type from list");
				}
			}
		} else if (source == clearButton) {
			createButton.setDown(false);
			definitionTypeIdLabel.setText("");
			definitionTypeListBox.setSelectedIndex(0);
			clear(definitionTypeGrid);
		} else if (source == deleteButton) {
			delete();
		} else if (source == closeButton) {
			close();
		}
	}
}
