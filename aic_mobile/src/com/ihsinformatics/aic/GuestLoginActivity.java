package com.ihsinformatics.aic;

/**
 * 
 * LOGIN ACTIVITY CLASS...
 * 
 */

import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;
import com.ihsinformatics.aic.util.ServerService;

public class GuestLoginActivity extends Activity implements OnClickListener {

	private ServerService serverService;
	protected static ProgressDialog loading;

	EditText name;
	EditText contactNumber;
	EditText email;

	Button guestLogin;

	View[] views;

	String tempUsername;
	String tempPassword;

	String startDateTime;
	Calendar enteredDate;

	int loginAttempt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guest_login);
		serverService = new ServerService(getApplicationContext());

		// initializing views
		loading = new ProgressDialog(this);

		name = (EditText) findViewById(R.guest_id.name);
		contactNumber = (EditText) findViewById(R.guest_id.phone);
		email = (EditText) findViewById(R.guest_id.email);
		guestLogin = (Button) findViewById(R.guest_id.loginButton);

		guestLogin.setOnClickListener(this);

		views = new View[] { guestLogin };
		initView(views);
	}

	public void initView(View[] views) {

		Long tsLong = System.currentTimeMillis() / 1000;
		startDateTime = tsLong.toString();
		enteredDate = Calendar.getInstance();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {

		if (v == guestLogin) {

			login();

		}

	}

	/***
	 * 
	 * Facilitates Login Online/Offline Process
	 */

	public void login() {

		// Check connection with server or offline mode
		if (!serverService.checkInternetConnection()) {
			showAlert(getResources().getString(R.string.data_connection_error),
					AlertType.ERROR);
		}

		else if (validate()) {

			// Authenticate from server
			AsyncTask<String, String, String> authenticationTask = new AsyncTask<String, String, String>() {
				@Override
				protected String doInBackground(String... params) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							loading.setIndeterminate(true);
							loading.setCancelable(false);
							loading.show();
						}
					});

					tempUsername = App.getUsername();
					tempPassword = App.getPassword();

					App.setUsername("guest");
					App.setPassword("");

					publishProgress(getResources().getString(
							R.string.loading_message_signing_in));

					final ContentValues values = new ContentValues();

					values.put("username", "guest");
					values.put("password", "");
					values.put("starttime", startDateTime);
					values.put("location", "IHS");
					values.put("entereddate", App.getSqlDate(enteredDate));

					App.setName("");
					App.setContactNumber("");
					App.setEmail("");

					String exists = serverService.authenticate(
							RequestType.LOGIN, values);
					return exists;

					// return "SUCCESS";
				}

				@Override
				protected void onProgressUpdate(String... values) {
					loading.setMessage(values[0]);
				};

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();

					String[] resultArray = result.split(":;:");

					if (resultArray[0].equals("SUCCESS")) {

						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(GuestLoginActivity.this);
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(Preferences.USERNAME,
								App.getUsername());
						editor.putString(Preferences.PASSWORD,
								App.getPassword());
						editor.putString(Preferences.LAST_LOGIN,
								App.getLastLogin());
						editor.apply();

						App.setName(App.get(name));
						App.setContactNumber(App.get(contactNumber));
						App.setEmail(App.get(email));

						// Start Main Menu Activity
						Intent intent = new Intent(GuestLoginActivity.this,
								MainMenuActivity.class);
						startActivity(intent);
						finish();
					} else if (resultArray[0].equals("ERROR")) {
						App.setUsername(tempUsername);
						App.setPassword(tempPassword);

						showAlert(resultArray[0] + "<br>" + resultArray[1],
								AlertType.ERROR);

					} else if (result.equals("CONNECTION_ERROR")) {

						App.setUsername(tempUsername);
						App.setPassword(tempPassword);

						showAlert(
								getResources().getString(
										R.string.data_connection_error),
								AlertType.ERROR);

					} else if (result.equals("MISSING_PROVIDER")) {
						App.setUsername(tempUsername);
						App.setPassword(tempPassword);

						showAlert(
								getResources().getString(
										R.string.missing_provider),
								AlertType.ERROR);
					} else {

						App.setUsername(tempUsername);
						App.setPassword(tempPassword);

						showAlert(result, AlertType.ERROR);

					}
				}
			};
			authenticationTask.execute("");

		}

	}

	/**
	 * 
	 * Checks validation for the input views
	 * 
	 * @return boolean
	 */
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { name, contactNumber, email };
		for (View view : mandatory) {
			if (view.getVisibility() == View.VISIBLE) {
				if (App.get(view).equals("")) {
					valid = false;
					message.append(view.getTag() + ". ");
					// Turn hint color Red
					((EditText) view).setHintTextColor(getResources().getColor(
							R.color.Red));
				} else {
					// Turn hint color back to Black
					((EditText) view).setHintTextColor(getResources().getColor(
							R.color.DarkGray));
				}
			}
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data)
					+ "<br>");
		}

		Boolean f = true;
		// Contact Number....
		if (App.get(contactNumber).length() != RegexUtil.labTestIdLength) {
			valid = false;
			f = false;
			message.append(contactNumber.getTag() + ". ");
			contactNumber.setTextColor(getResources().getColor(R.color.Red));
		} else
			contactNumber.setTextColor(getResources().getColor(
					R.color.mainTheme));

		// Name...
		if (App.get(name).length() < 3) {
			valid = false;
			f = false;
			message.append(name.getTag() + ". ");
			name.setTextColor(getResources().getColor(R.color.Red));
		} else
			name.setTextColor(getResources().getColor(R.color.mainTheme));

		// Email...
		if (!RegexUtil.isEmailAddress(App.get(email))) {
			valid = false;
			f = false;
			message.append(email.getTag() + ". ");
			email.setTextColor(getResources().getColor(R.color.Red));
		} else
			email.setTextColor(getResources().getColor(R.color.mainTheme));

		if (!f) {
			message.append(getResources().getString(R.string.invalid_data)
					+ "<br>");
		}

		if (!valid) {
			showAlert(message.toString(), AlertType.ERROR);
		}
		return valid;
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	@Override
	public void onBackPressed() {
		Intent LoginActivityIntent = new Intent(this, LoginActivity.class);
		startActivity(LoginActivityIntent);
		finish();
	}

	public void showAlert(String s, AlertType alertType) {

		App.getDialog(this, alertType, s, Gravity.CENTER_HORIZONTAL).show();

	}
}
