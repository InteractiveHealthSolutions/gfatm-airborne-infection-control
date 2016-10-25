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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.bouncycastle.crypto.engines.ISAACEngine;

import com.ihsinformatics.aic.R.status_id;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;
import com.ihsinformatics.aic.util.ServerService;
import com.ihsinformatics.aic.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UvgiTroubleshootStatusActivity extends Activity implements OnClickListener {
	
	private ServerService			serverService;
	protected static ProgressDialog	loading;
	
	LinearLayout					mainLayout;
	EditText						id;
	EditText						troubleshootNumber;
	Button							scanQRCode;
	Button							getStatus;
	Button							clear;
	
	View[]							views;
	
	
	
	int loginAttempt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_troubleshoot_status);
		serverService = new ServerService (getApplicationContext ());
		
		// initializing views
		loading = new ProgressDialog (this);
		mainLayout = (LinearLayout) findViewById(R.status_id.mainLayout);
		id = (EditText) findViewById(R.status_id.uniqueIdEditText);
		troubleshootNumber = (EditText) findViewById(R.status_id.troubleshootIdEditText);
		scanQRCode = (Button) findViewById(R.status_id.scanButton);
		getStatus = (Button) findViewById(R.status_id.getStatusButton);
		clear = (Button) findViewById(R.status_id.clearButton);
		
		scanQRCode.setOnClickListener(this);
		getStatus.setOnClickListener(this);
		clear.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater ().inflate (R.menu.menu, menu);
		return true;
	}


	@Override
	public void onClick(View v) {
		
		if(v == clear){
			id.setText("");
			troubleshootNumber.setText("");
		}
		if(v == scanQRCode){
			try {
	        	Intent intent = new Intent(Barcode.BARCODE_INTENT);
	        	if(isCallable(intent)){
	        		intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
					startActivityForResult(intent, Barcode.BARCODE_RESULT);
	        	}
	        	else{
	        		showAlert(getResources().getString(R.string.barcode_scanner_missing),AlertType.ERROR);
	        	}
			} catch (ActivityNotFoundException e) {
				showAlert(getResources().getString(R.string.barcode_scanner_missing),AlertType.ERROR);
			}
		}
		else if (v == getStatus){
			
			// Check connection with server or offline mode
			if (!serverService.checkInternetConnection ())
			{
				showAlert(getResources ().getString (R.string.data_connection_error), AlertType.ERROR);
			}
			else{
				if(App.get(id).equals("")){
					id.setHintTextColor(getResources().getColor(R.color.Red));
					showAlert( id.getTag()+ ": " + getResources ().getString (R.string.empty_data),AlertType.ERROR);
				}
				else{
				AsyncTask<String, String, HashMap<String, String>> updateTask = new AsyncTask<String, String, HashMap<String, String>> ()
				{
	
					@Override
					protected HashMap<String, String> doInBackground(String... params) {
						runOnUiThread (new Runnable ()
						{
							@Override
							public void run ()
							{
								loading.setIndeterminate (true);
								loading.setCancelable (false);
								loading.setMessage (getResources ().getString (R.string.loading_message_saving_trees));
								loading.show ();
							}
						});
						
						
						HashMap<String, String> hm = serverService.getUVGITroubleshootStatusRecord (App.get(id), App.get(troubleshootNumber));
						//String result = "SUCCESS";
						return hm;
					}
					
					@Override
					protected void onProgressUpdate (String... values)
					{
					};
	
					@Override
					protected void onPostExecute (HashMap<String, String> result)
					{
						super.onPostExecute (result);
						id.setHintTextColor(getResources().getColor(R.color.mainTheme));
						if(result.get("status").equals("SUCCESS")){
							String resultString = "<b>UVGI Light Id:</b> " + result.get("id") +
									"<br> <b>Troubleshoot Id:</b> " + result.get("troubleshootId") + "<br>" +
									"<br> <b>Location:</b> " + result.get("location") + 
									"<br> <b>OPD:</b> " + result.get("opd") + 
									"<br> <b>OPD Area:</b> " +result.get("opd_area") +
									"<br><br> <b>Problem:</b> " +result.get("problem");
							          
							if(!result.get("no").equals("0")){
								int no = Integer.parseInt(result.get("no"));
								resultString = resultString + "<br><br> <b><u> Status Update </u></b>";
								
								for(int i=1; i<=no; i++){
									resultString = resultString + "<br>" + result.get("status_"+i) + " - " + result.get("status_date_"+i);
								}
							}
							
							App.getDialog (UvgiTroubleshootStatusActivity.this, AlertType.INFO, resultString, Gravity.LEFT).show ();
						}
						else{
							if(!result.containsKey("troubleshoot_no"))
								App.getDialog (UvgiTroubleshootStatusActivity.this, AlertType.ERROR, result.get("details"), Gravity.CENTER_HORIZONTAL).show ();
							else{
								
								if(!App.get(troubleshootNumber).equals("")){
									String message = result.get("details");
									
									int no = Integer.parseInt(result.get("troubleshoot_no"));
									if(no == 0){
										message = message + "<br><br>" +
															"No logged complaint found in system for uvgi light id: " + App.get(id);
									}
									else{
										message = message + "<br><br>" +
												"Complaint found in system for uvgi light id: " + App.get(id) + "<br>" +
												"<font size=\"1\">" + "<i>" + "(Long press to copy troubleshoot number)" + "</i>" + "<\font>";
									}
									
									final Dialog d = App.getDialog (UvgiTroubleshootStatusActivity.this, AlertType.ERROR, message, Gravity.CENTER_HORIZONTAL);
									
									for(int i = 0; i < no; i++){
										
										final TextView text = App.addTroubleshootId(d, result.get("troubleshoot_"+i));
										
										text.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.large));
										LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
										params.setMargins(20, 0, 20, 10); 
										text.setLayoutParams(params);
										
										text.setOnLongClickListener(new OnLongClickListener() {
	
									        @Override
									        public boolean onLongClick(View v) {
									            
									        	String txt = text.getText().toString();
									        	d.dismiss();
									        	troubleshootNumber.setText(txt);
									        	
									        	setClipboard(getApplicationContext(),txt);
									        	
									            return false;
									        }
									});
									}
									d.show();
								}
								else{
									String message = result.get("details");
									
									int no = Integer.parseInt(result.get("troubleshoot_no"));
									if(no == 0){
										message = message + "<br><br>" +
															"No logged complaint found in system for uvgi light id: " + App.get(id);
									}
									else{
										message = message + "<br><br>" +
												"Complaint found in system for uvgi light id: " + App.get(id) + "<br>" +
												"<font size=\"1\">" + "<i>" + "(Long press to copy troubleshoot number)" + "</i>" + "<\font>";
									}
									
									final Dialog d = App.getDialog (UvgiTroubleshootStatusActivity.this, AlertType.SUCCESS, message, Gravity.CENTER_HORIZONTAL);
									
									for(int i = no-1; i >= 0; i--){
										final TextView text = App.addTroubleshootId(d, result.get("troubleshoot_"+i));
										
										text.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.large));
										LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
										params.setMargins(20, 0, 20, 10); 
										text.setLayoutParams(params);
										
										text.setOnLongClickListener(new OnLongClickListener() {
	
									        @Override
									        public boolean onLongClick(View v) {
									            
									        	String txt = text.getText().toString();
									        	d.dismiss();
									        	troubleshootNumber.setText(txt);
									        	
									        	setClipboard(getApplicationContext(),txt);
									        	
									            return false;
									        }
									});
									}
									d.show();
								}
							}
						}
						
						loading.dismiss ();
					}
					
				};
				updateTask.execute ("");
			}
		 }
	  }
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// Retrieve barcode scan results
		if (requestCode == Barcode.BARCODE_RESULT) {
			if (resultCode == RESULT_OK) {
				String str = data.getStringExtra(Barcode.SCAN_RESULT);
				// Check for valid Id
				/*if (RegexUtil.isValidId(str)
						&& !RegexUtil.isNumeric(str, false)) {*/
					id.setText(str);
				/*} else {
					App.getDialog(this, AlertType.ERROR, uniqueIdGenerated.getTag().toString()+ ": " + getResources().getString(R.string.invalid_data)).show();
				}*/
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				App.getDialog(this, AlertType.ERROR,
						getResources().getString(R.string.operation_cancelled),Gravity.CENTER_HORIZONTAL)
						.show();
			}
			// Set the locale again, since the Barcode app restores system's
			// locale because of orientation
			Locale.setDefault(App.getCurrentLocale());
			Configuration config = new Configuration();
			config.locale = App.getCurrentLocale();
			getApplicationContext().getResources().updateConfiguration(config,
					null);
		}
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
		Intent uvgiMenuIntent = new Intent (this, UvgiMenuActivity.class);
		startActivity (uvgiMenuIntent);
		finish ();
	}
	
	public void showAlert(String s, AlertType alertType){
		
		App.getDialog(this, alertType, s, Gravity.CENTER_HORIZONTAL).show();
		
	}
	
	public boolean isCallable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 
            PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
	}
	
	private void setClipboard(Context context,String text) {
	    if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
	        android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	        clipboard.setText(text);
	    } else {
	        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
	        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
	        clipboard.setPrimaryClip(clip);
	    }
	}
}
