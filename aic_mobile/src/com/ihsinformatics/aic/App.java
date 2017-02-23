/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * This class is used to hold data and other methods that are globally accessed
 */

package com.ihsinformatics.aic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ihsinformatics.aic.model.OpenMrsObject;
import com.ihsinformatics.aic.shared.AlertType;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class App {
	private static String server = "";
	private static String username = "";
	private static String password = "";
	private static String location = "";
	private static String supportContact = "";
	private static String supportEmail = "";
	private static String unionCouncil = "";
	private static String city = "";
	private static String landlineCode = "";
	private static String country = "";
	private static String version = "";
	private static String screenerName = "";
	private static String email = "";
	private static String name = "";
	private static String contactNumber = "";
	private static int delay = 3000;
	private static boolean useSsl = true;
	private static boolean autoLogin = true;
	private static boolean offlineMode = false;
	private static String lastLogin = "";
	private static String role = "";

	private static OpenMrsObject currentUser;
	private static Locale currentLocale;

	public enum dialogButtonPosition {
		LEFT, CENTER, RIGHT
	}

	public enum dialogButtonStatus {
		NEUTRAL, POSITIVE, NEGATIVE
	}

	public static void setThreadSafety(boolean state) {
		StrictMode.ThreadPolicy policy = StrictMode.getThreadPolicy();
		if (state) {
			policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		} else {
			policy = new StrictMode.ThreadPolicy.Builder().detectAll().build();
		}
		StrictMode.setThreadPolicy(policy);
	}

	public static String getServer() {
		return server;
	}

	public static void setServer(String server) {
		App.server = server;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		App.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		App.password = password;
	}

	public static String getLocation() {
		return location;
	}

	public static void setLocation(String location) {
		App.location = location;
	}

	public static String getSupportContact() {
		return supportContact;
	}

	public static void setSupportContact(String supportContact) {
		App.supportContact = supportContact;
	}

	public static String getSupportEmail() {
		return supportEmail;
	}

	public static void setSupportEmail(String supportEmail) {
		App.supportEmail = supportEmail;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		App.city = city;
	}

	public static String getCountry() {
		return country;
	}

	public static void setCountry(String country) {
		App.country = country;
	}

	public static int getDelay() {
		return delay;
	}

	public static void setDelay(int delay) {
		App.delay = delay;
	}

	public static boolean isUseSsl() {
		return useSsl;
	}

	public static boolean isAutoLogin() {
		return autoLogin;
	}

	public static void setAutoLogin(boolean autoLogin) {
		App.autoLogin = autoLogin;
	}

	public static void setUseSsl(boolean useSsl) {
		App.useSsl = useSsl;
	}

	public static boolean isOfflineMode() {
		return offlineMode;
	}

	public static void setOfflineMode(boolean offlineMode) {
		App.offlineMode = offlineMode;
	}

	public static Locale getCurrentLocale() {
		return currentLocale;
	}

	public static void setCurrentLocale(Locale currentLocale) {
		App.currentLocale = currentLocale;
	}

	public static String getLastLogin() {
		return lastLogin;
	}

	public static void setLastLogin(String lastLogin) {
		App.lastLogin = lastLogin;
	}

	public static String getScreenerName() {
		return screenerName;
	}

	public static void setScreenerName(String screenerName) {
		App.screenerName = screenerName;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		App.name = name;
	}

	public static String getContactNumber() {
		return contactNumber;
	}

	public static void setContactNumber(String contactNumber) {
		App.contactNumber = contactNumber;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		App.email = email;
	}

	/**
	 * Returns true if system language is Right-to-Left
	 * 
	 * @return
	 */
	public static boolean isLanguageRTL() {
		String code = currentLocale.getLanguage();
		if (code.equals("ar") || code.equals("fa") || code.equals("he")
				|| code.equals("ur"))
			return true;
		return false;
	}

	public static void setVersion(String version) {
		App.version = version;
	}

	public static String getVersion() {
		return version;
	}

	public static void setRole(String role) {
		App.role = role;
	}

	public static String getRole() {
		return role;
	}

	/**
	 * Returns selected value in string, depending on the view passed. If no
	 * value is present, an empty string will be returned
	 * 
	 * @param view
	 * @return
	 */
	public static String get(View view) {
		String str = null;
		if (view instanceof TextView) {
			str = ((TextView) view).getText().toString();
		} else if (view instanceof Spinner) {
			str = ((Spinner) view).getSelectedItem().toString();
		} else if (view instanceof EditText) {
			str = ((EditText) view).getText().toString();
		}

		return (str == null ? "" : str);
	}

	/**
	 * Returns instance of AlertDialog, based on the type provided
	 * 
	 * @param context
	 * @param type
	 * @param message
	 * @return
	 */
	public static AlertDialog getAlertDialog(Context context, AlertType type,
			String message) {
		AlertDialog dialog;
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(message);
		switch (type) {
		case ERROR:
			builder.setIcon(R.drawable.error);
			break;
		case INFO:
			builder.setIcon(R.drawable.info);
			break;
		case QUESTION:
			builder.setIcon(R.drawable.question);
			break;
		}
		dialog = builder.create();
		dialog.setTitle(type.toString());
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", listener);
		return dialog;
	}

	public static Dialog getDialog(Context context, AlertType type,
			String message, int gravity) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.dialog_custom_alert);

		TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
		text.setText(Html.fromHtml(message));
		text.setGravity(Gravity.CENTER);

		ImageView imageView = (ImageView) dialog.findViewById(R.id.a);
		Button dialogButton = (Button) dialog.findViewById(R.id.center_button);
		TextView title = (TextView) dialog.findViewById(R.id.title_dialog);

		if (gravity != 0)
			text.setGravity(gravity);

		switch (type) {
		case ERROR:
			imageView.setImageDrawable(context.getResources().getDrawable(
					R.drawable.dialog_cross));
			imageView.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.pink_background));
			dialogButton.setBackgroundResource(R.drawable.pink_button);
			title.setTextColor(context.getResources().getColor(
					R.color.error_color));
			break;
		case SUCCESS:
			imageView.setImageDrawable(context.getResources().getDrawable(
					R.drawable.dialog_success));
			imageView.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.green_background));
			dialogButton.setBackgroundResource(R.drawable.green_button);
			title.setTextColor(context.getResources().getColor(
					R.color.success_color));
			break;
		case INFO:
			imageView.setImageDrawable(context.getResources().getDrawable(
					R.drawable.dialog_info));
			imageView.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.khaki_background));
			dialogButton.setBackgroundResource(R.drawable.khaki_background);
			title.setTextColor(context.getResources().getColor(
					R.color.question_color));
			break;
		case QUESTION:
			imageView.setImageDrawable(context.getResources().getDrawable(
					R.drawable.dialog_question));
			imageView.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.khaki_background));
			dialogButton.setBackgroundResource(R.drawable.khaki_background);
			title.setTextColor(context.getResources().getColor(
					R.color.question_color));
			break;
		case URGENT:
			imageView.setImageDrawable(context.getResources().getDrawable(
					R.drawable.dialog_info));
			imageView.setBackgroundDrawable(context.getResources().getDrawable(
					R.drawable.red_background));
			dialogButton.setBackgroundResource(R.drawable.red_button);
			title.setTextColor(context.getResources().getColor(
					R.color.info_color));
			break;

		}

		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.cancel();
					return true;
				}
				return false;
			}
		});

		return dialog;
	}

	public static Dialog getDialog(Context context, String title,
			ArrayList<String> arrayOption) {
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setContentView(R.layout.dialog_radio_button_alert);

		TextView titleTextView = (TextView) dialog.findViewById(R.id.imageView);
		titleTextView.setText(title);

		LinearLayout layout = (LinearLayout) dialog
				.findViewById(R.id.listAlert);

		RadioGroup rg = new RadioGroup(dialog.getContext());
		for (String option : arrayOption) {
			RadioButton rb = new RadioButton(dialog.getContext());
			rb.setText(option);

			if (App.getLocation().equals(option)) {
				rb.setChecked(true);

			}

			rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					if (isChecked) {
						App.setLocation(buttonView.getText().toString());

						SharedPreferences preferences = PreferenceManager
								.getDefaultSharedPreferences(dialog
										.getContext());
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString(Preferences.LOCATION,
								App.getLocation());
						editor.apply();

						dialog.dismiss();
					}

				}
			});

			rg.addView(rb);
		}

		layout.addView(rg);

		Button okButton = (Button) dialog.findViewById(R.id.okButton);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.cancel();
					return true;
				}
				return false;
			}
		});

		return dialog;
	}

	public static void setDialogTitle(Dialog dialog, String title) {

		TextView text = (TextView) dialog.findViewById(R.id.title_dialog);
		text.setText(title);
		text.setVisibility(View.VISIBLE);

	}

	public static TextView addTroubleshootId(Dialog dialog, String id) {
		LinearLayout layout = (LinearLayout) dialog
				.findViewById(R.id.listsLayout);

		LinearLayout innerLayout = new LinearLayout(dialog.getContext());
		innerLayout.setOrientation(LinearLayout.HORIZONTAL);
		innerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		TextView text1 = new TextView(dialog.getContext());
		text1.setText(layout.getChildCount() + 1 + ".");
		text1.setTextColor(dialog.getContext().getResources()
				.getColor(R.color.mainTheme));
		innerLayout.addView(text1);
		TextView text = new TextView(dialog.getContext());
		text.setText(id);
		innerLayout.addView(text);

		layout.addView(innerLayout);

		layout.setVisibility(View.VISIBLE);

		return text;
	}

	public static Button addDialogButton(Dialog dialog, String message,
			dialogButtonPosition pos, dialogButtonStatus status) {

		Button button = null;

		switch (pos) {

		case LEFT:
			button = (Button) dialog.findViewById(R.id.left_button);
			break;

		case CENTER:
			button = (Button) dialog.findViewById(R.id.center_button);
			break;

		case RIGHT:
			button = (Button) dialog.findViewById(R.id.right_button);
			break;

		}

		button.setText(message);
		button.setVisibility(View.VISIBLE);

		switch (status) {

		case NEUTRAL:
			button.setBackgroundResource(R.drawable.blue_button);
			break;

		case NEGATIVE:
			button.setBackgroundResource(R.drawable.pink_button);
			break;

		case POSITIVE:
			button.setBackgroundResource(R.drawable.green_button);
			break;

		}

		return button;

	}

	/*
	 * public static Dialog getGuestLoginDialog (final Context context) { final
	 * Dialog dialog = new Dialog(context);
	 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	 * dialog.setCancelable(false); dialog.getWindow().setBackgroundDrawable(new
	 * ColorDrawable(android.graphics.Color.TRANSPARENT));
	 * dialog.setContentView(R.layout.guest_login_dialog);
	 * 
	 * TextView close = (TextView)
	 * dialog.findViewById(R.guest_id.closeTextView);
	 * close.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { dialog.dismiss(); } });
	 * 
	 * Button guestLogin = (Button) dialog.findViewById(R.guest_id.loginButton);
	 * guestLogin.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * final ContentValues values = new ContentValues ();
	 * 
	 * values.put ("username", "admin"); values.put ("password", "Admin123");
	 * values.put ("starttime", App.getSqlDate(new Date())); values.put
	 * ("location", "IHS"); values.put ("entereddate", App.getSqlDate(new
	 * Date()));
	 * 
	 * ServerService serverService = new ServerService(context); String exists =
	 * serverService.authenticate (RequestType.LOGIN, values);
	 * 
	 * String[] resultArray = exists.split(":;:"); if
	 * (resultArray[0].equals("SUCCESS")){ dialog.dismiss(); } } });
	 * 
	 * dialog.setOnKeyListener(new Dialog.OnKeyListener() {
	 * 
	 * @Override public boolean onKey(DialogInterface arg0, int keyCode,
	 * KeyEvent event) { // TODO Auto-generated method stub if (keyCode ==
	 * KeyEvent.KEYCODE_BACK) { dialog.dismiss(); } return true; } });
	 * 
	 * 
	 * return dialog; }
	 */

	/**
	 * Returns date in sql date string format
	 * 
	 * @param date
	 * @return
	 */
	public static String getSqlDate(Calendar date) {
		return DateFormat.format("yyyy-MM-dd", date).toString();
	}

	/**
	 * Returns date in sql date string format
	 * 
	 * @param date
	 * @return
	 */
	public static String getSqlDateTime(Calendar date) {
		return DateFormat.format("yyyy-MM-dd hh:mm:ss", date).toString();
	}

	/**
	 * Returns date in sql date string format
	 * 
	 * @param date
	 * @return
	 */
	public static String getSqlDate(Date date) {
		return DateFormat.format("yyy-MM-dd", date).toString();
	}

	/**
	 * Returns date in sql date string format
	 * 
	 * @param date
	 * @return
	 */
	public static String getSqlDateTime(Date date) {
		return DateFormat.format("yyy-MM-dd hh:mm:ss", date).toString();
	}

	/**
	 * Returns string array adapter containing the items passed in parameter.
	 * 
	 * @param context
	 * @param list
	 * @return
	 */
	public static ArrayAdapter<String> getAdapter(Context context, String[] list) {
		return new ArrayAdapter<String>(context,
				android.R.layout.select_dialog_item, list);
	}

	public static boolean isJSONValid(String test) {
		try {
			new JSONObject(test);
		} catch (JSONException ex) {
			// edited, to include @Arthur's comment
			// e.g. in case JSONArray is valid as well...
			try {
				new JSONArray(test);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

}
