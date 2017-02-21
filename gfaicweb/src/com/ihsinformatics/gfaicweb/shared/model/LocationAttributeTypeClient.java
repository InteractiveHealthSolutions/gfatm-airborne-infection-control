/*
Copyright(C) 2016 Interactive Health Solutions, Pvt. Ltd.
This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.
 */

/**
 * @author owais.hussain@ihsinformatics.com
 */

package com.ihsinformatics.gfaicweb.shared.model;

// Generated Dec 9, 2015 9:08:22 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * LocationAttributeType generated by hbm2java
 */
@Entity
@Table(name = "location_attribute_type", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class LocationAttributeTypeClient implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 5528218809005656179L;
	private Integer locationAttributeTypeId;
	private UsersClient usersByCreatedBy;
	private LocationClient locationByCreatedAt;
	private LocationClient locationByChangedAt;
	private UsersClient usersByChangedBy;
	private String attributeName;
	private String dataType;
	private String validationRegex;
	private Boolean required;
	private String description;
	private Date dateCreated;
	private Date dateChanged;
	private String uuid;

	public LocationAttributeTypeClient() {
	}

	public LocationAttributeTypeClient(String attributeName, Boolean required) {
		this.required = required;
	}

	public LocationAttributeTypeClient(String attributeName,
			UsersClient createdBy, Date dateCreated, Boolean required,
			String uuid) {
		this.usersByCreatedBy = createdBy;
		this.dateCreated = dateCreated;
		this.required = required;
		this.uuid = uuid;
	}

	public LocationAttributeTypeClient(String attributeName, String dataType,
			String validationRegex, Boolean required, String description,
			UsersClient createdBy, LocationClient createdAt, Date dateCreated,
			String uuid) {
		this.usersByCreatedBy = createdBy;
		this.locationByCreatedAt = createdAt;
		this.attributeName = attributeName;
		this.dataType = dataType;
		this.validationRegex = validationRegex;
		this.required = required;
		this.description = description;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public LocationAttributeTypeClient(UsersClient usersByCreatedBy,
			LocationClient locationByCreatedAt,
			LocationClient locationByChangedAt, UsersClient usersByChangedBy,
			String attributeName, String dataType, String validationRegex,
			Boolean required, String description, Date dateCreated,
			Date dateChanged, String uuid) {
		this.usersByCreatedBy = usersByCreatedBy;
		this.locationByCreatedAt = locationByCreatedAt;
		this.locationByChangedAt = locationByChangedAt;
		this.usersByChangedBy = usersByChangedBy;
		this.attributeName = attributeName;
		this.dataType = dataType;
		this.validationRegex = validationRegex;
		this.required = required;
		this.description = description;
		this.dateCreated = dateCreated;
		this.dateChanged = dateChanged;
		this.uuid = uuid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_attribute_type_id", unique = true, nullable = false)
	public Integer getLocationAttributeTypeId() {
		return this.locationAttributeTypeId;
	}

	public void setLocationAttributeTypeId(Integer locationAttributeTypeId) {
		this.locationAttributeTypeId = locationAttributeTypeId;
	}

	@Column(name = "attribute_name", nullable = false, unique = true, length = 45)
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	public UsersClient getUsersByCreatedBy() {
		return this.usersByCreatedBy;
	}

	public void setUsersByCreatedBy(UsersClient usersByCreatedBy) {
		this.usersByCreatedBy = usersByCreatedBy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_at")
	public LocationClient getLocationByCreatedAt() {
		return this.locationByCreatedAt;
	}

	public void setLocationByCreatedAt(LocationClient locationByCreatedAt) {
		this.locationByCreatedAt = locationByCreatedAt;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changed_at")
	public LocationClient getLocationByChangedAt() {
		return this.locationByChangedAt;
	}

	public void setLocationByChangedAt(LocationClient locationByChangedAt) {
		this.locationByChangedAt = locationByChangedAt;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "changed_by")
	public UsersClient getUsersByChangedBy() {
		return this.usersByChangedBy;
	}

	public void setUsersByChangedBy(UsersClient usersByChangedBy) {
		this.usersByChangedBy = usersByChangedBy;
	}

	@Column(name = "data_type", length = 10, nullable = false)
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Column(name = "validation_regex")
	public String getValidationRegex() {
		return this.validationRegex;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@Column(name = "required", nullable = false)
	public Boolean getRequired() {
		return this.required;
	}

	public void setValidationRegex(String validationRegex) {
		this.validationRegex = validationRegex;
	}

	@Column(name = "description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = false, length = 19)
	public Date getDateCreated() {
		return this.dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_changed", length = 19)
	public Date getDateChanged() {
		return this.dateChanged;
	}

	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}

	@Column(name = "uuid", unique = true, nullable = false, length = 38)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return locationAttributeTypeId + ", " + attributeName + ", "
				+ validationRegex + ", " + required + ", " + description + ", "
				+ dateCreated + ", " + dateChanged + ", " + uuid;
	}
}
