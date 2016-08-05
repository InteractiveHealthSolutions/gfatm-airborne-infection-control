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

import com.ihsinformatics.aic.custom.MyButton;
import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.custom.MyRadioButton;
import com.ihsinformatics.aic.custom.MyRadioGroup;
import com.ihsinformatics.aic.custom.MySpinner;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
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
	MySpinner			fixtureNumber;
	
	MyTextView			uniqueIdGeneratedTextView;
	MyEditText			uniqueIdGenerated;
	
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
		
		FORM_NAME = FormType.FEEDBACK;
		TAG = "FeedbackActivity";
		PAGE_COUNT = 1;
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
		facilityName = 	new MySpinner (context, getResources ().getStringArray (R.array.feedback_types), R.string.feedback_type, R.string.option_hint);

		otherFacilityNameTextView = new MyTextView (context, R.style.text, R.string.other_facility);
		
		opdNameTextView = new MyTextView (context, R.style.text, R.string.opd_lights_installed);
		
		otherOpdNameTextView = new MyTextView (context, R.style.text, R.string.other_opd);
		
		opdAreaTextView = new MyTextView (context, R.style.text, R.string.opd_area);
		
		otherOpdAreaTextView = new MyTextView (context, R.style.text, R.string.other_opd_area);
		
		fixtureNumberTextView = new MyTextView (context, R.style.text, R.string.fixture_number);
		
		uniqueIdGeneratedTextView = new MyTextView (context, R.style.text, R.string.unique_id);
		
		threeFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_3_reading);
		
		threeFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		
		sixFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_6_reading);
		
		sixFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		
		sevenFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.ft_7_reading);
		
		sevenFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		
		installtionCompleteTextView = new MyTextView (context, R.style.text, R.string.installation_complete);
		
		
		View[][] viewGroups = {{formDateTextView,formDateButton,facilityNameTextView, facilityName}};
		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup> ();
		for (int i = 0; i < PAGE_COUNT; i++)
		{
			LinearLayout layout = new LinearLayout (context);
			layout.setOrientation (LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++)
			{
				layout.addView (viewGroups[i][j]);
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
		
		View[] setListener = new View[]{firstButton, lastButton, clearButton, saveButton, navigationSeekbar,
										formDateButton};
		
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
		
		views = new View[] {};
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
		saveButton.setText(getResources().getString(R.string.send));
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
		// Not implemented
	}

	@Override
	public boolean onLongClick (View v)
	{
		return false;
	}
}
