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
import com.ihsinformatics.gfaicweb.shared.model.RoleClient;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class RoleComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	FlexTable roleFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();

	Grid roleGrid = new Grid(2, 2);

	Label formTitleLabel = new Label("Role");
	Label roleIdLabel = new Label("Role ID:");
	Label roleId = new Label("");
	Label roleNameLabel = new Label("Role Name:");

	TextBox roleNameTextBox = new TextBox();
	ListBox rolelistBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");

	ToggleButton createButton = new ToggleButton("Create New");

	List<RoleClient> role = new ArrayList<RoleClient>();
	RoleClient current = null;

	public RoleComposite() {
		TBR.setCurrentForm("ROLE");
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
		initWidget(roleFlexTable);
		roleFlexTable.setStyleName("flextable");
		formTitleLabel.setStyleName("title");
		roleFlexTable.setWidget(0, 1, formTitleLabel);
		rolelistBox.setStyleName("list");
		roleFlexTable.setWidget(1, 0, rolelistBox);
		rolelistBox.setVisibleItemCount(5);
		rolelistBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String roleName = rolelistBox.getItemText(rolelistBox
						.getSelectedIndex());
				webService.getRoleByName(roleName,
						new AsyncCallback<RoleClient>() {

							@Override
							public void onSuccess(RoleClient result) {
								roleId.setText(result.getRoleId().toString());
								roleNameTextBox.setText(result.getRoleName());
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Unable to fetch");
							}
						});
			}
		});
		roleFlexTable.setWidget(1, 1, roleGrid);
		roleIdLabel.setStyleName("label");
		roleGrid.setWidget(0, 0, roleIdLabel);
		roleId.setStyleName("label");
		roleGrid.setWidget(0, 1, roleId);
		roleNameLabel.setStyleName("label");
		roleGrid.setWidget(1, 0, roleNameLabel);
		roleNameTextBox.setVisibleLength(25);
		roleNameTextBox.setMaxLength(45);
		roleGrid.setWidget(1, 1, roleNameTextBox);
		roleNameTextBox.setStyleName("textbox");
		roleFlexTable.setWidget(2, 1, bottomFlexTable);
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
		roleFlexTable.getCellFormatter().setVerticalAlignment(1, 1,
				HasVerticalAlignment.ALIGN_TOP);
		roleFlexTable.getCellFormatter().setVerticalAlignment(1, 0,
				HasVerticalAlignment.ALIGN_TOP);
		init();
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			clear(roleGrid);
			roleId.setText(null);
			rolelistBox.setEnabled(false);
		}
		if (!createButton.isDown()) {
			rolelistBox.setEnabled(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!roleId.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select Role from list");
				}
			}
		} else if (source == clearButton) {
			createButton.setDown(false);
			roleId.setText("");
			rolelistBox.setSelectedIndex(0);
			clear(roleGrid);
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
		role = new ArrayList<RoleClient>();
		webService.getAllRoles(new AsyncCallback<List<RoleClient>>() {
			@Override
			public void onSuccess(List<RoleClient> result) {
				role = result;
				for (RoleClient type : role) {
					rolelistBox.addItem(type.getRoleName(), type.getRoleId()
							.toString());
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
		if (get(roleNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR)
					+ "\n"
					+ roleNameTextBox.getName());
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
			webService.saveRole(roleNameTextBox.getText(),
					new AsyncCallback<RoleClient>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(" ERROR!!" + caught.getMessage());

						}

						@Override
						public void onSuccess(RoleClient result) {
							Window.alert(" Role :" + roleNameTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							role.add(result);

						}

					});
		}
	}

	@Override
	public void update() {
		webService.updateRole(Integer.parseInt(roleId.getText()),
				roleNameTextBox.getText(), new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						Window.alert("Successfully updated Role");
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to update Role");
					}
				});
	}

	@Override
	public void delete() {
		// Option not availaible for metaDeta classes
	}

}
