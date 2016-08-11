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
import android.graphics.Typeface;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiMaintenanceActivity extends AbstractFragmentActivity
{
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView			formDateTextView;
	MyButton			formDateButton;
	
	MyTextView			uniqueIdGeneratedTextView;
	MyEditText			uniqueIdGenerated;
	MyButton			scanBarcodeButton;
	
	MyTextView 			threeFtUvMeterBeforeReadingTextView;
	MyEditText 			threeFtUvMeterBeforeReading;
	
	MyTextView 			sixFtUvMeterBeforeReadingTextView;
	MyEditText 			sixFtUvMeterBeforeReading;
	
	MyTextView 			sevenFtUvMeterBeforeReadingTextView;
	MyEditText 			sevenFtUvMeterBeforeReading;
	
	MyTextView          maintenanceChecklistHeading;

	MyTextView 			powerDisconnectedTextView;
	MyRadioGroup 		powerDisconnected;
	MyRadioButton		yesPowerDisconnected;
	MyRadioButton 		noPowerDisconnected;

	MyTextView 			louverOpenedTextView;
	MyRadioGroup 		louverOpened;
	MyRadioButton 		yesLouverOpened;
	MyRadioButton 		noLouverOpened;

	MyTextView 			lampsRemovedTextView;
	MyRadioGroup 		lampsRemoved;
	MyRadioButton 		yesLampsRemoved;
	MyRadioButton 		noLampsRemoved;

	MyTextView 			lampsMicrofiberTextView;
	MyRadioGroup 		lampsMicrofiber;
	MyRadioButton 		yesLampsMicrofiber;
	MyRadioButton 		noLampsMicrofiber;

	MyTextView 			interiorMicrofiberTextView;
	MyRadioGroup 		interiorMicrofiber;
	MyRadioButton 		yesInteriorMicrofiber;
	MyRadioButton 		noInteriorMicrofiber;

	MyTextView 			partsReplacedTextView;
	MyRadioGroup 		partReplaced;
	MyRadioButton 		yesPartReplaced;
	MyRadioButton 		noPartReplaced;

	MyTextView 			replacedPartNameTextView;
	MyEditText 			replacedPartName;

	MyTextView 			lampInstalledCorrectlyTextView;
	MyRadioGroup 		lampInstalledCorrectly;
	MyRadioButton 		yesLampInstalledCorrectly;
	MyRadioButton 		noLampInstalledCorrectly;

	MyTextView 			louverClosedTextView;
	MyRadioGroup 		louverClosed;
	MyRadioButton 		yesLouverClosed;
	MyRadioButton 		noLouverClosed;

	MyTextView 			powerConnectTextView;
	MyRadioGroup 		powerConnect;
	MyRadioButton 		yesPowerConnect;
	MyRadioButton 		noPowerConnect;

	MyTextView 			lightWorkingTextView;
	MyRadioGroup 		lightWorking;
	MyRadioButton 		yesLightorking;
	MyRadioButton 		noLightWorking;

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

	MyTextView 			maintenancePersonNameTextView;
	MyEditText 			maintenancePersonName;


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
		
		FORM_NAME = FormType.UVGI_MAINTENANCE;
		TAG = "UVGILightMaintenanceActivity";
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
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit (PAGE_COUNT);
		
		// Create views for pages
		formDateTextView = new MyTextView (context, R.style.text, R.string.form_date);
		formDateButton = new MyButton (context, R.style.text, R.drawable.form_button, R.string.form_date, R.string.form_date);
		
		uniqueIdGeneratedTextView = new MyTextView (context, R.style.text, R.string.unique_id_maintenance);
		uniqueIdGenerated = new MyEditText(context, R.string.unique_id_maintenance, R.string.unique_id_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.idLength, false); 
		scanBarcodeButton = new MyButton (context, R.style.text, R.drawable.form_button, R.string.scan_qr_code, R.string.scan_qr_code);
		
		threeFtUvMeterBeforeReadingTextView = new MyTextView (context, R.style.text, R.string.reading_before_3ft_cleaning);
		threeFtUvMeterBeforeReading = new MyEditText(context,R.string.reading_before_3ft_cleaning, R.string.ft_3_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		sixFtUvMeterBeforeReadingTextView = new MyTextView (context, R.style.text, R.string.reading_before_6ft_cleaning);
		sixFtUvMeterBeforeReading = new MyEditText(context,R.string.reading_before_6ft_cleaning, R.string.ft_6_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		sevenFtUvMeterBeforeReadingTextView = new MyTextView (context, R.style.text, R.string.reading_before_7ft_cleaning);
		sevenFtUvMeterBeforeReading = new MyEditText(context,R.string.reading_before_7ft_cleaning, R.string.ft_7_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);
		
		maintenanceChecklistHeading = new MyTextView (context, R.style.text, R.string.maintenance_checklist);
		maintenanceChecklistHeading.setTextColor(getResources().getColor(R.color.mainTheme));
		maintenanceChecklistHeading.setTypeface(null, Typeface.BOLD);
		maintenanceChecklistHeading.setGravity(Gravity.CENTER);

		powerDisconnectedTextView = new MyTextView (context, R.style.text, R.string.power_disconnected);
		noPowerDisconnected = new MyRadioButton(context, R.string.power_disconnected, R.style.radio,R.string.no);
		yesPowerDisconnected = new MyRadioButton(context, R.string.power_disconnected, R.style.radio,R.string.yes);
		powerDisconnected = new MyRadioGroup(context,new MyRadioButton[] { noPowerDisconnected, yesPowerDisconnected,}, R.string.power_disconnected,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		louverOpenedTextView = new MyTextView (context, R.style.text, R.string.louver_open);
		noLouverOpened = new MyRadioButton(context, R.string.louver_open, R.style.radio,R.string.no);
		yesLouverOpened = new MyRadioButton(context, R.string.louver_open, R.style.radio,R.string.yes);
		louverOpened = new MyRadioGroup(context,new MyRadioButton[] {noLouverOpened, yesLouverOpened}, R.string.louver_open,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		lampsRemovedTextView = new MyTextView (context, R.style.text, R.string.lamps_removed);
		noLampsRemoved = new MyRadioButton(context, R.string.lamps_removed, R.style.radio,R.string.no);
		yesLampsRemoved = new MyRadioButton(context, R.string.lamps_removed, R.style.radio,R.string.yes);
		lampsRemoved = new MyRadioGroup(context,new MyRadioButton[] {noLampsRemoved, yesLampsRemoved}, R.string.lamps_removed,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		lampsMicrofiberTextView = new MyTextView (context, R.style.text, R.string.lamps_microfiber);
		noLampsMicrofiber = new MyRadioButton(context, R.string.lamps_microfiber, R.style.radio,R.string.no);
		yesLampsMicrofiber = new MyRadioButton(context, R.string.lamps_microfiber, R.style.radio,R.string.yes);
		lampsMicrofiber = new MyRadioGroup(context,new MyRadioButton[] {noLampsMicrofiber, yesLampsMicrofiber}, R.string.lamps_microfiber,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		interiorMicrofiberTextView = new MyTextView (context, R.style.text, R.string.interior_microfiber);
		yesInteriorMicrofiber  = new MyRadioButton(context, R.string.interior_microfiber, R.style.radio,R.string.yes);
		noInteriorMicrofiber  = new MyRadioButton(context, R.string.interior_microfiber, R.style.radio,R.string.no);
		interiorMicrofiber = new MyRadioGroup(context,new MyRadioButton[] {noInteriorMicrofiber, yesInteriorMicrofiber}, R.string.interior_microfiber,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		partsReplacedTextView = new MyTextView (context, R.style.text, R.string.parts_replacement);
		yesPartReplaced = new MyRadioButton(context, R.string.parts_replacement, R.style.radio,R.string.yes);
		noPartReplaced = new MyRadioButton(context, R.string.parts_replacement, R.style.radio,R.string.no);
		partReplaced = new MyRadioGroup(context,new MyRadioButton[] {noPartReplaced, yesPartReplaced}, R.string.parts_replacement,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		replacedPartNameTextView = new MyTextView (context, R.style.text, R.string.parts_replaced);
		replacedPartName = new MyEditText(context, R.string.parts_replaced, R.string.parts_replaced_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength, false);

		lampInstalledCorrectlyTextView  = new MyTextView (context, R.style.text, R.string.lamps_reinstalled);
		yesLampInstalledCorrectly = new MyRadioButton(context, R.string.lamps_reinstalled, R.style.radio,R.string.yes);
		noLampInstalledCorrectly = new MyRadioButton(context, R.string.lamps_reinstalled, R.style.radio,R.string.no);
		lampInstalledCorrectly = new MyRadioGroup(context,new MyRadioButton[] {noLampInstalledCorrectly, yesLampInstalledCorrectly}, R.string.lamps_reinstalled,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		louverClosedTextView = new MyTextView (context, R.style.text, R.string.louver_closed);
		noLouverClosed = new MyRadioButton(context, R.string.louver_closed, R.style.radio,R.string.no);
		yesLouverClosed = new MyRadioButton(context, R.string.louver_closed, R.style.radio,R.string.yes);
		louverClosed = new MyRadioGroup(context,new MyRadioButton[] {noLouverClosed, yesLouverClosed}, R.string.louver_closed,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		powerConnectTextView = new MyTextView (context, R.style.text, R.string.power_connect);
		yesPowerConnect = new MyRadioButton(context, R.string.power_connect, R.style.radio,R.string.yes);
		noPowerConnect = new MyRadioButton(context, R.string.power_connect, R.style.radio,R.string.no);
		powerConnect = new MyRadioGroup(context,new MyRadioButton[] {noPowerConnect, yesPowerConnect}, R.string.power_connect,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		lightWorkingTextView = new MyTextView (context, R.style.text, R.string.light_working);
		yesLightorking = new MyRadioButton(context, R.string.light_working, R.style.radio,R.string.yes);
		noLightWorking = new MyRadioButton(context, R.string.light_working, R.style.radio,R.string.no);
		lightWorking = new MyRadioGroup(context,new MyRadioButton[] {noLightWorking, yesLightorking}, R.string.light_working,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		threeFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.reading_after_3ft_cleaning);
		threeFtUvMeterReading = new MyEditText(context,R.string.reading_after_3ft_cleaning, R.string.ft_3_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);

		threeFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noThreeFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesThreeFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		threeFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesThreeFtCorrectReading, noThreeFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		sixFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.reading_after_6ft_cleaning);
		sixFtUvMeterReading = new MyEditText(context,R.string.reading_after_6ft_cleaning, R.string.ft_6_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);

		sixFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noSixFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesSixFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		sixFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesSixFtCorrectReading, noSixFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		sevenFtUvMeterReadingTextView = new MyTextView (context, R.style.text, R.string.reading_after_7ft_cleaning);
		sevenFtUvMeterReading = new MyEditText(context,R.string.reading_after_7ft_cleaning, R.string.ft_7_reading_hint, InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 5, false);

		sevenFtCorrectReadingTextView = new MyTextView (context, R.style.text, R.string.reading_confirmation);
		noSevenFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.no);
		yesSevenFtCorrectReading = new MyRadioButton(context, R.string.reading_confirmation, R.style.radio,R.string.yes);
		sevenFtCorrectReading = new MyRadioGroup(context,new MyRadioButton[] { yesSevenFtCorrectReading, noSevenFtCorrectReading }, R.string.reading_confirmation,R.style.radio, App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);

		maintenancePersonNameTextView = new MyTextView (context, R.style.text, R.string.maintenance_person_name);
		maintenancePersonName =  new MyEditText(context, R.string.maintenance_person_name, R.string.maintenance_person_name_hint, InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength, false);


		View[][] viewGroups = {{formDateTextView, formDateButton, uniqueIdGeneratedTextView, uniqueIdGenerated, scanBarcodeButton},
				               {threeFtUvMeterBeforeReadingTextView, threeFtUvMeterBeforeReading, sixFtUvMeterBeforeReadingTextView, sixFtUvMeterBeforeReading,sevenFtUvMeterBeforeReadingTextView, sevenFtUvMeterBeforeReading},
							   {maintenanceChecklistHeading, powerDisconnectedTextView, powerDisconnected,louverOpenedTextView, louverOpened, lampsRemovedTextView, lampsRemoved,lampsMicrofiberTextView, lampsMicrofiber},
				               {interiorMicrofiberTextView, interiorMicrofiber, partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName, lampInstalledCorrectlyTextView, lampInstalledCorrectly},
							   {louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking},
				               {threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading},
				               {sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading},
				               {sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading},
							   {maintenancePersonNameTextView, maintenancePersonName}};
		
		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup> ();
		for (int i = 0; i < PAGE_COUNT; i++)
		{
			LinearLayout layout = new LinearLayout (context);
			layout.setOrientation (LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++)
			{
				
				View v = viewGroups[i][j];
				
				if((j%2 == 0 && i != 2) || (j%2 != 0 && i == 2)){
					
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
										formDateButton, scanBarcodeButton, noPowerDisconnected, yesPowerDisconnected, noLouverOpened, yesLouverOpened, noLampsRemoved, yesLampsRemoved,
										noLampsMicrofiber, yesLampsMicrofiber, noInteriorMicrofiber, yesInteriorMicrofiber, noPartReplaced, yesPartReplaced,
										noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};
		
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
		
		views = new View[] {maintenancePersonName, uniqueIdGenerated,
							threeFtUvMeterBeforeReading, sixFtUvMeterBeforeReading, sevenFtUvMeterBeforeReading,
							replacedPartName,
							threeFtUvMeterReading, sixFtUvMeterReading, sevenFtUvMeterReading};
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
				  threeFtCorrectReadingTextView.setVisibility(View.GONE);
				  threeFtCorrectReading.setVisibility(View.GONE);
			 	  noThreeFtCorrectReading.setChecked(true);
			  }
			  
			  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
					  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
					  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
				  
				  if(noPartReplaced.isChecked())
					   saveButton.setEnabled(true);
				  else if(App.get(replacedPartName).equals(""))
					   saveButton.setEnabled(false);
				  else
					  saveButton.setEnabled(true);
			  }
			 else
				 saveButton.setEnabled(false);
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
				  sixFtCorrectReadingTextView.setVisibility(View.GONE);
				  sixFtCorrectReading.setVisibility(View.GONE);
			 	  noSixFtCorrectReading.setChecked(true);
			  }	
			 
			 if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
					  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
					  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
				  
				 if(noPartReplaced.isChecked())
					   saveButton.setEnabled(true);
				  else if(App.get(replacedPartName).equals(""))
					   saveButton.setEnabled(false);
				  else
					  saveButton.setEnabled(true);
			  }
			 else
				 saveButton.setEnabled(false);
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
				  sevenFtCorrectReadingTextView.setVisibility(View.GONE);
			 	  sevenFtCorrectReading.setVisibility(View.GONE);
			 	  noSevenFtCorrectReading.setChecked(true);
			  }	
			  
			  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
					  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
					  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
				  
				  if(noPartReplaced.isChecked())
					   saveButton.setEnabled(true);
				  else if(App.get(replacedPartName).equals(""))
					   saveButton.setEnabled(false);
				  else
					  saveButton.setEnabled(true);
			  }
			 else
				 saveButton.setEnabled(false);
		  }
		  
       });
		
		
		uniqueIdGenerated.addTextChangedListener(new TextWatcher() {
	
	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}
	
			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					 
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
						  saveButton.setEnabled(true);
				  }
				 else
					 saveButton.setEnabled(false);
				
			  }
	       });
		
		threeFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					  
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
						  saveButton.setEnabled(true);
				  }
				 else
					 saveButton.setEnabled(false);
				
			  }
	       });
		
		
		sixFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					  
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
						  saveButton.setEnabled(true);
				  }
				 else
					 saveButton.setEnabled(false);
				
			  }
	       });
		
		
		sevenFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					  
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
						  saveButton.setEnabled(true);
				  }
				 else
					 saveButton.setEnabled(false);
				
			  }
	       });
		
		replacedPartName.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					  
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
						  saveButton.setEnabled(true);
				  }
				 else
					 saveButton.setEnabled(false);
				
			  }
	       });
		
		maintenancePersonName.addTextChangedListener(new TextWatcher() {

	          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	          public void onTextChanged(CharSequence s, int start, int before, int count) {}

			  @Override
			  public void afterTextChanged(Editable s) {
				
				  if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
						  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
						  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
						  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
					  
					  if(noPartReplaced.isChecked())
						   saveButton.setEnabled(true);
					  else if(App.get(replacedPartName).equals(""))
						   saveButton.setEnabled(false);
					  else
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
		
		View[] v = new View[]{noPowerDisconnected, yesPowerDisconnected, noLouverOpened, yesLouverOpened, noLampsRemoved, yesLampsRemoved,
							noLampsMicrofiber, yesLampsMicrofiber, noInteriorMicrofiber, yesInteriorMicrofiber, noPartReplaced, yesPartReplaced,
							noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};
		
		for(View view : v)
			((RadioButton) view).setChecked(false);
		
		for(View view : views){
			if(view instanceof TextView)
				((TextView) view).setHintTextColor (getResources ().getColor (R.color.DarkGray));
		}
		
		noPartReplaced.setChecked(true);
		
		updateDisplay ();
	}

	@Override
	public void updateDisplay ()
	{
		formDateButton.setText (DateFormat.format ("dd-MMM-yyyy", formDate));
		
		if (formDate.getTime().after(new Date())) {
			formDateButton.setTextColor(getResources ().getColor (R.color.Red));
		}
		else{
			formDateButton.setTextColor(getResources ().getColor (R.color.mainTheme));
		}
		
		if(yesPowerDisconnected.isChecked()){
			louverOpenedTextView.setVisibility(View.VISIBLE);
			louverOpened.setVisibility(View.VISIBLE);
		}
		else{
			
			View[] v = new View[]{louverOpenedTextView, louverOpened, lampsRemovedTextView, lampsRemoved,lampsMicrofiberTextView, lampsMicrofiber,
		               interiorMicrofiberTextView, interiorMicrofiber, partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName,
					   lampInstalledCorrectlyTextView, lampInstalledCorrectly, louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLouverOpened, yesLouverOpened, noLampsRemoved, yesLampsRemoved,
					noLampsMicrofiber, yesLampsMicrofiber, noInteriorMicrofiber, yesInteriorMicrofiber,
					noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
			
			noPartReplaced.setChecked(true);
			
		}
		
		if(yesLouverOpened.isChecked()){
			lampsRemoved.setVisibility(View.VISIBLE);
			lampsRemovedTextView.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{lampsRemovedTextView, lampsRemoved,lampsMicrofiberTextView, lampsMicrofiber,
		               interiorMicrofiberTextView, interiorMicrofiber, partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName,
					   lampInstalledCorrectlyTextView, lampInstalledCorrectly, louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLampsRemoved, yesLampsRemoved,
					noLampsMicrofiber, yesLampsMicrofiber, noInteriorMicrofiber, yesInteriorMicrofiber,
					noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
			
			noPartReplaced.setChecked(true);
		}
		
		if(yesLampsRemoved.isChecked()){
			lampsMicrofiberTextView.setVisibility(View.VISIBLE);
			lampsMicrofiber.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{lampsMicrofiberTextView, lampsMicrofiber,
		               interiorMicrofiberTextView, interiorMicrofiber, partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName,
					   lampInstalledCorrectlyTextView, lampInstalledCorrectly, louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLampsMicrofiber, yesLampsMicrofiber, noInteriorMicrofiber, yesInteriorMicrofiber,
					noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
			
			noPartReplaced.setChecked(true);
		}
		
		if(yesLampsMicrofiber.isChecked()){
			interiorMicrofiberTextView.setVisibility(View.VISIBLE);
			interiorMicrofiber.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{interiorMicrofiberTextView, interiorMicrofiber, partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName,
					   lampInstalledCorrectlyTextView, lampInstalledCorrectly, louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noInteriorMicrofiber, yesInteriorMicrofiber,
					noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
			
			noPartReplaced.setChecked(true);
		}
		
		if(yesInteriorMicrofiber.isChecked()){
			partsReplacedTextView.setVisibility(View.VISIBLE);
			partReplaced.setVisibility(View.VISIBLE);
			lampInstalledCorrectlyTextView.setVisibility(View.VISIBLE);
			lampInstalledCorrectly.setVisibility(View.VISIBLE);
			
		}
		else{
			View[] v = new View[]{
					   partsReplacedTextView, partReplaced, replacedPartNameTextView, replacedPartName, lampInstalledCorrectlyTextView, lampInstalledCorrectly, louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLampInstalledCorrectly, yesLampInstalledCorrectly, noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
			
			noPartReplaced.setChecked(true);
		}
		
		
		if(yesLampInstalledCorrectly.isChecked()){
			louverClosedTextView.setVisibility(View.VISIBLE);
			louverClosed.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{louverClosedTextView, louverClosed, powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLouverClosed, yesLouverClosed, noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
		}
		
		if(yesLouverClosed.isChecked()){
			powerConnectTextView.setVisibility(View.VISIBLE);
			powerConnect.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{powerConnectTextView, powerConnect, lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noPowerConnect, yesPowerConnect, noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
		}
		
		if(yesPowerConnect.isChecked()){
			lightWorkingTextView.setVisibility(View.VISIBLE);
			lightWorking.setVisibility(View.VISIBLE);
		}
		else{
			View[] v = new View[]{lightWorkingTextView, lightWorking,
		               threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			v = new View[]{noLightWorking, yesLightorking};

			for(View view : v)
				((RadioButton) view).setChecked(false);
		}
		
		if(yesLightorking.isChecked()){
			View[] v = new View[]{threeFtUvMeterReadingTextView, threeFtUvMeterReading,
		               			sixFtUvMeterReadingTextView, sixFtUvMeterReading, 
		               			sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, 
		               			maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.VISIBLE);
		}
		else{
			
			View[] v = new View[]{threeFtUvMeterReadingTextView, threeFtUvMeterReading, threeFtCorrectReadingTextView, threeFtCorrectReading,
		               sixFtUvMeterReadingTextView, sixFtUvMeterReading, sixFtCorrectReadingTextView, sixFtCorrectReading,
		               sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, sevenFtCorrectReadingTextView, sevenFtCorrectReading,
					   maintenancePersonNameTextView, maintenancePersonName};
			
			for(View view : v)
				view.setVisibility(View.GONE);
			
			maintenancePersonName.setText("");
			sevenFtUvMeterReading.setText("");
			noSixFtCorrectReading.setText("");
			sixFtUvMeterReading.setText("");
			threeFtUvMeterReading.setText("");
			
		}
		
		if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
				  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
				  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
				  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals("")) &&
				  lightWorking.getVisibility() == View.VISIBLE && yesLightorking.isChecked()){
			
			 if(noPartReplaced.isChecked())
				   saveButton.setEnabled(true);
			  else if(App.get(replacedPartName).equals(""))
				   saveButton.setEnabled(false);
			  else
				  saveButton.setEnabled(true);
		  }
		 else
			 saveButton.setEnabled(false);
		
	}

	@Override
	public boolean validate ()
	{
		boolean valid = true;
		StringBuffer message = new StringBuffer ();
		// Validate mandatory controls
		View[] mandatory = {uniqueIdGenerated, threeFtUvMeterBeforeReading, sixFtUvMeterBeforeReading, sevenFtUvMeterReading,
							replacedPartName, threeFtUvMeterReading, sixFtUvMeterReading, sevenFtUvMeterReading, maintenancePersonName};
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
						App.getDialog (UvgiMaintenanceActivity.this, AlertType.SUCCESS, FORM_NAME + " " + getResources ().getString (R.string.form_send_success)).show ();
						initView (views);
					}
					else
					{
						App.getDialog (UvgiMaintenanceActivity.this, AlertType.ERROR, result).show ();
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
		else if (view == noPowerDisconnected || view == yesPowerDisconnected || view == noLouverOpened || view == yesLouverOpened ||
				view == noLampsRemoved || view == yesLampsRemoved || view == noLampsMicrofiber || view == yesLampsMicrofiber ||
				view == noInteriorMicrofiber || view == yesInteriorMicrofiber ||  
				view == noLampInstalledCorrectly || view == yesLampInstalledCorrectly || view == noLouverClosed || view == yesLouverClosed ||
				view == noPowerConnect || view == yesPowerConnect || view == noLightWorking || view == yesLightorking){
			
			updateDisplay();
		}
		else if (view == noPartReplaced || view == yesPartReplaced){
			
			if(yesPartReplaced.isChecked()){
				replacedPartNameTextView.setVisibility(View.VISIBLE);
				replacedPartName.setVisibility(View.VISIBLE);
			}
			else{
				replacedPartNameTextView.setVisibility(View.GONE);
				replacedPartName.setVisibility(View.GONE);
			}
			
			if(!(App.get(threeFtUvMeterReading).equals("")) && !(App.get(sixFtUvMeterReadingTextView).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(uniqueIdGenerated).equals("")) &&
					  !(App.get(threeFtUvMeterBeforeReading).equals("")) && !(App.get(sixFtUvMeterBeforeReading).equals("")) &&
					  !(App.get(sevenFtUvMeterReading).equals("")) && !(App.get(maintenancePersonName).equals(""))){
				
				
				 if(noPartReplaced.isChecked())
					   saveButton.setEnabled(true);
				  else if(App.get(replacedPartName).equals(""))
					   saveButton.setEnabled(false);
				  else
					  saveButton.setEnabled(true);
				
			  }
			else
				 saveButton.setEnabled(false);
			
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
