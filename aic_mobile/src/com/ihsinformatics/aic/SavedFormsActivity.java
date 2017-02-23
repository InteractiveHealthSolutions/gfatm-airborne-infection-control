/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * Activity to view, submit and delete saved forms
 */

package com.ihsinformatics.aic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.Toast;

import com.ihsinformatics.aic.custom.MyCheckBox;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.util.DateTimeUtil;
import com.ihsinformatics.aic.util.JsonUtil;
import com.ihsinformatics.aic.util.ServerService;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class SavedFormsActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener {
	public static final String TAG = "ReportsActivity";
	public static final String SEARCH_RESULT = "SEARCH_RESULT";
	private static final AtomicInteger counter = new AtomicInteger();
	public static ServerService serverService;
	public static ProgressDialog loading;

	Button showSavedFormsButton;
	Button submitFormsButton;
	Button discardFormsButton;
	ScrollView searchResultsScrollView;
	ListView savedFormsListView;
	RadioGroup formsRadioGroup;
	Animation alphaAnimation;

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.saved_forms);
		super.onCreate(savedInstanceState);
		serverService = new ServerService(this);
		loading = new ProgressDialog(this);
		showSavedFormsButton = (Button) findViewById(R.saved_forms_id.showSavedFormsButton);
		submitFormsButton = (Button) findViewById(R.saved_forms_id.submitSavedFormsButton);
		discardFormsButton = (Button) findViewById(R.saved_forms_id.discardFormsButton);
		searchResultsScrollView = (ScrollView) findViewById(R.saved_forms_id.resultsScrollView);
		savedFormsListView = (ListView) findViewById(R.saved_forms_id.savedFormsListView);
		formsRadioGroup = (RadioGroup) findViewById(R.saved_forms_id.formsRadioGroup);
		alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.alpha_animation);

		showSavedFormsButton.setOnClickListener(this);
		submitFormsButton.setOnClickListener(this);
		discardFormsButton.setOnClickListener(this);
		discardFormsButton.setEnabled(false);
		submitFormsButton.setEnabled(false);

		if (App.isOfflineMode()) {
			discardFormsButton.setEnabled(false);
			submitFormsButton.setEnabled(false);
		}

	}

	@Override
	public void onBackPressed() {
		finish();
		Intent loginIntent = new Intent(getApplicationContext(),
				MainMenuActivity.class);
		startActivity(loginIntent);
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		// Not implemented
	}

	@Override
	public void onClick(View view) {
		if (view == showSavedFormsButton) {
			showForms();
		} else if (view == submitFormsButton) {
			final String[][] forms = serverService.getSavedForms(App
					.getUsername());
			if (forms.length == 0) {
				App.getAlertDialog(this, AlertType.INFO,
						getResources().getString(R.string.item_not_found))
						.show();
			} else {
				Boolean flag = false;
				for (int i = 0; i < formsRadioGroup.getChildCount(); i++) {
					MyCheckBox checkBox = (MyCheckBox) formsRadioGroup
							.getChildAt(i);
					if (checkBox.isChecked())
						flag = true;
				}
				if (!flag) {
					App.getDialog(this, AlertType.ERROR,
							getResources().getString(R.string.no_selection),
							Gravity.CENTER_HORIZONTAL).show();
				} else {
					final Dialog d = App.getDialog(this, AlertType.QUESTION,
							getResources().getString(R.string.delete_forms),
							Gravity.CENTER_HORIZONTAL);
					App.setDialogTitle(d,
							getResources().getString(R.string.delete_form));

					Button yesButton = App.addDialogButton(d, getResources()
							.getString(R.string.yes),
							App.dialogButtonPosition.LEFT,
							App.dialogButtonStatus.POSITIVE);
					yesButton.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							d.dismiss();
							submitForms();
						}

					});
					App.addDialogButton(d, getResources()
							.getString(R.string.no),
							App.dialogButtonPosition.CENTER,
							App.dialogButtonStatus.NEGATIVE);
					d.show();
				}
			}

		} else if (view == discardFormsButton) {
			final String[][] forms = serverService.getSavedForms(App
					.getUsername());
			if (forms.length == 0) {
				App.getAlertDialog(this, AlertType.INFO,
						getResources().getString(R.string.item_not_found))
						.show();
			} else {
				Boolean flag = false;
				for (int i = 0; i < formsRadioGroup.getChildCount(); i++) {
					MyCheckBox checkBox = (MyCheckBox) formsRadioGroup
							.getChildAt(i);
					if (checkBox.isChecked())
						flag = true;
				}
				if (!flag) {
					App.getDialog(this, AlertType.ERROR,
							getResources().getString(R.string.no_selection),
							Gravity.CENTER_HORIZONTAL).show();
				} else {
					final Dialog d = App.getDialog(this, AlertType.QUESTION,
							getResources().getString(R.string.delete_forms),
							Gravity.CENTER_HORIZONTAL);
					App.setDialogTitle(d,
							getResources().getString(R.string.delete_form));

					Button yesButton = App.addDialogButton(d, getResources()
							.getString(R.string.yes),
							App.dialogButtonPosition.LEFT,
							App.dialogButtonStatus.POSITIVE);
					yesButton.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							d.dismiss();
							StringBuilder formsData = new StringBuilder();
							for (int i = 0; i < formsRadioGroup.getChildCount(); i++) {
								MyCheckBox checkBox = (MyCheckBox) formsRadioGroup
										.getChildAt(i);
								if (!checkBox.isChecked()) {
									continue;
								}
								Long formTimestamp = Long.parseLong(checkBox
										.getTag().toString());
								String[] form = serverService.getSavedForm(
										App.getUsername(),
										formTimestamp.toString());
								String formStr = Arrays.toString(form);
								formsData.append(formStr);
								formsData.append("\n\n\n");
							}
							String[] emailAddreses = { App.getSupportEmail() };
							Intent emailIntent = new Intent(Intent.ACTION_SEND);
							emailIntent.putExtra(Intent.EXTRA_EMAIL,
									emailAddreses);
							StringBuilder subject = new StringBuilder();
							subject.append(getResources().getString(
									R.string.app_name));
							subject.append(" : ");
							subject.append(App.getUsername());
							emailIntent.putExtra(Intent.EXTRA_SUBJECT,
									subject.toString());
							emailIntent.setType("plain/text");
							emailIntent.putExtra(Intent.EXTRA_TEXT,
									formsData.toString());

							for (int i = 0; i < formsRadioGroup.getChildCount(); i++) {
								MyCheckBox checkBox = (MyCheckBox) formsRadioGroup
										.getChildAt(i);
								if (!checkBox.isChecked()) {
									continue;
								}
								Long formTimestamp = Long.parseLong(checkBox
										.getTag().toString());
								serverService.deleteSavedForm(formTimestamp);
							}

							Toast.makeText(SavedFormsActivity.this,
									getResources().getString(R.string.deleted),
									App.getDelay()).show();
							startActivity(emailIntent);
							showForms();

						}
					});
					App.addDialogButton(d, getResources()
							.getString(R.string.no),
							App.dialogButtonPosition.CENTER,
							App.dialogButtonStatus.NEGATIVE);
					d.show();
				}
			}
		}
	}

	public void showAlert(String s, AlertType alertType) {

		App.getDialog(this, alertType, s, Gravity.CENTER_HORIZONTAL).show();
	}

	public void submitForms() {
		AsyncTask<String, Integer, String[]> updateTask = new AsyncTask<String, Integer, String[]>() {
			@Override
			protected String[] doInBackground(String... params) {
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
				ArrayList<String> responses = new ArrayList<String>();
				for (int i = 0; i < formsRadioGroup.getChildCount(); i++) {
					MyCheckBox checkBox = (MyCheckBox) formsRadioGroup
							.getChildAt(i);
					if (!checkBox.isChecked()) {
						continue;
					}
					Long formTimestamp = Long.parseLong(checkBox.getTag()
							.toString());
					String[] form = serverService.getSavedForm(
							App.getUsername(), formTimestamp.toString());
					try {
						String formName = form[1];
						String json = form[2];

						String response = serverService.post(json);
						JSONObject jsonResponse = JsonUtil
								.getJSONObject(response);
						if (jsonResponse.has("response")) {
							String result = jsonResponse.getString("response");
							if (result.equalsIgnoreCase("SUCCESS")) {
								Date date = new Date(formTimestamp);
								responses.add("<font color=\"green\">"
										+ formName + " ("
										+ DateTimeUtil.getSQLDate(date) + "): "
										+ result + "</font>");
								serverService.deleteSavedForm(formTimestamp);
							} else {
								responses.add("<font color=\"red\">"
										+ formTimestamp.toString() + " ("
										+ formName + "). " + response
										+ "</font>");
							}
						} else {
							responses.add("<font color=\"red\">"
									+ formTimestamp.toString() + " ("
									+ formName + "). " + response + "</font>");
						}
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
				return responses.toArray(new String[] {});
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

			@Override
			protected void onPostExecute(String[] result) {
				super.onPostExecute(result);
				loading.dismiss();
				StringBuilder response = new StringBuilder();
				for (String s : result) {
					response.append("<br>" + s + "<br>");
				}
				discardFormsButton.setEnabled(true);
				showAlert(response.toString(), AlertType.INFO);
				showForms();
			}
		};
		updateTask.execute("");
	}

	public void showForms() {
		formsRadioGroup.removeAllViews();
		String[][] forms = serverService.getSavedForms(App.getUsername());
		if (forms == null || forms.length == 0) {
			discardFormsButton.setEnabled(false);
			submitFormsButton.setEnabled(false);
		} else {
			discardFormsButton.setEnabled(true);
			submitFormsButton.setEnabled(true);
		}

		for (String[] form : forms) {
			Date date = new Date(Long.parseLong(form[0]));
			String formName = form[1];
			MyCheckBox checkBox = new MyCheckBox(this);
			checkBox.setTag(form[0]);
			checkBox.setText(formName + " \n" + form[3] + " \n"
					+ DateTimeUtil.getSQLDate(date));
			checkBox.setId(counter.getAndIncrement());
			checkBox.setTextAppearance(this, R.style.text);
			checkBox.setChecked(true);
			formsRadioGroup.addView(checkBox);
		}

		if (App.isOfflineMode()) {
			discardFormsButton.setEnabled(false);
			submitFormsButton.setEnabled(false);
		}
	}

}