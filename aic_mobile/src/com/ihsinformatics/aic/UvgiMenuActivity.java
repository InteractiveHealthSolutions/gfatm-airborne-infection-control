package com.ihsinformatics.aic;
 

/**
 * 
 * MAIN MENU ACTIVITY CLASS...
 * 
 */

import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.util.ServerService;
import com.ihsinformatics.aic.R;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class UvgiMenuActivity extends Activity implements OnClickListener {
	
	
	private ServerService			serverService;
	protected static ProgressDialog	loading;
	
	Button uvgiInstallationButton;
	Button uvgiMaintenanceButton;
	Button uvgiTroubleshootLogButton;
	Button uvgiTroubleshootResolutionButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uvgi_menu);
		serverService = new ServerService (getApplicationContext ());
		
		uvgiInstallationButton = (Button) findViewById(R.main_id.installation_form);
		uvgiMaintenanceButton = (Button) findViewById(R.main_id.maintenance_form);
		uvgiTroubleshootLogButton = (Button) findViewById(R.main_id.troubleshootingLog_form);
		uvgiTroubleshootResolutionButton = (Button) findViewById(R.main_id.troubleshootingResolution_form);
		
		// initializing views
		loading = new ProgressDialog (this);
	
		if(App.isOfflineMode()){  // Disable some forms in offline mode.
			
			ActionBar actionBar = getActionBar();
			
			actionBar.setTitle("-- Offline Mode --");
	
		}
		
		// When online, check if there are offline forms for current user
		if (!App.isOfflineMode ())
		{
			int count = serverService.countSavedForms (App.getUsername ());
			if (count > 0)
			{
				App.getDialog(this, AlertType.INFO, getResources().getString(R.string.offline_forms)).show();
			}
		}
		
		uvgiInstallationButton.setOnClickListener(this);
		uvgiMaintenanceButton.setOnClickListener(this);
		uvgiTroubleshootLogButton.setOnClickListener(this);
		uvgiTroubleshootResolutionButton.setOnClickListener(this);
		
	}
	

	
	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed ()
	{
		Intent mainMenuIntent = new Intent (getApplicationContext (), MainMenuActivity.class);
		startActivity (mainMenuIntent);
		finish();
	}

	
	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		
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
	public void onClick(View v) {
		if(v == uvgiInstallationButton){
			
			Intent uvgiInstallationIntent = new Intent (this, UvgiInstallationActivity.class);
			startActivity (uvgiInstallationIntent);
			finish();
			
		}
		else if(v == uvgiMaintenanceButton){
			
			Intent uvgiMaintenanceActivity = new Intent (this, UvgiMaintenanceActivity.class);
			startActivity (uvgiMaintenanceActivity);
			finish();
			
		}
		else  if(v == uvgiTroubleshootLogButton){
			Intent uvgiTroubleshootLogIntent = new Intent (this, UvgiTroubleshootLogActivity.class);
			startActivity (uvgiTroubleshootLogIntent);
			finish();
		}
		else if(v == uvgiTroubleshootResolutionButton){

			Intent UvgiTroubleshootResolutionIntent = new Intent (this, UvgiTroubleshootResolutionActivity.class);
			startActivity (UvgiTroubleshootResolutionIntent);
			finish();
		}
	}
	
}
