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

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class HeaderComposite extends Composite {
	public HeaderComposite() {

		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("700px", "135px");

		Image ihsLogo = new Image("WEB-INF/classes/ihsLogo.png");
		flexTable.setWidget(0, 0, ihsLogo);

		VerticalPanel verticalPanel_2 = new VerticalPanel();
		flexTable.setWidget(0, 1, verticalPanel_2);

		Label lblApplicationTitle = new Label("Global Fund - Airborne Infection Control");
		verticalPanel_2.add(lblApplicationTitle);
		lblApplicationTitle.setStyleName("heading");

		Label lblWebInterfaceFor = new Label(
				"Web interface for Global Fund - Airborne Infection Control. Powered by Google Web Tookit");
		lblWebInterfaceFor.setStyleName("sub-label");
		verticalPanel_2.add(lblWebInterfaceFor);

		VerticalPanel verticalPanel = new VerticalPanel();
		flexTable.setWidget(0, 2, verticalPanel);

		Image userImage = new Image("WEB-INF/classes/male.png");
		verticalPanel.add(userImage);
		verticalPanel.setCellVerticalAlignment(userImage,
				HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.setCellHorizontalAlignment(userImage,
				HasHorizontalAlignment.ALIGN_CENTER);

		Label lblUserName = new Label("owais.hussain");
		lblUserName.setStyleName("sub-label");
		verticalPanel.add(lblUserName);

		Label lblLocation = new Label("Interactive Health Sol..");
		lblLocation.setStyleName("sub-label");
		verticalPanel.add(lblLocation);

		Hyperlink hprlnkAccount = new Hyperlink("Account", false,
				"newHistoryToken");
		hprlnkAccount.setStyleName("link");
		verticalPanel.add(hprlnkAccount);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 2,
				HasHorizontalAlignment.ALIGN_RIGHT);
	}

}
