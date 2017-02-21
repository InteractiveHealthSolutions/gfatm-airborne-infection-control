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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.gfaicweb.shared.CustomMessage;
import com.ihsinformatics.gfaicweb.shared.ErrorType;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class PrivilegeComposite extends AbstractComposite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	VerticalPanel mainPanel = new VerticalPanel();

	FlexTable privilegeFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	Label privilegeLabel = new Label("Privileges:");
	Label privilegeNameLabel = new Label("Privileges Name:");

	TextBox privilegeTextBox = new TextBox();

	ListBox privilegeListBox = new ListBox();

	Grid privilegeGrid = new Grid(1, 2);

	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button saveButton = new Button("Save");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	List<PrivilegeClient> privilege = new ArrayList<PrivilegeClient>();
	PrivilegeClient current = null;

	public PrivilegeComposite() {
		TBR.setCurrentForm("PRIVILEGE");
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
		initWidget(privilegeFlexTable);
		privilegeFlexTable.setStyleName("flextable");
		privilegeLabel.setStyleName("title");
		privilegeFlexTable.setWidget(0, 1, privilegeLabel);
		privilegeNameLabel.setStyleName("label");
		privilegeFlexTable.setWidget(1, 0, privilegeListBox);
		privilegeListBox.setVisibleItemCount(5);
		privilegeListBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String privilegeName = privilegeListBox
						.getItemText(privilegeListBox.getSelectedIndex());
				privilegeTextBox.setText(privilegeName);
			}
		});
		privilegeFlexTable.setWidget(1, 1, privilegeGrid);
		privilegeGrid.setWidget(0, 0, privilegeNameLabel);
		privilegeNameLabel.setStyleName("label");
		privilegeGrid.setWidget(0, 1, privilegeTextBox);
		privilegeTextBox.setStyleName("textbox");
		privilegeTextBox.setWidth("220px");
		privilegeFlexTable.setWidget(2, 1, bottomFlexTable);
		bottomFlexTable.setWidth("100%");
		createButton.setStyleName("toggle-button");
		bottomFlexTable.setWidget(0, 0, createButton);
		createButton.setWidth("80%");
		saveButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 1, saveButton);
		saveButton.setWidth("80%");
		deleteButton.setStyleName("submit-button");
		bottomFlexTable.setWidget(0, 2, deleteButton);
		deleteButton.setEnabled(false);
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
			clear(privilegeGrid);
			privilegeListBox.setEnabled(false);
		}
		if (!createButton.isDown()) {
			privilegeListBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				update();
			}
		} else if (source == clearButton) {
			createButton.setDown(false);
			privilegeListBox.setEnabled(true);
			privilegeListBox.setSelectedIndex(0);
			clear(privilegeGrid);
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
		webService.getAllPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

			@Override
			public void onSuccess(List<PrivilegeClient> result) {
				for (PrivilegeClient item : result) {
					privilegeListBox.addItem(item.getPrivilege());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch All privileges");
			}
		});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (get(privilegeTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ privilegeTextBox.getName());
			valid = false;
		}
		if (!valid) {
			Window.alert(errorMessage.toString());
			// load(false);
		}
		return valid;
	}

	@Override
	public void setPrivileges() {
	}

	@Override
	public void save() {
		if (validate()) {
			webService.savePrivilegeClient(privilegeTextBox.getText(),
					new AsyncCallback<PrivilegeClient>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR" + caught.getMessage());

						}

						@Override
						public void onSuccess(PrivilegeClient result) {
							Window.alert("Privilege :"
									+ privilegeTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							privilege.add(result);

						}

					});
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		validate();

	}

}
