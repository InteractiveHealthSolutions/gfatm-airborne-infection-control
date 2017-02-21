/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

package com.ihsinformatics.gfaicweb.server;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.xml.bind.ValidationException;

import org.hibernate.HibernateException;

import com.ihsinformatics.gfaicweb.client.GreetingService;
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
import com.ihsinformatics.tbreachapi.core.TBR;
import com.ihsinformatics.tbreachapi.core.service.impl.ServerService;
import com.ihsinformatics.tbreachapi.model.DefinitionType;
import com.ihsinformatics.tbreachapi.model.Element;
import com.ihsinformatics.tbreachapi.model.EncounterType;
import com.ihsinformatics.tbreachapi.model.Location;
import com.ihsinformatics.tbreachapi.model.LocationAttribute;
import com.ihsinformatics.tbreachapi.model.LocationAttributeType;
import com.ihsinformatics.tbreachapi.model.PersonAttributeType;
import com.ihsinformatics.tbreachapi.model.Privilege;
import com.ihsinformatics.tbreachapi.model.Role;
import com.ihsinformatics.tbreachapi.model.UserAttribute;
import com.ihsinformatics.tbreachapi.model.UserAttributeType;
import com.ihsinformatics.tbreachapi.model.UserForm;
import com.ihsinformatics.tbreachapi.model.UserFormResult;
import com.ihsinformatics.tbreachapi.model.UserFormType;
import com.ihsinformatics.tbreachapi.model.UserLocation;
import com.ihsinformatics.tbreachapi.model.UserLocationId;
import com.ihsinformatics.tbreachapi.model.UserRole;
import com.ihsinformatics.tbreachapi.model.UserRoleId;
import com.ihsinformatics.tbreachapi.model.Users;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author babar.anis@ihsinformatics.com
 */

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public static ServerService service;
	public static ModelConverter converter;

	public GreetingServiceImpl() {
		TBR.readProperties("tbreach-api.properties");
		converter = new ModelConverter();
		service = new ServerService();
		service.startup();
	}

	@Override
	public List<UsersClient> getAllUsers() {
		List<UsersClient> users = new ArrayList<UsersClient>();
		List<Users> userList = service.getUserService().getAllUsers();

		for (Users user : userList) {
			users.add(converter.convertUsersToUsersClient(user));
		}
		return users;
	}

	@Override
	public UsersClient saveUser(UsersClient userClient, String password,
			String answer) {
		Users user = converter.convertUsersClientToUsers(userClient);
		user.setPassword(password);
		user.setSecretAnswer(answer);
		service.getUserService().saveUser(user);
		user = service.getUserService().getUserByUsername(
				userClient.getUsername());
		UsersClient userClientObject = new UsersClient();
		userClientObject = converter.convertUsersToUsersClient(user);
		return userClientObject;
	}

	@Override
	public List<DefinitionTypeClient> getDefinitionTypes() {
		List<DefinitionTypeClient> types = new ArrayList<DefinitionTypeClient>();
		List<DefinitionType> definitionTypes = service.getMetadataService()
				.getAllDefinitionTypes();
		for (DefinitionType type : definitionTypes) {
			DefinitionTypeClient definitionObject = new DefinitionTypeClient();
			definitionObject.setDefinitionTypeId(type.getDefinitionTypeId());
			definitionObject.setDefinitionType(type.getDefinitionType());
			types.add(definitionObject);
		}
		return types;
	}

	@Override
	public List<LocationAttributeTypeClient> getLocationAttributeType() {
		List<LocationAttributeTypeClient> types = new ArrayList<LocationAttributeTypeClient>();
		List<LocationAttributeType> locationAttributeType = service
				.getLocationService().getAllLocationAttributeTypes();
		for (LocationAttributeType type : locationAttributeType) {
			LocationAttributeTypeClient locationAttributeObject = new LocationAttributeTypeClient();
			locationAttributeObject = converter
					.convertLocationAttributeTypetoLocationAttributeTypeClient(type);
			types.add(locationAttributeObject);
		}
		return types;
	}

	@Override
	public DefinitionTypeClient saveDefinitionType(String definitionType) {
		DefinitionType newDefinition = new DefinitionType(definitionType);
		DefinitionTypeClient definitionTypeClient = new DefinitionTypeClient();
		service.getMetadataService().saveDefinitionType(newDefinition);
		newDefinition = service.getMetadataService().getDefinitionTypeByName(
				definitionType);
		definitionTypeClient.setDefinitionTypeId(newDefinition
				.getDefinitionTypeId());
		definitionTypeClient.setDefinitionType(newDefinition
				.getDefinitionType());
		return definitionTypeClient;
	}

	@Override
	public Boolean deleteDefinitionType(String definitionTypeName) {
		// DefinitionType definitionType =
		// service.getMetadataService().getDefinitionTypeByName(definitionTypeName);
		// if(definitionType==null){
		// return false;
		// }
		// service.getMetadataService().deleteDefinitionType(definitionType);
		return false;
	}

	@Override
	public PrivilegeClient savePrivilegeClient(String privilegeName) {
		Privilege savePrivilege = new Privilege(privilegeName);
		service.getUserService().savePrivilege(savePrivilege);
		PrivilegeClient privilegeClient = new PrivilegeClient();
		savePrivilege = service.getUserService().getPrivilegeByName(
				privilegeName);
		privilegeClient.setPrivilege(savePrivilege.getPrivilege());
		return privilegeClient;

	}

	@Override
	public void deleteUser(int userId) {
		Users userClient = new Users();
		userClient = service.getUserService().getUserById(userId);
		if (userClient != null) {
			List<UserRole> roleList = service.getUserService()
					.getUserRolesByUser(userClient);
			for (UserRole role : roleList) {
				service.getUserService().deleteUserRole(role);
			}
			List<UserLocation> locationList = service.getUserService()
					.getUserLocationsByUser(userClient);
			for (UserLocation location : locationList) {
				service.getUserService().deleteUserLocation(location);
			}

			List<UserAttribute> attributeList = service.getUserService()
					.getUserAttributesByUser(userClient);
			for (UserAttribute item : attributeList) {
				service.getUserService().deleteUserAttribute(item);
			}

			service.getUserService().deleteUser(userClient);
		}
	}

	@Override
	public List<EncounterTypeClient> getEncounterTypes() {
		List<EncounterTypeClient> clientTypes = new ArrayList<EncounterTypeClient>();
		List<EncounterType> encounterTypeList = service.getEncounterService()
				.getAllEncounterTypes();
		for (EncounterType type : encounterTypeList) {
			EncounterTypeClient encounterObject = new EncounterTypeClient();
			encounterObject.setEncounterTypeId(type.getEncounterTypeId());
			encounterObject.setEncounterType(type.getEncounterType());
			clientTypes.add(encounterObject);
		}
		return clientTypes;
	}

	@Override
	public EncounterTypeClient saveEncounterType(String encounteType) {
		EncounterType result = new EncounterType(encounteType);
		service.getEncounterService().saveEncounterType(result);
		result = service.getEncounterService().getEncounterTypeByName(
				encounteType);
		return converter.convertEncounterTypetoEncounterTypeClient(result);
	}

	@Override
	public RoleClient getRoleByName(String roleName) {
		return converter.convertRoletoRoleClient(service.getUserService()
				.getRoleByName(roleName));
	}

	@Override
	public List<UserFormTypeClient> getUserFormTypes() {
		List<UserFormType> userFormType = service.getUserFormService()
				.getAllUserFormTypes();
		List<UserFormTypeClient> userFormTypeClient = new ArrayList<UserFormTypeClient>();
		for (UserFormType item : userFormType) {
			userFormTypeClient.add(converter
					.convertUserFormTypetoUserFormTypeClient(item));
		}
		return userFormTypeClient;
	}

	@Override
	public UserFormTypeClient saveUserFormType(String userFormType) {
		UserFormType result = new UserFormType(userFormType);
		service.getUserFormService().saveUserFormType(result);
		result = service.getUserFormService().getUserFormTypeByName(
				userFormType);
		return (converter.convertUserFormTypetoUserFormTypeClient(result));
	}

	@Override
	public RoleClient saveRole(String roleName) {
		Role result = new Role(roleName);
		service.getUserService().saveRole(result);
		result = service.getUserService().getRoleByName(roleName);
		return converter.convertRoletoRoleClient(result);
	}

	@Override
	public int getDefinitionTypeId(String definitionName) {
		DefinitionType type = service.getMetadataService()
				.getDefinitionTypeByName(definitionName);
		return type.getDefinitionTypeId();
	}

	@Override
	public int getEncounterTypeId(String encounterName) {
		EncounterType type = service.getEncounterService()
				.getEncounterTypeByName(encounterName);
		return type.getEncounterTypeId();
	}

	@Override
	public LocationAttributeTypeClient getLocationAttributeTypeFromAttributeName(
			String attributeName) {
		LocationAttributeType tempObject = service.getLocationService()
				.getLocationAttributeTypeByName(attributeName);
		LocationAttributeTypeClient locationAttributeType = converter
				.convertLocationAttributeTypetoLocationAttributeTypeClient(tempObject);
		return locationAttributeType;
	}

	@Override
	public LocationAttributeTypeClient saveLocationAttributType(
			LocationAttributeTypeClient locationAttributeClient) {
		LocationAttributeType locObject = converter
				.LocationAttributeTypeClientToLocationAttributeType(locationAttributeClient);
		service.getLocationService().saveLocationAttributeType(locObject);
		return locationAttributeClient;
	}

	@Override
	public void updateLocationAttributeType(
			LocationAttributeTypeClient locationAttributeType) {
		LocationAttributeType locationAttributeObject = new LocationAttributeType();
		locationAttributeObject
				.setLocationAttributeTypeId(locationAttributeType
						.getLocationAttributeTypeId());
		locationAttributeObject.setAttributeName(locationAttributeType
				.getAttributeName());
		locationAttributeObject
				.setDataType(locationAttributeType.getDataType());
		locationAttributeObject.setDescription(locationAttributeType
				.getDescription());
		locationAttributeObject.setValidationRegex(locationAttributeType
				.getValidationRegex());
		locationAttributeObject
				.setRequired(locationAttributeType.getRequired());
		LocationAttributeType locationAttribute2 = service.getLocationService()
				.getLocationAttributeTypeById(
						locationAttributeType.getLocationAttributeTypeId());
		locationAttributeObject.setDateCreated(locationAttribute2
				.getDateCreated());
		locationAttributeObject.setUsersByCreatedBy(locationAttribute2
				.getUsersByCreatedBy());
		locationAttributeObject.setLocationByCreatedAt(locationAttribute2
				.getLocationByCreatedAt());
		locationAttributeObject.setDateChanged(locationAttribute2
				.getDateChanged());
		locationAttributeObject.setUsersByChangedBy(locationAttribute2
				.getUsersByChangedBy());
		locationAttributeObject.setLocationByChangedAt(locationAttribute2
				.getLocationByChangedAt());
		locationAttributeObject.setUuid(locationAttribute2.getUuid());
		service.getLocationService().updateLocationAttributeType(
				locationAttributeObject);
	}

	@Override
	public boolean login(String username, String password, String locationName) {
		if (locationName == null) {
			return service.login(username, password);
		}
		return service.login(username, password, locationName);
	}

	@Override
	public void updateDefinitionType(DefinitionTypeClient definitionTypeClient) {
		DefinitionType definitionType = new DefinitionType();
		definitionType.setDefinitionTypeId(definitionTypeClient
				.getDefinitionTypeId());
		definitionType.setDefinitionType(definitionTypeClient
				.getDefinitionType());
		service.getMetadataService().updateDefinitionType(definitionType);
	}

	@Override
	public UsersClient getCurrentUserClient() {
		Users user = ServerService.getCurrentUser();
		UsersClient userClient = converter.convertUsersToUsersClient(user);
		return userClient;
	}

	@Override
	public LocationClient getCurrentLocation() {
		ServerService.getCurrentLocation();
		LocationClient locationClient = new LocationClient();
		return locationClient;
	}

	@Override
	public List<PrivilegeClient> getCurrentUserPrivileges() {
		List<Privilege> privileges = ServerService.getCurrentUserPrivileges();
		List<PrivilegeClient> privilegeClientList = new ArrayList<PrivilegeClient>();
		for (Privilege privilege : privileges) {
			PrivilegeClient client = new PrivilegeClient(
					privilege.getPrivilege());
			privilegeClientList.add(client);
		}
		return privilegeClientList;
	}

	@Override
	public List<UsersClient> filterUserbyFullName(String fullName) {
		List<UsersClient> users = new ArrayList<UsersClient>();
		List<Users> userList = service.getUserService().getAllUsers();

		for (Users user : userList) {
			String userFullName = user.getFullName();
			userFullName = userFullName.toLowerCase();
			if (userFullName.contains(fullName)) {
				users.add(converter.convertUsersToUsersClient(user));
			}
		}
		return users;
	}

	@Override
	public List<UsersClient> filterUserbyUserName(String userName) {
		List<UsersClient> users = new ArrayList<UsersClient>();
		List<Users> userList = service.getUserService().getAllUsers();

		for (Users user : userList) {
			String userUserName = user.getUsername();
			userUserName = userUserName.toLowerCase();
			if (userUserName.contains(userName)) {
				users.add(converter.convertUsersToUsersClient(user));
			}
		}
		return users;
	}

	@Override
	public List<UsersClient> filterUserByBoth(String filterOption,
			String userName, String fullName) {
		List<UsersClient> users = new ArrayList<UsersClient>();
		List<Users> userList = service.getUserService().getAllUsers();
		if (filterOption.equalsIgnoreCase("AND")) {
			for (Users user : userList) {
				String userUserName = user.getUsername();
				userUserName = userUserName.toLowerCase();
				String userFullName = user.getFullName();
				userFullName = userFullName.toLowerCase();
				if (userUserName.contains(userName)
						&& userFullName.contains(fullName)) {
					users.add(converter.convertUsersToUsersClient(user));
				}
			}

		}

		else if (filterOption.equalsIgnoreCase("OR")) {
			for (Users user : userList) {
				String userUserName = user.getUsername();
				String userFullName = user.getFullName();
				userFullName = userFullName.toLowerCase();
				userUserName = userUserName.toLowerCase();
				if (userUserName.contains(userName)
						|| userFullName.contains(fullName)) {
					users.add(converter.convertUsersToUsersClient(user));
				}
			}

		}
		return users;
	}

	@Override
	public void updateUsers(UsersClient userClient) {
		Users users = new Users();
		Users user2 = service.getUserService().getUserById(
				userClient.getUserId());
		users.setUserId(userClient.getUserId());
		users.setUsername(userClient.getUsername());
		users.setFullName(userClient.getFullName());
		users.setGlobalDataAccess(userClient.getGlobalDataAccess());
		users.setSecretQuestion(userClient.getSecretQuestion());
		if (user2.getDisabled() == true) {
			users.setDisabled(true);
			users.setReasonDisabled(user2.getReasonDisabled());
		} else {
			users.setDisabled(false);
		}
		users.setPasswordHash(user2.getPasswordHash());
		users.setSecretAnswerHash(user2.getSecretAnswerHash());
		users.setDateCreated(user2.getDateCreated());
		users.setDateChanged(user2.getDateChanged());
		users.setUsersByCreatedBy(user2.getUsersByCreatedBy());
		users.setUsersByChangedBy(user2.getUsersByChangedBy());
		users.setLocationByCreatedAt(user2.getLocationByCreatedAt());
		users.setLocationByChangedAt(user2.getLocationByChangedAt());
		users.setUuid(user2.getUuid());
		service.getUserService().updateUser(users);
	}

	@Override
	public List<RoleClient> getCurrentUserRole(UsersClient userClient) {
		Users user = converter.convertUsersClientToUsers(userClient);
		List<RoleClient> currentRoleClient = new ArrayList<RoleClient>();
		List<UserRole> currentUserRole = service.getUserService()
				.getUserRolesByUser(user);
		for (UserRole userRole : currentUserRole) {
			currentRoleClient.add(converter.convertRoletoRoleClient(userRole
					.getRole()));
		}
		return currentRoleClient;
	}

	@Override
	public List<RoleClient> getAllRoles() {
		List<RoleClient> currentRoleClient = new ArrayList<RoleClient>();
		List<Role> allRole = service.getUserService().getAllRoles();
		for (Role role : allRole) {
			currentRoleClient.add(converter.convertRoletoRoleClient(role));
		}
		return currentRoleClient;
	}

	@Override
	public List<LocationClient> getCurrentUserLocation(UsersClient userClient) {
		Users user = converter.convertUsersClientToUsers(userClient);
		List<LocationClient> currentLocationClient = new ArrayList<LocationClient>();
		List<UserLocation> allLocation = service.getUserService()
				.getUserLocationsByUser(user);
		for (UserLocation userLocation : allLocation) {
			currentLocationClient.add(converter
					.LocationToLocationClient(userLocation
							.getLocationByLocationId()));
		}
		return currentLocationClient;
	}

	@Override
	public List<LocationClient> getAllLocations() {
		List<LocationClient> currentLocationClient = new ArrayList<LocationClient>();
		List<Location> allLocation = service.getLocationService()
				.getAllLocations();
		for (Location location : allLocation) {
			currentLocationClient.add(converter
					.LocationToLocationClient(location));
		}
		return currentLocationClient;
	}

	@Override
	public List<LocationClient> getFilterLocations(String filterText) {
		List<LocationClient> currentLocationClient = new ArrayList<LocationClient>();
		List<Location> allLocation = service.getLocationService()
				.getAllLocations();
		for (Location location : allLocation) {
			if (location.getLocationName().toLowerCase().contains(filterText))
				currentLocationClient.add(converter
						.LocationToLocationClient(location));
		}
		return currentLocationClient;
	}

	@Override
	public void saveUserRole(UsersClient userClient, ArrayList<String> roleList) {
		Users user = converter.convertUsersClientToUsers(userClient);
		List<UserRole> currentUserRoleList = service.getUserService()
				.getUserRolesByUser(user);
		for (UserRole userRole : currentUserRoleList) {
			service.getUserService().deleteUserRole(userRole);
		}
		for (String item : roleList) {
			Role role = service.getUserService().getRoleByName(item);
			UserRole userRole = new UserRole();
			UserRoleId userRoleId = new UserRoleId(user.getUserId(),
					role.getRoleId());
			userRole.setId(userRoleId);
			userRole.setUsersByUserId(user);
			userRole.setRole(role);
			service.getUserService().saveUserRole(userRole);
		}
	}

	@Override
	public void saveUserLocation(UsersClient userClient,
			ArrayList<String> locationList) {
		Users user = converter.convertUsersClientToUsers(userClient);
		List<UserLocation> currentUserLocationList = service.getUserService()
				.getUserLocationsByUser(user);
		for (UserLocation userLocation : currentUserLocationList) {
			service.getUserService().deleteUserLocation(userLocation);
		}
		for (String item : locationList) {
			Location location = service.getLocationService().getLocationByName(
					item);
			UserLocation userLocation = new UserLocation();
			UserLocationId userLocationId = new UserLocationId(
					user.getUserId(), location.getLocationId());
			userLocation.setId(userLocationId);
			userLocation.setLocationByLocationId(location);
			userLocation.setUsersByUserId(user);
			service.getUserService().saveUserLocation(userLocation);
		}

	}

	@Override
	public UsersClient getUserbyUserId(int userId) {
		Users user = service.getUserService().getUserById(userId);
		return converter.convertUsersToUsersClient(user);
	}

	@Override
	public List<UserAttributeClient> getUserAttribute(UsersClient userClient) {
		Users user = converter.convertUsersClientToUsers(userClient);
		List<UserAttribute> userAttributeList = service.getUserService()
				.getUserAttributesByUser(user);
		List<UserAttributeClient> userAttributeClient = new ArrayList<UserAttributeClient>();
		for (UserAttribute item : userAttributeList) {
			userAttributeClient.add(converter
					.convertUserAttributeToUserAttributeClient(item));
		}
		return userAttributeClient;
	}

	@Override
	public void saveUserAttribute(String attributeName, String attributeValue,
			UsersClient user) {
		UserAttributeType userAttributeType = service.getUserService()
				.getUserAttributeTypeByName(attributeName);
		UserAttribute userAttribute = new UserAttribute(
				converter.convertUsersClientToUsers(user), userAttributeType,
				attributeValue);
		try {
			service.getUserService().saveUserAttribute(userAttribute);
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<UserAttributeTypeClient> getAllUserAttribute() {
		List<UserAttributeType> userAttributeTypeList = service
				.getUserService().getAllUserAttributeTypes();
		List<UserAttributeTypeClient> userAttributeTypeClientList = new ArrayList<UserAttributeTypeClient>();
		for (UserAttributeType item : userAttributeTypeList) {
			userAttributeTypeClientList.add(converter
					.convertUserAttributeTypeToUserAttributeTypeClient(item));
		}
		return userAttributeTypeClientList;
	}

	@Override
	public void updateUsersForDisable(UsersClient userClient) {
		Users users = new Users();
		Users user2 = service.getUserService().getUserById(
				userClient.getUserId());
		users.setUserId(userClient.getUserId());
		users.setUsername(userClient.getUsername());
		users.setFullName(userClient.getFullName());
		users.setGlobalDataAccess(userClient.getGlobalDataAccess());
		users.setDisabled(userClient.getDisabled());
		users.setSecretQuestion(userClient.getSecretQuestion());
		if (userClient.getDisabled() == true) {
			users.setDisabled(true);
			users.setReasonDisabled(userClient.getReasonDisabled());
		} else {
			users.setDisabled(false);
		}
		users.setPasswordHash(user2.getPasswordHash());
		users.setSecretAnswerHash(user2.getSecretAnswerHash());
		users.setDateCreated(user2.getDateCreated());
		users.setDateChanged(user2.getDateChanged());
		users.setUsersByCreatedBy(user2.getUsersByCreatedBy());
		users.setUsersByChangedBy(user2.getUsersByChangedBy());
		users.setLocationByCreatedAt(user2.getLocationByCreatedAt());
		users.setLocationByChangedAt(user2.getLocationByChangedAt());
		users.setUuid(user2.getUuid());
		service.getUserService().updateUser(users);

	}

	@Override
	public String getUserAttributeNameById(int userAttributeid) {
		return service.getUserService()
				.getUserAttributeTypeById(userAttributeid).getAttributeName();
	}

	@Override
	public void deleteAllUserAttributebyUser(UsersClient user) {
		Users userObject = converter.convertUsersClientToUsers(user);
		List<UserAttribute> attributeList = service.getUserService()
				.getUserAttributesByUser(userObject);
		for (UserAttribute item : attributeList) {
			service.getUserService().deleteUserAttribute(item);
		}
	}

	@Override
	public LocationClient getLocationById(int id) {
		Location location = service.getLocationService().getLocationById(id);
		return converter.LocationToLocationClient(location);
	}

	@Override
	public List<LocationClient> filterLocationByLocationName(String locationName) {
		List<Location> locationList = service.getLocationService()
				.getAllLocations();
		List<LocationClient> locationClientList = new ArrayList<LocationClient>();
		for (Location location : locationList) {
			String loc = location.getLocationName().toLowerCase();
			if (loc.contains(locationName.toLowerCase())) {
				locationClientList.add(converter
						.LocationToLocationClient(location));
			}
		}
		return locationClientList;
	}

	@Override
	public UserAttributeTypeClient getUserAttributeByName(
			String userAttributeTypeName) {
		return converter
				.convertUserAttributeTypeToUserAttributeTypeClient(service
						.getUserService().getUserAttributeTypeByName(
								userAttributeTypeName));
	}

	@Override
	public UserAttributeTypeClient saveUserAttributeType(
			UserAttributeTypeClient userAttributeTypeClient) {
		UserAttributeType userAttributeType = converter
				.convertUserAttributeTypeClientToUserAttributeType(userAttributeTypeClient);
		userAttributeType = service.getUserService().saveUserAttributeType(
				userAttributeType);
		return converter
				.convertUserAttributeTypeToUserAttributeTypeClient(userAttributeType);
	}

	@Override
	public void updateUserAttributeType(
			UserAttributeTypeClient userAttributeTypeClient) {
		UserAttributeType userAttributeObject = new UserAttributeType();
		userAttributeObject.setUserAttributeTypeId(userAttributeTypeClient
				.getUserAttributeTypeId());
		userAttributeObject.setAttributeName(userAttributeTypeClient
				.getAttributeName());
		userAttributeObject.setDataType(userAttributeTypeClient.getDataType());
		userAttributeObject.setDescription(userAttributeTypeClient
				.getDescription());
		userAttributeObject.setValidationRegex(userAttributeTypeClient
				.getValidationRegex());
		userAttributeObject.setRequired(userAttributeTypeClient.getRequired());
		UserAttributeType userAttribute2 = service.getUserService()
				.getUserAttributeTypeById(
						userAttributeTypeClient.getUserAttributeTypeId());
		userAttributeObject.setDateCreated(userAttribute2.getDateCreated());
		userAttributeObject.setUsersByCreatedBy(userAttribute2
				.getUsersByCreatedBy());
		userAttributeObject.setLocationByCreatedAt(userAttribute2
				.getLocationByCreatedAt());
		userAttributeObject.setDateChanged(userAttribute2.getDateChanged());
		userAttributeObject.setUsersByChangedBy(userAttribute2
				.getUsersByChangedBy());
		userAttributeObject.setLocationByChangedAt(userAttribute2
				.getLocationByChangedAt());
		userAttributeObject.setUuid(userAttribute2.getUuid());
		service.getUserService().updateUserAttributeType(userAttributeObject);
	}

	@Override
	public void updateRole(int roleId, String roleName) {
		Role role = service.getUserService().getRoleById(roleId);
		role.setRoleName(roleName);
		service.getUserService().updateRole(role);
	}

	@Override
	public List<PrivilegeClient> getAllPrivileges() {
		List<Privilege> privileges = service.getUserService()
				.getAllPrivileges();
		List<PrivilegeClient> privilegesClient = new ArrayList<PrivilegeClient>();
		for (Privilege item : privileges) {
			PrivilegeClient object = new PrivilegeClient();
			object.setPrivilege(item.getPrivilege());
			privilegesClient.add(object);
		}
		return privilegesClient;
	}

	@Override
	public List<PersonAttributeTypeClient> getAllPersonAttributeType() {
		List<PersonAttributeType> personAttributeType = service
				.getPersonService().getAllPersonAttributeTypes();
		List<PersonAttributeTypeClient> personAttributeTypeClient = new ArrayList<PersonAttributeTypeClient>();
		for (PersonAttributeType item : personAttributeType) {
			personAttributeTypeClient
					.add(converter
							.convertPersonAttributeTypeToPersonAttributeTypeClient(item));
		}
		return personAttributeTypeClient;
	}

	@Override
	public PersonAttributeTypeClient getPersonAttributeTypeByName(String name) {
		PersonAttributeType personAttributeType = service.getPersonService()
				.getPersonAttributeTypeByName(name);
		return converter
				.convertPersonAttributeTypeToPersonAttributeTypeClient(personAttributeType);
	}

	@Override
	public PersonAttributeTypeClient savePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient) {
		service.getPersonService()
				.savePersonAttributeType(
						converter
								.convertPersonAttributeTypeClientToPersonAttributeType(personAttributeTypeClient));
		PersonAttributeType personObject = service.getPersonService()
				.getPersonAttributeTypeByName(
						personAttributeTypeClient.getAttributeName());
		return converter
				.convertPersonAttributeTypeToPersonAttributeTypeClient(personObject);
	}

	@Override
	public void updatePersonAttributeType(
			PersonAttributeTypeClient personAttributeTypeClient) {
		PersonAttributeType personAttributeObject = new PersonAttributeType();
		personAttributeObject
				.setPersonAttributeTypeId(personAttributeTypeClient
						.getPersonAttributeTypeId());
		personAttributeObject.setAttributeName(personAttributeTypeClient
				.getAttributeName());
		personAttributeObject.setDataType(personAttributeTypeClient
				.getDataType());
		personAttributeObject.setDescription(personAttributeTypeClient
				.getDescription());
		personAttributeObject.setValidationRegex(personAttributeTypeClient
				.getValidationRegex());
		personAttributeObject.setRequired(personAttributeTypeClient
				.getRequired());
		PersonAttributeType personAttribute2 = service.getPersonService()
				.getPersonAttributeTypeById(
						personAttributeTypeClient.getPersonAttributeTypeId());
		personAttributeObject.setDateCreated(personAttribute2.getDateCreated());
		personAttributeObject.setUsersByCreatedBy(personAttribute2
				.getUsersByCreatedBy());
		personAttributeObject.setLocationByCreatedAt(personAttribute2
				.getLocationByCreatedAt());
		personAttributeObject.setDateChanged(personAttribute2.getDateChanged());
		personAttributeObject.setUsersByChangedBy(personAttribute2
				.getUsersByChangedBy());
		personAttributeObject.setLocationByChangedAt(personAttribute2
				.getLocationByChangedAt());
		personAttributeObject.setUuid(personAttribute2.getUuid());
		service.getPersonService().updatePersonAttributeType(
				personAttributeObject);

	}

	@Override
	public UserFormTypeClient getUserFormTypeByName(String userFormTypeName) {
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeName);
		return converter.convertUserFormTypetoUserFormTypeClient(userFormType);
	}

	@Override
	public void updateUserFormType(int userFormTypeId, String userFormTypeName) {
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeById(userFormTypeId);
		userFormType.setUserFormType(userFormTypeName);
		service.getUserFormService().updateUserFormType(userFormType);
	}

	@Override
	public List<ElementClient> getAllElements() {
		List<Element> elementList = service.getEncounterService()
				.getAllElements();
		List<ElementClient> elementClientList = new ArrayList<ElementClient>();
		for (Element item : elementList) {
			elementClientList
					.add(converter.convertElementToElementClient(item));
		}
		return elementClientList;
	}

	@Override
	public ElementClient getElementByName(String elementName) {
		Element element = service.getEncounterService().getElementByName(
				elementName);
		return converter.convertElementToElementClient(element);
	}

	@Override
	public ElementClient saveElement(ElementClient elementClient) {
		service.getEncounterService().saveElement(
				converter.convertElementClientToElement(elementClient));
		Element element = service.getEncounterService().getElementByName(
				elementClient.getElementName());
		return converter.convertElementToElementClient(element);
	}

	@Override
	public void updateElement(ElementClient elementClient) {
		Element element = service.getEncounterService().getElementById(
				elementClient.getElementId());
		element.setElementName(elementClient.getElementName());
		element.setDataType(elementClient.getDataType());
		element.setDescription(elementClient.getDescription());
		element.setValidationRegex(elementClient.getValidationRegex());
		service.getEncounterService().updateElement(element);
	}

	@Override
	public void updateEncounterType(int encounterTypeId,
			String encounterTypeName) {
		EncounterType encounterType = service.getEncounterService()
				.getEncounterTypeById(encounterTypeId);
		encounterType.setEncounterType(encounterTypeName);
		service.getEncounterService().updateEncounterType(encounterType);
	}

	@Override
	public LocationClient saveLocation(LocationClient locationClient,
			String parentLocationString) {
		Location location = new Location();
		location.setLocationName(locationClient.getLocationName());
		location.setCategory(locationClient.getCategory());
		location.setDescription(locationClient.getDescription());
		location.setAddress1(locationClient.getAddress1());
		location.setAddress2(locationClient.getAddress2());
		location.setAddress3(locationClient.getAddress3());
		location.setCityVillage(locationClient.getCityVillage());
		location.setStateProvince(locationClient.getStateProvince());
		location.setCountry(locationClient.getCountry());
		location.setLandmark1(locationClient.getLandmark1());
		location.setLandmark2(locationClient.getLandmark2());
		location.setLatitude(locationClient.getLatitude());
		location.setLongitude(locationClient.getLongitude());
		location.setPrimaryContact(locationClient.getPrimaryContact());
		location.setSecondaryContact(locationClient.getSecondaryContact());
		location.setEmail(locationClient.getEmail());
		location.setFax(locationClient.getFax());
		Location parentLocation = service.getLocationService()
				.getLocationByName(parentLocationString);
		location.setLocationByParentLocation(parentLocation);
		service.getLocationService().saveLocation(location);
		Location returnLocation = service.getLocationService()
				.getLocationByName(locationClient.getLocationName());
		return converter.LocationToLocationClient(returnLocation);
	}

	@Override
	public void deleteLocation(int locationId) {
		Location location = service.getLocationService().getLocationById(
				locationId);
		service.getLocationService().deleteLocation(location);
	}

	@Override
	public void updateLocation(LocationClient locationClient,
			String parentLocationString) {
		Location location = service.getLocationService().getLocationById(
				locationClient.getLocationId());
		location.setLocationName(locationClient.getLocationName());
		location.setCategory(locationClient.getCategory());
		location.setDescription(locationClient.getDescription());
		location.setAddress1(locationClient.getAddress1());
		location.setAddress2(locationClient.getAddress2());
		location.setAddress3(locationClient.getAddress3());
		location.setCityVillage(locationClient.getCityVillage());
		location.setStateProvince(locationClient.getStateProvince());
		location.setCountry(locationClient.getCountry());
		location.setLandmark1(locationClient.getLandmark1());
		location.setLandmark2(locationClient.getLandmark2());
		location.setLatitude(locationClient.getLatitude());
		location.setLongitude(locationClient.getLongitude());
		location.setPrimaryContact(locationClient.getPrimaryContact());
		location.setSecondaryContact(locationClient.getSecondaryContact());
		location.setEmail(locationClient.getEmail());
		location.setFax(locationClient.getFax());
		Location parentLocation = service.getLocationService()
				.getLocationByName(parentLocationString);
		location.setLocationByParentLocation(parentLocation);
		service.getLocationService().updateLocation(location);
	}

	@Override
	public List<LocationAttributeTypeClient> getAllLocationAttribute() {
		List<LocationAttributeType> locationAttributeList = service
				.getLocationService().getAllLocationAttributeTypes();
		List<LocationAttributeTypeClient> locationAttributeClientList = new ArrayList<LocationAttributeTypeClient>();
		for (LocationAttributeType item : locationAttributeList) {
			locationAttributeClientList
					.add(converter
							.convertLocationAttributeTypetoLocationAttributeTypeClient(item));
		}
		return locationAttributeClientList;
	}

	@Override
	public void saveLocationAttribute(String attributeName,
			String attributeValue, LocationClient location) {
		LocationAttributeType LocationAttributeType = service
				.getLocationService().getLocationAttributeTypeByName(
						attributeName);
		LocationAttribute locationAttribute = new LocationAttribute(
				LocationAttributeType,
				converter.LocationClientToLocation(location), attributeValue);
		try {
			service.getLocationService().saveLocationAttribute(
					locationAttribute);
		} catch (PatternSyntaxException | HibernateException
				| javax.validation.ValidationException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<LocationAttributeClient> getLocationAttribute(
			LocationClient location) {
		List<LocationAttributeClient> locationAttributeClient = new ArrayList<LocationAttributeClient>();
		List<LocationAttribute> locationAttribute = service
				.getLocationService().getLocationAttributesByLocation(
						converter.LocationClientToLocation(location));
		for (LocationAttribute item : locationAttribute) {
			locationAttributeClient.add(converter
					.convertLocationAttributeToLocationAttributeClient(item));
		}
		return locationAttributeClient;
	}

	@Override
	public void deleteAllLocationAttributebyLocation(LocationClient location) {
		Location locationObject = converter.LocationClientToLocation(location);
		List<LocationAttribute> attributeList = service.getLocationService()
				.getLocationAttributesByLocation(locationObject);
		for (LocationAttribute item : attributeList) {
			service.getLocationService().deleteLocationAttribute(item);
		}
	}

	@Override
	public List<LocationClient> getLocationByCountry(String countryName) {
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		List<LocationClient> allLocations = getAllLocations();
		for (LocationClient item : allLocations) {
			String country = item.getCountry().toLowerCase();
			if (country.equals(countryName)) {
				returnList.add(item);
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByParentLocation(String parentCountry) {
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		List<LocationClient> allLocations = getAllLocations();
		for (LocationClient item : allLocations) {
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (parent.equals(parentCountry)) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByNameAndCountry(
			String locationName, String countryName) {
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		List<LocationClient> locationList = filterLocationByLocationName(locationName);
		for (LocationClient item : locationList) {
			String country = item.getCountry().toLowerCase();
			if (country.equals(countryName)) {
				returnList.add(item);
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByNameOrCountry(String locationName,
			String countryName) {
		List<LocationClient> locationList = new ArrayList<LocationClient>();
		List<LocationClient> returnList = getAllLocations();
		for (LocationClient item : returnList) {
			String location = item.getLocationName().toLowerCase();
			String country = item.getCountry().toLowerCase();
			if (location.contains(locationName) || country.equals(countryName)) {
				locationList.add(item);
			}
		}
		return locationList;
	}

	@Override
	public List<LocationClient> getLocationByNameAndParent(String locationName,
			String parentName) {
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		List<LocationClient> locationList = filterLocationByLocationName(locationName);
		for (LocationClient item : locationList) {
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (parent.equals(parentName)) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByNameOrParent(String locationName,
			String parentName) {
		List<LocationClient> locationList = new ArrayList<LocationClient>();
		List<LocationClient> returnList = getAllLocations();
		for (LocationClient item : returnList) {
			String location = item.getLocationName().toLowerCase();
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (location.contains(locationName)
						|| parent.equals(parentName)) {
					locationList.add(item);
				}
			} else {
				if (location.contains(locationName)) {
					locationList.add(item);
				}
			}
		}
		return locationList;
	}

	@Override
	public List<LocationClient> getLocationByCountryAndParent(
			String countryName, String parentName) {
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		List<LocationClient> locationList = getLocationByCountry(countryName);
		for (LocationClient item : locationList) {
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (parent.equals(parentName)) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByCountryOrParent(
			String countryName, String parentName) {
		List<LocationClient> locationList = new ArrayList<LocationClient>();
		List<LocationClient> returnList = getAllLocations();
		for (LocationClient item : returnList) {
			String country = item.getCountry().toLowerCase();
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (country.equals(countryName) || parent.equals(parentName)) {
					locationList.add(item);
				}

			} else {
				if (country.equals(countryName)) {
					locationList.add(item);
				}
			}

		}
		return locationList;
	}

	@Override
	public List<LocationClient> getLocationByNameAndCountryAndParent(
			String locationName, String countryName, String parentName) {
		List<LocationClient> locationList = getLocationByNameAndCountry(
				locationName, countryName);
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		for (LocationClient item : locationList) {
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (parent.equals(parentName)) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByNameOrCountryAndParent(
			String locationName, String countryName, String parentName) {
		List<LocationClient> locationList = getLocationByNameOrCountry(
				locationName, countryName);
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		for (LocationClient item : locationList) {
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (parent.equals(parentName)) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	@Override
	public List<LocationClient> getLocationByNameOrCountryOrParent(
			String locationName, String countryName, String parentName) {
		List<LocationClient> locationList = new ArrayList<LocationClient>();
		List<LocationClient> returnList = getAllLocations();
		for (LocationClient item : returnList) {
			String location = item.getLocationName().toLowerCase();
			String country = item.getCountry().toLowerCase();
			if (item.getLocationByParentLocation() != null) {
				String parent = item.getLocationByParentLocation()
						.getLocationName().toLowerCase();
				if (location.contains(locationName)
						|| parent.equals(parentName)
						|| country.equals(countryName)) {
					locationList.add(item);
				}
			} else {
				if (location.contains(locationName)
						|| country.equals(countryName)) {
					locationList.add(item);
				}
			}
		}
		return locationList;
	}

	@Override
	public List<LocationClient> getLocationByNameAndCountryorParent(
			String locationName, String countryName, String parentName) {

		List<LocationClient> locationList = getLocationByCountryOrParent(
				countryName, parentName);
		List<LocationClient> returnList = new ArrayList<LocationClient>();
		for (LocationClient item : locationList) {
			String name = item.getLocationName().toLowerCase();
			if (name.contains(locationName)) {
				returnList.add(item);
			}
		}
		return returnList;
	}

	@Override
	public List<UserFormTypeClient> getAllUserFormType() {
		List<UserFormType> userFormTypeList = service.getUserFormService()
				.getAllUserFormTypes();
		List<UserFormTypeClient> userFormTypeClient = new ArrayList<UserFormTypeClient>();
		for (UserFormType item : userFormTypeList) {
			userFormTypeClient.add(converter
					.convertUserFormTypetoUserFormTypeClient(item));
		}
		return userFormTypeClient;
	}

	@Override
	public int saveUserForm(String username, String userFormTypeName,
			String dateEntered) {
		Users user = service.getUserService().getUserByUsername(username);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeName);
		String[] splitArray = dateEntered.split(" ");
		String finalString = new String();
		finalString = finalString + (splitArray[0] + "-");
		switch (splitArray[1]) {
		case "January":
			finalString = finalString + ("01-");
			break;
		case "February":
			finalString = finalString + ("02-");
			break;
		case "March":
			finalString = finalString + ("03-");
			break;
		case "April":
			finalString = finalString + ("04-");
			break;
		case "May":
			finalString = finalString + ("05-");
			break;
		case "June":
			finalString = finalString + ("06-");
			break;
		case "July":
			finalString = finalString + ("07-");
			break;
		case "August":
			finalString = finalString + ("08-");
			break;
		case "September":
			finalString = finalString + ("09-");
			break;
		case "October":
			finalString = finalString + ("10-");
			break;
		case "November":
			finalString = finalString + ("11-");
			break;
		case "December":
			finalString = finalString + ("12-");
			break;
		default:
			break;
		}
		finalString = finalString + (splitArray[2]);
		Date sqlDate = java.sql.Date.valueOf(finalString);
		UserForm userForm = new UserForm(userFormType, user, sqlDate, null,
				null, null, null);
		return service.getUserFormService().saveUserForm(userForm)
				.getUserFormId();
	}

	@Override
	public void saveUserFormResult(int userFormId, String elementString,
			String value) {
		UserForm userForm = service.getUserFormService().getUserFormById(
				userFormId);
		Element element = service.getEncounterService().getElementByName(
				elementString);
		UserFormResult userFormResult = new UserFormResult(element, value,
				userForm);
		try {
			service.getUserFormService().saveUserFormResult(userFormResult);
		} catch (PatternSyntaxException | HibernateException
				| ValidationException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserFormClient getUserFormById(int id) {
		UserForm userForm = service.getUserFormService().getUserFormById(id);
		if (userForm != null)
			return converter.convertUserFormtoUserFormClient(userForm);
		return null;
	}

	@Override
	public List<UserFormClient> getUserFormByUser(String userName) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userForm = service.getUserFormService()
				.getUserFormsByUser(user);
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (UserForm item : userForm) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}

	@Override
	public List<UserFormClient> getUserFormByUserFormType(
			String userFormTypeString) {
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userForm = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (UserForm item : userForm) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}

	@Override
	public List<UserFormResultClient> getUserFormResultByUserForm(
			UserFormClient userForm) {
		List<UserFormResult> userFormResult = service.getUserFormService()
				.getUserFormResultsByUserForm(
						converter.convertUserFormClientToUserForm(userForm));
		List<UserFormResultClient> userFormResultClient = new ArrayList<UserFormResultClient>();

		for (UserFormResult item : userFormResult) {
			userFormResultClient.add(converter
					.convertUserFormResultToUserFormClient(item));
		}
		return userFormResultClient;
	}

	@Override
	public UserFormClient updateUserForm(int userFormId, String userName,
			String userFormTypeString, String dateEntered) {
		UserForm userForm = service.getUserFormService().getUserFormById(
				userFormId);
		Users user = service.getUserService().getUserByUsername(userName);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		userForm.setUserId(user);
		userForm.setUserFormType(userFormType);
		String[] splitArray = dateEntered.split(" ");
		String finalString = new String();
		finalString = finalString + (splitArray[0] + "-");
		switch (splitArray[1]) {
		case "January":
			finalString = finalString + ("01-");
			break;
		case "February":
			finalString = finalString + ("02-");
			break;
		case "March":
			finalString = finalString + ("03-");
			break;
		case "April":
			finalString = finalString + ("04-");
			break;
		case "May":
			finalString = finalString + ("05-");
			break;
		case "June":
			finalString = finalString + ("06-");
			break;
		case "July":
			finalString = finalString + ("07-");
			break;
		case "August":
			finalString = finalString + ("08-");
			break;
		case "September":
			finalString = finalString + ("09-");
			break;
		case "October":
			finalString = finalString + ("10-");
			break;
		case "November":
			finalString = finalString + ("11-");
			break;
		case "December":
			finalString = finalString + ("12-");
			break;
		default:
			break;
		}
		finalString = finalString + (splitArray[2]);
		Date sqlDate = java.sql.Date.valueOf(finalString);
		userForm.setDateEntered(sqlDate);
		service.getUserFormService().updateUserForm(userForm);
		userForm = service.getUserFormService().getUserFormById(userFormId);
		return converter.convertUserFormtoUserFormClient(userForm);
	}

	@Override
	public void deleteAllUserFormResulltByUserForm(UserFormClient userForm) {
		List<UserFormResult> userFormResult = service.getUserFormService()
				.getUserFormResultsByUserForm(
						converter.convertUserFormClientToUserForm(userForm));
		for (UserFormResult item : userFormResult) {
			service.getUserFormService().deleteUserFormResult(item);
		}
	}

	@Override
	public void deleteUserForm(UserFormClient userFormClient) {
		service.getUserFormService().deleteUserForm(
				converter.convertUserFormClientToUserForm(userFormClient),
				false);
	}

	@Override
	public UserFormClient getUserFormByIDAndUser(int userFormId, String userName) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormClient userFormClient = new UserFormClient();
		for (UserForm item : userFormByUser) {
			if (item.getUserFormId().equals(userFormId)) {
				userFormClient = converter
						.convertUserFormtoUserFormClient(item);
				return userFormClient;
			}
		}
		return null;
	}

	@Override
	public List<UserFormClient> getUserFormByIDorUser(int userFormId,
			String userName) {

		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserForm userFormById = service.getUserFormService().getUserFormById(
				userFormId);
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		boolean check = false;
		for (UserForm item : userFormByUser) {
			if (item.getUserFormId().equals(userFormById)) {
				check = true;
				userFormClient.add(converter
						.convertUserFormtoUserFormClient(item));
			} else {
				userFormClient.add(converter
						.convertUserFormtoUserFormClient(item));
			}
		}
		if (check == false) {
			userFormClient.add(converter
					.convertUserFormtoUserFormClient(userFormById));
		}
		return userFormClient;
	}

	@Override
	public UserFormClient getUserFormByIDAndUserAndUserFormType(int userFormId,
			String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserForm> userFormUserAndUserFormType = new ArrayList<UserForm>();
		for (int i = 0; i < userFormByUser.size(); i++) {
			for (int j = 0; j < userFormByUserFormType.size(); j++) {
				if (userFormByUser.get(i).getUserFormId()
						.equals(userFormByUserFormType.get(j).getUserFormId())) {
					userFormUserAndUserFormType.add(userFormByUser.get(i));
				}
			}
		}
		for (UserForm item : userFormUserAndUserFormType) {
			if (item.getUserFormId().equals(userFormId)) {
				return converter.convertUserFormtoUserFormClient(item);
			}
		}
		return null;
	}

	@Override
	public UserFormClient getUserFormByIDAndUserOrUserFormType(int userFormId,
			String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserForm> userFormByUserOrUserFormType = new ArrayList<UserForm>();
		for (UserForm item : userFormByUser) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}

		for (UserForm item : userFormByUserFormType) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}

		for (UserForm item : userFormByUserOrUserFormType) {
			if (item.getUserFormId().equals(userFormId)) {
				return converter.convertUserFormtoUserFormClient(item);
			}
		}
		return null;
	}

	@Override
	public List<UserFormClient> getUserFormByIDOrUserAndUserFormType(
			int userFormId, String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserForm> userFormUserAndUserFormType = new ArrayList<UserForm>();
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (int i = 0; i < userFormByUser.size(); i++) {
			for (int j = 0; j < userFormByUserFormType.size(); j++) {
				if (userFormByUser.get(i).getUserFormId()
						.equals(userFormByUserFormType.get(j).getUserFormId())) {
					userFormUserAndUserFormType.add(userFormByUser.get(i));
				}
			}
		}
		UserForm userFormByUserFormId = service.getUserFormService()
				.getUserFormById(userFormId);

		if (!userFormUserAndUserFormType.contains(userFormByUserFormId)) {
			userFormUserAndUserFormType.add(userFormByUserFormId);
		}
		for (UserForm item : userFormUserAndUserFormType) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}

	@Override
	public List<UserFormClient> getUserFormByIDOrUserOrUserFormType(
			int userFormId, String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserForm> userFormByUserOrUserFormType = new ArrayList<UserForm>();
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (UserForm item : userFormByUser) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}

		for (UserForm item : userFormByUserFormType) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}
		UserForm userFormByUserFormId = service.getUserFormService()
				.getUserFormById(userFormId);

		if (!userFormByUserOrUserFormType.contains(userFormByUserFormId)) {
			userFormByUserOrUserFormType.add(userFormByUserFormId);
		}
		for (UserForm item : userFormByUserOrUserFormType) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;

	}

	@Override
	public List<UserFormClient> getUserFormByUserAndUserFormType(
			String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		List<UserForm> userFormUserAndUserFormType = new ArrayList<UserForm>();

		for (int i = 0; i < userFormByUser.size(); i++) {
			for (int j = 0; j < userFormByUserFormType.size(); j++) {
				if (userFormByUser.get(i).getUserFormId()
						.equals(userFormByUserFormType.get(j).getUserFormId())) {
					userFormUserAndUserFormType.add(userFormByUser.get(i));
				}
			}
		}

		for (UserForm item : userFormUserAndUserFormType) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}

	@Override
	public List<UserFormClient> getUserFormByUserOrUserFormType(
			String userName, String userFormTypeString) {
		Users user = service.getUserService().getUserByUsername(userName);
		List<UserForm> userFormByUser = service.getUserFormService()
				.getUserFormsByUser(user);
		UserFormType userFormType = service.getUserFormService()
				.getUserFormTypeByName(userFormTypeString);
		List<UserForm> userFormByUserFormType = service.getUserFormService()
				.getUserFormsByType(userFormType);
		List<UserForm> userFormByUserOrUserFormType = new ArrayList<UserForm>();
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (UserForm item : userFormByUser) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}

		for (UserForm item : userFormByUserFormType) {
			if (!userFormByUserOrUserFormType.contains(item)) {
				userFormByUserOrUserFormType.add(item);
			}
		}
		for (UserForm item : userFormByUserOrUserFormType) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}

	@Override
	public List<UserFormClient> getLatestUserForms() {
		List<UserForm> userForm = service.getUserFormService()
				.getLatestUserForms(10);
		List<UserFormClient> userFormClient = new ArrayList<UserFormClient>();
		for (UserForm item : userForm) {
			userFormClient.add(converter.convertUserFormtoUserFormClient(item));
		}
		return userFormClient;
	}
}
