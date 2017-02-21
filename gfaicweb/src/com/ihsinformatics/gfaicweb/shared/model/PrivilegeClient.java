/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

/**
 * 
 */
package com.ihsinformatics.gfaicweb.shared.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

/**
 * @author owais.hussain@ihsinformatics.com
 *
 */
@Entity
@Table(name = "privilege", uniqueConstraints = @UniqueConstraint(columnNames = "privilege"))
public class PrivilegeClient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8851538369382014142L;

	private String privilege;

	public PrivilegeClient() {
	}

	public PrivilegeClient(String privilege) {
		this.privilege = privilege;
	}

	@Id
	@Column(name = "privilege", unique = true, nullable = false, length = 45)
	public String getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((privilege == null) ? 0 : privilege.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrivilegeClient other = (PrivilegeClient) obj;
		if (privilege == null) {
			if (other.privilege != null)
				return false;
		} else if (!privilege.equals(other.privilege))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return privilege;
	}

	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("privilege", JSONParser.parseStrict(privilege));
		return jsonObject;
	}

	public Serializable fromJson(JSONObject jsonObject) {
		PrivilegeClient privilegeObj = new PrivilegeClient();
		privilegeObj.setPrivilege(jsonObject.get("privilege").toString());
		return privilegeObj;
	}
}
