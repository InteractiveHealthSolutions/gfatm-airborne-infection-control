/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * 
 */

package com.ihsinformatics.aic;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.aic.custom.MyButton;
import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.util.RegexUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiTroubleshootLogActivity extends AbstractFragmentActivity
{
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView			formDateTextView;
	MyButton			formDateButton;

	MyTextView			uniqueIdGeneratedTextView;
	MyEditText			uniqueIdGenerated;
	MyButton			scanBarcodeButton;
	
	MyTextView 			problemTextView;
	MyEditText 			problem;

	MyTextView 			mobileNumberTextView;
	MyEditText 			mobileNumber;

	MyTextView 			troubleshootingNumberTextView;
	MyEditText 			troubleshootingNumber;


	/**
	 * Subclass representing Fragment for feedback form
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class FeedbackFragment extends Fragment
	{
		int	currentPage;

		@Override
		public void onCreate (Bundle savedInstanceState)
		{
			super.onCreate (savedInstanceState);
			Bundle data = getArguments ();
			currentPage = data.getInt ("current_page", 0);
		}

		@Override
		public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			// Return a layout of views from pre-filled ArrayList of groups
			if (currentPage != 0 && groups.size () != 0)
				return groups.get (currentPage - 1);
			else
				return null;
		}
	}

	/**
	 * Subclass for Pager Adapter. Uses FeedbackFragment subclass as items
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class FeedbackFragmentPagerAdapter extends FragmentPagerAdapter
	{
		/** Constructor of the class */
		public FeedbackFragmentPagerAdapter (FragmentManager fragmentManager)
		{
			super (fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem (int arg0)
		{
			FeedbackFragment fragment = new FeedbackFragment ();
			Bundle data = new Bundle ();
			data.putInt ("current_page", arg0 + 1);
			fragment.setArguments (data);
			return fragment;
		}

		/** Returns the number of pages */
		@Override
		public int getCount ()
		{
			return PAGE_COUNT;
		}
	}

	@Override
	public void createViews (final Context context)
	{
		
		FORM_NAME = FormType.UVGI_TROUBLESHOOT_LOG;
		TAG = "UVGITroubleshootLogActivity";
		PAGE_COUNT = 3;
		pager = (ViewPager) findViewById (R.template_id.pager);
		navigationSeekbar.setMax (PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById (R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2)
		{
			navigatorLayout.setVisibility (View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager ();
		FeedbackFragmentPagerAdapter pagerAdapter = new FeedbackFragmentPagerAdapter (fragmentManager);
		pager.setAdapter (pagerAdapter);
		pager.setOffscreenPageLimit (PAGE_COUNT);
		
		// Create views for pages
		formDateTextView = new MyTextView (context, R.style.text, R.string.form_date);
		formDateButton = new MyButton (context, R.style.text, R.drawable.form_button, R.string.form_date, R.string.form_date);
		
		uniqueIdGeneratedTextView = new MyTextView (context, R.style.text, R.string.unique_id);
		uniqueIdGenerated = new MyEditText(context, R.string.unique_id, R.string.unique_id_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.idLength, false); 
		scanBarcodeButton = new MyButton (context, R.style.text, R.drawable.form_button, R.string.scan_qr_code, R.string.scan_qr_code);
		
		problemTextView = new MyTextView (context, R.style.text, R.string.problem_with_fixture);
		problem = new MyEditText(context,R.string.problem_with_fixture, R.string.problem_with_fixture_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, 100, false);
		problem.setSingleLine(false);
		problem.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		problem.setMinLines (8);
		problem.setMaxHeight (10);
		problem.setGravity(Gravity.TOP);
		
		mobileNumberTextView = new MyTextView (context, R.style.text, R.string.mobile_number);
		mobileNumber = new MyEditText(context,R.string.mobile_number, R.string.mobile_number_hint, InputType.TYPE_CLASS_NUMBER, R.style.edit, 11, false);

		troubleshootingNumberTextView = new MyTextView (context, R.style.text, R.string.troubleshooting_number);
		troubleshootingNumber = new MyEditText(context,R.string.troubleshooting_number, R.string.troubleshooting_number_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, 11, false);
		

		View[][] viewGroups = {{formDateTextView,formDateButton,uniqueIdGeneratedTextView, uniqueIdGenerated, scanBarcodeButton},
							   {problemTextView, problem},
							   { mobileNumberTextView, mobileNumber, troubleshootingNumberTextView, troubleshootingNumber}
							   };
		
		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup> ();
		for (int i = 0; i < PAGE_COUNT; i++)
		{
			LinearLayout layout = new LinearLayout (context);
			layout.setOrientation (LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++)
			{
				
				View v = viewGroups[i][j];
				
				if(j%2 == 0){
					
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 30, 0, 0); 
					v.setLayoutParams(params);
					
				}
				
				layout.addView(v);
			}
			ScrollView scrollView = new ScrollView (context);
			scrollView.setLayoutParams (new LayoutParams (LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			scrollView.addView (layout);
			groups.add (scrollView);
		}
		// Set event listeners
		firstButton.setOnClickListener (this);
		lastButton.setOnClickListener (this);
		clearButton.setOnClickListener (this);
		saveButton.setOnClickListener(this);
		navigationSeekbar.setOnSeekBarChangeListener (this);
		
		View[] setListener = new View[]{firstButton, lastButton, clearButton, saveButton, navigationSeekbar, nextButton,
										formDateButton, scanBarcodeButton};
		
		for (View v : setListener) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			} else if (v instanceof RadioGroup) {
				((RadioGroup) v).setOnClickListener(this);
			} else if (v instanceof Button) {
				((Button) v).setOnClickListener(this);
			}  else if (v instanceof RadioButton) {
				((RadioButton) v).setOnClickListener(this);
			} else if (v instanceof ImageButton) {
				((ImageButton) v).setOnClickListener(this);
			}
		}
		
		pager.setOnPageChangeListener (this);
		
		views = new View[] {uniqueIdGenerated};
		// Detect RTL language
		if (App.isLanguageRTL ())
		{
			Collections.reverse (groups);
			for (ViewGroup g : groups)
			{
				LinearLayout linearLayout = (LinearLayout) g.getChildAt (0);
				linearLayout.setGravity (Gravity.RIGHT);
			}
			for (View v : views)
			{
				if (v instanceof EditText)
				{
					((EditText) v).setGravity (Gravity.RIGHT);
				}
			}
		}
		
		uniqueIdGenerated.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(problem).equals("")) && !(App.get(mobileNumber).equals("")) &&
						  !(App.get(troubleshootingNumber).equals("")) && !(App.get(uniqueIdGenerated).equals(""))){
					  saveButton.setEnabled(true);
				  }
				  else
					  saveButton.setEnabled(false);
				
			  }
	       });
		
		problem.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(problem).equals("")) && !(App.get(mobileNumber).equals("")) &&
						  !(App.get(troubleshootingNumber).equals("")) && !(App.get(uniqueIdGenerated).equals(""))){
					  saveButton.setEnabled(true);
				  }
				  else
					  saveButton.setEnabled(false);
				
			  }
	       });
		
		mobileNumber.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(problem).equals("")) && !(App.get(mobileNumber).equals("")) &&
						  !(App.get(troubleshootingNumber).equals("")) && !(App.get(uniqueIdGenerated).equals(""))){
					  saveButton.setEnabled(true);
				  }
				  else
					  saveButton.setEnabled(false);
				
			  }
	       });
		
		troubleshootingNumber.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(problem).equals("")) && !(App.get(mobileNumber).equals("")) &&
						  !(App.get(troubleshootingNumber).equals("")) && !(App.get(uniqueIdGenerated).equals(""))){
					  saveButton.setEnabled(true);
				  }
				  else
					  saveButton.setEnabled(false);
				
			  }
	       });

	}

	@Override
	public void initView (View[] views)
	{
		super.initView (views);
		formDate = Calendar.getInstance ();
		Date date = new Date();
		formDate.setTime(date);
		
		updateDisplay ();
	}

	@Override
	public void updateDisplay ()
	{
		formDateButton.setText (DateFormat.format ("dd-MMM-yyyy", formDate));
		

	}

	@Override
	public boolean validate ()
	{
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		View[] mandatory = {uniqueIdGenerated,problem,mobileNumber,troubleshootingNumber};
		for (View view : mandatory)
		{
			if(view.getVisibility() == View.VISIBLE){
				if (App.get (view).equals (""))
				{
					valid = false;
					message.append (view.getTag () + ". ");
					// Turn hint color Red
					((EditText) view).setHintTextColor (getResources ().getColor (R.color.Red));
				}
				else
				{
					// Turn hint color back to Black
					((TextView) view).setHintTextColor (getResources ().getColor (R.color.Black));
				}
			}
		}
		if (!valid)
		{
			message.append (getResources ().getString (R.string.empty_data) + "\n");
		}
		
		// Check ID
		/*if (!RegexUtil.isValidId(App.get(uniqueIdGenerated))) {
			valid = false;
			message.append(uniqueIdGenerated.getTag().toString()
					+ ": "
					+ getResources().getString(
							R.string.invalid_data) + "\n");
			uniqueIdGenerated.setTextColor(getResources().getColor(
					R.color.Red));
		}*/
					
		// Check Date
		try {
			// Form date range
			if (formDate.getTime().after(new Date())) {
				valid = false;
				message.append(formDateButton.getTag()
						+ ": "
						+ getResources().getString(
								R.string.invalid_date_or_time) + "\n");
			}
			
		} catch (NumberFormatException e) { }
				
		if (!valid)
		{
			App.getDialog (this, AlertType.ERROR, message.toString ()).show ();
		}
		return valid;
	}

	public boolean submit ()
	{
		if (validate ())
		{
			final ContentValues values = new ContentValues ();
			values.put ("formDate", App.getSqlDate (formDate));
			values.put ("location", App.getLocation ());
			
			AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String> ()
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
							loading.setMessage (getResources ().getString (R.string.loading_message_saving_trees));
							loading.show ();
						}
					});
					
					//String result = serverService.saveFeedback (FORM_NAME, values);
					String result = "SUCCESS";
					return result;
				}

				@Override
				protected void onProgressUpdate (String... values)
				{
				};

				@Override
				protected void onPostExecute (String result)
				{
					super.onPostExecute (result);
					if (result.equals ("SUCCESS"))
					{
						App.getDialog (UvgiTroubleshootLogActivity.this, AlertType.SUCCESS, FORM_NAME + " " + getResources ().getString (R.string.form_send_success)).show ();
						initView (views);
					}
					else
					{
						App.getDialog (UvgiTroubleshootLogActivity.this, AlertType.ERROR, result).show ();
					}
					loading.dismiss ();
				}
			};
			updateTask.execute ("");
		}
		return true;
	}

	@Override
	public void onClick (View view)
	{
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		} 
		else if (view == firstButton)
		{
			gotoFirstPage ();
		}
		else if (view == lastButton)
		{
			gotoLastPage ();
		}
		else if (view == nextButton){
			gotoNextPage();
		}
		else if (view == clearButton)
		{
			final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources ().getString (R.string.clear_close));
			App.setDialogTitle(d, getResources ().getString (R.string.clear_form));
			
			Button yesButton = App.addDialogButton(d, getResources ().getString (R.string.yes), App.dialogButtonPosition.LEFT, App.dialogButtonStatus.POSITIVE);
			yesButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	
	            	d.dismiss();
	            	initView(views);
	            	
					
	            }
	        });
			
			App.addDialogButton(d, getResources ().getString (R.string.no), App.dialogButtonPosition.CENTER, App.dialogButtonStatus.NEGATIVE);
			
			d.show ();
		}
		else if (view == saveButton)
		{
			final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources ().getString (R.string.save_close));
			App.setDialogTitle(d, getResources ().getString (R.string.save_form));
			
			Button yesButton = App.addDialogButton(d, getResources ().getString (R.string.yes), App.dialogButtonPosition.LEFT, App.dialogButtonStatus.POSITIVE);
			yesButton.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	
	            	d.dismiss();
	            	submit();
	            	
	            }
	        });
			
			App.addDialogButton(d, getResources ().getString (R.string.no), App.dialogButtonPosition.CENTER, App.dialogButtonStatus.NEGATIVE);
			
			d.show ();
		}
		else if (view == scanBarcodeButton) {
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
	}
	
	/**
	 * Shows confirmation dialog, in case the back button was pressed
	 * accidentally during form activity
	 */
	@Override
	public void onBackPressed ()
	{
		final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources ().getString (R.string.confirm_close));
		App.setDialogTitle(d, getResources ().getString (R.string.close_form));
		
		Button yesButton = App.addDialogButton(d, getResources ().getString (R.string.yes), App.dialogButtonPosition.LEFT, App.dialogButtonStatus.POSITIVE);
		yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            		
            	finish ();
				Intent uvgiMenuIntent = new Intent (getApplicationContext (), UvgiMenuActivity.class);
				startActivity (uvgiMenuIntent);
				
            }
        });
		
		App.addDialogButton(d, getResources ().getString (R.string.no), App.dialogButtonPosition.CENTER, App.dialogButtonStatus.NEGATIVE);
		
		d.show ();
	}

	@Override
	public void onCheckedChanged (CompoundButton button, boolean state)
	{
		// Not implemented
	}

	@Override
	public void onItemSelected (AdapterView<?> parent, View view, int position, long id)
	{

		
	}

	@Override
	public boolean onLongClick (View v)
	{
		return false;
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
					uniqueIdGenerated.setText(str);
				/*} else {
					App.getDialog(this, AlertType.ERROR, uniqueIdGenerated.getTag().toString()+ ": " + getResources().getString(R.string.invalid_data)).show();
				}*/
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				App.getDialog(this, AlertType.ERROR,
						getResources().getString(R.string.operation_cancelled))
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
}
