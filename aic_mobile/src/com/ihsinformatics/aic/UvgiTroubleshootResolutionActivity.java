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
import com.ihsinformatics.aic.custom.MyRadioButton;
import com.ihsinformatics.aic.custom.MyRadioGroup;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiTroubleshootResolutionActivity extends
		AbstractFragmentActivity {
	// Views displayed in pages, sorted w.r.t. appearance on pager
	MyTextView formDateTextView;
	MyButton formDateButton;

	MyTextView uniqueIdGeneratedTextView;
	MyEditText uniqueIdGenerated;
	MyButton scanBarcodeButton;

	MyTextView troubleshootingNumberTextView;
	MyEditText troubleshootingNumber;
	MyButton verifyButton;

	MyTextView problemResolvedTextView;
	MyRadioGroup problemResolved;
	MyRadioButton yesProblemResolved;
	MyRadioButton noProblemResolved;

	MyTextView reasonProblemNotResolvedTextView;
	MyEditText reasonProblemNotResolved;

	MyTextView problemTextView;
	MyEditText problem;

	MyTextView resolvedByTextView;
	MyEditText resolvedBy;

	MyTextView contactNumberTextView;
	MyEditText contactNumber;

	Calendar startDateTime;

	/**
	 * Subclass representing Fragment for feedback form
	 * 
	 * @author owais.hussain@irdresearch.org
	 * 
	 */
	class FeedbackFragment extends Fragment {
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
			FeedbackFragment fragment = new FeedbackFragment();
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

		FORM_NAME = FormType.UVGI_TROUBLESHOOT_RESOLUTION;
		TAG = "UVGITroubleshootResolutionActivity";
		PAGE_COUNT = 4;
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

		uniqueIdGeneratedTextView = new MyTextView(context, R.style.text,
				R.string.unique_id);
		uniqueIdGenerated = new MyEditText(context, R.string.unique_id,
				R.string.unique_id_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, RegexUtil.idLength, false);
		scanBarcodeButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.scan_qr_code,
				R.string.scan_qr_code);

		troubleshootingNumberTextView = new MyTextView(context, R.style.text,
				R.string.troubleshooting_number);
		troubleshootingNumber = new MyEditText(context,
				R.string.troubleshooting_number,
				R.string.troubleshooting_number_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 50, false);
		verifyButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.verify, R.string.verify);

		problemResolvedTextView = new MyTextView(context, R.style.text,
				R.string.problem_resolved);
		noProblemResolved = new MyRadioButton(context,
				R.string.problem_resolved, R.style.radio, R.string.no);
		yesProblemResolved = new MyRadioButton(context,
				R.string.problem_resolved, R.style.radio, R.string.yes);
		problemResolved = new MyRadioGroup(context, new MyRadioButton[] {
				noProblemResolved, yesProblemResolved },
				R.string.problem_resolved, R.style.radio, App.isLanguageRTL(),
				MyRadioGroup.HORIZONTAL);

		reasonProblemNotResolvedTextView = new MyTextView(context,
				R.style.text, R.string.reason_problem_not_resolved);
		reasonProblemNotResolved = new MyEditText(context,
				R.string.reason_problem_not_resolved,
				R.string.reason_problem_not_resolved_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 50, false);
		reasonProblemNotResolved.setSingleLine(false);
		reasonProblemNotResolved
				.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		reasonProblemNotResolved.setMinLines(8);
		reasonProblemNotResolved.setMaxHeight(10);
		reasonProblemNotResolved.setGravity(Gravity.TOP);

		problemTextView = new MyTextView(context, R.style.text,
				R.string.problem);
		problem = new MyEditText(context, R.string.problem,
				R.string.problem_with_fixture_hint, InputType.TYPE_CLASS_TEXT,
				R.style.edit, 50, false);
		problem.setSingleLine(false);
		problem.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		problem.setMinLines(8);
		problem.setMaxHeight(10);
		problem.setGravity(Gravity.TOP);

		resolvedByTextView = new MyTextView(context, R.style.text,
				R.string.problem_resolved_by);
		resolvedBy = new MyEditText(context, R.string.problem_resolved_by,
				R.string.maintenance_person_name_hint,
				InputType.TYPE_CLASS_TEXT, R.style.edit, 50, false);

		contactNumberTextView = new MyTextView(context, R.style.text,
				R.string.troubleshoot_contact_number);
		contactNumber = new MyEditText(context,
				R.string.troubleshoot_contact_number,
				R.string.maintenance_contact_number_hint,
				InputType.TYPE_CLASS_PHONE, R.style.edit, 11, false);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, uniqueIdGeneratedTextView,
						uniqueIdGenerated, scanBarcodeButton },
				{ troubleshootingNumberTextView, troubleshootingNumber,
						verifyButton },
				{ problemResolvedTextView, problemResolved,
						reasonProblemNotResolvedTextView,
						reasonProblemNotResolved, problemTextView, problem },
				{ resolvedByTextView, resolvedBy, contactNumberTextView,
						contactNumber } };

		// Create layouts and store in ArrayList
		groups = new ArrayList<ViewGroup>();
		for (int i = 0; i < PAGE_COUNT; i++) {
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.VERTICAL);
			for (int j = 0; j < viewGroups[i].length; j++) {

				View v = viewGroups[i][j];

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
				formDateButton, scanBarcodeButton, noProblemResolved,
				yesProblemResolved };

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

		views = new View[] { uniqueIdGenerated, troubleshootingNumber, problem,
				reasonProblemNotResolved, resolvedBy, contactNumber };
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

		reasonProblemNotResolved.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
				} else
					saveButton.setEnabled(false);

			}
		});

		problem.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
				} else
					saveButton.setEnabled(false);

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

				if (!App.get(uniqueIdGenerated).equals("")
						&& !App.get(troubleshootingNumber).equals(""))
					verifyButton.setVisibility(View.VISIBLE);
				else
					verifyButton.setVisibility(View.GONE);

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
				} else
					saveButton.setEnabled(false);

			}
		});

		resolvedBy.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
				} else
					saveButton.setEnabled(false);

			}
		});

		contactNumber.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
				} else
					saveButton.setEnabled(false);

			}
		});

		troubleshootingNumber.addTextChangedListener(new TextWatcher() {

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {

				if (!App.get(uniqueIdGenerated).equals("")
						&& !App.get(troubleshootingNumber).equals(""))
					verifyButton.setVisibility(View.VISIBLE);
				else
					verifyButton.setVisibility(View.GONE);

				if (!(App.get(troubleshootingNumber).equals(""))
						&& !(App.get(uniqueIdGenerated).equals(""))
						&& !(App.get(resolvedBy).equals(""))
						&& !(App.get(contactNumber).equals(""))) {

					if (noProblemResolved.isChecked()
							&& !(App.get(reasonProblemNotResolved).equals("")))
						saveButton.setEnabled(true);
					else if (yesProblemResolved.isChecked()
							&& !(App.get(problem).equals("")))
						saveButton.setEnabled(true);
					else
						saveButton.setEnabled(false);
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

		reasonProblemNotResolvedTextView.setVisibility(View.GONE);
		reasonProblemNotResolved.setVisibility(View.GONE);

		problemTextView.setVisibility(View.GONE);
		problem.setVisibility(View.GONE);

		verifyButton.setVisibility(View.GONE);

		yesProblemResolved.setChecked(false);
		noProblemResolved.setChecked(false);

		saveButton.setEnabled(false);

		updateDisplay();
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { uniqueIdGenerated, troubleshootingNumber,
				reasonProblemNotResolved, problem, resolvedBy };
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
		 * ().getColor (R.color.mainTheme)); }
		 */

		if (reasonProblemNotResolvedTextView.getVisibility() == View.VISIBLE) {
			if (App.get(reasonProblemNotResolved).length() < 3) {
				valid = false;
				f = false;
				message.append(reasonProblemNotResolved.getTag() + ". ");
				reasonProblemNotResolved.setTextColor(getResources().getColor(
						R.color.Red));
			} else
				reasonProblemNotResolved.setTextColor(getResources().getColor(
						R.color.mainTheme));
		}

		if (problem.getVisibility() == View.VISIBLE) {
			if (App.get(problem).length() < 3) {
				valid = false;
				f = false;
				message.append(problem.getTag() + ". ");
				problem.setTextColor(getResources().getColor(R.color.Red));
			} else
				problem.setTextColor(getResources().getColor(R.color.mainTheme));
		}

		// name..
		if (App.get(resolvedBy).length() < 3) {
			valid = false;
			f = false;
			message.append(resolvedBy.getTag() + ". ");
			resolvedBy.setTextColor(getResources().getColor(R.color.Red));
		} else
			resolvedBy.setTextColor(getResources().getColor(R.color.mainTheme));

		// Phone Number
		if (App.get(contactNumber).length() != contactNumber.getMaxLength()) {
			valid = false;
			f = false;
			message.append(contactNumber.getTag() + ". ");
			contactNumber.setTextColor(getResources().getColor(R.color.Red));
		} else
			contactNumber.setTextColor(getResources().getColor(
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
				observations.add(new String[] { "TROUBLESHOOT_NUMBER",
						App.get(troubleshootingNumber) });
				observations.add(new String[] { "PROBLEM_RESOLVED",
						yesProblemResolved.isChecked() ? "Y" : "N" });
				if (problemTextView.getVisibility() == View.VISIBLE)
					observations
							.add(new String[] { "PROBLEM", App.get(problem) });
				if (reasonProblemNotResolvedTextView.getVisibility() == View.VISIBLE)
					observations.add(new String[] {
							"REASON_PROBLEM_UNRESOLVED",
							App.get(reasonProblemNotResolved) });
				observations.add(new String[] { "TROUBLESHOOTER_NAME",
						App.get(resolvedBy) });
				observations.add(new String[] { "TROUBLESHOOTER_CONTACT",
						App.get(contactNumber) });
				observations.add(new String[] { "starttime",
						App.getSqlDateTime(startDateTime) });

				String result = serverService.saveUVGIForm(
						RequestType.UVGI_RESOLUTION, values,
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
							UvgiTroubleshootResolutionActivity.this,
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
					App.getDialog(UvgiTroubleshootResolutionActivity.this,
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
		} else if (view == noProblemResolved || view == yesProblemResolved) {
			if (noProblemResolved.isChecked()) {

				reasonProblemNotResolvedTextView.setVisibility(View.VISIBLE);
				reasonProblemNotResolved.setVisibility(View.VISIBLE);

				problemTextView.setVisibility(View.GONE);
				problem.setText("");
				problem.setVisibility(View.GONE);

			} else {
				reasonProblemNotResolvedTextView.setVisibility(View.GONE);
				reasonProblemNotResolved.setVisibility(View.GONE);
				reasonProblemNotResolved.setText("");

				problemTextView.setVisibility(View.VISIBLE);
				problem.setVisibility(View.VISIBLE);
			}

			if (!(App.get(troubleshootingNumber).equals(""))
					&& !(App.get(uniqueIdGenerated).equals(""))
					&& !(App.get(resolvedBy).equals(""))
					&& !(App.get(contactNumber).equals(""))) {

				if (noProblemResolved.isChecked()
						&& !(App.get(reasonProblemNotResolved).equals("")))
					saveButton.setEnabled(true);
				else if (yesProblemResolved.isChecked()
						&& !(App.get(problem).equals("")))
					saveButton.setEnabled(true);
				else
					saveButton.setEnabled(false);
			} else
				saveButton.setEnabled(false);
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
								.getUVGITroubleshootLogRecord(
										App.get(uniqueIdGenerated),
										App.get(troubleshootingNumber));
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
									+ "<br> <b>Troubleshoot Id:</b> "
									+ result.get("troubleshootId")
									+ "<br>"
									+ "<br> <b>Location:</b> "
									+ result.get("location")
									+ "<br> <b>OPD:</b> "
									+ result.get("opd")
									+ "<br> <b>OPD Area:</b> "
									+ result.get("opd_area")
									+ "<br><br> <b>Problem:</b> "
									+ result.get("problem");
							App.getDialog(
									UvgiTroubleshootResolutionActivity.this,
									AlertType.INFO, resultString, Gravity.LEFT)
									.show();
						} else {
							App.getDialog(
									UvgiTroubleshootResolutionActivity.this,
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
		// Not implemented
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
