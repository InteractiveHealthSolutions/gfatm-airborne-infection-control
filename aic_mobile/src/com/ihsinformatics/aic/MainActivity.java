/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */

package com.ihsinformatics.aic;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ihsinformatics.aic.util.DatabaseUtil;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";
	private static DatabaseUtil dbUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// on Application start
		MainActivity.resetPreferences(this); // loading preferences

		super.onCreate(savedInstanceState);

		try {
			dbUtil = new DatabaseUtil(this);
			dbUtil.buildDatabase(false); // build sql lite db in app memory
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}

		// Start Login Activity...
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();

	}

	/**
	 * Reads preferences from application preferences and loads into App class
	 * members
	 */
	public static void resetPreferences(Context context) {
		PreferenceManager.setDefaultValues(context, R.xml.preference, false);
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		App.setServer(preferences.getString(Preferences.SERVER, ""));
		App.setUseSsl(preferences.getBoolean(Preferences.USE_SSL, true));
		App.setUsername(preferences.getString(Preferences.USERNAME, ""));
		App.setScreenerName(preferences
				.getString(Preferences.SCREENER_NAME, ""));
		App.setPassword(preferences.getString(Preferences.PASSWORD, ""));
		App.setLocation(preferences.getString(Preferences.LOCATION, ""));
		App.setSupportContact(preferences.getString(
				Preferences.SUPPORT_CONTACT, ""));
		App.setSupportEmail(preferences
				.getString(Preferences.SUPPORT_EMAIL, ""));
		App.setCity(preferences.getString(Preferences.CITY, ""));
		App.setCountry(preferences.getString(Preferences.COUNTRY, ""));
		App.setDelay(Integer.parseInt(preferences.getString(Preferences.DELAY,
				"30000")));
		App.setAutoLogin(preferences.getBoolean(Preferences.AUTO_LOGIN, false));
		App.setLastLogin(preferences.getString(Preferences.LAST_LOGIN, ""));
		App.setRole(preferences.getString(Preferences.ROLE, ""));
		Locale locale = new Locale(preferences.getString(Preferences.LANGUAGE,
				"en").substring(0, 2));
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		context.getApplicationContext().getResources()
				.updateConfiguration(config, null);
		App.setCurrentLocale(locale);
		String version = "0";
		try {
			version = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		App.setVersion(version);
	}

}
