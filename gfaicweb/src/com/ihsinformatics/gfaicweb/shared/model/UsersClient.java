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
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "uuid"))
public class UsersClient implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 5639287915088163382L;
	private Integer userId;
	private UsersClient usersByCreatedBy;
	private LocationClient locationByCreatedAt;
	private LocationClient locationByChangedAt;
	private UsersClient usersByChangedBy;
	private String username;
	private String fullName;
	private Boolean globalDataAccess;
	private Boolean disabled;
	private String reasonDisabled;
	private String passwordHash;
	private String secretQuestion;
	private String secretAnswerHash;
	private Date dateCreated;
	private Date dateChanged;
	private String uuid;

	public UsersClient() {
	}

	public UsersClient(String username) {
		this.username = username;
	}

	public UsersClient(String username, UsersClient createdBy,
			Date dateCreated, String uuid) {
		this.username = username;
		this.usersByCreatedBy = createdBy;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public UsersClient(Integer userId, String username, String fullName,
			Boolean globalDataAccess, UsersClient createdBy,
			LocationClient createdAt, String passwordHash, Date dateCreated,
			String uuid) {
		super();
		this.userId = userId;
		this.usersByCreatedBy = createdBy;
		this.locationByCreatedAt = createdAt;
		this.username = username;
		this.fullName = fullName;
		this.globalDataAccess = globalDataAccess;
		this.passwordHash = passwordHash;
		this.dateCreated = dateCreated;
		this.uuid = uuid;
	}

	public UsersClient(Integer userId, UsersClient usersByCreatedBy,
			LocationClient locationByCreatedAt,
			LocationClient locationByChangedAt, UsersClient usersByChangedBy,
			String username, String fullName, Boolean globalDataAccess,
			Boolean disabled, String reasonDisabled, String passwordHash,
			String secretQuestion, String secretAnswerHash, Date dateCreated,
			Date dateChanged, String uuid) {
		super();
		this.userId = userId;
		this.usersByCreatedBy = usersByCreatedBy;
		this.locationByCreatedAt = locationByCreatedAt;
		this.locationByChangedAt = locationByChangedAt;
		this.usersByChangedBy = usersByChangedBy;
		this.username = username;
		this.fullName = fullName;
		this.globalDataAccess = globalDataAccess;
		this.disabled = disabled;
		this.reasonDisabled = reasonDisabled;
		this.passwordHash = passwordHash;
		this.secretQuestion = secretQuestion;
		this.secretAnswerHash = secretAnswerHash;
		this.dateCreated = dateCreated;
		this.dateChanged = dateChanged;
		this.uuid = uuid;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "username", nullable = false, unique = true, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	@Column(name = "full_name")
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "global_data_access")
	public Boolean getGlobalDataAccess() {
		return this.globalDataAccess;
	}

	public void setGlobalDataAccess(Boolean globalDataAccess) {
		this.globalDataAccess = globalDataAccess;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "reason_disabled")
	public String getReasonDisabled() {
		return this.reasonDisabled;
	}

	public void setReasonDisabled(String reasonDisabled) {
		this.reasonDisabled = reasonDisabled;
	}

	@Column(name = "password_hash")
	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Column(name = "secret_question")
	public String getSecretQuestion() {
		return this.secretQuestion;
	}

	public void setSecretQuestion(String secretQuestion) {
		this.secretQuestion = secretQuestion;
	}

	@Column(name = "secret_answer_hash")
	public String getSecretAnswerHash() {
		return this.secretAnswerHash;
	}

	public void setSecretAnswerHash(String secretAnswerHash) {
		this.secretAnswerHash = secretAnswerHash;
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
}