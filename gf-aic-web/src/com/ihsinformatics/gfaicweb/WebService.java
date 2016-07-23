/* Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

/**
 * 
 */
package com.ihsinformatics.gfaicweb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.ihsinformatics.tbreachapi.core.TBR;
import com.ihsinformatics.tbreachapi.core.model.Element;
import com.ihsinformatics.tbreachapi.core.model.Location;
import com.ihsinformatics.tbreachapi.core.model.UserForm;
import com.ihsinformatics.tbreachapi.core.model.UserFormResult;
import com.ihsinformatics.tbreachapi.core.model.UserFormType;
import com.ihsinformatics.tbreachapi.core.model.Users;
import com.ihsinformatics.tbreachapi.core.service.impl.ServerService;
import com.ihsinformatics.util.DateTimeUtil;
import com.ihsinformatics.util.JsonUtil;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class WebService extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -823823726983678814L;
	private HttpServletRequest request;
	private static ServerService apiService = new ServerService();
	private String contentType = "application/json";

	public WebService() {
		if (!ServerService.isRunning()) {
			TBR.readProperties("tbreach-api.properties");
			apiService.startup();
		}
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * Handles HTTP requests from client-side
	 * 
	 * @param request
	 * @return
	 */
	public String handleRequest(final HttpServletRequest request) {
		contentType = request.getContentType();
		setRequest(request);
		String response = null;
		/* TODO: Try to match version if present */
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Parse parameters according to the content type
		Map<String, Object> params = parseParameters(contentType);
		// Mandatory fields
		String[] mandatoryParams = { "type", "username", "starttime" };
		for (String param : mandatoryParams) {
			if (!params.containsKey(param)) {
				response = "ERROR: Parameter " + param + " must be provided.";
				return response;
			}
		}
		String requestType = params.get("type").toString();
		if (requestType.equals(RequestType.LOGIN)) {
			String username = params.get("username").toString();
			String password = params.get("password").toString();
			response = doLogin(username, password);
		} else if (requestType.equals(RequestType.UVGI_INSTALL)) {
			response = doUvgiInstallation(params);
		} else {
			response = "ERROR: Unknown request type";
		}
		return response;
	}

	/**
	 * Generates response in the same format as content-type
	 * 
	 * @param errorMessage
	 * @return
	 */
	protected String getError(String errorMessage) {
		return null;
	}

	/**
	 * This method parses parameters from HTTP request according to the
	 * content-type set in client request
	 * 
	 * @param contentType
	 * @return Map<String, Object>
	 */
	public Map<String, Object> parseParameters(String contentType) {
		Map<String, Object> params = new HashMap<>();
		Enumeration<String> parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String name = parameters.nextElement();
			System.out.println(name);
		}
		try {
			// JSON text should be like
			// content={type:login,username:admin,password:admin123,starttime:1469163427,endtime:1469163490}
			String content = request.getParameter("content");
			JSONObject jsonObject = JsonUtil.getJSONObject(content);
			Iterator<?> iterator = jsonObject.keys();
			while (iterator.hasNext()) {
				String name = iterator.next().toString();
				String value = jsonObject.getString(name);
				params.put(name, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/* Web service methods */
	public String doLogin(String username, String password) {
		String response = "ERROR: Unable to authenticate. Please provide a valid username and password.";
		if (apiService.getAuthenticationService().authenticate(username,
				password)) {
			response = "SUCCESS";
		}
		return response;
	}

	private String doUvgiInstallation(Map<String, Object> params) {
		String response = null;
		String userFormTypeName = params.get("type").toString().toUpperCase();
		String username = params.get("username").toString();
		String location = params.get("location").toString();
		Date dateEntered;
		try {
			dateEntered = DateTimeUtil.getDateFromString(params.get("entereddate").toString(), "yyyyMMdd");
		} catch (ParseException e) {
			e.printStackTrace();
			response = "ERROR: Invalid value for Entered Date";
			return response;
		}
		String opdValue = params.get("opd").toString().toUpperCase();

		UserFormType userFormType = apiService.getUserFormService().getUserFormTypeByName(userFormTypeName);
		Users user = apiService.getUserService().getUserByUsername(username);
		Location createdAt = apiService.getLocationService().getLocationByName(location);
		
		UserForm userForm = new UserForm(userFormType, user, dateEntered, user, createdAt, new Date(), null);
		List<UserFormResult> userFormResults = new ArrayList<UserFormResult>();

		Element opd = apiService.getEncounterService().getElementByName("OPD");
		
		userFormResults.add(new UserFormResult(opd, opdValue, userForm));
		userForm = apiService.getUserFormService().saveUserForm(userForm, userFormResults);
		if (userForm.getUserFormId() != null) {
			response = "SUCCESS";
		} else {
			response = "ERROR: Unable to submit form.";
		}
		return response;
	}
}
