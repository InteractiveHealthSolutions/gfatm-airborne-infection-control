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

import java.util.List;

import com.ihsinformatics.gfaicweb.shared.model.LocationClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserRoleClient;
import com.ihsinformatics.gfaicweb.shared.model.UsersClient;

/**
 * @author owais.hussain@ihsinformatics.com
 */
public class TBR {
	private static UsersClient currentUser;
	private static LocationClient currentLocation;
	private static List<UserRoleClient> userRoles;
	private static String currentFormName;
	private static List<PrivilegeClient> currentUserPrivileges;
	private static boolean createUpdateCase;
	private static long sessionLimit = 1200000;
	private static String passcode;

	public static void setPassCode(String passcode) {
		TBR.passcode = passcode;
	}

	public static String getPassCode() {
		return TBR.passcode;
	}

	public static long getSessionLimit() {
		return sessionLimit;
	}

	public static void setSessionLimit(long sessionLimit) {
		TBR.sessionLimit = sessionLimit;
	}

	public static UsersClient getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(UsersClient currentUser) {
		TBR.currentUser = currentUser;
	}

	public static LocationClient getCurrentLocation() {
		return currentLocation;
	}

	public static void setCurrentLocation(LocationClient currentLocation) {
		TBR.currentLocation = currentLocation;
	}

	public static List<UserRoleClient> getUserRoles() {
		return userRoles;
	}

	public static void setUserRoles(List<UserRoleClient> userRoles) {
		TBR.userRoles = userRoles;
	}

	public static String getCurrentFormName() {
		return currentFormName;
	}

	public static void setCurrentForm(String currentFormName) {
		TBR.currentFormName = currentFormName;
	}

	public static List<PrivilegeClient> getCurrentUserPrivileges() {
		return currentUserPrivileges;
	}

	public static void setCurrentUserPrivileges(
			List<PrivilegeClient> userPrivileges) {
		TBR.currentUserPrivileges = userPrivileges;
	}

	public static boolean isCreateUpdateCase() {
		return createUpdateCase;
	}

	public static void setCreateUpdateCase(boolean createUpdateCase) {
		TBR.createUpdateCase = createUpdateCase;
	}

}
