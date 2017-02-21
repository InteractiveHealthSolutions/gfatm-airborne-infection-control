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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ihsinformatics.gfaicweb.shared.model.DefinitionTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.ElementClient;
import com.ihsinformatics.gfaicweb.shared.model.EncounterTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.LocationAttributeClient;
import com.ihsinformatics.gfaicweb.shared.model.LocationAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.LocationClient;
import com.ihsinformatics.gfaicweb.shared.model.PersonAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.PrivilegeClient;
import com.ihsinformatics.gfaicweb.shared.model.RoleClient;
import com.ihsinformatics.gfaicweb.shared.model.UserAttributeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserAttributeTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormResultClient;
import com.ihsinformatics.gfaicweb.shared.model.UserFormTypeClient;
import com.ihsinformatics.gfaicweb.shared.model.UsersClient;

/**
 * @author babar.anis@ihsinformatics.com
 */

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getAllUsers(AsyncCallback<List<UsersClient>> callback);

	void getDefinitionTypes(
			AsyncCallback<List<DefinitionTypeClient>> asyncCallback);

	void saveDefinitionType(String definitionType,
			AsyncCallback<DefinitionTypeClient> callback);

	void deleteDefinitionType(String definitionTypeName,
			AsyncCallback<Boolean> asyncCallback);

	void savePrivilegeClient(String privilegeName,
			AsyncCallback<PrivilegeClient> callback);

	void deleteUser(int userId, AsyncCallback<Void> callback);

	void getEncounterTypes(AsyncCallback<List<EncounterTypeClient>> callback);

	void saveEncounterType(String encounteType,
			AsyncCallback<EncounterTypeClient> callback);

	void getRoleByName(String roleName, AsyncCallback<RoleClient> callback);

	void getUserFormTypes(AsyncCallback<List<UserFormTypeClient>> callback);

	void saveUserFormType(String userFormType,
			AsyncCallback<UserFormTypeClient> callback);

	void saveRole(String roleName, AsyncCallback<RoleClient> callback);

	void saveUser(UsersClient userClient, String password, String answer,
			AsyncCallback<UsersClient> callback);

	void getDefinitionTypeId(String definitionName,
			AsyncCallback<Integer> callback);

	void getEncounterTypeId(String encounterName,
			AsyncCallback<Integer> callback);

	void getLocationAttributeType(
			AsyncCallback<List<LocationAttributeTypeClient>> callback);

	void getLocationAttributeTypeFromAttributeName(String attributeName,
			AsyncCallback<LocationAttributeTypeClient> callback);

	void saveLocationAttributType(
			LocationAttributeTypeClient locationAttributeClient,
			AsyncCallback<LocationAttributeTypeClient> callback);

	void updateLocationAttributeType(
			LocationAttributeTypeClient locationAttributeTypeClient,
			AsyncCallback<Void> callback);

	void login(String username, String password, String locationName,
			AsyncCallback<Boolean> callback);

	void updateDefinitionType(DefinitionTypeClient definitionTypeClient,
			AsyncCallback<Void> callback);

	void getCurrentUserClient(AsyncCallback<UsersClient> callback);

	void getCurrentLocation(AsyncCallback<LocationClient> callback);

	void getCurrentUserPrivileges(AsyncCallback<List<PrivilegeClient>> callback);

	void filterUserbyFullName(String fullName,
			AsyncCallback<List<UsersClient>> callback);

	void filterUserbyUserName(String userName,
			AsyncCallback<List<UsersClient>> callback);

	void filterUserByBoth(String filterOption, String username,
			String fullName, AsyncCallback<List<UsersClient>> callback);

	void updateUsers(UsersClient userClient, AsyncCallback<Void> callback);

	void getCurrentUserRole(UsersClient userClient,
			AsyncCallback<List<RoleClient>> callback);

	void getAllRoles(AsyncCallback<List<RoleClient>> callback);

	void getCurrentUserLocation(UsersClient user,
			AsyncCallback<List<LocationClient>> callback);

	void getAllLocations(AsyncCallback<List<LocationClient>> callback);

	void getFilterLocations(String filterText,
			AsyncCallback<List<LocationClient>> callback);

	void saveUserRole(UsersClient userClient, ArrayList<String> roleList,
			AsyncCallback<Void> callback);

	void saveUserLocation(UsersClient userClient,
			ArrayList<String> locationList, AsyncCallback<Void> callback);

	void getUserbyUserId(int userId, AsyncCallback<UsersClient> callback);

	void getUserAttribute(UsersClient user,
			AsyncCallback<List<UserAttributeClient>> callback);

	void saveUserAttribute(String attributeName, String attributeValue,
			UsersClient user, AsyncCallback<Void> callback);

	void getAllUserAttribute(
			AsyncCallback<List<UserAttributeTypeClient>> callback);

	void updateUsersForDisable(UsersClient userClient,
			AsyncCallback<Void> callback);

	void getUserAttributeNameById(int userAttributeid,
			AsyncCallback<String> callback);

	void deleteAllUserAttributebyUser(UsersClient user,
			AsyncCallback<Void> callback);

	void getLocationById(int id, AsyncCallback<LocationClient> callback);

	void filterLocationByLocationName(String locationName,
			AsyncCallback<List<LocationClient>> callback);

	void getUserAttributeByName(String userAttributeTypeName,
			AsyncCallback<UserAttributeTypeClient> callback);

	void saveUserAttributeType(UserAttributeTypeClient userAttributeTypeClient,
			AsyncCallback<UserAttributeTypeClient> callback);

	void updateUserAttributeType(
			UserAttributeTypeClient userAttributeTypeClient,
			AsyncCallback<Void> callback);

	void updateRole(int roleId, String roleName, AsyncCallback<Void> callback);

	void getAllPrivileges(AsyncCallback<List<PrivilegeClient>> callback);

	void getAllPersonAttributeType(
			AsyncCallback<List<PersonAttributeTypeClient>> callback);

	void getPersonAttributeTypeByName(String name,
			AsyncCallback<PersonAttributeTypeClient> callback);

	void savePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient,
			AsyncCallback<PersonAttributeTypeClient> callback);

	void updatePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient,
			AsyncCallback<Void> callback);

	void getUserFormTypeByName(String userFormTypeName,
			AsyncCallback<UserFormTypeClient> callback);

	void updateUserFormType(int userFormTypeId, String userFormTypeName,
			AsyncCallback<Void> callback);

	void getAllElements(AsyncCallback<List<ElementClient>> callback);

	void getElementByName(String elementName,
			AsyncCallback<ElementClient> callback);

	void saveElement(ElementClient elementClient,
			AsyncCallback<ElementClient> callback);

	void updateElement(ElementClient elementClie, AsyncCallback<Void> callback);

	void updateEncounterType(int encounterTypeId, String encounterTypeName,
			AsyncCallback<Void> callback);

	void saveLocation(LocationClient locationClient, String parentLocation,
			AsyncCallback<LocationClient> callback);

	void deleteLocation(int locationId, AsyncCallback<Void> callback);

	void updateLocation(LocationClient locationClient, String parentLocation,
			AsyncCallback<Void> callback);

	void getAllLocationAttribute(
			AsyncCallback<List<LocationAttributeTypeClient>> callback);

	void saveLocationAttribute(String attributeName, String attributeValue,
			LocationClient location, AsyncCallback<Void> callback);

	void getLocationAttribute(LocationClient location,
			AsyncCallback<List<LocationAttributeClient>> callback);

	void deleteAllLocationAttributebyLocation(LocationClient location,
			AsyncCallback<Void> callback);

	void getLocationByCountry(String countryName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByParentLocation(String parentCountry,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameAndCountry(String locationName, String countryName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameOrCountry(String locationName, String countryName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameAndParent(String locationName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameOrParent(String locationName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByCountryAndParent(String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByCountryOrParent(String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameAndCountryAndParent(String locationName,
			String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameOrCountryAndParent(String locationName,
			String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameOrCountryOrParent(String locationName,
			String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void getLocationByNameAndCountryorParent(String locationName,
			String countryName, String parentName,
			AsyncCallback<List<LocationClient>> callback);

	void saveUserForm(String username, String userFormTypeName,
			String dateEntered, AsyncCallback<Integer> callback);

	void getAllUserFormType(AsyncCallback<List<UserFormTypeClient>> callback);

	void saveUserFormResult(int userFormId, String element, String value,
			AsyncCallback<Void> callback);

	void getUserFormById(int id, AsyncCallback<UserFormClient> callback);

	void getUserFormByUser(String username,
			AsyncCallback<List<UserFormClient>> callback);

	void getUserFormByUserFormType(String userFormType,
			AsyncCallback<List<UserFormClient>> callback);

	void getUserFormResultByUserForm(UserFormClient userForm,
			AsyncCallback<List<UserFormResultClient>> callback);

	void updateUserForm(int userFormId, String userName, String userFormType,
			String dateEntered, AsyncCallback<UserFormClient> callback);

	void deleteAllUserFormResulltByUserForm(UserFormClient userForm,
			AsyncCallback<Void> callback);

	void deleteUserForm(UserFormClient userFormClient,
			AsyncCallback<Void> callback);

	void getUserFormByIDAndUser(int userFormId, String userName,
			AsyncCallback<UserFormClient> callback);

	void getUserFormByIDorUser(int userFormId, String userName,
			AsyncCallback<List<UserFormClient>> callback);

	void getUserFormByIDAndUserAndUserFormType(int userFormId, String userName,
			String userFormType, AsyncCallback<UserFormClient> callback);

	void getUserFormByIDAndUserOrUserFormType(int userFormId, String userName,
			String userFormType, AsyncCallback<UserFormClient> callback);

	void getUserFormByIDOrUserAndUserFormType(int userFormId, String userName,
			String userFormType, AsyncCallback<List<UserFormClient>> callback);

	void getUserFormByIDOrUserOrUserFormType(int userFormId, String userName,
			String userFormType, AsyncCallback<List<UserFormClient>> callback);

	void getUserFormByUserAndUserFormType(String userName, String userFormType,
			AsyncCallback<List<UserFormClient>> callback);

	void getUserFormByUserOrUserFormType(String userName, String userFormType,
			AsyncCallback<List<UserFormClient>> callback);

	void getLatestUserForms(AsyncCallback<List<UserFormClient>> callback);
}
