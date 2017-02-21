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
import com.ihsinformatics.gfaicweb.shared.model.EncounterTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class EncounterTypeComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable encounterTypeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	Grid encounterTypeGrid = new Grid(2, 2);

	Label lblEncounterTypeTitle = new Label("Encounter Type");
	Label lblEncounterTypeId = new Label("Encounter Type ID:");
	Label encounterTypeIdValueLabel = new Label("");
	Label lblEncounterType = new Label("Encouter Type:");

	TextBox encounterTypeTextBox = new TextBox();

	ListBox encounterTypeListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	List<EncounterTypeClient> encounterTypes = new ArrayList<EncounterTypeClient>();
	EncounterTypeClient current = null;

	public EncounterTypeComposite() {
		TBR.setCurrentForm("ENCOUNTER_TYPE");
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

		initWidget(encounterTypeFlexTable);
		encounterTypeFlexTable.setStyleName("flextable");
		lblEncounterTypeTitle.setStyleName("title");
		encounterTypeFlexTable.setWidget(0, 1, lblEncounterTypeTitle);
		encounterTypeListBox.setStyleName("list");
		encounterTypeListBox.setHeight("100px");
		encounterTypeListBox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String encounterName = encounterTypeListBox
						.getItemText(encounterTypeListBox.getSelectedIndex());
				encounterTypeTextBox.setText(encounterName);
				webService.getEncounterTypeId(encounterName,
						new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Unable to fetch Selected EncounterType");
							}

							@Override
							public void onSuccess(Integer result) {
								encounterTypeIdValueLabel.setText(result
										.toString());
							}

						});
			}
		});
		encounterTypeFlexTable.setWidget(1, 0, encounterTypeListBox);
		encounterTypeListBox.setVisibleItemCount(5);
		encounterTypeFlexTable.setWidget(1, 1, encounterTypeGrid);
		lblEncounterTypeId.setStyleName("label");
		encounterTypeGrid.setWidget(0, 0, lblEncounterTypeId);
		encounterTypeIdValueLabel.setStyleName("label");
		encounterTypeGrid.setWidget(0, 1, encounterTypeIdValueLabel);
		lblEncounterType.setStyleName("label");
		encounterTypeGrid.setWidget(1, 0, lblEncounterType);
		encounterTypeTextBox.setVisibleLength(25);
		encounterTypeTextBox.setMaxLength(45);
		encounterTypeGrid.setWidget(1, 1, encounterTypeTextBox);
		encounterTypeFlexTable.setWidget(2, 1, bottomFlexTable);
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
		encounterTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		encounterTypeFlexTable.getCellFormatter().setVerticalAlignment(1, 0,
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
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			encounterTypeIdValueLabel.setText("");
			clear(encounterTypeGrid);
			encounterTypeListBox.setEnabled(false);
			encounterTypeListBox.setSelectedIndex(0);
		}
		if (!createButton.isDown()) {
			encounterTypeListBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
				clear();

			} else {
				if (!encounterTypeIdValueLabel.getText().isEmpty()) {
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
	public void fill() {
		encounterTypes = new ArrayList<EncounterTypeClient>();
		webService
				.getEncounterTypes(new AsyncCallback<List<EncounterTypeClient>>() {

					@Override
					public void onFailure(Throwable arg0) {
						Window.alert("Failed to Fetch encounter types"
								+ arg0.getMessage());
					}

					@Override
					public void onSuccess(List<EncounterTypeClient> result) {
						encounterTypes = result;
						for (EncounterTypeClient type : encounterTypes) {
							encounterTypeListBox.addItem(type
									.getEncounterType(), type
									.getEncounterTypeId().toString());
						}

					}
				});

	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		if (get(encounterTypeTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ encounterTypeTextBox.getName());
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
			webService.saveEncounterType(encounterTypeTextBox.getText(),
					new AsyncCallback<EncounterTypeClient>() {

						@Override
						public void onSuccess(EncounterTypeClient result) {
							Window.alert("Encounter type :"
									+ result.getEncounterType()
									+ " has been added suceessfully");
							current = result;
							encounterTypes.add(result);
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(CustomMessage
									.getErrorMessage(ErrorType.INSERT_ERROR));
						}
					});
		}
	}

	@Override
	public void update() {
		webService.updateEncounterType(
				Integer.parseInt(encounterTypeIdValueLabel.getText()),
				encounterTypeTextBox.getText(), new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						Window.alert("Successfully saved EncounterType: "
								+ encounterTypeTextBox.getText());
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to Update Encounter Type");
					}
				});
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	public void clear() {
		createButton.setDown(false);
		encounterTypeIdValueLabel.setText("");
		clear(encounterTypeGrid);
		encounterTypeListBox.setEnabled(true);
		encounterTypeListBox.setSelectedIndex(0);
	}

}
