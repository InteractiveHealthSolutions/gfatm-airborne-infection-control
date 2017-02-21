/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

package com.ihsinformatics.gfaicweb.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.ihsinformatics.gfaicweb.shared.DateTimeUtil;

/**
 * @author owais.hussain@ihsinformatics.com
 * 
 */
public abstract class AbstractComposite extends Composite implements
		ClickHandler {

	boolean canView = false, canCreate = false, canEdit = false,
			canDelete = false;

	/**
	 * Get usually desired value from a widget 1. Text fields return their
	 * respective text 2. List boxes return selected value
	 * 
	 * @param widget
	 * @return
	 */
	public String get(Widget widget) {
		try {
			if (widget instanceof TextBoxBase)
				return ((TextBoxBase) widget).getText();
			if (widget instanceof ListBox)
				return ((ListBox) widget).getValue(((ListBox) widget)
						.getSelectedIndex());
			if (widget instanceof ValueBoxBase<?>)
				return ((ValueBoxBase<?>) widget).getText();
			if (widget instanceof DateBox)
				return DateTimeUtil.getFormattedDate(
						((DateBox) widget).getValue(),
						DateTimeUtil.SQL_DATETIME);
		} catch (Exception e) {
		}
		return "";
	}
	/**
	 * 
	 * @param listbox
	 * @return
	 */
	public ArrayList<String> getSelectedIndicesNames(ListBox listbox){
		 ArrayList<String> list = new ArrayList<String>();
		 for(int i=0; i<listbox.getItemCount(); i++){
			 if(listbox.isItemSelected(i)){
				 list.add(listbox.getItemText(i));
			 }
		 }
		 return list;
	}
	
	/**
	 * 
	 * @param listbox
	 * @return
	 */
	public ArrayList<Integer> getSelectedIndices(ListBox listbox){
		 ArrayList<Integer> list = new ArrayList<Integer>();
		 for(int i=0; i<listbox.getItemCount(); i++){
			 if(listbox.isItemSelected(i)){
				 list.add(i);
			 }
		 }
		return list;
	}

	/**
	 * Get index of a given value from a widget (probably ListBox). Returns -1
	 * if value not found
	 * 
	 * @param widget
	 * 
	 * @param value
	 * @return
	 */
	public int getIndex(Widget widget, String value) {
		if (widget instanceof ListBox) {
			ListBox listBox = (ListBox) widget;
			for (int i = 0; i < listBox.getItemCount(); i++)
				if (listBox.getValue(i).equalsIgnoreCase(value))
					return i;
		}
		return -1;
	}

	/**
	 * Clears/Resets values in child widgets of the widget passed, depending
	 * upon the widget type
	 * 
	 * @param widget
	 */
	public void clear(Widget widget) {
		if (widget instanceof Panel) {
			Iterator<Widget> iter = ((Panel) widget).iterator();
			while (iter.hasNext())
				clear(iter.next());
		} else if (widget instanceof TextBoxBase) {
			((TextBoxBase) widget).setText("");
		} else if (widget instanceof RichTextArea) {
			((RichTextArea) widget).setText("");
		} else if (widget instanceof ListBox) {
			((ListBox) widget).setSelectedIndex(0);
		} else if (widget instanceof DatePicker) {
			((DatePicker) widget).setValue(new Date());
		}
	}

	public void close() {
		TBR.setCurrentForm(null);
		MasterComposite.setCompositePanelWidget(null);
	}

	/**
	 * Initialize events, lists, checks, defaults, etc.
	 */
	public abstract void init();

	/**
	 * Fill data in lists and combo boxes
	 */
	public abstract void fill();

	/**
	 * Validate input data
	 */
	public abstract boolean validate();

	/**
	 * Enable/Disable operations based on user's privileges
	 */
	public abstract void setPrivileges();

	/**
	 * Create new record
	 */
	public abstract void save();

	/**
	 * Update current record
	 */
	public abstract void update();

	/**
	 * Delete a record with dependencies
	 */
	public abstract void delete();

}
