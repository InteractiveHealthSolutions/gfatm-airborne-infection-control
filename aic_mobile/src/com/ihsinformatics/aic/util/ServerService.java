package com.ihsinformatics.aic.util;

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ihsinformatics.aic.App;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.shared.Metadata;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.R;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;



public class ServerService {

	
	private static final String	TAG	= "ServerService";
	private static DatabaseUtil	dbUtil;
	private static String		tbr3Uri;
	private HttpRequest			httpClient;
	private HttpsClient			httpsClient;
	private Context				context;
	public static HashMap   	decisionsScreening = new HashMap();
	public static HashMap   	decisionsTreatment = new HashMap();
	public static HashMap   	wfhlRatioForBoys = new HashMap();
	public static HashMap   	wfhlRatioForGirls = new HashMap();

	public ServerService (Context context)
	{
		this.context = context;
		String prefix = "http" + (App.isUseSsl () ? "s" : "") + "://";
		tbr3Uri = prefix + App.getServer () + "/gf-aic-web/gfaicweb.jsp";
		//tbr3Uri = prefix + App.getServer () + "/eIMCIweb";
		httpClient = new HttpRequest (this.context);
		httpsClient = new HttpsClient (this.context);
		dbUtil = new DatabaseUtil (this.context);
	}
	
	/**
	 * Checks to see if the client is connected to any network (GPRS/Wi-Fi)
	 * 
	 * @return status
	 */
	public boolean checkInternetConnection ()
	{
		boolean status = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo ();
		if (netInfo != null && netInfo.isConnectedOrConnecting ())
		{
			status = true;
		}
		return status;
	}
	
	/**
	 * Returns list of Patients matching the parameter(s) from the server
	 * 
	 * @param patientId
	 * @param fullName
	 * @param gender
	 * @param ageStart
	 * @param ageEnd
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public HashMap<String, String[]> searchPatients (String patientId, String firstName, String gender, int ageStart, int ageEnd, String fatherName, String mobileNumber, String unionCouncil) throws UnsupportedEncodingException
	{
		String response = "";
		HashMap<String, String[]> patients = new HashMap<String, String[]> ();
		JSONObject json = new JSONObject ();
		try
		{
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.SEARCH_PATIENTS);
			json.put ("patient_id", patientId);
			json.put ("first_name", firstName);
			json.put ("father_name", fatherName);
			json.put ("mobile_number", mobileNumber);
			json.put ("union_council", unionCouncil);
			json.put ("gender", gender);
			json.put ("age_start", ageStart);
			json.put ("age_end", ageEnd);
			response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return patients;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse.has ("patients"))
			{
				JSONArray patientList = new JSONArray (jsonResponse.getString ("patients"));
				for (int i = 0; i < patientList.length (); i++)
				{
					try
					{
						JSONObject obj = patientList.getJSONObject (i);
						String id = obj.get ("patient_id").toString ();
						String name = obj.get ("name").toString ();
						int age = Integer.parseInt (obj.get ("age").toString ());
						String gen = obj.get ("gender").toString ();
						if (gen.equals (gender) && age >= ageStart && age <= ageEnd)
						{
							patients.put (id, new String[] {name, String.valueOf (age), gen});
						}
					}
					catch (JSONException e)
					{
						Log.e (TAG, e.getMessage ());
					}
				}
			}
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return patients;
	}

	
	public String[][] getPatientDetail (String patientId)
	{
		String response = "";
		String[][] details = null;
		JSONObject json = new JSONObject ();
		try
		{
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_PATIENT_DETAIL);
			json.put ("patient_id", patientId);
			response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return details;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			{
				try
				{
					String name = jsonResponse.get ("name").toString ();
					int age = jsonResponse.getInt ("age");
					String gen = jsonResponse.get ("gender").toString ();
					JSONArray encounters = new JSONArray (jsonResponse.get ("encounters").toString ());
					details = new String[encounters.length () + 3][];
					details[0] = new String[] {"Name", name};
					details[1] = new String[] {"Age", String.valueOf (age)};
					details[2] = new String[] {"Gender", gen};
					for (int i = 0; i < encounters.length (); i++)
					{
						JSONObject obj = new JSONObject (encounters.get (i).toString ());
						details[i + 2] = new String[] {obj.get ("encounter").toString (), obj.get ("date").toString ()};
					}
				}
				catch (JSONException e)
				{
					Log.e (TAG, e.getMessage ());
				}
			}
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return details;
	}
	
	public String post (String json)
	{
		String response = null;
		try
		{
			if (App.isOfflineMode ())
			{
				JSONObject responseJson = new JSONObject ();
				responseJson.put ("result", "FAIL: Application Offline");
				return responseJson.toString ();
			}
			/*if (App.isUseSsl ())
				response = httpsClient.clientPost (tbr3Uri + json, null);
			else{
				response = httpClient.clientPost (tbr3Uri + json, null);
			}*/
			
			response = httpClient.makeRequest(tbr3Uri, json);
			
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return response;
	}

	
	public String get (String uri)
	{
		String response = null;
		try
		{
			if (App.isOfflineMode ())
			{
				JSONObject responseJson = new JSONObject ();
				responseJson.put ("result", "FAIL: Application Offline");
				return responseJson.toString ();
			}
			if (App.isUseSsl ())
				response = httpsClient.clientGet (tbr3Uri + uri);
			else
				response = httpClient.clientGet (tbr3Uri + uri);
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
		}
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return response;
	}
	
	/**
	 * Returns true/false after checking if the user in App variable exists in
	 * the local database. If not found, it searches for the user in the Server.
	 * 
	 * @return status
	 */
	public String authenticate (String username)
	{
		//return true;
		return getUser (username);
		
	}

	public String getUser (String name)
	{
		JSONObject userObj = null;
		// Always fetch from server and save this user
		try
		{
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("type", RequestType.LOGIN);
			json.put ("username", name);
			json.put ("starttime", name);
			json.put ("password", name);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			
			if(response == null){
			    return "CONNECTION_ERROR";	
			}
			
			userObj = JsonUtil.getJSONObject (response);
			if (userObj == null)
			{
				return response;
			}
			
			if (response != null)
			{
				String screenerName = userObj.getString ("sname");
				String location = userObj.getString ("location");
				String role = userObj.getString("role");
				String returnString = "SUCCESS:,:"+screenerName+":,:"+role+":,:"+location;
				
				return returnString;
			}
			
		}
		catch (Exception e)
		{
			try
			{
				String error = userObj.getString ("ERROR");
				return error;
			}
			catch (Exception error)
			{
				return "UNKNOWN_ERROR";
			}
		}
		
		return "FAIL";
		
	}
	
	
	/**
	 * 
	 * Returns View with matching tag
	 *
	 * @param tag
	 * @param views
	 * @return
	 */
	
	public View findViewByTag(String tag, View[] views){
		
		for(View view: views){
			String t = view.getTag().toString();
			if(t.equals(tag)){
				
				return view;
			}
			
		}
		return null;
	}
	
	/**
	 * 
	 * returns View with matching id
	 * 
	 * @param id
	 * @param views
	 * @return
	 */
	
	public View findViewById(int id, View[] views){
		
		for(View view: views){
			int i = view.getId();
			if(i == id){
				
				return view;
			}
			
		}
		return null;
	}
	
	
	public Boolean renewLoginStatus () {
		
		String lastTimeStamp = App.getLastLogin();
		
		Date date = new Date();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String newTimeStamp = formatter.format(date);
			
		if(newTimeStamp.equals(lastTimeStamp)){
			return false;
		}
		
		return true;
	}
	
	
	public String[][] getPerformanceReport(String encounterType, ContentValues values)
	{
		String response = "";
		
		String dateTo = values.getAsString ("toDate");
	    String dateFrom = values.getAsString ("fromDate");
	    String username = values.getAsString ("username");
	    
	    String[][] details = null;
	    
	    try
		{

		    JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			
			json.put ("toDate", dateTo);
			json.put ("fromDate", dateFrom);
			json.put ("username", username);
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				details = new String[1][];
				details[0] = new String[] {"response", response};
				return details;
			}
			
			String newEnrollments = jsonResponse.get ("new_enrollments").toString();
			String reScreening = jsonResponse.get ("re_screening").toString();
			String followup = jsonResponse.get ("followup").toString();
			String referrals = jsonResponse.get ("referrals").toString();
			String uName = jsonResponse.get ("username").toString();
			String name = jsonResponse.get ("name").toString();
			String location = jsonResponse.get ("location").toString();
			
			details = new String[7][];
	    
			details[0] = new String[] {"newEnrollments", newEnrollments};
			details[1] = new String[] {"reScreening", reScreening}; 
			details[2] = new String[] {"followup", followup}; 
			details[3] = new String[] {"referrals", referrals}; 
			details[4] = new String[] {"username", uName}; 
			details[5] = new String[] {"name", name}; 
			details[6] = new String[] {"location", location}; 
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			details = new String[1][];
			details[0] = new String[] {"response", context.getResources ().getString (R.string.invalid_data)};
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			details = new String[1][];
			details[0] = new String[] {"response", context.getResources ().getString (R.string.unknown_error)};
		}
		
		return details;
	}
	
	
	public String saveNewPatient (String encounterType, ContentValues values, String[][] observations)
	{
		
		String response = "";
		
		String givenName = TextUtil.capitalizeFirstLetter (values.getAsString ("firstName"));
		String familyName = TextUtil.capitalizeFirstLetter (values.getAsString ("lastName"));
		String gender = values.getAsString ("gender");
		String patientId = values.getAsString ("patientId");
		String formDate = values.getAsString ("formDate");
		String dateOfBirth = values.getAsString("dateOfBirth");
		String fatherName = values.getAsString("fatherName");
		String cnic = values.getAsString("cnic");
		String houseNumber = values.getAsString("houseAddress");
		String streetName = values.getAsString("landmark");
		String colony = "";
		String town = "";
		String city = App.getCity();
		String country = App.getCountry();
		String location = App.getLocation();
		String landlineNumber = values.getAsString("landlineNumber");
		String mobileNumber = values.getAsString("mobileNumber");
		
		try
		{
			String id = null;
			/*if (!App.isOfflineMode () && !encounterType.equals(FormType.INFORMATION_VITALS))
			{	
				id = getPatientId (patientId);
				if (id != null)
					return context.getResources ().getString (R.string.duplication);
			}*/
			// Save Patient
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("patient_id", patientId);
			json.put ("given_name", givenName);
			json.put ("family_name", familyName);
			json.put ("gender", gender);
			//json.put ("age", age);
			json.put ("dob", dateOfBirth);
			json.put ("location",location);
			
			json.put ("houseNumber",houseNumber);
			json.put ("streetName",streetName);
			json.put ("colony",colony);
			json.put ("town",town);
			json.put ("city",city);
			json.put ("country",country);
			
            // Add contacts as array of person attributes
			JSONArray attributes = new JSONArray ();
			
			JSONObject fatherNameAttributeJson = new JSONObject ();
			fatherNameAttributeJson.put ("attribute", "Father's Name");
			fatherNameAttributeJson.put ("value", fatherName);
			attributes.put (fatherNameAttributeJson);
			
			JSONObject fatherCnicAttributeJson = new JSONObject ();
			fatherCnicAttributeJson.put ("attribute", "Father's CNIC");
			fatherCnicAttributeJson.put ("value", cnic);
			attributes.put (fatherCnicAttributeJson);
			
			JSONObject landlineNumberAttributeJson = new JSONObject ();
			landlineNumberAttributeJson.put ("attribute", "Landline Number");
			landlineNumberAttributeJson.put ("value", landlineNumber);
			attributes.put (landlineNumberAttributeJson);
			
			JSONObject mobileNumberaAttributeJson = new JSONObject ();
			mobileNumberaAttributeJson.put ("attribute", "Mobile Number");
			mobileNumberaAttributeJson.put ("value", mobileNumber);
			attributes.put (mobileNumberaAttributeJson);
			
			json.put ("attributes", attributes.toString ());
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	/**
	 * Returns patient's DB Id using Patient Identifier
	 * 
	 * @param patientId
	 * @return
	 */
	public String getPatientId (String patientId)
	{
		try
		{
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_PATIENT);
			json.put ("patient_id", patientId);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (response != null)
			{
				if (jsonResponse == null)
				{
					return null;
				}
				if (jsonResponse.has ("id"))
				{
					return jsonResponse.getString ("id");
				}
				return null;
			}
		}
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return null;
	}
	
	public String[][] fetchPatientVitals(String id){
		
		String[][] details = null;
		
		try
		{
			JSONObject json = new JSONObject ();
			
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_VITALS);
			json.put ("id", id);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return null;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			
				try
				{
					
					String firstName = jsonResponse.get ("first_name").toString ();
					String lastName = jsonResponse.get ("last_name").toString ();
					String dob = jsonResponse.get ("dob").toString ();
					dob = dob.substring(0,dob.indexOf(" "));
					String gen = jsonResponse.get ("gender").toString ();
					String fatherName = "";
					if(jsonResponse.get ("fatherName") != null)
						fatherName = jsonResponse.get ("fatherName").toString ();
					String cnic = "";
					if(jsonResponse.get ("fatherCnic") != null)
						cnic = jsonResponse.get ("fatherCnic").toString ();
					String landlineNumber = "";
					if(jsonResponse.get ("landlineNumber") != null)
						landlineNumber = jsonResponse.get ("landlineNumber").toString ();
					String mobileNumber = "";
					if(jsonResponse.get ("mobileNumber") != null)
						mobileNumber = jsonResponse.get ("mobileNumber").toString ();
					
					String houseNo = "";
					if(jsonResponse.get ("houseNumber") != null)
						houseNo = jsonResponse.get ("houseNumber").toString ();
					String streetName = "";
					if(jsonResponse.get ("streetName") != null)
						streetName = jsonResponse.get ("streetName").toString ();
					String town = "";
					if(jsonResponse.get ("town") != null)
					    town = jsonResponse.get ("town").toString ();
					String colony = "";
					if(jsonResponse.get ("colony") != null)
						colony = jsonResponse.get ("colony").toString ();
					
					String weight = jsonResponse.get ("weight").toString ();
					String temperature = jsonResponse.get ("temperature").toString ();
					String height = jsonResponse.get ("height").toString ();
					String muac = jsonResponse.get ("muac").toString ();
					String wfhl = jsonResponse.get ("wfhl").toString ();
					String immHist = jsonResponse.get ("immunization_history").toString ();
					String visitType = jsonResponse.get ("visitType").toString ();
					
					details = new String[20][];
					details[0] = new String[] {"First_Name", firstName};
					details[1] = new String[] {"Last_Name", lastName};
					details[2] = new String[] {"DOB", dob};
					details[3] = new String[] {"Gender", gen};
					details[4] = new String[] {"Father_Name", fatherName};
					details[5] = new String[] {"Father_CNIC", cnic};
					details[6] = new String[] {"landlineNumber", landlineNumber};
					details[7] = new String[] {"mobileNumber", mobileNumber};
					details[8] = new String[] {"houseNumber", houseNo};
					details[9] = new String[] {"streetName", streetName};
					details[10] = new String[] {"town", town};
					details[11] = new String[] {"colony", colony};
					details[12] = new String[] {"weight", weight};
					details[13] = new String[] {"temperature", temperature};
					details[14] = new String[] {"height", height};
					details[15] = new String[] {"muac", muac};
					details[16] = new String[] {"wfhl", wfhl};
					details[17] = new String[] {"immunization_history", immHist};
					details[18] = new String[] {"visitType", visitType};
				}
				catch (JSONException e)
				{
					Log.e (TAG, e.getMessage ());
				}
			}	
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return details;
	}
	
	public ArrayList<String[]> getReferralStatus(String encounterType, ContentValues values){
		
		ArrayList array_name = null;
		
		String dateTo = values.getAsString ("toDate");
	    String dateFrom = values.getAsString ("fromDate");
	    String username = values.getAsString ("username");
		
		try
		{
			JSONObject json = new JSONObject ();
	
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			
			json.put ("toDate", dateTo);
			json.put ("fromDate", dateFrom);
			json.put ("username", username);
			
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return null;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			
			
			String tR = jsonResponse.get ("total_referral").toString();
			String cR = jsonResponse.get ("completed_referral").toString();

			
			String size = jsonResponse.get ("active_referral").toString();
			int s = Integer.valueOf(size);
			
			array_name = new ArrayList (s);
			
			String d[] = new String[5];
			d[0] = jsonResponse.get ("total_referral").toString();
			d[1] = jsonResponse.get ("completed_referral").toString();
			d[2] = jsonResponse.get ("name").toString();
			d[3] = jsonResponse.get ("location").toString();
			d[4] = jsonResponse.get ("username").toString();
			array_name.add(d);
			
			for(int i = 0; i<s; i++){
				
				String data[] = new String[7];
				data[0] = jsonResponse.get ("identifier_"+i).toString();
				data[1] = jsonResponse.get ("fatherName_"+i).toString();
				data[2] = jsonResponse.get ("mobileNumber_"+i).toString();
				data[3] = jsonResponse.get ("name_"+i).toString();
				data[4] = jsonResponse.get ("totalReferrals_"+i).toString();
				data[5] = jsonResponse.get ("completedReferrals_"+i).toString();
				data[6] = jsonResponse.get ("activeReferrals_"+i).toString();
				
				array_name.add(data);
						
			}
				
		}	
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return array_name;
	}

	
	public String[][] getLatestTreatment(String id){
		
		String[][] details = null;
		
		try
		{
			JSONObject json = new JSONObject ();
			
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_TREATMENTS);
			json.put ("id", id);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return null;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			
				try
				{
					
					String dangerSignClassification = jsonResponse.get ("danger_sign_classification").toString ();
					String dangerSignTreatment = jsonResponse.get ("danger_sign").toString ();
					String jaundiceClassification = jsonResponse.get ("jaundice_classification").toString ();
					String jaundiceTreatment = jsonResponse.get ("jaundice").toString ();
					String dehydrationClassification = jsonResponse.get ("dehydration_classification").toString ();
					String dehydrationTreatment = jsonResponse.get ("dehydration").toString ();
					String diarrhoeaClassification = jsonResponse.get ("diarrhoea_classification").toString ();
					String diarrhoeaTreatment = jsonResponse.get ("diarrhoea").toString ();
					String dysenteryClassification = jsonResponse.get ("dysentery_classification").toString ();
					String dysenteryTreatment = jsonResponse.get ("dysentery").toString ();
					String feedingProblemClassification = jsonResponse.get ("feedingProblem_classification").toString ();
					String feedingProblemTreatment = jsonResponse.get ("feedingProblem").toString ();
					String feedingProblemNonBreastfedClassification = jsonResponse.get ("feedingProblemNonBreastfed_classification").toString ();
					String feedingProblemNonBreastfedTreatment = jsonResponse.get ("feedingProblemNonBreastfed").toString ();
					String hivInfectionClassification = jsonResponse.get ("hivInfection_classification").toString ();
					String hivInfectionTreatment = jsonResponse.get ("hivInfection").toString ();
					String dengueClassification = jsonResponse.get ("dengue_classification").toString ();
					String dengueTreatment = jsonResponse.get ("dengue").toString ();
					String earProblemClassification = jsonResponse.get ("earProblem_classification").toString ();
					String earProblemTreatment = jsonResponse.get ("earProblem").toString ();
					String malnutritionClassification = jsonResponse.get ("malnutrition_classification").toString ();
					String malnutritionTreatment = jsonResponse.get ("malnutrition").toString ();
					String anaemiaClassification = jsonResponse.get ("anaemia_classification").toString ();
					String anaemiaTreatment = jsonResponse.get ("anaemia").toString ();
					String measlesClassification = jsonResponse.get ("measles_classification").toString ();
					String measlesTreatment = jsonResponse.get ("measles").toString ();
					String malariaClassification = jsonResponse.get ("malaria_classification").toString ();
					String malariaTreatment = jsonResponse.get ("malaria").toString ();
					String pneumoniaClassification = jsonResponse.get ("pneumonia_classification").toString ();
					String pneumoniaTreatment = jsonResponse.get ("pneumonia").toString ();
					
					String weight = jsonResponse.get ("weight").toString ();
					String temperature = jsonResponse.get ("temperature").toString ();
					String height = jsonResponse.get ("height").toString ();
					String muac = jsonResponse.get ("muac").toString ();
					String wfhl = jsonResponse.get ("wfhl").toString ();
					String dob = jsonResponse.get ("dateOfBirth").toString ();
					String name = jsonResponse.get ("first_name").toString () + " " +jsonResponse.get ("last_name").toString ();
					String fatherName = jsonResponse.get ("fatherName").toString ();
					String vaccinationNeeded = jsonResponse.get ("vaccination_needed").toString ();
					String vaccinationClassification = jsonResponse.get ("vaccination_classification").toString ();
					String vaccinationTreatment = jsonResponse.get ("vaccination").toString ();
					
					details = new String[41][];
					details[0] = new String[] {"dangerSignClassification", dangerSignClassification}; 
					details[1] = new String[] {"dangerSignTreatment", dangerSignTreatment}; 
					details[2] = new String[] {"jaundiceClassification", jaundiceClassification}; 
					details[3] = new String[] {"jaundiceTreatment", jaundiceTreatment}; 
					details[4] = new String[] {"dehydrationClassification", dehydrationClassification}; 
					details[5] = new String[] {"dehydrationTreatment", dehydrationTreatment};
					details[6] = new String[] {"feedingProblemClassification", feedingProblemClassification}; 
					details[7] = new String[] {"feedingProblemTreatment", feedingProblemTreatment};
					details[8] = new String[] {"feedingProblemNonBreastfedClassification", feedingProblemNonBreastfedClassification}; 
					details[9] = new String[] {"feedingProblemNonBreastfedTreatment", feedingProblemNonBreastfedTreatment};
					details[10] = new String[] {"hivInfectionClassification", hivInfectionClassification}; 
					details[11] = new String[] {"hivInfectionTreatment", hivInfectionTreatment};
					details[12] = new String[] {"dengueClassification", dengueClassification}; 
					details[13] = new String[] {"dengueTreatment", dengueTreatment};
					details[14] = new String[] {"weight", weight};
					details[15] = new String[] {"dob", dob};
					details[16] = new String[] {"diarrhoeaClassification", diarrhoeaClassification};
					details[17] = new String[] {"diarrhoeaTreatment", diarrhoeaTreatment};		
					details[18] = new String[] {"dysenteryClassification", dysenteryClassification};
					details[19] = new String[] {"dysenteryTreatment", dysenteryTreatment};
					details[20] = new String[] {"earProblemClassification", earProblemClassification};
					details[21] = new String[] {"earProblemTreatment", earProblemTreatment};
					details[22] = new String[] {"malnutritionClassification", malnutritionClassification};
					details[23] = new String[] {"malnutritionTreatment", malnutritionTreatment};
					details[24] = new String[] {"anaemiaClassification", anaemiaClassification};
					details[25] = new String[] {"anaemiaTreatment", anaemiaTreatment};
					details[26] = new String[] {"malariaClassification", malariaClassification};
					details[27] = new String[] {"malariaTreatment", malariaTreatment};
					details[28] = new String[] {"measlesClassification", measlesClassification};
					details[29] = new String[] {"measlesTreatment", measlesTreatment};
					details[30] = new String[] {"pneumoniaClassification", pneumoniaClassification};
					details[31] = new String[] {"pneumoniaTreatment", pneumoniaTreatment};
					details[32] = new String[] {"name", name};
					details[33] = new String[] {"fatherName", fatherName};
					details[34] = new String[] {"height", height};
					details[35] = new String[] {"temperature", temperature};
					details[36] = new String[] {"muac", muac};
					details[37] = new String[] {"wfhl", wfhl};
					details[38] = new String[] {"vaccinationNeeded", vaccinationNeeded};
					details[39] = new String[] {"vaccinationClassification", vaccinationClassification};
					details[40] = new String[] {"vaccinationTreatment", vaccinationTreatment};
				}
				catch (JSONException e)
				{
					Log.e (TAG, e.getMessage ());
				}
			}	
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return details;
	}
	
	
public String[][] getLatestReferrals(String id){
		
		String[][] details = null;
		
		try
		{
			JSONObject json = new JSONObject ();
			
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_LATEST_REFERRAL);
			json.put ("id", id);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return null;
			}
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			
				try
				{
					
					String dangerSignClassification = jsonResponse.get ("danger_sign_classification").toString ();
					String jaundiceClassification = jsonResponse.get ("jaundice_classification").toString ();
					String dehydrationClassification = jsonResponse.get ("dehydration_classification").toString ();
					String diarrhoeaClassification = jsonResponse.get ("diarrhoea_classification").toString ();
					String dysenteryClassification = jsonResponse.get ("dysentery_classification").toString ();
					String feedingProblemClassification = jsonResponse.get ("feedingProblem_classification").toString ();
					String feedingProblemNonBreastfedClassification = jsonResponse.get ("feedingProblemNonBreastfed_classification").toString ();
					String hivInfectionClassification = jsonResponse.get ("hivInfection_classification").toString ();
					String dengueClassification = jsonResponse.get ("dengue_classification").toString ();
					String earProblemClassification = jsonResponse.get ("earProblem_classification").toString ();
					String malnutritionClassification = jsonResponse.get ("malnutrition_classification").toString ();
					String anaemiaClassification = jsonResponse.get ("anaemia_classification").toString ();
					String measlesClassification = jsonResponse.get ("measles_classification").toString ();
					String malariaClassification = jsonResponse.get ("malaria_classification").toString ();
					String pneumoniaClassification = jsonResponse.get ("pneumonia_classification").toString ();
					String vaccinationNeeded = jsonResponse.get ("vaccination_needed").toString ();
					String vaccinationClassification = jsonResponse.get ("vaccination_classification").toString ();
					String vaccinationTreatment = jsonResponse.get ("vaccination").toString ();
					
					String weight = jsonResponse.get ("weight").toString ();
					String temperature = jsonResponse.get ("temperature").toString ();
					String height = jsonResponse.get ("height").toString ();
					String muac = jsonResponse.get ("muac").toString ();
					String wfhl = jsonResponse.get ("wfhl").toString ();
					String dob = jsonResponse.get ("dateOfBirth").toString ();
					String name = jsonResponse.get ("first_name").toString () + " " +jsonResponse.get ("last_name").toString ();
					String fatherName = jsonResponse.get ("fatherName").toString ();
					String referred = jsonResponse.get ("referred").toString ();
					
					details = new String[42][];
					details[0] = new String[] {"dangerSignClassification", dangerSignClassification}; 
					details[1] = new String[] {"dangerSignTreatment", ""}; 
					details[2] = new String[] {"jaundiceClassification", jaundiceClassification}; 
					details[3] = new String[] {"jaundiceTreatment", ""}; 
					details[4] = new String[] {"dehydrationClassification", dehydrationClassification}; 
					details[5] = new String[] {"dehydrationTreatment", ""};
					details[6] = new String[] {"feedingProblemClassification", feedingProblemClassification}; 
					details[7] = new String[] {"feedingProblemTreatment", ""};
					details[8] = new String[] {"feedingProblemNonBreastfedClassification", feedingProblemNonBreastfedClassification}; 
					details[9] = new String[] {"feedingProblemNonBreastfedTreatment", ""};
					details[10] = new String[] {"hivInfectionClassification", hivInfectionClassification}; 
					details[11] = new String[] {"hivInfectionTreatment", ""};
					details[12] = new String[] {"dengueClassification", dengueClassification}; 
					details[13] = new String[] {"dengueTreatment", ""};
					details[14] = new String[] {"weight", weight};
					details[15] = new String[] {"dob", dob};
					details[16] = new String[] {"diarrhoeaClassification", diarrhoeaClassification};
					details[17] = new String[] {"diarrhoeaTreatment", ""};		
					details[18] = new String[] {"dysenteryClassification", dysenteryClassification};
					details[19] = new String[] {"dysenteryTreatment", ""};
					details[20] = new String[] {"earProblemClassification", earProblemClassification};
					details[21] = new String[] {"earProblemTreatment", ""};
					details[22] = new String[] {"malnutritionClassification", malnutritionClassification};
					details[23] = new String[] {"malnutritionTreatment", ""};
					details[24] = new String[] {"anaemiaClassification", anaemiaClassification};
					details[25] = new String[] {"anaemiaTreatment", ""};
					details[26] = new String[] {"malariaClassification", malariaClassification};
					details[27] = new String[] {"malariaTreatment", ""};
					details[28] = new String[] {"measlesClassification", measlesClassification};
					details[29] = new String[] {"measlesTreatment", ""};
					details[30] = new String[] {"pneumoniaClassification", pneumoniaClassification};
					details[31] = new String[] {"pneumoniaTreatment", ""};
					details[32] = new String[] {"name", name};
					details[33] = new String[] {"fatherName", fatherName};
					details[34] = new String[] {"height", height};
					details[35] = new String[] {"temperature", temperature};
					details[36] = new String[] {"muac", muac};
					details[37] = new String[] {"wfhl", wfhl};
					details[38] = new String[] {"referred", referred};
					details[39] = new String[] {"vaccinationNeeded", vaccinationNeeded};
					details[40] = new String[] {"vaccinationClassification", vaccinationClassification};
					details[41] = new String[] {"vaccinationTreatment", vaccinationTreatment};
					
				}
				catch (JSONException e)
				{
					Log.e (TAG, e.getMessage ());
				}
			}	
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return details;
	}
	
public String[][] getClassifications(String id){
		
		String[][] details = null;
		
		try
		{
			JSONObject json = new JSONObject ();
			
			json.put ("app_ver", App.getVersion ());
			//json.put ("form_name", FormType.GET_CLASSIFICATION);
			json.put ("id", id);
			String response = get ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			response = get ("?content=" + JsonUtil.getEncodedJson (json));
			if (response == null)
			{
				return null;
			}
				try
				{
					
					String dangerSignClassification = jsonResponse.get ("danger_sign_classification").toString ();
					String jaundiceClassification = jsonResponse.get ("jaundice_classification").toString ();
					String dehydrationClassification = jsonResponse.get ("dehydration_classification").toString ();
					String feedingProblemClassification = jsonResponse.get ("feedingProblem_classification").toString ();
					String feedingProblemNonBreastfedClassification = jsonResponse.get ("feedingProblemNonBreastfed_classification").toString ();
					String hivInfectionClassification = jsonResponse.get ("hivInfection_classification").toString ();
					String dengueClassification = jsonResponse.get ("dengue_classification").toString ();
					
					details = new String[10][];
					details[0] = new String[] {"dangerSignClassification", dangerSignClassification};
					details[1] = new String[] {"jaundiceClassification", jaundiceClassification}; 
					details[2] = new String[] {"dehydrationClassification", dehydrationClassification};
					details[3] = new String[] {"feedingProblemClassification", feedingProblemClassification}; 
					details[4] = new String[] {"feedingProblemNonBreastfedClassification", feedingProblemNonBreastfedClassification}; 
					details[5] = new String[] {"hivInfectionClassification", hivInfectionClassification}; 
					details[6] = new String[] {"dengueClassification", dengueClassification}; 
					
				}
				catch (JSONException e)
				{
					Log.e (TAG, e.getMessage ());
				}
			}	
		catch (Exception e)
		{
			Log.e (TAG, e.getMessage ());
		}
		return details;
	}
	
	
	public String saveScreening (String encounterType, ContentValues values, String[][] observations)
	{
          	
		String response = "";
		// Demographics
		String patientId = values.getAsString ("pid");
		String formDate = values.getAsString ("formDate");
		
		try
		{
			// Check if the patient Id exists
			if (!App.isOfflineMode ()){
				String id = getPatientId (patientId);
				if (id == null)
					return context.getResources ().getString (R.string.patient_id_missing);
			}
			
			// Save Form
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", App.getLocation ());
			json.put ("patient_id", patientId);
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	public String saveSecScreening (String encounterType, ContentValues values, String[][] observations)
	{
          	
		String response = "";
		// Demographics
		String patientId = values.getAsString ("pid");
		String formDate = values.getAsString ("formDate");
		String followupDays = values.getAsString("followupInDays");
		String referralToHospital = values.getAsString("referral");
		String referralFor = values.getAsString("referredFor");
		
		try
		{
			// Check if the patient Id exists
			if (!App.isOfflineMode ()){
				String id = getPatientId (patientId);
				if (id == null)
					return context.getResources ().getString (R.string.patient_id_missing);
			}
				
			// Save Form
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", App.getLocation ());
			json.put ("patient_id", patientId);
			json.put ("followup_in_days", followupDays);
			json.put ("referral_to_hospital", referralToHospital);
			json.put ("referral_for", referralFor);
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	public String saveReferralCompletion (String encounterType, ContentValues values, String[][] observations)
	{
          	
		String response = "";
		// Demographics
		String patientId = values.getAsString ("pid");
		String formDate = values.getAsString ("formDate");
		String referredFor = values.getAsString("referredFor");
		String status = values.getAsString("status");
		
		try
		{
			// Check if the patient Id exists
			if (!App.isOfflineMode ()){
				String id = getPatientId (patientId);
				if (id == null)
					return context.getResources ().getString (R.string.patient_id_missing);
			}
				
			// Save Form
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", App.getLocation ());
			json.put ("patient_id", patientId);
			json.put ("referredFor", referredFor);
			json.put ("status", status);
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	
	public String saveTreatment (String encounterType, ContentValues values, String[][] observations)
	{
          	
		String response = "";
		// Demographics
		String patientId = values.getAsString ("pid");
		String formDate = values.getAsString ("formDate");
		
		try
		{
			// Check if the patient Id exists
			if (!App.isOfflineMode ()){
				String id = getPatientId (patientId);
				if (id == null)
					return context.getResources ().getString (R.string.patient_id_missing);
			}
				
			// Save Form
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", App.getLocation ());
			json.put ("patient_id", patientId);
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	public String saveFollowup (String encounterType, ContentValues values, String[][] observations)
	{
          	
		String response = "";
		// Demographics
		String patientId = values.getAsString ("pid");
		String formDate = values.getAsString ("formDate");
		
		try
		{
			// Check if the patient Id exists
			if (!App.isOfflineMode ()){
				String id = getPatientId (patientId);
				if (id == null)
					return context.getResources ().getString (R.string.patient_id_missing);
			}
				
			// Save Form
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", App.getLocation ());
			json.put ("patient_id", patientId);
			
			JSONArray obs = new JSONArray ();
			for (int i = 0; i < observations.length; i++)
			{
				if ("".equals (observations[i][0]) || "".equals (observations[i][1]))
					continue;
				JSONObject obsJson = new JSONObject ();
				obsJson.put ("concept", observations[i][0]);
				obsJson.put ("value", observations[i][1]);
				obs.put (obsJson);
			}
			
			json.put ("encounter_type", encounterType);
			json.put ("form_date", formDate);
			json.put ("encounter_location", App.getLocation());
			json.put ("provider", App.getUsername ());
			json.put ("obs", obs.toString ());
			
			// Save form locally if in offline mode
			if (App.isOfflineMode ())
			{
				saveOfflineForm (encounterType, json.toString (), patientId);
				return "SUCCESS";
			}
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse == null)
			{
				return response;
			}
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			return response;
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.invalid_data);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.unknown_error);
		}
		return response;
	}
	
	public String saveReverseReferral (String encounterType, ContentValues values){
		
		String response = "";
		try
		{
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_date", values.getAsString ("formDate"));
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", values.getAsString ("location"));
			json.put ("child_enrolled", values.getAsString ("child_enrolled"));
			if(values.getAsString ("child_enrolled").equals("Yes"))
				json.put("pid", values.getAsString ("pid"));
			else{
				json.put ("child_name", values.getAsString ("child_name"));
				json.put ("gender", values.getAsString ("gender"));
				json.put ("age_in_year", values.getAsString ("age_in_year"));
				json.put ("father_name", values.getAsString ("father_name"));
				json.put ("address", values.getAsString ("address"));
				json.put ("mobile_number", values.getAsString("mobile_number"));
			}
			
			json.put ("union_council", values.getAsString ("union_council"));
			
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			else
			{
				return response;
			}
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}
		return response;
		
		
	}
	
	public String saveFeedback (String encounterType, ContentValues values)
	{
		String response = "";
		try
		{
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("form_name", encounterType);
			json.put ("username", App.getUsername ());
			json.put ("location", values.getAsString ("location"));
			json.put ("feedback_type", values.getAsString ("feedbackType"));
			json.put ("feedback", values.getAsString ("feedback"));
			response = post ("?content=" + JsonUtil.getEncodedJson (json));
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			else
			{
				return response;
			}
		}
		catch (JSONException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}
		return response;
	}
	
	
	public String authenticate (String encounterType, ContentValues values)
	{
		String response = "";
		try
		{
			JSONObject json = new JSONObject ();
			json.put ("app_ver", App.getVersion ());
			json.put ("type", encounterType);
			json.put ("username", values.getAsString ("username"));
			json.put ("password", values.getAsString ("password"));
			json.put ("starttime", values.getAsString ("starttime"));
			String val = /*"?content=" +*/ json.toString();
			response = post (val);
			JSONObject jsonResponse = JsonUtil.getJSONObject (response);
			if (jsonResponse.has ("result"))
			{
				String result = jsonResponse.getString ("result");
				return result;
			}
			else
			{
				return response;
			}
		}
		catch (JSONException e) 
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}
		/*catch (UnsupportedEncodingException e)
		{
			Log.e (TAG, e.getMessage ());
			response = context.getResources ().getString (R.string.insert_error);
		}*/
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
	public boolean saveOfflineForm (String encounterType, String json, String id)
	{
		ContentValues data = new ContentValues ();
		Date date = new Date ();
		data.put ("username", App.getUsername ());
		data.put ("timestamp", date.getTime ());
		data.put ("form", encounterType);
		data.put ("json", json);
		data.put ("pid", id);
		return dbUtil.insert ("offline_forms", data);
	}
	
	public String[][] getSavedForms (String username)
	{
		String[][] forms = dbUtil.getTableData ("select timestamp, form, json, pid from offline_forms where username='" + username + "'");
		return forms;
	}
	
	public boolean deleteSavedForm (Long timestamp)
	{
		return dbUtil.delete ("offline_forms", "timestamp=?", new String[] {timestamp.toString ()});
	}
	
	public String[] getSavedForm (String username, String timestamp)
	{
		String[] forms = dbUtil.getRecord ("select timestamp, form, json from offline_forms where username='" + username + "' and timestamp=" + timestamp);
		return forms;
	}
	
	public int countSavedForms (String username)
	{
		return Integer.parseInt (dbUtil.getObject ("select count(*) from offline_forms where username='" + username + "'"));
	}
	
	public String[][] getLocations (){
		
		String[][] locations = dbUtil.getTableData ("select id,name from identifiers where type='"+Metadata.LOCATION+"';");
		return locations;
	}
	
	public String[][] getUsers (String role){
		
		String[][] users = dbUtil.getTableData ("select id,name from identifiers where type='"+Metadata.USER+"' and name like '"+role+"_%';");
		return users;
	}
	
	public String[][] getUsers (String role, String location){
		
		String[][] users = dbUtil.getTableData ("select id,name from identifiers where type='"+Metadata.USER+"' and name like '"+role+"_"+location+"';");
		return users;
	}

}
