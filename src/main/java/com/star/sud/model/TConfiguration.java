package com.star.sud.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_CONFIGURATION")
public class TConfiguration implements Serializable {

	// Static Attributes
	/////////////////////
	private static final long serialVersionUID = -4529244754157921885L;

	// Attributes
	//////////////
	@Id
	@Column(name = "CONFIG_KEY", length = 35, unique = true, nullable = false)
	private String configKey;

	@Column(name = "CONFIG_VALUE", nullable = false)
	private String configValue;

	@Column(name = "STATUS", length = 1, nullable = false)
	private Character status;

	// Constructors
	///////////////////
	public TConfiguration() {
		super();
	}

	/**
	 * @param configKey
	 * @param configValue
	 */
	public TConfiguration(String configKey, String configValue) {
		super();
		this.configKey = configKey;
		this.configValue = configValue;
	}

	/**
	 * @param configKey
	 * @param configValue
	 * @param status
	 */
	public TConfiguration(String configKey, String configValue, Character status) {
		super();
		this.configKey = configKey;
		this.configValue = configValue;
		this.status = status;
	}

	// Properties
	/////////////////
	/**
	 * @return the configKey
	 */
	public String getConfigKey() {
		return configKey;
	}

	/**
	 * @param configKey the configKey to set
	 */
	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	/**
	 * @return the configValue
	 */
	public String getConfigValue() {
		return configValue;
	}

	/**
	 * @param configValue the configValue to set
	 */
	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	/**
	 * @return the status
	 */
	public Character getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Character status) {
		this.status = status;
	}

}
