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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author owais.hussain@ihsinformatics.com
 */

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gfaicweb implements EntryPoint {

	static RootPanel root;
	final GreetingServiceAsync webService = GWT.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		root = RootPanel.get("appContainer");
		LoginComposite loginComposite = new LoginComposite();
		setComposite(loginComposite);
	}

	/**
	 * Clear out existing composites and set the one passed
	 * 
	 * @param composite
	 */
	public static void setComposite(Composite composite) {
		root.clear();
		root.add(composite.asWidget());
	}
}
