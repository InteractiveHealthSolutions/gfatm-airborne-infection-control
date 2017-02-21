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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.ihsinformatics.gfaicweb.shared.FormType;

/**
 * @author babar.anis@ihsinformatics.com
 */
public class MainMenuComposite extends Composite {

	public MainMenuComposite() {
		MenuBar mainMenuBar = new MenuBar(false);
		MenuBar setupMenuBar = new MenuBar(true);
		MenuBar formsMenuBar = new MenuBar(true);
		MenuBar reportingMenuBar = new MenuBar(true);
		MenuBar advancedMenuBar = new MenuBar(true);
		MenuBar helpMenuBar = new MenuBar(true);

		MenuItem setupMenuItem = new MenuItem("Setup", false, setupMenuBar);
		MenuItem formsMenuItem = new MenuItem("Forms", false, formsMenuBar);
		MenuItem reportingMenuItem = new MenuItem("Reporting", false,
				reportingMenuBar);
		MenuItem advancedMenuItem = new MenuItem("Advanced", false,
				advancedMenuBar);
		MenuItem helpMenuItem = new MenuItem("Help", false, helpMenuBar);

		MenuItem definitionsMenuItem = new MenuItem("Definitions", false,
				(Command) null);

		MenuItem definitionsTypeMenuItem = new MenuItem("Definition Types",
				false, (Command) null);

		MenuItem locationsMenuItem = new MenuItem("Locations", false,
				(Command) null);
		MenuItem usersMenuItem = new MenuItem("Users", false, (Command) null);

		MenuItem userFormMenuItem = new MenuItem("User Form", false,
				(Command) null);

		MenuItem userAttributeTypeMenuItem = new MenuItem(
				"User Attribute Type", false, (Command) null);

		MenuItem userFormTypeMenuItem = new MenuItem("User Form Type", false,
				(Command) null);

		MenuItem encounterTypeMenuItem = new MenuItem("Encounter Types", false,
				(Command) null);
		MenuItem elementMenuItem = new MenuItem("Elements", false,
				(Command) null);
		MenuItem encountersMenuItem = new MenuItem("Encounters", false,
				(Command) null);
		MenuItem reportsMenuItem = new MenuItem("Reports", false,
				(Command) null);
		MenuItem syncMenuItem = new MenuItem("Synchronization", false,
				(Command) null);
		MenuItem aboutMenuItem = new MenuItem("About Us", false, (Command) null);
		MenuItem aboutMeMenuItem = new MenuItem("About Me", false,
				(Command) null);
		MenuItem helpContentsMenuItem = new MenuItem("Help Contents", false,
				(Command) null);
		MenuItem feedbackMenuItem = new MenuItem("Feedback", false,
				(Command) null);
		MenuItem roleMenuItem = new MenuItem("Role", false, (Command) null);

		MenuItem privilegeMenuItem = new MenuItem("Privilege", false,
				(Command) null);

		MenuItem personAttributeTypeMenuItem = new MenuItem(
				"Person Attribute Type", false, (Command) null);

		MenuItem locationAttributeTypeMenuItem = new MenuItem(
				"Location Attribute Type", false, (Command) null);

		initWidget(mainMenuBar);
		mainMenuBar.setStyleName("main-menu");
		mainMenuBar.setSize("600px", "100%");
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
				Cookies.setCookie("CurrentMenu", "DEFINITION");
			}
		});

		definitionsTypeMenuItem.setStyleName("menu-button");
		definitionsTypeMenuItem.setCommand(new Command() {

			@Override
			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.DEFINITION_TYPE);
			}
		});
		locationsMenuItem.setStyleName("menu-button");
		locationsMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", "SETUP");
			}
		});
		usersMenuItem.setStyleName("menu-button");
		usersMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.USER);
			}
		});

		userFormMenuItem.setStyleName("menu-button");
		userFormMenuItem.setCommand(new Command() {

			@Override
			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.USER_FORM);
			}
		});
		userAttributeTypeMenuItem.setStyleName("menu-button");
		userAttributeTypeMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.USER_ATTRIBUTE_TYPE);
			}
		});

		userFormTypeMenuItem.setStyleName("menu-button");
		userFormTypeMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.USER_FORM_TYPE);
			}
		});
		encounterTypeMenuItem.setStyleName("menu-button");
		encounterTypeMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.ENCOUNTER_TYPE);
			}
		});

		privilegeMenuItem.setStyleName("menu-button");
		privilegeMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.PRIVILEGE);
			}
		});
		locationAttributeTypeMenuItem.setStyleName("menu-button");
		locationAttributeTypeMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu",
						FormType.LOCATION_ATTRIBUTE_TYPE);
			}
		});
		personAttributeTypeMenuItem.setStyleName("menu-button");
		personAttributeTypeMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.PERSON_ATTRIBUTE_TYPE);
			}
		});

		roleMenuItem.setStyleName("menu-button");
		roleMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu", FormType.ROLE);
			}
		});

		elementMenuItem.setStyleName("menu-button");
		elementMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", "ENCOUNTER");
			}
		});
		encountersMenuItem.setStyleName("menu-button");
		encountersMenuItem.setCommand(new Command() {
			public void execute() {
				Cookies.setCookie("CurrentMenu", "ENCOUNTER");
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
					Date loginDate = new Date(Long.parseLong(Cookies
							.getCookie("LoginTime")));
					int mins = new Date(new Date().getTime()
							- loginDate.getTime()).getMinutes();
					String str = "CURRENT USER: " + user + "\n"
							+ "LOGIN TIME: "
							+ loginDate.toGMTString().replace("GMT", "") + "\n"
							+ "CURRENT SESSION: " + mins + " mins";
					Window.alert(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		aboutMenuItem.setStyleName("menu-button");
		aboutMenuItem.setCommand(new Command() {

			public void execute() {
				Cookies.setCookie("CurrentMenu", "About Us");
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

		setupMenuBar.addItem(definitionsMenuItem);
		setupMenuBar.addItem(locationsMenuItem);
		setupMenuBar.addItem(usersMenuItem);
		setupMenuBar.addItem(userFormMenuItem);
		setupMenuBar.addItem(userAttributeTypeMenuItem);
		setupMenuBar.addItem(userFormTypeMenuItem);

		formsMenuBar.addItem(encounterTypeMenuItem);
		formsMenuBar.addItem(elementMenuItem);
		formsMenuBar.addItem(encountersMenuItem);
		formsMenuBar.addItem(definitionsTypeMenuItem);
		formsMenuBar.addItem(roleMenuItem);
		formsMenuBar.addItem(privilegeMenuItem);
		formsMenuBar.addItem(personAttributeTypeMenuItem);
		formsMenuBar.addItem(locationAttributeTypeMenuItem);

		reportingMenuBar.addItem(reportsMenuItem);

		advancedMenuBar.addItem(syncMenuItem);

		helpMenuBar.addItem(aboutMeMenuItem);
		helpMenuBar.addItem(aboutMenuItem);
		helpMenuBar.addItem(feedbackMenuItem);
		helpMenuBar.addItem(helpContentsMenuItem);
	}
}
