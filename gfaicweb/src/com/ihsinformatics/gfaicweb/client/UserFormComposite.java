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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.ihsinformatics.gfaicweb.shared.model.ElementClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormResultClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.UsersClient;

/**
 * @author babar.anis@ihsinformatics.com
 */

public class UserFormComposite extends AbstractComposite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	SimplePanel gridPanel = new SimplePanel();

	VerticalPanel mainPanel = new VerticalPanel();
	VerticalPanel centerVerticalPanel = new VerticalPanel();
	VerticalPanel userFormPanel = new VerticalPanel();
	VerticalPanel allLocationsPanel = new VerticalPanel();
	VerticalPanel userFormAttributePanel = new VerticalPanel();
	VerticalPanel deleteUserFormPanel = new VerticalPanel();

	HorizontalPanel deleteConfirmationPanel = new HorizontalPanel();
	HorizontalPanel locationFilterPanel = new HorizontalPanel();
	HorizontalPanel userFormAttributeHorizontalPanel = new HorizontalPanel();
	HorizontalPanel searchLocationPanel = new HorizontalPanel();

	FlexTable topFlexTable = new FlexTable();
	FlexTable userFormFlexTable = new FlexTable();
	FlexTable centerFlexTable = new FlexTable();
	FlexTable bottomFlexTable = new FlexTable();
	FlexTable filterTable = new FlexTable();
	FlexTable userFormAttributeFlexTable = new FlexTable();

	ScrollPanel gridScrollPanel = new ScrollPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	Grid locationAttributesGrid = new Grid(2, 3);
	Grid grid = new Grid(1, 5);

	TabLayoutPanel formTabLayoutPanel = new TabLayoutPanel(2.0, Unit.EM);
	DecoratedStackPanel formStackPanel = new DecoratedStackPanel();

	Label formTitleLabel = new Label("User Form");
	Label userLabel = new Label("User:");
	Label userFormTypeLabel = new Label("User Form Type:");
	Label dateLabel = new Label("Date:");
	Label addUserFormAttribute = new Label("Add new User Form elements:");
	Label lblUserFormID = new Label("UserForm ID");
	Label lblUser = new Label("User");
	Label lblUserFormType = new Label("UserFormType");
	Label searchUserForm = new Label("User Form ID:");
	Label content = new Label();

	final DialogBox dialog = new DialogBox(false, true);

	TextBox attributeTypeIdTextBox = new TextBox();
	TextBox attributeLocationIdTextBox = new TextBox();
	TextBox attributeValueTextBox = new TextBox();
	TextBox locationIdFilterTextBox = new TextBox();
	TextBox filterUserFormTextBox = new TextBox();
	TextBox searchUserFormTextBox = new TextBox();

	ListBox userFormOptionListBox = new ListBox();
	ListBox userFormTypeOptionListBox = new ListBox();
	ListBox userFormTypeListBox = new ListBox();
	ListBox filterUserFormTypeListBox = new ListBox();

	Button saveButton = new Button("Save");
	Button clearButton = new Button("Clear");
	Button deleteButton = new Button("Delete");
	Button closeButton = new Button("Close");
	Button filterButton = new Button("Filter");
	Button adduserFormElementButton = new Button("+");
	Button searchUserFormButton = new Button("Search UserForm");
	Button yes = new Button("YES");
	Button no = new Button("NO");

	DatePicker datePicker = new DatePicker();
	DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
	DateBox dateBox = new DateBox();

	MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();
	MultiWordSuggestOracle oracle3 = new MultiWordSuggestOracle();
	SuggestBox suggestionBox, suggestionBox3, userSuggestionBox;
	ToggleButton createButton = new ToggleButton("Create New");

	CellTable<UserFormClient> dataCellTable = new CellTable<UserFormClient>();
	List<UserFormClient> userForm = new ArrayList<UserFormClient>();
	List<String> userFormType = new ArrayList<String>();
	UserFormClient current = null;
	int rowCount = 0;

	public UserFormComposite() {

		TBR.setCurrentForm("USER_FORM");
		webService
				.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

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
									"DELETE " + TBR.getCurrentFormName())) {
								canDelete = true;
							} else if (privilege.getPrivilege().equals(
									"VIEW " + TBR.getCurrentFormName())) {
								canView = true;
							}
						}
						createButton.setEnabled(canCreate);
						saveButton.setEnabled(canEdit);
						deleteButton.setEnabled(canDelete);
						if (!(canView | canCreate)) {
							formStackPanel.setVisible(false);
							Window.alert("Sorry, current user does not have privileges to View this form.");
							close();
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch Current User Privileges");
					}
				});

		webService.getAllUsers(new AsyncCallback<List<UsersClient>>() {

			@Override
			public void onSuccess(List<UsersClient> result) {
				for (UsersClient item : result) {
					oracle.add(item.getUserId() + ", " + item.getUsername());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch Users");
			}
		});
		filterUserFormTypeListBox.addItem("");
		webService
				.getAllUserFormType(new AsyncCallback<List<UserFormTypeClient>>() {

					@Override
					public void onSuccess(List<UserFormTypeClient> result) {
						for (UserFormTypeClient item : result) {
							userFormTypeListBox.addItem(item.getUserFormType());
							filterUserFormTypeListBox.addItem(item
									.getUserFormType());
							userFormType.add(item.getUserFormType());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch UserFormType");
					}
				});
		userSuggestionBox = new SuggestBox(oracle);
		initWidget(mainPanel);
		mainPanel.setSize("900px", "100%");
		mainPanel.add(topFlexTable);
		formTitleLabel.setStyleName("title");
		topFlexTable.setWidget(0, 0, formTitleLabel);
		mainPanel.add(centerVerticalPanel);
		centerVerticalPanel.setSize("100%", "100%");
		formTabLayoutPanel.setAnimationDuration(500);
		formTabLayoutPanel.setAnimationVertical(true);
		formTabLayoutPanel.add(formStackPanel, "Form View", false);
		formStackPanel.setWidth("100%");
		ScrollPanel scrollPanel = new ScrollPanel(userFormPanel);
		scrollPanel.setSize("100%", "385px");
		formStackPanel.add(scrollPanel, "User Form Information", false);
		userFormPanel.add(userFormFlexTable);
		userFormFlexTable.setStyleName("FlexTable");
		userFormFlexTable.setSize("100%", "100%");
		userFormFlexTable.setWidget(0, 0, searchUserForm);
		searchUserForm.setStyleName("label");
		searchLocationPanel.add(searchUserFormTextBox);
		searchUserFormTextBox.setStyleName("textbox");
		searchUserFormTextBox.setWidth("55px");
		searchUserFormTextBox.setMaxLength(6);
		searchLocationPanel.add(searchUserFormButton);
		searchLocationPanel.setSpacing(5);
		searchUserFormButton.setStyleName("search-location-button");
		userFormFlexTable.setWidget(0, 1, searchLocationPanel);
		userFormFlexTable.setWidget(1, 0, userLabel);
		userLabel.setStyleName("label");
		suggestionBox = new SuggestBox(oracle);
		suggestionBox.setWidth("200");
		suggestionBox
				.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

					@Override
					public void onSelection(SelectionEvent<Suggestion> event) {
						String display = event.getSelectedItem()
								.getDisplayString();
						display = display.replaceAll("<strong>", "")
								.replaceAll("</strong>", "");
						String[] parts = display.split(", ");
						suggestionBox.setText(parts[1]);
					}
				});
		userFormFlexTable.setWidget(1, 1, suggestionBox);
		suggestionBox.setStyleName("textbox");
		userFormFlexTable.setWidget(2, 0, userFormTypeLabel);
		userFormTypeLabel.setStyleName("label");
		userFormFlexTable.setWidget(2, 1, userFormTypeListBox);
		userFormTypeListBox.setStyleName("listbox");
		userFormFlexTable.setWidget(3, 0, dateLabel);
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
		userFormFlexTable.setWidget(3, 1, dateBox);

		userFormPanel.add(userFormAttributeFlexTable);
		dateBox.setStyleName("textbox");
		dateLabel.setStyleName("label");
		userFormAttributeHorizontalPanel.add(adduserFormElementButton);
		userFormAttributeHorizontalPanel.add(addUserFormAttribute);
		adduserFormElementButton.setStyleName("operation-button");
		userFormAttributeFlexTable.setWidget(0, 0,
				userFormAttributeHorizontalPanel);
		addUserFormAttribute.setStyleName("sub-label2");
		userFormAttributeFlexTable.setWidget(1, 0, userFormAttributePanel);
		userFormAttributeFlexTable.setSize("100%", "100%");
		webService.getAllElements(new AsyncCallback<List<ElementClient>>() {

			@Override
			public void onSuccess(List<ElementClient> result) {
				for (ElementClient item : result) {
					oracle3.add(item.getElementName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch Elements");
			}
		});
		adduserFormElementButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (current == null && !createButton.isDown()) {
					Window.alert("Please select UserForm first or press Create Button");
				} else {
					HorizontalPanel newPanel = new HorizontalPanel();
					suggestionBox3 = new SuggestBox(oracle3);
					suggestionBox3.setWidth("135px");
					TextBox attributeValueTextBox = new TextBox();
					Button deleteRow = new Button("X");
					deleteRow.setTitle(String.valueOf(rowCount));
					newPanel.setSpacing(5);
					newPanel.add(suggestionBox3);
					newPanel.add(attributeValueTextBox);
					newPanel.add(deleteRow);
					userFormAttributePanel.add(newPanel);
					rowCount++;

					deleteRow.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Button button = (Button) event.getSource();
							for (int i = 0; i < userFormAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
										.getWidget(i);
								Button thisButton = (Button) horizontal
										.getWidget(2);
								if (thisButton.getTitle().equals(
										button.getTitle())) {
									userFormAttributePanel.remove(i);
								}
							}
						}
					});

				}
			}
		});

		formTabLayoutPanel.add(gridPanel, "Grid View", false);
		gridPanel.setWidget(gridScrollPanel);
		gridScrollPanel.setSize("100%", "100%");
		gridScrollPanel.setWidget(allLocationsPanel);
		allLocationsPanel.setSize("100%", "100%");
		allLocationsPanel.add(grid);
		filterTable.setWidget(0, 0, lblUserFormID);
		lblUserFormID.setStyleName("label");
		filterTable.setWidget(0, 2, lblUser);
		lblUser.setStyleName("label");
		filterTable.setWidget(0, 4, lblUserFormType);
		lblUserFormType.setStyleName("label");
		filterTable.setWidget(1, 0, filterUserFormTextBox);
		filterUserFormTextBox.setStyleName("textbox");
		filterUserFormTextBox.setMaxLength(40);
		filterTable.setWidget(1, 1, userFormOptionListBox);
		filterTable.setWidget(1, 2, userSuggestionBox);
		userSuggestionBox
				.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {

					@Override
					public void onSelection(SelectionEvent<Suggestion> event) {
						String display = event.getSelectedItem()
								.getDisplayString();
						display = display.replaceAll("<strong>", "")
								.replaceAll("</strong>", "");
						String[] parts = display.split(", ");
						userSuggestionBox.setText(parts[1]);
					}
				});
		userSuggestionBox.setStyleName("suggestBox");
		filterTable.setWidget(1, 3, userFormTypeOptionListBox);
		filterTable.setWidget(1, 4, filterUserFormTypeListBox);
		filterUserFormTypeListBox.setStyleName("listbox");
		filterTable.setWidget(1, 5, filterButton);
		filterButton.setStyleName("search-button");
		HTMLTable.CellFormatter formatter2 = filterTable.getCellFormatter();
		formatter2.setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(0, 4, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 4,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 4, HasVerticalAlignment.ALIGN_MIDDLE);

		userFormOptionListBox.addItem("AND");
		userFormOptionListBox.addItem("OR");

		userFormTypeOptionListBox.addItem("AND");
		userFormTypeOptionListBox.addItem("OR");
		dialog.setText("Confirmation");
		deleteUserFormPanel.add(content);
		deleteUserFormPanel.add(deleteConfirmationPanel);
		deleteConfirmationPanel.add(yes);
		deleteConfirmationPanel.add(no);
		deleteConfirmationPanel.setSpacing(15);
		deleteConfirmationPanel.setWidth("60%");
		deleteUserFormPanel.setCellHorizontalAlignment(deleteConfirmationPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		deleteUserFormPanel.setSpacing(15);
		dialog.setWidget(deleteUserFormPanel);
		allLocationsPanel.add(grid);
		grid.setSize("850px", "20px");
		grid.setWidget(0, 0, filterTable);
		dataCellTable.setPageSize(10);
		allLocationsPanel.add(dataCellTable);
		dataCellTable.setSize("100%", "100%");
		centerVerticalPanel.add(formTabLayoutPanel);
		formTabLayoutPanel.setHeight("500px");
		mainPanel.add(centerFlexTable);
		centerFlexTable.setWidth("100%");
		mainPanel.add(bottomFlexTable);
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

	/**
	 * Additional initialization. Events, Lists, Grids, Defaults, etc.
	 */
	public void init() {
		createButton.addClickHandler(this);
		saveButton.addClickHandler(this);
		clearButton.addClickHandler(this);
		deleteButton.addClickHandler(this);
		closeButton.addClickHandler(this);
		filterButton.addClickHandler(this);
		searchUserFormButton.addClickHandler(this);
		yes.addClickHandler(this);
		no.addClickHandler(this);
		createColumns();
		fill();
	}

	private void createColumns() {
		TextColumn<UserFormClient> userFormIdTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				return object.getUserFormId().toString();
			}

		};
		userFormIdTextColumn.setSortable(true);
		dataCellTable.addColumn(userFormIdTextColumn, "UserFormID");

		TextColumn<UserFormClient> userFormTypeEditTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				return (String) object.getUserFormType().getUserFormTypeId()
						.toString();
			}

		};
		userFormTypeEditTextColumn.setSortable(true);
		dataCellTable.addColumn(userFormTypeEditTextColumn, "UserFormTypeID");

		TextColumn<UserFormClient> userIdEditTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				return (String) object.getUserId().getUserId().toString();
			}

		};
		userIdEditTextColumn.setSortable(true);
		dataCellTable.addColumn(userIdEditTextColumn, "UserID");

		TextColumn<UserFormClient> durationSecondsEditTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				try {
					return object.getDurationSeconds().toString();
				} catch (Exception e) {
					return null;
				}
			}

		};
		durationSecondsEditTextColumn.setSortable(true);
		dataCellTable.addColumn(durationSecondsEditTextColumn,
				"DurationSeconds");

		TextColumn<UserFormClient> dateEnteredEditTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				if (object.getDateEntered() != null) {
					return (String) object.getDateEntered().toString();
				} else {
					return null;
				}

			}

		};
		dateEnteredEditTextColumn.setSortable(true);
		dataCellTable.addColumn(dateEnteredEditTextColumn, "DateEntered");

		TextColumn<UserFormClient> uuidEditTextColumn = new TextColumn<UserFormClient>() {

			@Override
			public String getValue(UserFormClient object) {
				return (String) object.getUuid();
			}

		};
		uuidEditTextColumn.setSortable(true);
		dataCellTable.addColumn(uuidEditTextColumn, "UUID");

		dataCellTable.addCellPreviewHandler(new Handler<UserFormClient>() {

			@Override
			public void onCellPreview(CellPreviewEvent<UserFormClient> event) {
				if (event.getNativeEvent().getType().contains("click")) {
					current = event.getValue();
					searchUserFormTextBox.setText(event.getValue()
							.getUserFormId().toString());
					suggestionBox.setText(event.getValue().getUserId()
							.getUsername());
					userFormTypeListBox.setSelectedIndex(userFormType
							.indexOf(event.getValue().getUserFormType()
									.getUserFormType()));
					webService.getUserFormResultByUserForm(event.getValue(),
							new AsyncCallback<List<UserFormResultClient>>() {

								@Override
								public void onSuccess(
										List<UserFormResultClient> result) {
									userFormAttributePanel.clear();
									for (UserFormResultClient item : result) {
										SuggestBox attributeSuggestBox = new SuggestBox(
												oracle3);
										TextBox attributeValueTextBox = new TextBox();
										Button deleteRow = new Button("X");
										attributeSuggestBox.setText(item
												.getElement().getElementName());
										attributeValueTextBox.setText(item
												.getResult());
										HorizontalPanel newPanel = new HorizontalPanel();
										deleteRow.setTitle(String
												.valueOf(rowCount));
										newPanel.setSpacing(5);
										newPanel.add(attributeSuggestBox);
										newPanel.add(attributeValueTextBox);
										newPanel.add(deleteRow);
										userFormAttributePanel.add(newPanel);
										rowCount++;
										deleteRow
												.addClickHandler(new ClickHandler() {

													@Override
													public void onClick(
															ClickEvent event) {
														Button button = (Button) event
																.getSource();
														for (int i = 0; i < userFormAttributePanel
																.getWidgetCount(); i++) {
															HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
																	.getWidget(i);
															Button thisButton = (Button) horizontal
																	.getWidget(2);
															if (thisButton
																	.getTitle()
																	.equals(button
																			.getTitle())) {
																userFormAttributePanel
																		.remove(i);
															}
														}
													}
												});
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to fetch UserFormResult for current UserForm");
								}
							});
				}

			}
		});

	}

	public void setCurrent() {
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			clear(userFormFlexTable);
			searchUserFormTextBox.setText(null);
			searchUserFormTextBox.setVisible(false);
			searchUserFormButton.setVisible(false);
			userFormAttributePanel.clear();
			clear(userFormAttributeFlexTable);
		}
		if (!createButton.isDown()) {
			searchUserFormTextBox.setVisible(true);
			searchUserFormButton.setVisible(true);
		}
		if (source == saveButton) {
			if (createButton.isDown()) {
				save();
			} else {
				if (!searchUserFormTextBox.getText().isEmpty()) {
					update();
					clear();
				} else {
					Window.alert("ERROR, Please select UserForm first");
				}
			}
		} else if (source == clearButton) {
			clear();
			current = null;
		} else if (source == closeButton) {
			close();
		} else if (source == deleteButton) {
			content.setText("Are you sure you want to delete UserForm");
			dialog.setPopupPosition(500, 250);
			dialog.show();
		} else if (source == yes) {
			dialog.hide();
			delete();
		} else if (source == no) {
			dialog.hide();
		} else if (source == filterButton) {
			if (!filterUserFormTextBox.getText().equals("")) {
				if (!userSuggestionBox.getText().equals("")) {
					if (!filterUserFormTypeListBox.getItemText(
							filterUserFormTypeListBox.getSelectedIndex())
							.equals("")) {
						if (userFormOptionListBox.getItemText(
								userFormOptionListBox.getSelectedIndex())
								.equalsIgnoreCase("AND")) {
							if (userFormTypeOptionListBox.getItemText(
									userFormTypeOptionListBox
											.getSelectedIndex())
									.equalsIgnoreCase("AND")) {
								webService
										.getUserFormByIDAndUserAndUserFormType(
												Integer.parseInt(filterUserFormTextBox
														.getText()),
												userSuggestionBox.getText(),
												filterUserFormTypeListBox
														.getItemText(filterUserFormTypeListBox
																.getSelectedIndex()),
												new AsyncCallback<UserFormClient>() {

													@Override
													public void onSuccess(
															UserFormClient result) {
														if (result != null) {
															current = result;
															dataCellTable
																	.flush();
															userForm.clear();
															userForm.add(result);
															dataCellTable
																	.setRowData(userForm);
														} else {
															Window.alert("No UserForm Found");
														}

													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter");
													}
												});
							} else {
								webService
										.getUserFormByIDAndUserOrUserFormType(
												Integer.parseInt(filterUserFormTextBox
														.getText()),
												userSuggestionBox.getText(),
												filterUserFormTypeListBox
														.getItemText(filterUserFormTypeListBox
																.getSelectedIndex()),
												new AsyncCallback<UserFormClient>() {

													@Override
													public void onSuccess(
															UserFormClient result) {
														if (result != null) {
															current = result;
															dataCellTable
																	.flush();
															userForm.clear();
															userForm.add(result);
															dataCellTable
																	.setRowData(userForm);
														} else {
															Window.alert("No UserForm Found");
														}

													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to filter");
													}
												});
							}
						} else {
							if (userFormTypeOptionListBox.getItemText(
									userFormTypeOptionListBox
											.getSelectedIndex())
									.equalsIgnoreCase("AND")) {
								webService
										.getUserFormByIDOrUserAndUserFormType(
												Integer.parseInt(filterUserFormTextBox
														.getText()),
												userSuggestionBox.getText(),
												filterUserFormTypeListBox
														.getItemText(filterUserFormTypeListBox
																.getSelectedIndex()),
												new AsyncCallback<List<UserFormClient>>() {

													@Override
													public void onSuccess(
															List<UserFormClient> result) {
														userForm = result;
														dataCellTable.flush();
														if (userForm.size() > 0) {
															current = userForm
																	.get(0);
															dataCellTable
																	.setRowData(userForm);
														} else {
															Window.alert("No UserForm found");
														}

													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to Filter");
													}
												});
							} else {
								webService
										.getUserFormByIDOrUserOrUserFormType(
												Integer.parseInt(filterUserFormTextBox
														.getText()),
												userSuggestionBox.getText(),
												filterUserFormTypeListBox
														.getItemText(filterUserFormTypeListBox
																.getSelectedIndex()),
												new AsyncCallback<List<UserFormClient>>() {

													@Override
													public void onSuccess(
															List<UserFormClient> result) {
														userForm = result;
														dataCellTable.flush();
														if (userForm.size() > 0) {
															current = userForm
																	.get(0);
															dataCellTable
																	.setRowData(userForm);
														} else {
															Window.alert("No UserForm found");
														}

													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to Filter");
													}
												});
							}
						}
					} else if (userFormOptionListBox.getItemText(
							userFormOptionListBox.getSelectedIndex())
							.equalsIgnoreCase("AND")) {
						webService.getUserFormByIDAndUser(Integer
								.parseInt(filterUserFormTextBox.getText()),
								userSuggestionBox.getText(),
								new AsyncCallback<UserFormClient>() {

									@Override
									public void onSuccess(UserFormClient result) {
										if (result != null) {
											current = result;
											dataCellTable.flush();
											userForm.clear();
											userForm.add(result);
											dataCellTable.setRowData(userForm);
										} else {
											Window.alert("No UserForm Found");
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to filter");
									}
								});
					} else {
						webService.getUserFormByIDorUser(Integer
								.parseInt(filterUserFormTextBox.getText()),
								userSuggestionBox.getText(),
								new AsyncCallback<List<UserFormClient>>() {

									@Override
									public void onSuccess(
											List<UserFormClient> result) {
										userForm = result;
										dataCellTable.flush();
										if (userForm.size() > 0) {
											current = userForm.get(0);
											dataCellTable.setRowData(userForm);
										} else {
											Window.alert("No UserForm found");
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to Filter");
									}
								});
					}

				} else {
					webService.getUserFormById(
							Integer.parseInt(filterUserFormTextBox.getText()),
							new AsyncCallback<UserFormClient>() {

								@Override
								public void onSuccess(UserFormClient result) {
									if (result != null) {
										current = result;
										dataCellTable.flush();
										userForm.clear();
										userForm.add(result);
										dataCellTable.setRowData(userForm);
									} else {
										Window.alert("No UserForm exist with ID: "
												+ filterUserFormTextBox
														.getText());
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to filter");
								}
							});
				}
			} else if (!userSuggestionBox.getText().equals("")) {
				if (!filterUserFormTypeListBox.getItemText(
						filterUserFormTypeListBox.getSelectedIndex())
						.equals("")) {
					if (userFormTypeOptionListBox.getItemText(
							userFormTypeOptionListBox.getSelectedIndex())
							.equalsIgnoreCase("AND")) {
						webService.getUserFormByUserAndUserFormType(
								userSuggestionBox.getText(),
								filterUserFormTypeListBox
										.getItemText(filterUserFormTypeListBox
												.getSelectedIndex()),
								new AsyncCallback<List<UserFormClient>>() {

									@Override
									public void onSuccess(
											List<UserFormClient> result) {
										userForm = result;
										dataCellTable.flush();
										if (userForm.size() > 0) {
											current = userForm.get(0);
											dataCellTable.setRowData(userForm);
										} else {
											Window.alert("No UserForm found");
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to Filter");
									}
								});
					} else {
						webService.getUserFormByUserOrUserFormType(
								userSuggestionBox.getText(),
								filterUserFormTypeListBox
										.getItemText(filterUserFormTypeListBox
												.getSelectedIndex()),
								new AsyncCallback<List<UserFormClient>>() {

									@Override
									public void onSuccess(
											List<UserFormClient> result) {
										userForm = result;
										dataCellTable.flush();
										if (userForm.size() > 0) {
											current = userForm.get(0);
											dataCellTable.setRowData(userForm);
										} else {
											Window.alert("No UserForm found");
										}

									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to Filter");
									}
								});
					}
				} else {
					webService.getUserFormByUser(userSuggestionBox.getText(),
							new AsyncCallback<List<UserFormClient>>() {

								@Override
								public void onSuccess(
										List<UserFormClient> result) {
									userForm = result;
									dataCellTable.flush();
									if (userForm.size() > 0) {
										current = userForm.get(0);
										dataCellTable.setRowData(userForm);
									} else {
										Window.alert("No UserForm found");
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to Filter");
								}
							});
				}
			} else if (!filterUserFormTypeListBox.getItemText(
					filterUserFormTypeListBox.getSelectedIndex()).equals("")) {
				webService.getUserFormByUserFormType(filterUserFormTypeListBox
						.getItemText(filterUserFormTypeListBox
								.getSelectedIndex()),
						new AsyncCallback<List<UserFormClient>>() {

							@Override
							public void onSuccess(List<UserFormClient> result) {
								userForm = result;
								dataCellTable.flush();
								if (userForm.size() > 0) {
									current = userForm.get(0);
									dataCellTable.setRowData(userForm);
								} else {
									Window.alert("No UserForm found");
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("Unable to filter");
							}
						});
			} else {
				Window.alert("No filtering property has been set. Select atleast one to filter");
			}
		} else if (source == searchUserFormButton) {
			if (onlyContainsNumbers(searchUserFormTextBox.getText())) {
				webService.getUserFormById(
						Integer.parseInt(searchUserFormTextBox.getText()),
						new AsyncCallback<UserFormClient>() {

							@Override
							public void onSuccess(UserFormClient result) {
								if (result != null) {
									current = result;
									suggestionBox.setText(result.getUserId()
											.getUsername());
									userFormTypeListBox.setSelectedIndex(userFormType
											.indexOf(result.getUserFormType()
													.getUserFormType()));
									webService
											.getUserFormResultByUserForm(
													result,
													new AsyncCallback<List<UserFormResultClient>>() {

														@Override
														public void onSuccess(
																List<UserFormResultClient> result) {
															userFormAttributePanel
																	.clear();
															for (UserFormResultClient item : result) {
																SuggestBox attributeSuggestBox = new SuggestBox(
																		oracle3);
																TextBox attributeValueTextBox = new TextBox();
																Button deleteRow = new Button(
																		"X");
																attributeSuggestBox
																		.setText(item
																				.getElement()
																				.getElementName());
																attributeValueTextBox
																		.setText(item
																				.getResult());
																HorizontalPanel newPanel = new HorizontalPanel();
																deleteRow
																		.setTitle(String
																				.valueOf(rowCount));
																newPanel.setSpacing(5);
																newPanel.add(attributeSuggestBox);
																newPanel.add(attributeValueTextBox);
																newPanel.add(deleteRow);
																userFormAttributePanel
																		.add(newPanel);
																rowCount++;
																deleteRow
																		.addClickHandler(new ClickHandler() {

																			@Override
																			public void onClick(
																					ClickEvent event) {
																				Button button = (Button) event
																						.getSource();
																				for (int i = 0; i < userFormAttributePanel
																						.getWidgetCount(); i++) {
																					HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
																							.getWidget(i);
																					Button thisButton = (Button) horizontal
																							.getWidget(2);
																					if (thisButton
																							.getTitle()
																							.equals(button
																									.getTitle())) {
																						userFormAttributePanel
																								.remove(i);
																					}
																				}
																			}
																		});
															}

														}

														@Override
														public void onFailure(
																Throwable caught) {
															Window.alert("Unable to fetch UserFormResult for current UserForm");
														}
													});
								} else {
									Window.alert("No UserForm Found with ID: "
											+ searchUserFormTextBox.getText());
								}
							}

							@Override
							public void onFailure(Throwable caught) {
							}
						});
			} else {
				Window.alert("Please Enter numeric ID for UserForm");
			}
		}

	}

	@Override
	public void fill() {
		webService
				.getLatestUserForms(new AsyncCallback<List<UserFormClient>>() {

					@Override
					public void onSuccess(List<UserFormClient> result) {
						userForm = result;
						dataCellTable.flush();
						if (userForm.size() > 0) {
							current = userForm.get(0);
							dataCellTable.setRowData(userForm);
						} else {
							Window.alert("No UserForm found");
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to Filter");
					}
				});
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		if (!checkDuplicateUserAttribute()) {
			Window.alert("Duplicate UserAttribute, Please select Unique User Attribute");
			valid = false;
		}
		return valid;
	}

	@Override
	public void setPrivileges() {
	}

	@Override
	public void save() {
		if (validate()) {
			webService.saveUserForm(suggestionBox.getText(),
					userFormTypeListBox.getItemText(userFormTypeListBox
							.getSelectedIndex()), dateBox.getTextBox()
							.getText(), new AsyncCallback<Integer>() {

						@Override
						public void onSuccess(Integer result) {
							for (int i = 0; i < userFormAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
										.getWidget(i);
								SuggestBox thisSuggestBox = (SuggestBox) horizontal
										.getWidget(0);
								TextBox thisTextBox = (TextBox) horizontal
										.getWidget(1);
								String attributeName = thisSuggestBox.getText();
								String attributeValue = thisTextBox.getText();
								webService.saveUserFormResult(result,
										attributeName, attributeValue,
										new AsyncCallback<Void>() {

											@Override
											public void onSuccess(Void result) {
											}

											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("Unable to save UserForm Result"
														+ caught.getMessage());
											}
										});
							}
							Window.alert("Succesfully saved UserForm");
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save UserForm");
						}
					});

		}
	}

	@Override
	public void update() {
		if (validate()) {
			webService.updateUserForm(Integer.parseInt(searchUserFormTextBox
					.getText()), suggestionBox.getText(), userFormTypeListBox
					.getItemText(userFormTypeListBox.getSelectedIndex()),
					dateBox.getTextBox().getText(),
					new AsyncCallback<UserFormClient>() {

						@Override
						public void onSuccess(UserFormClient result) {
							webService.deleteAllUserFormResulltByUserForm(
									result, new AsyncCallback<Void>() {

										@Override
										public void onSuccess(Void result) {
											Window.alert("Deleted All Location Attributes");
										}

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Unable to Update Location Attributes");
										}
									});
							for (int i = 0; i < userFormAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
										.getWidget(i);
								SuggestBox thisSuggestBox = (SuggestBox) horizontal
										.getWidget(0);
								TextBox thisTextBox = (TextBox) horizontal
										.getWidget(1);
								String attributeName = thisSuggestBox.getText();
								String attributeValue = thisTextBox.getText();
								webService.saveUserFormResult(
										result.getUserFormId(), attributeName,
										attributeValue,
										new AsyncCallback<Void>() {

											@Override
											public void onSuccess(Void result) {
											}

											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("Unable to save UserForm Result"
														+ caught.getMessage());
											}
										});
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to save UserForm Result"
									+ caught.getMessage());
						}
					});
		}
	}

	@Override
	public void delete() {
		webService.deleteAllUserFormResulltByUserForm(current,
				new AsyncCallback<Void>() {

					@Override
					public void onSuccess(Void result) {
						webService.deleteUserForm(current,
								new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										Window.alert("Successfully deleted UserForm");
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to delete UserForm");
									}
								});
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to delete UserForm Result");
					}
				});
	}

	public void clear() {
		clear(userFormFlexTable);
		userFormAttributePanel.clear();
		clear(userFormAttributeFlexTable);
	}

	public boolean checkDuplicateUserAttribute() {
		List<String> attributesList = new ArrayList<String>();
		for (int i = 0; i < userFormAttributePanel.getWidgetCount(); i++) {
			HorizontalPanel horizontal = (HorizontalPanel) userFormAttributePanel
					.getWidget(i);
			SuggestBox thisSuggestBox = (SuggestBox) horizontal.getWidget(0);
			if (attributesList.contains(thisSuggestBox.getText())) {
				return false;
			} else {
				attributesList.add(thisSuggestBox.getText());
			}
		}
		return true;
	}

	private boolean onlyContainsNumbers(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
