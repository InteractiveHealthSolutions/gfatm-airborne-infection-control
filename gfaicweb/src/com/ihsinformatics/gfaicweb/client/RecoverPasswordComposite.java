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

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class RecoverPasswordComposite extends Composite {
	public RecoverPasswordComposite() {

		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("450px", "100%");

		Label lblUsername = new Label("Username:");
		lblUsername.setStyleName("label");
		flexTable.setWidget(0, 0, lblUsername);

		HorizontalPanel horizontalPanel_1 = new HorizontalPanel();
		flexTable.setWidget(0, 1, horizontalPanel_1);

		TextBox usernameTextBox = new TextBox();
		horizontalPanel_1.add(usernameTextBox);
		usernameTextBox.setStyleName("textbox");
		usernameTextBox.setMaxLength(50);
		usernameTextBox.setVisibleLength(30);

		PushButton btnFind = new PushButton(new Image(
				"WEB-INF/classes/magnifier.png"));
		btnFind.setStyleName("serverResponseLabelError");
		horizontalPanel_1.add(btnFind);

		Label lblPassword = new Label("Secret Question:");
		lblPassword.setStyleName("label");
		flexTable.setWidget(1, 0, lblPassword);

		Label lblWhatIsYour = new Label("What is your secret question?");
		lblWhatIsYour.setStyleName("message");
		flexTable.setWidget(1, 1, lblWhatIsYour);

		Label lblSecretAnswer = new Label("Secret Answer:");
		lblSecretAnswer.setStyleName("label");
		flexTable.setWidget(2, 0, lblSecretAnswer);

		PasswordTextBox passwordTextBox = new PasswordTextBox();
		passwordTextBox.setEnabled(false);
		passwordTextBox.setStyleName("textbox");
		passwordTextBox.setMaxLength(50);
		passwordTextBox.setVisibleLength(30);
		flexTable.setWidget(2, 1, passwordTextBox);

		Button btnLogin = new Button("Change Password");
		btnLogin.setEnabled(false);
		flexTable.setWidget(3, 1, btnLogin);
		btnLogin.setStyleName("submit-button");
		btnLogin.setSize("200px", "48px");
	}
}
