/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors. */
/**
 * Custom EditText view for ease
 */

package com.ihsinformatics.aic.custom;

import android.content.Context;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.widget.EditText;

import com.ihsinformatics.aic.R;

/**
 * @author owais.hussain@irdresearch.org
 * 
 */
public class MyEditText extends EditText {
	public MyEditText(Context context) {
		super(context);
	}

	private int maxLength;

	/**
	 * 
	 * @param context
	 * @param tag
	 *            Text Id from resources. Pass -1 if no tag is to be set
	 * @param hint
	 *            Text Id from resources. Pass -1 if no hint is to be set
	 * @param inputType
	 *            InputType enum value
	 * @param style
	 *            Style Id from resources. Pass -1 to keep default style
	 * @param maxLength
	 *            Limit of number of maximum characters. Pass 0 for unlimited
	 */
	public MyEditText(Context context, int tag, int hint, int inputType,
			int style, int maxLength, boolean multiline) {
		super(context);
		setInputType(inputType);
		if (style != -1) {
			setTextAppearance(context, style);
		}
		if (tag != -1) {
			setTag(getResources().getString(tag));
		}
		if (hint != -1) {
			setHint(hint);
		}
		if (maxLength > 0) {
			this.setMaxLength(maxLength);
			setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					maxLength) });
		}
		if (!multiline) {
			setMaxLines(1);
		}
	}

	public void setEnabled(boolean flag) {
		super.setEnabled(flag);
		if (flag) {
			setTextColor(getResources().getColor(R.color.mainTheme));
		} else {
			setTextColor(getResources().getColor(R.color.DarkGray));
		}
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	private KeyImeChange keyImeChangeListener;

	public void setKeyImeChangeListener(KeyImeChange listener) {
		keyImeChangeListener = listener;
	}

	public interface KeyImeChange {
		public void onKeyIme(int keyCode, KeyEvent event);
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent event) {
		if (keyImeChangeListener != null) {
			keyImeChangeListener.onKeyIme(keyCode, event);
		}
		return false;
	}

}
