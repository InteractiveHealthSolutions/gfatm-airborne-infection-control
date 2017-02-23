package com.ihsinformatics.aic;

/**
 * 
 * MAIN MENU ACTIVITY CLASS...
 * 
 */

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.Metadata;
import com.ihsinformatics.aic.util.ServerService;

public class MainMenuActivity extends Activity implements OnClickListener {

	private ServerService serverService;
	protected static ProgressDialog loading;

	Button uvgiLightsButton;
	TextView selectedLocation;
	Button locationButton;
	Button formButton;
	TextView formCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		serverService = new ServerService(getApplicationContext());

		uvgiLightsButton = (Button) findViewById(R.main_id.uvgibutton);
		selectedLocation = (TextView) findViewById(R.id.selectedLocation);
		locationButton = (Button) findViewById(R.id.locationButton);
		formButton = (Button) findViewById(R.id.formButton);
		formCount = (TextView) findViewById(R.id.formCount);

		selectedLocation.setText(App.getLocation());
		String[][] forms = serverService.getSavedForms(App.getUsername());
		if (forms != null)
			formCount.setText(String.valueOf(forms.length));
		else
			formCount.setText("0");

		// initializing vi
		loading = new ProgressDialog(this);

		if (App.isOfflineMode()) { // Disable some forms in offline mode.

			ActionBar actionBar = getActionBar();
			actionBar.setTitle("-- Offline Mode --");

		}

		// When online, check if there are offline forms for current user
		if (!App.isOfflineMode()) {
			int count = serverService.countSavedForms(App.getUsername());
			if (count > 0) {
				App.getDialog(this, AlertType.URGENT,
						getResources().getString(R.string.offline_forms),
						Gravity.CENTER_HORIZONTAL).show();
			}
		}

		uvgiLightsButton.setOnClickListener(this);
		locationButton.setOnClickListener(this);
		formButton.setOnClickListener(this);

		Boolean updateMetaData = getIntent().getBooleanExtra("updateMetadata",
				false);
		if (updateMetaData) {
			getMetadata();
		}

	}

	@Override
	public void onClick(View v) {

		if (v == locationButton) {

			ArrayList<String> facilityList = serverService
					.getMetaDataFromLocalDb(Metadata.LOCATION);
			Dialog d = App.getDialog(MainMenuActivity.this,
					"Select Location ....", facilityList);
			d.show();
			d.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					selectedLocation.setText(App.getLocation());
				}
			});

		} else if (v == formButton) {
			Intent savedFormsIntent = new Intent(this, SavedFormsActivity.class);
			startActivity(savedFormsIntent);
			finish();
		} else {
			if (App.getLocation().equals("")
					&& !App.getUsername().equals("guest"))
				App.getDialog(MainMenuActivity.this, AlertType.ERROR,
						getResources().getString(R.string.missing_location),
						Gravity.CENTER_HORIZONTAL).show();
			else {
				if (v == uvgiLightsButton) {

					Intent uvgiMenuIntent = new Intent(this,
							UvgiMenuActivity.class);
					startActivity(uvgiMenuIntent);
					finish();
				}
			}
		}

	}

	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed() {

		final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources()
				.getString(R.string.exit_operation), Gravity.CENTER_HORIZONTAL);
		App.setDialogTitle(d,
				getResources().getString(R.string.exit_application));

		Button logoutButton = App.addDialogButton(d,
				getResources().getString(R.string.logout),
				App.dialogButtonPosition.LEFT, App.dialogButtonStatus.NEGATIVE);
		logoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Preferences.AUTO_LOGIN, false);
				editor.apply();
				App.setAutoLogin(false); // AutoLogin disabled
				finish();
				Intent mainMenuIntent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(mainMenuIntent);

			}
		});

		Button exitButton = App
				.addDialogButton(d, getResources().getString(R.string.exit),
						App.dialogButtonPosition.RIGHT,
						App.dialogButtonStatus.NEGATIVE);
		exitButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(Preferences.AUTO_LOGIN, true);
				editor.apply();
				App.setAutoLogin(true); // AutoLogin enabled
				finish();

			}
		});

		App.addDialogButton(d, getResources().getString(R.string.cancel),
				App.dialogButtonPosition.CENTER, App.dialogButtonStatus.NEUTRAL);

		d.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.menu_id.loctionMenu:
			ArrayList<String> facilityList = serverService
					.getMetaDataFromLocalDb(Metadata.LOCATION);
			Dialog d = App.getDialog(MainMenuActivity.this,
					"Select Location ....", facilityList);
			d.show();
			d.setOnDismissListener(new OnDismissListener() {
				public void onDismiss(DialogInterface dialog) {
					selectedLocation.setText(App.getLocation());
				}
			});
			return true;

		case R.menu_id.updateMetada:
			getMetadata();
			return true;

		case R.menu_id.formsActivity:
			Intent formsIntent = new Intent(this, SavedFormsActivity.class);
			finish();
			startActivity(formsIntent);
			break;
		}

		return true;
	}

	/**
	 * 
	 * Restarts the current activity...
	 */
	private void restartActivity() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}

	@Override
	public void onResume() {
		super.onResume();
		/*
		 * Intent intent = getIntent(); finish(); startActivity(intent);
		 */
	}

	public void getMetadata() {

		// Check connection with server or offline mode
		if (App.isOfflineMode()) {
			App.getDialog(this, AlertType.ERROR,
					getResources().getString(R.string.offline_mode_error),
					Gravity.CENTER_HORIZONTAL).show();
		} else if (!serverService.checkInternetConnection()) {
			App.getDialog(this, AlertType.ERROR,
					getResources().getString(R.string.data_connection_error),
					Gravity.CENTER_HORIZONTAL).show();
		}

		else {
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

					publishProgress(getResources().getString(
							R.string.fetch_from_server));
					serverService.fetchMetadata();
					return "SUCCESS";

				}

				@Override
				protected void onProgressUpdate(String... values) {
					loading.setMessage(values[0]);
				};

				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					loading.dismiss();
					ArrayList<String> facilityList = serverService
							.getMetaDataFromLocalDb(Metadata.LOCATION);
					Dialog d = App.getDialog(MainMenuActivity.this,
							"Select Location ....", facilityList);
					d.show();
					d.setOnDismissListener(new OnDismissListener() {
						public void onDismiss(DialogInterface dialog) {
							selectedLocation.setText(App.getLocation());
						}
					});
				}
			};
			authenticationTask.execute("");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return true;
	}

}
