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

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.gfaicweb.shared.model.LocationClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.UsersClient;

/**
 * @author owais.hussain@ihsinformatics.com
 * 
 */
public class LoginComposite extends AbstractComposite {
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	DialogBox sessionRenewDialogBox = new DialogBox();

	HorizontalPanel buttonsPanel = new HorizontalPanel();
	HorizontalPanel dialogButtonPanel = new HorizontalPanel();

	VerticalPanel dialogVerticalPanel = new VerticalPanel();

	FlexTable loginFlexTable = new FlexTable();

	Label loginLabel = new Label("Login");
	Label usernameLabel = new Label("Username:");
	Label passwordLabel = new Label("Password:");
	Label renewPassword = new Label("Please enter first 4 digits of password");

	TextBox usernameTextBox = new TextBox();

	PasswordTextBox passwordTextBox = new PasswordTextBox();
	PasswordTextBox sessionRestorePassword = new PasswordTextBox();

	CheckBox locationCheckBox = new CheckBox("Location");

	ListBox locationComboBox = new ListBox();

	Button loginButton = new Button("Login");
	Button clearButton = new Button("Clear");
	Button recoverPasswordButton = new Button("Recover Password");
	Button restoreSession = new Button("Restore Session");
	Button logoutButton = new Button("Logout");

	boolean sessionCheck;

	public LoginComposite() {
		initWidget(loginFlexTable);
		loginFlexTable.setSize("", "100%");
		loginFlexTable.setWidget(0, 1, loginLabel);
		loginFlexTable.getCellFormatter().setStylePrimaryName(0, 1, "title");
		usernameLabel.setStyleName("label");
		loginFlexTable.setWidget(1, 0, usernameLabel);
		usernameTextBox.setStyleName("textbox");
		usernameTextBox.setMaxLength(50);
		usernameTextBox.setVisibleLength(30);
		loginFlexTable.setWidget(1, 1, usernameTextBox);
		passwordLabel.setStyleName("label");
		loginFlexTable.setWidget(2, 0, passwordLabel);
		passwordTextBox.setStyleName("textbox");
		passwordTextBox.setMaxLength(50);
		passwordTextBox.setVisibleLength(30);
		loginFlexTable.setWidget(2, 1, passwordTextBox);
		locationCheckBox.setStyleName("checkbox");
		loginFlexTable.setWidget(3, 0, locationCheckBox);
		locationComboBox.setName("location.location_name");
		locationComboBox.setStylePrimaryName("list");
		locationComboBox.addItem("IHS");
		locationComboBox.addItem("IRD");
		locationComboBox.setStyleName("list");
		loginFlexTable.setWidget(3, 1, locationComboBox);
		loginFlexTable.getCellFormatter()
				.setStylePrimaryName(3, 1, "list-cell");
		loginFlexTable.setWidget(4, 1, buttonsPanel);
		loginButton.setStyleName("submit-button");
		buttonsPanel.add(loginButton);
		loginButton.setSize("96px", "48px");
		clearButton.setStyleName("submit-button");
		buttonsPanel.add(clearButton);
		clearButton.setSize("96px", "48px");
		recoverPasswordButton.setStyleName("operation-button");
		buttonsPanel.add(recoverPasswordButton);
		recoverPasswordButton.setSize("96px", "");
		dialogButtonPanel.add(restoreSession);
		dialogButtonPanel.add(logoutButton);
		sessionRenewDialogBox.setText("Renew Session");
		dialogVerticalPanel.add(renewPassword);
		dialogVerticalPanel.add(sessionRestorePassword);
		dialogVerticalPanel.add(dialogButtonPanel);
		sessionRenewDialogBox.add(dialogVerticalPanel);
		init();
	}

	public static void setCookies(String userName, String userId,
			String location, String passCode) {
		Cookies.removeCookie("username");
		Cookies.removeCookie("user_id");
		Cookies.removeCookie("location");
		Cookies.removeCookie("passcode");
		Cookies.removeCookie("login_time");
		Cookies.removeCookie("session_limit");
		Cookies.removeCookie("current_form");
		if (!userName.equals(""))
			Cookies.setCookie("username", TBR.getCurrentUser().getUsername());
		if (!userId.equals(""))
			Cookies.setCookie("user_id", TBR.getCurrentUser().getUserId()
					.toString());
		if (!location.equals(""))
			Cookies.setCookie("location", TBR.getCurrentLocation()
					.getLocationName());
		if (!passCode.equals("")) {
			Cookies.setCookie("Pass", TBR.getPassCode());
			Cookies.setCookie("login_time",
					String.valueOf(new Date().getTime()));
			Cookies.setCookie("session_limit", String.valueOf(new Date()
					.getTime() + TBR.getSessionLimit()));
		}
	}

	public boolean renewSession() {
		sessionRenewDialogBox.setPopupPosition(600, 350);
		sessionRenewDialogBox.show();
		restoreSession.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (sessionRestorePassword.equals(TBR.getPassCode())) {
					sessionCheck = true;
				} else {
					sessionCheck = false;
				}
			}
		});
		return sessionCheck;
	}

	public void login() {
		String userName;
		String userId;
		String location;
		String passCode;
		String sessionLimit;
		// try
		// {
		// Try to get Cookies
		userName = Cookies.getCookie("username");
		userId = Cookies.getCookie("user_id");
		passCode = Cookies.getCookie("passcode");
		location = Cookies.getCookie("location");
		if (location == null)
			location = "";
		sessionLimit = Cookies.getCookie("session_limit");
		// if (userName == null || passCode == null || sessionLimit == null)
		// throw new Exception();
		// usernameTextBox.setText(userName);
		// if (new Date().getTime() > Long.parseLong(sessionLimit))
		// if (!renewSession())
		// throw new Exception();
		// setCookies(userName, userId, location, passCode);

		if (locationCheckBox.getValue()) {
			webService.login(usernameTextBox.getText(), passwordTextBox
					.getText(), locationComboBox.getItemText(locationComboBox
					.getSelectedIndex()), new AsyncCallback<Boolean>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("ERROR!" + "\n" + caught.getMessage());
				}

				@Override
				public void onSuccess(Boolean result) {
					if (result == true) {
						// Get all user privileges
						String passCode = passwordTextBox.getText()
								.substring(
										0,
										Math.min(passwordTextBox.getText()
												.length(), 4));
						TBR.setPassCode(passCode);
						webService
								.getCurrentUserClient(new AsyncCallback<UsersClient>() {
									@Override
									public void onFailure(Throwable caught) {
										Window.alert("ERROR" + "\n"
												+ caught.getMessage());
									}

									@Override
									public void onSuccess(UsersClient result) {
										TBR.setCurrentUser(result);
										webService
												.getCurrentLocation(new AsyncCallback<LocationClient>() {

													@Override
													public void onSuccess(
															LocationClient result) {
														TBR.setCurrentLocation(result);
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Unable to fetch Current User Location");
													}
												});
										webService
												.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

													@Override
													public void onSuccess(
															List<PrivilegeClient> result) {
														TBR.setCurrentUserPrivileges(result);
														Gfaicweb
																.setComposite(new MasterComposite());
													}

													@Override
													public void onFailure(
															Throwable caught) {
														Window.alert("Login Failed!"
																+ "\n"
																+ "Enter Valid Username & Password");
													}
												});
									}

								});
					} else {
						Window.alert("Invalid Username or Password");
					}

				}

			});

		} else {
			webService.login(usernameTextBox.getText(),
					passwordTextBox.getText(), null,
					new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("ERROR!" + "\n" + caught.getMessage());
						}

						@Override
						public void onSuccess(Boolean result) {
							if (result == true) {
								// Get all user privileges
								String passCode = passwordTextBox
										.getText()
										.substring(
												0,
												Math.min(passwordTextBox
														.getText().length(), 4));
								TBR.setPassCode(passCode);
								webService
										.getCurrentUserClient(new AsyncCallback<UsersClient>() {
											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("ERROR" + "\n"
														+ caught.getMessage());
											}

											@Override
											public void onSuccess(
													UsersClient result) {
												TBR.setCurrentUser(result);
												webService
														.getCurrentLocation(new AsyncCallback<LocationClient>() {

															@Override
															public void onSuccess(
																	LocationClient result) {
																TBR.setCurrentLocation(result);
															}

															@Override
															public void onFailure(
																	Throwable caught) {
																Window.alert("Unable to fetch Current User Location");
															}
														});
												webService
														.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

															@Override
															public void onSuccess(
																	List<PrivilegeClient> result) {
																TBR.setCurrentUserPrivileges(result);
																Gfaicweb
																		.setComposite(new MasterComposite());
															}

															@Override
															public void onFailure(
																	Throwable caught) {
																Window.alert("Login Failed!"
																		+ "\n"
																		+ "Enter Valid Username & Password");
															}
														});
											}

										});
							} else {
								Window.alert("Invalid Username or Password");
							}

						}

					});
		}
		// }
		// catch(Exception e)
		// {
		// loginFlexTable.setVisible(true);
		// }
		//
	}

	public void recover() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.gfaicweb.client.AbstractComposite#init()
	 */
	@Override
	public void init() {
		// Add event handlers
		loginButton.addClickHandler(this);
		clearButton.addClickHandler(this);
		recoverPasswordButton.addClickHandler(this);
		locationCheckBox
				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						locationComboBox.setEnabled(locationCheckBox.getValue());
					}
				});
		setPrivileges();
		fill();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.gfaicweb.client.AbstractComposite#fill()
	 */
	@Override
	public void fill() {
		// TODO fill lists/combo boxes
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event
	 * .dom.client.ClickEvent)
	 */
	@Override
	public void onClick(ClickEvent event) {
		Widget widget = (Widget) event.getSource();
		if (widget == loginButton) {
			login();
		} else if (widget == clearButton) {
			clear(loginFlexTable);
		} else if (widget == recoverPasswordButton) {
			recover();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.gfaicweb.client.AbstractComposite#validate()
	 */
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ihsinformatics.gfaicweb.client.AbstractComposite#setPrivileges
	 * ()
	 */
	@Override
	public void setPrivileges() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.gfaicweb.client.AbstractComposite#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.gfaicweb.client.AbstractComposite#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ihsinformatics.gfaicweb.client.AbstractComposite#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
	}
}
