package com.star.sud.dto;

public class Configuration {

	private String configKey;
	private String configValue;
	private Character status;

	public Configuration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Configuration(String configKey, String configValue, Character status) {
		super();
		this.configKey = configKey;
		this.configValue = configValue;
		this.status = status;
	}

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
