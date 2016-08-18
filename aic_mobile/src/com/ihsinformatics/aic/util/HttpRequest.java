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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.ihsinformatics.aic.App;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

/**
 * @author owais.hussain@irdinformatics.org
 * 
 */
public class HttpRequest
{
	private static final String	TAG			= "HttpRequest";
	private final Context		context;
	HttpClient					httpClient	= new DefaultHttpClient ();

	public HttpRequest (Context context)
	{
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
	public String clientGet (String requestUri)
	{
		HttpsClient client = new HttpsClient (context);
		HttpUriRequest request = null;
		String response = "";
		String auth = "";
		try
		{
			request = new HttpGet (requestUri);
			auth = Base64.encodeToString ((App.getUsername () + ":" + App.getPassword ()).getBytes ("UTF-8"), Base64.NO_WRAP);
			request.addHeader ("Authorization", auth);
			response = client.request (request);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		catch (IllegalArgumentException e)
		{
			Log.e (TAG, e.getMessage ());
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
	public String clientPost (String postUri, String content)
	{
		HttpsClient client = new HttpsClient (context);
		HttpUriRequest request = null;
		HttpResponse response = null;
		HttpEntity entity;
		StringBuilder builder = new StringBuilder ();
		String auth = "";
		try
		{
			
			/* Uncomment if you do not want to send data in Parameters HttpPost
			 * httpPost = new HttpPost (postUri); httpPost.setHeader ("Accept",
			 * "application/json"); httpPost.setHeader ("Content-Type",
			 * "application/json"); StringEntity stringEntity = new StringEntity
			 * (content); httpPost.setEntity (stringEntity); request = httpPost;
			*/
			
			auth = Base64.encodeToString ((App.getUsername () + ":" + App.getPassword ()).getBytes ("UTF-8"), Base64.NO_WRAP);
			//String finalRequestString = URLEncoder.encode(json,"UTF-8");
			request = new HttpGet (postUri);
			request.addHeader ("Authorization", auth);
			response = client.execute (request);
			entity = response.getEntity ();
			InputStream is = entity.getContent ();
			BufferedReader bufferedReader = new BufferedReader (new InputStreamReader (is));
			builder = new StringBuilder ();
			String line = null;
			while ((line = bufferedReader.readLine ()) != null)
				builder.append (line);
			entity.consumeContent ();
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			builder.append ("UNSUPPORTED_ENCODING");
		}
		catch (ClientProtocolException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		catch (IOException e)
		{
			Log.e (TAG, e.getMessage ());
			builder.append ("SERVER_NOT_RESPONDING");
		}
		return builder.toString ();
	}
	
	public static String makeRequest(String uri, String json){
		HttpURLConnection httpConnection = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		int responseCode = 0;
		StringBuilder sb = null;
		URL url;
		try {
			url = new URL(uri);
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.setRequestProperty("Content-Language", "en-US");
			httpConnection.setDoOutput(true);
			outputStream = httpConnection.getOutputStream();
			outputStream.write(json.getBytes());
			outputStream.flush();
			outputStream.close();
			
			httpConnection.connect();
			
			responseCode = httpConnection.getResponseCode();
	         
	         if (responseCode == HttpURLConnection.HTTP_OK) 
	        	 inputStream = httpConnection.getInputStream();
	         else
	        	 inputStream = httpConnection.getErrorStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader((inputStream)));
			sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpConnection.disconnect();
		}
		return sb.toString();
	}
	
	public static String makeRequests(String uri, String json) {
	    try {

	    	DefaultHttpClient httpclient = new DefaultHttpClient();
	    	HttpPost httppostreq = new HttpPost(uri);
	    	StringEntity se = new StringEntity(json);
	    	se.setContentType("application/json;charset=UTF-8");
	    	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
	    	httppostreq.setEntity(se);
	    	HttpResponse httpresponse = httpclient.execute(httppostreq);
	    	
			HttpEntity resultentity = httpresponse.getEntity();
			InputStream inputstream = resultentity.getContent();
			Header contentencoding = httpresponse.getFirstHeader("Content-Encoding");
			/*if(contentencoding != null && contentencoding.getValue().equalsIgnoreCase("gzip")) {
				inputstream = new GZIPInputStream(inputstream);
			}*/
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
