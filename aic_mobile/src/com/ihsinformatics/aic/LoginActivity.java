package com.ihsinformatics.aic;

/**
 * 
 * LOGIN ACTIVITY CLASS...
 * 
 */

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.ServerService;
import com.ihsinformatics.aic.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener {
	
	private ServerService			serverService;
	protected static ProgressDialog	loading;
	
	EditText						username;
	EditText						password;
	Button							login;
	CheckBox						offline;
	TextView						showPassword;
	
	View[]							views;
	String                          tempUsername;
	String							tempPassword;
	
	String							startDateTime;
	Calendar						enteredDate;
	
	int loginAttempt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		serverService = new ServerService (getApplicationContext ());
		
		// initializing views
		loading = new ProgressDialog (this);
		username = (EditText) findViewById(R.login_id.usernameEditText);
		password = (EditText) findViewById(R.login_id.passwordEditText);
		login = (Button) findViewById(R.login_id.loginButton);
		offline = (CheckBox) findViewById(R.login_id.offlineCheckBox);
		showPassword = (TextView) findViewById (R.login_id.showPasswordTextView);
		
		login.setOnClickListener(this);
		showPassword.setOnClickListener (this);
		username.setText(App.getUsername());
		username.setSelection(username.getText().length());
		
		views = new View[] {username, password, login};
		initView (views);
	}

	public void initView (View[] views)
	{
		
		Long tsLong = System.currentTimeMillis()/1000;
		startDateTime = tsLong.toString();
		enteredDate = Calendar.getInstance();
		
		Boolean status = serverService.renewLoginStatus();  // Check if login status needs to be renew or not
		
		if(!status){      // if login status doesn't need to renewed & App is set to Auto login start Main Menu Activity
			if (App.isAutoLogin ())
			{
				Intent intent = new Intent (this, MainMenuActivity.class);
				startActivity (intent);
				finish ();
			}
		}
		
		
		username.setText (App.getUsername ());
		
		loginAttempt = 0;	
		
		View view = this.getCurrentFocus();
	    if (view != null) {
	        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
	        inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		switch (item.getItemId ())
		{
			case R.menu_id.itemPreferences :
				startActivity (new Intent (this, Preferences.class));
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v == login){
			
			login();
			
		}
	  
		else if(v == showPassword){
			
			// Hide & Show Password Text
			String status = showPassword.getText().toString();
			if(status.equals("Show Password"))
			{
				password.setInputType( InputType.TYPE_TEXT_VARIATION_PASSWORD);
				showPassword.setText("Hide Password");
			}
			else{
				password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				showPassword.setText("Show Password");
			}
		
			int position = password.getText().length(); 
			password.setSelection(position);
		}
	}
	
	/***
	 * 
	 * Facilitates Login Online/Offline Process
	 */
	
	public void login(){
		
		// Check connection with server or offline mode
		if (!serverService.checkInternetConnection () && !offline.isChecked ())
		{
			showAlert(getResources ().getString (R.string.data_connection_error), AlertType.ERROR);
		}
		
		else if(validate()){

			// Authenticate from server
			AsyncTask<String, String, String> authenticationTask = new AsyncTask<String, String, String> ()
			{
				@Override
				protected String doInBackground (String... params)
				{
					runOnUiThread (new Runnable ()
					{
						@Override
						public void run ()
						{
							loading.setIndeterminate (true);
							loading.setCancelable (false);
							//loading.show ();
						}
					});
					
					if (offline.isChecked ())   // in case of offline login...
					{
						tempUsername = App.getUsername();
						tempPassword = App.getPassword();
						
						// Check if typed username and password matches loaded from preferences
						if (App.getUsername ().equalsIgnoreCase (App.get (username)) && App.getPassword ().equals (App.get (password)))
						{
							App.setOfflineMode(true);
							return "SUCCESS";
						}
						
						else{// if offline login fails
						
							loginAttempt++;   
							
							if(loginAttempt == 3){  // If there are 3 fail login attempts in one go, clear the loaded credentials.
								
								tempUsername = "";
								tempPassword = "";
								
							}
							
							return "FAIL";
						}
					}
					
					// incase of online login
					
					App.setOfflineMode(false);
					tempUsername = App.getUsername();
					tempPassword = App.getPassword();
					
					App.setUsername (App.get (username));
					App.setPassword (App.get (password));
					
					publishProgress (getResources ().getString (R.string.loading_message_signing_in)); 
					
					final ContentValues values = new ContentValues ();
					
					values.put ("username", App.getUsername());
					values.put ("password", App.getPassword());
					values.put ("starttime", startDateTime);
					values.put ("location", "IHS");
					values.put ("entereddate", App.getSqlDate(enteredDate));
				
					//String exists = serverService.authenticate (RequestType.LOGIN, values);     
					//return exists;
					
					return "SUCCESS";
				}

				@Override
				protected void onProgressUpdate (String... values)
				{
					loading.setMessage (values[0]);
				};

				@Override
				protected void onPostExecute (String result)
				{
					super.onPostExecute (result);
					loading.dismiss ();
					
					String[] resultArray = result.split(":;:");
					
					if (resultArray[0].equals("SUCCESS"))
					{
				
						Date date = new Date();
						Format formatter = new SimpleDateFormat("yyyy-MM-dd");
						String newTimeStamp = formatter.format(date);
						
						App.setLastLogin(newTimeStamp);

						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (LoginActivity.this);
						SharedPreferences.Editor editor = preferences.edit ();
						editor.putString (Preferences.USERNAME, App.getUsername ());
						editor.putString (Preferences.PASSWORD, App.getPassword ());
						editor.putString(Preferences.LAST_LOGIN, App.getLastLogin());
						editor.apply ();
						
						//Start Main Menu Activity
						Intent intent = new Intent (LoginActivity.this, MainMenuActivity.class);
						startActivity (intent);
						finish ();
					}
					else if(resultArray[0].equals("ERROR"))
					{
						
						App.setUsername (tempUsername);
						App.setPassword (tempPassword);
						
						showAlert(resultArray[0] + "\n" + resultArray[1], AlertType.ERROR);
					
					}
					else if(result.equals("CONNECTION_ERROR"))
					{
						
						App.setUsername (tempUsername);
						App.setPassword (tempPassword);
						
						showAlert(getResources ().getString (R.string.data_connection_error), AlertType.ERROR);
					
					}
					else if(result.equals("MISSING_PROVIDER")) 
					{
						App.setUsername (tempUsername);
						App.setPassword (tempPassword);
						
						showAlert(getResources ().getString (R.string.missing_provider), AlertType.ERROR);
					}
					else {
						
						App.setUsername (tempUsername);
						App.setPassword (tempPassword);
						
						showAlert(result, AlertType.ERROR);
						
					}
				}
			};
			authenticationTask.execute ("");
	
		}
		
	}
	
	
	/**
	 * 
	 * Checks validation for the input views
	 * 
	 * @return boolean 
	 */
	public boolean validate(){
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		if (App.get (username).equals (""))
		{
			valid = false;
			message.append (username.getTag () + ". ");
			((EditText) username).setHintTextColor (getResources ().getColor (R.color.Red));
		}
		if (App.get (password).equals (""))
		{
			valid = false;
			message.append (password.getTag () + ". ");
			((EditText) password).setHintTextColor (getResources ().getColor (R.color.Red));
		}
		if (!valid)
		{
			message.append ("\n" + getResources ().getString (R.string.empty_data) + "\n");
		}
		if (!valid)
		{
			showAlert(message.toString (), AlertType.ERROR);
		}
		return valid;
	}
	
	
	@Override
	protected void onStop ()
	{
		super.onStop ();
		finish ();
	}
	
	@Override
	public void onBackPressed ()
	{
		finish ();
	}
	
	public void showAlert(String s, AlertType alertType){
		
		App.getDialog(this, alertType, s).show();
		
	}
}
