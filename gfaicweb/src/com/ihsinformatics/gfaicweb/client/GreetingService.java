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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	Boolean deleteDefinitionType(String definitionTypeName);

	void deleteUser(int userId);

	List<UsersClient> getAllUsers();

	int getDefinitionTypeId(String definitionName);

	int getEncounterTypeId(String encounterName);

	List<DefinitionTypeClient> getDefinitionTypes();

	List<EncounterTypeClient> getEncounterTypes();

	RoleClient getRoleByName(String roleName);

	List<UserFormTypeClient> getUserFormTypes();

	List<LocationAttributeTypeClient> getLocationAttributeType();

	LocationAttributeTypeClient getLocationAttributeTypeFromAttributeName(
			String attributeName);

	UsersClient saveUser(UsersClient userClient, String password, String answer);

	LocationAttributeTypeClient saveLocationAttributType(
			LocationAttributeTypeClient locationAttributeClient);

	DefinitionTypeClient saveDefinitionType(String definitionType);

	UserFormTypeClient saveUserFormType(String userFormType);

	EncounterTypeClient saveEncounterType(String encounteType);

	PrivilegeClient savePrivilegeClient(String privilegeName);

	RoleClient saveRole(String roleName);

	void updateLocationAttributeType(
			LocationAttributeTypeClient locationAttributeType);

	void updateUsers(UsersClient userClient);

	boolean login(String username, String password, String locationName);

	void updateDefinitionType(DefinitionTypeClient definitionTypeClient);

	UsersClient getCurrentUserClient();

	List<PrivilegeClient> getCurrentUserPrivileges();

	LocationClient getCurrentLocation();

	List<UsersClient> filterUserbyFullName(String fullName);

	List<UsersClient> filterUserbyUserName(String userName);

	List<UsersClient> filterUserByBoth(String filterOption, String username,
			String fullName);

	List<RoleClient> getCurrentUserRole(UsersClient user);

	List<RoleClient> getAllRoles();

	List<LocationClient> getCurrentUserLocation(UsersClient user);

	List<LocationClient> getAllLocations();

	List<LocationClient> getFilterLocations(String filterText);

	void saveUserRole(UsersClient userClient, ArrayList<String> roleList);

	void saveUserLocation(UsersClient userClient, ArrayList<String> locationList);

	UsersClient getUserbyUserId(int userId);

	List<UserAttributeClient> getUserAttribute(UsersClient user);

	void saveUserAttribute(String attributeName, String attributeValue,
			UsersClient user);

	List<UserAttributeTypeClient> getAllUserAttribute();

	void updateUsersForDisable(UsersClient userClient);

	String getUserAttributeNameById(int userAttributeid);

	void deleteAllUserAttributebyUser(UsersClient user);

	LocationClient getLocationById(int id);

	List<LocationClient> filterLocationByLocationName(String locationName);

	UserAttributeTypeClient getUserAttributeByName(String userAttributeTypeName);

	UserAttributeTypeClient saveUserAttributeType(
			UserAttributeTypeClient userAttributeTypeClient);

	void updateUserAttributeType(UserAttributeTypeClient userAttributeTypeClient);

	void updateRole(int roleId, String roleName);

	List<PrivilegeClient> getAllPrivileges();

	List<PersonAttributeTypeClient> getAllPersonAttributeType();

	PersonAttributeTypeClient getPersonAttributeTypeByName(String name);

	PersonAttributeTypeClient savePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient);

	void updatePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient);

	UserFormTypeClient getUserFormTypeByName(String userFormTypeName);

	void updateUserFormType(int userFormTypeId, String userFormTypeName);

	List<ElementClient> getAllElements();

	ElementClient getElementByName(String elementName);

	ElementClient saveElement(ElementClient elementClient);

	void updateElement(ElementClient elementClient);

	void updateEncounterType(int encounterTypeId, String encounterTypeName);

	LocationClient saveLocation(LocationClient locationClient,
			String parentLocation);

	void deleteLocation(int locationId);

	void updateLocation(LocationClient locationClient, String parentLocation);

	List<LocationAttributeTypeClient> getAllLocationAttribute();

	void saveLocationAttribute(String attributeName, String attributeValue,
			LocationClient location);

	List<LocationAttributeClient> getLocationAttribute(LocationClient location);

	void deleteAllLocationAttributebyLocation(LocationClient location);

	List<LocationClient> getLocationByCountry(String countryName);

	List<LocationClient> getLocationByParentLocation(String parentCountry);

	List<LocationClient> getLocationByNameAndCountry(String locationName,
			String countryName);

	List<LocationClient> getLocationByNameOrCountry(String locationName,
			String countryName);

	List<LocationClient> getLocationByNameAndParent(String locationName,
			String parentName);

	List<LocationClient> getLocationByNameOrParent(String locationName,
			String parentName);

	List<LocationClient> getLocationByCountryAndParent(String countryName,
			String parentName);

	List<LocationClient> getLocationByCountryOrParent(String countryName,
			String parentName);

	List<LocationClient> getLocationByNameAndCountryAndParent(
			String locationName, String countryName, String parentName);

	List<LocationClient> getLocationByNameOrCountryAndParent(
			String locationName, String countryName, String parentName);

	List<LocationClient> getLocationByNameOrCountryOrParent(
			String locationName, String countryName, String parentName);

	List<LocationClient> getLocationByNameAndCountryorParent(
			String locationName, String countryName, String parentName);

	List<UserFormTypeClient> getAllUserFormType();

	int saveUserForm(String username, String userFormTypeName,
			String dateEntered);

	void saveUserFormResult(int userFormId, String element, String value);

	UserFormClient getUserFormById(int id);

	List<UserFormClient> getUserFormByUser(String userName);

	List<UserFormClient> getUserFormByUserFormType(String userFormType);

	List<UserFormResultClient> getUserFormResultByUserForm(
			UserFormClient userForm);

	UserFormClient updateUserForm(int userFormId, String userName,
			String userFormType, String dateEntered);

	void deleteAllUserFormResulltByUserForm(UserFormClient userForm);

	void deleteUserForm(UserFormClient userFormClient);

	UserFormClient getUserFormByIDAndUser(int userFormId, String userName);

	List<UserFormClient> getUserFormByIDorUser(int userFormId, String userName);

	UserFormClient getUserFormByIDAndUserAndUserFormType(int userFormId,
			String userName, String userFormType);

	UserFormClient getUserFormByIDAndUserOrUserFormType(int userFormId,
			String userName, String userFormType);

	List<UserFormClient> getUserFormByIDOrUserAndUserFormType(int userFormId,
			String userName, String userFormType);

	List<UserFormClient> getUserFormByIDOrUserOrUserFormType(int userFormId,
			String userName, String userFormType);

	List<UserFormClient> getUserFormByUserAndUserFormType(String userName,
			String userFormType);

	List<UserFormClient> getUserFormByUserOrUserFormType(String userName,
			String userFormType);

	List<UserFormClient> getLatestUserForms();
}
