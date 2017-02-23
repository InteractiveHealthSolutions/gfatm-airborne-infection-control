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
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ihsinformatics.aic.custom.MyButton;
import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.custom.MyTextView;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.shared.FormType;
import com.ihsinformatics.aic.shared.RequestType;
import com.ihsinformatics.aic.util.RegexUtil;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class UvgiTroubleshootStatusUpdateActivity extends
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

	MyTextView statusTextView;
	Spinner status;

	MyTextView statusDateTextView;
	MyButton statusDateButton;

	Calendar startDateTime;
	Calendar statusDate;

	public static final int STATUS_DIALOG_ID = 3;

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

		FORM_NAME = FormType.UVGI_TROUBLESHOOT_STATUS_UPDATE;
		TAG = "UVGITroubleshootStatusActivity";
		PAGE_COUNT = 3;
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

		statusTextView = new MyTextView(context, R.style.text,
				R.string.troubleshoot_status);
		status = new Spinner(this);
		ArrayAdapter<String> statusArrayAdapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_dropdown_item,
				getResources().getStringArray(R.array.troubleshoot_statuses));
		statusArrayAdapter
				.setDropDownViewResource(R.drawable.textview_to_spinner);
		status.setAdapter(statusArrayAdapter);

		statusDateTextView = new MyTextView(context, R.style.text,
				R.string.date);
		statusDateButton = new MyButton(context, R.style.text,
				R.drawable.form_button, R.string.date, R.string.date);

		View[][] viewGroups = {
				{ formDateTextView, formDateButton, uniqueIdGeneratedTextView,
						uniqueIdGenerated, scanBarcodeButton },
				{ troubleshootingNumberTextView, troubleshootingNumber,
						verifyButton },
				{ statusTextView, status, statusDateTextView, statusDateButton } };

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
				formDateButton, scanBarcodeButton, statusDateButton, status };

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

		views = new View[] { uniqueIdGenerated, troubleshootingNumber, status };
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
						&& App.get(troubleshootingNumber).equals("")) {
					saveButton.setEnabled(false);
					verifyButton.setVisibility(View.VISIBLE);
					verifyButton.setText(getResources().getString(
							R.string.get_troubleshoot_number));
				} else if (!App.get(uniqueIdGenerated).equals("")
						&& !App.get(troubleshootingNumber).equals("")) {
					saveButton.setEnabled(true);
					verifyButton.setVisibility(View.VISIBLE);
					verifyButton.setText(getResources().getString(
							R.string.verify));
				} else {
					saveButton.setEnabled(false);
					verifyButton.setVisibility(View.GONE);
				}

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
						&& App.get(troubleshootingNumber).equals("")) {
					saveButton.setEnabled(false);
					verifyButton.setVisibility(View.VISIBLE);
					verifyButton.setText(getResources().getString(
							R.string.get_troubleshoot_number));
				} else if (!App.get(uniqueIdGenerated).equals("")
						&& !App.get(troubleshootingNumber).equals("")) {
					saveButton.setEnabled(true);
					verifyButton.setVisibility(View.VISIBLE);
					verifyButton.setText(getResources().getString(
							R.string.verify));
				} else {
					saveButton.setEnabled(false);
					verifyButton.setVisibility(View.GONE);
				}

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
		statusDate = Calendar.getInstance();
		statusDate.setTime(date);

		saveButton.setEnabled(false);
		verifyButton.setVisibility(View.GONE);

		updateDisplay();
	}

	@Override
	public void updateDisplay() {
		formDateButton.setText(DateFormat.format("dd-MMM-yyyy", formDate));
		statusDateButton.setText(DateFormat.format("dd-MMM-yyyy", statusDate));

	}

	@Override
	public boolean validate() {
		boolean valid = true;
		StringBuffer message = new StringBuffer();
		// Validate mandatory controls
		View[] mandatory = { uniqueIdGenerated, troubleshootingNumber };
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
		// Check ID
		/*
		 * if (!RegexUtil.isValidId(App.get(uniqueIdGenerated))) { valid =
		 * false; message.append(uniqueIdGenerated.getTag().toString() + ": " +
		 * getResources().getString( R.string.invalid_data) + "\n");
		 * uniqueIdGenerated.setTextColor(getResources().getColor(
		 * R.color.Red)); }
		 */

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
		// Check connection with server or offline mode
		if (!serverService.checkInternetConnection()) {
			showAlert(getResources().getString(R.string.data_connection_error),
					AlertType.ERROR);
		} else if (validate()) {
			final ContentValues values = new ContentValues();
			values.put("formDate", App.getSqlDate(formDate));
			values.put("location", App.getLocation());

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
					observations
							.add(new String[] { "STATUS", App.get(status) });
					observations.add(new String[] { "STATUS_DATE",
							App.getSqlDate(statusDate) });
					observations.add(new String[] { "starttime",
							App.getSqlDateTime(startDateTime) });

					String result = serverService.saveUVGIForm(
							RequestType.UVGI_TROUBLESHOOT_STATUS, values,
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
								UvgiTroubleshootStatusUpdateActivity.this,
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
						App.getDialog(
								UvgiTroubleshootStatusUpdateActivity.this,
								AlertType.ERROR, result,
								Gravity.CENTER_HORIZONTAL).show();
					}
					loading.dismiss();
				}
			};
			updateTask.execute("");
		}
		return true;
	}

	@Override
	public void onClick(View view) {
		if (view == formDateButton) {
			showDialog(DATE_DIALOG_ID);
		} else if (view == statusDateButton) {
			showDialog(STATUS_DIALOG_ID);
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
				if (App.get(uniqueIdGenerated).equals("")) {
					uniqueIdGenerated.setHintTextColor(getResources().getColor(
							R.color.Red));
					showAlert(uniqueIdGenerated.getTag() + ": "
							+ getResources().getString(R.string.empty_data),
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
									loading.setMessage(getResources()
											.getString(
													R.string.loading_message_saving_trees));
									loading.show();
								}
							});

							HashMap<String, String> hm = serverService
									.getUVGITroubleshootStatusRecord(
											App.get(uniqueIdGenerated),
											App.get(troubleshootingNumber));
							// String result = "SUCCESS";
							return hm;
						}

						@Override
						protected void onProgressUpdate(String... values) {
						};

						@Override
						protected void onPostExecute(
								HashMap<String, String> result) {
							super.onPostExecute(result);
							uniqueIdGenerated.setHintTextColor(getResources()
									.getColor(R.color.mainTheme));
							if (result.get("status").equals("SUCCESS")) {
								String resultString = "<b>UVGI Light Id:</b> "
										+ result.get("id")
										+ "<br> <b>Troubleshoot Id:</b> "
										+ result.get("troubleshootId") + "<br>"
										+ "<br> <b>Location:</b> "
										+ result.get("location")
										+ "<br> <b>OPD:</b> "
										+ result.get("opd")
										+ "<br> <b>OPD Area:</b> "
										+ result.get("opd_area")
										+ "<br><br> <b>Problem:</b> "
										+ result.get("problem");

								if (!result.get("no").equals("0")) {
									int no = Integer.parseInt(result.get("no"));
									resultString = resultString
											+ "<br><br> <b><u> Status Update </u></b>";

									for (int i = 1; i <= no; i++) {
										resultString = resultString
												+ "<br>"
												+ result.get("status_" + i)
												+ " - "
												+ result.get("status_date_" + i);
									}
								}

								App.getDialog(
										UvgiTroubleshootStatusUpdateActivity.this,
										AlertType.INFO, resultString,
										Gravity.LEFT).show();
							} else {
								if (!result.containsKey("troubleshoot_no"))
									App.getDialog(
											UvgiTroubleshootStatusUpdateActivity.this,
											AlertType.ERROR,
											result.get("details"),
											Gravity.CENTER_HORIZONTAL).show();
								else {

									if (!App.get(troubleshootingNumber).equals(
											"")) {
										String message = result.get("details");

										int no = Integer.parseInt(result
												.get("troubleshoot_no"));
										if (no == 0) {
											message = message
													+ "<br><br>"
													+ "No logged complaint found in system for uvgi light id: "
													+ App.get(uniqueIdGenerated);
										} else {
											message = message
													+ "<br><br>"
													+ "Complaint found in system for uvgi light id: "
													+ App.get(uniqueIdGenerated)
													+ "<br>"
													+ "<font size=\"1\">"
													+ "<i>"
													+ "(Long press to copy troubleshoot number)"
													+ "</i>" + "<\font>";
										}

										final Dialog d = App
												.getDialog(
														UvgiTroubleshootStatusUpdateActivity.this,
														AlertType.ERROR,
														message,
														Gravity.CENTER_HORIZONTAL);

										for (int i = 0; i < no; i++) {

											final TextView text = App
													.addTroubleshootId(
															d,
															result.get("troubleshoot_"
																	+ i));

											text.setTextSize(
													TypedValue.COMPLEX_UNIT_PX,
													getResources()
															.getDimension(
																	R.dimen.large));
											LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
													LayoutParams.MATCH_PARENT,
													LayoutParams.WRAP_CONTENT);
											params.setMargins(20, 0, 20, 10);
											text.setLayoutParams(params);

											text.setOnLongClickListener(new OnLongClickListener() {

												@Override
												public boolean onLongClick(
														View v) {

													String txt = text.getText()
															.toString();
													d.dismiss();
													troubleshootingNumber
															.setText(txt);

													setClipboard(
															getApplicationContext(),
															txt);

													return false;
												}
											});
										}
										d.show();
									} else {
										String message = result.get("details");

										int no = Integer.parseInt(result
												.get("troubleshoot_no"));
										if (no == 0) {
											message = message
													+ "<br><br>"
													+ "No logged complaint found in system for uvgi light id: "
													+ App.get(uniqueIdGenerated);
										} else {
											message = message
													+ "<br><br>"
													+ "Complaint found in system for uvgi light id: "
													+ App.get(uniqueIdGenerated)
													+ "<br>"
													+ "<font size=\"1\">"
													+ "<i>"
													+ "(Long press to copy troubleshoot number)"
													+ "</i>" + "<\font>";
										}

										final Dialog d = App
												.getDialog(
														UvgiTroubleshootStatusUpdateActivity.this,
														AlertType.SUCCESS,
														message,
														Gravity.CENTER_HORIZONTAL);

										for (int i = no - 1; i >= 0; i--) {
											final TextView text = App
													.addTroubleshootId(
															d,
															result.get("troubleshoot_"
																	+ i));

											text.setTextSize(
													TypedValue.COMPLEX_UNIT_PX,
													getResources()
															.getDimension(
																	R.dimen.large));
											LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
													LayoutParams.MATCH_PARENT,
													LayoutParams.WRAP_CONTENT);
											params.setMargins(20, 0, 20, 10);
											text.setLayoutParams(params);

											text.setOnLongClickListener(new OnLongClickListener() {

												@Override
												public boolean onLongClick(
														View v) {

													String txt = text.getText()
															.toString();
													d.dismiss();
													troubleshootingNumber
															.setText(txt);

													setClipboard(
															getApplicationContext(),
															txt);

													return false;
												}
											});
										}
										d.show();
									}
								}
							}

							loading.dismiss();
						}

					};
					updateTask.execute("");
				}
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

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// Show date dialog
		case DATE_DIALOG_ID:
			OnDateSetListener dateSetListener = new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					formDate.set(year, monthOfYear, dayOfMonth);
					updateDisplay();
				}

			};
			return new DatePickerDialog(this, dateSetListener,
					formDate.get(Calendar.YEAR), formDate.get(Calendar.MONTH),
					formDate.get(Calendar.DAY_OF_MONTH));
			// Show time dialog
		case TIME_DIALOG_ID:
			OnTimeSetListener timeSetListener = new OnTimeSetListener() {
				@Override
				public void onTimeSet(TimePicker view, int hour, int minute) {
					formDate.set(Calendar.HOUR_OF_DAY, hour);
					formDate.set(Calendar.MINUTE, minute);
					updateDisplay();
				}
			};
			return new TimePickerDialog(this, timeSetListener,
					formDate.get(Calendar.HOUR_OF_DAY),
					formDate.get(Calendar.MINUTE), true);

		case STATUS_DIALOG_ID:
			OnDateSetListener statusDateSetListener = new OnDateSetListener() {
				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					statusDate.set(year, monthOfYear, dayOfMonth);
					updateDisplay();
				}
			};
			return new DatePickerDialog(this, statusDateSetListener,
					statusDate.get(Calendar.YEAR),
					statusDate.get(Calendar.MONTH),
					statusDate.get(Calendar.DAY_OF_MONTH));
		}
		return null;
	}

	private void setClipboard(Context context, String text) {
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			clipboard.setText(text);
		} else {
			android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData
					.newPlainText("Copied Text", text);
			clipboard.setPrimaryClip(clip);
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
