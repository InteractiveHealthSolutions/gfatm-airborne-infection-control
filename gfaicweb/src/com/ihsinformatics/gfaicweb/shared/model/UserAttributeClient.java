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
 * UserAttribute generated by hbm2java
 */
@Entity
@Table(name = "user_attribute", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class UserAttributeClient implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1045714733584474550L;
	private Integer userAttributeId;
	private UsersClient usersByCreatedBy;
	private LocationClient locationByCreatedAt;
	private UserAttributeTypeClient userAttributeType;
	private LocationClient locationByChangedAt;
	private UsersClient usersByChangedBy;
	private UsersClient userId;
	private String attributeValue;
	private Date dateCreated;
	private Date dateChanged;
	private String uuid;

	public UserAttributeClient() {
	}

	public UserAttributeClient(UsersClient user,
			UserAttributeTypeClient userAttributeType, String attributeValue) {
		this.userId = user;
		this.userAttributeType = userAttributeType;
		this.attributeValue = attributeValue;
	}

	public UserAttributeClient(UsersClient user,
			UserAttributeTypeClient userAttributeType, String attributeValue,
			UsersClient createdBy, Date dateCreated, String uuid) {
		this.userId = user;
		this.userAttributeType = userAttributeType;
		this.attributeValue = attributeValue;
		this.usersByCreatedBy = createdBy;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public UserAttributeClient(UserAttributeTypeClient userAttributeType,
			UsersClient user, String attributeValue, UsersClient createdBy,
			LocationClient createdAt, Date dateCreated, String uuid) {
		this.usersByCreatedBy = createdBy;
		this.locationByCreatedAt = createdAt;
		this.userAttributeType = userAttributeType;
		this.userId = user;
		this.attributeValue = attributeValue;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public UserAttributeClient(UsersClient usersByCreatedBy,
			LocationClient locationByCreatedAt,
			UserAttributeTypeClient userAttributeType,
			LocationClient locationByChangedAt, UsersClient usersByChangedBy,
			UsersClient user, String attributeValue, Date dateCreated,
			Date dateChanged, String uuid) {
		this.usersByCreatedBy = usersByCreatedBy;
		this.locationByCreatedAt = locationByCreatedAt;
		this.userAttributeType = userAttributeType;
		this.locationByChangedAt = locationByChangedAt;
		this.usersByChangedBy = usersByChangedBy;
		this.userId = user;
		this.attributeValue = attributeValue;
		this.dateCreated = dateCreated;
		this.dateChanged = dateChanged;
		this.uuid = uuid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_attribute_id", unique = true, nullable = false)
	public Integer getUserAttributeId() {
		return this.userAttributeId;
	}

	public void setUserAttributeId(Integer userAttributeId) {
		this.userAttributeId = userAttributeId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_attribute_type_id", nullable = false)
	public UserAttributeTypeClient getUserAttributeType() {
		return this.userAttributeType;
	}

	public void setUserAttributeType(UserAttributeTypeClient userAttributeType) {
		this.userAttributeType = userAttributeType;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public UsersClient getUserId() {
		return this.userId;
	}

	public void setUserId(UsersClient user) {
		this.userId = user;
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
		return userAttributeId + ", " + userId.getUserId() + ", "
				+ attributeValue + ", " + dateCreated + ", " + dateChanged
				+ ", " + uuid;
	}

}
