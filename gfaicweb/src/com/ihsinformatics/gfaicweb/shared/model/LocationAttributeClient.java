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
 * LocationAttribute generated by hbm2java
 */
@Entity
@Table(name = "location_attribute", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class LocationAttributeClient implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -740889293115716329L;
	private Integer locationAttributeId;
	private UsersClient usersByCreatedBy;
	private LocationClient locationByCreatedAt;
	private LocationAttributeTypeClient locationAttributeType;
	private LocationClient locationByChangedAt;
	private UsersClient usersByChangedBy;
	private LocationClient locationByLocationId;
	private String attributeValue;
	private Date dateCreated;
	private Date dateChanged;
	private String uuid;

	public LocationAttributeClient() {
	}

	public LocationAttributeClient(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public LocationAttributeClient(String attributeValue, Date dateCreated,
			UsersClient createdBy, String uuid) {
		this.attributeValue = attributeValue;
		this.usersByCreatedBy = createdBy;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public LocationAttributeClient(
			LocationAttributeTypeClient locationAttributeType,
			LocationClient locationId, String attributeValue,
			UsersClient createdBy, LocationClient createdAt, Date dateCreated,
			String uuid) {
		this.usersByCreatedBy = createdBy;
		this.locationByCreatedAt = createdAt;
		this.locationAttributeType = locationAttributeType;
		this.locationByLocationId = locationId;
		this.attributeValue = attributeValue;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public LocationAttributeClient(UsersClient usersByCreatedBy,
			LocationClient locationByCreatedAt,
			LocationAttributeTypeClient locationAttributeType,
			LocationClient locationByChangedAt, UsersClient usersByChangedBy,
			LocationClient locationByLocationId, String attributeValue,
			Date dateCreated, Date dateChanged, String uuid) {
		this.usersByCreatedBy = usersByCreatedBy;
		this.locationByCreatedAt = locationByCreatedAt;
		this.locationAttributeType = locationAttributeType;
		this.locationByChangedAt = locationByChangedAt;
		this.usersByChangedBy = usersByChangedBy;
		this.locationByLocationId = locationByLocationId;
		this.attributeValue = attributeValue;
		this.dateCreated = dateCreated;
		this.dateChanged = dateChanged;
		this.uuid = uuid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "location_attribute_id", unique = true, nullable = false)
	public Integer getLocationAttributeId() {
		return this.locationAttributeId;
	}

	public void setLocationAttributeId(Integer locationAttributeId) {
		this.locationAttributeId = locationAttributeId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "attribute_type_id")
	public LocationAttributeTypeClient getLocationAttributeType() {
		return this.locationAttributeType;
	}

	public void setLocationAttributeType(
			LocationAttributeTypeClient locationAttributeType) {
		this.locationAttributeType = locationAttributeType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "location_id", nullable = false)
	public LocationClient getLocationByLocationId() {
		return this.locationByLocationId;
	}

	public void setLocationByLocationId(LocationClient locationByLocationId) {
		this.locationByLocationId = locationByLocationId;
	}

	@Column(name = "attribute_value", nullable = false)
	public String getAttributeValue() {
		return this.attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
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
		return locationAttributeId + ", " + attributeValue + ", " + dateCreated
				+ ", " + dateChanged + ", " + uuid;
	}
}
