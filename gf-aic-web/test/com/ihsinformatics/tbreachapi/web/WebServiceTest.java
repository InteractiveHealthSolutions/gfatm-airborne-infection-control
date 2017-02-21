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
package com.ihsinformatics.tbreachapi.web;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ihsinformatics.gfaicweb.WebService;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
public class WebServiceTest {

	static WebService service;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		service = new WebService();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for Login functionality. Takes form type, username, password
	 * as a queryString and authenticate login operation against provided
	 * credentials.
	 */
	@Test
	public final void testLoginRequest() {
		JSONObject params = new JSONObject();
		try {
			params.put("type", "login");
			params.put("username", "admin");
			params.put("password", "Admin123");
			params.put("location", "IHS");
			params.put("entereddate", "20160829");
		
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String queryString = "?params=";
		String response = "";
		queryString = queryString + params;
		System.out.println(queryString);
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice"
					+ queryString);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("GET parameters : " + queryString);
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}

			assertTrue("Login failed.", response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}

	/**
	 * Test method for Login functionality. Takes form type, username, password
	 * as a queryString and authenticate login operation against provided
	 * credentials.
	 */
	@Test
	public final void shouldNotLogin() {
		JSONObject params = new JSONObject();
		try {
			params.put("type", "login");
			params.put("username", "admin123");
			params.put("password", "Admin123");
			params.put("location", "IHS");
			params.put("entereddate", "20160829");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String queryString = "?params=";
		String response = "";
		queryString = queryString + params;
		System.out.println(queryString);
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice"
					+ queryString);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("GET parameters : " + queryString);
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}
			assertTrue("Login successful on invalid credentials.",
					!response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}

	/**
	 * Test method for Installation form. elementArray contains keys for
	 * queryString while valueArray contains value for each value. Passes query
	 * string to Output stream and set request type to JSON. In response if form
	 * completely executed returned with a success response message.
	 */
	@Test
	public final void testInstallationRequest() {
		StringBuilder query = new StringBuilder();
		query.append("params=");
		JSONObject params = new JSONObject();
		try {
			params.put("type", "uvgi_installation");
			params.put("username", "admin");
			params.put("password", "Admin123");
			params.put("location", "IRD");
			params.put("entereddate", "20160829");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray resultsArray = new JSONArray();
		String[] elements = { "starttime", "uvgi_install_location", "opd",
				"opd_area", "fixture_number", "uv_read_3ft", "uv_read_6ft",
				"uv_read_7ft" };
		String[] values = { "2016-08-30 14:20:00", "Dehli Hospital", "DIAB",
				"WA", "16", "0.2", "0.3", "0.35" };
		for (int i = 0; i < elements.length; i++) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("name", elements[i]);
				obj.put("value", values[i]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			resultsArray.put(obj);
		}
		try {
			params.put("results", resultsArray);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		query.append(params);
		String response = "";
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			DataOutputStream wr = new DataOutputStream(
					httpConnection.getOutputStream());
			wr.writeBytes(query.toString());
			wr.flush();
			wr.close();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + query);
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}
			assertTrue("Installation Form failed.", response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}

	/**
	 * Test method for Maintenance form.elementArray contains keys for
	 * queryString while valueArray contains value for each value. Passes query
	 * string to Output stream and set request type to JSON. In response if form
	 * completely executed returned with a success response message.
	 */
	@Test
	public final void testMaintenanceRequest() {
		StringBuilder query = new StringBuilder();
		query.append("params=");
		JSONObject params = new JSONObject();
		try {
			params.put("type", "uvgi_maintenance");
			params.put("username", "admin");
			params.put("password", "Admin123");
			params.put("location", "IHS");
			params.put("entereddate", "20160829");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONArray resultsArray = new JSONArray();
		String[] elements = { "starttime", "uv_read_3ft_before_clean",
				"uv_read_6ft_before_clean", "uv_read_7ft_before_clean",
				"power_disconnected", "louver_opened", "lamps_removed",
				"lamps_cleaned_with_microfiber_alcohol",
				"interior_cleaned_with_microfiber_alcohol", "lamps_replaced",
				"uv_part_replaced", "lamps_reinstalled", "louver_closed",
				"power_connected", "uv_light_working",
				"uv_read_3ft_after_clean", "uv_read_6ft_after_clean",
				"uv_read_7ft_after_clean" };
		String[] values = { "2016-08-30 14:20:00", "0.36", "0.25", "0.37", "Y",
				"Y", "N", "Y", "Y", "N", "Y", "N", "Y", "N", "Y", "0.25",
				"0.74", "0.68" };
		
		try {
			for (int i = 0; i < elements.length; i++) {
				JSONObject obj = new JSONObject();
				
					obj.put("name", elements[i]);
					obj.put("value", values[i]);
					resultsArray.put(obj);
			}
			params.put("results", resultsArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query.append(params);
		String response = "";
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			DataOutputStream wr = new DataOutputStream(
					httpConnection.getOutputStream());
			wr.writeBytes(query.toString());
			wr.flush();
			wr.close();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + query.toString());
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}
			assertTrue("Maintenance Form failed.", response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}

	/**
	 * Test method for TroubleShooting form.elementArray contains keys for
	 * queryString while valueArray contains value for each value. Passes query
	 * string to Output stream and set request type to JSON. In response if form
	 * completely executed returned with a success response message.
	 */
	@Test
	public final void testTroubleshootingRequest() {
		StringBuilder query = new StringBuilder();
		query.append("params=");
		JSONObject params = new JSONObject();
		try {
			params.put("type", "uvgi_troubleshooting");
			params.put("username", "admin");
			params.put("password", "Admin123");
			params.put("location", "IHS");
			params.put("entereddate", "20160829");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray resultsArray = new JSONArray();
		String[] elements = { "starttime", "uvgi_install_location", "id",
				"problem", "mobile_number", "troubleshoot_number" };
		String[] values = { "2016-08-30 14:20:00", "Hussain Hospital",
				"A1A68S4DF86A4SDF8",
				"UVGI ligts are not working properly in waiting area section.",
				"009233433221771457", "054" };
		try {
			for (int i = 0; i < elements.length; i++) {
				JSONObject obj = new JSONObject();
				obj.put("name", elements[i]);
				obj.put("value", values[i]);
				resultsArray.put(obj);
			}
			params.put("results", resultsArray);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		query.append(params);
		String response = "";
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			DataOutputStream wr = new DataOutputStream(
					httpConnection.getOutputStream());
			wr.writeBytes(query.toString());
			wr.flush();
			wr.close();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + query.toString());
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}
			assertTrue("Installation Form failed.", response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}

	/**
	 * 
	 * Test method for TroubleShooting Resolution form.elements contains keys
	 * for queryString while values contains value for each value. Passes query
	 * string to Output stream and set request type to JSON. In response if form
	 * completely executed returned with a success response message.
	 * 
	 **/
	@Test
	public final void testResolutionRequest() {
		StringBuilder query = new StringBuilder();
		query.append("params=");
		JSONObject params = new JSONObject();
		try {
			params.put("type", "uvgi_resolution");
			params.put("username", "admin");
			params.put("password", "Admin123");
			params.put("location", "IHS");
			params.put("entereddate", "20160829");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray resultsArray = new JSONArray();
		String[] elements = { "starttime", "uvgi_install_location", "id",
				"problem_resolved", "reason_problem_unresolved", "problem",
				"troubleshooter_name" };
		String[] values = { "2016-08-30 14:20:00", "Kareem Hospital",
				"AG6ASDF65654DS5F6", "N",
				"Lack of resources available to resolve problem",
				"UVGI ligts are not working properly in waiting area section",
				"Shahmeer Hassan" };
		try {
			for (int i = 0; i < elements.length; i++) {
				JSONObject obj = new JSONObject();
				
					obj.put("name", elements[i]);
				
				obj.put("value", values[i]);
				resultsArray.put(obj);
			}
			params.put("results", resultsArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query.append(params);
		String response = "";
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://localhost:8080/gf-aic-web/webservice");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			DataOutputStream wr = new DataOutputStream(
					httpConnection.getOutputStream());
			wr.writeBytes(query.toString());
			wr.flush();
			wr.close();
			responseCode = httpConnection.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + query.toString());
			System.out.println("Response Code : " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						httpConnection.getInputStream()));
				String inputLine;
				StringBuffer responseBuffer = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					responseBuffer.append(inputLine);
				}
				in.close();
				System.out.println(responseBuffer.toString());
				JSONObject jsonObj = new JSONObject(responseBuffer.toString());
				response = jsonObj.getString("response");
			} else {
				response = "ERROR";
			}
			assertTrue("Resolution Form failed.", response.equals("SUCCESS"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
	}
}
