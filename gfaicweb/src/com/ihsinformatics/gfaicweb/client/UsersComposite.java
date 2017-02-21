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
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.CellPreviewEvent;
import com.google.gwt.view.client.CellPreviewEvent.Handler;
import com.ihsinformatics.gfaicweb.shared.CustomMessage;
import com.ihsinformatics.gfaicweb.shared.ErrorType;
import com.ihsinformatics.gfaicweb.shared.model.LocationClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.RoleClient;
import com.ihsinformatics.gfaicweb.shared.model.UserAttributeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.UsersClient;

/**
 * @author owais.hussain@ihsinformatics.com
 * 
 */
public class UsersComposite extends AbstractComposite {

	private final GreetingServiceAsync WEB_SERVICE = GWT
			.create(GreetingService.class);

	SimplePanel gridPanel = new SimplePanel();

	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel centerVerticalPanel = new VerticalPanel();
	private VerticalPanel userRolesButtonPanel = new VerticalPanel();
	private VerticalPanel userLocationsButtonPanel = new VerticalPanel();
	private VerticalPanel allLocationsPanel = new VerticalPanel();
	private VerticalPanel verticalPanel = new VerticalPanel();
	private VerticalPanel deleteUserPanel = new VerticalPanel();
	private VerticalPanel passwordPanel = new VerticalPanel();
	private VerticalPanel disableUserPanel = new VerticalPanel();
	private VerticalPanel userAttributePanel = new VerticalPanel();

	private HorizontalPanel picturePanel = new HorizontalPanel();
	private HorizontalPanel locationFilterPanel = new HorizontalPanel();
	private HorizontalPanel securityPanel = new HorizontalPanel();
	private HorizontalPanel deleteConfirmationPanel = new HorizontalPanel();
	private HorizontalPanel passwordHorizontalPanel = new HorizontalPanel();
	private HorizontalPanel searchUserHorizontalPanel = new HorizontalPanel();
	private HorizontalPanel disableUserHorizontalPanel = new HorizontalPanel();
	private HorizontalPanel userAttributeHorizontalPanel = new HorizontalPanel();

	private FlexTable topFlexTable = new FlexTable();
	private FlexTable userFlexTable = new FlexTable();
	private FlexTable userRolesFlexTable = new FlexTable();
	private FlexTable userLocationsFlexTable = new FlexTable();
	private FlexTable centerFlexTable = new FlexTable();
	private FlexTable bottomFlexTable = new FlexTable();
	private FlexTable filterTable = new FlexTable();
	private FlexTable userAttributeFlexTable = new FlexTable();

	private ScrollPanel gridScrollPanel = new ScrollPanel();

	private Grid userRolesGrid = new Grid(2, 3);
	private Grid userLocationsGrid = new Grid(2, 3);
	private Grid grid = new Grid(1, 2);

	private TabLayoutPanel formTabLayoutPanel = new TabLayoutPanel(2.0, Unit.EM);
	private DecoratedStackPanel formStackPanel = new DecoratedStackPanel();

	private Label formTitleLabel = new Label("Users");
	private Label userIdLabel = new Label("User ID:");
	private Label usernameLabel = new Label("Username:");
	private Label fullNameLabel = new Label("Full Name:");
	private Label pictureLabel = new Label("Picture:");
	private Label userRolesLabel = new Label("User Roles:");
	private Label selectedRolesLabel = new Label("Selected Roles");
	private Label allRolesLabel = new Label("All Roles");
	private Label locationsLabel = new Label("Locations:");
	private Label selectedLocationsLabel = new Label("Selected Locations");
	private Label allLocationsLabel = new Label("All Locations");
	private Label locationPatternLabel = new Label(
			"Enter pattern to limit locations");
	private Label securityLabel = new Label("Security:");
	private Label lblUsername = new Label("Username");
	private Label lblFullName = new Label("Full Name");
	private Label globalAccessLabel = new Label("Global Data Access:");
	private Label secretQuestionLabel = new Label("Secret Question:");
	private Label answerLabel = new Label("Answer:");
	private Label content = new Label();
	private Label retrievalLabel = new Label("Retrieval form: ");
	private Label passwordLabel = new Label("Enter Password");
	private Label reasonDisableLabel = new Label("Enter Reason for Disabled");
	private Label reasonShowLabel = new Label();
	private Label addUserAttribute = new Label("Add new User Attribute");

	private CheckBox globalAccessCheckBox = new CheckBox();

	private TextBox answerTextBox = new TextBox();
	private TextBox reasonTextBox = new TextBox();
	private TextBox usernameTextBox = new TextBox();
	private TextBox fullNameTextBox = new TextBox();
	private TextBox locationFilterTextBox = new TextBox();
	private TextBox usernameFilterTextBox = new TextBox();
	private TextBox fullNameFilterTextBox = new TextBox();
	private TextBox userIdTextBox = new TextBox();
	private TextBox dltUserTextBox = new TextBox();

	private PasswordTextBox passwordTextBox = new PasswordTextBox();

	private ListBox selectedRolesListBox = new ListBox(true);
	private ListBox rolesListBox = new ListBox(true);
	private ListBox selectedLocationsListBox = new ListBox(true);
	private ListBox locationsListBox = new ListBox(true);
	private ListBox secretQuestionListBox = new ListBox();
	private ListBox filterOptionListBox = new ListBox();

	private Button addAllRolesButton = new Button("<<");
	private Button addRolesButton = new Button("<");
	private Button removeRolesButton = new Button(">");
	private Button removeAllRolesButton = new Button(">>");
	private Button addAllLocationsButton = new Button("<<");
	private Button addLocationsButton = new Button("<");
	private Button removeLocationsButton = new Button(">");
	private Button removeAllLocationsButton = new Button(">>");
	private Button changePasswordButton = new Button("Change Password");
	private Button disableUserButton = new Button("Disable User");
	private Button enableUserButton = new Button("Enable User");
	private Button saveButton = new Button("Save");
	private Button clearButton = new Button("Clear");
	private Button deleteButton = new Button("Delete");
	private Button closeButton = new Button("Close");
	private Button filterButton = new Button("Filter");
	private Button yes = new Button("YES");
	private Button no = new Button("NO");
	private Button saveUser = new Button("Save User");
	private Button passwordCloseButton = new Button("Close");
	private Button searchUserButton = new Button("Search User");
	private Button disableUserDialogButton = new Button("Disable User");
	private Button disableUserCloseButton = new Button("Close");
	private Button addUserAttributeButton = new Button("+");

	private final DialogBox dialog = new DialogBox(false, true);
	private final DialogBox passwordDialog = new DialogBox(false, true);
	private final DialogBox disableDialog = new DialogBox(false, true);

	private ToggleButton createButton = new ToggleButton("Create New");
	private FileUpload pictureUpload = new FileUpload();
	private Image userImage;
	private CellTable<UsersClient> dataCellTable = new CellTable<UsersClient>();
	private List<UsersClient> users = new ArrayList<UsersClient>();
	private List<String> questionList = new ArrayList<String>();
	private List<String> attributeNames = new ArrayList<String>();
	private List<UserAttributeTypeClient> attributeList = new ArrayList<UserAttributeTypeClient>();
	private List<UserAttributeClient> getExistingUserAttribute = new ArrayList<UserAttributeClient>();
	private UsersClient current = null;
	private int rowCount = 0;

	public UsersComposite() {
		TBR.setCurrentForm("USERS");
		WEB_SERVICE
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
		initWidget(mainPanel);
		mainPanel.setSize("900px", "100%");
		mainPanel.add(topFlexTable);
		formTitleLabel.setStyleName("title");
		topFlexTable.setWidget(0, 0, formTitleLabel);
		mainPanel.add(centerVerticalPanel);
		centerVerticalPanel.setSize("100%", "100%");
		formTabLayoutPanel.setAnimationDuration(500);
		formTabLayoutPanel.setWidth("913px");
		formTabLayoutPanel.setAnimationVertical(true);
		formTabLayoutPanel.add(formStackPanel, "Form View", false);
		formStackPanel.setWidth("100%");
		formStackPanel.add(userFlexTable, "User Information", false);
		userFlexTable.setSize("100%", "100%");
		userFlexTable.setWidget(0, 0, userIdLabel);
		userIdLabel.setStyleName("label");
		userFlexTable.setWidget(0, 1, searchUserHorizontalPanel);
		searchUserHorizontalPanel.add(userIdTextBox);
		userIdTextBox.setStyleName("textbox");
		userIdTextBox.setWidth("55px");
		userIdTextBox.setMaxLength(6);
		searchUserHorizontalPanel.add(searchUserButton);
		searchUserHorizontalPanel.setSpacing(5);
		searchUserButton.setStyleName("search-user-button");
		searchUserButton.setSize("50px", "50px");
		userFlexTable.setWidget(1, 0, usernameLabel);
		usernameLabel.setStyleName("label");
		userFlexTable.setWidget(1, 1, usernameTextBox);
		usernameTextBox.setStyleName("textbox");
		userFlexTable.setWidget(2, 0, fullNameLabel);
		fullNameLabel.setStyleName("label");
		userFlexTable.setWidget(2, 1, fullNameTextBox);
		fullNameTextBox.setVisibleLength(30);
		fullNameTextBox.setStyleName("textbox");
		pictureLabel.setStyleName("label");
		userFlexTable.setWidget(3, 0, globalAccessLabel);
		globalAccessLabel.setStyleName("label");
		userFlexTable.setWidget(3, 1, globalAccessCheckBox);
		globalAccessCheckBox.setValue(true);
		userFlexTable.setWidget(5, 0, secretQuestionLabel);
		secretQuestionLabel.setStyleName("label");
		questionList.add("WHO IS YOUR FAVOURITE NATIONAL HERO?");
		questionList.add("WHAT PHONE MODEL ARE YOU PLANNING TO PURCHASE NEXT?");
		questionList.add("WHERE WAS YOUR MOTHER BORN?");
		questionList.add("WHEN DID YOU BUY YOUR FIRST CAR?");
		questionList.add("WHAT WAS YOUR CHILDHOOD NICKNAME?");
		questionList.add("WHAT IS YOUR FAVOURITE CARTOON CHARACTER?");
		for (String question : questionList) {
			secretQuestionListBox.addItem(question);
		}
		userFlexTable.setWidget(5, 1, secretQuestionListBox);
		secretQuestionListBox.setStyleName("listbox");
		userFlexTable.setWidget(6, 0, answerLabel);
		answerLabel.setStyleName("label");
		userFlexTable.setWidget(6, 1, answerTextBox);
		answerTextBox.setStyleName("textbox");
		userFlexTable.setWidget(7, 0, pictureLabel);
		userFlexTable.setWidget(7, 1, picturePanel);
		dialog.setText("Confirmation");
		deleteUserPanel.add(content);
		deleteUserPanel.add(deleteConfirmationPanel);
		deleteConfirmationPanel.add(yes);
		deleteConfirmationPanel.add(no);
		deleteConfirmationPanel.setSpacing(15);
		deleteConfirmationPanel.setWidth("60%");
		deleteUserPanel.setCellHorizontalAlignment(deleteConfirmationPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		deleteUserPanel.setSpacing(15);
		dialog.setWidget(deleteUserPanel);
		picturePanel.add(pictureUpload);
		passwordPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		passwordPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		passwordPanel.setSpacing(10);
		passwordPanel.add(passwordLabel);
		passwordLabel.setStyleName("label");
		passwordPanel.add(passwordTextBox);
		passwordTextBox.setStyleName("textbox");
		passwordHorizontalPanel.add(saveUser);
		passwordHorizontalPanel.setSpacing(5);
		passwordHorizontalPanel.add(passwordCloseButton);
		passwordPanel.add(passwordHorizontalPanel);
		passwordDialog.setText("Password Dialog");
		passwordDialog.add(passwordPanel);
		disableDialog.setText("Disable Reason");
		disableDialog.add(disableUserPanel);
		disableUserPanel.add(reasonDisableLabel);
		reasonDisableLabel.setStyleName("label");
		disableUserPanel.add(reasonTextBox);
		reasonTextBox.setStyleName("textbox");
		reasonTextBox.setVisibleLength(35);
		disableUserPanel
				.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		disableUserPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		disableUserHorizontalPanel.add(disableUserDialogButton);
		disableUserHorizontalPanel.add(disableUserCloseButton);
		disableUserPanel.add(disableUserHorizontalPanel);
		disableUserHorizontalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		disableUserHorizontalPanel.setSpacing(5);
		formStackPanel.add(userRolesFlexTable, "User Roles", false);
		userRolesFlexTable.setSize("100%", "100%");
		userRolesFlexTable.setWidget(0, 0, userRolesLabel);
		userRolesLabel.setStyleName("label");
		userRolesFlexTable.setWidget(0, 1, userRolesGrid);
		userRolesGrid.setWidget(0, 0, selectedRolesLabel);
		selectedRolesLabel.setStyleName("sub-label");
		userRolesGrid.setWidget(0, 2, allRolesLabel);
		allRolesLabel.setStyleName("sub-label");
		userRolesGrid.setWidget(1, 0, selectedRolesListBox);
		selectedRolesListBox.setStyleName("list-box");
		selectedRolesListBox.setWidth("200px");
		selectedRolesListBox.setVisibleItemCount(7);
		userRolesGrid.setWidget(1, 1, userRolesButtonPanel);
		addAllRolesButton.setStyleName("cursor-button");
		userRolesButtonPanel.add(addAllRolesButton);
		addRolesButton.setStyleName("cursor-button");
		userRolesButtonPanel.add(addRolesButton);
		addRolesButton.setWidth("100%");
		removeRolesButton.setStyleName("cursor-button");
		userRolesButtonPanel.add(removeRolesButton);
		removeRolesButton.setWidth("100%");
		removeAllRolesButton.setStyleName("cursor-button");
		userRolesButtonPanel.add(removeAllRolesButton);
		userRolesGrid.setWidget(1, 2, rolesListBox);
		rolesListBox.setStyleName("list-box");
		rolesListBox.setWidth("200px");
		rolesListBox.setVisibleItemCount(7);
		WEB_SERVICE.getAllRoles(new AsyncCallback<List<RoleClient>>() {

			@Override
			public void onSuccess(List<RoleClient> result) {
				for (RoleClient role : result) {
					rolesListBox.addItem(role.getRoleName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch all Roles");
			}
		});
		formStackPanel.add(userLocationsFlexTable, "User Locations", false);
		userLocationsFlexTable.setSize("100%", "100%");
		userLocationsFlexTable.setWidget(0, 0, locationsLabel);
		locationsLabel.setStyleName("label");
		userLocationsFlexTable.setWidget(0, 1, userLocationsGrid);
		selectedLocationsLabel.setStyleName("sub-label");
		userLocationsGrid.setWidget(0, 0, selectedLocationsLabel);
		allLocationsLabel.setStyleName("sub-label");
		userLocationsGrid.setWidget(0, 2, allLocationsLabel);
		selectedLocationsListBox.setVisibleItemCount(10);
		selectedLocationsListBox.setStyleName("list-box");
		WEB_SERVICE.getAllLocations(new AsyncCallback<List<LocationClient>>() {

			@Override
			public void onSuccess(List<LocationClient> result) {
				for (LocationClient location : result) {
					locationsListBox.addItem(location.getLocationName());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch all locations");
			}
		});
		userLocationsGrid.setWidget(1, 0, selectedLocationsListBox);

		selectedLocationsListBox.setWidth("250px");
		userLocationsGrid.setWidget(1, 1, userLocationsButtonPanel);
		addAllLocationsButton.setStyleName("cursor-button");
		userLocationsButtonPanel.add(addAllLocationsButton);
		addLocationsButton.setStyleName("cursor-button");
		userLocationsButtonPanel.add(addLocationsButton);
		addLocationsButton.setWidth("100%");
		removeLocationsButton.setStyleName("cursor-button");
		userLocationsButtonPanel.add(removeLocationsButton);
		removeLocationsButton.setWidth("100%");
		removeAllLocationsButton.setStyleName("cursor-button");
		userLocationsButtonPanel.add(removeAllLocationsButton);
		userLocationsGrid.setWidget(1, 2, allLocationsPanel);
		locationsListBox.setVisibleItemCount(7);
		locationsListBox.setStyleName("list-box");
		allLocationsPanel.add(locationsListBox);
		locationsListBox.setWidth("250px");
		allLocationsPanel.add(locationFilterPanel);
		locationFilterTextBox.setVisibleLength(5);
		locationFilterTextBox.setStyleName("textbox");
		locationFilterTextBox.setMaxLength(5);
		locationFilterPanel.add(locationFilterTextBox);
		locationFilterTextBox.setWidth("");
		locationFilterTextBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				boolean enterPressed = KeyCodes.KEY_ENTER == event
						.getNativeEvent().getKeyCode();
				if (enterPressed) {
					WEB_SERVICE.getFilterLocations(locationFilterTextBox
							.getText().toLowerCase(),
							new AsyncCallback<List<LocationClient>>() {

								@Override
								public void onSuccess(
										List<LocationClient> result) {
									locationsListBox.clear();
									for (LocationClient location : result) {
										locationsListBox.addItem(location
												.getLocationName());
									}

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to filter Locations");
								}
							});
				}

			}
		});
		locationPatternLabel.setStyleName("message");
		locationFilterPanel.add(locationPatternLabel);
		locationFilterPanel.setCellVerticalAlignment(locationPatternLabel,
				HasVerticalAlignment.ALIGN_MIDDLE);
		ScrollPanel scrollPanel = new ScrollPanel(userAttributeFlexTable);
		scrollPanel.setSize("100%", "256px");
		formStackPanel.add(scrollPanel, "User Attribute", false);
		userAttributeHorizontalPanel.add(addUserAttributeButton);
		userAttributeHorizontalPanel.add(addUserAttribute);
		addUserAttributeButton.setStyleName("operation-button");
		userAttributeFlexTable.setWidget(0, 0, userAttributeHorizontalPanel);
		addUserAttribute.setStyleName("sub-label2");
		userAttributeFlexTable.setWidget(1, 0, userAttributePanel);
		WEB_SERVICE
				.getAllUserAttribute(new AsyncCallback<List<UserAttributeTypeClient>>() {

					@Override
					public void onSuccess(List<UserAttributeTypeClient> result) {
						for (UserAttributeTypeClient item : result) {
							attributeList.add(item);
							attributeNames.add(item.getAttributeName());
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Unable to fetch User Attributes");
					}
				});
		addUserAttributeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (current == null && !createButton.isDown()) {
					Window.alert("Please select User first to add UserAttribute");
				} else {
					HorizontalPanel newPanel = new HorizontalPanel();
					ListBox attributeListBox = new ListBox();
					for (UserAttributeTypeClient item2 : attributeList) {
						attributeListBox.addItem(item2.getAttributeName());
					}
					TextBox attributeValueTextBox = new TextBox();
					Button deleteRow = new Button("X");
					deleteRow.setTitle(String.valueOf(rowCount));
					newPanel.setSpacing(5);
					newPanel.add(attributeListBox);
					newPanel.add(attributeValueTextBox);
					newPanel.add(deleteRow);
					userAttributePanel.add(newPanel);
					rowCount++;

					deleteRow.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Button button = (Button) event.getSource();
							for (int i = 0; i < userAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
										.getWidget(i);
								Button thisButton = (Button) horizontal
										.getWidget(2);
								if (thisButton.getTitle().equals(
										button.getTitle())) {
									userAttributePanel.remove(i);
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
		gridScrollPanel.setWidget(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		verticalPanel.add(grid);
		filterTable.setWidget(0, 0, lblUsername);
		lblUsername.setStyleName("label");
		filterTable.setWidget(0, 2, lblFullName);
		lblFullName.setStyleName("label");
		filterTable.setWidget(1, 0, usernameFilterTextBox);
		usernameFilterTextBox.setVisibleLength(10);
		usernameFilterTextBox.setStyleName("textbox");
		usernameFilterTextBox.setMaxLength(40);
		filterTable.setWidget(1, 1, filterOptionListBox);
		filterTable.setWidget(1, 2, fullNameFilterTextBox);
		fullNameFilterTextBox.setVisibleLength(10);
		fullNameFilterTextBox.setStyleName("textbox");
		fullNameFilterTextBox.setMaxLength(40);
		filterTable.setWidget(1, 3, filterButton);
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
		formatter2.setHorizontalAlignment(1, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter2.setHorizontalAlignment(1, 2,
				HasHorizontalAlignment.ALIGN_CENTER);
		formatter2
				.setVerticalAlignment(1, 2, HasVerticalAlignment.ALIGN_MIDDLE);
		filterOptionListBox.addItem("AND");
		filterOptionListBox.addItem("OR");
		grid.setSize("850px", "20px");
		grid.setWidget(0, 0, filterTable);
		retrievalLabel.setStyleName("label");
		HTMLTable.CellFormatter formatter = grid.getCellFormatter();
		formatter.setHorizontalAlignment(0, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
		formatter.setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		grid.setCellPadding(3);
		dataCellTable.setPageSize(10);
		verticalPanel.add(dataCellTable);
		dataCellTable.setSize("100%", "100%");
		centerVerticalPanel.add(formTabLayoutPanel);
		formTabLayoutPanel.setHeight("500px");
		mainPanel.add(centerFlexTable);
		centerFlexTable.setWidth("100%");
		securityLabel.setStyleName("label");
		centerFlexTable.setWidget(0, 0, securityLabel);
		centerFlexTable.setWidget(0, 1, securityPanel);
		secretQuestionLabel.setStyleName("label");
		securityPanel.add(changePasswordButton);
		securityPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		securityPanel.setCellHorizontalAlignment(reasonShowLabel,
				HasHorizontalAlignment.ALIGN_RIGHT);
		changePasswordButton.setStyleName("operation-button");
		securityPanel.add(disableUserButton);
		securityPanel.add(enableUserButton);
		enableUserButton.setStyleName("operation-button");
		disableUserButton.setStyleName("operation-button");
		securityPanel.add(reasonShowLabel);
		reasonShowLabel.setStyleName("sub-label2");
		reasonShowLabel.setVisible(false);
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
		disableUserButton.addClickHandler(this);
		changePasswordButton.addClickHandler(this);
		filterButton.addClickHandler(this);
		yes.addClickHandler(this);
		no.addClickHandler(this);
		saveUser.addClickHandler(this);
		passwordCloseButton.addClickHandler(this);
		addAllRolesButton.addClickHandler(this);
		removeAllRolesButton.addClickHandler(this);
		removeRolesButton.addClickHandler(this);
		addRolesButton.addClickHandler(this);
		addAllLocationsButton.addClickHandler(this);
		addLocationsButton.addClickHandler(this);
		removeAllLocationsButton.addClickHandler(this);
		removeLocationsButton.addClickHandler(this);
		searchUserButton.addClickHandler(this);
		enableUserButton.addClickHandler(this);
		disableUserDialogButton.addClickHandler(this);
		disableUserCloseButton.addClickHandler(this);
		addUserAttributeButton.addClickHandler(this);
		createColumns();
		fill();
	}

	private void createColumns() {
		TextColumn<UsersClient> userIdTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				return object.getUserId().toString();
			}
		};
		userIdTextColumn.setSortable(true);
		dataCellTable.addColumn(userIdTextColumn, "UserID");

		TextColumn<UsersClient> usernamEditTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				return (String) object.getUsername();
			}
		};
		usernamEditTextColumn.setSortable(true);
		dataCellTable.addColumn(usernamEditTextColumn, "Username");

		TextColumn<UsersClient> nameEditTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				return (String) object.getFullName();
			}
		};
		nameEditTextColumn.setSortable(true);
		dataCellTable.addColumn(nameEditTextColumn, "Full Name");

		TextColumn<UsersClient> globalAccessCheckBoxColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				return object.getGlobalDataAccess().toString();
			}
		};
		globalAccessCheckBoxColumn.setSortable(true);
		dataCellTable.addColumn(globalAccessCheckBoxColumn, "Global Access");

		TextColumn<UsersClient> createdByTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				UsersClient creator = object.getUsersByCreatedBy();
				if (creator != null) {
					return creator.getUsername();
				}
				return "";
			}
		};
		createdByTextColumn.setSortable(true);
		dataCellTable.addColumn(createdByTextColumn, "Created By");

		TextColumn<UsersClient> createdAtTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				LocationClient location = object.getLocationByCreatedAt();
				if (location != null) {
					return location.getLocationName();
				}
				return "";
			}
		};
		createdAtTextColumn.setSortable(true);
		dataCellTable.addColumn(createdAtTextColumn, "Created At");

		TextColumn<UsersClient> disabledCheckBoxColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				try {
					return object.getDisabled().toString();
				} catch (Exception e) {
					return null;
				}

			}
		};
		disabledCheckBoxColumn.setSortable(true);
		dataCellTable.addColumn(disabledCheckBoxColumn, "Disabled");

		TextColumn<UsersClient> uuidTextColumn = new TextColumn<UsersClient>() {
			@Override
			public String getValue(UsersClient object) {
				return object.getUuid();
			}
		};
		uuidTextColumn.setSortable(true);
		dataCellTable.addColumn(uuidTextColumn, "UUID");
		dataCellTable.addCellPreviewHandler(new Handler<UsersClient>() {

			@Override
			public void onCellPreview(CellPreviewEvent<UsersClient> event) {
				if (event.getNativeEvent().getType().contains("click")) {
					userIdTextBox.setText(event.getValue().getUserId()
							.toString());
					usernameTextBox.setText(event.getValue().getUsername());
					fullNameTextBox.setText(event.getValue().getFullName());
					globalAccessCheckBox.setValue(event.getValue()
							.getGlobalDataAccess());
					secretQuestionListBox.setSelectedIndex(questionList
							.indexOf(event.getValue().getSecretQuestion()));
					answerLabel.setVisible(false);
					answerTextBox.setVisible(false);
					current = event.getValue();
					WEB_SERVICE.getCurrentUserRole(current,
							new AsyncCallback<List<RoleClient>>() {

								@Override
								public void onSuccess(List<RoleClient> result) {
									selectedRolesListBox.clear();
									for (RoleClient role : result) {
										selectedRolesListBox.addItem(role
												.getRoleName());
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to fetch User Role");

								}
							});
					WEB_SERVICE.getCurrentUserLocation(current,
							new AsyncCallback<List<LocationClient>>() {

								@Override
								public void onSuccess(
										List<LocationClient> result) {
									selectedLocationsListBox.clear();
									for (LocationClient location : result) {
										selectedLocationsListBox
												.addItem(location
														.getLocationName());
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to fetch locations");
								}
							});

					WEB_SERVICE.getUserAttribute(current,
							new AsyncCallback<List<UserAttributeClient>>() {

								@Override
								public void onSuccess(
										List<UserAttributeClient> result) {
									userAttributePanel.clear();
									for (UserAttributeClient item : result) {
										ListBox attributeListBox = new ListBox();
										TextBox attributeValueTextBox = new TextBox();
										Button deleteRow = new Button("X");
										for (UserAttributeTypeClient item2 : attributeList) {
											attributeListBox.addItem(item2
													.getAttributeName());
										}
										getExistingUserAttribute.add(item);
										attributeListBox
												.setSelectedIndex(attributeNames
														.indexOf(item
																.getUserAttributeType()
																.getAttributeName()));
										attributeValueTextBox.setText(item
												.getAttributeValue());
										HorizontalPanel newPanel = new HorizontalPanel();
										deleteRow.setTitle(String
												.valueOf(rowCount));
										newPanel.setSpacing(5);
										newPanel.add(attributeListBox);
										newPanel.add(attributeValueTextBox);
										newPanel.add(deleteRow);
										userAttributePanel.add(newPanel);
										rowCount++;
										deleteRow
												.addClickHandler(new ClickHandler() {

													@Override
													public void onClick(
															ClickEvent event) {
														Button button = (Button) event
																.getSource();
														for (int i = 0; i < userAttributePanel
																.getWidgetCount(); i++) {
															HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
																	.getWidget(i);
															Button thisButton = (Button) horizontal
																	.getWidget(2);
															if (thisButton
																	.getTitle()
																	.equals(button
																			.getTitle())) {
																userAttributePanel
																		.remove(i);
															}
														}
													}
												});
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to fetch UserAttribute for User");
								}
							});
					if (event.getValue().getDisabled() == true) {
						disableControllers();
					} else {
						enableControllers();
					}
					if (current.getDisabled() == true) {
						disableUserButton.setEnabled(false);
						reasonShowLabel.setVisible(true);
						reasonShowLabel.setText("Reason Disable : "
								+ current.getReasonDisabled());
						enableUserButton.setEnabled(true);
					} else {
						disableUserButton.setEnabled(true);
						reasonShowLabel.setVisible(false);
						enableUserButton.setEnabled(false);
					}

				}

			}

		});
	}

	public void setCurrent() {
		userIdTextBox.setText(current.getUserId().toString());
		usernameTextBox.setValue(current.getUsername());
		fullNameTextBox.setValue(current.getFullName());
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if (source == createButton) {
			if (createButton.isDown()) {
				clear();
				enableControllers();
				current = null;
				userIdTextBox.setText(null);
				userIdTextBox.setVisible(false);
				searchUserButton.setVisible(false);
			} else {
				clear();
				current = null;
				userIdTextBox.setText(null);
				userIdTextBox.setVisible(true);
				searchUserButton.setVisible(true);

			}
		} else if (source == saveButton) {
			if (createButton.isDown()) {
				passwordDialog.setPopupPosition(600, 350);
				passwordDialog.show();

			} else {
				if (!userIdTextBox.getText().isEmpty()) {
					update();
				} else {
					Window.alert("ERROR, Please select Definition type from list");
				}
			}
		} else if (source == clearButton) {
			clear();
			current = null;
		} else if (source == deleteButton) {
			content.setText("Are you sure you want to delete user : "
					+ current.getFullName() + "?");
			dialog.setPopupPosition(500, 250);
			dialog.show();
		} else if (source == disableUserButton) {
			if (current == null) {
				Window.alert("Please select User first to Disable/Enable or Create New User");
			} else {
				disableDialog.setPopupPosition(550, 350);
				disableDialog.show();
			}
		} else if (source == enableUserButton) {
			if (current == null) {
				Window.alert("Please select User first to Disable/Enable");
			} else {
				enableUser();
			}
		} else if (source == changePasswordButton) {
		} else if (source == filterButton) {
			filterUsers();
		} else if (source == yes) {
			dialog.hide();
			delete();
		} else if (source == no) {
			dialog.hide();
		} else if (source == closeButton) {
			close();
		} else if (source == saveUser) {
			passwordDialog.hide();
			save();
		} else if (source == passwordCloseButton) {
			passwordDialog.hide();
		} else if (source == addAllRolesButton) {
			if (current == null && !createButton.isDown()) {
				Window.alert("Please select User from Grid View first");
			} else {
				Window.alert("Pressed addAllRoles");
				addAllRoles();
			}

		} else if (source == removeAllRolesButton) {
			if (!isListEmpty(selectedRolesListBox)) {
				removeAllRoles();
			}
		} else if (source == addRolesButton) {
			if (current == null && !createButton.isDown()) {
				Window.alert("Please select User from Grid View first");
			} else {
				addRole();
			}
		} else if (source == removeRolesButton) {
			if (!isListEmpty(selectedRolesListBox)) {
				removeRole();
			}
		} else if (source == addAllLocationsButton) {
			if (current == null && !createButton.isDown()) {
				Window.alert("Please select User from Grid View first");
			} else {
				addAllLocations();
			}
		} else if (source == addLocationsButton) {
			if (current == null && !createButton.isDown()) {
				Window.alert("Please select User from Grid View first");
			} else {
				addLocation();
			}
		} else if (source == removeAllLocationsButton) {
			removeAllLocations();
		} else if (source == removeLocationsButton) {
			removeLocation();
		} else if (source == searchUserButton) {
			if (!userIdTextBox.getText().equals("")) {
				searchUserButtonOperation();
			}
		} else if (source == disableUserDialogButton) {
			disableUser();
		} else if (source == disableUserCloseButton) {
			disableDialog.hide();
		}
	}

	@Override
	public void fill() {
		WEB_SERVICE.getAllUsers(new AsyncCallback<List<UsersClient>>() {

			@Override
			public void onSuccess(List<UsersClient> result) {
				if (result.size() > 10) {
					List<UsersClient> subList = result.subList(
							result.size() - 10, result.size());
					users = subList;
					// current = users.get(0);
					dataCellTable.setRowData(users);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to fetch users " + caught.getMessage());
			}
		});
	}

	@Override
	public boolean validate() {
		final StringBuilder errorMessage = new StringBuilder();
		boolean valid = true;
		/* Validate mandatory fields */
		if (get(fullNameTextBox).equals("")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.EMPTY_DATA_ERROR));
			valid = false;
		}
		if (usernameTextBox.getText().contains(" ")) {
			errorMessage.append(CustomMessage
					.getErrorMessage(ErrorType.INVALID_DATA_ERROR) + "\n");
			valid = false;
		}
		if (!valid) {
			Window.alert(errorMessage.toString());
		}
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
			UsersClient newUser = new UsersClient();
			newUser.setUsername(usernameTextBox.getText());
			newUser.setFullName(fullNameTextBox.getText());
			newUser.setGlobalDataAccess(globalAccessCheckBox.getValue());
			newUser.setDisabled(false);
			newUser.setSecretQuestion(secretQuestionListBox
					.getItemText(secretQuestionListBox.getSelectedIndex()));
			WEB_SERVICE.saveUser(newUser, passwordTextBox.getText(),
					answerTextBox.getText(), new AsyncCallback<UsersClient>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR, Failed to save User "
									+ caught.getMessage());

						}

						@Override
						public void onSuccess(UsersClient result) {
							Window.alert(" User :" + usernameTextBox.getText()
									+ " has been added suceessfully");
							current = result;
							ArrayList<String> roleList = new ArrayList<String>();
							for (int i = 0; i < selectedRolesListBox
									.getItemCount(); i++) {
								roleList.add(selectedRolesListBox
										.getItemText(i));
							}
							WEB_SERVICE.saveUserRole(current, roleList,
									new AsyncCallback<Void>() {

										@Override
										public void onSuccess(Void result) {
											System.out.println(" User :"
													+ usernameTextBox.getText()
													+ " UserRole has been updated suceessfully");
										}

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("ERROR, Failed to update UserRole "
													+ "\n"
													+ caught.getMessage());

										}
									});
							ArrayList<String> locationList = new ArrayList<String>();
							for (int i = 0; i < selectedLocationsListBox
									.getItemCount(); i++) {
								locationList.add(selectedLocationsListBox
										.getItemText(i));
							}
							WEB_SERVICE.saveUserLocation(current, locationList,
									new AsyncCallback<Void>() {

										@Override
										public void onSuccess(Void result) {
											System.out.println(" User :"
													+ usernameTextBox.getText()
													+ " UserLocation has been updated suceessfully");

										}

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("ERROR, Failed to update UserLocation "
													+ "\n"
													+ caught.getMessage());
										}
									});
							for (int i = 0; i < userAttributePanel
									.getWidgetCount(); i++) {
								HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
										.getWidget(i);
								ListBox thisListBox = (ListBox) horizontal
										.getWidget(0);
								TextBox thisTextBox = (TextBox) horizontal
										.getWidget(1);
								String attributeName = thisListBox
										.getItemText(thisListBox
												.getSelectedIndex());
								String attributeValue = thisTextBox.getText();
								WEB_SERVICE.saveUserAttribute(attributeName,
										attributeValue, current,
										new AsyncCallback<Void>() {

											@Override
											public void onSuccess(Void result) {
												System.out.println(" User :"
														+ usernameTextBox
																.getText()
														+ " UserAttributes has been updated suceessfully");
											}

											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("Unable to save UserAttribute");
											}
										});
							}
							if (result.getDisabled() == true) {
								disableControllers();
							} else {
								enableControllers();
							}
							if (current.getDisabled() == true) {
								reasonShowLabel.setVisible(true);
								reasonShowLabel.setText("Reason Disable : "
										+ current.getReasonDisabled());
								disableUserButton.setEnabled(false);
								enableUserButton.setEnabled(true);
							} else {
								reasonShowLabel.setVisible(false);
								disableUserButton.setEnabled(true);
								enableUserButton.setEnabled(false);
							}
							users.add(result);

						}

					});
		}

	}

	@Override
	public void update() {
		if (validate()) {
			UsersClient userClient = new UsersClient();
			userClient.setUserId(Integer.parseInt(userIdTextBox.getText()));
			userClient.setUsername(usernameTextBox.getText());
			userClient.setFullName(fullNameTextBox.getText());
			userClient.setGlobalDataAccess(globalAccessCheckBox.getValue());
			userClient.setSecretQuestion(secretQuestionListBox
					.getItemText(secretQuestionListBox.getSelectedIndex()));
			WEB_SERVICE.updateUsers(userClient, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					ArrayList<String> roleList = new ArrayList<String>();
					for (int i = 0; i < selectedRolesListBox.getItemCount(); i++) {
						roleList.add(selectedRolesListBox.getItemText(i));
					}
					WEB_SERVICE.saveUserRole(current, roleList,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									System.out.println("User :"
											+ usernameTextBox.getText()
											+ " UserRole has been updated suceessfully");
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("ERROR, Failed to update UserRole "
											+ "\n" + caught.getMessage());

								}
							});
					ArrayList<String> locationList = new ArrayList<String>();
					for (int i = 0; i < selectedLocationsListBox.getItemCount(); i++) {
						locationList.add(selectedLocationsListBox
								.getItemText(i));
					}
					WEB_SERVICE.saveUserLocation(current, locationList,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									System.out.println("User :"
											+ usernameTextBox.getText()
											+ " UserLocation has been updated suceessfully");

								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("ERROR, Failed to update UserLocation "
											+ "\n" + caught.getMessage());
								}
							});
					WEB_SERVICE.deleteAllUserAttributebyUser(current,
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("Unable to clear UserAttributes for User");
								}
							});

					for (int i = 0; i < userAttributePanel.getWidgetCount(); i++) {
						HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
								.getWidget(i);
						ListBox thisListBox = (ListBox) horizontal.getWidget(0);
						TextBox thisTextBox = (TextBox) horizontal.getWidget(1);
						String attributeName = thisListBox
								.getItemText(thisListBox.getSelectedIndex());
						String attributeValue = thisTextBox.getText();
						WEB_SERVICE.saveUserAttribute(attributeName,
								attributeValue, current,
								new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("Unable to save UserAttribute");
									}
								});
					}

					Window.alert(" User :" + usernameTextBox.getText()
							+ " has been updated suceessfully");
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERROR, Failed to update User " + "\n"
							+ caught.getMessage());

				}
			});

		}
	}

	@Override
	public void delete() {
		WEB_SERVICE.deleteUser(current.getUserId(), new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				Window.alert("User with username:" + usernameTextBox.getText()
						+ " has been deleted suceessfully");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to delete user with id :"
						+ dltUserTextBox.getText() + "\n" + caught.getMessage());
			}
		});
		fill();
	}

	public boolean isListEmpty(ListBox listbox) {
		if (listbox.getItemCount() == 0) {
			return true;
		}
		return false;
	}

	public void searchUserButtonOperation() {
		WEB_SERVICE.getUserbyUserId(Integer.parseInt(userIdTextBox.getText()),
				new AsyncCallback<UsersClient>() {

					@Override
					public void onSuccess(UsersClient result) {
						if (result != null) {
							current = result;
							usernameTextBox.setText(result.getUsername());
							fullNameTextBox.setText(result.getFullName());
							globalAccessCheckBox.setValue(result
									.getGlobalDataAccess());
							secretQuestionListBox.setSelectedIndex(questionList
									.indexOf(result.getSecretQuestion()));
							answerTextBox.setVisible(false);
							answerLabel.setVisible(false);
							if (result.getDisabled() == true) {
								disableControllers();
							} else {
								enableControllers();
							}
							if (current.getDisabled() == true) {
								reasonShowLabel.setVisible(true);
								reasonShowLabel.setText("Reason Disable : "
										+ current.getReasonDisabled());
								disableUserButton.setEnabled(false);
								enableUserButton.setEnabled(true);
							} else {
								reasonShowLabel.setVisible(false);
								disableUserButton.setEnabled(true);
								enableUserButton.setEnabled(false);
							}

							WEB_SERVICE
									.getUserAttribute(
											current,
											new AsyncCallback<List<UserAttributeClient>>() {

												@Override
												public void onSuccess(
														List<UserAttributeClient> result) {
													userAttributePanel.clear();
													for (UserAttributeClient item : result) {
														ListBox attributeListBox = new ListBox();
														TextBox attributeValueTextBox = new TextBox();
														Button deleteRow = new Button(
																"X");
														for (UserAttributeTypeClient item2 : attributeList) {
															attributeListBox
																	.addItem(item2
																			.getAttributeName());
														}
														getExistingUserAttribute
																.add(item);
														attributeListBox
																.setSelectedIndex(attributeNames
																		.indexOf(item
																				.getUserAttributeType()
																				.getAttributeName()));
														attributeValueTextBox.setText(item
																.getAttributeValue());
														HorizontalPanel newPanel = new HorizontalPanel();
														deleteRow
																.setTitle(String
																		.valueOf(rowCount));
														newPanel.setSpacing(5);
														newPanel.add(attributeListBox);
														newPanel.add(attributeValueTextBox);
														newPanel.add(deleteRow);
														userAttributePanel
																.add(newPanel);
														rowCount++;
														deleteRow
																.addClickHandler(new ClickHandler() {

																	@Override
																	public void onClick(
																			ClickEvent event) {
																		Button button = (Button) event
																				.getSource();
																		for (int i = 0; i < userAttributePanel
																				.getWidgetCount(); i++) {
																			HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
																					.getWidget(i);
																			Button thisButton = (Button) horizontal
																					.getWidget(2);
																			if (thisButton
																					.getTitle()
																					.equals(button
																							.getTitle())) {
																				userAttributePanel
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
													Window.alert("Unable to fetch User Attributes");
												}
											});
							WEB_SERVICE.getCurrentUserRole(current,
									new AsyncCallback<List<RoleClient>>() {

										@Override
										public void onSuccess(
												List<RoleClient> result) {
											selectedRolesListBox.clear();
											for (RoleClient role : result) {
												selectedRolesListBox
														.addItem(role
																.getRoleName());
											}
										}

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Unable to fetch User Role");

										}
									});
							WEB_SERVICE.getCurrentUserLocation(current,
									new AsyncCallback<List<LocationClient>>() {

										@Override
										public void onSuccess(
												List<LocationClient> result) {
											selectedLocationsListBox.clear();
											for (LocationClient location : result) {
												selectedLocationsListBox.addItem(location
														.getLocationName());
											}
										}

										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Unable to fetch locations");
										}
									});

						} else {
							Window.alert("No User Found with ID:"
									+ userIdTextBox.getText());
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("No User found with Id: "
								+ userIdTextBox.getText());
					}
				});
	}

	public void clear() {
		userIdTextBox.setText("");
		reasonShowLabel.setVisible(false);
		usernameTextBox.setEnabled(true);
		fullNameTextBox.setEnabled(true);
		globalAccessCheckBox.setEnabled(true);
		secretQuestionListBox.setEnabled(true);
		answerTextBox.setEnabled(true);
		selectedRolesListBox.setEnabled(true);
		selectedLocationsListBox.setEnabled(true);
		rolesListBox.setEnabled(true);
		locationsListBox.setEnabled(true);
		addAllLocationsButton.setEnabled(true);
		addAllRolesButton.setEnabled(true);
		removeAllLocationsButton.setEnabled(true);
		removeAllRolesButton.setEnabled(true);
		addLocationsButton.setEnabled(true);
		addRolesButton.setEnabled(true);
		removeLocationsButton.setEnabled(true);
		removeRolesButton.setEnabled(true);
		locationFilterTextBox.setEnabled(true);
		clear(userFlexTable);
		selectedLocationsListBox.clear();
		selectedRolesListBox.clear();
		disableUserButton.setEnabled(true);
		enableUserButton.setEnabled(true);
		answerLabel.setVisible(true);
		answerTextBox.setVisible(true);
		pictureUpload.setEnabled(true);
		userAttributePanel.clear();
		passwordTextBox.setText("");
	}

	public void enableUser() {
		current.setDisabled(false);
		current.setReasonDisabled(null);
		WEB_SERVICE.updateUsersForDisable(current, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				Window.alert(current.getFullName()
						+ " has been Enabled successfully");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to Enable User");
			}
		});
	}

	public void filterUsers() {
		String searchString = null;
		if (!usernameFilterTextBox.getText().isEmpty()
				&& !fullNameFilterTextBox.getText().isEmpty()) {
			String userNameString = usernameFilterTextBox.getText();
			String fullNameString = fullNameFilterTextBox.getText();
			WEB_SERVICE.filterUserByBoth(filterOptionListBox
					.getItemText(filterOptionListBox.getSelectedIndex()),
					userNameString.toLowerCase(), fullNameString.toLowerCase(),
					new AsyncCallback<List<UsersClient>>() {

						@Override
						public void onSuccess(List<UsersClient> result) {
							users = result;
							dataCellTable.flush();
							if (users.size() > 0) {
								current = users.get(0);
								dataCellTable.setRowData(users);
							} else {
								Window.alert("No user found with Username : "
										+ usernameFilterTextBox.getText()
										+ " and FullName :"
										+ fullNameFilterTextBox.getText());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to filter users "
									+ caught.getMessage());
						}
					});
		} else if (!usernameFilterTextBox.getText().isEmpty()) {
			searchString = usernameFilterTextBox.getText();
			WEB_SERVICE.filterUserbyUserName(searchString.toLowerCase(),
					new AsyncCallback<List<UsersClient>>() {

						@Override
						public void onSuccess(List<UsersClient> result) {
							users = result;
							dataCellTable.flush();
							if (users.size() > 0) {
								current = users.get(0);
								dataCellTable.setRowData(users);
							} else {
								Window.alert("No user found with Username Name : "
										+ usernameFilterTextBox.getText());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to filter users "
									+ caught.getMessage());
						}
					});
		} else if (!fullNameFilterTextBox.getText().isEmpty()) {
			searchString = fullNameFilterTextBox.getText();
			WEB_SERVICE.filterUserbyFullName(searchString.toLowerCase(),
					new AsyncCallback<List<UsersClient>>() {

						@Override
						public void onSuccess(List<UsersClient> result) {
							users = result;
							dataCellTable.flush();
							if (users.size() > 0) {
								current = users.get(0);
								dataCellTable.setRowData(users);
							} else {
								Window.alert("No user found with Full Name : "
										+ fullNameFilterTextBox.getText());
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Unable to filter users "
									+ caught.getMessage());
						}
					});
		}

		else {
			fill();
		}

	}

	public void addAllRoles() {
		List<String> roleList = new ArrayList<String>();
		for (int i = 0; i < rolesListBox.getItemCount(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < selectedRolesListBox.getItemCount(); j++) {
				if (selectedRolesListBox.getItemText(j).equals(
						rolesListBox.getItemText(i))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				roleList.add(rolesListBox.getItemText(i));
			}
		}

		for (String item : roleList) {
			selectedRolesListBox.addItem(item);
		}
		if (isListEmpty(rolesListBox)) {
			addAllRolesButton.setEnabled(false);
			addRolesButton.setEnabled(false);
		}

		if (!isListEmpty(selectedRolesListBox)) {
			removeAllRolesButton.setEnabled(true);
			removeRolesButton.setEnabled(true);
		}
	}

	public void removeAllRoles() {
		List<String> roleList = new ArrayList<String>();
		for (int i = 0; i < selectedRolesListBox.getItemCount(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < rolesListBox.getItemCount(); j++) {
				if (rolesListBox.getItemText(j).equals(
						selectedRolesListBox.getItemText(i))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				roleList.add(selectedRolesListBox.getItemText(i));
			}
		}

		for (String item : roleList) {
			rolesListBox.addItem(item);
		}
		selectedRolesListBox.clear();

		if (isListEmpty(selectedRolesListBox)) {
			removeAllRolesButton.setEnabled(false);
			removeRolesButton.setEnabled(false);
		}

		if (!isListEmpty(rolesListBox)) {
			addAllRolesButton.setEnabled(true);
			addRolesButton.setEnabled(true);
		}
	}

	public void addRole() {
		ArrayList<Integer> list = getSelectedIndices(rolesListBox);
		List<String> roleList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < selectedRolesListBox.getItemCount(); j++) {
				if (selectedRolesListBox.getItemText(j).equals(
						rolesListBox.getItemText(list.get(i)))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				roleList.add(rolesListBox.getItemText(list.get(i)));
			}
		}

		for (String item : roleList) {
			selectedRolesListBox.addItem(item);
		}

		if (isListEmpty(rolesListBox)) {
			addAllRolesButton.setEnabled(false);
			addRolesButton.setEnabled(false);
		}

		if (!isListEmpty(selectedRolesListBox)) {
			removeAllRolesButton.setEnabled(true);
			removeRolesButton.setEnabled(true);
		}
	}

	public void removeRole() {
		ArrayList<String> list = getSelectedIndicesNames(selectedRolesListBox);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < selectedRolesListBox.getItemCount(); j++) {
				if (selectedRolesListBox.getItemText(j).equals(list.get(i))) {
					selectedRolesListBox.removeItem(j);

				}
			}
		}
		if (isListEmpty(selectedRolesListBox)) {
			removeAllRolesButton.setEnabled(false);
			removeRolesButton.setEnabled(false);
		}

		if (!isListEmpty(rolesListBox)) {
			addAllRolesButton.setEnabled(true);
			addRolesButton.setEnabled(true);
		}
	}

	public void addAllLocations() {
		List<String> locationList = new ArrayList<String>();
		for (int i = 0; i < locationsListBox.getItemCount(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < selectedLocationsListBox.getItemCount(); j++) {
				if (selectedLocationsListBox.getItemText(j).equals(
						locationsListBox.getItemText(i))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				locationList.add(locationsListBox.getItemText(i));
			}
		}

		for (String item : locationList) {
			selectedLocationsListBox.addItem(item);
		}
		if (isListEmpty(locationsListBox)) {
			addAllLocationsButton.setEnabled(false);
			addLocationsButton.setEnabled(false);
		}

		if (!isListEmpty(selectedLocationsListBox)) {
			removeAllLocationsButton.setEnabled(true);
			removeLocationsButton.setEnabled(true);
		}
	}

	public void addLocation() {
		ArrayList<Integer> list = getSelectedIndices(locationsListBox);
		List<String> locationList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < selectedLocationsListBox.getItemCount(); j++) {
				if (selectedLocationsListBox.getItemText(j).equals(
						locationsListBox.getItemText(list.get(i)))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				locationList.add(locationsListBox.getItemText(list.get(i)));
			}
		}

		for (String item : locationList) {
			selectedLocationsListBox.addItem(item);
		}
		if (isListEmpty(locationsListBox)) {
			addAllLocationsButton.setEnabled(false);
			addLocationsButton.setEnabled(false);
		}

		if (!isListEmpty(selectedLocationsListBox)) {
			removeAllLocationsButton.setEnabled(true);
			removeLocationsButton.setEnabled(true);
		}
	}

	public void removeAllLocations() {
		List<String> locationsList = new ArrayList<String>();
		for (int i = 0; i < selectedLocationsListBox.getItemCount(); i++) {
			boolean listBoxcheck = false;
			for (int j = 0; j < locationsListBox.getItemCount(); j++) {
				if (locationsListBox.getItemText(j).equals(
						selectedLocationsListBox.getItemText(i))) {
					listBoxcheck = true;
				}
			}
			if (listBoxcheck == false) {
				locationsList.add(selectedLocationsListBox.getItemText(i));
			}
		}
		for (String item : locationsList) {
			locationsListBox.addItem(item);
		}
		selectedLocationsListBox.clear();

		if (isListEmpty(selectedLocationsListBox)) {
			removeAllLocationsButton.setEnabled(false);
			removeLocationsButton.setEnabled(false);
		}

		if (!isListEmpty(locationsListBox)) {
			addAllLocationsButton.setEnabled(true);
			addLocationsButton.setEnabled(true);
		}
	}

	public void removeLocation() {
		ArrayList<String> list = getSelectedIndicesNames(selectedLocationsListBox);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < selectedLocationsListBox.getItemCount(); j++) {
				if (selectedLocationsListBox.getItemText(j).equals(list.get(i))) {
					selectedLocationsListBox.removeItem(j);
				}
			}
		}
		if (isListEmpty(selectedLocationsListBox)) {
			removeAllLocationsButton.setEnabled(false);
			removeLocationsButton.setEnabled(false);
		}

		if (!isListEmpty(locationsListBox)) {
			addAllLocationsButton.setEnabled(true);
			addLocationsButton.setEnabled(true);
		}
	}

	public void disableUser() {
		current.setDisabled(true);
		current.setReasonDisabled(reasonTextBox.getText());
		WEB_SERVICE.updateUsersForDisable(current, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				Window.alert("User has been Disabled successfully");
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to Disable User");
			}
		});
		disableDialog.hide();
	}

	public static <T> boolean hasDuplicates(List<T> list) {
		int count = list.size();
		T t1, t2;

		for (int i = 0; i < count; i++) {
			t1 = list.get(i);
			for (int j = i + 1; j < count; j++) {
				t2 = list.get(j);
				if (t2.equals(t1)) {
					return true;
				}
			}
		}
		return false;
	}

	public void disableControllers() {
		usernameTextBox.setEnabled(false);
		fullNameTextBox.setEnabled(false);
		globalAccessCheckBox.setEnabled(false);
		secretQuestionListBox.setEnabled(false);
		answerTextBox.setEnabled(false);
		selectedRolesListBox.setEnabled(false);
		selectedLocationsListBox.setEnabled(false);
		rolesListBox.setEnabled(false);
		locationsListBox.setEnabled(false);
		addAllLocationsButton.setEnabled(false);
		addAllRolesButton.setEnabled(false);
		removeAllLocationsButton.setEnabled(false);
		removeAllRolesButton.setEnabled(false);
		addLocationsButton.setEnabled(false);
		addRolesButton.setEnabled(false);
		removeLocationsButton.setEnabled(false);
		removeRolesButton.setEnabled(false);
		locationFilterTextBox.setEnabled(false);
		pictureUpload.setEnabled(false);
		addUserAttributeButton.setEnabled(false);
	}

	public void enableControllers() {
		usernameTextBox.setEnabled(true);
		fullNameTextBox.setEnabled(true);
		globalAccessCheckBox.setEnabled(true);
		secretQuestionListBox.setEnabled(true);
		answerTextBox.setEnabled(true);
		selectedRolesListBox.setEnabled(true);
		selectedLocationsListBox.setEnabled(true);
		rolesListBox.setEnabled(true);
		locationsListBox.setEnabled(true);
		addAllLocationsButton.setEnabled(true);
		addAllRolesButton.setEnabled(true);
		removeAllLocationsButton.setEnabled(true);
		removeAllRolesButton.setEnabled(true);
		addLocationsButton.setEnabled(true);
		addRolesButton.setEnabled(true);
		removeLocationsButton.setEnabled(true);
		removeRolesButton.setEnabled(true);
		locationFilterTextBox.setEnabled(true);
		pictureUpload.setEnabled(true);
		addUserAttributeButton.setEnabled(true);
	}

	public boolean checkDuplicateUserAttribute() {
		List<String> attributesList = new ArrayList<String>();
		for (int i = 0; i < userAttributePanel.getWidgetCount(); i++) {
			HorizontalPanel horizontal = (HorizontalPanel) userAttributePanel
					.getWidget(i);
			ListBox thisListBox = (ListBox) horizontal.getWidget(0);
			if (attributesList.contains(thisListBox.getItemText(thisListBox
					.getSelectedIndex()))) {
				return false;
			} else {
				attributesList.add(thisListBox.getItemText(thisListBox
						.getSelectedIndex()));
			}
		}
		return true;
	}

}
