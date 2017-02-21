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
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.gfaicweb.shared.CustomMessage;
import com.ihsinformatics.gfaicweb.shared.ErrorType;
import com.ihsinformatics.gfaicweb.shared.model.ElementClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 *
 */
public class ElementComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable elementFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();
	FlexTable validationFlexTable = new FlexTable();

	Label formTitleLabel = new Label("Element");
	Label elementIdLabel = new Label("Element ID: ");
	Label elementIdValueLabel = new Label("");
	Label elementNameLabel = new Label("Element Name:");
	Label validationRegexLabel = new Label("Validation Regex:");
	Label dataTypeLabel = new Label("Data type:");
	Label descriptionLabel = new Label("Description:");

	TextBox elementNameTextBox = new TextBox();
	TextBox validationRegexTextBox = new TextBox();
	TextBox descriptionTextBox = new TextBox();

	ListBox elementListBox = new ListBox();
	ListBox validationRegexTypeListBox = new ListBox();
	ListBox dataTypeListBox = new ListBox();

	ToggleButton createButton = new ToggleButton("Create New");

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");
	Button filterButton = new Button("Filter");

	Grid elementGrid = new Grid(5, 2);

	CellTable<ElementClient> dataCellTable = new CellTable<ElementClient>();
	List<ElementClient> element = new ArrayList<ElementClient>();
	List<String> dataTypeList = new ArrayList<String>(Arrays.asList("String",
			"Integer", "Double", "Date", "DateTime"));
	ArrayList<String> validationTypeList = new ArrayList<String>(Arrays.asList(
			"list", "regex", "relation", "range"));
	ElementClient current = null;
	String validationRegexString;

	public ElementComposite() {
		TBR.setCurrentForm("ELEMENT");
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
		initWidget(elementFlexTable);
		elementFlexTable.setStyleName("flextable");
		formTitleLabel.setStyleName("title");
		elementFlexTable.setWidget(0, 1, formTitleLabel);

		elementFlexTable.setWidget(1, 0, elementListBox);
		elementListBox.setStyleName("list");
		elementListBox.setVisibleItemCount(7);
		elementFlexTable.setWidget(1, 1, elementGrid);

		elementListBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String elementName = elementListBox.getItemText(elementListBox
						.getSelectedIndex());
				webService.getElementByName(elementName,
						new AsyncCallback<ElementClient>() {

							@Override
							public void onSuccess(ElementClient result) {
								elementIdValueLabel.setText(result
										.getElementId().toString());
								elementNameTextBox.setText(result
										.getElementName());
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

		elementGrid.setWidget(0, 0, elementIdLabel);
		elementIdLabel.setStyleName("label");

		elementGrid.setWidget(0, 1, elementIdValueLabel);
		elementIdValueLabel.setStyleName("label");

		elementNameLabel.setStyleName("label");
		elementGrid.setWidget(1, 0, elementNameLabel);

		elementGrid.setWidget(1, 1, elementNameTextBox);
		elementNameTextBox.setVisibleLength(25);
		elementNameTextBox.setMaxLength(45);
		elementNameTextBox.setStyleName("textbox");

		elementGrid.setWidget(2, 0, dataTypeLabel);
		dataTypeLabel.setStyleName("label");
		for (String item : dataTypeList) {
			dataTypeListBox.addItem(item);
		}
		elementGrid.setWidget(2, 1, dataTypeListBox);

		elementGrid.setWidget(3, 0, validationRegexLabel);
		validationRegexLabel.setStyleName("label");
		for (String item : validationTypeList) {
			validationRegexTypeListBox.addItem(item);
		}
		validationFlexTable.setWidget(0, 0, validationRegexTypeListBox);
		validationFlexTable.setWidget(0, 1, validationRegexTextBox);
		validationRegexTextBox.setStyleName("textbox");
		elementGrid.setWidget(3, 1, validationFlexTable);
		validationRegexTextBox.setStyleName("textbox");

		descriptionLabel.setStyleName("label");
		elementGrid.setWidget(4, 0, descriptionLabel);

		descriptionTextBox.setStyleName("textbox");
		elementGrid.setWidget(4, 1, descriptionTextBox);
		elementFlexTable.setWidget(5, 1, bottomFlexTable);
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
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			elementListBox.setEnabled(false);
			clear(elementGrid);
			elementIdValueLabel.setText(null);
		}
		if (!createButton.isDown()) {
			elementListBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
				clear();

			} else {
				if (!elementIdValueLabel.getText().isEmpty()) {
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
		filterButton.addClickHandler(this);
		fill();
	}

	@Override
	public void fill() {
		webService.getAllElements(new AsyncCallback<List<ElementClient>>() {

			@Override
			public void onSuccess(List<ElementClient> result) {
				for (ElementClient item : result) {
					elementListBox.addItem(item.getElementName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch Elements");
			}
		});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (get(elementNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ elementNameTextBox.getName());
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
			ElementClient newElementClient = new ElementClient();
			newElementClient.setElementName(elementNameTextBox.getText());
			newElementClient.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			newElementClient.setValidationRegex(validationRegexString);
			newElementClient.setDescription(descriptionTextBox.getText());
			webService.saveElement(newElementClient,
					new AsyncCallback<ElementClient>() {

						@Override
						public void onSuccess(ElementClient result) {
							current = result;
							Window.alert("Element : " + result.getElementName()
									+ " has been saved successfully");
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save Element");
						}
					});
		}
	}

	@Override
	public void update() {
		if (validate()) {
			ElementClient elementClient = new ElementClient();
			elementClient.setElementId(Integer.parseInt(elementIdValueLabel
					.getText()));
			elementClient.setElementName(elementNameTextBox.getText());
			validationRegexString = validationRegexTypeListBox
					.getItemText(validationRegexTypeListBox.getSelectedIndex())
					+ "=" + validationRegexTextBox.getText();
			elementClient.setValidationRegex(validationRegexString);
			elementClient.setDataType(dataTypeListBox
					.getItemText(dataTypeListBox.getSelectedIndex()));
			elementClient.setDescription(descriptionTextBox.getText());
			webService.updateElement(elementClient, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					Window.alert("Successfully updated Element");
					clear();
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Unable to update Element");
				}
			});

		}
	}

	@Override
	public void delete() {

	}

	public void clear() {
		createButton.setDown(false);
		elementIdValueLabel.setText("");
		clear(elementGrid);
		elementListBox.setEnabled(true);
		elementListBox.setSelectedIndex(0);
	}
}
