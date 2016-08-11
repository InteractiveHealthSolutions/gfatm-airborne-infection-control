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

public class MainMenuActivity extends Activity implements OnClickListener {
	
	
	private ServerService			serverService;
	protected static ProgressDialog	loading;
	
	Button uvgiLightsButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		serverService = new ServerService (getApplicationContext ());
		
		uvgiLightsButton = (Button) findViewById(R.main_id.uvgibutton);
		
		// initializing views
		loading = new ProgressDialog (this);
		
		if(App.isOfflineMode()){  // Disable some forms in offline mode.
			
			 ActionBar actionBar = getActionBar();
			 actionBar.setTitle(actionBar.getTitle() + "-- Offline Mode --");
	
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
		
		
		uvgiLightsButton.setOnClickListener(this);
		
	}
	

	@Override
	public void onClick(View v) {
		
		if(v == uvgiLightsButton){
			
			Intent uvgiMenuIntent = new Intent (this, UvgiMenuActivity.class);
			startActivity (uvgiMenuIntent);
			finish();
		}
		
	}
	
	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed ()
	{
		
		final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources ().getString (R.string.exit_operation));
		App.setDialogTitle(d, getResources ().getString (R.string.exit_application));
		
		Button logoutButton = App.addDialogButton(d, getResources ().getString (R.string.logout), App.dialogButtonPosition.LEFT, App.dialogButtonStatus.NEGATIVE);
		logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit ();
				editor.putBoolean (Preferences.AUTO_LOGIN, false);
				editor.apply ();
				App.setAutoLogin(false);	// AutoLogin disabled
				finish ();
				Intent mainMenuIntent = new Intent (getApplicationContext (), LoginActivity.class);
				startActivity (mainMenuIntent);
				
            }
        });
		
		Button exitButton = App.addDialogButton(d, getResources ().getString (R.string.exit), App.dialogButtonPosition.RIGHT, App.dialogButtonStatus.NEGATIVE);
		exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences (MainMenuActivity.this);
				SharedPreferences.Editor editor = preferences.edit ();
				editor.putBoolean (Preferences.AUTO_LOGIN, true);
				editor.apply ();
				App.setAutoLogin(true);    // AutoLogin enabled
				finish ();
				
            }
        });
		
		App.addDialogButton(d, getResources ().getString (R.string.cancel), App.dialogButtonPosition.CENTER, App.dialogButtonStatus.NEUTRAL);
		
		d.show();
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
	
	
}
