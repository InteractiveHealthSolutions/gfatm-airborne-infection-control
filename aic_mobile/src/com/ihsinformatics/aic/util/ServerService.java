package com.ihsinformatics.aic.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ihsinformatics.aic.App;
import com.ihsinformatics.aic.R;
import com.ihsinformatics.aic.shared.Metadata;
import com.ihsinformatics.aic.shared.RequestType;

public class ServerService {

	private static final String TAG = "ServerService";
	private static DatabaseUtil dbUtil;
	private static String gfatmUri;
	private HttpRequest httpClient;
	private HttpsClient httpsClient;
	private Context context;

	public ServerService(Context context) {
		this.context = context;
		String prefix = "http" + (App.isUseSsl() ? "s" : "") + "://";
		gfatmUri = prefix + App.getServer() + "/gfatmweb/gfatmweb.jsp";
		// gfatmUri = prefix + App.getServer () + "/eIMCIweb";
		httpClient = new HttpRequest(this.context);
		httpsClient = new HttpsClient(this.context);
		dbUtil = new DatabaseUtil(this.context);
	}

	/**
	 * Checks to see if the client is connected to any network (GPRS/Wi-Fi)
	 * 
	 * @return status
	 */
	public boolean checkInternetConnection() {
		boolean status = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			status = true;
		}
		return status;
	}

	public String post(String json) {
		String response = null;
		try {
			if (App.isOfflineMode()) {
				JSONObject responseJson = new JSONObject();
				responseJson.put("result", "FAIL: Application Offline");
				return responseJson.toString();
			}

			response = httpClient.clientPost(gfatmUri, json);

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return response;
	}

	public String get(String uri) {
		String response = null;
		try {
			if (App.isOfflineMode()) {
				JSONObject responseJson = new JSONObject();
				responseJson.put("result", "FAIL: Application Offline");
				return responseJson.toString();
			}
			if (App.isUseSsl())
				response = httpsClient.clientGet(gfatmUri + "?params=" + uri);
			else
				response = httpClient.clientGet(gfatmUri + "?params=" + uri);
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return response;
	}

	public Boolean renewLoginStatus() {

		String lastTimeStamp = App.getLastLogin();

		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newTimeStamp = formatter.format(date);

		if (newTimeStamp.equals(lastTimeStamp)) {
			return false;
		}

		return true;
	}

	public String authenticate(String encounterType, ContentValues values) {
		String response = "";
		try {
			JSONObject json = new JSONObject();
			json.put("appver", App.getVersion());
			json.put("type", encounterType);
			json.put("username", values.getAsString("username"));
			json.put("password", values.getAsString("password"));
			json.put("starttime", values.getAsString("starttime"));
			json.put("location", values.getAsString("location"));
			json.put("entereddate", values.getAsString("entereddate"));
			String val = json.toString();
			response = get(val);

			if (!App.isJSONValid(response))
				return response;

			JSONObject jsonResponse = JsonUtil.getJSONObject(response);
			if (jsonResponse.has("response")) {
				String result = jsonResponse.getString("response");
				String detail = jsonResponse.getString("details");

				String updatedFlag = "true";
				if (result.equalsIgnoreCase("SUCCESS")) {

					dbUtil.delete(Metadata.METADATA_TABLE, "type = '"
							+ Metadata.PRIVILEGE + "'", null);

					if (jsonResponse.has("privilege_no")) {
						int privilegeSize = jsonResponse.getInt("privilege_no");
						for (int i = 1; i <= privilegeSize; i++) {

							values = new ContentValues();
							values.put("id", i);
							values.put("type", Metadata.PRIVILEGE);
							values.put("name",
									jsonResponse.getString("privilege_" + i));

							// If the user doesn't exist in DB, save it
							String id = dbUtil
									.getObject(
											Metadata.METADATA_TABLE,
											"id",
											"type='"
													+ Metadata.PRIVILEGE
													+ "' AND name='"
													+ jsonResponse
															.getString("privilege_"
																	+ i) + "'");
							if (id == null) {
								dbUtil.insert(Metadata.METADATA_TABLE, values);
							}

						}
					}

					int serverOpdSize = jsonResponse.getInt("opd_size");
					int serverAreaOpdSize = jsonResponse
							.getInt("opd_area_size");
					int serverLocSize = jsonResponse.getInt("loc_size");

					int localOpdSize = Integer.parseInt(dbUtil.getObject(
							Metadata.METADATA_TABLE, "count(*)", "type='"
									+ Metadata.OPD + "'"));
					int localOpdAreaSize = Integer.parseInt(dbUtil.getObject(
							Metadata.METADATA_TABLE, "count(*)", "type='"
									+ Metadata.OPD_AREA + "'"));
					int localLocSize = Integer.parseInt(dbUtil.getObject(
							Metadata.METADATA_TABLE, "count(*)", "type='"
									+ Metadata.LOCATION + "'"));

					if (serverOpdSize > localOpdSize
							|| serverAreaOpdSize > localOpdAreaSize
							|| serverLocSize > localLocSize)
						updatedFlag = "false";
				}

				return result + ":;:" + detail + ":;:" + updatedFlag;
			} else {
				return response;
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			response = context.getResources().getString(R.string.insert_error);
		}
		return response;
	}

	/**
	 * Save a form to local DB filled in offline
	 * 
	 * @param encounterType
	 * @param json
	 *            form content in json text (not encoded)
	 * @return
	 */
	public boolean saveOfflineForm(String encounterType, String json, String id) {
		ContentValues data = new ContentValues();
		Date date = new Date();
		data.put("username", App.getUsername());
		data.put("timestamp", date.getTime());
		data.put("form", encounterType);
		data.put("json", json);
		data.put("pid", id);
		return dbUtil.insert("offline_forms", data);
	}

	public String[][] getSavedForms(String username) {
		String[][] forms = dbUtil
				.getTableData("select timestamp, form, json, pid from offline_forms where username='"
						+ username + "'");
		return forms;
	}

	public boolean deleteSavedForm(Long timestamp) {
		return dbUtil.delete("offline_forms", "timestamp=?",
				new String[] { timestamp.toString() });
	}

	public String[] getSavedForm(String username, String timestamp) {
		String[] forms = dbUtil
				.getRecord("select timestamp, form, json from offline_forms where username='"
						+ username + "' and timestamp=" + timestamp);
		return forms;
	}

	public int countSavedForms(String username) {
		return Integer
				.parseInt(dbUtil
						.getObject("select count(*) from offline_forms where username='"
								+ username + "'"));
	}

	public String saveUVGIForm(String encounterType, ContentValues values,
			String[][] observations) {

		String response = "";

		String formDate = values.getAsString("entereddate");
		String location = values.getAsString("location");

		try {

			// Save Form
			JSONObject json = new JSONObject();
			json.put("app_ver", App.getVersion());
			json.put("type", encounterType);
			json.put("username", App.getUsername());
			json.put("password", App.getPassword());
			json.put("location", location);
			json.put("entereddate", formDate);

			JSONArray obs = new JSONArray();
			for (int i = 0; i < observations.length; i++) {
				if ("".equals(observations[i][0])
						|| "".equals(observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject();
				obsJson.put("name", observations[i][0]);
				obsJson.put("value", observations[i][1]);

				obs.put(obsJson);
			}
			json.put("results", obs);

			String val = json.toString();

			// Save form locally if in offline mode
			if (App.isOfflineMode()) {
				String id = "";
				String troubleshootId = "";
				for (int i = 0; i < observations.length; i++) {
					if ("".equals(observations[i][0])
							|| "".equals(observations[i][1]))
						continue;
					if (observations[i][0].equals("ID"))
						id = observations[i][1];
					else if (observations[i][0].equals("TROUBLESHOOT_NUMBER"))
						troubleshootId = observations[i][1];
				}

				if (!troubleshootId.equals(""))
					id = id + " (" + troubleshootId + ")";
				saveOfflineForm(encounterType, val, id);
				return "SUCCESS";
			}

			response = post(val);
			JSONObject jsonResponse = JsonUtil.getJSONObject(response);
			if (jsonResponse == null) {
				return response;
			}
			if (jsonResponse.has("response")) {
				String result = jsonResponse.getString("response");
				if (jsonResponse.getString("response").equals("ERROR"))
					result = result + " <br> "
							+ jsonResponse.getString("details");
				return result;
			}
			return response;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			response = context.getResources().getString(R.string.invalid_data);
		}
		return response;
	}

	public HashMap<String, String> getUVGIInstallationRecord(String id) {
		String response = "";
		HashMap hm = new HashMap();

		try {
			// Save Form
			JSONObject json = new JSONObject();
			json.put("app_ver", App.getVersion());
			json.put("type", RequestType.UVGI_GET_INSTALLATION_RECORD);
			json.put("username", App.getUsername());
			json.put("password", App.getPassword());
			json.put("location", "IHS");
			json.put("entereddate", App.getSqlDate(new Date()));
			json.put("id", id);

			String val = json.toString();
			response = post(val);
			JSONObject jsonResponse = JsonUtil.getJSONObject(response);
			if (jsonResponse == null) {
				return hm;
			}
			if (jsonResponse.has("response")) {
				hm.put("status", jsonResponse.getString("response"));
				hm.put("details", jsonResponse.getString("details"));
				hm.put("location", jsonResponse.getString("location"));
				hm.put("opd", jsonResponse.getString("opd"));
				hm.put("opd_area", jsonResponse.getString("opd_area"));
				hm.put("id", jsonResponse.getString("id"));
				return hm;
			}
			return hm;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			response = context.getResources().getString(R.string.invalid_data);
		}
		return hm;
	}

	public HashMap<String, String> getUVGITroubleshootLogRecord(String id,
			String troubleshootId) {
		String response = "";
		HashMap hm = new HashMap();

		try {
			// Save Form
			JSONObject json = new JSONObject();
			json.put("app_ver", App.getVersion());
			json.put("type", RequestType.UVGI_GET_TROUBLESHOOT_RECORD);
			json.put("username", App.getUsername());
			json.put("password", App.getPassword());
			json.put("location", "IHS");
			json.put("entereddate", App.getSqlDate(new Date()));
			json.put("id", id);
			json.put("troubleshootId", troubleshootId);

			String val = json.toString();
			response = post(val);
			JSONObject jsonResponse = JsonUtil.getJSONObject(response);
			if (jsonResponse == null) {
				return hm;
			}
			if (jsonResponse.has("response")) {
				hm.put("status", jsonResponse.getString("response"));
				hm.put("details", jsonResponse.getString("details"));
				hm.put("id", jsonResponse.getString("id"));
				hm.put("troubleshootId",
						jsonResponse.getString("troubleshootId"));
				hm.put("location", jsonResponse.getString("location"));
				hm.put("opd", jsonResponse.getString("opd"));
				hm.put("opd_area", jsonResponse.getString("opd_area"));
				hm.put("problem", jsonResponse.getString("problem"));
				return hm;
			}
			return hm;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			response = context.getResources().getString(R.string.invalid_data);
		}

		return hm;
	}

	public HashMap<String, String> getUVGITroubleshootStatusRecord(String id,
			String troubleshootId) {
		String response = "";
		HashMap hm = new HashMap();

		try {
			// Save Form
			JSONObject json = new JSONObject();
			json.put("app_ver", App.getVersion());
			json.put("type", RequestType.UVGI_GET_TROUBLESHOOT_STATUS_RECORD);
			json.put("username", App.getUsername());
			json.put("password", App.getPassword());
			json.put("location", "IHS");
			json.put("entereddate", App.getSqlDate(new Date()));
			json.put("id", id);
			json.put("troubleshootId", troubleshootId);

			String val = json.toString();
			response = post(val);
			JSONObject jsonResponse = JsonUtil.getJSONObject(response);
			if (jsonResponse == null) {
				return hm;
			}
			if (jsonResponse.has("response")) {

				if (jsonResponse.has("troubleShoot_no")) {
					hm.put("troubleshoot_no",
							jsonResponse.getString("troubleShoot_no"));

					int no = Integer.parseInt(jsonResponse
							.getString("troubleShoot_no"));
					for (int i = no - 1; i >= 0; i--) {

						hm.put("troubleshoot_" + i,
								jsonResponse.getString("troubleShoot_" + i));
					}
				}

				hm.put("status", jsonResponse.getString("response"));
				hm.put("details", jsonResponse.getString("details"));
				hm.put("id", jsonResponse.getString("id"));
				hm.put("troubleshootId",
						jsonResponse.getString("troubleshootId"));
				hm.put("location", jsonResponse.getString("location"));
				hm.put("opd", jsonResponse.getString("opd"));
				hm.put("opd_area", jsonResponse.getString("opd_area"));
				hm.put("problem", jsonResponse.getString("problem"));
				hm.put("no", jsonResponse.getString("no"));

				int no = Integer.parseInt(jsonResponse.getString("no"));
				for (int i = 1; i <= no; i++) {

					hm.put("status_" + i, jsonResponse.getString("status_" + i));
					hm.put("status_date_" + i,
							jsonResponse.getString("status_date_" + i));

				}

			}
			return hm;
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
			response = context.getResources().getString(R.string.invalid_data);
		}

		return hm;
	}

	public boolean userHasPrivilge(String privilge) {

		String[][] locations = dbUtil
				.getTableData("select id,name from identifiers where type='"
						+ Metadata.PRIVILEGE + "';");
		int i = locations.length;

		String id = dbUtil.getObject(Metadata.METADATA_TABLE, "id", "type='"
				+ Metadata.PRIVILEGE + "' AND name='" + privilge + "'");
		if (id == null)
			return false;
		else
			return true;

	}

	public void updateLoginTime() {

		String lastTimeStamp = dbUtil.getObject(
				Metadata.METADATA_TABLE,
				"name",
				"type='" + Metadata.TIME_STAMP + "' and id='"
						+ App.getUsername() + "'");

		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newTimeStamp = formatter.format(date);

		if (lastTimeStamp == null) {

			ContentValues values = new ContentValues();
			values.put("name", newTimeStamp);
			values.put("type", Metadata.TIME_STAMP);
			values.put("id", App.getUsername());

			dbUtil.insert(Metadata.METADATA_TABLE, values);

		} else {

			ContentValues values = new ContentValues();
			values.put("name", newTimeStamp);

			dbUtil.update(Metadata.METADATA_TABLE, values, "type='"
					+ Metadata.TIME_STAMP + "' and id='" + App.getUsername()
					+ "'", null);

		}

	}

	public void fetchMetadata() {

		try {
			JSONObject json = new JSONObject();

			json.put("app_ver", App.getVersion());
			json.put("type", RequestType.UVGI_GET_METADATA);
			json.put("username", App.getUsername());
			json.put("password", App.getPassword());
			json.put("location", "IHS");
			json.put("entereddate", App.getSqlDate(new Date()));

			String val = json.toString();
			String response = post(val);
			if (response == null) {
				return;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject(response);

			int opdSize = jsonResponse.getInt("opd_size");
			int opdAreaSize = jsonResponse.getInt("opd_area_size");
			int locationSize = jsonResponse.getInt("loc_size");

			dbUtil.delete(Metadata.METADATA_TABLE, "type = '" + Metadata.OPD
					+ "'", null);

			for (int i = 0; i < opdSize; i++) {

				ContentValues values = new ContentValues();
				values.put("id", i);
				values.put("type", Metadata.OPD);
				values.put("name", jsonResponse.getString("opd_" + i));

				// If the user doesn't exist in DB, save it
				String id = dbUtil.getObject(
						Metadata.METADATA_TABLE,
						"id",
						"type='" + Metadata.OPD + "' AND name='"
								+ jsonResponse.getString("opd_" + i) + "'");
				if (id == null) {
					dbUtil.insert(Metadata.METADATA_TABLE, values);
				}

			}

			dbUtil.delete(Metadata.METADATA_TABLE, "type = '"
					+ Metadata.OPD_AREA + "'", null);

			for (int i = 0; i < opdAreaSize; i++) {

				ContentValues values = new ContentValues();
				values.put("id", i);
				values.put("type", Metadata.OPD_AREA);
				values.put("name", jsonResponse.getString("opd_area_" + i));

				// If the user doesn't exist in DB, save it
				String id = dbUtil
						.getObject(Metadata.METADATA_TABLE, "id", "type='"
								+ Metadata.OPD_AREA + "' AND name='"
								+ jsonResponse.getString("opd_area_" + i) + "'");
				if (id == null) {
					dbUtil.insert(Metadata.METADATA_TABLE, values);
				}

			}

			dbUtil.delete(Metadata.METADATA_TABLE, "type = '"
					+ Metadata.LOCATION + "'", null);

			for (int i = 0; i < locationSize; i++) {

				ContentValues values = new ContentValues();
				values.put("id", i);
				values.put("type", Metadata.LOCATION);
				values.put("name", jsonResponse.getString("loc_" + i));

				// If the user doesn't exist in DB, save it
				String id = dbUtil.getObject(Metadata.METADATA_TABLE, "id",
						"type='" + Metadata.LOCATION + "' AND name='"
								+ jsonResponse.getString("loc_" + i) + "'");
				if (id == null) {
					dbUtil.insert(Metadata.METADATA_TABLE, values);
				}

			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public ArrayList<String> getMetaDataFromLocalDb(String type) {

		ArrayList<String> list = new ArrayList<String>();

		String[] columnData = dbUtil.getColumnData(Metadata.METADATA_TABLE,
				"name", "type='" + type + "'", true);
		for (int i = 0; i < columnData.length; i++)
			list.add(columnData[i]);

		return list;

	}

}
