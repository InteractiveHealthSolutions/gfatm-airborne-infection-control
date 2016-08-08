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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import com.ihsinformatics.aic.custom.MyButton;
import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.custom.MyRadioButton;
import com.ihsinformatics.aic.custom.MyRadioGroup;
import com.ihsinformatics.aic.custom.MySpinner;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.util.RegexUtil;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiInstallationActivity extends AbstractFragmentActivity
{
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView			formDateTextView;
	MyButton			formDateButton;
	
	MyTextView			facilityNameTextView;
	MySpinner			facilityName;
	
	MyTextView			otherFacilityNameTextView;
	MyEditText			otherFacilityName;			
	
	MyTextView			opdNameTextView;
	MySpinner			opdName;
	
	MyTextView			otherOpdNameTextView;
	MyEditText			otherOpdName;
	
	MyTextView			opdAreaTextView;
	MySpinner			opdArea;
	
	MyTextView			otherOpdAreaTextView;
	MyEditText			otherOpdArea;
	
	MyTextView			fixtureNumberTextView;
	NumberPicker		fixtureNumber;
	
	MyTextView			uniqueIdGeneratedTextView;
	MyEditText			uniqueIdGenerated;
	MyButton			scanBarcodeButton;
	
	MyTextView			threeFtUvMeterReadingTextView;
	MyEditText			threeFtUvMeterReading;
	
	MyTextView			threeFtCorrectReadingTextView;
	MyRadioGroup		threeFtCorrectReading;
	MyRadioButton		yesThreeFtCorrectReading;
	MyRadioButton		noThreeFtCorrectReading;
	
	MyTextView			sixFtUvMeterReadingTextView;
	MyEditText			sixFtUvMeterReading;
	
	MyTextView			sixFtCorrectReadingTextView;
	MyRadioGroup		sixFtCorrectReading;
	MyRadioButton		yesSixFtCorrectReading;
	MyRadioButton		noSixFtCorrectReading;
	
	MyTextView			sevenFtUvMeterReadingTextView;
	MyEditText			sevenFtUvMeterReading;
	
	MyTextView			sevenFtCorrectReadingTextView;
	MyRadioGroup		sevenFtCorrectReading;
	MyRadioButton		yesSevenFtCorrectReading;
	MyRadioButton		noSevenFtCorrectReading;
	
	MyTextView			installtionCompleteTextView;
	MyRadioGroup		installtionComplete;
	MyRadioButton		yesInstalltionComplete;
	MyRadioButton		noInstalltionComplete;

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
		
		FORM_NAME = FormType.UVGI_INSATLLATION;
		TAG = "UVGILightsInstallationActivity";
		PAGE_COUNT = 9;
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
		
		facilityNameTextView = new MyTextView (context, R.style.text, R.string.facility_name);
		facilityName = new MySpinner (context, getResources ().getStringArray (R.array.feedback_types), R.string.facility_name, R.string.option_hint);

		otherFacilityNameTextView = new MyTextView (context, R.style.text, R.string.other_facility);
		otherFacilityName =  new MyEditText(context, R.string.other_facility, R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength, false);
		
		opdNameTextView = new MyTextView (context, R.style.text, R.string.opd_lights_installed);
		opdName = new MySpinner (context, getResources ().getStringArray (R.array.feedback_types), R.string.opd_lights_installed, R.string.option_hint);
		
		otherOpdNameTextView = new MyTextView (context, R.style.text, R.string.other_opd);
		otherOpdName = new MyEditText(context, R.string.other_opd, R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength, false);
		
		opdAreaTextView = new MyTextView (context, R.style.text, R.string.opd_area);
		opdArea = new MySpinner (context, getResources ().getStringArray (R.array.feedback_types), R.string.opd_area, R.string.option_hint);
		
		otherOpdAreaTextView = new MyTextView (context, R.style.text, R.string.other_opd_area);
		otherOpdArea = new MyEditText(context, R.string.other_opd_area, R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength, false);
		
		fixtureNumberTextView = new MyTextView (context, R.style.text, R.string.fixture_number);
		fixtureNumber = new NumberPicker(context);
		fixtureNumber.setMinValue(0);
		fixtureNumber.setMaxValue(30);
		fixtureNumber.setWrapSelectorWheel(false);
		
		uniqueIdGeneratedTextView = new MyTextView (context, R.style.text, R.string.unique_id);
		uniqueIdGenerated = new MyEditText(context, R.string.unique_id, R.string.unique_id_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.idLength, false); 
		scanBarcodeButton = new MyButton (context, R.style.text, R.drawable.form_button, R.string.scan_qr_code, R.string.scan_qr_code);
		
		threeFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_3_reading);
		threeFtUvMeterReading = new MyEditText(context,R.string.ft_3_reading, R.string.ft_3_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		threeFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noThreeFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesThreeFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		threeFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesThreeFtCorrectReading, noThreeFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		
		sixFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_6_reading);
		sixFtUvMeterReading = new MyEditText(context,R.string.ft_6_reading, R.string.ft_6_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		sixFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noSixFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesSixFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		sixFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesSixFtCorrectReading, noSixFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		
		sevenFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_7_reading);
		sevenFtUvMeterReading = new MyEditText(context,R.string.ft_7_reading, R.string.ft_7_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		sevenFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noSevenFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesSevenFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		sevenFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesSevenFtCorrectReading, noSevenFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		
		installtionCompleteTextView = new MyTextView (context, R.style.text, R.string.installation_complete);
		noInstalltionComplete = new MyRadioButton(context, R.string.installation_complete, R.style.radio,R.string.no);
		yesInstalltionComplete = new MyRadioButton(context, R.string.installation_complete, R.style.radio,R.string.yes);
		installtionComplete = new MyRadioGroup(context,new MyRadioButton[] { yesInstalltionComplete, noInstalltionComplete }, R.string.installation_complete,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		
		View[][] viewGroups = {{formDateTextView,formDateButton,facilityNameTextView, facilityName, otherFacilityNameTextView, otherFacilityName},
							   {opdNameTextView, opdName, otherOpdNameTextView, otherOpdName},
							   {opdAreaTextView, opdArea, otherOpdAreaTextView, otherOpdArea},
							   {fixtureNumberTextView, fixtureNumber},
							   {uniqueIdGeneratedTextView, uniqueIdGenerated, scanBarcodeButton},
							   {threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading},
							   {sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading},
							   {sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading},
							   {installtionCompleteTextView, installtionComplete}};
		
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
										formDateButton, scanBarcodeButton,
										facilityName, opdName, opdArea};
		
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
		
		views = new View[] {otherFacilityName, otherOpdName, otherOpdArea, uniqueIdGenerated, 
							yesInstalltionComplete, yesSevenFtCorrectReading, yesSixFtCorrectReading, yesThreeFtCorrectReading};
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
		
		threeFtUvMeterReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
					
				  try {
				 	String val = App.get(threeFtUvMeterReading);
				 	Double valNumber = Double.parseDouble(val);
				 	
				 	if(valNumber < 0.4){
				 		threeFtCorrectReadingTextView.setVisibility(View.GONE);
				 		threeFtCorrectReading.setVisibility(View.GONE);
				 	}
				 	else{
				 		threeFtCorrectReadingTextView.setVisibility(View.VISIBLE);
				 		threeFtCorrectReading.setVisibility(View.VISIBLE);
				 	}
				  }catch (NumberFormatException e) {
					  // p did not contain a valid double
				  }
			  }
			  
	       });
		
		sixFtUvMeterReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				 try{	
				 	String val = App.get(sixFtUvMeterReading);
				 	Double valNumber = Double.parseDouble(val);
				 	
				 	if(valNumber < 0.4){
				 		sixFtCorrectReadingTextView.setVisibility(View.GONE);
				 		sixFtCorrectReading.setVisibility(View.GONE);
				 	}
				 	else{
				 		sixFtCorrectReadingTextView.setVisibility(View.VISIBLE);
				 		sixFtCorrectReading.setVisibility(View.VISIBLE);
				 	}
				  }catch (NumberFormatException e) {
					  // p did not contain a valid double
				  }	
			  }
			  
	       });
		
		sevenFtUvMeterReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
					
				  try{
				 	String val = App.get(sevenFtUvMeterReading);
				 	Double valNumber = Double.parseDouble(val);
				 	
				 	if(valNumber < 0.4){
				 		sevenFtCorrectReadingTextView.setVisibility(View.GONE);
				 		sevenFtCorrectReading.setVisibility(View.GONE);
				 	}
				 	else{
				 		sevenFtCorrectReadingTextView.setVisibility(View.VISIBLE);
				 		sevenFtCorrectReading.setVisibility(View.VISIBLE);
				 	}
				  }catch (NumberFormatException e) {
					  // p did not contain a valid double
				  }	
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
		
		View[] goneView = new View[]{otherFacilityNameTextView, otherFacilityName, otherOpdNameTextView, otherOpdName, otherOpdAreaTextView, otherOpdArea,
				threeFtCorrectReadingTextView, threeFtCorrectReading, sixFtCorrectReadingTextView, sixFtCorrectReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading};
		for(View v : goneView){
			v.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean validate ()
	{
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		View[] mandatory = {};
		for (View view : mandatory)
		{
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
		if (!valid)
		{
			message.append (getResources ().getString (R.string.empty_data) + "\n");
		}
		// Validate data
		// Validate range
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
						App.getDialog (UvgiInstallationActivity.this, AlertType.SUCCESS, getResources ().getString (R.string.feedback_send_success)).show ();
						initView (views);
					}
					else
					{
						App.getDialog (UvgiInstallationActivity.this, AlertType.ERROR, result).show ();
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
		MySpinner spinner = (MySpinner) parent;
		if(spinner == facilityName){
			if(App.get(spinner).equalsIgnoreCase("Other")){
				otherFacilityNameTextView.setVisibility(View.VISIBLE);
				otherFacilityName.setVisibility(View.VISIBLE);
			}
			else{
				otherFacilityNameTextView.setVisibility(View.GONE);
				otherFacilityName.setVisibility(View.GONE);
			}
		}
		else if(spinner == opdName){
			if(App.get(spinner).equalsIgnoreCase("Other")){
				otherOpdNameTextView.setVisibility(View.VISIBLE);
				otherOpdName.setVisibility(View.VISIBLE);
			}
			else{
				otherOpdNameTextView.setVisibility(View.GONE);
				otherOpdName.setVisibility(View.GONE);
			}
		}
		else if(spinner == opdArea){
			if(App.get(spinner).equalsIgnoreCase("Other")){
				otherOpdAreaTextView.setVisibility(View.VISIBLE);
				otherOpdArea.setVisibility(View.VISIBLE);
			}
			else{
				otherOpdAreaTextView.setVisibility(View.GONE);
				otherOpdArea.setVisibility(View.GONE);
			}
		}
		
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
				if (RegexUtil.isValidId(str)
						&& !RegexUtil.isNumeric(str, false)) {
					uniqueIdGenerated.setText(str);
				} else {
					App.getDialog(
							this,
							AlertType.ERROR,
							uniqueIdGenerated.getTag().toString()
									+ ": "
									+ getResources().getString(
											R.string.invalid_data)).show();
				}
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
