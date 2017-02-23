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
import android.widget.ArrayAdapter;
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
import com.ihsinformatics.aic.custom.MyEditText.KeyImeChange;
import com.ihsinformatics.aic.custom.MyRadioButton;
import com.ihsinformatics.aic.custom.MyRadioGroup;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.shared.Metadata;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiInstallationActivity extends AbstractFragmentActivity {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView facilityNameTextView;
	Spinner facilityName;

	MyTextView otherFacilityNameTextView;
	MyEditText otherFacilityName;

	MyTextView opdNameTextView;
	Spinner opdName;

	MyTextView otherOpdNameTextView;
	MyEditText otherOpdName;

	MyTextView opdAreaTextView;
	Spinner opdArea;

	MyTextView otherOpdAreaTextView;
	MyEditText otherOpdArea;

	MyTextView uniqueIdGeneratedTextView;
	MyEditText uniqueIdGenerated;
	MyButton scanBarcodeButton;
	MyButton verifyButton;

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

	MyTextView installtionCompleteTextView;
	MyRadioGroup installtionComplete;
	MyRadioButton yesInstalltionComplete;
	MyRadioButton noInstalltionComplete;

	MyTextView reasonInstallationCompleteTextView;
	MyEditText reasonInstallationComplete;

	Calendar startDateTime;

	class UVIInstallationFragment extends Fragment {
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
			UVIInstallationFragment fragment = new UVIInstallationFragment();
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

		FORM_NAME = FormType.UVGI_INSATLLATION;
		TAG = "UVGILightsInstallationActivity";
		PAGE_COUNT = 6;
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

		facilityNameTextView = new MyTextView(context, R.style.text,
				R.string.facility_name);
		// facilityName = new Spinner (context, getResources ().getStringArray
		// (R.array.facilities_name), R.string.facility_name,
		// R.string.option_hint);
		facilityName = new Spinner(this);
		ArrayList<String> facilityList = serverService
				.getMetaDataFromLocalDb(Metadata.LOCATION);
		facilityList.add("Other");
		ArrayAdapter<String> facilityArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				facilityList);
		facilityArrayAdapter
				.setDropDownViewResource(R.drawable.textview_to_spinner);
		facilityName.setAdapter(facilityArrayAdapter);

		otherFacilityNameTextView = new MyTextView(context, R.style.text,
				R.string.other_facility);
		otherFacilityName = new MyEditText(context, R.string.other_facility,
				R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				RegexUtil.textLength, false);

		opdNameTextView = new MyTextView(context, R.style.text,
				R.string.opd_lights_installed);
		opdName = new Spinner(this);
		ArrayList<String> opdList = serverService
				.getMetaDataFromLocalDb(Metadata.OPD);
		opdList.add("Other");
		ArrayAdapter<String> opdArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, opdList);
		opdArrayAdapter.setDropDownViewResource(R.drawable.textview_to_spinner);
		opdName.setAdapter(opdArrayAdapter);

		otherOpdNameTextView = new MyTextView(context, R.style.text,
				R.string.other_opd);
		otherOpdName = new MyEditText(context, R.string.other_opd,
				R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				RegexUtil.textLength, false);

		opdAreaTextView = new MyTextView(context, R.style.text,
				R.string.opd_area);
		opdArea = new Spinner(this);
		ArrayList<String> opdAreaList = serverService
				.getMetaDataFromLocalDb(Metadata.OPD_AREA);
		opdAreaList.add("Other");
		ArrayAdapter<String> opdAreaArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				opdAreaList);
		opdAreaArrayAdapter
				.setDropDownViewResource(R.drawable.textview_to_spinner);
		opdArea.setAdapter(opdAreaArrayAdapter);

		otherOpdAreaTextView = new MyTextView(context, R.style.text,
				R.string.other_opd_area);
		otherOpdArea = new MyEditText(context, R.string.other_opd_area,
				R.string.other_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				RegexUtil.textLength, false);

		uniqueIdGeneratedTextView = new MyTextView(context, R.style.text,
				R.string.unique_id);
		uniqueIdGenerated = new MyEditText(context, R.string.unique_id,
				R.string.unique_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		scanBarcodeButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.scan_qr_code,
				R.string.scan_qr_code);

		threeFtUvMeterReadingTextView = new MyTextView(context, R.style.text,
				R.string.ft_3_reading);
		threeFtUvMeterReading = new MyEditText(context, R.string.ft_3_reading,
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
				R.string.ft_6_reading);
		sixFtUvMeterReading = new MyEditText(context, R.string.ft_6_reading,
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
				R.string.ft_7_reading);
		sevenFtUvMeterReading = new MyEditText(context, R.string.ft_7_reading,
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
		installtionCompleteTextView = new MyTextView(context, R.style.text,
				R.string.installation_complete);
		noInstalltionComplete = new MyRadioButton(context,
				R.string.installation_complete, R.style.radio, R.string.no);
		yesInstalltionComplete = new MyRadioButton(context,
				R.string.installation_complete, R.style.radio, R.string.yes);
		installtionComplete = new MyRadioGroup(context, new MyRadioButton[] {
				yesInstalltionComplete, noInstalltionComplete },
				R.string.installation_complete, R.style.radio,
				App.isLanguageRTL(), MyRadioGroup.HORIZONTAL);

		reasonInstallationCompleteTextView = new MyTextView(context,
				R.style.text, R.string.reason);
		reasonInstallationComplete = new MyEditText(context, R.string.reason,
				R.string.reason_hint, InputType.TYPE_CLASS_TEXT, R.style.edit,
				100, false);
		reasonInstallationComplete.setSingleLine(false);
		reasonInstallationComplete
				.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		reasonInstallationComplete.setMinLines(8);
		reasonInstallationComplete.setMaxHeight(10);
		reasonInstallationComplete.setGravity(Gravity.TOP);

		verifyButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.verify, R.string.verify);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, uniqueIdGeneratedTextView,
						uniqueIdGenerated, scanBarcodeButton, verifyButton },
				{ facilityNameTextView, facilityName,
						otherFacilityNameTextView, otherFacilityName },
				{ opdNameTextView, opdName, otherOpdNameTextView, otherOpdName },
				{ opdAreaTextView, opdArea, otherOpdAreaTextView, otherOpdArea },
				{ threeFtUvMeterReadingTextView, threeFtUvMeterReading,
						sixFtUvMeterReadingTextView, sixFtUvMeterReading,
						sevenFtUvMeterReadingTextView, sevenFtUvMeterReading },
				{ installtionCompleteTextView, installtionComplete,
						reasonInstallationCompleteTextView,
						reasonInstallationComplete } };

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

				if (j % 2 == 0) {

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
				formDateButton, scanBarcodeButton, facilityName, opdName,
				opdArea, yesInstalltionComplete, noInstalltionComplete };

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

		views = new View[] { facilityName, otherFacilityName, opdName,
				otherOpdName, opdArea, otherOpdArea, uniqueIdGenerated,
				noInstalltionComplete, sevenFtUvMeterReading,
				sixFtUvMeterReading, threeFtUvMeterReading,
				reasonInstallationComplete };
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
									UvgiInstallationActivity.this,
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
							&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
									.isChecked() && !App.get(
									reasonInstallationComplete).equals("")))) {
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
											UvgiInstallationActivity.this,
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
									&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
											.isChecked() && !App.get(
											reasonInstallationComplete).equals(
											"")))) {
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
												UvgiInstallationActivity.this,
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
										&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
												.isChecked() && !App.get(
												reasonInstallationComplete)
												.equals("")))) {
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
									UvgiInstallationActivity.this,
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
							&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
									.isChecked() && !App.get(
									reasonInstallationComplete).equals("")))) {
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
											UvgiInstallationActivity.this,
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
									&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
											.isChecked() && !App.get(
											reasonInstallationComplete).equals(
											"")))) {
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
												UvgiInstallationActivity.this,
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
										&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
												.isChecked() && !App.get(
												reasonInstallationComplete)
												.equals("")))) {
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
									UvgiInstallationActivity.this,
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
							&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
									.isChecked() && !App.get(
									reasonInstallationComplete).equals("")))) {
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
											UvgiInstallationActivity.this,
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
									&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
											.isChecked() && !App.get(
											reasonInstallationComplete).equals(
											"")))) {
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
											UvgiInstallationActivity.this,
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
									&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
											.isChecked() && !App.get(
											reasonInstallationComplete).equals(
											"")))) {
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
						&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
								.isChecked() && !App.get(
								reasonInstallationComplete).equals("")))) {
					saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

		reasonInstallationComplete.addTextChangedListener(new TextWatcher() {

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
						&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
								.isChecked() && !App.get(
								reasonInstallationComplete).equals("")))) {
					saveButton.setEnabled(true);
				} else
					saveButton.setEnabled(false);

			}
		});

	}

	@Override
	public void initView(View[] views) {

		startDateTime = Calendar.getInstance();
		/* DateFormat.is24HourFormat(this); */

		super.initView(views);
		formDate = Calendar.getInstance();
		Date date = new Date();
		formDate.setTime(date);

		View[] goneView = new View[] { verifyButton, otherFacilityNameTextView,
				otherFacilityName, otherOpdNameTextView, otherOpdName,
				otherOpdAreaTextView, otherOpdArea };

		for (View v : goneView) {
			v.setVisibility(View.GONE);
		}

		reasonInstallationCompleteTextView.setVisibility(View.VISIBLE);
		reasonInstallationComplete.setVisibility(View.VISIBLE);

		for (View view : views) {
			if (view instanceof TextView)
				((TextView) view).setHintTextColor(getResources().getColor(
						R.color.DarkGray));
		}

		saveButton.setEnabled(false);

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
	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { otherFacilityName, otherOpdName, otherOpdArea,
				uniqueIdGenerated, threeFtUvMeterReading, sixFtUvMeterReading,
				sevenFtUvMeterReading };
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
					((EditText) view).setHintTextColor(getResources().getColor(
							R.color.DarkGray));
				}
			}
		}
		if (!valid) {
			message.append(getResources().getString(R.string.empty_data) + "\n");
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

		Boolean f = true;
		// Check ID
		/*
		 * if (!RegexUtil.isValidId(App.get(uniqueIdGenerated))) { valid =
		 * false; f = false; message.append (view.getTag () + ". ");
		 * uniqueIdGenerated.setTextColor (getResources ().getColor
		 * (R.color.Red)); } else{ uniqueIdGenerated.setTextColor (getResources
		 * ().getColor (R.color.mainTheme)); } }
		 */

		// Check Readings
		View[] v = { threeFtUvMeterReading, sixFtUvMeterReading,
				sevenFtUvMeterReading };
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

		if (reasonInstallationComplete.getVisibility() == View.VISIBLE
				&& !RegexUtil.isWord(App.get(reasonInstallationComplete))) {
			valid = false;
			f = false;
			message.append(reasonInstallationComplete.getTag() + ". ");
			reasonInstallationComplete.setTextColor(getResources().getColor(
					R.color.Red));
		} else
			reasonInstallationComplete.setTextColor(getResources().getColor(
					R.color.mainTheme));

		if (!f) {
			message.append(getResources().getString(R.string.invalid_data)
					+ "\n");
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
				if (otherFacilityName.getVisibility() == View.VISIBLE)
					observations.add(new String[] { "UVGI_INSTALL_LOCATION",
							App.get(otherFacilityName) });
				else
					observations.add(new String[] { "UVGI_INSTALL_LOCATION",
							App.get(facilityName) });
				if (otherOpdName.getVisibility() == View.VISIBLE)
					observations.add(new String[] { "OPD",
							App.get(otherOpdName) });
				else
					observations.add(new String[] { "OPD", App.get(opdName) });
				if (otherOpdArea.getVisibility() == View.VISIBLE)
					observations.add(new String[] { "OPD_AREA",
							App.get(opdArea) });
				else
					observations.add(new String[] { "OPD_AREA",
							App.get(opdArea) });
				observations.add(new String[] { "UV_READING_3FT",
						App.get(threeFtUvMeterReading) });
				// if(threeFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "3ft_reading_correct",
				// yesThreeFtCorrectReading.isChecked() ? "Y" : "N"});
				observations.add(new String[] { "UV_READING_6FT",
						App.get(sixFtUvMeterReading) });
				// if(sixFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "6ft_reading_correct",
				// yesSixFtCorrectReading.isChecked() ? "Y" : "N"});
				observations.add(new String[] { "UV_READING_7FT",
						App.get(sevenFtUvMeterReading) });
				// if(sevenFtCorrectReadingTextView.getVisibility() ==
				// View.VISIBLE)
				// observations.add(new String[] { "7ft_reading_correct",
				// yesSevenFtCorrectReading.isChecked() ? "Y" : "N"});
				observations.add(new String[] { "UVGI_INSTALLED",
						yesInstalltionComplete.isChecked() ? "Y" : "N" });
				if (reasonInstallationComplete.getVisibility() == View.VISIBLE)
					observations.add(new String[] {
							"REASON_UVGI_NOT_INSTALLED",
							App.get(reasonInstallationComplete) });
				observations.add(new String[] { "starttime",
						App.getSqlDateTime(startDateTime) });

				String result = serverService.saveUVGIForm(
						RequestType.UVGI_INSTALLATION, values,
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
							UvgiInstallationActivity.this,
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
					App.getDialog(UvgiInstallationActivity.this,
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
		} else if (view == noInstalltionComplete
				|| view == yesInstalltionComplete) {

			if (!(App.get(threeFtUvMeterReading).equals(""))
					&& !(App.get(sixFtUvMeterReadingTextView).equals(""))
					&& !(App.get(sevenFtUvMeterReading).equals(""))
					&& !(App.get(uniqueIdGenerated).equals(""))
					&& (yesInstalltionComplete.isChecked() || (noInstalltionComplete
							.isChecked() && !App
							.get(reasonInstallationComplete).equals("")))) {
				saveButton.setEnabled(true);
			} else
				saveButton.setEnabled(false);

			if (noInstalltionComplete.isChecked()) {
				reasonInstallationComplete.setVisibility(View.VISIBLE);
				reasonInstallationCompleteTextView.setVisibility(View.VISIBLE);
			} else {
				reasonInstallationComplete.setVisibility(View.GONE);
				reasonInstallationCompleteTextView.setVisibility(View.GONE);
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
			}

			else {
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
							String resultString = "UVGI Light Id <br>"
									+ result.get("id")
									+ "</b> is already in System.<br>"
									+ "<br> <b>Location:</b> "
									+ result.get("location")
									+ "<br> <b>OPD:</b> " + result.get("opd")
									+ "<br> <b>OPD Area:</b> "
									+ result.get("opd_area");
							App.getDialog(UvgiInstallationActivity.this,
									AlertType.ERROR, resultString, Gravity.LEFT)
									.show();
						} else {
							App.getDialog(UvgiInstallationActivity.this,
									AlertType.INFO, result.get("details"),
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
		// Not implemented
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		((TextView) view).setTextColor(getResources().getColor(
				R.color.mainTheme));
		Spinner spinner = (Spinner) parent;
		if (spinner == facilityName) {
			if (App.get(spinner).equalsIgnoreCase("Other")) {
				otherFacilityNameTextView.setVisibility(View.VISIBLE);
				otherFacilityName.setVisibility(View.VISIBLE);
			} else {
				otherFacilityNameTextView.setVisibility(View.GONE);
				otherFacilityName.setVisibility(View.GONE);
			}
		} else if (spinner == opdName) {
			if (App.get(spinner).equalsIgnoreCase("Other")) {
				otherOpdNameTextView.setVisibility(View.VISIBLE);
				otherOpdName.setVisibility(View.VISIBLE);
			} else {
				otherOpdNameTextView.setVisibility(View.GONE);
				otherOpdName.setVisibility(View.GONE);
			}
		} else if (spinner == opdArea) {
			if (App.get(spinner).equalsIgnoreCase("Other")) {
				otherOpdAreaTextView.setVisibility(View.VISIBLE);
				otherOpdArea.setVisibility(View.VISIBLE);
			} else {
				otherOpdAreaTextView.setVisibility(View.GONE);
				otherOpdArea.setVisibility(View.GONE);
			}
		}

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
