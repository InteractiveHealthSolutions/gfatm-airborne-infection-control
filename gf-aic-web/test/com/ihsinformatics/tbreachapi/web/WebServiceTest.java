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

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
	 * Test method for {@link com.ihsinformatics.gfaicweb.WebService#getRequest()}.
	 */
	@Test
	public final void testLoginRequest() {
		String queryString = "content={\"\"type\":\"login\",\"username\":\"admin\",\"password\":\"Admin123\",\"starttime\":\"1469163490\"}\"";
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		int responseCode = 0;
		URL url;
		try {
			url = new URL("http://127.0.0.1:8080/tbreach-web/tbreachweb.jsp");
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("Content-Type", "application/json");
			httpConnection.setRequestProperty("Content-Language", "en-US");
			httpConnection.setDoOutput(true);
			outputStream = httpConnection.getOutputStream();
			outputStream.write(queryString.getBytes());
			outputStream.flush();
			responseCode = httpConnection.getResponseCode();
			outputStream.close();
			httpConnection.disconnect();
			assertTrue("Login failed.", responseCode == HttpURLConnection.HTTP_OK);
			BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
