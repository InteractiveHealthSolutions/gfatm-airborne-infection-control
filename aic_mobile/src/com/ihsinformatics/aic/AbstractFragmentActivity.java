/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * Abstract class to be extended by Fragment Activities
 */

package com.ihsinformatics.aic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.ihsinformatics.aic.custom.MyEditText;
import com.ihsinformatics.aic.shared.AlertType;
import com.ihsinformatics.aic.util.ServerService;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public abstract class AbstractFragmentActivity extends FragmentActivity
		implements OnClickListener, OnPageChangeListener,
		OnSeekBarChangeListener, OnItemSelectedListener,
		OnCheckedChangeListener, OnLongClickListener {
	public static final int TIME_DIALOG_ID = 0;
	public static final int DATE_DIALOG_ID = 1;
	public static final int TEST_DATE_DIALOG_ID = 2;
	protected static String TAG;
	protected static String FORM_NAME;
	protected static int PAGE_COUNT = 0;
	protected static ProgressDialog loading;
	protected ServerService serverService;
	protected Calendar formDate;
	// Layout view containing navigation bar and buttons
	protected LinearLayout navigatorLayout;
	protected Button firstButton;
	protected Button lastButton;
	protected Button nextButton;
	protected Button saveButton;
	protected Button clearButton;
	protected Button pageButton;
	protected SeekBar navigationSeekbar;
	// View pager to holds all the pages generated dynamically
	protected ViewPager pager;
	// ArrayList to hold all the linear layouts-1 for each page
	protected ArrayList<ViewGroup> groups;
	protected View[] views;
	protected Animation alphaAnimation;
	protected View[] mandatoryViews;

	/**
	 * Set theme, create and initialize members on Activity Create
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.template);
		// Initialize server service for server calls
		serverService = new ServerService(getApplicationContext());
		loading = new ProgressDialog(this);
		formDate = Calendar.getInstance();
		// Navivation bar layout
		navigatorLayout = (LinearLayout) findViewById(R.template_id.navigatorLayout);
		firstButton = (Button) findViewById(R.template_id.first_button);
		lastButton = (Button) findViewById(R.template_id.last_button);
		nextButton = (Button) findViewById(R.template_id.next_button);
		clearButton = (Button) findViewById(R.template_id.clearButton);
		saveButton = (Button) findViewById(R.template_id.saveButton);
		pageButton = (Button) findViewById(R.template_id.pageCount);
		navigationSeekbar = (SeekBar) findViewById(R.template_id.navigationSeekbar);
		alphaAnimation = AnimationUtils.loadAnimation(this,
				R.anim.alpha_animation);

		createViews(this);
		initView(views);

		ActionBar actionBar = getActionBar();
		actionBar.setTitle(FORM_NAME);

		if (App.isOfflineMode())
			actionBar.setSubtitle("-- Offline Mode --");

	}

	/**
	 * Displays Date or Time dialog and sets the selected value to formDate
	 */
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
		}
		return null;
	}

	/**
	 * Creates all views and their event listeners
	 * 
	 * @param context
	 */
	public abstract void createViews(Context context);

	/**
	 * Resets all the views passed in parameter
	 * 
	 * @param views
	 *            array of Views
	 */
	public void initView(View[] views) {
		for (View v : views) {
			if (v instanceof Spinner) {
				((Spinner) v).setSelection(0);
			} else if (v instanceof EditText || v instanceof MyEditText) {
				((EditText) v).setText("");
				((EditText) v).setHintTextColor(getResources().getColor(
						R.color.Grey));
			} else if (v instanceof RadioButton) {
				((RadioButton) v).setChecked(true);
			}
		}

		gotoFirstPage();
		setPageCountStatus();
	}

	/**
	 * Updates data in form views
	 */
	public abstract void updateDisplay();

	/**
	 * Goto first view in the pager
	 */
	public void gotoNextPage() {

		if (App.isLanguageRTL()) {
			if (pager.getCurrentItem() - 1 >= 0)
				gotoPage(pager.getCurrentItem() - 1);
		} else {
			if (pager.getCurrentItem() + 1 != PAGE_COUNT)
				gotoPage(pager.getCurrentItem() + 1);
		}
	}

	/**
	 * Goto first view in the pager
	 */
	public void gotoFirstPage() {
		if (App.isLanguageRTL()) {
			gotoPage(PAGE_COUNT - 1);
		} else {
			gotoPage(0);
		}
	}

	/**
	 * Goto last view in the pager
	 */
	public void gotoLastPage() {
		if (App.isLanguageRTL()) {
			gotoPage(0);
		} else {
			gotoPage(PAGE_COUNT - 1);
		}
	}

	/**
	 * Goto view at given location in the pager
	 */
	protected void gotoPage(int pageNo) {

		pager.setCurrentItem(pageNo);
		navigationSeekbar.setProgress(pageNo);
	}

	/**
	 * Validate form views and values
	 * 
	 * @return
	 */
	public abstract boolean validate();

	/**
	 * Submit the form to the server
	 * 
	 * @return
	 */
	public abstract boolean submit();

	@Override
	public void onNothingSelected(AdapterView<?> view) {
		// Not implemented
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekbar) {
		// Not implemented
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekbar) {
		// Not implemented
	}

	@Override
	public void onProgressChanged(SeekBar seekbar, int progress,
			boolean isByUser) {
		// Move to page at the index of progress
		pager.setCurrentItem(progress);
		setPageCountStatus();
	}

	@Override
	public void onPageSelected(int pageNo) {
		gotoPage(pageNo);

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
		updateDisplay();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// Not implemented
	}

	public void setPageCountStatus() {

		String currentPage = "";
		String totalPage = "";

		if (App.isLanguageRTL()) {

			int count = PAGE_COUNT - pager.getCurrentItem();

			if (count < 10)
				currentPage = "0" + (count);
			else
				currentPage = "" + (count);

			if (PAGE_COUNT < 10)
				totalPage = "0" + PAGE_COUNT;
			else
				totalPage = "" + PAGE_COUNT;

		} else {

			if (pager.getCurrentItem() + 1 < 10)
				currentPage = "0" + (pager.getCurrentItem() + 1);
			else
				currentPage = "" + (pager.getCurrentItem() + 1);

			if (PAGE_COUNT < 10)
				totalPage = "0" + PAGE_COUNT;
			else
				totalPage = "" + PAGE_COUNT;

		}

		String page = currentPage + "/" + totalPage;
		pageButton.setText(page);

	}

	public boolean isCallable(Intent intent) {
		List<ResolveInfo> list = getPackageManager().queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public void showAlert(String s, AlertType alertType) {

		App.getDialog(this, alertType, s, Gravity.CENTER_HORIZONTAL).show();
	}
}
