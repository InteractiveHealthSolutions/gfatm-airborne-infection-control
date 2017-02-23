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
import java.util.HashMap;
import java.util.Locale;

import android.app.ActionBar;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
import com.ihsinformatics.aic.custom.MyCheckBox;
import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.custom.MyEditText.KeyImeChange;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiMaintenanceActivity extends AbstractFragmentActivity {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;
	MyButton verifyButton;

	MyTextView uniqueIdGeneratedTextView;
	MyEditText uniqueIdGenerated;
	MyButton scanBarcodeButton;

	MyTextView threeFtUvMeterBeforeReadingTextView;
	MyEditText threeFtUvMeterBeforeReading;

	MyTextView sixFtUvMeterBeforeReadingTextView;
	MyEditText sixFtUvMeterBeforeReading;

	MyTextView sevenFtUvMeterBeforeReadingTextView;
	MyEditText sevenFtUvMeterBeforeReading;

	MyTextView maintenanceChecklistHeading;

	MyCheckBox powerDisconnectedCheckBox;
	/* MyTextView powerDisconnectedTextView; */
	/*
	 * MyRadioGroup powerDisconnected; MyRadioButton yesPowerDisconnected;
	 * MyRadioButton noPowerDisconnected;
	 */

	MyCheckBox louverOpenedCheckBox;
	/* MyTextView louverOpenedTextView; */
	/*
	 * MyRadioGroup louverOpened; MyRadioButton yesLouverOpened; MyRadioButton
	 * noLouverOpened;
	 */

	MyCheckBox lampsRemovedCheckBox;
	/* MyTextView lampsRemovedTextView; */
	/*
	 * MyRadioGroup lampsRemoved; MyRadioButton yesLampsRemoved; MyRadioButton
	 * noLampsRemoved;
	 */

	MyCheckBox lampsMicrofiberCheckBox;
	/* MyTextView lampsMicrofiberTextView; */
	/*
	 * MyRadioGroup lampsMicrofiber; MyRadioButton yesLampsMicrofiber;
	 * MyRadioButton noLampsMicrofiber;
	 */

	MyCheckBox interiorMicrofiberCheckBox;
	/* MyTextView interiorMicrofiberTextView; */
	/*
	 * MyRadioGroup interiorMicrofiber; MyRadioButton yesInteriorMicrofiber;
	 * MyRadioButton noInteriorMicrofiber;
	 */

	MyCheckBox partsReplacedCheckBox;
	/* MyTextView partsReplacedTextView; */
	/*
	 * MyRadioGroup partReplaced; MyRadioButton yesPartReplaced; MyRadioButton
	 * noPartReplaced;
	 */

	MyTextView replacedPartNameTextView;
	MyEditText replacedPartName;

	MyCheckBox lampInstalledCorrectlyCheckBox;
	/* MyTextView lampInstalledCorrectlyTextView; */
	/*
	 * MyRadioGroup lampInstalledCorrectly; MyRadioButton
	 * yesLampInstalledCorrectly; MyRadioButton noLampInstalledCorrectly;
	 */

	MyCheckBox louverClosedCheckBox;
	/* MyTextView louverClosedTextView; */
	/*
	 * MyRadioGroup louverClosed; MyRadioButton yesLouverClosed; MyRadioButton
	 * noLouverClosed;
	 */

	MyCheckBox powerConnectCheckBox;
	/* MyTextView powerConnectTextView; */
	/*
	 * MyRadioGroup powerConnect; MyRadioButton yesPowerConnect; MyRadioButton
	 * noPowerConnect;
	 */

	MyCheckBox lightWorkingCheckBox;
	/* MyTextView lightWorkingTextView; */
	/*
	 * MyRadioGroup lightWorking; MyRadioButton yesLightWorking; MyRadioButton
	 * noLightWorking;
	 */

	MyTextView threeFtUvMeterReadingTextView;
	MyEditText threeFtUvMeterReading;

	/*
	 * MyTextView threeFtCorrectReadingTextView; MyRadioGroup
	 * threeFtCorrectReading; MyRadioButton yesThreeFtCorrectReading;
	 * MyRadioButton noThreeFtCorrectReading;
	 */

	MyTextView sixFtUvMeterReadingTextView;
	MyEditText sixFtUvMeterReading;

	/*
	 * MyTextView sixFtCorrectReadingTextView; MyRadioGroup sixFtCorrectReading;
	 * MyRadioButton yesSixFtCorrectReading; MyRadioButton
	 * noSixFtCorrectReading;
	 */

	MyTextView sevenFtUvMeterReadingTextView;
	MyEditText sevenFtUvMeterReading;

	/*
	 * MyTextView sevenFtCorrectReadingTextView; MyRadioGroup
	 * sevenFtCorrectReading; MyRadioButton yesSevenFtCorrectReading;
	 * MyRadioButton noSevenFtCorrectReading;
	 */

	MyTextView maintenancePersonNameTextView;
	MyEditText maintenancePersonName;

	MyTextView maintenanceContactNumberTextView;
	MyEditText maintenanceContactNumber;

	Calendar startDateTime;

	class UVIMaintenanceFragment extends Fragment {
		int currentPage;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle data = getArguments();
			currentPage = data.getInt("current_page", 0);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Return a layout of views from pre-filled ArrayList of groups
			if (currentPage != 0 && groups.size() != 0)
				return groups.get(currentPage - 1);
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
	class FeedbackFragmentPagerAdapter extends FragmentPagerAdapter {
		/** Constructor of the class */
		public FeedbackFragmentPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/** This method will be invoked when a page is requested to create */
		@Override
		public Fragment getItem(int arg0) {
			UVIMaintenanceFragment fragment = new UVIMaintenanceFragment();
			Bundle data = new Bundle();
			data.putInt("current_page", arg0 + 1);
			fragment.setArguments(data);
			return fragment;
		}

		/** Returns the number of pages */
		@Override
		public int getCount() {
			return PAGE_COUNT;
		}
	}

	@Override
	public void createViews(final Context context) {

		FORM_NAME = FormType.UVGI_MAINTENANCE;
		TAG = "UVGILightMaintenanceActivity";
		PAGE_COUNT = 7;
		pager = (ViewPager) findViewById(R.template_id.pager);
		navigationSeekbar.setMax(PAGE_COUNT - 1);
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		// If the form consists only of single page, then hide the
		// navigatorLayout
		if (PAGE_COUNT < 2) {
			navigatorLayout.setVisibility(View.GONE);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		FeedbackFragmentPagerAdapter pagerAdapter = new FeedbackFragmentPagerAdapter(
				fragmentManager);
		pager.setAdapter(pagerAdapter);
		pager.setOffscreenPageLimit(PAGE_COUNT);

		// Create views for pages
		formDateTextView = new MyTextView(context, R.style.text,
				R.string.form_date);
		formDateButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.form_date, R.string.form_date);
		verifyButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.verify, R.string.verify);

		uniqueIdGeneratedTextView = new MyTextView(context, R.style.text,
				R.string.unique_id_maintenance);
		uniqueIdGenerated = new MyEditText(context,
				R.string.unique_id_maintenance, R.string.unique_id_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.idLength,
				false);
		scanBarcodeButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.scan_qr_code,
				R.string.scan_qr_code);

		threeFtUvMeterBeforeReadingTextView = new MyTextView(context,
				R.style.text, R.string.reading_before_3ft_cleaning);
		threeFtUvMeterBeforeReading = new MyEditText(context,
				R.string.reading_before_3ft_cleaning,
				R.string.ft_3_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		sixFtUvMeterBeforeReadingTextView = new MyTextView(context,
				R.style.text, R.string.reading_before_6ft_cleaning);
		sixFtUvMeterBeforeReading = new MyEditText(context,
				R.string.reading_before_6ft_cleaning,
				R.string.ft_6_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		sevenFtUvMeterBeforeReadingTextView = new MyTextView(context,
				R.style.text, R.string.reading_before_7ft_cleaning);
		sevenFtUvMeterBeforeReading = new MyEditText(context,
				R.string.reading_before_7ft_cleaning,
				R.string.ft_7_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		maintenanceChecklistHeading = new MyTextView(context, R.style.text,
				R.string.maintenance_checklist);
		maintenanceChecklistHeading.setTextColor(getResources().getColor(
				R.color.mainTheme));
		maintenanceChecklistHeading.setTypeface(null, Typeface.BOLD);
		maintenanceChecklistHeading.setGravity(Gravity.CENTER);

		powerDisconnectedCheckBox = new MyCheckBox(context,
				R.string.power_disconnected, R.style.text,
				R.string.power_disconnected, false);
		/*
		 * powerDisconnectedTextView = new MyTextView (context, R.style.text,
		 * R.string.power_disconnected);
		 *//*
			 * noPowerDisconnected = new MyRadioButton(context,
			 * R.string.power_disconnected, R.style.radio,R.string.no);
			 * yesPowerDisconnected = new MyRadioButton(context,
			 * R.string.power_disconnected, R.style.radio,R.string.yes);
			 * powerDisconnected = new MyRadioGroup(context,new MyRadioButton[]
			 * { noPowerDisconnected, yesPowerDisconnected,},
			 * R.string.power_disconnected,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		louverOpenedCheckBox = new MyCheckBox(context, R.string.louver_open,
				R.style.text, R.string.louver_open, false);
		/*
		 * louverOpenedTextView = new MyTextView (context, R.style.text,
		 * R.string.louver_open);
		 *//*
			 * noLouverOpened = new MyRadioButton(context, R.string.louver_open,
			 * R.style.radio,R.string.no); yesLouverOpened = new
			 * MyRadioButton(context, R.string.louver_open,
			 * R.style.radio,R.string.yes); louverOpened = new
			 * MyRadioGroup(context,new MyRadioButton[] {noLouverOpened,
			 * yesLouverOpened}, R.string.louver_open,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		lampsRemovedCheckBox = new MyCheckBox(context, R.string.lamps_removed,
				R.style.text, R.string.lamps_removed, false);
		/*
		 * lampsRemovedTextView = new MyTextView (context, R.style.text,
		 * R.string.lamps_removed);
		 *//*
			 * noLampsRemoved = new MyRadioButton(context,
			 * R.string.lamps_removed, R.style.radio,R.string.no);
			 * yesLampsRemoved = new MyRadioButton(context,
			 * R.string.lamps_removed, R.style.radio,R.string.yes); lampsRemoved
			 * = new MyRadioGroup(context,new MyRadioButton[] {noLampsRemoved,
			 * yesLampsRemoved}, R.string.lamps_removed,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		lampsMicrofiberCheckBox = new MyCheckBox(context,
				R.string.lamps_microfiber, R.style.text,
				R.string.lamps_microfiber, false);
		/*
		 * lampsMicrofiberTextView = new MyTextView (context, R.style.text,
		 * R.string.lamps_microfiber);
		 *//*
			 * noLampsMicrofiber = new MyRadioButton(context,
			 * R.string.lamps_microfiber, R.style.radio,R.string.no);
			 * yesLampsMicrofiber = new MyRadioButton(context,
			 * R.string.lamps_microfiber, R.style.radio,R.string.yes);
			 * lampsMicrofiber = new MyRadioGroup(context,new MyRadioButton[]
			 * {noLampsMicrofiber, yesLampsMicrofiber},
			 * R.string.lamps_microfiber,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		interiorMicrofiberCheckBox = new MyCheckBox(context,
				R.string.interior_microfiber, R.style.text,
				R.string.interior_microfiber, false);
		/*
		 * interiorMicrofiberTextView = new MyTextView (context, R.style.text,
		 * R.string.interior_microfiber);
		 *//*
			 * yesInteriorMicrofiber = new MyRadioButton(context,
			 * R.string.interior_microfiber, R.style.radio,R.string.yes);
			 * noInteriorMicrofiber = new MyRadioButton(context,
			 * R.string.interior_microfiber, R.style.radio,R.string.no);
			 * interiorMicrofiber = new MyRadioGroup(context,new MyRadioButton[]
			 * {noInteriorMicrofiber, yesInteriorMicrofiber},
			 * R.string.interior_microfiber,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		partsReplacedCheckBox = new MyCheckBox(context,
				R.string.parts_replacement, R.style.text,
				R.string.parts_replacement, false);
		/*
		 * partsReplacedTextView = new MyTextView (context, R.style.text,
		 * R.string.parts_replacement);
		 *//*
			 * yesPartReplaced = new MyRadioButton(context,
			 * R.string.parts_replacement, R.style.radio,R.string.yes);
			 * noPartReplaced = new MyRadioButton(context,
			 * R.string.parts_replacement, R.style.radio,R.string.no);
			 * partReplaced = new MyRadioGroup(context,new MyRadioButton[]
			 * {noPartReplaced, yesPartReplaced},
			 * R.string.parts_replacement,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		replacedPartNameTextView = new MyTextView(context, R.style.text,
				R.string.parts_replaced);
		replacedPartName = new MyEditText(context, R.string.parts_replaced,
				R.string.parts_replaced_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.textLength, false);

		lampInstalledCorrectlyCheckBox = new MyCheckBox(context,
				R.string.lamps_reinstalled, R.style.text,
				R.string.lamps_reinstalled, false);
		/*
		 * lampInstalledCorrectlyTextView = new MyTextView (context,
		 * R.style.text, R.string.lamps_reinstalled);
		 *//*
			 * yesLampInstalledCorrectly = new MyRadioButton(context,
			 * R.string.lamps_reinstalled, R.style.radio,R.string.yes);
			 * noLampInstalledCorrectly = new MyRadioButton(context,
			 * R.string.lamps_reinstalled, R.style.radio,R.string.no);
			 * lampInstalledCorrectly = new MyRadioGroup(context,new
			 * MyRadioButton[] {noLampInstalledCorrectly,
			 * yesLampInstalledCorrectly},
			 * R.string.lamps_reinstalled,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		louverClosedCheckBox = new MyCheckBox(context, R.string.louver_closed,
				R.style.text, R.string.louver_closed, false);
		/*
		 * louverClosedTextView = new MyTextView (context, R.style.text,
		 * R.string.louver_closed);
		 *//*
			 * noLouverClosed = new MyRadioButton(context,
			 * R.string.louver_closed, R.style.radio,R.string.no);
			 * yesLouverClosed = new MyRadioButton(context,
			 * R.string.louver_closed, R.style.radio,R.string.yes); louverClosed
			 * = new MyRadioGroup(context,new MyRadioButton[] {noLouverClosed,
			 * yesLouverClosed}, R.string.louver_closed,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		powerConnectCheckBox = new MyCheckBox(context, R.string.power_connect,
				R.style.text, R.string.power_connect, false);
		/*
		 * powerConnectTextView = new MyTextView (context, R.style.text,
		 * R.string.power_connect);
		 *//*
			 * yesPowerConnect = new MyRadioButton(context,
			 * R.string.power_connect, R.style.radio,R.string.yes);
			 * noPowerConnect = new MyRadioButton(context,
			 * R.string.power_connect, R.style.radio,R.string.no); powerConnect
			 * = new MyRadioGroup(context,new MyRadioButton[] {noPowerConnect,
			 * yesPowerConnect}, R.string.power_connect,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		lightWorkingCheckBox = new MyCheckBox(context, R.string.light_working,
				R.style.text, R.string.light_working, false);
		/*
		 * lightWorkingTextView = new MyTextView (context, R.style.text,
		 * R.string.light_working);
		 *//*
			 * yesLightWorking = new MyRadioButton(context,
			 * R.string.light_working, R.style.radio,R.string.yes);
			 * noLightWorking = new MyRadioButton(context,
			 * R.string.light_working, R.style.radio,R.string.no); lightWorking
			 * = new MyRadioGroup(context,new MyRadioButton[] {noLightWorking,
			 * yesLightWorking}, R.string.light_working,R.style.radio,
			 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
			 */
		threeFtUvMeterReadingTextView = new MyTextView(context, R.style.text,
				R.string.reading_after_3ft_cleaning);
		threeFtUvMeterReading = new MyEditText(context,
				R.string.reading_after_3ft_cleaning,
				R.string.ft_3_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		/*
		 * threeFtCorrectReadingTextView = new MyTextView (context,
		 * R.style.text, R.string.reading_confirmation); noThreeFtCorrectReading
		 * = new MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.no); yesThreeFtCorrectReading = new
		 * MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.yes); threeFtCorrectReading = new
		 * MyRadioGroup(context,new MyRadioButton[] { yesThreeFtCorrectReading,
		 * noThreeFtCorrectReading },
		 * R.string.reading_confirmation,R.style.radio,
		 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		 */
		sixFtUvMeterReadingTextView = new MyTextView(context, R.style.text,
				R.string.reading_after_6ft_cleaning);
		sixFtUvMeterReading = new MyEditText(context,
				R.string.reading_after_6ft_cleaning,
				R.string.ft_6_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		/*
		 * sixFtCorrectReadingTextView = new MyTextView (context, R.style.text,
		 * R.string.reading_confirmation); noSixFtCorrectReading = new
		 * MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.no); yesSixFtCorrectReading = new
		 * MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.yes); sixFtCorrectReading = new
		 * MyRadioGroup(context,new MyRadioButton[] { yesSixFtCorrectReading,
		 * noSixFtCorrectReading }, R.string.reading_confirmation,R.style.radio,
		 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		 */
		sevenFtUvMeterReadingTextView = new MyTextView(context, R.style.text,
				R.string.reading_after_7ft_cleaning);
		sevenFtUvMeterReading = new MyEditText(context,
				R.string.reading_after_7ft_cleaning,
				R.string.ft_7_reading_hint, InputType.TYPE_CLASS_NUMBER
						| InputType.TYPE_NUMBER_FLAG_DECIMAL, R.style.edit, 4,
				false);

		/*
		 * sevenFtCorrectReadingTextView = new MyTextView (context,
		 * R.style.text, R.string.reading_confirmation); noSevenFtCorrectReading
		 * = new MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.no); yesSevenFtCorrectReading = new
		 * MyRadioButton(context, R.string.reading_confirmation,
		 * R.style.radio,R.string.yes); sevenFtCorrectReading = new
		 * MyRadioGroup(context,new MyRadioButton[] { yesSevenFtCorrectReading,
		 * noSevenFtCorrectReading },
		 * R.string.reading_confirmation,R.style.radio,
		 * App.isLanguageRTL(),MyRadioGroup.HORIZONTAL);
		 */
		maintenancePersonNameTextView = new MyTextView(context, R.style.text,
				R.string.maintenance_person_name);
		maintenancePersonName = new MyEditText(context,
				R.string.maintenance_person_name,
				R.string.maintenance_person_name_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, RegexUtil.textLength,
				false);

		maintenanceContactNumberTextView = new MyTextView(context,
				R.style.text, R.string.maintenance_contact_number);
		maintenanceContactNumber = new MyEditText(context,
				R.string.maintenance_contact_number,
				R.string.maintenance_contact_number_hint,
				InputType.TYPE_CLASS_PHONE, R.style.edit,
				RegexUtil.labTestIdLength, false);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, uniqueIdGeneratedTextView,
						uniqueIdGenerated, scanBarcodeButton, verifyButton },
				{ threeFtUvMeterBeforeReadingTextView,
						threeFtUvMeterBeforeReading,
						sixFtUvMeterBeforeReadingTextView,
						sixFtUvMeterBeforeReading,
						sevenFtUvMeterBeforeReadingTextView,
						sevenFtUvMeterBeforeReading },
				{ maintenanceChecklistHeading, powerDisconnectedCheckBox,
						louverOpenedCheckBox, lampsRemovedCheckBox,
						lampsMicrofiberCheckBox },
				{ interiorMicrofiberCheckBox, partsReplacedCheckBox,
						replacedPartNameTextView, replacedPartName,
						lampInstalledCorrectlyCheckBox },
				{ louverClosedCheckBox, powerConnectCheckBox,
						lightWorkingCheckBox },
				{ threeFtUvMeterReadingTextView, threeFtUvMeterReading,
						sixFtUvMeterReadingTextView, sixFtUvMeterReading,
						sevenFtUvMeterReadingTextView, sevenFtUvMeterReading, },
				{ maintenancePersonNameTextView, maintenancePersonName,
						maintenanceContactNumberTextView,
						maintenanceContactNumber } };

		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup>();
		for (int i = 0; i < PAGE_COUNT; i++) {
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++) {

				View v = viewGroups[i][j];

				if (i == 0 && j == 5) {

					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 15, 0, 0);
					v.setLayoutParams(params);

				}

				if (j % 2 == 0 && (i != 2 || i != 3 || i != 4)) {

					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 30, 0, 0);
					v.setLayoutParams(params);

				} else if (j != 0 && (i == 2 || i == 3 || i == 4)) {

					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.setMargins(0, 30, 0, 0);
					v.setLayoutParams(params);
				}

				layout.addView(v);
			}
			ScrollView scrollView = new ScrollView(context);
			scrollView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			scrollView.addView(layout);
			groups.add(scrollView);
		}
		// Set event listeners
		navigationSeekbar.setOnSeekBarChangeListener(this);

		View[] setListener = new View[] { firstButton, lastButton, clearButton,
				saveButton, navigationSeekbar, nextButton, verifyButton,
				formDateButton, scanBarcodeButton, powerDisconnectedCheckBox,
				louverOpenedCheckBox, lampsRemovedCheckBox,
				lampsMicrofiberCheckBox, interiorMicrofiberCheckBox,
				partsReplacedCheckBox, lampInstalledCorrectlyCheckBox,
				louverClosedCheckBox, powerConnectCheckBox,
				lightWorkingCheckBox };

		for (View v : setListener) {
			if (v instanceof Spinner) {
				((Spinner) v).setOnItemSelectedListener(this);
			} else if (v instanceof CheckBox) {
				((CheckBox) v).setOnCheckedChangeListener(this);
			} else if (v instanceof RadioGroup) {
				((RadioGroup) v).setOnClickListener(this);
			} else if (v instanceof Button) {
				((Button) v).setOnClickListener(this);
			} else if (v instanceof RadioButton) {
				((RadioButton) v).setOnClickListener(this);
			} else if (v instanceof ImageButton) {
				((ImageButton) v).setOnClickListener(this);
			}
		}

		pager.setOnPageChangeListener(this);

		views = new View[] { maintenancePersonName, maintenanceContactNumber,
				uniqueIdGenerated, threeFtUvMeterBeforeReading,
				sixFtUvMeterBeforeReading, sevenFtUvMeterBeforeReading,
				replacedPartName, threeFtUvMeterReading, sixFtUvMeterReading,
				sevenFtUvMeterReading };
		// Detect RTL language
		if (App.isLanguageRTL()) {
			Collections.reverse(groups);
			for (ViewGroup g : groups) {
				LinearLayout linearLayout = (LinearLayout) g.getChildAt(0);
				linearLayout.setGravity(Gravity.RIGHT);
			}
			for (View v : views) {
				if (v instanceof EditText) {
					((EditText) v).setGravity(Gravity.RIGHT);
				}
			}
		}

		// on back pressed
		threeFtUvMeterReading.setKeyImeChangeListener(new KeyImeChange() {

			@Override
			public void onKeyIme(int keyCode, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {

					String val = App.get(threeFtUvMeterReading);
					Double valNumber;
					if (!val.equals("") && !val.equals(".")) {
						valNumber = Double.parseDouble(val);

						if (valNumber > 0.4) {
							App.getDialog(
									UvgiMaintenanceActivity.this,
									AlertType.URGENT,
									getResources()
											.getString(
													R.string.uvgi_reading_warning_3_6_ft),
									Gravity.CENTER_HORIZONTAL).show();
							threeFtUvMeterReading.requestFocus();
						}

					}

					if (!(App.get(threeFtUvMeterReading).equals(""))
							&& !(App.get(sixFtUvMeterReadingTextView)
									.equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(uniqueIdGenerated).equals(""))
							&& !(App.get(threeFtUvMeterBeforeReading)
									.equals(""))
							&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(maintenancePersonName).equals(""))
							&& !(App.get(maintenanceContactNumber).equals(""))
							&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
							&& lightWorkingCheckBox.isChecked()) {

						if (!partsReplacedCheckBox.isChecked())
							saveButton.setEnabled(true);
						else if (App.get(replacedPartName).equals(""))
							saveButton.setEnabled(false);
						else
							saveButton.setEnabled(true);
					} else
						saveButton.setEnabled(false);

				}
			}
		});

		// on focus changed
		threeFtUvMeterReading
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {

							String val = App.get(threeFtUvMeterReading);
							Double valNumber;
							if (!val.equals("") && !val.equals(".")) {
								valNumber = Double.parseDouble(val);

								if (valNumber > 0.4)
									App.getDialog(
											UvgiMaintenanceActivity.this,
											AlertType.URGENT,
											getResources()
													.getString(
															R.string.uvgi_reading_warning_3_6_ft),
											Gravity.CENTER_HORIZONTAL).show();

							}

							if (!(App.get(threeFtUvMeterReading).equals(""))
									&& !(App.get(sixFtUvMeterReadingTextView)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(uniqueIdGenerated).equals(""))
									&& !(App.get(threeFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sixFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(maintenancePersonName)
											.equals(""))
									&& !(App.get(maintenanceContactNumber)
											.equals(""))
									&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
									&& lightWorkingCheckBox.isChecked()) {

								if (!partsReplacedCheckBox.isChecked())
									saveButton.setEnabled(true);
								else if (App.get(replacedPartName).equals(""))
									saveButton.setEnabled(false);
								else
									saveButton.setEnabled(true);
							} else
								saveButton.setEnabled(false);

						}
					}
				});

		// on done pressed
		threeFtUvMeterReading
				.setOnEditorActionListener(new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							if (!event.isShiftPressed()) {

								String val = App.get(threeFtUvMeterReading);
								Double valNumber;
								if (!val.equals("") && !val.equals(".")) {
									valNumber = Double.parseDouble(val);

									if (valNumber > 0.4) {
										App.getDialog(
												UvgiMaintenanceActivity.this,
												AlertType.URGENT,
												getResources()
														.getString(
																R.string.uvgi_reading_warning_3_6_ft),
												Gravity.CENTER_HORIZONTAL)
												.show();
										threeFtUvMeterReading.requestFocus();
									}
								}

								if (!(App.get(threeFtUvMeterReading).equals(""))
										&& !(App.get(sixFtUvMeterReadingTextView)
												.equals(""))
										&& !(App.get(sevenFtUvMeterReading)
												.equals(""))
										&& !(App.get(uniqueIdGenerated)
												.equals(""))
										&& !(App.get(threeFtUvMeterBeforeReading)
												.equals(""))
										&& !(App.get(sixFtUvMeterBeforeReading)
												.equals(""))
										&& !(App.get(sevenFtUvMeterReading)
												.equals(""))
										&& !(App.get(maintenancePersonName)
												.equals(""))
										&& !(App.get(maintenanceContactNumber)
												.equals(""))
										&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
										&& lightWorkingCheckBox.isChecked()) {

									if (!partsReplacedCheckBox.isChecked())
										saveButton.setEnabled(true);
									else if (App.get(replacedPartName).equals(
											""))
										saveButton.setEnabled(false);
									else
										saveButton.setEnabled(true);
								} else
									saveButton.setEnabled(false);

								return true; // consume.
							}
						}
						return false; // pass on to other listeners.
					}
				});

		// on back pressed
		sixFtUvMeterReading.setKeyImeChangeListener(new KeyImeChange() {

			@Override
			public void onKeyIme(int keyCode, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {

					String val = App.get(sixFtUvMeterReading);
					Double valNumber;
					if (!val.equals("") && !val.equals(".")) {
						valNumber = Double.parseDouble(val);

						if (valNumber > 0.4) {
							App.getDialog(
									UvgiMaintenanceActivity.this,
									AlertType.URGENT,
									getResources()
											.getString(
													R.string.uvgi_reading_warning_3_6_ft),
									Gravity.CENTER_HORIZONTAL).show();
							sixFtUvMeterReading.requestFocus();
						}

					}

					if (!(App.get(threeFtUvMeterReading).equals(""))
							&& !(App.get(sixFtUvMeterReadingTextView)
									.equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(uniqueIdGenerated).equals(""))
							&& !(App.get(threeFtUvMeterBeforeReading)
									.equals(""))
							&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(maintenancePersonName).equals(""))
							&& !(App.get(maintenanceContactNumber).equals(""))
							&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
							&& lightWorkingCheckBox.isChecked()) {

						if (!partsReplacedCheckBox.isChecked())
							saveButton.setEnabled(true);
						else if (App.get(replacedPartName).equals(""))
							saveButton.setEnabled(false);
						else
							saveButton.setEnabled(true);
					} else
						saveButton.setEnabled(false);

				}
			}
		});

		// on focus changed
		sixFtUvMeterReading
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {

							String val = App.get(sixFtUvMeterReading);
							Double valNumber;
							if (!val.equals("") && !val.equals(".")) {
								valNumber = Double.parseDouble(val);

								if (valNumber > 0.4)
									App.getDialog(
											UvgiMaintenanceActivity.this,
											AlertType.URGENT,
											getResources()
													.getString(
															R.string.uvgi_reading_warning_3_6_ft),
											Gravity.CENTER_HORIZONTAL).show();

							}

							if (!(App.get(threeFtUvMeterReading).equals(""))
									&& !(App.get(sixFtUvMeterReadingTextView)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(uniqueIdGenerated).equals(""))
									&& !(App.get(threeFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sixFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(maintenancePersonName)
											.equals(""))
									&& !(App.get(maintenanceContactNumber)
											.equals(""))
									&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
									&& lightWorkingCheckBox.isChecked()) {

								if (!partsReplacedCheckBox.isChecked())
									saveButton.setEnabled(true);
								else if (App.get(replacedPartName).equals(""))
									saveButton.setEnabled(false);
								else
									saveButton.setEnabled(true);
							} else
								saveButton.setEnabled(false);

						}
					}
				});

		// on done pressed
		sixFtUvMeterReading
				.setOnEditorActionListener(new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							if (!event.isShiftPressed()) {

								String val = App.get(sixFtUvMeterReading);
								Double valNumber;
								if (!val.equals("") && !val.equals(".")) {
									valNumber = Double.parseDouble(val);

									if (valNumber > 0.4) {
										App.getDialog(
												UvgiMaintenanceActivity.this,
												AlertType.URGENT,
												getResources()
														.getString(
																R.string.uvgi_reading_warning_3_6_ft),
												Gravity.CENTER_HORIZONTAL)
												.show();
										sixFtUvMeterReading.requestFocus();
									}

								}

								if (!(App.get(threeFtUvMeterReading).equals(""))
										&& !(App.get(sixFtUvMeterReadingTextView)
												.equals(""))
										&& !(App.get(sevenFtUvMeterReading)
												.equals(""))
										&& !(App.get(uniqueIdGenerated)
												.equals(""))
										&& !(App.get(threeFtUvMeterBeforeReading)
												.equals(""))
										&& !(App.get(sixFtUvMeterBeforeReading)
												.equals(""))
										&& !(App.get(sevenFtUvMeterReading)
												.equals(""))
										&& !(App.get(maintenancePersonName)
												.equals(""))
										&& !(App.get(maintenanceContactNumber)
												.equals(""))
										&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
										&& lightWorkingCheckBox.isChecked()) {

									if (!partsReplacedCheckBox.isChecked())
										saveButton.setEnabled(true);
									else if (App.get(replacedPartName).equals(
											""))
										saveButton.setEnabled(false);
									else
										saveButton.setEnabled(true);
								} else
									saveButton.setEnabled(false);

								return true; // consume.
							}
						}
						return false; // pass on to other listeners.
					}
				});

		// on back pressed
		sevenFtUvMeterReading.setKeyImeChangeListener(new KeyImeChange() {

			@Override
			public void onKeyIme(int keyCode, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
						&& event.getAction() == KeyEvent.ACTION_UP) {

					String val = App.get(sevenFtUvMeterReading);
					Double valNumber;
					if (!val.equals("") && !val.equals(".")) {
						valNumber = Double.parseDouble(val);

						if (valNumber < 10) {
							App.getDialog(
									UvgiMaintenanceActivity.this,
									AlertType.URGENT,
									getResources()
											.getString(
													R.string.uvgi_reading_warning_10_ft),
									Gravity.CENTER_HORIZONTAL).show();
							sevenFtUvMeterReading.requestFocus();
						}

					}

					if (!(App.get(threeFtUvMeterReading).equals(""))
							&& !(App.get(sixFtUvMeterReadingTextView)
									.equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(uniqueIdGenerated).equals(""))
							&& !(App.get(threeFtUvMeterBeforeReading)
									.equals(""))
							&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
							&& !(App.get(sevenFtUvMeterReading).equals(""))
							&& !(App.get(maintenancePersonName).equals(""))
							&& !(App.get(maintenanceContactNumber).equals(""))
							&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
							&& lightWorkingCheckBox.isChecked()) {

						if (!partsReplacedCheckBox.isChecked())
							saveButton.setEnabled(true);
						else if (App.get(replacedPartName).equals(""))
							saveButton.setEnabled(false);
						else
							saveButton.setEnabled(true);
					} else
						saveButton.setEnabled(false);

				}
			}
		});

		// On change of focus
		sevenFtUvMeterReading
				.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {

							String val = App.get(sevenFtUvMeterReading);
							Double valNumber;
							if (!val.equals("") && !val.equals(".")) {
								valNumber = Double.parseDouble(val);

								if (valNumber < 10)
									App.getDialog(
											UvgiMaintenanceActivity.this,
											AlertType.URGENT,
											getResources()
													.getString(
															R.string.uvgi_reading_warning_10_ft),
											Gravity.CENTER_HORIZONTAL).show();

							}

							if (!(App.get(threeFtUvMeterReading).equals(""))
									&& !(App.get(sixFtUvMeterReadingTextView)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(uniqueIdGenerated).equals(""))
									&& !(App.get(threeFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sixFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(maintenancePersonName)
											.equals(""))
									&& !(App.get(maintenanceContactNumber)
											.equals(""))
									&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
									&& lightWorkingCheckBox.isChecked()) {

								if (!partsReplacedCheckBox.isChecked())
									saveButton.setEnabled(true);
								else if (App.get(replacedPartName).equals(""))
									saveButton.setEnabled(false);
								else
									saveButton.setEnabled(true);
							} else
								saveButton.setEnabled(false);

						}
					}
				});

		// On done pressed
		sevenFtUvMeterReading
				.setOnEditorActionListener(new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {

							String val = App.get(sevenFtUvMeterReading);
							Double valNumber;
							if (!val.equals("") && !val.equals(".")) {
								valNumber = Double.parseDouble(val);

								if (valNumber < 10) {
									App.getDialog(
											UvgiMaintenanceActivity.this,
											AlertType.URGENT,
											getResources()
													.getString(
															R.string.uvgi_reading_warning_10_ft),
											Gravity.CENTER_HORIZONTAL).show();
									sevenFtUvMeterReading.requestFocus();
								}
							}

							if (!(App.get(threeFtUvMeterReading).equals(""))
									&& !(App.get(sixFtUvMeterReadingTextView)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(uniqueIdGenerated).equals(""))
									&& !(App.get(threeFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sixFtUvMeterBeforeReading)
											.equals(""))
									&& !(App.get(sevenFtUvMeterReading)
											.equals(""))
									&& !(App.get(maintenancePersonName)
											.equals(""))
									&& !(App.get(maintenanceContactNumber)
											.equals(""))
									&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
									&& lightWorkingCheckBox.isChecked()) {

								if (!partsReplacedCheckBox.isChecked())
									saveButton.setEnabled(true);
								else if (App.get(replacedPartName).equals(""))
									saveButton.setEnabled(false);
								else
									saveButton.setEnabled(true);
							} else
								saveButton.setEnabled(false);

							return true; // consume.

						}
						return false; // pass on to other listeners.
					}
				});

		uniqueIdGenerated.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (App.get(uniqueIdGenerated).equals(""))
					verifyButton.setVisibility(View.GONE);
				else
					verifyButton.setVisibility(View.VISIBLE);

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		threeFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		sixFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		sevenFtUvMeterBeforeReading.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		replacedPartName.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		maintenancePersonName.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		maintenanceContactNumber.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(threeFtUvMeterReading).equals(""))
						&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
						&& !(App.get(sevenFtUvMeterReading).equals(""))
						&& !(App.get(maintenancePersonName).equals(""))
						&& !(App.get(maintenanceContactNumber).equals(""))
						&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
						&& lightWorkingCheckBox.isChecked()) {

					if (!partsReplacedCheckBox.isChecked())
						saveButton.setEnabled(true);
					else if (App.get(replacedPartName).equals(""))
						saveButton.setEnabled(false);
					else
						saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

	}

	@Override
	public void initView(View[] views) {
		startDateTime = Calendar.getInstance();
		super.initView(views);
		formDate = Calendar.getInstance();
		Date date = new Date();
		formDate.setTime(date);

		View[] v = new View[] { powerDisconnectedCheckBox,
				louverOpenedCheckBox, lampsRemovedCheckBox,
				lampsMicrofiberCheckBox, interiorMicrofiberCheckBox,
				partsReplacedCheckBox, partsReplacedCheckBox,
				lampInstalledCorrectlyCheckBox, louverClosedCheckBox,
				powerConnectCheckBox, lightWorkingCheckBox };

		for (View view : v)
			((CheckBox) view).setChecked(false);

		for (View view : views) {
			if (view instanceof TextView)
				((TextView) view).setHintTextColor(getResources().getColor(
						R.color.DarkGray));
		}

		verifyButton.setVisibility(View.GONE);

		updateDisplay();
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));

		if (formDate.getTime().after(new Date())) {
			formDateButton.setTextColor(getResources().getColor(R.color.Red));
		} else {
			formDateButton.setTextColor(getResources().getColor(
					R.color.mainTheme));
		}

		if (powerDisconnectedCheckBox.isChecked()) {
			/*
			 * louverOpenedTextView.setVisibility(View.VISIBLE);
			 */louverOpenedCheckBox.setVisibility(View.VISIBLE);
		} else {

			View[] v = new View[] { louverOpenedCheckBox, lampsRemovedCheckBox,
					lampsMicrofiberCheckBox, interiorMicrofiberCheckBox,
					partsReplacedCheckBox, replacedPartNameTextView,
					replacedPartName, lampInstalledCorrectlyCheckBox,
					louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox, threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { louverOpenedCheckBox, lampsRemovedCheckBox,
					lampsMicrofiberCheckBox, interiorMicrofiberCheckBox,
					lampInstalledCorrectlyCheckBox, partsReplacedCheckBox,
					louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);

		}

		if (louverOpenedCheckBox.isChecked()) {
			lampsRemovedCheckBox.setVisibility(View.VISIBLE);
			/*
			 * lampsRemovedTextView.setVisibility(View.VISIBLE);
			 */} else {
			View[] v = new View[] { lampsRemovedCheckBox,
					lampsMicrofiberCheckBox, interiorMicrofiberCheckBox,
					partsReplacedCheckBox, replacedPartNameTextView,
					replacedPartName, lampInstalledCorrectlyCheckBox,
					louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox, threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { lampsRemovedCheckBox, lampsMicrofiberCheckBox,
					interiorMicrofiberCheckBox, lampInstalledCorrectlyCheckBox,
					partsReplacedCheckBox, louverClosedCheckBox,
					powerConnectCheckBox, lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);

		}

		if (lampsRemovedCheckBox.isChecked()) {
			/*
			 * lampsMicrofiberTextView.setVisibility(View.VISIBLE);
			 */lampsMicrofiberCheckBox.setVisibility(View.VISIBLE);
		} else {
			View[] v = new View[] { lampsMicrofiberCheckBox,
					interiorMicrofiberCheckBox, partsReplacedCheckBox,
					replacedPartNameTextView, replacedPartName,
					lampInstalledCorrectlyCheckBox, louverClosedCheckBox,
					powerConnectCheckBox, lightWorkingCheckBox,
					threeFtUvMeterReadingTextView, threeFtUvMeterReading,
					sixFtUvMeterReadingTextView, sixFtUvMeterReading,
					sevenFtUvMeterReadingTextView, sevenFtUvMeterReading,
					maintenancePersonNameTextView, maintenancePersonName,
					maintenanceContactNumberTextView, maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { lampsMicrofiberCheckBox, partsReplacedCheckBox,
					interiorMicrofiberCheckBox, lampInstalledCorrectlyCheckBox,
					louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);

		}

		if (lampsMicrofiberCheckBox.isChecked()) {
			/*
			 * interiorMicrofiberTextView.setVisibility(View.VISIBLE);
			 */interiorMicrofiberCheckBox.setVisibility(View.VISIBLE);
		} else {
			View[] v = new View[] { interiorMicrofiberCheckBox,
					partsReplacedCheckBox, replacedPartNameTextView,
					replacedPartName, lampInstalledCorrectlyCheckBox,
					louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox, threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { interiorMicrofiberCheckBox, partsReplacedCheckBox,
					lampInstalledCorrectlyCheckBox, louverClosedCheckBox,
					powerConnectCheckBox, lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);

		}

		if (interiorMicrofiberCheckBox.isChecked()) {
			/*
			 * partsReplacedTextView.setVisibility(View.VISIBLE);
			 */partsReplacedCheckBox.setVisibility(View.VISIBLE);
			/*
			 * lampInstalledCorrectlyTextView.setVisibility(View.VISIBLE);
			 */lampInstalledCorrectlyCheckBox.setVisibility(View.VISIBLE);

		} else {
			View[] v = new View[] { partsReplacedCheckBox,
					replacedPartNameTextView, replacedPartName,
					lampInstalledCorrectlyCheckBox, louverClosedCheckBox,
					powerConnectCheckBox, lightWorkingCheckBox,
					threeFtUvMeterReadingTextView, threeFtUvMeterReading,
					sixFtUvMeterReadingTextView, sixFtUvMeterReading,
					sevenFtUvMeterReadingTextView, sevenFtUvMeterReading,
					maintenancePersonNameTextView, maintenancePersonName,
					maintenanceContactNumberTextView, maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { lampInstalledCorrectlyCheckBox,
					partsReplacedCheckBox, louverClosedCheckBox,
					powerConnectCheckBox, lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);

		}

		if (lampInstalledCorrectlyCheckBox.isChecked()) {
			/*
			 * louverClosedTextView.setVisibility(View.VISIBLE);
			 */louverClosedCheckBox.setVisibility(View.VISIBLE);
		} else {
			View[] v = new View[] { louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox, threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { louverClosedCheckBox, powerConnectCheckBox,
					lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);
		}

		if (louverClosedCheckBox.isChecked()) {
			/*
			 * powerConnectTextView.setVisibility(View.VISIBLE);
			 */powerConnectCheckBox.setVisibility(View.VISIBLE);
		} else {
			View[] v = new View[] { powerConnectCheckBox, lightWorkingCheckBox,
					threeFtUvMeterReadingTextView, threeFtUvMeterReading,
					sixFtUvMeterReadingTextView, sixFtUvMeterReading,
					sevenFtUvMeterReadingTextView, sevenFtUvMeterReading,
					maintenancePersonNameTextView, maintenancePersonName,
					maintenanceContactNumberTextView, maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { powerConnectCheckBox, lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);
		}

		if (powerConnectCheckBox.isChecked()) {
			/*
			 * lightWorkingTextView.setVisibility(View.VISIBLE);
			 */lightWorkingCheckBox.setVisibility(View.VISIBLE);
		} else {
			View[] v = new View[] { lightWorkingCheckBox,
					threeFtUvMeterReadingTextView, threeFtUvMeterReading,
					sixFtUvMeterReadingTextView, sixFtUvMeterReading,
					sevenFtUvMeterReadingTextView, sevenFtUvMeterReading,
					maintenancePersonNameTextView, maintenancePersonName,
					maintenanceContactNumberTextView, maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			v = new View[] { lightWorkingCheckBox };

			for (View view : v)
				((CheckBox) view).setChecked(false);
		}

		if (lightWorkingCheckBox.isChecked()) {
			View[] v = new View[] { threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.VISIBLE);
		} else {

			View[] v = new View[] { threeFtUvMeterReadingTextView,
					threeFtUvMeterReading, sixFtUvMeterReadingTextView,
					sixFtUvMeterReading, sevenFtUvMeterReadingTextView,
					sevenFtUvMeterReading, maintenancePersonNameTextView,
					maintenancePersonName, maintenanceContactNumberTextView,
					maintenanceContactNumber };

			for (View view : v)
				view.setVisibility(View.GONE);

			maintenanceContactNumber.setText("");
			maintenancePersonName.setText("");
			sevenFtUvMeterReading.setText("");
			sixFtUvMeterReading.setText("");
			threeFtUvMeterReading.setText("");

		}

		if (!(App.get(threeFtUvMeterReading).equals(""))
				&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
				&& !(App.get(sevenFtUvMeterReading).equals(""))
				&& !(App.get(uniqueIdGenerated).equals(""))
				&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
				&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
				&& !(App.get(sevenFtUvMeterReading).equals(""))
				&& !(App.get(maintenancePersonName).equals(""))
				&& !(App.get(maintenanceContactNumber).equals(""))
				&& lightWorkingCheckBox.getVisibility() == View.VISIBLE
				&& lightWorkingCheckBox.isChecked()) {

			if (!partsReplacedCheckBox.isChecked())
				saveButton.setEnabled(true);
			else if (App.get(replacedPartName).equals(""))
				saveButton.setEnabled(false);
			else
				saveButton.setEnabled(true);
		} else
			saveButton.setEnabled(false);

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { uniqueIdGenerated, threeFtUvMeterBeforeReading,
				sixFtUvMeterBeforeReading, sevenFtUvMeterReading,
				replacedPartName, threeFtUvMeterReading, sixFtUvMeterReading,
				sevenFtUvMeterReading, maintenancePersonName,
				maintenanceContactNumber };
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
					((TextView) view).setHintTextColor(getResources().getColor(
							R.color.Black));
				}
			}
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
		}

		Boolean f = true;
		// Check ID
		/*
		 * if (!RegexUtil.isValidId(App.get(uniqueIdGenerated))) { valid =
		 * false; f = false; message.append (view.getTag () + ". ");
		 * uniqueIdGenerated.setTextColor (getResources ().getColor
		 * (R.color.Red)); } else{ uniqueIdGenerated.setTextColor (getResources
		 * ().getColor (R.color.mainTheme)); }
		 */

		// Check Readings
		View[] v = { threeFtUvMeterReading, sixFtUvMeterReading,
				sevenFtUvMeterReading, maintenanceContactNumber };
		for (View view : v) {
			if (view.getVisibility() == View.VISIBLE) {
				if (App.get(view).equals(".")) {
					valid = false;
					f = false;
					message.append(view.getTag() + ". ");
					((EditText) view).setTextColor(getResources().getColor(
							R.color.Red));
				} else {
					((EditText) view).setTextColor(getResources().getColor(
							R.color.mainTheme));
				}
			}
		}

		// Phone Number
		if (App.get(maintenanceContactNumber).length() != maintenanceContactNumber
				.getMaxLength()) {
			valid = false;
			f = false;
			message.append(maintenanceContactNumber.getTag() + ". ");
			maintenanceContactNumber.setTextColor(getResources().getColor(
					R.color.Red));
		} else
			maintenanceContactNumber.setTextColor(getResources().getColor(
					R.color.mainTheme));

		// Maintainer Name...
		if (App.get(maintenancePersonName).length() < 3) {
			valid = false;
			f = false;
			message.append(maintenancePersonName.getTag() + ". ");
			maintenancePersonName.setTextColor(getResources().getColor(
					R.color.Red));
		} else
			maintenancePersonName.setTextColor(getResources().getColor(
					R.color.mainTheme));

		// replacement part names
		if (replacedPartName.getVisibility() == View.VISIBLE) {
			// Maintainer Name...
			if (App.get(replacedPartName).length() < 3) {
				valid = false;
				f = false;
				message.append(replacedPartName.getTag() + ". ");
				replacedPartName.setTextColor(getResources().getColor(
						R.color.Red));
			} else
				replacedPartName.setTextColor(getResources().getColor(
						R.color.mainTheme));
		}

		if (!f) {
			message.append(getResources().getString(R.string.invalid_data)
					+ "\n");
		}

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

		} catch (NumberFormatException e) {
		}

		if (!valid) {
			App.getDialog(this, AlertType.ERROR, message.toString(),
					Gravity.CENTER_HORIZONTAL).show();
		}
		return valid;
	}

	public boolean submit() {
		AsyncTask<String, String, String> updateTask = new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... params) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loading.setIndeterminate(true);
						loading.setCancelable(false);
						loading.setMessage(getResources().getString(
								R.string.loading_message_saving_trees));
						loading.show();
					}
				});

				final ArrayList<String[]> observations = new ArrayList<String[]>();
				final ContentValues values = new ContentValues();

				values.put("location", App.getLocation());
				values.put("entereddate", App.getSqlDate(formDate));

				observations.add(new String[] { "ID",
						App.get(uniqueIdGenerated) });
				observations.add(new String[] { "UV_BEFORE_CLEAN_3FT",
						App.get(threeFtUvMeterBeforeReading) });
				observations.add(new String[] { "UV_BEFORE_CLEAN_6FT",
						App.get(sixFtUvMeterBeforeReading) });
				observations.add(new String[] { "UV_BEFORE_CLEAN_7FT",
						App.get(sevenFtUvMeterBeforeReading) });
				observations.add(new String[] { "POWER_DISCONNECTED",
						powerDisconnectedCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "LOUVER_OPENED",
						louverOpenedCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "LAMPS_REMOVED",
						lampsRemovedCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "LAMPS_CLEANED",
						lampsMicrofiberCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "INTERIOR_CLEANED",
						interiorMicrofiberCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "UV_PART_REPLACED",
						partsReplacedCheckBox.isChecked() ? "Y" : "N" });
				if (replacedPartNameTextView.getVisibility() == View.VISIBLE)
					observations.add(new String[] { "PARTS_REPLACED_NAME",
							App.get(replacedPartName) });
				observations
						.add(new String[] {
								"LAMPS_REINSTALLED",
								lampInstalledCorrectlyCheckBox.isChecked() ? "Y"
										: "N" });
				observations.add(new String[] { "LOUVER_CLOSED",
						louverClosedCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "POWER_CONNECTED",
						powerConnectCheckBox.isChecked() ? "Y" : "N" });
				observations.add(new String[] { "UV_LIGHT_WORKING",
						lightWorkingCheckBox.isChecked() ? "Y" : "N" });

				observations.add(new String[] { "UV_AFTER_CLEAN_3FT",
						App.get(threeFtUvMeterReading) });
				// if(threeFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "3ft_reading_correct",
				// yesThreeFtCorrectReading.isChecked() ? "Yes" : "No"});
				observations.add(new String[] { "UV_AFTER_CLEAN_6FT",
						App.get(sixFtUvMeterReading) });
				// if(sixFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "6ft_reading_correct",
				// yesSixFtCorrectReading.isChecked() ? "Yes" : "No"});
				observations.add(new String[] { "UV_AFTER_CLEAN_7FT",
						App.get(sevenFtUvMeterReading) });
				// if(sevenFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "7ft_reading_correct",
				// yesSevenFtCorrectReading.isChecked() ? "Yes" : "No"});

				observations.add(new String[] { "MAINTAINER_NAME",
						App.get(maintenancePersonName) });
				observations.add(new String[] { "MAINTAINER_CONTACT",
						App.get(maintenanceContactNumber) });
				observations.add(new String[] { "starttime",
						App.getSqlDateTime(startDateTime) });

				String result = serverService.saveUVGIForm(
						RequestType.UVGI_MAINTENANCE, values,
						observations.toArray(new String[][] {}));
				// String result = "SUCCESS";
				return result;
			}

			@Override
			protected void onProgressUpdate(String... values) {
			};

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (result.equals("SUCCESS")) {
					App.getDialog(
							UvgiMaintenanceActivity.this,
							AlertType.SUCCESS,
							FORM_NAME
									+ " "
									+ getResources().getString(
											R.string.form_send_success),
							Gravity.CENTER_HORIZONTAL).show();
					initView(views);
				} else if (result.equals("CONNECTION_ERROR")) {
					switchToOffline();
				} else if (result.equals("SERVER_NOT_RESPONDING")) {
					switchToOffline();
				} else {
					App.getDialog(UvgiMaintenanceActivity.this,
							AlertType.ERROR, result, Gravity.CENTER_HORIZONTAL)
							.show();
				}
				loading.dismiss();
			}
		};
		updateTask.execute("");

		return true;
	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		} else if (view == firstButton) {
			gotoFirstPage();
		} else if (view == lastButton) {
			gotoLastPage();
		} else if (view == nextButton) {
			gotoNextPage();
		} else if (view == clearButton) {
			final Dialog d = App.getDialog(this, AlertType.QUESTION,
					getResources().getString(R.string.clear_close),
					Gravity.CENTER_HORIZONTAL);
			App.setDialogTitle(d, getResources().getString(R.string.clear_form));

			Button yesButton = App.addDialogButton(d,
					getResources().getString(R.string.yes),
					App.dialogButtonPosition.LEFT,
					App.dialogButtonStatus.POSITIVE);
			yesButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					d.dismiss();
					initView(views);

				}
			});

			App.addDialogButton(d, getResources().getString(R.string.no),
					App.dialogButtonPosition.CENTER,
					App.dialogButtonStatus.NEGATIVE);

			d.show();
		} else if (view == saveButton) {
			// Check connection with server or offline mode
			if (!serverService.checkInternetConnection()
					&& !App.isOfflineMode()) {
				switchToOffline();
				// showAlert(getResources ().getString
				// (R.string.data_connection_error), AlertType.ERROR);
			} else if (validate()) {
				final Dialog d = App.getDialog(this, AlertType.QUESTION,
						getResources().getString(R.string.save_close),
						Gravity.CENTER_HORIZONTAL);
				App.setDialogTitle(d,
						getResources().getString(R.string.save_form));

				Button yesButton = App.addDialogButton(d, getResources()
						.getString(R.string.yes),
						App.dialogButtonPosition.LEFT,
						App.dialogButtonStatus.POSITIVE);
				yesButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						d.dismiss();
						submit();

					}
				});

				App.addDialogButton(d, getResources().getString(R.string.no),
						App.dialogButtonPosition.CENTER,
						App.dialogButtonStatus.NEGATIVE);

				d.show();
			}
		} else if (view == scanBarcodeButton) {
			try {
				Intent intent = new Intent(Barcode.BARCODE_INTENT);
				if (isCallable(intent)) {
					intent.putExtra(Barcode.SCAN_MODE, Barcode.QR_MODE);
					startActivityForResult(intent, Barcode.BARCODE_RESULT);
				} else {
					showAlert(
							getResources().getString(
									R.string.barcode_scanner_missing),
							AlertType.ERROR);
				}
			} catch (ActivityNotFoundException e) {
				showAlert(
						getResources().getString(
								R.string.barcode_scanner_missing),
						AlertType.ERROR);
			}
		} else if (view == verifyButton) {
			// Check connection with server or offline mode
			if (App.isOfflineMode()) {
				App.getDialog(this, AlertType.ERROR,
						getResources().getString(R.string.offline_mode_error),
						Gravity.CENTER_HORIZONTAL).show();
			} else if (!serverService.checkInternetConnection()) {
				showAlert(
						getResources()
								.getString(R.string.data_connection_error),
						AlertType.ERROR);
			} else {
				AsyncTask<String, String, HashMap<String, String>> updateTask = new AsyncTask<String, String, HashMap<String, String>>() {

					@Override
					protected HashMap<String, String> doInBackground(
							String... params) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								loading.setIndeterminate(true);
								loading.setCancelable(false);
								loading.setMessage(getResources().getString(
										R.string.loading_message_saving_trees));
								loading.show();
							}
						});

						HashMap<String, String> hm = serverService
								.getUVGIInstallationRecord(App
										.get(uniqueIdGenerated));
						// String result = "SUCCESS";
						return hm;
					}

					@Override
					protected void onProgressUpdate(String... values) {
					};

					@Override
					protected void onPostExecute(HashMap<String, String> result) {
						super.onPostExecute(result);
						if (result.get("status").equals("SUCCESS")) {
							String resultString = "<p align=\"center\"><u><b>DETAILS</b></u></p>"
									+ "<b>UVGI Light Id:</b> "
									+ result.get("id")
									+ "<br>"
									+ "<br> <b>Location:</b> "
									+ result.get("location")
									+ "<br> <b>OPD:</b> "
									+ result.get("opd")
									+ "<br> <b>OPD Area:</b> "
									+ result.get("opd_area");
							App.getDialog(UvgiMaintenanceActivity.this,
									AlertType.INFO, resultString, Gravity.LEFT)
									.show();
						} else {
							App.getDialog(UvgiMaintenanceActivity.this,
									AlertType.ERROR, result.get("details"),
									Gravity.CENTER_HORIZONTAL).show();
						}

						loading.dismiss();
					}

				};
				updateTask.execute("");
			}
		}

	}

	/**
	 * Shows confirmation dialog, in case the back button was pressed
	 * accidentally during form activity
	 */
	@Override
	public void onBackPressed() {
		final Dialog d = App.getDialog(this, AlertType.QUESTION, getResources()
				.getString(R.string.confirm_close), Gravity.CENTER_HORIZONTAL);
		App.setDialogTitle(d, getResources().getString(R.string.close_form));

		Button yesButton = App.addDialogButton(d,
				getResources().getString(R.string.yes),
				App.dialogButtonPosition.LEFT, App.dialogButtonStatus.POSITIVE);
		yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				finish();
				Intent uvgiMenuIntent = new Intent(getApplicationContext(),
						UvgiMenuActivity.class);
				startActivity(uvgiMenuIntent);

			}
		});

		App.addDialogButton(d, getResources().getString(R.string.no),
				App.dialogButtonPosition.CENTER,
				App.dialogButtonStatus.NEGATIVE);

		d.show();
	}

	@Override
	public void onCheckedChanged(CompoundButton button, boolean state) {
		if (button == powerDisconnectedCheckBox
				|| button == louverOpenedCheckBox
				|| button == lampsRemovedCheckBox
				|| button == lampsMicrofiberCheckBox
				|| button == interiorMicrofiberCheckBox
				|| button == lampInstalledCorrectlyCheckBox
				|| button == louverClosedCheckBox
				|| button == powerConnectCheckBox
				|| button == lightWorkingCheckBox) {

			updateDisplay();
		} else if (button == partsReplacedCheckBox) {

			if (partsReplacedCheckBox.isChecked()) {
				replacedPartNameTextView.setVisibility(View.VISIBLE);
				replacedPartName.setVisibility(View.VISIBLE);
			} else {
				replacedPartNameTextView.setVisibility(View.GONE);
				replacedPartName.setVisibility(View.GONE);
			}

			if (!(App.get(threeFtUvMeterReading).equals(""))
					&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
					&& !(App.get(sevenFtUvMeterReading).equals(""))
					&& !(App.get(uniqueIdGenerated).equals(""))
					&& !(App.get(threeFtUvMeterBeforeReading).equals(""))
					&& !(App.get(sixFtUvMeterBeforeReading).equals(""))
					&& !(App.get(sevenFtUvMeterReading).equals(""))
					&& !(App.get(maintenancePersonName).equals(""))
					&& !(App.get(maintenanceContactNumber).equals(""))) {

				if (!partsReplacedCheckBox.isChecked())
					saveButton.setEnabled(true);
				else if (App.get(replacedPartName).equals(""))
					saveButton.setEnabled(false);
				else
					saveButton.setEnabled(true);

			} else
				saveButton.setEnabled(false);

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

	}

	@Override
	public boolean onLongClick(View v) {
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
				/*
				 * if (RegexUtil.isValidId(str) && !RegexUtil.isNumeric(str,
				 * false)) {
				 */
				uniqueIdGenerated.setText(str);
				/*
				 * } else { App.getDialog(this, AlertType.ERROR,
				 * uniqueIdGenerated.getTag().toString()+ ": " +
				 * getResources().getString(R.string.invalid_data)).show(); }
				 */
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				App.getDialog(this, AlertType.ERROR,
						getResources().getString(R.string.operation_cancelled),
						Gravity.CENTER_HORIZONTAL).show();
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

	void switchToOffline() {
		final Dialog d = App.getDialog(this, AlertType.ERROR, getResources()
				.getString(R.string.data_connection_error)
				+ "<br><br>"
				+ getResources().getString(R.string.switch_to_offlinemode),
				Gravity.CENTER_HORIZONTAL);
		App.setDialogTitle(d, getResources().getString(R.string.error_title));

		Button yesButton = App.addDialogButton(d,
				getResources().getString(R.string.yes),
				App.dialogButtonPosition.LEFT, App.dialogButtonStatus.POSITIVE);
		yesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				App.setOfflineMode(true);
				ActionBar actionBar = getActionBar();
				actionBar.setTitle(FORM_NAME);

				if (App.isOfflineMode())
					actionBar.setSubtitle("-- Offline Mode --");

				d.dismiss();
				submit();

			}
		});

		App.addDialogButton(d, getResources().getString(R.string.no),
				App.dialogButtonPosition.CENTER,
				App.dialogButtonStatus.NEGATIVE);

		d.show();
	}
}
