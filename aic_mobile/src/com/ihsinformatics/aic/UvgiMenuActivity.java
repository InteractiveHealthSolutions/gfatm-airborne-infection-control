package com.ihsinformatics.aic;

/**
 * 
 * MAIN MENU ACTIVITY CLASS...
 * 
 */

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.ihsinformatics.aic.shared.Privilege;
import com.ihsinformatics.aic.util.ServerService;

public class UvgiMenuActivity extends Activity implements OnClickListener {

	private ServerService serverService;
	protected static ProgressDialog loading;

	Button uvgiInstallationButton;
	Button uvgiMaintenanceButton;
	Button uvgiTroubleshootLogButton;
	Button uvgiTroubleshootStatusUpdateButton;
	Button uvgiTroubleshootStatusButton;
	Button uvgiTroubleshootResolutionButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uvgi_menu);
		serverService = new ServerService(getApplicationContext());

		uvgiInstallationButton = (Button) findViewById(R.main_id.installation_form);
		uvgiMaintenanceButton = (Button) findViewById(R.main_id.maintenance_form);
		uvgiTroubleshootLogButton = (Button) findViewById(R.main_id.troubleshootingLog_form);
		uvgiTroubleshootStatusUpdateButton = (Button) findViewById(R.main_id.troubleshootingStatusUpdate_form);
		uvgiTroubleshootStatusButton = (Button) findViewById(R.main_id.troubleshootingStatus_form);
		uvgiTroubleshootResolutionButton = (Button) findViewById(R.main_id.troubleshootingResolution_form);

		// initializing views
		loading = new ProgressDialog(this);

		if (App.isOfflineMode()) { // Disable some forms in offline mode.

			ActionBar actionBar = getActionBar();

			actionBar.setTitle("-- Offline Mode --");

		}

		uvgiInstallationButton.setOnClickListener(this);
		uvgiMaintenanceButton.setOnClickListener(this);
		uvgiTroubleshootLogButton.setOnClickListener(this);
		uvgiTroubleshootStatusUpdateButton.setOnClickListener(this);
		uvgiTroubleshootStatusButton.setOnClickListener(this);
		uvgiTroubleshootResolutionButton.setOnClickListener(this);

		if (serverService.userHasPrivilge(Privilege.AIC_UVGI_FORMS)) {
			uvgiInstallationButton.setVisibility(View.VISIBLE);
			uvgiMaintenanceButton.setVisibility(View.VISIBLE);
			uvgiTroubleshootResolutionButton.setVisibility(View.VISIBLE);
		} else {
			uvgiInstallationButton.setVisibility(View.GONE);
			uvgiMaintenanceButton.setVisibility(View.GONE);
			uvgiTroubleshootResolutionButton.setVisibility(View.GONE);
		}

		if (serverService.userHasPrivilge(Privilege.AIC_UVGI_STATUS)) {
			uvgiTroubleshootStatusUpdateButton.setVisibility(View.VISIBLE);
			uvgiTroubleshootStatusButton.setVisibility(View.GONE);
		} else {
			uvgiTroubleshootStatusUpdateButton.setVisibility(View.GONE);
			uvgiTroubleshootStatusButton.setVisibility(View.VISIBLE);
		}

		if (App.isOfflineMode())
			uvgiTroubleshootStatusButton.setVisibility(View.GONE);

	}

	/**
	 * Shows options to Exit and Log out
	 */
	@Override
	public void onBackPressed() {
		Intent mainMenuIntent = new Intent(getApplicationContext(),
				MainMenuActivity.class);
		startActivity(mainMenuIntent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

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
		if (v == uvgiInstallationButton) {

			Intent uvgiInstallationIntent = new Intent(this,
					UvgiInstallationActivity.class);
			startActivity(uvgiInstallationIntent);
			finish();

		} else if (v == uvgiMaintenanceButton) {

			Intent uvgiMaintenanceActivity = new Intent(this,
					UvgiMaintenanceActivity.class);
			startActivity(uvgiMaintenanceActivity);
			finish();

		} else if (v == uvgiTroubleshootLogButton) {
			Intent uvgiTroubleshootLogIntent = new Intent(this,
					UvgiTroubleshootLogActivity.class);
			startActivity(uvgiTroubleshootLogIntent);
			finish();
		} else if (v == uvgiTroubleshootResolutionButton) {

			Intent UvgiTroubleshootResolutionIntent = new Intent(this,
					UvgiTroubleshootResolutionActivity.class);
			startActivity(UvgiTroubleshootResolutionIntent);
			finish();
		} else if (v == uvgiTroubleshootStatusUpdateButton) {

			Intent uvgiTroubleshootStatusUpdateButton = new Intent(this,
					UvgiTroubleshootStatusUpdateActivity.class);
			startActivity(uvgiTroubleshootStatusUpdateButton);
			finish();
		} else if (v == uvgiTroubleshootStatusButton) {

			Intent uvgiTroubleshootStatusButton = new Intent(this,
					UvgiTroubleshootStatusActivity.class);
			startActivity(uvgiTroubleshootStatusButton);
			finish();
		}
	}

}
