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
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.ihsinformatics.gfaicweb.shared.FormType;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;

/**
 * @author owais.hussain@ihsinformatics.com
 * 
 */
public class MasterComposite extends Composite {

	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	private DockPanel masterDockPanel = new DockPanel();
	private FlexTable topFlexTable = new FlexTable();
	private FlexTable headerFlexTable = new FlexTable();
	private VerticalPanel menuVerticalPanel = new VerticalPanel();
	private HorizontalPanel titleHorizontalPanel = new HorizontalPanel();
	private VerticalPanel verticalPanel = new VerticalPanel();
	private static SimplePanel compositePanel = new SimplePanel();

	Label titleLabel = new Label("Global Fund - Airborne Infection Control");

	public MasterComposite() {
		initWidget(masterDockPanel);
		masterDockPanel.setSize("900px", "550px");
		masterDockPanel.add(topFlexTable, DockPanel.NORTH);
		masterDockPanel.setCellHorizontalAlignment(topFlexTable,
				HasHorizontalAlignment.ALIGN_CENTER);
		topFlexTable.setHeight("");
		topFlexTable.setWidget(0, 0, headerFlexTable);
		headerFlexTable.setSize("", "");
		Image logoImage = new Image("WEB-INF/classes/ihsLogo.png");
		headerFlexTable.setWidget(0, 0, logoImage);
		headerFlexTable.setWidget(0, 1, menuVerticalPanel);
		menuVerticalPanel.setWidth("100%");
		menuVerticalPanel.add(titleHorizontalPanel);
		verticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		titleHorizontalPanel.add(verticalPanel);
		titleHorizontalPanel.setCellHorizontalAlignment(verticalPanel,
				HasHorizontalAlignment.ALIGN_CENTER);
		verticalPanel.setWidth("770px");
		titleLabel.setStyleName("heading");
		verticalPanel.add(titleLabel);
		verticalPanel.setCellHorizontalAlignment(titleLabel,
				HasHorizontalAlignment.ALIGN_CENTER);

		Label descriptionLabel = new Label(
				"Web interface for Global Fund - Airborne Infection Control. Powered by GWT");
		descriptionLabel.setStyleName("sub-label");
		verticalPanel.add(descriptionLabel);

		webService
				.getCurrentUserPrivileges(new AsyncCallback<List<PrivilegeClient>>() {

					@Override
					public void onSuccess(List<PrivilegeClient> result) {
						MenuBar mainMenuBar = new MenuBar(false);
						menuVerticalPanel.add(mainMenuBar);
						MenuBar setupMenuBar = new MenuBar(true);
						MenuBar formsMenuBar = new MenuBar(true);
						MenuBar reportingMenuBar = new MenuBar(true);
						MenuBar advancedMenuBar = new MenuBar(true);
						MenuBar helpMenuBar = new MenuBar(true);

						MenuItem setupMenuItem = new MenuItem("Setup", false,
								setupMenuBar);
						MenuItem formsMenuItem = new MenuItem("Forms", false,
								formsMenuBar);
						MenuItem reportingMenuItem = new MenuItem("Reporting",
								false, reportingMenuBar);
						MenuItem advancedMenuItem = new MenuItem("Advanced",
								false, advancedMenuBar);
						MenuItem helpMenuItem = new MenuItem("Help", false,
								helpMenuBar);

						MenuItem definitionsMenuItem = new MenuItem(
								"Definitions", false, (Command) null);

						MenuItem definitionsTypeMenuItem = new MenuItem(
								"Definition Types", false, (Command) null);

						MenuItem locationsMenuItem = new MenuItem("Locations",
								false, (Command) null);

						MenuItem userFormItem = new MenuItem("User Form",
								false, (Command) null);
						MenuItem usersMenuItem = new MenuItem("Users", false,
								(Command) null);
						MenuItem userFormTypeMenuItem = new MenuItem(
								"User Form Type", false, (Command) null);
						MenuItem userAttributeTypeMenuItem = new MenuItem(
								"User Attribute Type", false, (Command) null);
						MenuItem encounterTypeMenuItem = new MenuItem(
								"Encounter Types", false, (Command) null);
						MenuItem elementMenuItem = new MenuItem("Elements",
								false, (Command) null);
						MenuItem encountersMenuItem = new MenuItem(
								"Encounters", false, (Command) null);

						MenuItem reportsMenuItem = new MenuItem("Reports",
								false, (Command) null);
						MenuItem syncMenuItem = new MenuItem("Synchronization",
								false, (Command) null);
						MenuItem aboutMeMenuItem = new MenuItem("About Me",
								false, (Command) null);
						MenuItem helpContentsMenuItem = new MenuItem(
								"Help Contents", false, (Command) null);
						MenuItem feedbackMenuItem = new MenuItem("Feedback",
								false, (Command) null);

						MenuItem roleMenuItem = new MenuItem("Role", false,
								(Command) null);

						MenuItem privilegeMenuItem = new MenuItem("Privilege",
								false, (Command) null);

						MenuItem locationAttributeTypeMenuItem = new MenuItem(
								"Location Attribute Type", false,
								(Command) null);

						MenuItem personAttributeTypeMenuItem = new MenuItem(
								"Person Attribute Type", false, (Command) null);

						mainMenuBar.setStyleName("main-menu");
						mainMenuBar.setSize("750px", "");
						mainMenuBar.setAutoOpen(true);
						mainMenuBar.setAnimationEnabled(true);
						setupMenuBar.setStyleName("sub-menu");
						setupMenuBar.setAutoOpen(true);
						setupMenuBar.setAnimationEnabled(true);
						formsMenuBar.setStyleName("sub-menu");
						formsMenuBar.setAutoOpen(true);
						formsMenuBar.setAnimationEnabled(true);
						reportingMenuBar.setStyleName("sub-menu");
						reportingMenuBar.setAutoOpen(true);
						reportingMenuBar.setAnimationEnabled(true);
						advancedMenuBar.setStyleName("sub-menu");
						advancedMenuBar.setAutoOpen(true);
						advancedMenuBar.setAnimationEnabled(true);
						helpMenuBar.setStyleName("sub-menu");
						helpMenuBar.setAutoOpen(true);
						helpMenuBar.setAnimationEnabled(true);

						definitionsMenuItem.setStyleName("menu-button");
						definitionsMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.DEFINITION);
							}
						});

						definitionsTypeMenuItem.setStyleName("menu-button");
						definitionsTypeMenuItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.DEFINITION_TYPE);
								setCompositePanelWidget(new DefinitionTypeComposite()
										.asWidget());

							}
						});
						locationsMenuItem.setStyleName("menu-button");
						locationsMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.LOCATION);
								setCompositePanelWidget(new LocationComposite()
										.asWidget());
							}
						});
						usersMenuItem.setStyleName("menu-button");
						usersMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu", FormType.USER);
								setCompositePanelWidget(new UsersComposite()
										.asWidget());
							}
						});

						userFormItem.setStyleName("menu-button");
						userFormItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.USER_FORM);
								setCompositePanelWidget(new UserFormComposite()
										.asWidget());
							}
						});
						userFormTypeMenuItem.setStyleName("menu-button");
						userFormTypeMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.USER_FORM_TYPE);
								setCompositePanelWidget(new UserFormTypeComposite()
										.asWidget());
							}
						});

						userAttributeTypeMenuItem.setStyleName("menu-button");
						userAttributeTypeMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.USER_ATTRIBUTE_TYPE);
								setCompositePanelWidget(new UserAttributeTypeComposite()
										.asWidget());
							}
						});
						encounterTypeMenuItem.setStyleName("menu-button");
						encounterTypeMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.ENCOUNTER_TYPE);
								setCompositePanelWidget(new EncounterTypeComposite()
										.asWidget());
							}
						});
						elementMenuItem.setStyleName("menu-button");
						elementMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.ELEMENT);
								setCompositePanelWidget(new ElementComposite()
										.asWidget());
							}
						});

						roleMenuItem.setStyleName("menu-button");
						roleMenuItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu", FormType.ROLE);
								setCompositePanelWidget(new RoleComposite()
										.asWidget());
							}
						});

						privilegeMenuItem.setStyleName("menu-button");
						privilegeMenuItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.PRIVILEGE);
								setCompositePanelWidget(new PrivilegeComposite()
										.asWidget());
							}
						});

						locationAttributeTypeMenuItem
								.setStyleName("menu-button");
						locationAttributeTypeMenuItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.LOCATION_ATTRIBUTE_TYPE);
								setCompositePanelWidget(new LocationAttributeTypeComposite()
										.asWidget());
							}
						});
						personAttributeTypeMenuItem.setStyleName("menu-button");
						personAttributeTypeMenuItem.setCommand(new Command() {

							@Override
							public void execute() {
								Cookies.setCookie("CurrentMenu",
										FormType.PERSON_ATTRIBUTE_TYPE);
								setCompositePanelWidget(new PersonAttributeTypeComposite()
										.asWidget());
							}
						});
						encountersMenuItem.setStyleName("menu-button");
						encountersMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu", "ENCOUNTER");
								setCompositePanelWidget(new EncounterComposite()
										.asWidget());
							}
						});
						reportsMenuItem.setStyleName("menu-button");
						reportsMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu", "DATALOG");
							}
						});
						syncMenuItem.setStyleName("menu-button");
						syncMenuItem.setCommand(new Command() {
							public void execute() {
								Cookies.setCookie("CurrentMenu", "DATALOG");
							}
						});
						aboutMeMenuItem.setStyleName("menu-button");
						aboutMeMenuItem.setCommand(new Command() {
							@SuppressWarnings("deprecation")
							public void execute() {
								try {
									String user = Cookies.getCookie("UserName");
									Date loginDate = new Date(Long
											.parseLong(Cookies
													.getCookie("LoginTime")));
									int mins = new Date(new Date().getTime()
											- loginDate.getTime()).getMinutes();
									String str = "CURRENT USER: "
											+ user
											+ "\n"
											+ "LOGIN TIME: "
											+ loginDate.toGMTString().replace(
													"GMT", "") + "\n"
											+ "CURRENT SESSION: " + mins
											+ " mins";
									Window.alert(str);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						feedbackMenuItem.setStyleName("menu-button");
						feedbackMenuItem.setCommand(new Command() {

							public void execute() {
								Cookies.setCookie("CurrentMenu", "FEEDBACK");
							}
						});
						helpContentsMenuItem.setStyleName("menu-button");
						helpContentsMenuItem.setCommand(new Command() {

							public void execute() {
								Cookies.setCookie("CurrentMenu", "HELP");
								Window.alert("Unavailable...");
							}
						});
						setupMenuItem.setStyleName("sub-menu");

						mainMenuBar.addItem(setupMenuItem);
						formsMenuItem.setStyleName("sub-menu");
						mainMenuBar.addItem(formsMenuItem);
						reportingMenuItem.setStyleName("sub-menu");
						mainMenuBar.addItem(reportingMenuItem);
						advancedMenuItem.setStyleName("sub-menu");
						mainMenuBar.addItem(advancedMenuItem);
						helpMenuItem.setStyleName("sub-menu");
						mainMenuBar.addItem(helpMenuItem);

						if (result.toString().contains("VIEW DEFINITION_TYPE")) {
							setupMenuBar.addItem(definitionsMenuItem);
						}
						if (result.toString().contains("VIEW ELEMENT")) {
							setupMenuBar.addItem(elementMenuItem);
						}
						if (result.toString().contains("VIEW ENCOUNTER")) {
							setupMenuBar.addItem(encountersMenuItem);
						}
						if (result.toString().contains("VIEW LOCATION")) {
							setupMenuBar.addItem(locationsMenuItem);
						}
						if (result.toString().contains("VIEW USERS")) {
							setupMenuBar.addItem(usersMenuItem);
						}
						if (result.toString().contains("VIEW USER_FORM")) {
							setupMenuBar.addItem(userFormItem);
						}

						if (result.toString().contains("VIEW USER_FORM_TYPE")) {
							formsMenuBar.addItem(userFormTypeMenuItem);
						}
						if (result.toString().contains(
								"VIEW LOCATION_ATTRIBUTE_TYPE")) {
							formsMenuBar.addItem(locationAttributeTypeMenuItem);
						}
						if (result.toString().contains("VIEW ENCOUNTER_TYPE")) {
							formsMenuBar.addItem(encounterTypeMenuItem);
						}
						if (result.toString().contains("VIEW DEFINITION_TYPE")) {
							formsMenuBar.addItem(definitionsTypeMenuItem);
						}
						if (result.toString().contains("VIEW ROLE")) {
							formsMenuBar.addItem(roleMenuItem);
						}
						if (result.toString().contains("VIEW PRIVILEGE")) {
							formsMenuBar.addItem(privilegeMenuItem);
						}
						if (result.toString().contains(
								"VIEW PERSON_ATTRIBUTE_TYPE")) {
							formsMenuBar.addItem(personAttributeTypeMenuItem);
						}
						if (result.toString().contains(
								"VIEW USER_ATTRIBUTE_TYPE")) {
							formsMenuBar.addItem(userAttributeTypeMenuItem);
						}

						reportingMenuBar.addItem(reportsMenuItem);

						advancedMenuBar.addItem(syncMenuItem);

						helpMenuBar.addItem(aboutMeMenuItem);
						helpMenuBar.addItem(feedbackMenuItem);
						helpMenuBar.addItem(helpContentsMenuItem);

					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});

		VerticalPanel accountVerticalPanel = new VerticalPanel();
		accountVerticalPanel.setStyleName("account");
		headerFlexTable.setWidget(0, 2, accountVerticalPanel);
		accountVerticalPanel.setSize("160px", "100px");

		Image image_1 = new Image("WEB-INF/classes/male.png");
		accountVerticalPanel.add(image_1);
		accountVerticalPanel.setCellHorizontalAlignment(image_1,
				HasHorizontalAlignment.ALIGN_CENTER);
		Hyperlink userHpr = new Hyperlink("Owais Hussain", false,
				"newHistoryToken");
		userHpr.setStyleName("link");

		accountVerticalPanel.add(userHpr);
		Label lblIhs = new Label("Interactive Health Sol...");
		lblIhs.setStyleName("sub-label");
		accountVerticalPanel.add(lblIhs);
		headerFlexTable.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		headerFlexTable.getCellFormatter().setVerticalAlignment(0, 2,
				HasVerticalAlignment.ALIGN_TOP);
		headerFlexTable.getCellFormatter().setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
		topFlexTable.getCellFormatter().setVerticalAlignment(0, 0,
				HasVerticalAlignment.ALIGN_TOP);
		topFlexTable.getCellFormatter().setHorizontalAlignment(0, 0,
				HasHorizontalAlignment.ALIGN_CENTER);

		masterDockPanel.add(getCompositePanel(), DockPanel.CENTER);
		getCompositePanel().setSize("100%", "70%");

		ScrollPanel centerScrollPanel = new ScrollPanel();
		getCompositePanel().setWidget(centerScrollPanel);
		centerScrollPanel.setSize("100%", "100%");

		FlexTable bottomFlexTable = new FlexTable();
		masterDockPanel.add(bottomFlexTable, DockPanel.SOUTH);
		bottomFlexTable.setSize("100%", "10%");
		masterDockPanel.setCellVerticalAlignment(bottomFlexTable,
				HasVerticalAlignment.ALIGN_BOTTOM);

		VerticalPanel footerVerticalPanel = new VerticalPanel();
		footerVerticalPanel
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		bottomFlexTable.setWidget(0, 0, footerVerticalPanel);
		footerVerticalPanel.setWidth("100%");

		HorizontalPanel linksHorizontalPanel = new HorizontalPanel();
		footerVerticalPanel.add(linksHorizontalPanel);

		Label footerLabel = new Label(
				"Copyrights: Interactive Health Solutions Pvt. Ltd. Pakistan, under GPL v3 License");
		linksHorizontalPanel.add(footerLabel);
		footerLabel.setStyleName("footer");

		Hyperlink licenseHyperlink = new Hyperlink("License", false,
				"newHistoryToken");
		licenseHyperlink.setStyleName("footer-links");
		linksHorizontalPanel.add(licenseHyperlink);

		Hyperlink contributorsHyperlink = new Hyperlink("Contributions", false,
				"newHistoryToken");
		contributorsHyperlink.setStyleName("footer-links");
		linksHorizontalPanel.add(contributorsHyperlink);

		Hyperlink languageHyperlink = new Hyperlink("English", false,
				"newHistoryToken");
		languageHyperlink.setStyleName("footer-links");
		linksHorizontalPanel.add(languageHyperlink);

		Hyperlink aboutHyperlink = new Hyperlink("About Us", false,
				"newHistoryToken");
		aboutHyperlink.setStyleName("footer-links");
		linksHorizontalPanel.add(aboutHyperlink);
	}

	public static SimplePanel getCompositePanel() {
		return compositePanel;
	}

	public static void setCompositePanelWidget(Widget widget) {
		if (widget == null) {
			MasterComposite.compositePanel.clear();
		} else {
			MasterComposite.compositePanel.setWidget(widget);
		}
	}

}
