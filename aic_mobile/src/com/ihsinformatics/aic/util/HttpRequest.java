/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * This class handles all HTTP calls
 */

package com.ihsinformatics.aic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

/**
 * @author owais.hussain@irdinformatics.org
 * 
 */
@SuppressWarnings("deprecation")
public class HttpRequest {
	private static final String TAG = "HttpRequest";
	private final Context context;
	HttpClient httpClient = new DefaultHttpClient();

	public HttpRequest(Context context) {
		this.context = context;
	}

	/**
	 * Makes HTTP GET call to server and returns the response. The method
	 * automatically appends authentication header using App.getUsername() and
	 * App.getPassword() methods.
	 * 
	 * @param requestUri
	 *            fully qualified URI, e.g.
	 *            https://myserver:port/ws/rest/v1/concept
	 * @return
	 */
	public String clientGet(String requestUri) {
		URL url;
		String response = "";
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(requestUri);

			urlConnection = (HttpURLConnection) url.openConnection();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			response = responseBuffer.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return "CONNECTION_ERROR";
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return response;
	}

	/**
	 * Makes a POST call to the server and returns the attached Entity in a
	 * String
	 * 
	 * @param postUri
	 * @param content
	 * @return
	 */
	public String clientPost(String postUri, String content) {
		HttpsClient client = new HttpsClient(context);
		HttpUriRequest request = null;
		HttpResponse response = null;
		HttpEntity entity;
		StringBuilder builder = new StringBuilder();
		String auth = "";
		try {

			/*
			 * Uncomment if you do not want to send data in Parameters HttpPost
			 * httpPost = new HttpPost (postUri); httpPost.setHeader ("Accept",
			 * "application/json"); httpPost.setHeader ("Content-Type",
			 * "application/json"); StringEntity stringEntity = new StringEntity
			 * (content); httpPost.setEntity (stringEntity); request = httpPost;
			 */

			HttpPost httpPost = new HttpPost(postUri);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-Type", "application/json");
			StringEntity stringEntity = new StringEntity(content);
			httpPost.setEntity(stringEntity);
			request = httpPost;
			response = client.execute(request);
			entity = response.getEntity();
			InputStream is = entity.getContent();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			builder = new StringBuilder();
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
				builder.append(line);
			entity.consumeContent();
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
			builder.append("UNSUPPORTED_ENCODING");
		} catch (ClientProtocolException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			builder.append("SERVER_NOT_RESPONDING");
		}
		return builder.toString();
	}

	public static String makeRequest(String uri, String json) {
		HttpURLConnection httpConnection = null;
		int responseCode = 0;
		String response = "";
		URL url;
		try {
			url = new URL(uri + "?params=" + json);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("GET");
			httpConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			httpConnection.setRequestProperty("Accept-Language",
					"en-US,en;q=0.5");
			httpConnection.setDoOutput(true);
			httpConnection.connect();
			responseCode = httpConnection.getResponseCode();

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
				response = jsonObj.toString();
			} else {
				response = "ERROR";
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
		return response.toString();
	}

	public static String makeRequests(String uri, String json) {
		try {

			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httppostreq = new HttpPost(uri);
			StringEntity se = new StringEntity(json);
			se.setContentType("application/json;charset=UTF-8");
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			httppostreq.setEntity(se);
			HttpResponse httpresponse = httpclient.execute(httppostreq);

			HttpEntity resultentity = httpresponse.getEntity();
			InputStream inputstream = resultentity.getContent();
			Header contentencoding = httpresponse
					.getFirstHeader("Content-Encoding");
			/*
			 * if(contentencoding != null &&
			 * contentencoding.getValue().equalsIgnoreCase("gzip")) {
			 * inputstream = new GZIPInputStream(inputstream); }
			 */
			String resultstring = convertStreamToString(inputstream);
			inputstream.close();
			return resultstring;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String convertStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		try {
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (Exception e) {
		}
		return total.toString();
	}
}
